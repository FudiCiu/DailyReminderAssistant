package com.latihanandroid.dailyreminderassistant.ui.minggu;


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

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MingguFragment extends Fragment {

    private RecyclerView rvMinggu;
    private JadwalHarianAdapterHelper rvMingguHelper;
    public MingguFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minggu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMinggu=view.findViewById(R.id.rvJadwalMinggu);
        rvMingguHelper=new JadwalHarianAdapterHelper(rvMinggu, Calendar.SUNDAY,getActivity());
    }
}
