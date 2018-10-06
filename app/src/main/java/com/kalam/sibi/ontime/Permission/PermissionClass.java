package com.kalam.sibi.ontime.Permission;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Sibi on 24-02-2018.
 */

public abstract class PermissionClass {

    static AlertDialog.Builder sysBuilder,notiyBuilder;
    static Intent checkIntent,i;



    public static void permitSysSettingDialog(final Context ctx){


        sysBuilder = new AlertDialog.Builder(ctx);
        sysBuilder.setMessage("Modify System Settings " +
                "is required for complete functioning of App");
        sysBuilder.setPositiveButton("GRANT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionCheck(ctx);
            }
        });
        sysBuilder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ctx,"Permission denied",Toast.LENGTH_SHORT).show();
            }
        });
        sysBuilder.create().show();
    }

     static void permissionCheck(Context ctx){

        checkIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        checkIntent.setData(Uri.parse("package:"+ctx.getPackageName()));
        checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(checkIntent);
    }

    public static void permitNotification(final Context ctx){
        notiyBuilder = new AlertDialog.Builder(ctx);
        notiyBuilder.setMessage("Permission needed for Do Not DisTurb Mode");
        notiyBuilder.setPositiveButton("GRANT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                ctx.startActivity(i);
            }
        });
        notiyBuilder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ctx,"Permission denied",Toast.LENGTH_SHORT).show();
            }
        });
        notiyBuilder.create().show();
    }
}
