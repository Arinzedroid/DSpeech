package tech.arinzedroid.dspeech.logics;

import java.io.File;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

/**
 * Created by ACER on 5/8/2018.
 */

public class RecordAudio implements MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener{

    private MediaRecorder recorder;
    private  File filePath;


    public RecordAudio(){

    }

    public void initialize(){

    }

    public String getPath(){
        return filePath.getPath();
    }


    private File sanitizePath() {
        String path = "dspeech.amr";
        filePath = new File(Environment.getExternalStorageDirectory() + File.separator + "DSpeech",path);
        File fp = new File(Environment.getExternalStorageDirectory() + File.separator + "DSpeech");
        if(!fp.exists()){
            boolean val = fp.mkdirs();
            Log.e("RecordAudio","directory creation is " + String.valueOf(val));
        }
        return filePath;
    }

    private void initRecord(){
        Log.e("RecordAudio","Record audio called");
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
        recorder.setAudioChannels(1);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setAudioEncodingBitRate(16);
        recorder.setAudioSamplingRate(16000);
        recorder.setOutputFile(sanitizePath().getPath());
    }

    /**
     * Starts a new recording.
     */
     public void start() throws Exception {
         initRecord();
         recorder.prepare();
         recorder.start();
    }

    /**
     * Stops a recording that has been previously started.
     */
    public File stop(){
        Log.e("RecordAudio","Record audio stop");
        Log.e("RecordAudio","path "+ filePath.getPath());
        recorder.stop();
        recorder.release();
        return filePath;
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        Log.e(this.getClass().getSimpleName(),"OnError >> " + String.valueOf(what));
        Log.e(this.getClass().getSimpleName(),"OnError >> " + String.valueOf(extra));
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        Log.e(this.getClass().getSimpleName(),"OnInfo >> " + String.valueOf(what));
        Log.e(this.getClass().getSimpleName(),"OnInfo >> " + String.valueOf(extra));
    }
}
