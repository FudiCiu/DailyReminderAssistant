package com.latihanandroid.dailyreminderassistant.ui.senin;


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
public class SeninFragment extends Fragment
//        implements JadwalHarianAdapterHelper.PopulateDataJadwalHarianInterface
{
    private RecyclerView rvJadwalHarianSenin;
    private JadwalHarianAdapterHelper mRVHelperSenin;
    private JadwalHarianViewModel jadwalHarianViewModel;
    public SeninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_senin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvJadwalHarianSenin=view.findViewById(R.id.rvJadwalSenin);
        mRVHelperSenin=new JadwalHarianAdapterHelper(rvJadwalHarianSenin,Calendar.MONDAY,getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void populateDataJadwalHarian(final ArrayList<JadwalHarian> jadwalHarians) {
//        jadwalHarianViewModel= new ViewModelProvider(getActivity()).get(JadwalHarianViewModel.class);
//        jadwalHarianViewModel.getAllByDay(Calendar.MONDAY).observe(getActivity(), new Observer<List<JadwalHarian>>() {
//            @Override
//            public void onChanged(List<JadwalHarian> datas) {
//                mRVHelperSenin.getmItemJadwalHarianAdapter().getmJadwalHarians().clear();
//                mRVHelperSenin.getmItemJadwalHarianAdapter().getmJadwalHarians().addAll(datas);
//                mRVHelperSenin.getmItemJadwalHarianAdapter().notifyDataSetChanged();
//            }
//        });
//    }


}
