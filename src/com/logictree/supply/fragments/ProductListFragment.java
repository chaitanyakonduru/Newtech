package com.logictree.supply.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.activities.PrductListActivity;
import com.logictree.supply.adapter.ProductAdapter;
import com.logictree.supply.models.Product;
import com.logictree.supply.network.ApiManager;
import com.logictree.supply.network.NewTecCallback;
import com.logictreeit.supply.listeners.OrderAddListener;

public class ProductListFragment extends ListFragment implements OnItemClickListener, OrderAddListener {

	protected static final String TAG = "ProductListFragment";
	private ProductSelectedListener selectedListener;
	private ListView listView;
	private ProductAdapter mAdapter;
	//	private boolean isOrderScreen;
	private static final int MENU_REFRESH = 104;
	private Menu menu;
	private PrductListActivity activity;
	private NewTecApp app;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ProductAdapter(getActivity(), R.layout.list_item_product, new ArrayList<Product>());
		setHasOptionsMenu(true);
		activity = (PrductListActivity) getActivity();
		app = (NewTecApp) getActivity().getApplicationContext();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setItemChecked(0, true);
		listView.setBackgroundResource(R.drawable.bg_order_list);
		setListAdapter(mAdapter);
		listView.setOnItemClickListener(this);
		getProducts("All");
		setEmptyText("No Products");
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		this.menu = menu;
		menu.add(0,MENU_REFRESH,0,"Refresh").setIcon(R.drawable.btn_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case MENU_REFRESH:
			Log.v(TAG,"refresh");
			menu.findItem(MENU_REFRESH).setEnabled(false);
			activity.showProgress(true);
			refreshProducts();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void refreshProducts() {
		/*if(!Util.isNetworkAvailable(getActivity())){
			menu.findItem(MENU_REFRESH).setEnabled(true);
			activity.showProgress(false);
			return;
		}*/

		if(app.getUserId() != null) {
			ApiManager.getInstance(getActivity()).getProductList(getActivity(), 90,
					new NewTecCallback<Object>() {

				public void onSuccess(Object object) {
					Log.v(TAG, "SUCCESS");
					if(app != null) getProducts("All");
					menu.findItem(MENU_REFRESH).setEnabled(true);
					activity.showProgress(false);
				}

				public void onError(Exception exception) {
					Log.v(TAG, "Failed because " + exception.toString());
					menu.findItem(MENU_REFRESH).setEnabled(true);
					activity.showProgress(false);
				}
			});
			
			ApiManager.getInstance(getActivity()).getDepartmentList(getActivity(), 80, new NewTecCallback<Object>() {

				public void onSuccess(Object object) {
				}
				public void onError(Exception exception) {
				}
			});

		} else {
			activity.showProgress(false);
			menu.getItem(MENU_REFRESH).setEnabled(true);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			selectedListener = (ProductSelectedListener) activity;
		} catch (ClassCastException e) {
			//			throw new ClassCastException(activity.toString()
			//					+ " must implement ListItemSelectedListener in Activity");
		}
	}

	public interface ProductSelectedListener {
		public void onListItemSelected(Product product, String quantity);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ProductListFragment productListFragment = (ProductListFragment) getFragmentManager().findFragmentById(R.id.product_list_fragment);
		final Product product = (Product) arg0.getAdapter().getItem(arg2);
		if(productListFragment != null) {
			selectedListener.onListItemSelected(product, "1");
		}
	}

	public void getProducts(final String deptname){
		Log.v(TAG,"getProducts");
		if(app.getUserId() != null) {
			List<Product> productList = app.shareDatabaseInstance().getProducts(app.getUserId(), null);
			if(productList != null) {
				if(deptname!=null && deptname.length()>0) {
					List<Product> deptProductList = filterProductList(deptname,productList);
					mAdapter.clear();
					if(deptProductList != null && deptProductList.size() > 0){
						mAdapter = new ProductAdapter(getActivity(), R.layout.list_item_product, deptProductList);
						setListAdapter(mAdapter);
						if(selectedListener !=null)	selectedListener.onListItemSelected(deptProductList.get(0), "1");
						listView.setSelection(0);
						Log.v(TAG, "adapter count   "+mAdapter.getCount());
					} else {
						Log.v(TAG,"empty");
						if(selectedListener !=null)	selectedListener.onListItemSelected(null, "1");
					}
				} 
			}
		}
	}

	private List<Product> filterProductList(String deptname, List<Product> depProducts) {
		List<Product> deptProducts = new ArrayList<Product>();
		if(depProducts != null && depProducts.size() > 0) {
			for(Product product : depProducts) {
				if((product.getDepartmentName().toString().equals(deptname))) {
					deptProducts.add(product);
				} else if((deptname.equalsIgnoreCase("All"))) {
					deptProducts.add(product);
				}
			}
		}
		return deptProducts;
	}

	public void onOrderAdd(Product product) {
		NewOrderFragment fragment = (NewOrderFragment) getFragmentManager().findFragmentById(R.id.fragment3);
		if(product != null) {
			fragment.updateTable(product); 
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG,"onDestroy");
		app = null;
	}

	/*public void refreshProducts(List<Product> products) {
		Log.v(TAG,"refersh products");
		if(products != null && products.size() > 0){
			mAdapter = new ProductAdapter(getActivity(), R.layout.list_item_product, products);
			mAdapter.notifyDataSetChanged();
			setListAdapter(mAdapter);
			if(selectedListener !=null)	selectedListener.onListItemSelected(products.get(0), "1");
			listView.setSelection(0);
		} 
	}*/
}