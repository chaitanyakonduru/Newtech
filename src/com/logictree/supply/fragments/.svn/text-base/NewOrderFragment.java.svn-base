package com.logictree.supply.fragments;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewOrderActivity;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.models.Customer;
import com.logictree.supply.models.LoginInfo;
import com.logictree.supply.models.NewOrder;
import com.logictree.supply.models.Order;
import com.logictree.supply.models.Product;
import com.logictree.supply.models.Util;
import com.logictree.supply.network.ApiManager;
import com.logictree.supply.network.NewTecCallback;
import com.logictreeit.supply.listeners.NewOrderEditListener;

public class NewOrderFragment extends Fragment implements OnClickListener,
NewOrderEditListener {

	private static final String TAG = "NewOrderFragment";
	private TableLayout mainTableLayout;
	private TextView businessNameView;
	private TextView totalView;
	private ImageButton savebtn;
	private ImageButton cancelBtn;
	private ImageButton sendToWareHouse;

	private TextView orderIdView;
	private TextView orderdateView;
	private TextView neworderIdTextView;

	private Customer customer;
	private List<Product> products;
	private Order order;
	private boolean isUpdateOrder;
	private AsyncTask task;
	private EditText orderNotesET;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		products = new ArrayList<Product>();
		final NewTecApp app = (NewTecApp) getActivity().getApplication();
		Bundle bundle = getActivity().getIntent().getExtras();
		if (bundle != null) {
			if (bundle.containsKey("orderId")) {
				String orderId = bundle.getString("orderId");

				if(app.getUserId() != null) {
					order = app.shareDatabaseInstance().getOrder(app.getUserId(), orderId);
				}
				isUpdateOrder = true;
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (order != null) {
			initTable();
		}
	}

	private void initTable() {
		Log.v(TAG,"initTable()");
		if (order != null) {
			Log.v(TAG,"order");
			this.businessNameView.setText(order.getBusinessname());
			if(order.isSaved()) {
				this.orderIdView.setText("0");
				Log.v(TAG,"orderid "+order.getOrderId());
			} else {
				this.orderIdView.setText(order.getOrderId());
			}
			Log.v(TAG, "Order id - "+order.getOrderId());
			this.orderdateView.setText(order.getOrderDate());
			this.products = order.getProducts();
			updateTotal();
			for (Product mproduct : this.products) {
				View tablerowView = LayoutInflater.from(getActivity()).inflate(
						R.layout.layout_new_tablerow, null);
				tablerowView.setTag(mproduct);
				TextView productIdView = (TextView) tablerowView
				.findViewById(R.id.new_table_row_productid);
				TextView productnameView = (TextView) tablerowView
				.findViewById(R.id.new_table_row_productname);
				TextView priceView = (TextView) tablerowView
				.findViewById(R.id.new_table_row_price);
				/*TextView unitView = (TextView) tablerowView
				.findViewById(R.id.table_row_unit);
				TextView srpView = (TextView) tablerowView
				.findViewById(R.id.table_row_srp);*/
				TextView qtyView = (TextView) tablerowView
				.findViewById(R.id.new_table_row_qty);
				/*TextView extnView = (TextView) tablerowView
				.findViewById(R.id.table_row_cost);*/
				ImageButton button = (ImageButton) tablerowView
				.findViewById(R.id.new_table_row_delteButton);
				LinearLayout layout = (LinearLayout) tablerowView
				.findViewById(R.id.new_action_layout);
				layout.setVisibility(View.VISIBLE);

				ImageButton editButton = (ImageButton) tablerowView
				.findViewById(R.id.new_table_row_edit);
				editButton.setTag(mproduct);
				sendToWareHouse.setTag(mproduct);
				savebtn.setTag(mproduct);
				button.setOnClickListener(this);
				editButton.setOnClickListener(this);
				productIdView.setText(mproduct.getProductId().toString());
				productnameView.setText(mproduct.getProductName().toString());
				priceView.setText(mproduct.getPrice().toString());
				//				unitView.setText(mproduct.getUnit().toString());
				//				srpView.setText(mproduct.getSuggestedPrice().toString());
				qtyView.setText(mproduct.getQuantity().toString());
				/*if(extnView != null){
					DecimalFormat df = new DecimalFormat("###.##");
					float extn = Integer.parseInt(mproduct.getQuantity()) * Float.parseFloat(mproduct.getPrice()) ;
					extnView.setText(""+df.format(extn));
				}*/
				Util.sQtyViewcache.put(mproduct.getProductId(), tablerowView);
				mainTableLayout.addView(tablerowView);
			}
		} else {
			Log.v(TAG," ! order");
			// order = new Order("-1", customer.getCustomername(), "", "",
			// info.getUsername() , "", customer.getCustomerId(), false,false);
			// order.setProducts(products);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_newfragment, container);
		mainTableLayout = (TableLayout) view.findViewById(R.id.maintable);
		businessNameView = (TextView) view.findViewById(R.id.my_table_custname);
		neworderIdTextView = (TextView)view.findViewById(R.id.my_table_orderidview);
		totalView = (TextView) view.findViewById(R.id.total);
		savebtn = (ImageButton) view.findViewById(R.id.my_table_save);
		cancelBtn = (ImageButton) view.findViewById(R.id.my_table_cancel);
		orderIdView = (TextView) view.findViewById(R.id.my_table_orderid);
		orderdateView = (TextView) view.findViewById(R.id.my_table_orderdate);
		orderNotesET = (EditText) view.findViewById(R.id.notes);
		orderNotesET.setText("      Please deliver asap");
		savebtn.setOnClickListener(NewOrderFragment.this);
		view.findViewById(R.id.my_table_cancel).setOnClickListener(NewOrderFragment.this);
		sendToWareHouse = (ImageButton) view.findViewById(R.id.my_table_sendtowarehouse);
		sendToWareHouse.setOnClickListener(NewOrderFragment.this);
		cancelBtn.setOnClickListener(NewOrderFragment.this);
		return view;
	}

	public void updateTable(Product product) {
		Log.v(TAG,"updateTable");
		View tablerowView = null;
		DecimalFormat df = new DecimalFormat("###.##");
		float cost = (Float.valueOf(product.getPrice())
				* Integer.parseInt(product.getQuantity())); 
		String qty = product.getQuantity();
		if (products.contains(product)) {
			Log.v(TAG, "products.contains(product)");
			product = products.get(products.indexOf(product));
			product.setQuantity(qty);

			tablerowView = Util.sQtyViewcache.get(product.getProductId());

			TextView qtyView = (TextView) tablerowView
			.findViewById(R.id.new_table_row_qty);
			TextView productnameView = (TextView) tablerowView
			.findViewById(R.id.new_table_row_productname);
			TextView productIdView = (TextView) tablerowView
			.findViewById(R.id.new_table_row_productid);
			TextView priceView = (TextView) tablerowView
			.findViewById(R.id.new_table_row_price);
			/*TextView unitView = (TextView) tablerowView
			.findViewById(R.id.table_row_unit);
			TextView srpView = (TextView) tablerowView
			.findViewById(R.id.table_row_srp);
			TextView extnView = (TextView) tablerowView
			.findViewById(R.id.table_row_cost);*/
			productIdView.setText(product.getProductId());
			productnameView.setText(product.getProductName());
			priceView.setText(product.getPrice());
			//			unitView.setText(product.getUnit());
			//			srpView.setText("" + product.getSuggestedPrice());

			float xtn = Integer.parseInt(qty)
			* Float.parseFloat(product.getPrice().toString());
			qtyView.setText("" + qty);
			//			extnView.setText("" + df.format(xtn));
			updateTotal();
		} else {
			Log.v(TAG," !  products.contains(product)");
			products.add(product);
			tablerowView = LayoutInflater.from(getActivity()).inflate(
					R.layout.layout_new_tablerow, null);
			tablerowView.setTag(product);
			mainTableLayout.addView(tablerowView);
			Util.sQtyViewcache.put(product.getProductId(), tablerowView);
			TextView qtyView = (TextView) tablerowView
			.findViewById(R.id.new_table_row_qty);
			LinearLayout layout = (LinearLayout) tablerowView
			.findViewById(R.id.new_action_layout);
			layout.setVisibility(View.VISIBLE);

			ImageButton editButton = (ImageButton) tablerowView
			.findViewById(R.id.new_table_row_edit);
			editButton.setTag(product);
			ImageButton button = (ImageButton) tablerowView
			.findViewById(R.id.new_table_row_delteButton);
			button.setOnClickListener(this);
			editButton.setOnClickListener(this);
			// editButton.setVisibility(View.GONE);
			/*TextView extnView = (TextView) tablerowView
			.findViewById(R.id.table_row_cost);*/
			TextView productIdView = (TextView) tablerowView
			.findViewById(R.id.new_table_row_productid);
			TextView productnameView = (TextView) tablerowView
			.findViewById(R.id.new_table_row_productname);
			TextView priceView = (TextView) tablerowView
			.findViewById(R.id.new_table_row_price);
			/*TextView unitView = (TextView) tablerowView
			.findViewById(R.id.table_row_unit);
			TextView srpView = (TextView) tablerowView
			.findViewById(R.id.table_row_srp);*/
			productIdView.setText(product.getProductId());
			productnameView.setText(product.getProductName());
			priceView.setText(product.getPrice());
			//			unitView.setText(product.getUnit());
			//			srpView.setText("" + product.getSuggestedPrice());
			qtyView.setText(qty + "");
			//			extnView.setText("" + df.format(cost));
		}
		sendToWareHouse.setTag(product);
		savebtn.setTag(product);

		final String totalString = totalView.getText().toString();
		float total = Float.parseFloat(totalString.trim()) + cost;
		totalView.setText(""+df.format(total));
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_table_row_delteButton:
			if(isUpdateOrder && products != null && products.size() == 1) {
				showErrorDialog(v);
			} else {
				showConfirmDialog(v);
			}
			break;
		case R.id.my_table_save:
			Product m_product = (Product) v.getTag();
			if(m_product != null)	Log.v(TAG, "save  " + m_product.getProductName()+"  "+products.size());
			sendToWarehouse(false);
			break;
		case R.id.my_table_cancel:
			getActivity().finish();
			break;
		case R.id.my_table_sendtowarehouse:
			Product mProduct = (Product) v.getTag();
			if(mProduct != null) Log.v(TAG, mProduct.getProductName());
			sendToWarehouse(true);
			break;
		case R.id.new_table_row_edit:
			View _v =  (View) v.getParent().getParent();
			final Product product = (Product) _v.getTag();
			TableRow tableRowView = (TableRow) v.getParent().getParent();
			// Util.updateProduct(getActivity(), product,(TextView)
			// tableRowView.findViewById(R.id.table_row_qty),(TextView)
			// tableRowView.findViewById(R.id.total));
			Util.updateProduct(getActivity(), product, tableRowView, this);
			break;
		default:
			break;
		}
	}

	private void showErrorDialog(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("   Order must have atleast one product");
		builder.setPositiveButton("Ok", null);
		builder.create().show();
	}

	public void deleterow(View view) {
		Log.v("tag", "delete");
		Log.v(TAG,""+mainTableLayout.getChildCount());
		TableRow tableRowView = (TableRow) view.getParent().getParent();
		mainTableLayout.removeView((View) view.getParent().getParent());
		final Product product = (Product) tableRowView.getTag();
		products.remove(product);
		Util.sQtyViewcache.remove(product.getProductId());
		updateTotal();
		if(order != null) {
			Log.v(TAG,order.getOrderId());
			Log.v(TAG,product.getProductId());
			final NewTecApp app = (NewTecApp) getActivity().getApplication();
			if(app.getUserId() != null && order.getOrderId() != null && product.getProductId() != null) {
				app.shareDatabaseInstance().deleteProduct(app.getUserId(), order.getOrderId(), product.getProductId());
			}
		} else {
			Log.v(TAG,"empty");
		}
	}

	private void updateTotal() {
		DecimalFormat df = new DecimalFormat("###.##");
		float total = 0;
		for (Product product : this.products) {
			total += Integer.parseInt(product.getQuantity())
			* Float.parseFloat(product.getPrice());
			Log.v(TAG, "Quantity - "+product.getQuantity() + "  Update Total + "+ total + "  hhh "+products.size());
		} 

		Log.v(TAG, " Update Total + "+ df.format(total));
		final String _total = ""+total;
		Log.v(TAG, "  Total + "+ _total);
		final String totalString = totalView.getText().toString();
		Log.v(TAG, " Total String + "+ totalString);

		totalView.setText(""+df.format(total));
	}

	public void setCustomer(Customer customer) {
		if (businessNameView != null) {
			businessNameView.setText(customer.getBusinessName());
			this.customer = customer;
			this.neworderIdTextView.setVisibility(View.INVISIBLE);
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String formattedDate = df.format(c.getTime());
			orderdateView.setText(formattedDate);
		}
	}

	public void NeworderEdit(Context context, Product product, View view,
			String quantity) {
		DecimalFormat df = new DecimalFormat("###.##");
		TextView qtyView = (TextView) view.findViewById(R.id.new_table_row_qty);
		//		TextView extnView = (TextView) view.findViewById(R.id.table_row_cost);
		// TextView totalView = (TextView) view.findViewById(R.id.total);
		String price = product.getPrice().toString();
		Log.v(TAG, "qty  " + quantity);
		float extn = Integer.parseInt(quantity) * Float.parseFloat(price);
		qtyView.setText(quantity);
		//		extnView.setText("" + df.format(extn));

		updateTotal();
		/*
		 * if(totalView.getText().toString() != null) { String cost =
		 * totalView.getText().toString(); int total = Integer.parseInt(cost) +
		 * extn; totalView.setText(""+total); } else {
		 * totalView.setText(""+extn); }
		 */
	}

	private void showConfirmDialog(final View v){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("   Do you really want to delete this Product?");
		builder.setPositiveButton("Cancel",null );

		builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				deleterow(v);
			}
		});
		builder.create().show();
	}

	public void sendToWarehouse(final boolean isSendtowarehouse) {
		if (mainTableLayout.getChildCount() > 0) {
			updateOrder(isSendtowarehouse);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("   Please place the order");
			builder.setPositiveButton("Ok", null);
			builder.create().show();
		}
	}

	private void updateOrder(boolean isSendtowarehouse) {
		Log.v(TAG, isSendtowarehouse+"");
		NewOrderActivity activity = (NewOrderActivity) getActivity();
		final NewTecApp newTecApp = (NewTecApp) activity.getApplication();
		final LoginInfo info = newTecApp.shareDatabaseInstance().getLoginInfo(newTecApp.getUserId());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String formattedDate = df.format(c.getTime());

		if(!isSendtowarehouse) {
			final ProgressDialog progressDialog = new ProgressDialog(
					getActivity());
			progressDialog.show();
			progressDialog.setMessage("Processesing..");

			if (order == null) {
				final String totalString = totalView.getText().toString();
				Log.v(TAG,"total  "+totalString);
				String orderId = "S"+newTecApp.shareDatabaseInstance().getLastInsertOrderId();
				Log.v(TAG,"local  "+ customer.getBusinessName()+"  "+customer.getCustomerId());
				order = new Order(orderId, customer
						.getBusinessName(), formattedDate, totalString,
						info.getName(), "Active",
						customer.getCustomerId(), false,
						true, false);
				order.setOrderNotes(orderNotesET.getText().toString().trim());
				order.setAmount(totalString);
				order.setSaved(true);
				order.setWarehouse(false);
				Log.v(TAG,"orderId  "+orderId);
				order.setProducts(products);
				for(Product product:products) {
					product.setSaved(true);
					Log.v(TAG,products.size()+"  id " +product.getProductId());
				}
				order.setSaved(true);
			} else {
				List<Product> products = order.getProducts();
				final String totalString = totalView.getText().toString();
				order.setAmount(totalString);
				order.setProducts(products);
				order.setOrderNotes(orderNotesET.getText().toString().trim());
				order.setWarehouse(false);
				order.setSaved(true);
				order.setOrderDate(formattedDate);
				if(!products.isEmpty()) {
					for(Product product:products) {
						product.setSaved(true);
						Log.v(TAG, products.size()+"  local  " + product.getProductId()+"  "+ product.getQuantity());
					}
				}
			}

			task = new AsyncTask() {

				@Override
				protected Object doInBackground(
						Object... params) {
					boolean res = newTecApp.shareDatabaseInstance().insertOrder(
							order, newTecApp.getUserId());
					return res;
				}

				@Override
				protected void onPostExecute(Object result) {
					if (result instanceof Boolean) {
						if (((Boolean) result)
								.booleanValue()) {
							progressDialog.dismiss();
							showAlertDialog(getActivity(),"     Order Saved.",true);
						}
					}
				}
			};
			task.execute();
		} else {
			sendtoServer(isSendtowarehouse);
		}
	}

	private void sendtoServer(final boolean isSendtowarehouse) {
		Log.v(TAG, isSendtowarehouse+"");
		NewOrderActivity activity = (NewOrderActivity) getActivity();
		final NewTecApp newTecApp = (NewTecApp) activity.getApplication();
		final LoginInfo info = newTecApp.shareDatabaseInstance().getLoginInfo(newTecApp.getUserId());
		final String custId = order != null ? order.getOrderId() : customer
				.getCustomerId();
		final ProgressDialog progressDialog = new ProgressDialog(
				getActivity());
		progressDialog.show();
		progressDialog.setMessage("Processesing..");

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String formattedDate = df.format(c.getTime());

		NewTecCallback<Object> callback  = new NewTecCallback<Object>() {

			public void onSuccess(Object object) {
				Log.v(TAG, newTecApp.getUserId() + "");
				progressDialog.dismiss();
				NewOrder newOrder = (NewOrder) object;
				Log.v(TAG,"neworder id   "+newOrder.getNewOrderId());
				if(newTecApp.getUserId() != null &&  !isUpdateOrder){
					showAlertDialog(getActivity(),"  Order added successfully .",true);
					if(order == null) {
						final String totalString = totalView.getText().toString();
						final String orderId = newOrder.getNewOrderId();
						Log.v(TAG,"total  "+totalString);
						Log.v(TAG, customer.getBusinessName()+"  "+customer.getCustomerId());
						order = new Order(orderId, customer
								.getBusinessName(), formattedDate, totalString,
								info.getName(), "Active",
								customer.getCustomerId(), false,
								false, false);
						order.setOrderNotes(orderNotesET.getText().toString().trim());
						order.setSend(false);
						order.setAmount(totalString);
						order.setWarehouse(isSendtowarehouse);
						Log.v(TAG,"orderId success "+orderId);
						order.setProducts(products);
						for(Product product:products) {
							product.setSaved(false);
							product.setSend(false);
							product.setNewOrder(true);
							Log.v(TAG,products.size()+"  id " +product.getProductId());
						}
						order.setSaved(false);
						order.setNewOrder(true);
						newTecApp.shareDatabaseInstance().insertOrder(
								order, newTecApp.getUserId());
					}
				}  else {
					newTecApp.shareDatabaseInstance().deleteCacheOrderId(newTecApp.getUserId(), order.getOrderId());
					newTecApp.shareDatabaseInstance().deleteCacheProductId(newTecApp.getUserId(), order.getOrderId());
					showAlertDialog(getActivity(),"  Order added successfully .",true);
					order.setOrderId(newOrder.getNewOrderId());
					order.setSaved(false);
					order.setStatus("Active");
					order.setOrderDate(formattedDate);
					order.setWarehouse(isSendtowarehouse);
					newTecApp.shareDatabaseInstance().insertOrder(
							order, newTecApp.getUserId());
				}
			}

			public void onError(Exception exception) {
				Log.v(TAG, "failure   " + exception.getMessage());
				progressDialog.dismiss();
				Toast.makeText(getActivity(),
						"Unable to Connect to server. ",
						Toast.LENGTH_SHORT).show();
				if (order == null) {
					final String totalString = totalView.getText().toString();
					Log.v(TAG,"total  "+totalString);
					String orderId = "S"+newTecApp.shareDatabaseInstance().getLastInsertOrderId();
					Log.v(TAG, customer.getBusinessName()+"  "+customer.getCustomerId());
					order = new Order(orderId, customer
							.getBusinessName(), formattedDate, totalString,
							info.getName(), "Active",
							customer.getCustomerId(), false,
							false, false);
					order.setOrderNotes(orderNotesET.getText().toString().trim());
					order.setAmount(totalString);
					order.setWarehouse(isSendtowarehouse);
					Log.v(TAG,"orderId  "+orderId);
					order.setProducts(products);
					for(Product product:products) {
						product.setSaved(false);
						product.setSend(true);
						Log.v(TAG,products.size()+"  id " +product.getProductId());
					}
					order.setSaved(false);
					order.setSend(true);
				} else {
					List<Product> products = order.getProducts();
					final String totalString = totalView.getText().toString();
					order.setSaved(false);
					order.setAmount(totalString);
					order.setSend(true);
					order.setProducts(products);
					order.setOrderNotes(orderNotesET.getText().toString().trim());
					order.setWarehouse(isSendtowarehouse);
					order.setOrderDate(formattedDate);
					if(!products.isEmpty()) {
						for(Product product:products) {
							product.setSaved(false);
							product.setSend(true);
							Log.v(TAG, products.size()+"  failure  " + product.getProductId()+"  "+ product.getQuantity());
						}
					}
				}

				task = new AsyncTask() {

					@Override
					protected Object doInBackground(
							Object... params) {
						boolean res = newTecApp.shareDatabaseInstance().insertOrder(
								order, newTecApp.getUserId());
						return res;
					}

					@Override
					protected void onPostExecute(Object result) {
						if (result instanceof Boolean) {
							if (((Boolean) result)
									.booleanValue()) {
								showAlertDialog(getActivity(),"  Order Saved .",true);
							}
						}
					}
				};
				task.execute();
			}
		};

		if (newTecApp.getUserId() != null  ) {
			if(!isUpdateOrder){
				Log.v(TAG, " Customerc id " + custId);
				ApiManager.getInstance(getActivity()).getAddOrder(getActivity(), products,
						newTecApp.getUserId(), custId, isSendtowarehouse, 100, orderNotesET.getText().toString().trim(), callback);
			} else {
				Log.v(TAG, " order id " + custId);
				if(custId.contains("S")) {
					if(order != null) {
						List<Product> products = order.getProducts();
						final String totalString = totalView.getText().toString();
						order.setAmount(totalString);
						order.setProducts(products);
						order.setWarehouse(isSendtowarehouse);
						order.setOrderDate(formattedDate);
						if(!products.isEmpty()) {
							for(Product product:products) {
								Log.v(TAG, order.getOrderId() +"  "+products.size()+"  success  " + product.getProductId()+"  "+ product.getQuantity()+" custname "+order.getBusinessname()+"  "+order.getCustId());
							}
						}
						ApiManager.getInstance(getActivity()).getAddOrder(getActivity(), products,
								newTecApp.getUserId(), order.getCustId(), isSendtowarehouse, 100, orderNotesET.getText().toString().trim(), callback);
					}
				} else {
					ApiManager.getInstance(getActivity()).getAddOrder(getActivity(), products,
							newTecApp.getUserId(), order.getCustId(), isSendtowarehouse, 100, orderNotesET.getText().toString().trim(), callback);
				}
			}
		} else {
			progressDialog.dismiss();
			Toast.makeText(getActivity(),
					"Unable to add order an error occured",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void showAlertDialog(final Context context,String message , boolean isCancelable){
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				getActivity().finish();
				builder.create().dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.v(TAG,"onpause");
		if(task != null) {
			task.cancel(true);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.v(TAG,"onstop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG,"ondestroy");
		if(task != null) {
			task.cancel(true);
		}
		Util.sQtyViewcache.clear();
	}
}
