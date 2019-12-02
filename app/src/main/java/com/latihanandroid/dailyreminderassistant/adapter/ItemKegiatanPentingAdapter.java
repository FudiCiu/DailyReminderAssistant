package com.latihanandroid.dailyreminderassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latihanandroid.dailyreminderassistant.R;
import com.latihanandroid.dailyreminderassistant.model.KegiatanPenting;

import java.util.ArrayList;

public class ItemKegiatanPentingAdapter extends RecyclerView.Adapter<ItemKegiatanPentingAdapter.ItemKegiatanPentingViewHolder> {
    private ArrayList<KegiatanPenting> mKegiatanPentings=new ArrayList<>();
    private OnItemKegiatanPentingListener onItemKegiatanPentingListener;

    public ArrayList<KegiatanPenting> getmKegiatanPentings() {
        return mKegiatanPentings;
    }

    public void setmKegiatanPentings(ArrayList<KegiatanPenting> mKegiatanPentings) {
        this.mKegiatanPentings = mKegiatanPentings;
    }

    public OnItemKegiatanPentingListener getOnItemKegiatanPentingListener() {
        return onItemKegiatanPentingListener;
    }

    public void setOnItemKegiatanPentingListener(OnItemKegiatanPentingListener onItemKegiatanPentingListener) {
        this.onItemKegiatanPentingListener = onItemKegiatanPentingListener;
    }

    public ItemKegiatanPentingAdapter(ArrayList<KegiatanPenting> mKegiatanPentings) {
        this.mKegiatanPentings = mKegiatanPentings;
    }

    public ItemKegiatanPentingAdapter() {
    }

    @NonNull
    @Override
    public ItemKegiatanPentingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kegiatan_penting,parent,false);
        return new ItemKegiatanPentingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemKegiatanPentingViewHolder holder, int position) {
        final KegiatanPenting kegiatanPenting=mKegiatanPentings.get(position);
        holder.txtTanggal.setText(kegiatanPenting.getMTanggalAsLString());
        holder.txtWaktu.setText(kegiatanPenting.getMWaktuAsString());
        holder.txtTempat.setText(kegiatanPenting.getMTempat());
        holder.txtDeskripsi.setText(kegiatanPenting.getMKeterangan());
        holder.imgIcon.setImageResource(R.drawable.ic_event_note_black_32dp);
        if (onItemKegiatanPentingListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemKegiatanPentingListener.onItemKegiatanPentingClick(kegiatanPenting);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mKegiatanPentings.size();
    }

    public class ItemKegiatanPentingViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtWaktu,txtTempat,txtTanggal,txtDeskripsi;
        public ItemKegiatanPentingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon=itemView.findViewById(R.id.img_icon_kegiatan_penting);
            txtWaktu=itemView.findViewById(R.id.txtWaktu);
            txtTempat=itemView.findViewById(R.id.txtTempat);
            txtDeskripsi=itemView.findViewById(R.id.txtDeskripsi);
            txtTanggal=itemView.findViewById(R.id.txtTanggal);

        }
    }

    public interface OnItemKegiatanPentingListener{
        public void onItemKegiatanPentingClick(KegiatanPenting kegiatanPenting);
    }
}
