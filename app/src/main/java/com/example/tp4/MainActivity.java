package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    private RecyclerView recyclerView;
    private List<Sensor> lesCapteurs;
    private CapteurAdapteur capteurAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lesCapteurs= sensorManager.getSensorList(Sensor.TYPE_ALL);  // On obtient la liste des capteurs

        recyclerView =(RecyclerView) findViewById(R.id.idRv);
        capteurAdapter=new CapteurAdapteur(lesCapteurs);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(capteurAdapter);

    }
}