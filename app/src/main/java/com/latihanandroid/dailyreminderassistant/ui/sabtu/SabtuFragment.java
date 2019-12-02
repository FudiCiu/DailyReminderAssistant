package com.latihanandroid.dailyreminderassistant.ui.sabtu;


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
public class SabtuFragment extends Fragment {

    private RecyclerView rvSabtu;
    private JadwalHarianAdapterHelper rvSabtuHelper;
    public SabtuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sabtu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSabtu=view.findViewById(R.id.rvJadwalSabtu);
        rvSabtuHelper=new JadwalHarianAdapterHelper(rvSabtu, Calendar.SATURDAY,getActivity());
    }
}
