package tech.arinzedroid.dspeech.activity;

import android.arch.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import butterknife.BindView;
import butterknife.ButterKnife;
import tech.arinzedroid.dspeech.R;
import tech.arinzedroid.dspeech.adapter.ViewPagerAdapter;
import tech.arinzedroid.dspeech.fragment.HomeFragment;
import tech.arinzedroid.dspeech.interfaces.ErrorInterface;
import tech.arinzedroid.dspeech.logics.Credits;
import tech.arinzedroid.dspeech.logics.RecordAudio;
import tech.arinzedroid.dspeech.model.Rbody;
import tech.arinzedroid.dspeech.model.ResponseValue;
import tech.arinzedroid.dspeech.model.TranslateBody;
import tech.arinzedroid.dspeech.utils.PrefUtils;
import tech.arinzedroid.dspeech.viewmodels.AppViewModel;

public class MainActivity extends AppCompatActivity implements ErrorInterface,HomeFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.bottom_navigation_layout)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private AppViewModel appViewModel;
    private String key;
    private String fields = "results";
    private String languageCode;
    private ViewPagerAdapter adapter;
    private RecordAudio recordAudio;
    private volatile boolean isConcurrentRecording;
    private PrefUtils prefUtils; double mainAmt = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        ButterKnife.bind(this);

        prefUtils = new PrefUtils(this);

        setMainAmt();

        //get the api key
        key = getString(R.string.api_key);

        //set up toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        toolbarTitle.setText(String.valueOf("Home"));

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.item_home:{
                    item.setChecked(true);
                    viewPager.setCurrentItem(0,true);
                    break;
                }
                case R.id.item_settings:{
                    item.setChecked(true);
                    viewPager.setCurrentItem(1,true);
                    break;
                }
                case R.id.item_credits:{
                    item.setChecked(true);
                    viewPager.setCurrentItem(2,true);
                    break;
                }
            }
            return true;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:{
                        homeClicked();
                        bottomNavigationView.getMenu().getItem(position).setChecked(true);
                        break;
                    }
                    case 1:{
                        settingsClicked();
                        bottomNavigationView.getMenu().getItem(position).setChecked(true);
                        break;
                    }
                    case 2:{
                        creditClicked();
                        bottomNavigationView.getMenu().getItem(position).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void delete(File filePath){

        if(filePath.exists())
            filePath.delete();
    }

    void setMainAmt(){
        mainAmt = prefUtils.getMainAmount();
        appViewModel.setMainCredit(String.valueOf(mainAmt));
    }

    private void startRecord(String languageCode){
        if(!Credits.isEnoughCredit(prefUtils.getMainAmount())){
            isConcurrentRecording = false;
            Toast.makeText(this, "Insufficient credit", Toast.LENGTH_SHORT).show();
            return;
        }
        this.languageCode = languageCode;
        if(isConcurrentRecording)
            startTimer();
        else return;
        try{
            if(recordAudio == null){
                recordAudio = new RecordAudio();
            }
            recordAudio.start();
        }catch (Exception ex){
            Log.e(this.getClass().getSimpleName(),"Error >>",ex);
            ex.printStackTrace();
        }
    }

    private void stopRecord(String LanguageCode){
        this.languageCode = LanguageCode;
        final File path = recordAudio.stop();
        if(isConcurrentRecording){
            startRecord(LanguageCode);
        }
//        try{
//            byte[] data = getBytesFromFile(path);
//            delete(path);
//            String endData =  Base64.encodeToString(data, Base64.NO_WRAP);
//            transcribe(endData, LanguageCode);
//        }catch (Exception ex){
//            Log.e(this.getClass().getSimpleName(),"Error >> ", ex);
//        }
        new Thread(() -> {
            try{
                byte[] data = getBytesFromFile(path);
                delete(path);
                String endData =  Base64.encodeToString(data, Base64.NO_WRAP);
                transcribe(endData, LanguageCode);
            }catch (Exception ex){
                Log.e(this.getClass().getSimpleName(),"Error >> ", ex);
            }
        }).start();

    }

    private void startTimer() {
        isConcurrentRecording = true;
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(isConcurrentRecording){
                    stopRecord(languageCode);
                }
            }
        }.start();
    }

    private void homeClicked(){
        toolbarTitle.setText(String.valueOf("Home"));
    }

    private void settingsClicked(){
        toolbarTitle.setText(String.valueOf("Settings"));
    }

    private void creditClicked(){
        toolbarTitle.setText(String.valueOf("Credits"));
    }

    private void transcribe(String endData, String LanguageCode){
        StringBuilder transcribeText = new StringBuilder();
        Rbody requestBody = new Rbody();
        TranslateBody translateBody = new TranslateBody();
        translateBody.setTarget(prefUtils.getLanguage());
        Rbody.Audio audio = new Rbody.Audio();
        Rbody.Config config = new Rbody.Config();
        config.setLanguageCode(LanguageCode);
        audio.setContent(endData);
        requestBody.setAudio(audio);
        requestBody.setConfig(config);

        mainAmt = mainAmt - 1.5;
        appViewModel.setMainCredit(String.valueOf(mainAmt));
        prefUtils.setMainAmount(mainAmt);
        appViewModel.getResponseValueLiveData(requestBody,fields,key).observe(this,data -> {
            if(data != null){
                if(data.getResults() != null){
                    for (ResponseValue.Result result : data.getResults()) {
                        ResponseValue.Result.Alternative alternative  = result.getAlternatives().get(0);
                        translateBody.setQ(alternative.getTranscript());
                        appViewModel.translateResponseLiveData(translateBody,key).observe(
                                MainActivity.this, response ->{
                            if (response != null && response.getData() != null
                                    && response.getData().getTranslations() != null) {
                                transcribeText.append(response.getData().getTranslations().get(0)
                                        .getTranslatedText());
                                appViewModel.setTranscribedText(transcribeText.toString());
                            }
                        });

                        Log.e(MainActivity.class.getSimpleName(),"data got " + transcribeText.toString());
                    }
                }
            }
        });
    }

    private void signOutOnly(@NonNull GoogleSignInClient googleSignInClient){
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            startActivity(new Intent(MainActivity.this,SignInActivity.class));
            finish();
        });
    }

    private void revokeAccess(GoogleSignInClient googleSignInClient){
        googleSignInClient.revokeAccess().addOnCompleteListener(task ->{
            Toast.makeText(getApplicationContext(),"Data cleared successfully",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,SignInActivity.class));
            finish();
        });

    }

    private void signOutAndRevokeAccess(@NonNull GoogleSignInClient googleSignInClient){

        googleSignInClient.signOut().addOnCompleteListener(task -> revokeAccess(googleSignInClient));
    }

    private void showDialog(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this,gso);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to clear google account data associated with this app. " +
                "This includes display name and email address. 'No' if you will install the app again")
                .setTitle("Sign Out")
                .setPositiveButton("Yes, clear data", (dialogInterface, i) ->
                        signOutAndRevokeAccess(googleSignInClient))
                .setNegativeButton("No, sign out only", (dialogInterface, i) ->
                        signOutOnly(googleSignInClient))
                .setNeutralButton("Cancel", (dialogInterface, i) -> {
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Returns the contents of the file in a byte array.
    public byte[] getBytesFromFile(File file) throws IOException {
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                    && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }

    @Override
    public void onError(final String error) {
        MainActivity.this.runOnUiThread(() -> Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onRecordStart(String languageCode) {
        isConcurrentRecording = true;
        startRecord(languageCode);
    }

    @Override
    public void onRecordStop(String LanguageCode) {
        isConcurrentRecording = false;
        stopRecord(LanguageCode);
    }

    @Override
    public void onCallEnd() {
    }

    @Override
    public void signOut(){
        showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();
                Log.e("RaveResult","Successful >> " + message);
                double mainAmt = prefUtils.getMainAmount();
                double total = prefUtils.getTxAmount() + mainAmt;
                prefUtils.setMainAmount(total);
                appViewModel.setMainCredit(String.valueOf(total));
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
                Log.e("RaveResult","Error >> " + message);
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
                Log.e("RaveResult","Cancelled >> " + message);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

