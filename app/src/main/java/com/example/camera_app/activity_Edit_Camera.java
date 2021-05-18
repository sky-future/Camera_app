package com.example.camera_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import model.Camera;

public class activity_Edit_Camera extends AppCompatActivity {

    private SwitchMaterial switchGpio12, switchGpio13, switchGpio14, switchGpio15, switchGpio16;
    private TextInputLayout ipCamera, gpio13Name, gpio14Name, gpio15Name, gpio16Name, gpio12Name;

    private Boolean gpio12 = false,
            gpio13 = false,
            gpio14 =false ,
            gpio15 = false,
            gpio16 = false;

    private List<Camera> cameras;

    private String ipCam;
    //private Camera cam;

    private EditText nameGpio12;
    private EditText nameGpio13;
    private EditText nameGpio14;
    private EditText nameGpio15;
    private EditText nameGpio16;

    private Button editCameraButton;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_camera);

        ipCamera = findViewById(R.id.editIp);

        Intent intent = getIntent();
        ipCam = intent.getStringExtra("ipCam");


        switchGpio12 = findViewById(R.id.switch_addButtons);
        switchGpio13 = findViewById(R.id.switch_addButtons2);
        switchGpio14 = findViewById(R.id.switch_addButtons3);
        switchGpio15 = findViewById(R.id.switch_addButtons4);
        switchGpio16 = findViewById(R.id.switch_addButtons5);

        gpio12Name = findViewById(R.id.gpio12Name);
        gpio13Name = findViewById(R.id.gpio13Name);
        gpio14Name = findViewById(R.id.gpio14Name);
        gpio15Name = findViewById(R.id.gpio15Name);
        gpio16Name = findViewById(R.id.gpio16Name);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://authentification-app-camera-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        //TODO terminer editer camera

        reference.child(userID)
                .child("Cameras").
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){
                    cameras = new ArrayList<>();
                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {

                        Camera cam = snapshot1.getValue(Camera.class);
                        cameras.add(cam);
                    }
                    for(Camera cam : cameras){
                        if(cam.getIpCamera().equals(ipCam)){
                            ipCamera.getEditText().setText(ipCam);
                            Toast.makeText(activity_Edit_Camera.this,
                                    "Ip ok", Toast.LENGTH_SHORT).show();
                            if(cam.isGpio12()){
                            switchGpio12.setChecked(true);
                            gpio12Name.getEditText().setText(cam.getGpio12Name());

                            }
                            if(cam.isGpio13()){
                                switchGpio13.setChecked(true);
                                gpio13Name.getEditText().setText(cam.getGpio13Name());
                            }
                            if(cam.isGpio14()){
                                switchGpio14.setChecked(true);
                                gpio14Name.getEditText().setText(cam.getGpio14Name());
                            }
                            if(cam.isGpio15()){
                                switchGpio15.setChecked(true);
                                gpio15Name.getEditText().setText(cam.getGpio15Name());
                            }
                            if(cam.isGpio16()){
                                switchGpio16.setChecked(true);
                                gpio16Name.getEditText().setText(cam.getGpio16Name());
                            }
                        }
                        else{
                            continue;
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}