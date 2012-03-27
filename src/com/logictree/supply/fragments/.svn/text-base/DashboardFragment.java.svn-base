package com.logictree.supply.fragments;

import com.logictree.supply.R;
import com.logictree.supply.activities.CustomerListActivity;
import com.logictree.supply.activities.MyAccountActivity;
import com.logictree.supply.activities.OrderListActivity;
import com.logictree.supply.activities.PrductListActivity;
import com.logictree.supply.activities.SettingsActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {

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
		View root = inflater.inflate(R.layout.fragment_dashboard,container);
		root.findViewById(R.id.home_btn_customers).setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				startActivity(new Intent(getActivity(),CustomerListActivity.class));
			}
		});
		root.findViewById(R.id.home_btn_products).setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				startActivity(new Intent(getActivity(),PrductListActivity.class));
			}
		});
		root.findViewById(R.id.home_btn_orders).setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				startActivity(new Intent(getActivity(),OrderListActivity.class));
			}
		});
		root.findViewById(R.id.home_btn_myaccount).setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				startActivity(new Intent(getActivity(),MyAccountActivity.class));
			}
		});
		root.findViewById(R.id.home_btn_settings).setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				startActivity(new Intent(getActivity(),SettingsActivity.class));
			}
		});

		return root;
	}

}
