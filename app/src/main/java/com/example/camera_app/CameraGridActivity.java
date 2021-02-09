package com.example.camera_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CameraGridActivity extends AppCompatActivity {

    private Button logout;
    private WebView testCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_grid);

        testCamera = (WebView) findViewById(R.id.testCamera);
        testCamera.setWebViewClient(new WebViewClient());
        testCamera.loadUrl("http://192.168.1.71:81/stream");


        logout = (Button)findViewById(R.id.loggoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CameraGridActivity.this, MainActivity.class));
            }
        });
    }
}