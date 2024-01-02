package com.penofdreams.unspokenwords.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.penofdreams.unspokenwords.activities.MainActivity;


// This class is used to receive the broadcast from the wear device
public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("OPEN_MESSAGES".equals(intent.getAction())) {
            Intent newIntent = new Intent(context, MainActivity.class);
            newIntent.setAction("OPEN_MESSAGES");
            context.startActivity(newIntent);
        }
    }
}
