package com.latihanandroid.dailyreminderassistant.ui.date_picker_fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static final String DATE_PICKER_FOR_KEGIATAN_PENTING="com.latihanandroid.dailyreminderassistant.ui.date_picker_fragment.DATE_PICKER_DIALOG_KEGIATAN_PENTING";
    public static final String EXTRA_YEAR="com.latihanandroid.dailyreminderassistant.ui.date_picker_fragment.YEAR";
    public static final String EXTRA_MONTH="com.latihanandroid.dailyreminderassistant.ui.date_picker_fragment.MONTH";
    public static final String EXTRA_DAY_OF_MONTH="com.latihanandroid.dailyreminderassistant.ui.date_picker_fragment.DAY_OF_MONTH";
    public DialogDateListener mDialogDateListener;
    public Calendar calendar;
    public static DatePickerFragment newInstance(int defaultYear,int defaultMonth,int defaultDayOfMonth){
        DatePickerFragment datePickerFragment=new DatePickerFragment();
        Bundle args=new Bundle();
        args.putInt(EXTRA_YEAR,defaultYear);
        args.putInt(EXTRA_MONTH,defaultMonth);
        args.putInt(EXTRA_DAY_OF_MONTH,defaultDayOfMonth);
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DialogDateListener){
            mDialogDateListener= (DialogDateListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDialogDateListener!=null){
            mDialogDateListener=null;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        calendar=Calendar.getInstance();
        Bundle bundle=getArguments();
        if (bundle!=null){
            calendar.set(Calendar.YEAR,bundle.getInt(EXTRA_YEAR));
            calendar.set(Calendar.MONTH,bundle.getInt(EXTRA_MONTH));
            calendar.set(Calendar.DAY_OF_MONTH,bundle.getInt(EXTRA_DAY_OF_MONTH));
        }
        return new DatePickerDialog(getContext(),this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        mDialogDateListener.onDialogDateSet(year,month,dayOfMonth);
    }

    public interface DialogDateListener{
        public void onDialogDateSet(int year,int month,int dayOfMonth);
    }
}
