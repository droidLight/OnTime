package com.kalam.sibi.ontime.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kalam.sibi.ontime.Activities.EnterProperty;

import static com.kalam.sibi.ontime.RecyclerView.RecyclerAdapter.actions;

/**
 * Created by Sibi on 23-02-2018.
 */


public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView textView,timeText;
    Switch alarmToggle;
    ImageButton delete;
    Context ctx;
    Intent editIntent;
    ImageView actionIcon;


    public MyViewHolder(View itemView, Context ctx) {
        super(itemView);
        this.ctx=ctx;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d("LISTNER", "CLICKED");
        editIntent = new Intent(ctx, EnterProperty.class);
        editIntent.putExtra("ID",actions.get(getAdapterPosition()).get_Id());
        ctx.startActivity(editIntent);
    }
}
