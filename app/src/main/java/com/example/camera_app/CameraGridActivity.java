package com.example.camera_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
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
import model.User;

public class CameraGridActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private TextView loggedInUser;

    private MjpegView streamView;
    private int countCameras;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout testCam;
    Toolbar toolbar;

    List<Camera> cameras;

    private String location = "http://10.104.210.29";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_grid);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://authentification-app-camera-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        drawerLayout = findViewById(R.id.cameraLayout);
        navigationView = findViewById(R.id.nav_view);
        testCam = findViewById(R.id.testCamera); //test camera stream
        toolbar = findViewById(R.id.toolbar);
        loggedInUser = findViewById(R.id.welkomText);
//        loggedInUser.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


        /*---------------- Toolbar--------------------*/

        setSupportActionBar(toolbar);

        /*---------------- Navigation Drawer Menu--------------------*/

        //Permet de rendre le menu clickable en le mettant en avant.
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //rend le menu clickable, il faut implementer OnNavigationItemSelectedListener...
       navigationView.setNavigationItemSelectedListener(this);

       //Selectionne un élément du menu par défaut.
       navigationView.setCheckedItem(R.id.nav_home);

       /*-----------------Calculate number of cameras in Database-------------------*/

        reference.child(userID)
                .child("Cameras")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            int i = 0;
                            countCameras = (int) snapshot.getChildrenCount();
                            Toast.makeText(CameraGridActivity.this, String.valueOf(countCameras), Toast.LENGTH_SHORT).show();

                            cameras = new ArrayList<>();
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){

                                Camera cam = snapshot1.getValue(Camera.class);
                                Toast.makeText(CameraGridActivity.this, cam.getIpCamera(), Toast.LENGTH_SHORT).show();
                                cameras.add(cam);

                                streamView = new MjpegView(CameraGridActivity.this);
                                streamView.setId(i++);
                                testCam.addView(streamView);
                                streamView.setMode(streamView.MODE_FIT_WIDTH);
                                streamView.getLayoutParams().height = 50;
                                streamView.setAdjustHeight(true);
                                streamView.setUrl("http://"+cam.getIpCamera()+"/stream");
                                streamView.startStream();

                                streamView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        streamView.stopStream();
                                        Intent intent = new Intent(CameraGridActivity.this, CameraViewActivity.class);
                                        intent.putExtra("ipCam", cam.getIpCamera());

                                        startActivity(intent);

                                    }
                                });
                            }
                        }
                        else
                        {
                            countCameras = 0;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //TODO ajouter une action si ça fonctionne pas
                    }
                });

//       reference.child(userID)
//               .child("Cameras").addListenerForSingleValueEvent(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//               User userProfile = snapshot.getValue(User.class);
//
//               if(userProfile != null){
//                  String ip = userProfile.getCameras().get(0).ipCamera;
//                   Toast.makeText(CameraGridActivity.this, ip, Toast.LENGTH_SHORT).show();
//               }
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//               Toast.makeText(CameraGridActivity.this, "quelque chose ne s'est pas passé comme prévu", Toast.LENGTH_SHORT).show();
//           }
//       });

    }

    /*---------------- Action when backpressed--------------------*/
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    /*---------------- Menu Selection organiser--------------------*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                streamView.stopStream();
                startActivity(new Intent(CameraGridActivity.this, MainActivity.class));
                break;
            case R.id.nav_logout:
                streamView.stopStream();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CameraGridActivity.this, MainActivity.class));
                Toast.makeText(CameraGridActivity.this, "Logout succesfull", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_add:
                if(cameras != null){
                    streamView.stopStream();
                }

                startActivity(new Intent(CameraGridActivity.this, AddCameraActivity.class));
                break;
            case R.id.nav_profile:
                streamView.stopStream();
                startActivity(new Intent(CameraGridActivity.this, activity_user_profile.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);//Ferme le menu quand un bouton est exécuté.
        return true;
    }



}


//        onButton = (Button)findViewById(R.id.onButton);
//        onButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Utilise une requete asynchrone afin de ne pas faire la requete dans le mainthread.
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.get(location+"/on", new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        Toast.makeText(CameraGridActivity.this, "On / 200 ok", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                        Toast.makeText(CameraGridActivity.this, "Erreur 404", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        });
//
//        offButton = (Button)findViewById(R.id.offButton);
//        offButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.get(location+"/off", new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        Toast.makeText(CameraGridActivity.this, "Off / 200 ok", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                        Toast.makeText(CameraGridActivity.this, "Erreur 404", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        });

        //mjpegView1 = (MjpegView) findViewById(R.id.mjpegView1);
        //DrawerLayout drawerLayout = findViewById(R.id.cameraLayout);


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




//    public static int doHttpGetRequest(String url) {
//        try {
//            HttpURLConnection conn = (HttpURLConnection) (new URL(url))
//                    .openConnection();
//            conn.setUseCaches(false);
//            conn.connect();
//            int status = conn.getResponseCode();
//            conn.disconnect();
//            return status;
//        } catch (Exception e) {
//            Log.e("doHttpGetRequest", e.toString());
//        }
//        return 0;
//    }

