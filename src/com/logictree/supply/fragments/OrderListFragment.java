package com.logictree.supply.fragments;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.activities.OrderListActivity;
import com.logictree.supply.adapter.OrderAdapter;
import com.logictree.supply.models.NewOrder;
import com.logictree.supply.models.Order;
import com.logictree.supply.models.Product;
import com.logictree.supply.network.ApiManager;
import com.logictree.supply.network.DatabaseThread;
import com.logictree.supply.network.DatabaseThread.onDatabaseUpdateCompletion;
import com.logictree.supply.network.NewTecCallback;

public class OrderListFragment extends ListFragment {

	private static final String TAG = "OrderListFragment";
	private OrderSelectedListener selectedListener;
	private ListView listView;
	private OrderAdapter orderAdapter;
	private List<Order> orderlist;
	private String name;
	private List<Order> ordersByCustomer;
	private List<Order> ordersByDate;
	private List<Order> ordersByStatus;
	private Order order;
	private Menu menu;
	private OrderListActivity activity;
	private NewTecApp app;

	private static final int MENU_REFRESH = 102;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		activity = (OrderListActivity) getActivity();
		app = (NewTecApp) getActivity().getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG,"onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.v(TAG,"onActivityCreated");
		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setItemChecked(0, true);
		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setListNavigationCallbacks(ArrayAdapter.createFromResource(
				getActivity(), R.array.actionbar_list,
				android.R.layout.simple_spinner_dropdown_item),
				new OnNavigationListener() {

			public boolean onNavigationItemSelected(int arg0, long arg1) {
				TextView textView = (TextView) getActivity()
				.findViewById(android.R.id.text1);
				name = textView.getText().toString().trim();

				if (name.equalsIgnoreCase("Sort by Order")) {
					Log.v(TAG, name);

					if (orderlist != null && orderlist.size() > 0) {
						orderAdapter = new OrderAdapter(getActivity(),
								R.layout.list_item_order,
								orderlist);
						setListAdapter(orderAdapter);
						final Order order = orderlist.get(0);
						selectedListener.onListItemSelected(order);
					}

				} else if (name.equalsIgnoreCase("Sort by Business Name")) {
					Log.v(TAG, name);
					if (ordersByCustomer == null) {
						ordersByCustomer = app.shareDatabaseInstance().getOrders(app.getUserId(), "Sort by Business Name", false);
					}

					if(ordersByCustomer != null && ordersByCustomer.size() >0) {
						orderAdapter = new OrderAdapter(getActivity(),
								R.layout.list_item_order,
								ordersByCustomer);
						setListAdapter(orderAdapter);
						final Order order = ordersByCustomer.get(0);
						selectedListener.onListItemSelected(order);
						getListView().setSelection(0);
					}
				} else if(name.equalsIgnoreCase("Sort by Order Date")) {
					Log.v(TAG, name);
					if (ordersByDate == null) {
						ordersByDate = app.shareDatabaseInstance().getOrders(app.getUserId(), "Sort by Order Date", false);
					}

					if(ordersByDate != null && ordersByDate.size() >0) {
						orderAdapter = new OrderAdapter(getActivity(),
								R.layout.list_item_order,
								ordersByDate);
						setListAdapter(orderAdapter);
						final Order order = ordersByDate.get(0);
						selectedListener.onListItemSelected(order);
						getListView().setSelection(0);
					}
				} else if(name.equalsIgnoreCase("Sort by Order Status")) {
					Log.v(TAG, name);
					if (ordersByStatus == null) {
						ordersByStatus = app.shareDatabaseInstance().getOrders(app.getUserId(), "Sort by Order Status", false);
					}

					if(ordersByStatus != null && ordersByStatus.size() >0) {
						orderAdapter = new OrderAdapter(getActivity(),
								R.layout.list_item_order,
								ordersByStatus);
						setListAdapter(orderAdapter);
						final Order order = ordersByStatus.get(0);
						selectedListener.onListItemSelected(order);
						getListView().setSelection(0);
					}
				}
				return true;
			}
		});
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		listView.setOnItemClickListener(itemClickListener);
		LayoutInflater inflater = LayoutInflater.from(getActivity()); 
		View v = inflater.inflate(R.layout.order_list_header, null);
		v.setClickable(false);
		listView.addHeaderView(v);
		//		getOrders();
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.v(TAG,"onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG,"onResume");
		getOrders();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		this.menu = menu;
		menu.add(0,MENU_REFRESH,0,"Refresh").setIcon(R.drawable.btn_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case MENU_REFRESH:
			Log.v(TAG,"refresh");
			menu.findItem(MENU_REFRESH).setEnabled(false);
			activity.showProgress(true);
			refreshOrders();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void refreshOrders() {
		Log.v(TAG,"refreshorders");

		if(app.getUserId() != null) {
			Log.v(TAG,"rf");
			final List<Order> cacheOrders = app.shareDatabaseInstance().getCacheOrders(app.getUserId());

			if(cacheOrders != null && cacheOrders.size() > 0) {
				for(final Order order : cacheOrders) {
					List<Product> products = order.getProducts();
					if(!products.isEmpty()) {
						Log.v(TAG,"cache size      "+cacheOrders.size()+"  "+products.size()+"  "+order.getCustId()+" index  "+cacheOrders.lastIndexOf(order));
						ApiManager.getInstance(getActivity()).getAddOrder(getActivity(), products,
								app.getUserId(), order.getCustId(), order.isWarehouse(), 90, order.getOrderNotes(), new NewTecCallback<Object>() {

							public void onSuccess(Object object) {
								Log.v(TAG,"success "+cacheOrders.indexOf(order)+"  "+order.getOrderId());
								app.shareDatabaseInstance().deleteCacheOrderId(app.getUserId(), order.getOrderId());
								app.shareDatabaseInstance().deleteCacheProductId(app.getUserId(), order.getOrderId());
								NewOrder newOrder = (NewOrder) object;
								order.setOrderId(newOrder.getNewOrderId());
								order.setSaved(false);
								order.setSend(false);
								order.setWarehouse(true);
								Log.v(TAG,"new orderid  "+newOrder.getNewOrderId() + "  "+order.getOrderId());
								app.shareDatabaseInstance().insertOrder(order, app.getUserId());

								if(app != null) 	getOrders();
								if(cacheOrders.lastIndexOf(order) == cacheOrders.size() -1) loadOrders();
							}

							public void onError(Exception exception) {
								Log.v(TAG,"failedo");
								cacheOrders.clear();
								if(app != null) 	{
									Toast.makeText(getActivity(), "Unable to connect to server", Toast.LENGTH_SHORT).show();
									getOrders();
								}
								menu.findItem(MENU_REFRESH).setEnabled(true);
								activity.showProgress(false);
							}
						});
					} 
				} 
			} else {
				Log.v(TAG, "cache orders empty");
				if(app != null) loadOrders();
			}
		} else {
			activity.showProgress(false);
			menu.findItem(MENU_REFRESH).setEnabled(true);
		}
	}

	private void loadOrders() {
		if(app != null) {
			app.pauseDatabaseThread();
			ApiManager.getInstance(getActivity()).getOrderList(getActivity(), app.getUserId(), ApiManager.PRIORITY_REFRESH ,
					new NewTecCallback<Object>() {

				public void onSuccess(Object object) {
					Log.v(TAG, "SUCCESS");
					List<Order> orders = (List<Order>) object;
					if(app != null){
						updateOrders(orders);
					} 
				}

				public void onError(Exception exception) {
					menu.findItem(MENU_REFRESH).setEnabled(true);
					activity.showProgress(false);
					if(app != null) 	{
						Toast.makeText(getActivity(), "Unable to connect to server", Toast.LENGTH_SHORT).show();
						getOrders();
					}
					Log.v(TAG, "Failed because " + exception.toString());
				}
			});
		}
	}

	private void updateOrders(final List<Order> orders){
		onDatabaseUpdateCompletion updateCompletion = new onDatabaseUpdateCompletion() {
			public void databaseCompleted() {
				Log.v(TAG, " Database updation completed ");
				if(app != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							app.resumeDatabaseThread();
							getOrders();
							activity.showProgress(false);
							menu.findItem(MENU_REFRESH).setEnabled(true);
						}
					});
				} 
			}
		};
		DatabaseThread databaseThread = new DatabaseThread(getActivity().getApplicationContext(), updateCompletion);
		databaseThread.start();
		for (Order order : orders) {
			databaseThread.addJob(order);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v(TAG,"onAttach");
		try {
			selectedListener = (OrderSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ListItemSelectedListener in Activity");
		}
	}

	public interface OrderSelectedListener {
		public void onListItemSelected(Order order);
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			final Order _order = (Order) arg0.getAdapter().getItem(arg2);
			if ( order == null || OrderListFragment.this.order != _order) {
				OrderListFragment.this.order = _order;
				selectedListener.onListItemSelected(_order);
			}
		}
	};

	public void getOrders() {
		Log.v(TAG, "In getOrders ");
		if(app.getUserId() != null) {

			orderlist = app.shareDatabaseInstance().getOrders(app.getUserId(),
					"Sort by Order",false);

			orderAdapter = new OrderAdapter(getActivity(),
					R.layout.list_item_order, orderlist);
			if (orderlist != null && orderlist.size() > 0) {
				final Order order = orderlist.get(0);
				selectedListener.onListItemSelected(order);
				listView.setSelection(0);
			}
			setListAdapter(orderAdapter);
			setEmptyText("No Orders");
			if(orderAdapter.getCount() > 0) {
				Log.v(TAG, "adapter count  "+orderAdapter.getCount());
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.v(TAG,"onPause");
		menu.findItem(MENU_REFRESH).setEnabled(true);
		activity.showProgress(false);
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.v(TAG,"onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.v(TAG,"onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.v(TAG,"onDetach");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG,"onDestroy");
		menu.findItem(MENU_REFRESH).setEnabled(true);
		activity.showProgress(false);
		app = null;
	}
}
