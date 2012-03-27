package com.logictree.supply.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.logictree.supply.R;

public class SettingsActivity extends Activity {
	
	private static final String TAG = "SettingsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_settings);
		setTitle("Settings");
		Log.v(TAG,"oncreate");
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG,"onstart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG,"onresume");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, HomeActivity.class);
			intent.putExtra("database", true);
			startActivity(intent);
			break;

		default:
			break;
		}
		return true;
	}
}