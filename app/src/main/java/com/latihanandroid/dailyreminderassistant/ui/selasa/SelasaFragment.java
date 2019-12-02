package com.latihanandroid.dailyreminderassistant.ui.selasa;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.latihanandroid.dailyreminderassistant.R;
import com.latihanandroid.dailyreminderassistant.helper.JadwalHarianAdapterHelper;
import com.latihanandroid.dailyreminderassistant.model.JadwalHarianViewModel;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelasaFragment extends Fragment   {
    private JadwalHarianAdapterHelper rvSelasaHelper;
    private RecyclerView rvSelasa;
    private JadwalHarianViewModel jadwalHarianViewModel;
    public SelasaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selasa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSelasa=view.findViewById(R.id.rvJadwalSelasa);
        rvSelasaHelper=new JadwalHarianAdapterHelper(rvSelasa,Calendar.TUESDAY,getActivity());
    }



}
