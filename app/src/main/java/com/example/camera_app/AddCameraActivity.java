package com.example.camera_app;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Camera;

public class AddCameraActivity extends AppCompatActivity {

    private EditText ipCamera;
    
    private SwitchMaterial switchGpio12, switchGpio13, switchGpio14, switchGpio15, switchGpio16;
    private String gpio13Name, gpio14Name, gpio15Name, gpio16Name, gpio12Name;

    private Boolean gpio12 = false,
            gpio13 = false,
            gpio14 =false ,
            gpio15 = false,
            gpio16 = false;

    private EditText nameGpio12;
    private EditText nameGpio13;
    private EditText nameGpio14;
    private EditText nameGpio15;
    private EditText nameGpio16;

    private int countCameras = 0;

    private Button addCameraSaveButton;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    

    private static final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camera);

        ipCamera = findViewById(R.id.addCameraIp);

        switchGpio12 = findViewById(R.id.switch_addButtons);
        switchGpio13 = findViewById(R.id.switch_addButtons2);
        switchGpio14 = findViewById(R.id.switch_addButtons3);
        switchGpio15 = findViewById(R.id.switch_addButtons4);
        switchGpio16 = findViewById(R.id.switch_addButtons5);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://authentification-app-camera-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        switchGpio12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(AddCameraActivity.this, "active", Toast.LENGTH_LONG).show();
                    gpio12 = true;
                } else{
                    gpio12 = false;
                }
            }
        });
        switchGpio13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(AddCameraActivity.this, "active", Toast.LENGTH_LONG).show();
                    gpio13 = true;
                }else{
                    gpio13 = false;
                }
            }
        });
        switchGpio14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(AddCameraActivity.this, "active", Toast.LENGTH_LONG).show();
                    gpio14 = true;
                }else{
                    gpio14 = false;
                }
            }
        });
        switchGpio15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(AddCameraActivity.this, "active", Toast.LENGTH_LONG).show();
                    gpio15 = true;
                }else{
                    gpio15 = false;
                }
            }
        });
        switchGpio16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(AddCameraActivity.this, "active", Toast.LENGTH_LONG).show();
                    gpio16 = true;
                }else{
                    gpio16 = false;
                }
            }
        });

        nameGpio12 = (EditText) findViewById(R.id.gpio12Name);
        nameGpio13 = (EditText) findViewById(R.id.gpio13Name);
        nameGpio14 = (EditText) findViewById(R.id.gpio14Name);
        nameGpio15 = (EditText) findViewById(R.id.gpio15Name);
        nameGpio16 = (EditText) findViewById(R.id.gpio16Name);


        addCameraSaveButton = (Button) findViewById(R.id.saveAddCameraButton);
        addCameraSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCameratoUser();

            }
        });

        reference.child(userID)
                .child("Cameras")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            countCameras = (int) snapshot.getChildrenCount();
                            Toast.makeText(AddCameraActivity.this, String.valueOf(countCameras), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            countCameras = 0;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void addCameratoUser() {


        String ipCam = ipCamera.getText().toString().trim();

         gpio12Name = nameGpio12.getText().toString();
         gpio13Name = nameGpio13.getText().toString();
         gpio14Name = nameGpio14.getText().toString();
         gpio15Name = nameGpio15.getText().toString();
         gpio16Name = nameGpio16.getText().toString();

        Matcher matcher = IP_ADDRESS.matcher(ipCam);

        if( matcher.matches() == false){
            ipCamera.setError("Veuillez entrer une adresse ip correcte !");
            ipCamera.requestFocus();
            return;
        }

        if(switchGpio12.isChecked() == true && gpio12Name.matches("")){
            nameGpio12.setError("Veuillez entrez un nom ! ");
            nameGpio12.requestFocus();
            return;
        }

        if(switchGpio13.isChecked() == true && gpio13Name.matches("")){
            nameGpio13.setError("Veuillez entrez un nom ! ");
            nameGpio13.requestFocus();
            return;
        }

        if(switchGpio14.isChecked() == true && gpio14Name.matches("")){
            nameGpio14.setError("Veuillez entrez un nom ! ");
            nameGpio14.requestFocus();
            return;
        }

        if(switchGpio15.isChecked() == true && gpio15Name.matches("")){
            nameGpio15.setError("Veuillez entrez un nom ! ");
            nameGpio15.requestFocus();
            return;
        }

        if(switchGpio16.isChecked() == true && gpio16Name.matches("")){
            nameGpio16.setError("Veuillez entrez un nom ! ");
            nameGpio16.requestFocus();
            return;
        }


        Camera cam = new Camera(ipCam,gpio12, gpio13, gpio14, gpio15, gpio16, gpio12Name, gpio13Name, gpio14Name, gpio15Name, gpio16Name);

                reference.child(userID)
                        .child("Cameras")
                        .child(String.valueOf(countCameras+1))
                        .setValue(cam)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(AddCameraActivity.this, "la camera a bien été ajouté", Toast.LENGTH_LONG).show();


                }
            }
        });


        startActivity(new Intent(AddCameraActivity.this, CameraGridActivity.class));

    }

}
