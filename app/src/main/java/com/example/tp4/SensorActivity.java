package com.example.tp4;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class SensorActivity extends AppCompatActivity {

    private TextView name;
    private TextView vendor;
    private TextView power;
    private TextView version;
    private TextView resolution;
    private TextView message;
    private SensorManager sensorManager;
    private FragmentManager fm ;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        fm=this.getSupportFragmentManager();

        name=(TextView) findViewById(R.id.sensorName);
        vendor=(TextView) findViewById(R.id.vendeur);
        power=(TextView) findViewById(R.id.power);
        version=(TextView) findViewById(R.id.version);
        resolution=(TextView) findViewById(R.id.resolution);
        message=(TextView) findViewById(R.id.sensorMessage);

        Intent intent=this.getIntent();
        String sensorName=intent.getStringExtra(Constante.SENSORNAME);
        name.setText(sensorName+"");
        for(Sensor s : sensorManager.getSensorList(Sensor.TYPE_ALL)){
            if(sensorName.equals(s.getName())){
                vendor.setText("Vendor : "+s.getVendor());
                power.setText("Power : "+s.getPower());
                version.setText("Version : "+s.getVersion());
                resolution.setText("Resolution : "+s.getResolution());
                message.setText("");
                if(s.getType()==Sensor.TYPE_ACCELEROMETER){
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.add(R.id.layoutContainer,new FragmentAccelerometre());
                    ft.commit();
                }
            }
        }

    }

}
