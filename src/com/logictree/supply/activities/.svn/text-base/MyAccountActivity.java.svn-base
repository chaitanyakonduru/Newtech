package com.logictree.supply.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.logictree.supply.R;
import com.logictree.supply.models.LoginInfo;
import com.logictree.supply.network.RemebermeService;

public class MyAccountActivity extends Activity {

	private static final String TAG = "MyAccountActivity";
	private static final int MENU_LOGOUT = 108;
	private NewTecApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myaccount);
		setTitle("My Account");
		getActionBar().setDisplayShowHomeEnabled(true);
		app = (NewTecApp)getApplication();
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		getAccount();
	}

	private void getAccount() {
		final NewTecApp app = (NewTecApp) getApplication();
//		LoginInfo loginInfo = app.getLoginInfo();
		Log.v(TAG,"userid  "+app.getUserId());
		LoginInfo loginInfo = app.shareDatabaseInstance().getLoginInfo(app.getUserId());
		((TextView)findViewById(R.id.myaccount_name)).setText(loginInfo.getName());
		((TextView)findViewById(R.id.email)).setText(loginInfo.getUsername());
		((TextView)findViewById(R.id.myaccount_address)).setText(loginInfo.getAddress());
		((TextView)findViewById(R.id.myaccount_city)).setText(loginInfo.getCity());
		((TextView)findViewById(R.id.myaccount_phone)).setText(loginInfo.getPhone());
		((TextView)findViewById(R.id.myaccount_state)).setText(loginInfo.getState());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_LOGOUT, 0, "Logout").setIcon(R.drawable.logout).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
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

		case MENU_LOGOUT:
			//			((NewTecApp) getApplication()).userId = "-1";
			Intent homeintent = new Intent(MyAccountActivity.this, LoginScreenActivity.class);
			homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeintent);
			finish();
			app.setRememberMe(false);
//			app.stopDatabaseThread();
//			app.onServiceDisconnected();
//			app.stopDownload();
			SharedPreferences sharedPreferences = getSharedPreferences("newtec", MODE_PRIVATE);
			SharedPreferences.Editor editor =  sharedPreferences.edit();
			editor.putString("user_id", null);
			editor.commit();
			stopService(new Intent(MyAccountActivity.this, RemebermeService.class));
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
