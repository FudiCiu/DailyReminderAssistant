package com.latihanandroid.dailyreminderassistant.ui.kegiatan_penting;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.latihanandroid.dailyreminderassistant.R;
import com.latihanandroid.dailyreminderassistant.TambahUpdateKegiatanPentingActivity;
import com.latihanandroid.dailyreminderassistant.adapter.ItemKegiatanPentingAdapter;
import com.latihanandroid.dailyreminderassistant.model.KegiatanPenting;
import com.latihanandroid.dailyreminderassistant.model.KegiatanPentingViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class KegiatanPentingFragment extends Fragment implements ItemKegiatanPentingAdapter.OnItemKegiatanPentingListener {
    private RecyclerView rvKegiatanPenting;
    private ItemKegiatanPentingAdapter kegiatanPentingAdapter;
    private KegiatanPentingViewModel viewModel;
    public KegiatanPentingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kegiatan_penting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvKegiatanPenting=view.findViewById(R.id.rvKegiatanPenting);
        kegiatanPentingAdapter=new ItemKegiatanPentingAdapter();

        rvKegiatanPenting.setLayoutManager(new LinearLayoutManager(getContext()));
        rvKegiatanPenting.setHasFixedSize(false);
        rvKegiatanPenting.setAdapter(kegiatanPentingAdapter);
        populateAdapter();
        kegiatanPentingAdapter.setOnItemKegiatanPentingListener(this);
    }

    public void populateAdapter(){
        if (getActivity()==null) return;
        viewModel=new ViewModelProvider(getActivity()).get(KegiatanPentingViewModel.class);
        viewModel.getAllKegiatanPenting().observe(getActivity(),new Observer<List<KegiatanPenting>>() {
            @Override
            public void onChanged(List<KegiatanPenting> kegiatanPentings) {
                kegiatanPentingAdapter.getmKegiatanPentings().clear();
                kegiatanPentingAdapter.getmKegiatanPentings().addAll(kegiatanPentings);
                kegiatanPentingAdapter.notifyDataSetChanged();
                kegiatanPentingAdapter.getmKegiatanPentings();
                Log.d(KegiatanPentingFragment.class.getSimpleName(), "dataSetChanged: ");
//                tampilkanPesanToast(String.valueOf(kegiatanPentings.size())+"  "+String.valueOf(kegiatanPentingAdapter.getmKegiatanPentings().size()));
//                rvKegiatanPenting.scrollToPosition(k);
            }
        });
    }
    private void tampilkanPesanToast(String message){
        Toast.makeText(this.getContext(),message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onItemKegiatanPentingClick(KegiatanPenting kegiatanPenting) {
        Intent intent=new Intent(getActivity(), TambahUpdateKegiatanPentingActivity.class);
        intent.putExtra(TambahUpdateKegiatanPentingActivity.EXTRA_KEGIATAN_PENTING,kegiatanPenting);
        intent.putExtra(TambahUpdateKegiatanPentingActivity.EXTRA_RC,TambahUpdateKegiatanPentingActivity.RC_UPDATE);
        startActivity(intent);
    }
}
