package com.logictree.supply.activities;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

import com.logictree.supply.R;
import com.logictree.supply.models.LoginInfo;
import com.logictree.supply.models.Util;
import com.logictree.supply.network.ApiManager;
import com.logictree.supply.network.NewTecCallback;
import com.logictree.supply.network.RemebermeService;

public class LoginScreenActivity extends Activity implements OnClickListener {

	protected static final String TAG = "LoginScreenActivity";
	private EditText userName;
	private EditText passWord;
	private ProgressDialog progressDialog;
	private CheckBox checkBox;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		sharedPreferences = PreferenceManager
		.getDefaultSharedPreferences(this);
		progressDialog = new ProgressDialog(LoginScreenActivity.this);
		progressDialog.setMessage("Loging in.......");
		userName = (EditText) findViewById(R.id.username);
		passWord = (EditText) findViewById(R.id.password);
		checkBox = (CheckBox) findViewById(R.id.remember_me);
		findViewById(R.id.login).setOnClickListener(this);
//		findViewById(R.id.forgot_pw).setOnClickListener(this);
//		userName.setText("admin@gmail.com");
//		passWord.setText("password");
		final NewTecApp app = (NewTecApp) getApplication();
		if(app.isRememberMe()) {
			Intent intent = new Intent(LoginScreenActivity.this,
					HomeActivity.class);
			intent.putExtra("database", true);
			startActivity(intent);
		} else {
			Log.v(TAG, "remeber f  "+app.isRememberMe());
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.login:
			Log.v(TAG,"checkbox  "+checkBox.isChecked());
			final ApiManager apiManager = ApiManager.getInstance(LoginScreenActivity.this);
			final String username = userName.getText().toString().trim();
			final String password = passWord.getText().toString().trim();
			final NewTecApp app = (NewTecApp) getApplication();

			if (username != null && username.length() > 0 && password != null
					&& password.length() > 0) {
				progressDialog.show();
			}

			if (TextUtils.isEmpty(username)) {
				userName.setError("enter username");
				return;
			} else if (TextUtils.isEmpty(password)) {
				passWord.setError("enter password");
				return;
			}
			LoginInfo loginInfo = app.shareDatabaseInstance().isUserExists(username);
		
			if (loginInfo != null) {
				Log.v(TAG, "database");
				progressDialog.dismiss();
				if (loginInfo.getPassword().equals(password)) {
					app.setLoginInfo(loginInfo);
					boolean isSynwithServer = sharedPreferences.getBoolean(
							"syc_with_server", false);
					Log.v(TAG, "isSynwithServer  "+isSynwithServer +" userid  "+loginInfo.getUserId());

					if (isSynwithServer) {
						app.onServiceConnected();
					} else {
						app.onServiceDisconnected();
					}
					Intent intent = new Intent(LoginScreenActivity.this,
							HomeActivity.class);
					intent.putExtra("database", true);
					startActivity(intent);
					if(checkBox.isChecked()) {
						startRemembermeService();
						app.setRememberMe(true);
					} else {
						app.setRememberMe(false);
					}
				} else {
					passWord.setError("Invalid password.");
					passWord.setText("");
//					Util.showErrorDialog(this, "Invalid user name and password.", true);
				}
			} else {
				final NewTecCallback<Object> callback = new NewTecCallback<Object>() {
					public void onSuccess(Object object) {
						Log.v(TAG, "server");
						Log.v(TAG, " Login - " + object.toString());
						progressDialog.dismiss();
						if (object instanceof LoginInfo) {
							LoginInfo loginInfo = (LoginInfo) object;

							if (loginInfo != null && loginInfo.getError() != null) {
								Util.showErrorDialog(LoginScreenActivity.this, ""+loginInfo.getError(), true);
								passWord.setText("");
							} else if (loginInfo != null
									&& loginInfo.getUserId() != null) {

								app.shareDatabaseInstance().insertUserInfo(loginInfo);
								app.setLoginInfo(loginInfo);
								boolean isSynwithServer = sharedPreferences.getBoolean(
										"syc_with_server", false);
								Log.v(TAG, "isSynwithServer  "+isSynwithServer +" userid "+ loginInfo.getUserId());

								if (isSynwithServer) {
									app.onServiceConnected();
								} else {
									app.onServiceDisconnected();
								}
								startActivity(new Intent(LoginScreenActivity.this,HomeActivity.class));
								if(checkBox.isChecked()) {
									startRemembermeService();
									app.setRememberMe(true);
								} else {
									app.setRememberMe(false);
								}
							}

						}
					}

					public void onError(Exception e) {
						progressDialog.dismiss();
						if (e instanceof IOException) {
							Log.v(TAG, "Exception");
							Util.showErrorDialog(LoginScreenActivity.this,
									"Please check network connection. ", true);
						} else {
							
							Util.showErrorDialog(LoginScreenActivity.this,
									"Please check network connection. ", true);
							
						}
					}
				};

				apiManager.login(getApplicationContext(), username, password,
						callback);
			}
			break;
		/*case R.id.forgot_pw:
			String userame = userName.getText().toString().trim();
			if (TextUtils.isEmpty(userame)) {
				userName.setError("enter username");
				return;
			}  else {
				sendmail(userame);
			}
			break;*/
		default:
			break;
		}
	}

	private void startRemembermeService() {
		startService(new Intent(LoginScreenActivity.this, RemebermeService.class));
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		finish();
	}
	
	protected void onPause() {
		super.onPause();
		Log.v(TAG,"onPause");
	}
	
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG,"ondestroy");
		NewTecApp app = (NewTecApp) getApplication();
		if(!app.isRememberMe()) {
			Log.v(TAG,"not remember");
			app.stopDatabaseThread();
			app.onServiceDisconnected();
			app.stopDownload();
			SharedPreferences sharedPreferences = getSharedPreferences("newtec", MODE_PRIVATE);
			SharedPreferences.Editor editor =  sharedPreferences.edit();
			editor.putString("user_id", null);
			editor.commit();
		}
	}
}
