package com.example.camera_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.User;

public class activity_user_profile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID, firstName, lastName, email;
    TextInputLayout firstNameTextInput, lastNameTextInput, emailTextInput;
    private Button updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://authentification-app-camera-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        firstNameTextInput = findViewById(R.id.firstName);
        lastNameTextInput = findViewById(R.id.lastName);
        emailTextInput = findViewById(R.id.email);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                     firstName = userProfile.firstName;
                     lastName = userProfile.lastName;
                     email = userProfile.email;

                    firstNameTextInput.getEditText().setText(firstName);
                    lastNameTextInput.getEditText().setText(lastName);
                    emailTextInput.getEditText().setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity_user_profile.this, "quelque chose ne s'est pas passé comme prévu", Toast.LENGTH_SHORT).show();
            }
        });

        updateProfile = findViewById(R.id.updateButton);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                Toast.makeText(activity_user_profile.this, "Le profil a été mit à jour !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity_user_profile.this, CameraGridActivity.class));
            }
        });



    }



    public void update(){

        if(!firstName.equals(firstNameTextInput.getEditText().getText().toString()) || !lastName.equals(lastNameTextInput.getEditText().getText().toString()) || !email.equals(emailTextInput.getEditText().getText().toString())){

            reference.child(userID).child("firstName").setValue(firstNameTextInput.getEditText().getText().toString());
            reference.child(userID).child("lastName").setValue(lastNameTextInput.getEditText().getText().toString());
            reference.child(userID).child("email").setValue(emailTextInput.getEditText().getText().toString());
            return;
        }else{
            return;
        }


    }

}