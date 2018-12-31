package tech.arinzedroid.dspeech.repo;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.arinzedroid.dspeech.DSpeech;
import tech.arinzedroid.dspeech.interfaces.ApiService;
import tech.arinzedroid.dspeech.interfaces.ErrorInterface;
import tech.arinzedroid.dspeech.model.Rbody;
import tech.arinzedroid.dspeech.model.ResponseValue;
import tech.arinzedroid.dspeech.model.TranslateBody;
import tech.arinzedroid.dspeech.model.TranslateResponse;

/**
 * Created by ACER on 5/9/2018.
 */

public class Repo implements Application.ActivityLifecycleCallbacks{
    private ApiService apiService;
    private ErrorInterface errorInterface;
    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient;
    private String SPEECH_BASE_URL = "https://speech.googleapis.com/";
    private String TRANSLATE_BASE_URL = "https://translation.googleapis.com/";

    public Repo(){

       DSpeech.getDspeech().registerActivityLifecycleCallbacks(this);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS);

        // add logging as last interceptor
        httpClient.addInterceptor(logging);

    }

    private void setRetrofitObject(final String BASE_URL) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private void attachInterface(Activity activity){
        try{
            if(activity instanceof ErrorInterface){
                errorInterface = (ErrorInterface) activity;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public LiveData<ResponseValue> transcribe(Rbody rbody, String field, String key){
      final MutableLiveData<ResponseValue> responseValueMutableLiveData = new MutableLiveData<>();
      setRetrofitObject(SPEECH_BASE_URL);
      apiService.getText(rbody,field,key).enqueue(new Callback<ResponseValue>() {
          @Override
          public void onResponse(@NonNull Call<ResponseValue> call,@NonNull Response<ResponseValue> response) {
              if(response.isSuccessful()){
                 responseValueMutableLiveData.postValue(response.body());
              }
              else{
                  try{
                      if(errorInterface != null){
                          errorInterface.onError(response.errorBody().string());
                      }
                  }catch (Exception ex){
                      ex.printStackTrace();
                  }

              }
          }
          @Override
          public void onFailure(@NonNull Call<ResponseValue> call,@NonNull Throwable t) {
            t.printStackTrace();
          }
      });
      return responseValueMutableLiveData;
    }

    public LiveData<TranslateResponse> translateText(TranslateBody translateBody, String key){
        final MutableLiveData<TranslateResponse> translateResponseMutableLiveData = new MutableLiveData<>();
        setRetrofitObject(TRANSLATE_BASE_URL);
        apiService.translateText(translateBody,key).enqueue(new Callback<TranslateResponse>() {
            @Override
            public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                if(response.isSuccessful()){
                    translateResponseMutableLiveData.postValue(response.body());
                }else{
                    try{
                        if(errorInterface != null){
                            errorInterface.onError(response.errorBody().string());
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TranslateResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return translateResponseMutableLiveData;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        attachInterface(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        DSpeech.getDspeech().unregisterActivityLifecycleCallbacks(Repo.this);
    }
}
