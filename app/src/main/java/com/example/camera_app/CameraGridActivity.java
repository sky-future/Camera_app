package com.example.camera_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.longdo.mjpegviewer.MjpegView;

public class CameraGridActivity extends AppCompatActivity {

    private Button logout;

    private ImageView testCamera;

    private MjpegView mjpegView1;

    private String url = "http://192.168.1.47/stream";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_grid);

        //testCamera = (ImageView) findViewById(R.id.testCamera);
        mjpegView1 = (MjpegView) findViewById(R.id.mjpegView1);

       // testCamera.setVisibility(View.GONE);
        mjpegView1.setVisibility(View.VISIBLE);
        mjpegView1.setMode(mjpegView1.MODE_FIT_WIDTH);
        mjpegView1.setAdjustHeight(true);
        mjpegView1.setUrl(url);
        mjpegView1.startStream();




        logout = (Button)findViewById(R.id.loggoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CameraGridActivity.this, MainActivity.class));
                mjpegView1.stopStream();
            }
        });
    }
}