package tech.arinzedroid.dspeech.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tech.arinzedroid.dspeech.model.Rbody;
import tech.arinzedroid.dspeech.model.ResponseValue;
import tech.arinzedroid.dspeech.model.TranslateBody;
import tech.arinzedroid.dspeech.model.TranslateResponse;

/**
 * Created by ACER on 5/9/2018.
 */

public interface ApiService {
//    @Headers("rate:16000")
    @POST("/v1/speech:recognize")
    Call<ResponseValue> getText(@Body Rbody rbody, @Query("fields")String fields, @Query("key")String Key);

    @POST("/language/translate/v2")
    Call<TranslateResponse> translateText(@Body TranslateBody translateBody,@Query("key")String Key);
}
