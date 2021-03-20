package com.example.camera_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Camera;

public class AddCameraActivity extends AppCompatActivity   {

    private EditText ipCamera;
    
    private Switch switchGpio12, switchGpio13, switchGpio14, switchGpio15, switchGpio16;

    private EditText nameGpio12;
    private EditText nameGpio13;
    private EditText nameGpio14;
    private EditText nameGpio15;
    private EditText nameGpio16;

    private Button addCameraSaveButton;
    

    private static final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camera);

        ipCamera = findViewById(R.id.addCameraIp);

        Switch switchGpio12 = (Switch) findViewById(R.id.switch_addButtons);
        Switch switchGpio13 = (Switch) findViewById(R.id.switch_addButtons2);
        Switch switchGpio14 = (Switch) findViewById(R.id.switch_addButtons3);
        Switch switchGpio15 = (Switch) findViewById(R.id.switch_addButtons4);
        Switch switchGpio16 = (Switch) findViewById(R.id.switch_addButtons5);

        addCameraSaveButton = (Button) findViewById(R.id.saveAddCameraButton);
        addCameraSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddCameraActivity.this, "la camera a bien été ajouté", Toast.LENGTH_LONG).show();
                addCameratoUser();
               // ipCamera.setText("test");
               // startActivity(new Intent(AddCameraActivity.this, CameraGridActivity.class));
            }
        });


        nameGpio12 = (EditText) findViewById(R.id.gpio12Name);
        nameGpio13 = (EditText) findViewById(R.id.gpio13Name);
        nameGpio14 = (EditText) findViewById(R.id.gpio14Name);
        nameGpio15 = (EditText) findViewById(R.id.gpio15Name);
        nameGpio16 = (EditText) findViewById(R.id.gpio16Name);




//        if(switchGpio12 != null && switchGpio13 !=null && switchGpio14 !=null && switchGpio15 !=null &&switchGpio16 != null){
//            switchGpio12.setOnCheckedChangeListener(this);
//            switchGpio13.setOnCheckedChangeListener(this);
//            switchGpio14.setOnCheckedChangeListener(this);
//            switchGpio15.setOnCheckedChangeListener(this);
//            switchGpio16.setOnCheckedChangeListener(this);
//        }



    }

    private void addCameratoUser() {

        Toast.makeText(AddCameraActivity.this, "la camera a bien été ajouté", Toast.LENGTH_LONG).show();

        String ipCam = ipCamera.getText().toString().trim();
        Boolean gpio12 = switchGpio12.isChecked();
        Boolean gpio13 = switchGpio12.isChecked();
        Boolean gpio14 = switchGpio12.isChecked();
        Boolean gpio15 = switchGpio12.isChecked();
        Boolean gpio16 = switchGpio12.isChecked();
        String gpio12Name = nameGpio12.getText().toString();
        String gpio13Name = nameGpio13.getText().toString();
        String gpio14Name = nameGpio14.getText().toString();
        String gpio15Name = nameGpio15.getText().toString();
        String gpio16Name = nameGpio16.getText().toString();

        Matcher matcher = IP_ADDRESS.matcher(ipCam);

        if( matcher.matches() == false){
            ipCamera.setError("Veuillez entrer une adresse ip correcte !");
            ipCamera.requestFocus();
            return;
        }

        if(gpio12 == true && gpio12Name.matches("")){
            nameGpio12.setError("Veuillez entrez un nom ! ");
            nameGpio12.requestFocus();
            return;
        }

        if(gpio13 == true && gpio13Name.matches("")){
            nameGpio13.setError("Veuillez entrez un nom ! ");
            nameGpio13.requestFocus();
            return;
        }

        if(gpio14 == true && gpio14Name.matches("")){
            nameGpio14.setError("Veuillez entrez un nom ! ");
            nameGpio14.requestFocus();
            return;
        }

        if(gpio15 == true && gpio15Name.matches("")){
            nameGpio15.setError("Veuillez entrez un nom ! ");
            nameGpio15.requestFocus();
            return;
        }

        if(gpio16 == true && gpio16Name.matches("")){
            nameGpio16.setError("Veuillez entrez un nom ! ");
            nameGpio16.requestFocus();
            return;
        }

        Camera cam = new Camera(ipCam, gpio12, gpio13, gpio14, gpio15, gpio16, gpio12Name, gpio13Name, gpio14Name, gpio15Name, gpio16Name);

        Toast.makeText(AddCameraActivity.this, "la camera a bien été ajouté", Toast.LENGTH_LONG).show();

//        FirebaseDatabase.getInstance("https://authentification-app-camera-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .setValue(cam).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if(task.isSuccessful()){
//                    Toast.makeText(AddCameraActivity.this, "la camera a bien été ajouté", Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });


    }


//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Toast.makeText(this, "The Switch is" + (isChecked ? "On" : "off"), Toast.LENGTH_SHORT).show();
//    }


//    @Override
//    public void onClick(View v) {
//        switch(v.getId()){
//            case R.id.saveAddCameraButton:
//                Toast.makeText(AddCameraActivity.this, "la camera a bien été ajouté", Toast.LENGTH_SHORT).show();
//                //addCameratoUser();
//                break;
//
//        }
//
//    }
}
