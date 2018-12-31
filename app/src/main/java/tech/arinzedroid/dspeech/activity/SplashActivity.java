package tech.arinzedroid.dspeech.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private int permissionCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT < 23){
            checkSignIn();
        }else{
            checkPermissions();
        }
    }

    private void checkPermissions(){
        String[] permissions = new String[]{
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };

        List<String> permission_not_granted = new ArrayList<>();

        for(String permission : permissions){
            if(ActivityCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                permission_not_granted.add(permission);
            }
        }

        if(!permission_not_granted.isEmpty()){
            String[] newPermissions = new String[permission_not_granted.size()];
            permission_not_granted.toArray(newPermissions);
            ActivityCompat.requestPermissions(this,newPermissions,permissionCode);
        }else{
            checkSignIn();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
        if(requestCode == permissionCode){
            boolean ok = true;
            for (int grantResult : grantResults) {
                ok = ok && (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (ok) {
                checkSignIn();
            }else{
                Toast.makeText(this, "All permissions not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void checkSignIn(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else{
            startActivity(new Intent(this,SignInActivity.class));
            finish();
        }
    }
}
