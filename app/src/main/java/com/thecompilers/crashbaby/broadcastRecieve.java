package com.thecompilers.crashbaby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class broadcastRecieve extends BroadcastReceiver {
    MainActivity workingGame;
    public  boolean key=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extra=intent.getExtras();
        if(extra !=null){
            String state=extra.getString(TelephonyManager.EXTRA_STATE);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String phoneNumbers=extra.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Toast.makeText(context,"You Recieve a Call",Toast.LENGTH_SHORT).show();



            }
        }
    }
}
