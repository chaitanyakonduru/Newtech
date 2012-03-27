package com.logictree.supply.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.logictree.supply.R;
import com.logictree.supply.models.Order;

public class OrderAdapter extends ArrayAdapter<Order> {

	private List<Order> items;
	private LayoutInflater inflater;

	// private ListView listView;

	public OrderAdapter(Context context, int textViewResourceId,
			List<Order> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		// this.listView = listView;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public void addOrderList(List<Order> orders) {
		this.items.addAll(orders);
		notifyDataSetChanged();
	}

	@Override
	public int getPosition(Order item) {
		return items.indexOf(item);
	}

	@Override
	public Order getItem(int position) {
		return items.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_order, null);
			holder = new ViewHolder();
			holder.custname = (TextView) convertView
					.findViewById(R.id.list_item_order_custname);
			holder.orderid = (TextView) convertView
					.findViewById(R.id.list_item_order_id);
			holder.status = (TextView) convertView
					.findViewById(R.id.list_item_order_status);
			holder.orderdate = (TextView) convertView
					.findViewById(R.id.list_item_order_date);
			holder.amount = (TextView) convertView
					.findViewById(R.id.list_item_order_amonut);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		int bgId = ((position & 1) == 0) ? R.drawable.bg_list_item_1
				: R.drawable.bg_list_item_2;

		convertView.setBackgroundResource(bgId);

		// if(position == 0){
		// convertView.setBackgroundResource(R.drawable.bg_list_item_1);
		// }else if( position == getCount()-1 && convertView.getBottom() ==
		// listView.getHeight()){
		// convertView.setBackgroundResource(R.drawable.bg_list_item_1);
		// }
		Order order = items.get(position);
		String status = null;
		if (order.getStatus().equalsIgnoreCase("Active")) {
			status = "Sent";
			holder.status.setTextColor(Color.parseColor("#689554"));
		} else if (order.getStatus().equalsIgnoreCase("InActive")) {
			status = "Pending";
			holder.status.setTextColor(Color.parseColor("#e17873"));
		}
		if (!TextUtils.isEmpty(order.getBusinessname().toString().trim())) {
			holder.custname.setText(order.getBusinessname());
		} else {
			holder.custname.setText("Business Name");
		}
		holder.orderid.setText(order.getOrderId());
		holder.status.setText(status);
		holder.orderdate.setText(order.getOrderDate());
		holder.amount.setText("$" + order.getAmount());

		if (order.isSaved() || order.isSend()) {
//			holder.status.setText("Saved");
//			holder.status.setTextColor(Color.parseColor("#5388ff"));
			holder.status.setText("Pending");
			holder.status.setTextColor(Color.parseColor("#e17873"));
			holder.orderid.setText("0");
		}

		// holder.text1.setText(order.getCustomername() + " - "
		// + order.getOrderId() + " ( " + status + " ) ");
		// holder.text2.setText(order.getOrderDate()+ " , "+"$"
		// + order.getAmount());

		return convertView;
	}

	private static class ViewHolder {
		private TextView custname;
		private TextView orderid;
		private TextView status;
		private TextView orderdate;
		private TextView amount;
	}
}
