package com.logictree.supply.fragments;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewOrderActivity;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.models.NewOrder;
import com.logictree.supply.models.Order;
import com.logictree.supply.models.Product;
import com.logictree.supply.network.ApiManager;
import com.logictree.supply.network.NewTecCallback;

public class OrderDetailFragment extends Fragment implements OnClickListener {

	private static final String TAG = "OrderDetailFragment";
	private TextView orderDateView;
	private TextView orderAmountView;
	private TextView custIdView;
	private TextView businessNameView;
	private TextView orderByView;
	private Order order;
	private TableLayout mainTableLayout;
	private TableLayout mainTable;
	private Button editBtn;
	private Button warehouseBtn;
	private TextView orderIdView;		
	private NewTecApp newTecApp;
	private ProgressDialog progressDialog;


	public static OrderDetailFragment newInstance(){
		OrderDetailFragment f = new OrderDetailFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		Log.v(TAG,"onCreate");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.v(TAG,"onActivityCreated");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG,"onResume");
		newTecApp = (NewTecApp) getActivity().getApplication();
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("Processesing..");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_order_details, null);
		Log.v(TAG,"onCreateView");
		orderIdView = (TextView)view.findViewById(R.id.order_details_orderid);
		orderDateView = (TextView)view.findViewById(R.id.order_details_orderdate);
		orderAmountView = (TextView)view.findViewById(R.id.order_details_amount);
		custIdView = (TextView)view.findViewById(R.id.order_details_custid);
		businessNameView = (TextView)view.findViewById(R.id.order_details_businessname);
		orderByView = (TextView)view.findViewById(R.id.order_details_orderby);
		mainTableLayout = (TableLayout)view.findViewById(R.id.orderitems);
		mainTable = (TableLayout)view.findViewById(R.id.order_details_mainTable);
		editBtn = (Button)view.findViewById(R.id.order_details_editbutton);
		warehouseBtn = (Button)view.findViewById(R.id.order_details_warehousebutton);
		editBtn.setOnClickListener(this);
		warehouseBtn.setOnClickListener(this);
		return view;
	}

	public void update(Order mOrder) {
		Log.v(TAG, mOrder.getOrderId());
		final NewTecApp app = (NewTecApp) getActivity().getApplication();
		final Order order = app.shareDatabaseInstance().getOrder(app.getUserId(), mOrder.getOrderId());
		if(order == null) {
			Log.v(TAG,"null");
			orderIdView.setText("");
			custIdView.setText("");
			orderDateView.setText("");
			businessNameView.setText("");
			orderByView.setText("");
			orderAmountView.setText("");
			mainTableLayout.removeAllViews();
			mainTable.setVisibility(View.GONE);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Please refersh the Orders once");
			builder.setPositiveButton("Ok", null);
			builder.create().show();
		} else {
			this.order = order;
			if(order.isSaved() || order.isSend()) {
				orderIdView.setText("0");
			} else {
				orderIdView.setText(order.getOrderId().toString());
			}
			orderDateView.setText(order.getOrderDate());
			custIdView.setText(order.getCustId().toString());
			businessNameView.setText(order.getBusinessname().toString());
			orderByView.setText(order.getOrderBY().toString());
			orderAmountView.setText(order.getAmount().toString());
			List<Product> products = order.getProducts();

			if(mainTableLayout != null ){
				mainTableLayout.removeAllViews();
				mainTable.setVisibility(View.VISIBLE);
			}
			DecimalFormat df = new DecimalFormat("###.##");
			float total = 0;
			//		totalView.setText(""+df.format(total));

			for(Product product:products) {
				View tablerowView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_table_row, null);
				TextView extnView = (TextView)tablerowView.findViewById(R.id.table_row_cost);
				TextView productidView = (TextView)tablerowView.findViewById(R.id.table_row_productid);
				TextView productnameView = (TextView)tablerowView.findViewById(R.id.table_row_productname);
				TextView priceView = (TextView)tablerowView.findViewById(R.id.table_row_price);
				TextView unitView = (TextView)tablerowView.findViewById(R.id.table_row_unit);
				TextView srpView = (TextView)tablerowView.findViewById(R.id.table_row_srp);
				TextView qtyView = (TextView)tablerowView.findViewById(R.id.table_row_qty);

				productidView.setText(product.getProductId().toString());
				productnameView.setText(product.getProductName().toString());
				qtyView.setText(product.getQuantity().toString());
				unitView.setText(product.getUnit().toString());
				srpView.setText(product.getSuggestedPrice().toString());
				priceView.setText(product.getPrice().toString());
				extnView.setText(""+Integer.parseInt(product.getQuantity()) * Float.parseFloat(product.getPrice()));
				mainTableLayout.addView(tablerowView);

				total += Integer.parseInt(product.getQuantity()) * Float.parseFloat(product.getPrice());
				Log.v(TAG, product.getProductId() +" Quantity - "+product.getQuantity() + "  Update Total + "+ df.format(total) + "  hhh "+products.size());
			}

			if(!order.isWarehouse()) {
				editBtn.setVisibility(View.VISIBLE);
				warehouseBtn.setVisibility(View.VISIBLE);
			} else {
				editBtn.setVisibility(View.GONE);
				warehouseBtn.setVisibility(View.GONE);
			}
		}
	}

	public void updateOrderDetails(Order order2, String quantity) {
		//		qtyView.setText(quantity);
		//		Integer cost = Integer.parseInt(order2.getAmount()) * Integer.parseInt(quantity);
		//		orderAmountView.setText(cost.toString());
		//		order.setAmount(cost.toString());
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_details_editbutton:
			Log.v(TAG,"edit  "+order.getOrderId());
			Intent intent = new Intent(getActivity().getApplicationContext(), NewOrderActivity.class);
			intent.putExtra("orderId", order.getOrderId());
			startActivity(intent);
			break;
		case R.id.order_details_warehousebutton:
			Log.v(TAG,"id "+order.getOrderId());
			progressDialog.show();
			sendtoServer();
			break;
		default:
			break;
		}
	}

	private void sendtoServer() {
		if(newTecApp.getUserId() != null) {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			final String formattedDate = df.format(c.getTime());

			NewTecCallback<Object> callback  = new NewTecCallback<Object>() {

				public void onSuccess(Object object) {
					progressDialog.dismiss();
					newTecApp.shareDatabaseInstance().deleteCacheOrderId(newTecApp.getUserId(), order.getOrderId());
					newTecApp.shareDatabaseInstance().deleteCacheProductId(newTecApp.getUserId(), order.getOrderId());
					NewOrder newOrder = (NewOrder) object;
					Log.v(TAG,"neworder id   "+newOrder.getNewOrderId());
					order.setSend(false);
					order.setOrderId(newOrder.getNewOrderId());
					order.setSaved(false);
					order.setOrderDate(formattedDate);
					editBtn.setVisibility(View.GONE);
					warehouseBtn.setVisibility(View.GONE);
					order.setWarehouse(true);
					newTecApp.shareDatabaseInstance().insertOrder(order, newTecApp.getUserId());
					showAlertDialog(getActivity(), "  Order added successfully.", true);
				}

				public void onError(Exception exception) {
					progressDialog.dismiss();
					Toast.makeText(getActivity(), "  Unable to Connect to server.", Toast.LENGTH_SHORT).show();
					order.setSend(true);
					order.setSaved(false);
					order.setOrderDate(formattedDate);
					editBtn.setVisibility(View.GONE);
					warehouseBtn.setVisibility(View.GONE);
					order.setWarehouse(true);
					newTecApp.shareDatabaseInstance().insertOrder(order, newTecApp.getUserId());
					showAlertDialog(getActivity(), "   Order Saved. ", true);
				}
			};

			ApiManager.getInstance(getActivity()).getAddOrder(getActivity(), order.getProducts(),
					newTecApp.getUserId(), order.getCustId(), true, 100, order.getOrderNotes(), callback);
		} else {
			progressDialog.dismiss();
			order.setWarehouse(false);
			showAlertDialog(getActivity(), "   Unable to Send.", true);
		}
	}

	private void showAlertDialog(final Context context,String message , boolean isCancelable){
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				OrderListFragment orderListFragment = (OrderListFragment) getActivity().getFragmentManager().findFragmentById(R.id.orderlistfragment);
				orderListFragment.getOrders();
				builder.create().dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
