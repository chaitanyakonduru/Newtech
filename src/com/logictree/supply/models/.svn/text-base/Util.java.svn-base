package com.logictree.supply.models;

import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.logictree.supply.R;
import com.logictreeit.supply.listeners.NewOrderEditListener;
import com.logictreeit.supply.listeners.OrderAddListener;

public class Util {
	
    protected static final String TAG = "Util";
	public static HashMap<String, View> sQtyViewcache = new HashMap<String, View>();
	
	//NewOrderListFragment
	public static void orderProduct(final Context context , final Product product , final OrderAddListener listener){

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.popup_add_order,null);
		final TextView productnameView = (TextView) view.findViewById(R.id.popup_productname);
		final TextView priceView = (TextView) view.findViewById(R.id.popup_price);
		final TextView unitView = (TextView)view.findViewById(R.id.popup_unit);
		final EditText qty = (EditText)view.findViewById(R.id.qty);
		final Button cancelBtn = (Button)view.findViewById(R.id.alert_dialog_negativebutoon);
		final Button positiveBtn = (Button)view.findViewById(R.id.alert_dialog_positivebutoon);
		qty.setInputType(0);
		productnameView.setText(product.getProductName());
		priceView.setText(product.getPrice());
		unitView.setText(product.getUnit());
		builder.setTitle("Order");
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		positiveBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				String quantity = qty.getText().toString();
				if(TextUtils.isEmpty(quantity)){
					qty.setError("enter quantity");
				}  else if(quantity.equals("0") || quantity.equals("00") || quantity.equals("000") ){
					qty.setError("please enter a valid quantity");
					qty.setText("");
				}  else {
					product.setQuantity(quantity);
					listener.onOrderAdd(product);
					dialog.dismiss();
				}
			}
		});
		
		dialog.show();

		((Button)view.findViewById(R.id.one)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() + "1" );
			}
		});

		((Button)view.findViewById(R.id.two)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() + "2");
			}
		});
		((Button)view.findViewById(R.id.three)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"3");
			}
		});
		((Button)view.findViewById(R.id.four)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"4");
			}
		});
		((Button)view.findViewById(R.id.five)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() + "5");
			}
		});
		((Button)view.findViewById(R.id.six)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"6");
			}
		});
		((Button)view.findViewById(R.id.seven)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"7");
			}
		});
		((Button)view.findViewById(R.id.eight)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"8");
			}
		});
		((Button)view.findViewById(R.id.nine)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"9");
			}
		});
		((Button)view.findViewById(R.id.zero)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"0");
			}
		});
		
		((Button)view.findViewById(R.id.clear)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				qty.setCursorVisible(true);
				qty.setText("");
				/*String number = qty.getText().toString();
				int length = number.length();
				int endIndex = length - 1;
				if (endIndex < 1) {
					qty.setCursorVisible(true);
					qty.setText("");
				} else {
					qty.setText(number.subSequence(0, endIndex));
				}*/
			}
		});
	}
	
	
	//NewOrderFragment
	public static void updateProduct(final Context context , final Product product ,final View _view,final NewOrderEditListener listener){

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.popup_add_order,null);
		final TextView productnameView = (TextView) view.findViewById(R.id.popup_productname);
		final TextView priceView = (TextView) view.findViewById(R.id.popup_price);
		final TextView unitView = (TextView)view.findViewById(R.id.popup_unit);
		final EditText qty = (EditText)view.findViewById(R.id.qty);
		final Button cancelBtn = (Button)view.findViewById(R.id.alert_dialog_negativebutoon);
		final Button positiveBtn = (Button)view.findViewById(R.id.alert_dialog_positivebutoon);
		qty.setInputType(0);
		if(product != null && product.getQuantity()!= null){
			qty.setText(product.getQuantity());
		}
		productnameView.setText(product.getProductName());
		priceView.setText(product.getPrice());
		unitView.setText(product.getUnit());
		builder.setTitle("Order");
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		positiveBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				String quantity = qty.getText().toString().trim();
				if(TextUtils.isEmpty(quantity)){
					qty.setError("enter quantity");
				} else if(quantity.equals("0") || quantity.equals("00") || quantity.equals("000") ){
					qty.setError("please enter a valid quantity");
					qty.setText("");
				}  else {
					Log.v("TAG","quantiy  "+quantity);
					product.setQuantity(""+quantity);
					listener.NeworderEdit(context, product, _view,quantity);
//					_view.setText(quantity);
					dialog.dismiss();
				}
			}
		});
		
		dialog.show();

		((Button)view.findViewById(R.id.one)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() + "1" );
			}
		});

		((Button)view.findViewById(R.id.two)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() + "2");
			}
		});
		((Button)view.findViewById(R.id.three)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"3");
			}
		});
		((Button)view.findViewById(R.id.four)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"4");
			}
		});
		((Button)view.findViewById(R.id.five)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() + "5");
			}
		});
		((Button)view.findViewById(R.id.six)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"6");
			}
		});
		((Button)view.findViewById(R.id.seven)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"7");
			}
		});
		((Button)view.findViewById(R.id.eight)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"8");
			}
		});
		((Button)view.findViewById(R.id.nine)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"9");
			}
		});
		((Button)view.findViewById(R.id.zero)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				qty.setCursorVisible(false);
				qty.setText(qty.getText().toString() +"0");
			}
		});
		
		((Button)view.findViewById(R.id.clear)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				qty.setCursorVisible(true);
				qty.setText("");
				/*String number = qty.getText().toString();
				int length = number.length();
				int endIndex = length - 1;
				if (endIndex < 1) {
					qty.setCursorVisible(true);
					qty.setText("");
				} else {
					qty.setText(number.subSequence(0, endIndex));
				}*/
			}
		});
	}
	
	public static void showErrorDialog(Context context,String message , boolean isCancelable){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton("Ok", null);
		builder.create().show();
	}
}
