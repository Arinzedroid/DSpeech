package tech.arinzedroid.dspeech;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class DSpeech extends MultiDexApplication {
    public static DSpeech dSpeech;

    @Override
    public void onCreate(){
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        dSpeech = this;
    }

    public static DSpeech getDspeech() {
        return dSpeech;
    }
}
