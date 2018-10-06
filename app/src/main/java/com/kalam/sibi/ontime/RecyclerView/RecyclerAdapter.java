package com.kalam.sibi.ontime.RecyclerView;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kalam.sibi.ontime.AlarmAndEvents.AlarmClass;
import com.kalam.sibi.ontime.Storage.ActionProp;
import com.kalam.sibi.ontime.R;
import com.kalam.sibi.ontime.Storage.AppDatabase;
import com.kalam.sibi.ontime.Storage.WorkerThread.DeleteAdapter;

import java.util.List;

/**
 * Created by Sibi on 23-02-2018.
 */



public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {


    static List<ActionProp> actions;
    View v;
    AppDatabase database;
    Context ctx;


    public RecyclerAdapter(List<ActionProp> action, AppDatabase database,Context ctx) {
        this.actions = action;
        this.database = database;
        this.ctx=ctx;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        v = inflater.inflate(R.layout.currentlist, parent, false);

        MyViewHolder holder = new MyViewHolder(v,ctx);
        holder.textView = (TextView) v.findViewById(R.id.text_view);
        holder.timeText = (TextView) v.findViewById(R.id.text_time);
        holder.alarmToggle = (Switch) v.findViewById(R.id.alarmSwitch);
        holder.delete = (ImageButton) v.findViewById(R.id.delete);
        holder.actionIcon = (ImageView) v.findViewById(R.id.imageView);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ActionProp temp = actions.get(position);
        holder.textView.setText(temp.get_Event());
        holder.timeText.setText("At" + temp.get_Hour() + ":" + temp.get_Min());
        holder.alarmToggle.setChecked(temp.get_State());
        holder.actionIcon.setImageResource(setImage(temp.get_Event()));
        //alarm on and off
        holder.alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new AlarmClass(v.getContext(), actions.get(position).get_Time(), actions.get(position).get_Id(), actions.get(position).get_Event(), actions.get(position).get_interval());
                    new findById(database, actions.get(position), true).execute();
                    Log.d("RE", "ALARM SET");
                } else {
                    new AlarmClass(v.getContext(), actions.get(position).get_Id());
                    new findById(database, actions.get(position), false).execute();
                    Log.d("RE", "ALARM CANCEL");
                }
            }
        });
        //item delete
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlarmClass(v.getContext(), actions.get(position).get_Id());
                new DeleteAdapter(actions.get(position).get_Id(), database).execute();
                Log.d("TAG", "DELETED");
            }
        });

    }

    @Override
    public int getItemCount() {
        if (actions != null) {
            return actions.size();
        }
        return 0;

    }

    //ADD FUNCTION
    public void addItems(List<ActionProp> act) {
        this.actions = act;


        notifyDataSetChanged();
    }

    private int setImage(String event){

        switch (event){

            case "Silent":
                return R.drawable.vibrate;

            case "Wifi On":
                return R.drawable.wifion;

            case "Wifi Off":
                return R.drawable.wifioff;

            case "Do Not Disturb":
                return R.drawable.donotdisturb;

            case "Normal":
                return R.drawable.normal;

            case "MobileData On":
                return R.drawable.dataon;

            case "MobileData Off":
                return R.drawable.dataoff;

            case "Ringer Volume":
                return R.drawable.ringervolume;

            case "Notification Volume":
                return R.drawable.notityvolume;

            case "Brightness":
                return R.drawable.brightness;

            case "Remainder":
                return R.drawable.notification;

            case "Airplane mode On":
                return R.drawable.airplaneactive;

            case "Airplane mode Off":
                return R.drawable.airplaneoff;

        }
        return 0;
    }


    //Async Thread for finding actions by id
    private class findById extends AsyncTask<Void, Void, Void> {

        AppDatabase data;
        int id;
        boolean state;
        ActionProp act;

        public findById(AppDatabase data, ActionProp act,boolean state) {
            this.data = data;
            this.act=act;
            this.state=state;

        }

        @Override
        protected Void doInBackground(Void... params) {
            act.set_State(state);
            data.dao().updateData(act);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}





