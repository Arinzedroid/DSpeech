package tech.arinzedroid.dspeech.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.arinzedroid.dspeech.model.Rbody;
import tech.arinzedroid.dspeech.model.ResponseValue;
import tech.arinzedroid.dspeech.model.TranslateBody;
import tech.arinzedroid.dspeech.model.TranslateResponse;
import tech.arinzedroid.dspeech.repo.Repo;

public class AppViewModel extends ViewModel {
    private Repo appRepo;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private MutableLiveData<String> transcribedText = new MutableLiveData<>();
    private MutableLiveData<List<String>> transcribedList = new MutableLiveData<>();
    private MutableLiveData<String> mainCredit = new MutableLiveData<>();
    private MutableLiveData<String> bonusCredit = new MutableLiveData<>();

    public AppViewModel(){
        appRepo = new Repo();
    }

    public LiveData<String> getMainCredit() {
        return mainCredit;
    }

    public void setMainCredit(String mainCredit) {
        this.mainCredit.postValue(mainCredit);
    }

    public LiveData<String> getBonusCredit() {
        return bonusCredit;
    }

    public void setBonusCredit(String bonusCredit) {
        this.bonusCredit.setValue(bonusCredit);
    }


    public LiveData<ResponseValue> getResponseValueLiveData(Rbody rbody, String field, String key) {
        return appRepo.transcribe(rbody,field,key);
    }

    public LiveData<TranslateResponse> translateResponseLiveData(TranslateBody translateBody,String key){
        return appRepo.translateText(translateBody,key);
    }

    public void setTranscribedText(String transcribedText) {
        this.transcribedText.setValue(transcribedText);
        stringArrayList.add(transcribedText);
        transcribedList.setValue(stringArrayList);
    }

    public LiveData<String> getTranscribedText(){
        return transcribedText;
    }

    public LiveData<List<String>> getTranscribedList(){
        return transcribedList;
    }

    public void clearList(){
        stringArrayList.clear();
        transcribedText.setValue(null);
        transcribedList.setValue(stringArrayList);
    }
}
