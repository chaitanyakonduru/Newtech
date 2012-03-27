package com.logictree.supply.adapter;


import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.logictree.supply.R;
import com.logictree.supply.models.Product;

public class ProductAdapter extends ArrayAdapter<Product> {
	
	private List<Product> products;
	private LayoutInflater inflater;

	public ProductAdapter(Context context, int textViewResourceId, List<Product> products) {
		super(context, textViewResourceId, products);
		this.products = products;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return products.size();
	}
	
	public void addProductList(List<Product> products){
		this.products.addAll(products);
		notifyDataSetChanged();
	}
	
	public void removeAll(List<Product> products) {
		this.products.removeAll(products);
	}
	
	@Override
	public int getPosition(Product item) {
		return products.indexOf(item);
	}
	
	@Override
	public Product getItem(int position) {
		return products.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_product, null);
			holder = new ViewHolder();
			holder.productname = (TextView)convertView.findViewById(android.R.id.text1);
//			holder.unit = (TextView)convertView.findViewById(android.R.id.text2);
//			holder.status = (TextView)convertView.findViewById(R.id.product_list_item_status);
//			holder.deptname= (TextView)convertView.findViewById(R.id.product_list_item_dept);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int bgId = ((position & 1) == 0) ? R.drawable.bg_list_item_1
				: R.drawable.bg_list_item_2;
		convertView.setBackgroundResource(bgId);
		Product product = products.get(position);
		/*if(product.getStatus().equalsIgnoreCase("Active")) {
			holder.status.setTextColor(Color.parseColor("#689554"));
		} else if(product.getStatus().equalsIgnoreCase("InActive")) {
			holder.status.setTextColor(Color.parseColor("#e17873"));
		}*/
		holder.productname.setText(product.getProductName());
//		holder.unit.setText(product.getUnit());
//		holder.status.setText(product.getStatus());
//		holder.deptname.setText(product.getDepartmentName());
		return convertView;
	}
	
	private static class ViewHolder {
		private TextView productname;
//		private TextView unit;
//		private TextView status;
//		private TextView deptname;
	}
}
