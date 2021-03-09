package com.example.camera_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.longdo.mjpegviewer.MjpegView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class CameraGridActivity extends AppCompatActivity {

    private Button logout;
    private Button onButton;
    private Button offButton;

    private MjpegView mjpegView1;

    private String location = "http://10.104.210.29";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_grid);

        onButton = (Button)findViewById(R.id.onButton);
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utilise une requete asynchrone afin de ne pas faire la requete dans le mainthread.
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(location+"/on", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(CameraGridActivity.this, "On / 200 ok", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(CameraGridActivity.this, "Erreur 404", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        offButton = (Button)findViewById(R.id.offButton);
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(location+"/off", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(CameraGridActivity.this, "Off / 200 ok", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(CameraGridActivity.this, "Erreur 404", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        mjpegView1 = (MjpegView) findViewById(R.id.mjpegView1);

        mjpegView1.setVisibility(View.VISIBLE);
        mjpegView1.setMode(mjpegView1.MODE_FIT_WIDTH);
        mjpegView1.setAdjustHeight(true);
        mjpegView1.setUrl(location+"/stream");
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