package com.example.camera_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.longdo.mjpegviewer.MjpegView;

import java.util.ArrayList;
import java.util.List;

import model.Camera;

public class CameraViewActivity extends AppCompatActivity {

    private MjpegView streamCamera;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    List<Camera> cameras ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://authentification-app-camera-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userId = user.getUid();

        Intent intent = getIntent();
        String ipCam = intent.getStringExtra("ipCam");
        Toast.makeText(CameraViewActivity.this, ipCam, Toast.LENGTH_SHORT).show();


        streamCamera = findViewById(R.id.mjpegView1);
        streamCamera.setUrl("http://"+ ipCam + "/stream");
        streamCamera.startStream();

        reference.child(userId)
                .child("Cameras")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            cameras = new ArrayList<>();
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                Camera cam = snapshot1.getValue(Camera.class);
                                cameras.add(cam);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        for(Camera cam : cameras){
            if(cam.getIpCamera() == ipCam){
                if(cam.isGpio15()){

                    Button myButton = new Button(this);
                    myButton.setText(cam.getGpio12Name());
                    LinearLayout layout = findViewById(R.id.testStream);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layout.addView(myButton, layoutParams);
                }
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        streamCamera.stopStream();
        startActivity(new Intent(CameraViewActivity.this, CameraGridActivity.class));
    }
}