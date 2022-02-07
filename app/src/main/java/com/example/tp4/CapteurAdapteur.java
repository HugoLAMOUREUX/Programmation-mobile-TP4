package com.example.tp4;

import android.content.Intent;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CapteurAdapteur extends RecyclerView.Adapter<CapteurAdapteur.MyViewHolder> {

    protected List<Sensor> lesCapteurs;
    public CapteurAdapteur(List<Sensor> l){
        lesCapteurs=l;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvNom;
        public MyViewHolder(View view){
            super(view);
            tvNom=(TextView)view.findViewById(R.id.tvNom);
        }
    }

    @NonNull
    @Override
    public CapteurAdapteur.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View capteurView=layoutInflater.inflate(R.layout.sensorline,parent,false);
        return new MyViewHolder(capteurView);
    }

    @Override
    public void onBindViewHolder(@NonNull CapteurAdapteur.MyViewHolder holder, int position) {
        final CapteurAdapteur adapter=this;
        Sensor sensor=lesCapteurs.get(position);
        holder.tvNom.setText(sensor.getName());

        holder.itemView.setOnClickListener((view) -> {
            Intent intent=new Intent(view.getContext(),SensorActivity.class);
            intent.putExtra(Constante.SENSORNAME,sensor.getName());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lesCapteurs.size();
    }


}
