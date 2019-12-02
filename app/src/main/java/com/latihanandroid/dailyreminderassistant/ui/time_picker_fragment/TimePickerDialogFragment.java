package com.latihanandroid.dailyreminderassistant.ui.time_picker_fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private DialogTimeListener mDialogTimeListener;
    public static final String TIME_PICKER_DIALOG_FOR_JADWAL_HARIAN="com.latihanandroid.dailyreminderassistant.ui.time_picker_fragment.TDP_JADWAL_HARIAN";
    public static final String TIME_PICKER_DIALOG_FOR_KEGIATAN_PENTING="com.latihanandroid.dailyreminderassistant.ui.time_picker_fragment.TDP_KEGIATAN_PENTING";
    public static final String DEFAULT_HOUR_EXTRA="com.latihanandroid.dailyreminderassistant.ui.time_picker_fragment.DEFAULT_HOUR";
    public static final String DEFAULT_MINUTE_EXTRA="com.latihanandroid.dailyreminderassistant.ui.time_picker_fragment.DEFAULT_MINUTE";
    public static TimePickerDialogFragment newInstance(int defaultHour,int defaultMinute){
        TimePickerDialogFragment timePickerDialogFragment=new TimePickerDialogFragment();
        Bundle args=new Bundle();
        args.putInt(DEFAULT_HOUR_EXTRA,defaultHour);
        args.putInt(DEFAULT_MINUTE_EXTRA,defaultMinute);
        timePickerDialogFragment.setArguments(args);
        return timePickerDialogFragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DialogTimeListener){
            mDialogTimeListener= (DialogTimeListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDialogTimeListener!=null){
            mDialogTimeListener=null;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        int defaultHour= getArguments().getInt(DEFAULT_HOUR_EXTRA,calendar.get(Calendar.HOUR_OF_DAY));
        int defaultMinute=getArguments().getInt(DEFAULT_MINUTE_EXTRA,calendar.get(Calendar.MINUTE));
        calendar.set(Calendar.HOUR_OF_DAY,defaultHour);
        calendar.set(Calendar.MINUTE,defaultMinute);
        return new TimePickerDialog(getContext(),this,calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hoursOfDay, int minutes) {
        mDialogTimeListener.onDialogTimeSet(hoursOfDay,minutes);
    }

    public interface DialogTimeListener{
        public void onDialogTimeSet(int hours, int minutes);
    }
}
