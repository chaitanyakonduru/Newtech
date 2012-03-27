package com.logictree.supply.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.logictree.supply.R;
import com.logictree.supply.network.ApiManager;
import com.logictree.supply.network.NewTecCallback;

public class HomeActivity extends Activity {

	protected static final String TAG = "HomeActivity";
	private ApiManager apiManager;
	private ProgressDialog progressDialog;
	private boolean fromDb;
	private NewTecApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);
		Log.v(TAG, "oncreate");
		progressDialog = new ProgressDialog(HomeActivity.this);
		progressDialog.setMessage("Loading...");
		app = (NewTecApp) getApplicationContext();
		apiManager = ApiManager.getInstance(HomeActivity.this);
		Bundle bundle = getIntent().getExtras();
		if(bundle != null) {
			fromDb = bundle.getBoolean("database");
		}

		if(fromDb) {
			progressDialog.dismiss();
		} else {
			Log.v(TAG,"not db");
			progressDialog.show();
			loadCustomers();
			loadOrders();
			loadProducts();
			loadDepartments();
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG,"onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG,"onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	private void loadOrders() {
		if(app.getUserId() != null) {
			apiManager.getOrderList(getApplicationContext(), app.getUserId(), 90,
					new NewTecCallback<Object>() {

				public void onSuccess(Object object) {
					progressDialog.dismiss();
				}

				public void onError(Exception exception) {
					progressDialog.dismiss();
					Toast.makeText(getApplicationContext(),
							"Unable to connect to server",
							Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			progressDialog.dismiss();
			Toast.makeText(getApplicationContext(),
					"Unable to connect to server",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	private void loadProducts() {
		apiManager.getProductList(getApplicationContext(), 80,
				new NewTecCallback<Object>() {

			public void onSuccess(Object object) {
			}

			public void onError(Exception exception) {
				progressDialog.dismiss();
			}
		});
	}

	private void loadCustomers() {
		apiManager.getCustomerList(this, 100 , new NewTecCallback<Object>() {

			public void onSuccess(Object object) {
			}

			public void onError(Exception exception) {
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext(),
						"Unable to connect to server",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void loadDepartments() {
		apiManager.getDepartmentList(this, 70 , new NewTecCallback<Object>() {

			public void onSuccess(Object object) {
			}

			public void onError(Exception exception) {
				progressDialog.dismiss();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG,"onDestroy");
		app = null;
	}
}