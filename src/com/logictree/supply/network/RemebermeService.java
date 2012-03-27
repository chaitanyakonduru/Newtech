package com.logictree.supply.network;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class RemebermeService extends Service {

	private static final String TAG = "RemebermeService";
	private AlarmManager alarmManager;
	private PendingIntent sender;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.v(TAG, "onstart");
		Calendar calendar=Calendar.getInstance();
//		calendar.add(Calendar.MINUTE, 55);
		calendar.add(Calendar.DATE, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss aaa");
		String date = sdf.format(calendar.getTimeInMillis());
		Log.v(TAG, date);
		Intent remebermeintent = new Intent(RemebermeService.this, RemembermeBroadcast.class);
		sender = PendingIntent.getBroadcast(RemebermeService.this, 0, remebermeintent, Intent.FLAG_ACTIVITY_CLEAR_TOP);
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		sender.cancel();
		alarmManager.cancel(sender);
		Log.v(TAG, "ondestroy");
	}
	
}
