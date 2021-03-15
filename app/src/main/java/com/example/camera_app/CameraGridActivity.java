package com.example.camera_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import java.net.HttpURLConnection;
import java.net.URL;

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
        DrawerLayout drawerLayout = findViewById(R.id.cameraLayout);


        mjpegView1.setVisibility(View.VISIBLE);
        mjpegView1.setMode(mjpegView1.MODE_FIT_WIDTH);
        mjpegView1.setAdjustHeight(true);
        mjpegView1.setUrl(location+"/stream");
        mjpegView1.startStream();

//        //testCamera = (ImageView) findViewById(R.id.testCamera);
//        mjpegView1 = (MjpegView) findViewById(R.id.mjpegView1);
//
//       // testCamera.setVisibility(View.GONE);
//        mjpegView1.setVisibility(View.VISIBLE);
//        mjpegView1.setMode(mjpegView1.MODE_FIT_WIDTH);
//        mjpegView1.setAdjustHeight(true);
//        mjpegView1.setUrl(url);
//        mjpegView1.startStream();


//
    }

    public static int doHttpGetRequest(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url))
                    .openConnection();
            conn.setUseCaches(false);
            conn.connect();
            int status = conn.getResponseCode();
            conn.disconnect();
            return status;
        } catch (Exception e) {
            Log.e("doHttpGetRequest", e.toString());
        }
        return 0;
    }
}