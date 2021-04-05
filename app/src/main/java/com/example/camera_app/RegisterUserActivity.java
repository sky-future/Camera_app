package com.example.camera_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import model.User;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword;
    private ProgressBar progressBarRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerButton);
        registerUser.setOnClickListener(this);

        editTextFirstName = findViewById(R.id.firstName);
        editTextLastName = findViewById(R.id.lastName);
        editTextEmail = findViewById(R.id.registerEmail);
        editTextPassword = findViewById(R.id.registerPassword);

        progressBarRegister = findViewById(R.id.progressBarRegister);

        editTextFirstName.setText("jordan");
        editTextLastName.setText("quicken");
        editTextEmail.setText("mistert9412@gmail.com");
        editTextPassword.setText("ingeid");
    }

    @Override
    public void onClick(View v) {
    //switch pour déterminer quel élément a été cliqué
        switch(v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }

    }

    private void registerUser() {

        //récupère les valeurs de nos champs text et les met dans une variable
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();



        //Vérification sur certains champs ne sont pas vide
        if (firstName.isEmpty()) {

            editTextFirstName.setError("Un prénom est requis !");
            editTextFirstName.requestFocus();
            return;

        }
        if (lastName.isEmpty()) {

            editTextLastName.setError("Un nom est requis !");
            editTextLastName.requestFocus();
            return;

        }
        if (email.isEmpty()) {

            editTextEmail.setError("Un email est requis !");
            editTextEmail.requestFocus();
            return;

        }
        if (password.isEmpty()) {

            editTextPassword.setError("Un mot de passe est requis !");
            editTextPassword.requestFocus();
            return;
        }
        //Pattern pour vérifier si l'adresse mail est correcte.
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Veuillez entrez une adresse mail correcte !");
            editTextEmail.requestFocus();
            return;
        }
        //Vérifie si on à minimum 6 carractères dans le mot de passe.
        if (password.length() < 6) {
            editTextPassword.setError("La taille minimum de mot de passe doit être de 6 carractères !");
            editTextPassword.requestFocus();
            return;
        }

        progressBarRegister.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(firstName, lastName, email);

                            FirebaseDatabase.getInstance("https://authentification-app-camera-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) //Renvoi l'id de l'utilisateur actuel.
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUserActivity.this, "l'Utilisateur a été crée avec succès !", Toast.LENGTH_LONG).show();
                                        progressBarRegister.setVisibility(View.GONE);
                                        //TODO ajouter une redirection vers le login
                                        //Redirige vers la fenêtre de login
                                    } else {
                                        Toast.makeText(RegisterUserActivity.this, "l'utilisateur n'a pas été ajouté, veuillez réessayer", Toast.LENGTH_LONG).show();
                                        progressBarRegister.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterUserActivity.this, "l'utilisateur n'a pas été ajouté, veuillez réessayer", Toast.LENGTH_LONG).show();
                            progressBarRegister.setVisibility(View.GONE);

                        }
                    }
                });




    }


}