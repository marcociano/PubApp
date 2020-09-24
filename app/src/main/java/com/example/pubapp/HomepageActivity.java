package com.example.pubapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import javax.xml.transform.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class HomepageActivity extends Activity implements ZXingScannerView.ResultHandler {

    Button scanQR;
    ImageView signout;
    FirebaseAuth fAuth;
    public static Button menu;
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);

        scanQR = (Button) findViewById(R.id.scan);
        signout = (ImageView) findViewById(R.id.logout);
        menu = (Button) findViewById(R.id.tomenu);
        scannerView = (ZXingScannerView) findViewById(R.id.zxscan);
        fAuth = FirebaseAuth.getInstance();


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.getInstance().signOut();
                startActivity(new Intent(HomepageActivity.this, LoginActivity.class));
                finish();
            }
        });


            Dexter.withActivity(HomepageActivity.this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    scannerView.setResultHandler(HomepageActivity.this);
                    scannerView.startCamera();
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    Toast.makeText(getApplicationContext(), "Devi accettare i permessi", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                }
            }).check();
        }


    protected void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(com.google.zxing.Result result) {
        menu.setVisibility(View.VISIBLE);
        scannerView.startCamera();
    }
}