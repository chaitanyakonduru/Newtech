package com.logictree.supply.adapter;

import java.util.List;

import com.logictree.supply.R;
import com.logictree.supply.models.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewOrderProductAdapter extends ArrayAdapter<Product> {

	private List<Product> products;
	private LayoutInflater inflater;

	public NewOrderProductAdapter(Context context, int textViewResourceId, List<Product> products) {
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
			convertView = inflater.inflate(R.layout.layout_neworder_product_list, null);
			holder = new ViewHolder();
			holder.productname = (TextView)convertView.findViewById(R.id.neworder_productname);
//			holder.unit = (TextView)convertView.findViewById(R.id.neworder_product_unit);
//			holder.deptname= (TextView)convertView.findViewById(R.id.neworder_product_deptname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int bgId = ((position & 1) == 0) ? R.drawable.bg_list_item_1
				: R.drawable.bg_list_item_2;
		convertView.setBackgroundResource(bgId);
		Product product = products.get(position);
		holder.productname.setText(product.getProductName());
//		holder.unit.setText(product.getUnit());
//		holder.deptname.setText(product.getDepartmentName());
		return convertView;
	}
	
	private static class ViewHolder {
		private TextView productname;
//		private TextView unit;
//		private TextView deptname;
	}

}
