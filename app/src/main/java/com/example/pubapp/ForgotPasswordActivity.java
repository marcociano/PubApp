package com.example.pubapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends Activity {

    public EditText emailRecovery;
    public Button sendRecovery;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword_activity);

        emailRecovery=(EditText)findViewById(R.id.emailRecovery);
        sendRecovery=(Button)findViewById(R.id.sendRecovery);

        sendRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailRec=emailRecovery.getText().toString().trim();

            }
        });
    }
}
