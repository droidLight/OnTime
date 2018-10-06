package com.kalam.sibi.ontime.Activities;
/**
 * Created by Sibi on 23-02-2018.
 */



import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v4.app.FragmentManager;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.SeekBar;
        import android.widget.Spinner;
        import android.widget.Switch;
        import android.widget.Toast;


        import com.kalam.sibi.ontime.DateAndTimeDialog.DateDialog;
        import com.kalam.sibi.ontime.DateAndTimeDialog.TimeDialog;
        import com.kalam.sibi.ontime.R;
        import com.kalam.sibi.ontime.Storage.AppDatabase;
        import com.kalam.sibi.ontime.Storage.WorkerThread.AsyncAdapter;
        import com.kalam.sibi.ontime.Storage.WorkerThread.UpdateAdapter;

        import java.util.Calendar;

public class EnterProperty extends AppCompatActivity implements AdapterView.OnItemSelectedListener,DateDialog.dateData,TimeDialog.Timedata{

    EditText interval;
    Button timeBtn, dateBtn;
    CheckBox dateCheck, checkHour, checkDay, checkCustom;
    FloatingActionButton okBtn;
    Spinner eventSpin;
    Switch repeatSwitch;
    DateDialog dateDialog;
    TimeDialog timeDialog;
    SeekBar volAndBright;
    Toolbar tool;
    SharedPreferences check;


    AlertDialog.Builder builder;
    EditText txt;

    Intent i;
    FragmentManager manager;
    Calendar cal;
    String event_name, notificationString;
    int seekValue;
    public static int hour, min;
    private long interTime;



    //Implemented function
    @Override
    public void getTime(int hour, int min) {
        cal = Calendar.getInstance();
        this.hour = hour;
        this.min = min;
        timeBtn.setText(hour + " : " + min);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
    }

    @Override
    public void getDate(int year, int month, int dayOfMonth) {

        try {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        } catch (Exception e) {
            Toast.makeText(EnterProperty.this, "Set the time first", Toast.LENGTH_SHORT);
        }
        dateBtn.setText(dayOfMonth + "-" + month + 1 + "-" + year);
        dateDialog.dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_property);

        tool = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //EditText
        interval = (EditText) findViewById(R.id.intreval);

        //CheckBoxes
        dateCheck = (CheckBox) findViewById(R.id.checkBox);
        checkHour = (CheckBox) findViewById(R.id.check_hour);
        checkHour.setVisibility(View.GONE);
        checkDay = (CheckBox) findViewById(R.id.check_day);
        checkDay.setVisibility(View.GONE);
        checkCustom = (CheckBox) findViewById(R.id.check_custom);
        checkCustom.setVisibility(View.GONE);


        check = getSharedPreferences("ROOTANDNOTIFI", MODE_PRIVATE);


        //Button
        timeBtn = (Button) findViewById(R.id.btn_time);
        dateBtn = (Button) findViewById(R.id.btn_date);
        dateBtn.setVisibility(View.GONE);

        okBtn = (FloatingActionButton) findViewById(R.id.ok_btn);


        //switch to repeat
        repeatSwitch = (Switch) findViewById(R.id.repeat);

        //seekbar for volume and brightness
        volAndBright = (SeekBar) findViewById(R.id.vol);
        volAndBright.setMax(255);

        //Check date checkbox
        dateCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dateBtn.setVisibility(View.VISIBLE);
                    dateCheck.setChecked(true);
                } else {
                    dateBtn.setVisibility(View.GONE);
                    dateCheck.setChecked(false);
                }
            }
        });


        //SPINNER
        eventSpin = (Spinner) findViewById(R.id.event_spinner);
        if (check.getInt("ROOT", 0) == 0) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Event_List_NOROOT, R.layout.spineritem);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            eventSpin.setAdapter(adapter);
            eventSpin.setOnItemSelectedListener(this);
        } else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Event_List_ROOT, R.layout.spineritem);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            eventSpin.setAdapter(adapter);
            eventSpin.setOnItemSelectedListener(this);
        }


        i = new Intent();

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DateDialog();
                manager = getSupportFragmentManager();
                dateDialog.show(manager, "date");

            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog = new TimeDialog();
                manager = getSupportFragmentManager();
                timeDialog.show(manager, "time");
            }
        });
        interval.setVisibility(View.INVISIBLE);
        //interTime=0;
        repeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //interval.setVisibility(View.VISIBLE);
                    checkHour.setVisibility(View.VISIBLE);
                    checkDay.setVisibility(View.VISIBLE);
                    checkCustom.setVisibility(View.VISIBLE);
                    checkCustom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                interval.setVisibility(View.VISIBLE);
                                checkCustom.setChecked(true);
                            } else {
                                interval.setVisibility(View.INVISIBLE);
                                checkCustom.setChecked(false);
                            }
                        }
                    });
                    repeatSwitch.setChecked(true);
                }
                if (!isChecked) {
                    interval.setVisibility(View.INVISIBLE);
                    checkHour.setVisibility(View.INVISIBLE);
                    checkDay.setVisibility(View.INVISIBLE);
                    checkCustom.setVisibility(View.INVISIBLE);
                    repeatSwitch.setChecked(false);
                    interTime = 0;
                }
            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (repeatSwitch.isChecked() && checkHour.isChecked()) {

                    interTime = 3600000;
                } else if (repeatSwitch.isChecked() && checkDay.isChecked()) {
                    interTime = 86400000;
                } else if (repeatSwitch.isChecked() && checkCustom.isChecked()) {
                    interTime = Long.parseLong(interval.getText().toString());
                } else {
                    interTime = 0;
                }

                if (getIntent().getIntExtra("ID", -1) == -1) {
                    try {
                        new AsyncAdapter(AppDatabase.getDatabse(EnterProperty.this), event_name, cal.getTimeInMillis(), notificationString, hour, min, interTime, seekValue, false, false).execute();
                        finish();
                    } catch (Exception e) {
                        finish();
                    }
                } else {
                    try {
                        new UpdateAdapter(getIntent().getIntExtra("ID", 0), AppDatabase.getDatabse(EnterProperty.this), event_name, cal.getTimeInMillis(), notificationString, hour, min, interTime, seekValue, false, false).execute();
                        finish();
                    } catch (Exception e) {
                        finish();
                    }
                }

            }
        });

        volAndBright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        builder = new AlertDialog.Builder(this);
        txt = new EditText(this);
        builder.setTitle("Enter Text").setView(txt);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notificationString = txt.getText().toString();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d("DIS", "MISS");
            }
        });
    }


    @Override
    public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {



            event_name = parent.getItemAtPosition(position).toString();
            if (parent.getItemAtPosition(position).toString().equals("Ringer Volume") || parent.getItemAtPosition(position).toString().equals("Brightness") || parent.getItemAtPosition(position).toString().equals("Notification Volume")) {
                volAndBright.setVisibility(View.VISIBLE);
            }

            if (parent.getItemAtPosition(position).toString().equals("Remainder")) {
                if (txt.getParent() != null) {
                    ((ViewGroup) txt.getParent()).removeView(txt);
                }
                builder.create().show();
                Log.d("NOTE", "NOTE");
            }

            if (!parent.getItemAtPosition(position).toString().equals("Ringer Volume")) {
                if (!parent.getItemAtPosition(position).toString().equals("Brightness")) {
                    if (!parent.getItemAtPosition(position).toString().equals("Notification Volume")) {
                        volAndBright.setVisibility(View.GONE);
                    }
                }
            }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(EnterProperty.this,"Event is not Selected",Toast.LENGTH_SHORT).show();
    }


}


