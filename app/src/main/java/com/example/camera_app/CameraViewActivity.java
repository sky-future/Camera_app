package com.example.camera_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import model.Camera;

public class CameraViewActivity extends AppCompatActivity {

    private MjpegView streamCamera;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    List<Camera> cameras ;
    Camera cam;


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
                                cam = snapshot1.getValue(Camera.class);
                                cameras.add(cam);
                                Toast.makeText(CameraViewActivity.this, String.valueOf(cam.isGpio15()), Toast.LENGTH_SHORT).show();
                                if(cam.isGpio15()){

                                    Toast.makeText(CameraViewActivity.this, String.valueOf(cam.isGpio12()), Toast.LENGTH_SHORT).show();

                                    Button myButton = new Button(CameraViewActivity.this);
                                    myButton.setText(cam.getGpio15Name());

                                    //TODO finaliser le code pour les boutons

                                    myButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String ipOn = "http://" + ipCam;
                                            AsyncHttpClient client = new AsyncHttpClient();
                                            client.get(ipOn + "/on", new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                    Toast.makeText(CameraViewActivity.this, "On / 200 ok", Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                    Toast.makeText(CameraViewActivity.this, "Erreur 404", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });

                                    LinearLayout layout = findViewById(R.id.testStream);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layout.addView(myButton, layoutParams);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        streamCamera.stopStream();
        startActivity(new Intent(CameraViewActivity.this, CameraGridActivity.class));
    }



}