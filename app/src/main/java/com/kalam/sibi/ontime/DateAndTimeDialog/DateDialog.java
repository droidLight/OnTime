package com.kalam.sibi.ontime.DateAndTimeDialog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;


import com.kalam.sibi.ontime.R;

import java.util.Calendar;

/**
 * Created by Sibi on 24-02-2018.
 */

public class DateDialog extends DialogFragment{


    dateData listener;
    DatePicker datePicker;
    Calendar cal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.datepicker,container,false);

        datePicker = (DatePicker) v.findViewById(R.id.picker);
        cal = Calendar.getInstance();
        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                listener.getDate(year,monthOfYear,dayOfMonth);
            }
        });
        return v;
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(dateData) context;

    }

    public interface dateData{

        void getDate(int year,int month,int dayOfMonth);
    }

}
