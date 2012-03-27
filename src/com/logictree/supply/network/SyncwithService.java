package com.logictree.supply.network;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.logictree.supply.activities.NewTecApp;

public class SyncwithService extends Service {

	private static final String TAG = "SyncwithServer";
	private AlarmManager alarmManager;
	private PendingIntent pendingIntent;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.v(TAG,"onstart");
		syncWithServer();
	}

	private void syncWithServer() {
		final NewTecApp app = (NewTecApp) getApplication();
		if(app.getUserId() != null) {
			Intent intent = new Intent(SyncwithService.this, SyncBroadcast.class);

			pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

			Log.v(TAG,"SynwithServer  "+app.getUserId());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss aaa");
			String date = sdf.format(calendar.getTimeInMillis());
			Log.v(TAG, date);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1*60*60*1000, pendingIntent);
		} else {
			Log.v(TAG, "empty");
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG,"onstartcommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(pendingIntent != null && alarmManager != null) {
			pendingIntent.cancel();
			alarmManager.cancel(pendingIntent);
			Log.v(TAG,"ondestroy");
		}
	}
}