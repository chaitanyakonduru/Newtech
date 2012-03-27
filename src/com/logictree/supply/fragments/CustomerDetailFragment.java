package com.logictree.supply.fragments;

import com.logictree.supply.R;
import com.logictree.supply.models.Customer;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerDetailFragment extends Fragment {

	private static final String TAG = "CustomerDetailFragment";
	private TextView custIdView;
	private TextView custNameView;
	private TextView businessnameView;
	private TextView licView;
	private TextView addressView;
	private TextView cityView;
	private TextView stateView;
	private TextView phnoView;
	private TextView statusView;
	private LinearLayout customerdetailsLayout;
	private TextView emptytextView;

	public static CustomerDetailFragment newInstance(){
		CustomerDetailFragment f = new CustomerDetailFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.custromer_details, null );
		customerdetailsLayout = (LinearLayout)view.findViewById(R.id.customer_details_layout);
		custIdView = (TextView) view.findViewById(R.id.customer_detail_custId);
		custNameView = (TextView) view.findViewById(R.id.customer_detail_custName);
		businessnameView = (TextView) view.findViewById(R.id.customer_detail_businessName);
		licView = (TextView) view.findViewById(R.id.customer_detail_lic);
		addressView = (TextView) view.findViewById(R.id.customer_detail_address);
		cityView = (TextView) view.findViewById(R.id.customer_detail_city);
		stateView = (TextView) view.findViewById(R.id.customer_detail_state);
		phnoView = (TextView) view.findViewById(R.id.customer_detail_phno);
		statusView = (TextView) view.findViewById(R.id.customer_detail_status);
		emptytextView = (TextView)view.findViewById(R.id.customers_empty);
		return view;
	}

	public void update(Customer customer) {
		if(customer != null) {
			Log.v(TAG,"custid  "+customer.getCustomerId());
			emptytextView.setVisibility(View.GONE);
			customerdetailsLayout.setVisibility(View.VISIBLE);
			
			if(customer.isSaved()){
				custIdView.setText("0");
			} else {
				custIdView.setText(customer.getCustomerId().toString());
			}
			
			custNameView.setText(customer.getCustomername().toString());
			businessnameView.setText(customer.getBusinessName().toString());
			licView.setText(customer.getLicence().toString());
			addressView.setText(customer.getAddress().toString());
			cityView.setText(customer.getCity().toString());
			stateView.setText(customer.getState().toString());
			phnoView.setText(customer.getPhoneNo().toString());
			statusView.setText(customer.getStatus().toString());
		} else {
			Log.v(TAG,"null");
			emptytextView.setVisibility(View.VISIBLE);
			customerdetailsLayout.setVisibility(View.GONE);
		}
	}
}
