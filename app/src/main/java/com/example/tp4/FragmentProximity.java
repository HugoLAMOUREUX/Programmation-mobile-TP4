package com.example.tp4;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentProximity extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    MediaPlayer deuxLaser;
    TextView distance;
    long lastUpdate;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.layoutproximity, container, false);

        distance=v.findViewById(R.id.distance);
        sensorManager=(SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        deuxLaser = MediaPlayer.create(getActivity(), R.raw.sabreclash);
        lastUpdate=System.currentTimeMillis();
        return v;
    }


    /* Fonction appelée quand le capteur détecte un changement */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY){
            getProximity(sensorEvent);
        }
    }

    /* Fonction qui traite un changement : elle affiche la distance entre le capteur et l'obstacle et joue le son deuxLaser */
    public void getProximity(SensorEvent event){
        float distanceFloat=event.values[0];
        long curTime = System.currentTimeMillis();
        if ((curTime - lastUpdate) > 1001){
            lastUpdate = curTime;
            distance.setText("Distance : "+distanceFloat);
            deuxLaser.start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /* Fonction qui mets un listener s'il a été enlevé en quittant l'application */

    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);
    }

    /* Fonction qui enlève le listener si on quitte l'application de manière temporaire afin de ne pas
    avoir encore les bruits même si on n'est plus dans l'application */
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onStop(){
        super.onStop();
        deuxLaser.release();
        deuxLaser = null;
    }
}
