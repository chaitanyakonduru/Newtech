package com.logictree.supply.network;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.logictree.supply.activities.LoginScreenActivity;
import com.logictree.supply.activities.NewTecApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class RemembermeBroadcast extends BroadcastReceiver {

	private static final String TAG = "RemembermeBroadcast";

	@Override
	public void onReceive(Context context, Intent arg1) {
		final NewTecApp app = (NewTecApp) context.getApplicationContext();
		if(app.getUserId() != null) {
			Date d=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			Log.v(TAG, sdf.format(d));
			Log.v(TAG, "Message Received at "+sdf.format(d)+" ");
			Intent scheduledIntent = new Intent(context, LoginScreenActivity.class);
			scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(scheduledIntent);

			SharedPreferences sharedPreferences = context.getSharedPreferences("newtec", 0);
			SharedPreferences.Editor editor =  sharedPreferences.edit();
			editor.putString("user_id", null);
			editor.commit();
			app.setRememberMe(false);
		}
	}
}
