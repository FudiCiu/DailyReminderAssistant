package com.latihanandroid.dailyreminderassistant.helper;

import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.latihanandroid.dailyreminderassistant.TambahUpdateJadwalHarianActivity;
import com.latihanandroid.dailyreminderassistant.adapter.ItemJadwalHarianAdapter;
import com.latihanandroid.dailyreminderassistant.model.JadwalHarian;
import com.latihanandroid.dailyreminderassistant.model.JadwalHarianViewModel;

import java.util.ArrayList;
import java.util.List;

public class JadwalHarianAdapterHelper implements ItemJadwalHarianAdapter.OnItemJadwalHarianClickListener {
    private RecyclerView mRVJadwalHarian;
    private ItemJadwalHarianAdapter mItemJadwalHarianAdapter;
    private ArrayList<JadwalHarian> mJadwalHarians=new ArrayList<>();
    private ItemJadwalHarianAdapter.OnItemJadwalHarianClickListener onItemJadwalHarianClick;
    private PopulateDataJadwalHarianInterface populateJadwalHarianItem;
    private JadwalHarianViewModel jadwalHarianViewModel;
    private int mDay;
    public JadwalHarianAdapterHelper(RecyclerView mRVJadwalHarian,
                                     PopulateDataJadwalHarianInterface populateJadwalHarianItem) {
        this.mRVJadwalHarian = mRVJadwalHarian;
        this.populateJadwalHarianItem = populateJadwalHarianItem;
        this.onItemJadwalHarianClick=this;
        this.mJadwalHarians.clear();
        populateJadwalHarianItem.populateDataJadwalHarian(this.mJadwalHarians);
        this.addAdapterToRecycleView();
    }

    public JadwalHarianAdapterHelper(RecyclerView mRVJadwalHarian,PopulateDataJadwalHarianInterface populateJadwalHarianItem,
                                     ItemJadwalHarianAdapter.OnItemJadwalHarianClickListener onItemJadwalHarianClick) {
        this.mRVJadwalHarian = mRVJadwalHarian;
        this.onItemJadwalHarianClick = onItemJadwalHarianClick;
        this.populateJadwalHarianItem = populateJadwalHarianItem;
        this.mJadwalHarians.clear();
        populateJadwalHarianItem.populateDataJadwalHarian(this.mJadwalHarians);
        this.addAdapterToRecycleView();
    }

    public JadwalHarianAdapterHelper(RecyclerView mRVJadwalHarian, int mDay,FragmentActivity activity) {
        this.mRVJadwalHarian = mRVJadwalHarian;
        this.mDay = mDay;
        this.onItemJadwalHarianClick=this;
        this.mJadwalHarians.clear();
        populateDataJadwalHarian(activity,mDay);
        this.addAdapterToRecycleView();

    }

    public ItemJadwalHarianAdapter.OnItemJadwalHarianClickListener getOnItemJadwalHarianClick() {
        return onItemJadwalHarianClick;
    }

    public void setOnItemJadwalHarianClick(ItemJadwalHarianAdapter.OnItemJadwalHarianClickListener onItemJadwalHarianClick) {
        this.onItemJadwalHarianClick = onItemJadwalHarianClick;
    }

    public RecyclerView getmRVJadwalHarian() {
        return mRVJadwalHarian;
    }

    public void setmRVJadwalHarian(RecyclerView mRVJadwalHarian) {
        this.mRVJadwalHarian = mRVJadwalHarian;
    }

    public ItemJadwalHarianAdapter getmItemJadwalHarianAdapter() {
        return mItemJadwalHarianAdapter;
    }

    public void setmItemJadwalHarianAdapter(ItemJadwalHarianAdapter mItemJadwalHarianAdapter) {
        this.mItemJadwalHarianAdapter = mItemJadwalHarianAdapter;
    }

    public ArrayList<JadwalHarian> getmJadwalHarians() {
        return mJadwalHarians;
    }

    public void setmJadwalHarians(ArrayList<JadwalHarian> mJadwalHarians) {
        this.mJadwalHarians = mJadwalHarians;
    }

    public PopulateDataJadwalHarianInterface getPopulateJadwalHarianItem() {
        return populateJadwalHarianItem;
    }

    public void setPopulateJadwalHarianItem(PopulateDataJadwalHarianInterface populateJadwalHarianItem) {
        this.populateJadwalHarianItem = populateJadwalHarianItem;
    }

    public JadwalHarianViewModel getJadwalHarianViewModel() {
        return jadwalHarianViewModel;
    }

    public void setJadwalHarianViewModel(JadwalHarianViewModel jadwalHarianViewModel) {
        this.jadwalHarianViewModel = jadwalHarianViewModel;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public void addAdapterToRecycleView(){
        mItemJadwalHarianAdapter=new ItemJadwalHarianAdapter(mJadwalHarians);
        if (onItemJadwalHarianClick!=null){
            mItemJadwalHarianAdapter.setOnItemJadwalHarianClickListener(onItemJadwalHarianClick);
        }
        mRVJadwalHarian.setLayoutManager(new LinearLayoutManager(mRVJadwalHarian.getContext()));
        mRVJadwalHarian.setHasFixedSize(true);
        mRVJadwalHarian.setAdapter(mItemJadwalHarianAdapter);
    }

    private void populateDataJadwalHarian(FragmentActivity activity, int day){
        jadwalHarianViewModel= new ViewModelProvider(activity).get(JadwalHarianViewModel.class);
        jadwalHarianViewModel.getAllByDay(day).observe(activity, new Observer<List<JadwalHarian>>() {
            @Override
            public void onChanged(List<JadwalHarian> datas) {
                getmItemJadwalHarianAdapter().getmJadwalHarians().clear();
                getmItemJadwalHarianAdapter().getmJadwalHarians().addAll(datas);
                getmItemJadwalHarianAdapter().notifyDataSetChanged();
                getmRVJadwalHarian().scrollToPosition(getmItemJadwalHarianAdapter().getItemCount()-1);
            }
        });
    }
    @Override
    public void onItemJadwalHarianClick(JadwalHarian jadwalHarian) {
        Intent intent=new Intent(mRVJadwalHarian.getContext(), TambahUpdateJadwalHarianActivity.class);
        intent.putExtra(TambahUpdateJadwalHarianActivity.JADWAL_HARIAN_EXTRA,jadwalHarian);
        mRVJadwalHarian.getContext().startActivity(intent);
        Toast.makeText(mRVJadwalHarian.getContext(),String.valueOf(jadwalHarian.getMId())+" "+
                String.valueOf(jadwalHarian.getMHari())+" "+
                jadwalHarian.getMHariAsString()+" "+
                jadwalHarian.getMWaktuAsString()+" "+
                jadwalHarian.getMTempat()+" "+
                String.valueOf(jadwalHarian.getMImageId())+" "+
                jadwalHarian.getMKeterangan(),Toast.LENGTH_SHORT).show();
    }


    public interface PopulateDataJadwalHarianInterface{
        public void populateDataJadwalHarian(ArrayList<JadwalHarian> jadwalHarians);
    }
}
