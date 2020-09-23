package com.example.pubapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {
     EditText email, password;
     Button loginB;
     TextView register, forgotPW;
     FirebaseAuth fAuth;
     ProgressBar progressLogin;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        email = (EditText) findViewById(R.id.emaillg);
        password = (EditText) findViewById(R.id.pswlg);
        loginB = (Button) findViewById(R.id.buttonLog);
        register = (TextView) findViewById(R.id.register);
        forgotPW = (TextView)findViewById(R.id.forgotPW);
        progressLogin = (ProgressBar)findViewById(R.id.progress_login);
        fAuth=FirebaseAuth.getInstance();

        progressLogin.setVisibility(View.INVISIBLE);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaillg = email.getText().toString().trim();
                String psw = password.getText().toString().trim();
                if (emaillg.isEmpty()) {
                    email.setError("Il campo email non può essere vuoto");
                    return;
                }
                if (psw.isEmpty()) {
                    password.setError("Il campo password non può essere vuoto");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emaillg).matches()) {
                    email.setError("L'input immesso non rispetta il formato. Es: example@gmail.com");
                    return;
                }
                if (psw.length() < 6) {
                    password.setError("La password deve essere lunga almeno 6 caratteri");
                    return;
                }

                progressLogin.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(emaillg, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login effettuato con successo", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(getApplicationContext(), HomepageActivity.class);
                            startActivity(in);
                        }else{
                            Toast.makeText(getApplicationContext(), "Errore!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressLogin.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        forgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(in);
            }
        });
    }
        public void goToHomepage (View view){
            Intent in = new Intent(this, HomepageActivity.class);
            Toast.makeText(getApplicationContext(), "Benvenuto", Toast.LENGTH_SHORT).show();
            startActivity(in);
        }



}
