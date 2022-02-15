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
    FragmentTransaction ft;
    Fragment frag;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        fm=this.getSupportFragmentManager();

        /* On récupère les TextView de la page de base, sans les fragments */
        name=(TextView) findViewById(R.id.sensorName);
        vendor=(TextView) findViewById(R.id.vendeur);
        power=(TextView) findViewById(R.id.power);
        version=(TextView) findViewById(R.id.version);
        resolution=(TextView) findViewById(R.id.resolution);
        message=(TextView) findViewById(R.id.sensorMessage);

        /* On récupère les données passées par l'intention */
        Intent intent=this.getIntent();
        String sensorName=intent.getStringExtra(Constante.SENSORNAME);

        name.setText(sensorName+"");

        /* On cherche le Capteur associé au nom donnée */
        for(Sensor s : sensorManager.getSensorList(Sensor.TYPE_ALL)){
            if(sensorName.equals(s.getName())){
                /* On affiche les données du capteur */
                vendor.setText("Vendor : "+s.getVendor());
                power.setText("Power : "+s.getPower());
                version.setText("Version : "+s.getVersion());
                resolution.setText("Resolution : "+s.getResolution());
                message.setText("");

                /* Si c'est un capteur accéléromètre, on ajoute le fragment accéléromètre qui contient les données
                spécifiques à ce capteur
                 */
                if(s.getType()==Sensor.TYPE_ACCELEROMETER){
                    ft=fm.beginTransaction();
                    frag=new FragmentAccelerometre();
                    ft.add(R.id.layoutContainer,frag);
                    ft.commit();
                }
                /* Si c'est un capteur accéléromètre, on ajoute le fragment proximity qui contient les données
                spécifiques à ce capteur
                 */
                if(s.getType()==Sensor.TYPE_PROXIMITY){
                    ft=fm.beginTransaction();
                    frag=new FragmentProximity();
                    ft.add(R.id.layoutContainer,frag);
                    ft.commit();
                }
            }
        }


    }

}
