package com.example.tp4;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAccelerometre extends Fragment implements SensorEventListener {

    private TextView ForceX;
    private TextView ForceY;
    private TextView ForceZ;
    float last_x;
    float last_y;
    float last_z;
    private SensorManager sensorManager;
    MediaPlayer unLaser;
    long lastUpdate;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_accelerometre, container, false);

        /* On sélectionne les TextView spécifiques à l'accéléromètre */
        ForceX=v.findViewById(R.id.acceX);
        ForceY=v.findViewById(R.id.acceY);
        ForceZ=v.findViewById(R.id.acceZ);

        sensorManager=(SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        /* On charge le son laser */
        unLaser = MediaPlayer.create(getActivity(), R.raw.lightsabre);

        lastUpdate=System.currentTimeMillis();
        return v;
    }


    /* Fonction appelée quand le capteur détecte un changement */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(sensorEvent);
        }
    }

    /* Fonction qui traite un changement : elle affiche les forces sur chaque axe et joue le son unLaser
     si le mouvement est assez brusque et long */
    public void getAccelerometer(SensorEvent sensorEvent){
        float[] values=sensorEvent.values;
        float x=values[0];
        float y=values[1];
        float z=values[2];

        long curTime = System.currentTimeMillis();
        if ((curTime - lastUpdate) > 100l) {

            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;
            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000f;
            float accelerationSquareRoot=(x*x+y*y+z*z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);
            if (speed > Constante.SHAKE_THRESHOLD && accelerationSquareRoot>=2) {
                unLaser.start();
                ForceX.setText("ForceX : "+x);
                ForceY.setText("ForceY : "+y);
                ForceZ.setText("ForceZ : "+z);
            }
            }
            last_x = x;
            last_y = y;
            last_z = z;
    }

    /* Fonction qui mets un listener s'il a été enlevé en quittant l'application */
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    /* Fonction qui enlève le listener si on quitte l'application de manière temporaire afin de ne pas
    avoir encore les bruits même si on n'est plus dans l'application */

    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onStop(){
        super.onStop();
        unLaser.release();
        unLaser = null;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
