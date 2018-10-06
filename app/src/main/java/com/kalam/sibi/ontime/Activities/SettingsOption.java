package com.kalam.sibi.ontime.Activities;
/**
 * Created by Sibi on 24-02-2018.
 */


import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.kalam.sibi.ontime.Permission.PermissionClass;
import com.kalam.sibi.ontime.R;
import com.kalam.sibi.ontime.Storage.AppDatabase;
import com.kalam.sibi.ontime.Storage.WorkerThread.UpdateAdapter;


public class SettingsOption extends AppCompatActivity {

    Toolbar tool;
    CheckBox notify,check_root;
    Button disable,enable,checkPermit;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    ConstraintLayout test;
    NotificationManager ntfm;
    boolean isRoot=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tool = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ntfm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notify = (CheckBox) findViewById(R.id.notify);
        check_root = (CheckBox) findViewById(R.id.rootcheck);
        disable = (Button) findViewById(R.id.disable);
        enable = (Button) findViewById(R.id.enable);
        test = (ConstraintLayout) findViewById(R.id.co_layout);
        checkPermit = (Button) findViewById(R.id.checkPermission);

        shared = getSharedPreferences("ROOTANDNOTIFI",MODE_PRIVATE);
        editor = shared.edit();

        if(shared.getInt("ROOT",0)==1){
            check_root.setChecked(true);
        }
        if(shared.getInt("ROOT",0)==0){
            check_root.setChecked(false);
        }
         if(shared.getInt("NOTIFI",0)==1){
            notify.setChecked(true);
            Log.d("N","JI");
        }
         if(shared.getInt("NOTIFI",0)==0){
            notify.setChecked(false);
        }




        notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putInt("NOTIFI",1);
                    editor.commit();
                    Log.d("CH","ON");
                }
                else{
                    editor.putInt("NOTIFI",0);
                    editor.commit();
                    Log.d("CH","OFF");
                }
            }
        });


        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateAdapter(SettingsOption.this,1,AppDatabase.getDatabse(SettingsOption.this)).execute();
            }
        });

        enable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new UpdateAdapter(SettingsOption.this,2,AppDatabase.getDatabse(SettingsOption.this)).execute();
            }
        });

        check_root.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    try{
                        Runtime.getRuntime().exec("su");
                    }
                    catch (Exception e){
                        isRoot=false;
                    }
                    if(isRoot){editor.putInt("ROOT",1).commit();}
                    else {editor.putInt("ROOT",0).commit();}
                }

            }
        });

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            checkPermit.setVisibility(View.GONE);
        }

        checkPermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                if (!ntfm.isNotificationPolicyAccessGranted()) {
                    PermissionClass.permitNotification(SettingsOption.this);
                    PermissionClass.permitSysSettingDialog(SettingsOption.this);
                }
                else{
                    Toast.makeText(SettingsOption.this,"All permissions Granted",Toast.LENGTH_SHORT).show();
                }
            }
            }
        });


    }




}
