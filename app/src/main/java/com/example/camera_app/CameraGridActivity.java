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

import java.net.HttpURLConnection;
import java.net.URL;

public class CameraGridActivity extends AppCompatActivity {

    private Button logout;
    private Button relayOn;
    private Button relayOff;


    private ImageView testCamera;

    private MjpegView mjpegView1;

    private String url = "http://192.168.1.47/stream";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_grid);

        DrawerLayout drawerLayout = findViewById(R.id.cameraLayout);



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

//    public void volleyGet(){
//
//    }

//    public void volleyGet(){
//        String relayOnString = "http://192.168.1.47/on";
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, relayOnString, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                String texte;
//                texte = response.substring(0, 500);
//                Toast.makeText(CameraGridActivity.this, texte, Toast.LENGTH_LONG).show();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(CameraGridActivity.this, "marche pas", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        requestQueue.add(stringRequest);
//    }
}