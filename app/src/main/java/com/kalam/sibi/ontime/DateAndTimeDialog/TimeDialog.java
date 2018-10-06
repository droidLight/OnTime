package com.kalam.sibi.ontime.DateAndTimeDialog;

;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.kalam.sibi.ontime.R;

import java.util.Calendar;

/**
 * Created by Sibi on 24-02-2018.
 */

public class TimeDialog extends DialogFragment{

    TimePicker timePicker;
    Calendar cal;
    Timedata timelistn;
    Button ok_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v;
        v=inflater.inflate(R.layout.timepicker,null);

        timePicker = (TimePicker)v.findViewById(R.id.timePicker);
        ok_btn = (Button) v.findViewById(R.id.ok_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hourOfDay;
                int minute;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                   hourOfDay = timePicker.getHour();
                    minute = timePicker.getMinute();
                    timelistn.getTime(hourOfDay,minute);
                }
                else{
                    minute=timePicker.getCurrentMinute();
                    hourOfDay=timePicker.getCurrentHour();
                    timelistn.getTime(hourOfDay,minute);
                }
                dismiss();
            }
        });

        /*timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timelistn.getTime(hourOfDay,minute);
            }
        });*/

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        timelistn = (Timedata) context;
    }

    public interface Timedata{
        void getTime(int hour,int min);
    }
}
