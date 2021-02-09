package com.example.camera_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailForgotPassword;
    private Button resetEmailButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailForgotPassword = (EditText) findViewById(R.id.mailForgotPassword);

        resetEmailButton = (Button) findViewById(R.id.resetEmailButton);

        auth = FirebaseAuth.getInstance();

        resetEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailForgotPassword.getText().toString().trim();

        if(email.isEmpty()){
            emailForgotPassword.setError("Email est requis !");
            emailForgotPassword.requestFocus();
            return;
        }
        
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailForgotPassword.setError("Veuillez entre une adresse mail valide !");
            emailForgotPassword.requestFocus();
            return;
        }
        
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Vérifiez votre boite mail", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgotPassword.this, "Veuillez essayez à nouveau", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}