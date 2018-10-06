package com.kalam.sibi.ontime.RecyclerView;
/**
 * Created by Sibi on 23-02-2018.
 */

import android.app.NotificationManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.kalam.sibi.ontime.Activities.AboutActivity;
import com.kalam.sibi.ontime.Permission.PermissionClass;
import com.kalam.sibi.ontime.Activities.SettingsOption;
import com.kalam.sibi.ontime.Storage.ActionProp;
import com.kalam.sibi.ontime.Activities.EnterProperty;
import com.kalam.sibi.ontime.R;
import com.kalam.sibi.ontime.Storage.AppDatabase;
import com.kalam.sibi.ontime.Storage.CustomData.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class ActionActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    LinearLayoutManager linear;
    Intent intent,set_intent;
    MyViewModel viewModel;
    NotificationManager ntfm;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Toolbar tool;
    ArrayList<ActionProp> actions = new ArrayList<>();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        tool = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.System.canWrite(ActionActivity.this)){
                //permitSysSettingDialog();
                PermissionClass.permitSysSettingDialog(ActionActivity.this);
            }
        }


        ntfm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && !ntfm.isNotificationPolicyAccessGranted()){
            PermissionClass.permitNotification(ActionActivity.this);
        }

        //NAVIGATION DRAWER
        drawerLayout = (DrawerLayout) findViewById(R.id.navLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_sidepane);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               if(item.getItemId()==R.id.settings){
                    startActivity(new Intent(ActionActivity.this, SettingsOption.class));

                }
                else if(item.getItemId()==R.id.addBtn){
                   startActivity(new Intent(ActionActivity.this,EnterProperty.class));
               }
               else {
                   startActivity(new Intent(ActionActivity.this,AboutActivity.class));
               }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        //RECYCLER VIEW
        linear = new LinearLayoutManager(ActionActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter(actions, AppDatabase.getDatabse(ActionActivity.this),ActionActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linear);
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(ActionActivity.this, R.anim.layout_animation_fall);
        recyclerView.setLayoutAnimation(controller);


        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        final Observer<List<ActionProp>>dataObserver = new Observer<List<ActionProp>>() {
            @Override
            public void onChanged(@Nullable List<ActionProp> actionProp) {
                adapter.addItems(actionProp);
            }
        };
        viewModel.getListOfData().observe(this,dataObserver);
    }

}
