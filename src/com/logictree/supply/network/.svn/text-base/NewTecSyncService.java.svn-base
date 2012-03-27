package com.logictree.supply.network;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.fragments.SettingsFragment;
import com.logictree.supply.models.AddCustomer;
import com.logictree.supply.models.Customer;
import com.logictree.supply.models.NewOrder;
import com.logictree.supply.models.Order;
import com.logictree.supply.models.Product;

public class NewTecSyncService extends Service {

	protected static final String TAG = "NewTecSyncService";
	private NotificationManager manager ;
	private ApiManager apimanager;
	private NewTecApp app;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		manager =   (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		apimanager = ApiManager.getInstance(this);
		app  = (NewTecApp) getApplication();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG,"ondestroy");
		getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(R.string.app_name);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.v(TAG, "onstart");
		startDownload();
	}

	public void startDownload() {
		Log.v(TAG,"startDownload");
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String formattedDate = df.format(c.getTime());
		showNotification("Download started for orders");
		if(app.getUserId() != null) {
			final List<Order> cacheOrders = app.shareDatabaseInstance().getCacheOrders(app.getUserId());
			final List<Customer> cachecustomers = app.shareDatabaseInstance().getCacheCustomers(app.getUserId());

			if(cacheOrders != null && cacheOrders.size() > 0) {
				for(final Order order : cacheOrders) {
					List<Product> products = order.getProducts();
					if(!products.isEmpty()) {
						Log.v(TAG,"cache size      "+products.size()+"  "+order.getCustId());
						ApiManager.getInstance(this).getAddOrder(this, products,
								app.getUserId(), order.getCustId(), order.isWarehouse(), 80,order.getOrderNotes(), new NewTecCallback<Object>() {

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
									loadOrders();
								}
							}

							public void onError(Exception exception) {
								Log.v(TAG,"failedo");
								cacheOrders.clear();
								getSystemService(NOTIFICATION_SERVICE);
								manager.cancel(R.string.app_name);
								showNotification(" Please check internet connection to download");
							}
						});
					}
				} 
			} else {
				Log.v(TAG, "cache orders empty");
				loadOrders();
			}

			apimanager.getProductList(this, 70,null);
			apimanager.getDepartmentList(this, 60, null);

			if(cachecustomers != null && cachecustomers.size() > 0) {
				for(final Customer customer: cachecustomers) {
					Log.v(TAG,""+customer.getCustomerId());
					apimanager.addCustomer(this,customer.getCustomername(),
							"", customer.getBusinessName(), customer.getAddress(),
							customer.getLicence(), customer.getCity(), customer.getState(),
							customer.getZipcode(), customer.getPhoneNo(),
							customer.isMsaFlagOn(), customer.getPrice_level(),
							customer.getStatus(), app.getUserId(), 85 ,
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

							if(cachecustomers.lastIndexOf(customer) == cachecustomers.size()-1) {
								loadCustomers();
							}
						}
						public void onError(Exception exception) {
							Log.v(TAG,"Failedc");
						}
					});
				}
			} else {
				Log.v(TAG, "cache customers empty");
				loadCustomers();
			}
		} else {
			getSystemService(NOTIFICATION_SERVICE);
			manager.cancel(R.string.app_name);
		}
	}

	private void loadCustomers() {
		if(app.getUserId() != null) {
			apimanager.getCustomerList(this, 65, new NewTecCallback<Object>() {

				public void onSuccess(Object object) {
				}

				public void onError(Exception exception) {
					Log.v(TAG, "failedc");
				}
			});
		}
	}

	private void loadOrders() {
		if(app.getUserId() != null) {
			apimanager.getOrderList(this,app.getUserId() , 75 , new NewTecCallback<Object>() {
				public void onSuccess(Object object) {
					getSystemService(NOTIFICATION_SERVICE);
					manager.cancel(R.string.app_name);
					SharedPreferences sharedPreferences = getSharedPreferences("download", 0);
					SharedPreferences.Editor editor =  sharedPreferences.edit();
					editor.putBoolean("downloading", false);
					editor.commit();
					showNotification(" Successfully downloaded  orders");
					SettingsFragment.changePreferences();
				}

				public void onError(Exception exception) {
					SharedPreferences sharedPreferences = getSharedPreferences("download", 0);
					SharedPreferences.Editor editor =  sharedPreferences.edit();
					editor.putBoolean("downloading", false);
					getSystemService(NOTIFICATION_SERVICE);
					manager.cancel(R.string.app_name);
					showNotification(" Please check internet connection to download");
					SettingsFragment.changePreferences();
				}
			});
		}
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.v(TAG,"onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	private void showNotification( String msg) {

		// In this sample, we'll use the same text for the ticker and the expanded notification
		CharSequence text = "Newtec update";

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(android.R.drawable.ic_notification_overlay, text,
				System.currentTimeMillis());

		// The PendingIntent to launch our activity if the user selects this notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(), 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this,msg,
				text, contentIntent);

		// Send the notification.
		// We use a string id because it is a unique number.  We use it later to cancel.
		manager.notify(R.string.app_name, notification);
	}
}