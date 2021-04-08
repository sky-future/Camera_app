package com.example.camera_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.longdo.mjpegviewer.MjpegView;

public class testCamera extends AppCompatActivity {


    private MjpegView mjpegView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camera);





        //testCamera = (ImageView) findViewById(R.id.testCamera);
        mjpegView1 = (MjpegView) findViewById(R.id.mjpegView1);

       // testCamera.setVisibility(View.GONE);
        mjpegView1.setVisibility(View.VISIBLE);
        mjpegView1.setMode(mjpegView1.MODE_FIT_WIDTH);
        mjpegView1.setAdjustHeight(true);
        mjpegView1.setUrl("192.168.1.47/stream");
        mjpegView1.startStream();

    }
}