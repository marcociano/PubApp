package com.example.pubapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class RegisterActivity extends Activity {


    EditText emailReg, passwordReg, checkPasswordReg;
    Button buttonReg;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);


        fAuth= FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        emailReg = (EditText)findViewById(R.id.emailReg);
        passwordReg = (EditText)findViewById(R.id.pswReg);
        checkPasswordReg = (EditText)findViewById(R.id.checkPsw);
        buttonReg = (Button)findViewById(R.id.buttonReg);

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }


        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailReg.getText().toString().trim();
                final String psw = passwordReg.getText().toString().trim();
                String confPsw = checkPasswordReg.getText().toString().trim();

                if (email.isEmpty()) {
                    emailReg.setError("Il campo email non può essere vuoto");
                    return;
                }
                if (psw.isEmpty()) {
                    passwordReg.setError("Il campo password non può essere vuoto");
                    return;
                }
                if (confPsw.isEmpty()) {
                    checkPasswordReg.setError("il campo conferma password non può essere vuoto");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailReg.setError("L'input immesso non rispetta il formato. Es: example@gmail.com");
                    return;
                }
                if (psw.length() < 6) {
                    passwordReg.setError("La password deve essere lunga almeno 6 caratteri");
                    return;
                }
                if (!psw.equals(confPsw)) {
                    passwordReg.setError("Le password immesse non corrispondono");
                    checkPasswordReg.setError("Le password immesse non corrispondono");
                }
                    fAuth.createUserWithEmailAndPassword(email, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registrazione avvenuta con successo", Toast.LENGTH_LONG).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference= fstore.collection("users").document(userID);
                                Map<String, Object> user= new HashMap<>();
                                user.put("email", email);
                                user.put("password", psw);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Sucesso: il profilo utente è stato creato per" + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "Fallimento: " + e.toString());
                                    }
                                });
                                Intent in= new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(in);
                            } else {
                                Toast.makeText(getApplicationContext(), "Errore!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
        });
    }
    public void goToLogin(View v){
        Intent in = new Intent(this, LoginActivity.class);
        startActivity(in);
    }
}
