package com.example.pubapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        scannerView= new ZXingScannerView(this);
        setContentView(scannerView);
        onBackPressed();
    }

    @Override
    public void handleResult(Result result) {
        Intent in = new Intent(QRScanActivity.this, MenuActivity.class);
        startActivity(in);
    }

    protected void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }

    protected void onResume(){
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
