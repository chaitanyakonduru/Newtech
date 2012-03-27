package com.logictree.supply.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.logictree.supply.R;
import com.logictree.supply.fragments.DepartmentsFragment.DepartmentSelectedListener;
import com.logictree.supply.fragments.NewOrderFragment;
import com.logictree.supply.fragments.NewOrderListFragment;
import com.logictree.supply.models.Customer;

public class NewOrderActivity extends Activity implements DepartmentSelectedListener {

	private static final String TAG ="NewOrderActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_new_order);
		setTitle("New Order Screen");
	
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		NewOrderFragment fragment = (NewOrderFragment) getFragmentManager().findFragmentById(R.id.fragment3);
		Bundle bundle = getIntent().getExtras();

		if(bundle != null && bundle.containsKey("customer")) {
			Customer customer = bundle.getParcelable("customer");
			fragment.setCustomer(customer);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
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

	public void onDeptSelected(String deptname) {
		NewOrderListFragment productListFragment = (NewOrderListFragment) getFragmentManager().findFragmentById(R.id.neworder_product_list_fragment);
		if(productListFragment != null) {
			productListFragment.getProducts(deptname);
		}
	}
}
