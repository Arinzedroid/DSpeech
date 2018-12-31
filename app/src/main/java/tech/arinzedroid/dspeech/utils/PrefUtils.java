package tech.arinzedroid.dspeech.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {

    private static final String LANGUAGE = "LANGUAGE";
    private static final String TX_AMOUNT = "TX_AMOUNT";
    private static final String MAIN_AMOUNT = "MAIN_AMOUNT";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PrefUtils(Activity activity){
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = prefs.edit();
    }

    public void setLanguage(String language){
        editor.putString(LANGUAGE,language);
        editor.apply();
    }

    public String getLanguage(){
        return prefs.getString(LANGUAGE,"");
    }

    public void setTxAmount(Double amount){
        editor.putString(TX_AMOUNT,String.valueOf(amount));
        editor.apply();
    }

    public double getTxAmount(){
        String txAmount = prefs.getString(TX_AMOUNT,"0.00");
        return Double.valueOf(txAmount);
    }

    public void setMainAmount(double mainAmount){
        editor.putString(MAIN_AMOUNT,String.valueOf(mainAmount));
        editor.apply();
    }

    public double getMainAmount(){
        String mainAmount = prefs.getString(MAIN_AMOUNT,"0.00");
        return Double.valueOf(mainAmount);
    }

}
