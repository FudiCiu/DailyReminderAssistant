package com.latihanandroid.dailyreminderassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latihanandroid.dailyreminderassistant.R;
import com.latihanandroid.dailyreminderassistant.model.JadwalHarian;

import java.util.ArrayList;

public class ItemJadwalHarianAdapter extends RecyclerView.Adapter<ItemJadwalHarianAdapter.ItemJadwalHarianViewHolder> {
    private ArrayList<JadwalHarian> mJadwalHarians=new ArrayList<>();
    private OnItemJadwalHarianClickListener onItemJadwalHarianClickListener;

    public ItemJadwalHarianAdapter(ArrayList<JadwalHarian> mJadwalHarians) {
        this.mJadwalHarians = mJadwalHarians;
    }

    public ArrayList<JadwalHarian> getmJadwalHarians() {
        return mJadwalHarians;
    }

    public void setmJadwalHarians(ArrayList<JadwalHarian> mJadwalHarians) {
        this.mJadwalHarians = mJadwalHarians;
    }

    public void setOnItemJadwalHarianClickListener(OnItemJadwalHarianClickListener onItemJadwalHarianClickListener) {
        this.onItemJadwalHarianClickListener = onItemJadwalHarianClickListener;
    }

    @NonNull
    @Override
    public ItemJadwalHarianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal_harian,parent,false);
        return new ItemJadwalHarianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemJadwalHarianViewHolder holder, int position) {
        final JadwalHarian currentJadwalHarian= mJadwalHarians.get(position);
        holder.txtWaktu.setText(currentJadwalHarian.getMWaktuAsString());
        holder.txtTempat.setText(currentJadwalHarian.getMTempat());
        holder.txtDeskripsi.setText(currentJadwalHarian.getMKeterangan());
        holder.imgIcon.setImageResource(currentJadwalHarian.getMImageId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemJadwalHarianClickListener.onItemJadwalHarianClick(currentJadwalHarian);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJadwalHarians.size();
    }

    public class ItemJadwalHarianViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtWaktu,txtTempat,txtDeskripsi;
        public ItemJadwalHarianViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon=itemView.findViewById(R.id.img_icon_jadwal_harian);
            txtWaktu=itemView.findViewById(R.id.txtWaktu);
            txtTempat=itemView.findViewById(R.id.txtTempat);
            txtDeskripsi=itemView.findViewById(R.id.txtDeskripsi);
        }
    }

    public interface OnItemJadwalHarianClickListener{
        public void onItemJadwalHarianClick(JadwalHarian jadwalHarian);
    }
}
