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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.longdo.mjpegviewer.MjpegView;

public class CameraGridActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button logout;
    private Button onButton;
    private Button offButton;
    private TextView loggedInUser;

    private MjpegView mjpegView1;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private String location = "http://10.104.210.29";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_grid);

        drawerLayout = findViewById(R.id.cameraLayout);
        navigationView = findViewById(R.id.nav_view);
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
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CameraGridActivity.this, MainActivity.class));
                Toast.makeText(CameraGridActivity.this, "Logout succesfull", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_add:
                startActivity(new Intent(CameraGridActivity.this, CameraViewActivity.class));
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

