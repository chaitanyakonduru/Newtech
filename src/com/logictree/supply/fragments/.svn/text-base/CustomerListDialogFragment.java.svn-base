package com.logictree.supply.fragments;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewOrderActivity;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.adapter.CustomerAdapter;
import com.logictree.supply.models.Customer;

public class CustomerListDialogFragment extends DialogFragment {

	protected static final String TAG = "CustomerListDialogFragment";

	public static CustomerListDialogFragment newInstance(int title) {
		CustomerListDialogFragment frag = new CustomerListDialogFragment();
		Bundle args = new Bundle();
		args.putInt("title", title);
		frag.setArguments(args);
		return frag;
	}

	private List<Customer> customerList;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final NewTecApp newTecApp = (NewTecApp) getActivity().getApplication();
		
		if(newTecApp.getUserId() != null)
			customerList  = newTecApp.shareDatabaseInstance().getActiveCustomers(newTecApp.getUserId());
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int title = getArguments().getInt("title");
		return new AlertDialog.Builder(getActivity())
		.setIcon(R.drawable.customer)
		.setTitle(title)
		.setAdapter((new CustomerAdapter(getActivity(), R.layout.list_item_customer,customerList)), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				final Customer customer = customerList.get(which);
				Log.v(TAG,customer.getCustomerId()+"  "+getTag());
				 Intent intent = new Intent(getActivity(), NewOrderActivity.class);
				 intent.putExtra("customer", customer);
				 startActivity(intent);
			}
		})
		.create();
	}
}
