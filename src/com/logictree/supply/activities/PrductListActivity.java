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
import com.logictree.supply.fragments.CustomerListDialogFragment;
import com.logictree.supply.fragments.DepartmentsFragment.DepartmentSelectedListener;
import com.logictree.supply.fragments.ProductDetailFragment;
import com.logictree.supply.fragments.ProductListFragment;
import com.logictree.supply.models.Product;

public class PrductListActivity extends Activity implements ProductListFragment.ProductSelectedListener, DepartmentSelectedListener {
	private static final String TAG = "PrductListActivity";
	private static final int MENU_NEWORDER = 103;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.layout_product);
		setProgressBarIndeterminateVisibility(false);
		setTitle("Products");
		getActionBar().setDisplayShowHomeEnabled(true);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_NEWORDER, 0, "New Order").setIcon(R.drawable.btn_neworder).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void showProgress(boolean visible){
		setProgressBarIndeterminateVisibility(visible);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case MENU_NEWORDER:
			showDialog();
			break;
		//		case 102:
		//			Util.showAddProductPopup(this);
		//			break;
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
		return super.onOptionsItemSelected(item);
	}

	private void showDialog() {
		DialogFragment newFragment = CustomerListDialogFragment.newInstance(R.string.alert_dialog_title);
		newFragment.show(getFragmentManager(), "dialog");
	}

	public void onListItemSelected(Product product, String quantity) {
		ProductDetailFragment productDetailFragment = (ProductDetailFragment) 
		getFragmentManager().findFragmentById(R.id.productDetailFragment);
		if (productDetailFragment != null) {
			productDetailFragment.update(product);
		}
	}

	public void onDeptSelected(String deptname) {
		ProductListFragment productListFragment = (ProductListFragment) getFragmentManager().findFragmentById(R.id.product_list_fragment);
		productListFragment.getProducts(deptname);
	}
}
