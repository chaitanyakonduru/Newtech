package com.logictree.supply.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.logictree.supply.R;
import com.logictree.supply.fragments.CustomerListDialogFragment;
import com.logictree.supply.fragments.OrderDetailFragment;
import com.logictree.supply.fragments.OrderListFragment.OrderSelectedListener;
import com.logictree.supply.models.Order;
import com.logictreeit.supply.listeners.OrderEditListener;

public class OrderListActivity extends Activity implements OrderSelectedListener, OrderEditListener {

	private static final String TAG ="OrderListActivity";
	private static final int MENU_NEWORDER = 100;
	private static final int MENU_SYNC = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.layout_orders);
		setProgressBarIndeterminateVisibility(false);
		setTitle("Orders");
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
	}
	
	
	public void showProgress(boolean visible){
		setProgressBarIndeterminateVisibility(visible);
	}
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK ) {
			Intent intent = new Intent(OrderListActivity.this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_NEWORDER, 0, "New Order").setIcon(R.drawable.btn_neworder).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(0, MENU_SYNC, 0, "Sync with scanner").setIcon(R.drawable.btn_syncwithscanner).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case MENU_NEWORDER:
			showDialog();
			break;
		case MENU_SYNC:
			Intent serverIntent = new Intent(this, BluetoothScannerActivity.class);
			startActivity(serverIntent);
			break;
		case android.R.id.home:
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, HomeActivity.class);
			intent.putExtra("database", true);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	void showDialog() {
		DialogFragment newFragment = CustomerListDialogFragment.newInstance(R.string.alert_dialog_title);
		newFragment.show(getFragmentManager(), "order");
	}

	public void onListItemSelected(Order order) {
		OrderDetailFragment orderDetailFragment = (OrderDetailFragment) 
											getFragmentManager().findFragmentById(R.id.fragment2);
		if (orderDetailFragment != null) {
			if(order != null) {
				orderDetailFragment.update(order);
			}
		}
	}

	public void onOrderEdit(Order order, String quantity) {
		if(quantity != null && quantity.length() > 0) {
			OrderDetailFragment orderDetailFragment = (OrderDetailFragment) 
									getFragmentManager().findFragmentById(R.id.fragment2);
			orderDetailFragment.updateOrderDetails(order, quantity);
		} else {
			Toast.makeText(getApplicationContext(), "Please enter quantity", Toast.LENGTH_SHORT).show();
		}
	}
}
