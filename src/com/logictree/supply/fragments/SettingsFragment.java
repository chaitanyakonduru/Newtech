package com.logictree.supply.fragments;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewTecApp;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

	protected static final String TAG = "SettingsFragment";
	private static Preference preference;
	private NewTecApp app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		app = (NewTecApp) getActivity().getApplicationContext(); 
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
		settings.registerOnSharedPreferenceChangeListener(this);
		
		Preference.OnPreferenceClickListener clickListener = new Preference.OnPreferenceClickListener() {

			public boolean onPreferenceClick(Preference preference) {
				app.startDownload();
				preference.setTitle("Downloading...");
				preference.setEnabled(false);
				return true;
			}
		};
		preference =  (Preference)findPreference("download_update");
		preference.setOnPreferenceClickListener(clickListener);
	}

	public void onSharedPreferenceChanged(SharedPreferences arg0, String key) {
		if(key.equals("syc_with_server")){
			boolean isSynwithServer = arg0.getBoolean("syc_with_server", false);
			Log.v(TAG, isSynwithServer+"");
			if(isSynwithServer) {
				Log.v(TAG,"isSynwithServer   true");
				app.onServiceConnected();
			} else {
				Log.v(TAG,"isSynwithServer   false");
				app.onServiceDisconnected();
			}
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, "onResume");
	}
	
	public static void changePreferences() {
		Log.v(TAG,"sync completed");
		preference.setTitle("Download and Update");
		preference.setEnabled(true);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.v(TAG, "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.v(TAG, "onStop");
	}
}
