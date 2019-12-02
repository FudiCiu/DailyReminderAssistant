package com.latihanandroid.dailyreminderassistant.ui.kamis;


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
public class KamisFragment extends Fragment {

    private RecyclerView rvKamis;
    private JadwalHarianAdapterHelper rvKamisHelper;
    public KamisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kamis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvKamis=view.findViewById(R.id.rvJadwalKamis);
        rvKamisHelper=new JadwalHarianAdapterHelper(rvKamis, Calendar.THURSDAY,getActivity());

    }
}
