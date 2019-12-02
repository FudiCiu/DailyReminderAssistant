package com.latihanandroid.dailyreminderassistant.ui.rabu;


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
public class RabuFragment extends Fragment {

    private RecyclerView rvRabu;
    private JadwalHarianAdapterHelper rvRabuHelper;
    public RabuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rabu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRabu=view.findViewById(R.id.rvJadwalRabu);
        rvRabuHelper=new JadwalHarianAdapterHelper(rvRabu, Calendar.WEDNESDAY,getActivity());
    }
}
