package com.logictree.supply.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.logictree.supply.R;
import com.logictree.supply.models.Customer;

public class CustomerAdapter extends ArrayAdapter<Customer> {
	
	private List<Customer> items;
	private LayoutInflater inflater;

	public CustomerAdapter(Context context, int textViewResourceId, List<Customer> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	public void addCustomerList(List<Customer> orders){
		this.items.addAll(orders);
		notifyDataSetChanged();
	}
	
	@Override
	public int getPosition(Customer item) {
		return items.indexOf(item);
		
	}
	
	@Override
	public Customer getItem(int position) {
		return items.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_customer, null);
			holder = new ViewHolder();
			holder.business_name = (TextView)convertView.findViewById(R.id.list_item_customer_businessname);
			holder.city = (TextView)convertView.findViewById(R.id.list_item_customer_city);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int bgId = ((position & 1) == 0) ? R.drawable.bg_list_item_1
				: R.drawable.bg_list_item_2;
		convertView.setBackgroundResource(bgId);
		
		Customer customer = items.get(position);
		holder.business_name.setText(customer.getBusinessName());
		holder.city.setText(customer.getCity() +" , "+customer.getState());
		return convertView;
	}
	
	private static class ViewHolder {
		private TextView business_name;
		private TextView city;
	}
}
