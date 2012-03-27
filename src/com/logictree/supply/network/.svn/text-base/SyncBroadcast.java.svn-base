package com.logictree.supply.network;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.models.AddCustomer;
import com.logictree.supply.models.Customer;
import com.logictree.supply.models.NewOrder;
import com.logictree.supply.models.Order;
import com.logictree.supply.models.Product;

public class SyncBroadcast extends BroadcastReceiver {

	private static final String TAG = "SyncBroadcast";
	private NotificationManager manager ;
	private NewTecApp app;

	@Override
	public void onReceive(Context context, Intent arg1) {
		app = (NewTecApp) context.getApplicationContext();
		SharedPreferences sharedPreferences = PreferenceManager
		.getDefaultSharedPreferences(context);
		boolean isSynwithServer = sharedPreferences.getBoolean("syc_with_server", false);
		manager =   (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		if(app.getUserId() != null && isSynwithServer) {
			Log.v(TAG, app.getUserId());
			showNotification(context, "Sync started");
			Date d=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			Log.v(TAG, sdf.format(d));
			Log.v(TAG, "Message Received at "+sdf.format(d)+" ");
				startSync(context);
		}
	}

	private void showNotification(Context context, String msg) {

		CharSequence text = "Newtec sync";

		Notification notification = new Notification(android.R.drawable.ic_notification_overlay, text,
				System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(), 0);

		notification.setLatestEventInfo(context,msg,
				text, contentIntent);

		manager.notify(R.string.app_name, notification);
	}

	private void startSync(final Context context) {
		final Date d=new Date();
		final SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String formattedDate = df.format(c.getTime());
		Log.v(TAG, "Sync at "+sdf.format(d));
		final ApiManager apimanager = ApiManager.getInstance(context);

		if(app.getUserId() != null) {
			final List<Order> cacheOrders = app.shareDatabaseInstance().getCacheOrders(app.getUserId());
			final List<Customer> cachecustomers = app.shareDatabaseInstance().getCacheCustomers(app.getUserId());

			if(cacheOrders != null && cacheOrders.size() > 0) {
				for(final Order order : cacheOrders) {
					List<Product> products = order.getProducts();
					if(!products.isEmpty()) {
						Log.v(TAG,"cache size      "+products.size()+"  "+order.getCustId());
						apimanager.getAddOrder(context, products,
								app.getUserId(), order.getCustId(), order.isWarehouse(),50,order.getOrderNotes(), new NewTecCallback<Object>() {

							public void onSuccess(Object object) {
								Log.v(TAG,"success "+cacheOrders.indexOf(order)+"  "+order.getOrderId());
								app.shareDatabaseInstance().deleteCacheOrderId(app.getUserId(), order.getOrderId());
								app.shareDatabaseInstance().deleteCacheProductId(app.getUserId(), order.getOrderId());

									NewOrder newOrder = (NewOrder) object;
									order.setOrderId(newOrder.getNewOrderId());
									order.setSaved(false);
									order.setSend(false);
									order.setWarehouse(true);
									order.setOrderDate(formattedDate);
									Log.v(TAG,"new orderid  "+newOrder.getNewOrderId() + "  "+order.getOrderId());
									app.shareDatabaseInstance().insertOrder(order, app.getUserId());

								if(cacheOrders.lastIndexOf(order) == cacheOrders.size()-1) {
									loadOrders(context);
								}
							}

							public void onError(Exception exception) {
								Log.v(TAG,"failedo");
								cacheOrders.clear();
								context.getSystemService(Context.NOTIFICATION_SERVICE);
								manager.cancel(R.string.app_name);
								showNotification(context,"Please check internet connection to sync");
							}
						});
					}
				} 
			} else {
				Log.v(TAG, "cache orders empty");
				loadOrders(context);
			}

			if(cachecustomers != null && cachecustomers.size() > 0) {
				for(final Customer customer: cachecustomers) {
					Log.v(TAG,""+customer.getCustomerId());
					apimanager.addCustomer(context,customer.getCustomername(),
							"",customer.getBusinessName(), customer.getAddress(),
							customer.getLicence(), customer.getCity(), customer.getState(),
							customer.getZipcode(), customer.getPhoneNo(),
							customer.isMsaFlagOn(), customer.getPrice_level(),
							customer.getStatus(), app.getUserId(), 60,
							new NewTecCallback<Object>() {

						public void onSuccess(Object object) {
							app.shareDatabaseInstance().deleteCacheCustomerId(app.getUserId(), customer.getCustomerId());

							List<AddCustomer> addCustomers = (List<AddCustomer>) object;

							for(AddCustomer addCustomer : addCustomers) {
								String customerId = addCustomer.getCustomerId();
								customer.setCustomerId(customerId);
								customer.setSaved(false);
								Log.v(TAG, customerId+"  "+customer.getCustomerId());
								app.shareDatabaseInstance().insertCustomer(customer, app.getUserId().trim());
							}

							context.getSystemService(Context.NOTIFICATION_SERVICE);
							manager.cancel(R.string.app_name);
							showNotification(context, "  Sync Completed");

							if(cachecustomers.lastIndexOf(customer) == cachecustomers.size()-1) {
								loadCustomers(context);
							}
						}
						public void onError(Exception exception) {
							Log.v(TAG,"Failedc");
							context.getSystemService(Context.NOTIFICATION_SERVICE);
							manager.cancel(R.string.app_name);
							showNotification(context,"Please check internet connection to sync");
						}
					});
				}
			} else {
				Log.v(TAG, "cache customers empty");
				loadCustomers(context);
			}
			apimanager.getProductList(context, 55, null);
			apimanager.getDepartmentList(context,55, null);
		}
	}
	
	private void loadOrders(final Context context) {
		ApiManager.getInstance(context).getOrderList(context, app.getUserId(), 40 , new NewTecCallback<Object>() {

			public void onSuccess(Object object) {
			}
			public void onError(Exception exception) {
				Log.v(TAG,"Failedo");
				context.getSystemService(Context.NOTIFICATION_SERVICE);
				manager.cancel(R.string.app_name);
				showNotification(context,"Please check internet connection to sync");
			}
		});
	}
	
	private void loadCustomers(final Context context) {
		ApiManager.getInstance(context).getCustomerList(context, 30, new NewTecCallback<Object>() {

			public void onSuccess(Object object) {
				context.getSystemService(Context.NOTIFICATION_SERVICE);
				manager.cancel(R.string.app_name);
				showNotification(context, "  Sync Completed");
			}

			public void onError(Exception exception) {
				Log.v(TAG, "failedc");
				context.getSystemService(Context.NOTIFICATION_SERVICE);
				manager.cancel(R.string.app_name);
				showNotification(context,"Please check internet connection to sync");
			}
		});
	}
}
