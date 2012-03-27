package com.logictree.supply.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.logictree.supply.R;
import com.logictree.supply.fragments.CustomerDetailFragment;
import com.logictree.supply.fragments.CustomerListDialogFragment;
import com.logictree.supply.fragments.CustomerListFragment.CustomerSelectedListener;
import com.logictree.supply.models.Customer;

public class CustomerListActivity extends Activity implements
		CustomerSelectedListener {
	private static final String TAG = "CustomerListActivity";
	private static final int MENU_NEWORDER = 105;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.layout_customers);
		setProgressBarIndeterminateVisibility(false);
		setTitle("Customers");
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
	}
	
	public void showProgress(boolean visible){
		setProgressBarIndeterminateVisibility(visible);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_NEWORDER, 0, "New Order").setIcon(R.drawable.btn_neworder).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case MENU_NEWORDER:
			showDialog();
			break;
		case android.R.id.home:
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, HomeActivity.class);
			intent.putExtra("database", true);
			startActivity(intent);
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	void showDialog() {
		DialogFragment newFragment = CustomerListDialogFragment
				.newInstance(R.string.alert_dialog_title);
		newFragment.show(getFragmentManager(), "dialog");
	}

	public void onListItemSelected(Customer customer) {
		CustomerDetailFragment customerDetailFragment = (CustomerDetailFragment) getFragmentManager()
				.findFragmentById(R.id.customerDetailFragment);
		if (customerDetailFragment != null) {
			customerDetailFragment.update(customer);
		}
	}
}
