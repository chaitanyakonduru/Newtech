package com.logictree.supply.activities;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.logictree.newtec.databases.NewTecDatabase;
import com.logictree.supply.models.LoginInfo;
import com.logictree.supply.network.DatabaseThread;
import com.logictree.supply.network.NewTecSyncService;
import com.logictree.supply.network.SyncwithService;

public class NewTecApp extends Application {

	private static final String TAG = "NewTecApp";
	public LoginInfo loginInfo;
	public String user_id;
	private SharedPreferences preferences;
	private boolean rememberMe = false;
	private DatabaseThread databaseThread;
	private NewTecDatabase newTecDatabase;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(TAG, "oncreate");
		preferences = this.getSharedPreferences("newtec", MODE_PRIVATE);
		newTecDatabase = NewTecDatabase.getDbInstance(this);
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public NewTecDatabase shareDatabaseInstance() {
		return newTecDatabase;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("user_id", loginInfo.getUserId());
		editor.commit();
	}

	public String getUserId() {
		return preferences.getString("user_id", null);
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void onServiceConnected() {
		startService(new Intent(NewTecApp.this, SyncwithService.class));
	}

	public void onServiceDisconnected() {
		stopService(new Intent(NewTecApp.this, SyncwithService.class));
	}

	public void startDownload() {
		startService(new Intent(NewTecApp.this, NewTecSyncService.class));
	}

	public void stopDownload() {
		stopService(new Intent(NewTecApp.this, NewTecSyncService.class));
	}

	public DatabaseThread shareDatabaseThread() {
		if (databaseThread == null) {
			databaseThread = new DatabaseThread(this);
			if (databaseThread != null && !databaseThread.isAlive()) {
				databaseThread.start();
			}
		}
		return databaseThread;
	}

	public void stopDatabaseThread() {
		if (databaseThread != null && databaseThread.isAlive()) {
			databaseThread.interrupt();
			databaseThread = null;
		}
	}

	public void pauseDatabaseThread() {
		if (databaseThread != null && databaseThread.isAlive()) {
			databaseThread.pause();
			databaseThread = null;
		}
	}

	public void resumeDatabaseThread() {
		if (databaseThread != null && databaseThread.isAlive()) {
			databaseThread.resume();
			databaseThread = null;
		}
	}

}