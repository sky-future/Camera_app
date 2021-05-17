package com.example.camera_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public class CameraViewActivity extends AppCompatActivity implements View.OnClickListener{

    private MjpegView streamCamera;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    List<Camera> cameras ;
    String cameraIp;
    String ipCam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://authentification-app-camera-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userId = user.getUid();

        Intent intent = getIntent();
        ipCam = intent.getStringExtra("ipCam");


        Button modifyButton = findViewById(R.id.buttonModify);
        Button deleteButton = findViewById(R.id.buttonDelete);

        modifyButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

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

                            for(Camera cam : cameras){

                                if(cam.getIpCamera().equals(ipCam)){

                                    cameraIp = cam.getIpCamera();

                                    if(cam.isGpio12()){
                                        GenerateButtons(cam.getGpio12Name(), "gpio12");
                                    }

                                    if(cam.isGpio13()){
                                        GenerateButtons(cam.getGpio13Name(), "gpio13");
                                    }

                                    if(cam.isGpio14()){
                                        GenerateButtons(cam.getGpio14Name(), "gpio14");
                                    }

                                    if(cam.isGpio15()){
                                        GenerateButtons(cam.getGpio15Name(), "gpio15");
                                    }

                                    if(cam.isGpio16()){
                                        GenerateButtons(cam.getGpio15Name(), "gpio16");
                                    }
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
    public void onClick(View v) {
        //switch pour déterminer quel élément a été cliqué
        switch(v.getId()){

            case R.id.buttonModify:
                    streamCamera.stopStream();
                Intent intent = new Intent(CameraViewActivity.this, activity_Edit_Camera.class);
                intent.putExtra("ipCam", cameraIp);
                startActivity(intent);

                break;
            case R.id.buttonDelete:
                    streamCamera.stopStream();

                    deleteCamera(ipCam);


                break;
        }

    }

    private void deleteCamera(String ipCam) {


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        streamCamera.stopStream();
        startActivity(new Intent(CameraViewActivity.this, CameraGridActivity.class));
    }


    public void GenerateButtons(String buttonName, String DomainName){

        Button myButtonOn = new Button(CameraViewActivity.this);

        myButtonOn.setText(buttonName + "On");

                                    //TODO finaliser le code pour les boutons

        myButtonOn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String ipOn = "http://" + ipCam;
                                            AsyncHttpClient client = new AsyncHttpClient();
                                            client.get(ipOn + "/" + DomainName + "/on", new AsyncHttpResponseHandler() {
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

        Button myButtonOff = new Button(CameraViewActivity.this);
        myButtonOff.setText(buttonName + "Off");

        myButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipOn = "http://" + ipCam;
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(ipOn + "/" + DomainName + "/off", new AsyncHttpResponseHandler() {
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
        layout.addView(myButtonOn, layoutParams);
        layout.addView(myButtonOff, layoutParams);


    }

}