package com.kalam.sibi.ontime.Activities;
/**
 * Created by Sibi on 23-02-2018.
 */


import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;


import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kalam.sibi.ontime.ChangeFont;
import com.kalam.sibi.ontime.R;
import com.kalam.sibi.ontime.RecyclerView.ActionActivity;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Intent I;
    RelativeLayout relativeLayout;
    SharedPreferences shared;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout);

        shared = getSharedPreferences("ROOTANDNOTIFI",MODE_PRIVATE);
        editor = shared.edit();

        //CUSTOM FONT
        ChangeFont.changeFont(this,"SERIF","font/Dosis-SemiBold.ttf");





        I = new Intent(this, ActionActivity.class);
        startActivity(I);
    }




}
