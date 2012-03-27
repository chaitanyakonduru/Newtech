package com.logictree.supply.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.adapter.NewOrderProductAdapter;
import com.logictree.supply.models.Product;
import com.logictree.supply.models.Util;
import com.logictreeit.supply.listeners.OrderAddListener;

public class NewOrderListFragment extends ListFragment implements OnItemClickListener, OrderAddListener {

	protected static final String TAG = "NeworderListFragment";
	private ProductSelectedListener selectedListener;
	private ListView listView;
	private NewOrderProductAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new NewOrderProductAdapter(getActivity(), R.layout.list_item_product, new ArrayList<Product>());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setBackgroundResource(R.drawable.bg_order_list);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(this);
		//		if(selectedListener != null) selectedListener.onListItemSelected(Product.getDefault().get(0),"");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setListAdapter(mAdapter);
		getProducts("All");
		setEmptyText("No Products");
	}

	//	@Override
	//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	//			Bundle savedInstanceState) {
	//		View v = inflater.inflate(R.layout.listview, container);
	//		listView =  (ListView) v.findViewById(android.R.id.list);
	//		empty = (TextView) v.findViewById(android.R.id.text1);
	//		return v;
	//	}

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
		NewOrderListFragment productListFragment = (NewOrderListFragment) getFragmentManager().findFragmentById(R.id.neworder_product_list_fragment);
		final Product product = (Product) arg0.getAdapter().getItem(arg2);
		if(productListFragment != null) {
			if(!Util.sQtyViewcache.containsKey(product.getProductId())){
			  Log.v(TAG, " product id - "+product.getProductId());
				Util.orderProduct(getActivity(), product, this);
			}else{
				Toast.makeText(NewOrderListFragment.this.getActivity(), "This product is placed  already. ", Toast.LENGTH_SHORT).show();
			}
		} else {
			selectedListener.onListItemSelected(product, "1");
		}
	}

	public void getProducts(final String deptname){
		Log.v(TAG,"getProducts");
		final NewTecApp app = (NewTecApp) getActivity().getApplication();
		List<Product> productList = app.shareDatabaseInstance().getActiveProducts(app.getUserId());
		if(productList != null && productList.size() >0) {
			Log.v(TAG,""+productList.size());
			if(deptname!=null && deptname.length()>0) {
				Log.v(TAG,deptname);
				List<Product> deptProductList = filterDeptProductList(deptname,productList);
				mAdapter.clear();
				if(deptProductList != null && deptProductList.size() > 0){
					mAdapter = new NewOrderProductAdapter(getActivity(), R.layout.list_item_product, deptProductList);
					listView.setSelection(0);
					setListAdapter(mAdapter);
					if(selectedListener !=null)	selectedListener.onListItemSelected(deptProductList.get(0), "1");
				} else {
					if(selectedListener !=null)	selectedListener.onListItemSelected(null, "1");
				}
			} 	
		} else if(productList.isEmpty()) {
			Log.v(TAG,"empty");
		}
	}

	private List<Product> filterDeptProductList(String deptname, List<Product> depProducts) {
		List<Product> deptProducts = new ArrayList<Product>();

		for(Product product : depProducts) {
			if((product.getDepartmentName().toString()).equals(deptname)) {
				deptProducts.add(product);
			} else if((deptname.equalsIgnoreCase("All"))) {
				deptProducts.add(product);
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
	public void onPause() {
		super.onPause();
		Log.v(TAG,"onPause");
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
	}
}
