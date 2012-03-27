package com.logictree.supply.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.logictree.supply.R;
import com.logictree.supply.activities.CustomerListActivity;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.adapter.CustomerAdapter;
import com.logictree.supply.models.AddCustomer;
import com.logictree.supply.models.Customer;
import com.logictree.supply.network.ApiManager;
import com.logictree.supply.network.NewTecCallback;

public class CustomerListFragment extends ListFragment implements
OnItemClickListener {

	private static final int MENU_NEWCUSTOMER = 106;
	private static final int MENU_REFRESH  = 107;
	//	private static final String TAG = "CustomerListFragment";
	private CustomerSelectedListener selectedListener;
	private ListView listView;
	private CustomerAdapter customerAdapter;
	private List<Customer> customerList;
	protected String TAG="CustomerListFragment";
	private Menu menu;
	private CustomerListActivity activity;
	private NewTecApp app;
	private int mCurCheckPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		app = (NewTecApp) getActivity().getApplication();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setItemChecked(0, true);
		listView.setBackgroundResource(R.drawable.bg_order_list);
		customerList = new ArrayList<Customer>();
		customerAdapter = new CustomerAdapter(getActivity(),
				R.layout.list_item_customer, customerList);
		
		listView.setOnItemClickListener(this);
		activity = (CustomerListActivity)getActivity();
		if (savedInstanceState != null) {
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
		}
		setListAdapter(customerAdapter);
		getCustomers();
		setEmptyText("No Customers");
	}
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		this.menu = menu;
		menu.add(0, MENU_NEWCUSTOMER, 0, "New Customer").setIcon(R.drawable.btn_newcustomer).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0,MENU_REFRESH,0,"Refresh").setIcon(R.drawable.btn_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case MENU_REFRESH:
			Log.v(TAG,"refresh");
			activity.showProgress(true);
			menu.findItem(MENU_REFRESH).setEnabled(false);
			refreshCustomers();
			break;
		case MENU_NEWCUSTOMER:
			showDialog(getActivity());
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showDialog(final Context context) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.new_customer,null);
		builder.setTitle("Add Customer");
		builder.setView(view);

		final Spinner statusSpinner = (Spinner) view.findViewById(R.id.newCustomer_status);
		final Spinner priceSpinner = (Spinner) view.findViewById(R.id.newCustomer_price);
		final CheckBox msaCheckbox = (CheckBox) view.findViewById(R.id.newCustomer_msa);
		final List<String> price_list = new ArrayList<String>();
		price_list.add("Std Price");
		price_list.add("Level 1 Price($)");
		price_list.add("Level 2 Price($)");
		price_list.add("Level 3 Price($)");
		price_list.add("Level 4 Price($)");

		ArrayAdapter<String> priceAdapter = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item, price_list);
		priceAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		priceSpinner.setAdapter(priceAdapter);
		priceSpinner.setScrollBarStyle(Spinner.FOCUS_DOWN);

		final List<String> list = new ArrayList<String>();
		list.add("Active");
		list.add("Inactive");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		statusSpinner.setAdapter(adapter);
		statusSpinner.setScrollBarStyle(Spinner.FOCUS_DOWN);

		final EditText custnameView = (EditText) view.findViewById(R.id.newCustomer_custname);
		final EditText businessnameView = (EditText) view.findViewById(R.id.newCustomer_businessname);
		final EditText licView = (EditText) view.findViewById(R.id.newCustomer_lic);
		final EditText addressView = (EditText) view.findViewById(R.id.newCustomer_address);
		final EditText cityView = (EditText) view.findViewById(R.id.newCustomer_city);
		final EditText stateView = (EditText) view.findViewById(R.id.newCustomer_state);
		final EditText zipcodeView = (EditText) view.findViewById(R.id.newCustomer_zipcode);
		final EditText phnoView = (EditText) view.findViewById(R.id.newCustomer_phno);

		builder.setPositiveButton("Cancel", null);
		builder.setNegativeButton("Submit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String custname = custnameView.getText().toString();
				String businessname = businessnameView.getText().toString();
				String lic = licView.getText().toString();
				String address = addressView.getText().toString();
				String city = cityView.getText().toString();
				String state = stateView.getText().toString();
				String zipcode = zipcodeView.getText().toString();
				String phoneNo = phnoView.getText().toString();
				String price = String.valueOf((priceSpinner.getSelectedItemPosition()+1));
				String status = statusSpinner.getSelectedItem().toString();
				Log.v(TAG,""+statusSpinner.getSelectedItemPosition());
				boolean isMsaChecked = msaCheckbox.isChecked();
				if((custname != null && custname.length()>0) && (businessname != null && businessname.length()>0)&& (address != null && address.length() >0) && (city != null && city.length()>0) && (state != null && state.length() >0) && (phoneNo != null && phoneNo.length() >0)) {
					Log.v(TAG, custname);
					//					Customer customer = new Customer(custId, custname, phoneNo, lic, address, city, state, zipcode,isMsaChecked, price,status, ""+System.currentTimeMillis() , ""+System.currentTimeMillis(),true);
					//					addNewCustomer(customer);
					builder.create().dismiss();
					addCustomer(custname, businessname, phoneNo, lic, address, city, state, zipcode,isMsaChecked, price,status);
				} else {
					Toast.makeText(context, "Please enter all details of a customer", Toast.LENGTH_SHORT).show();
				}
			}
		});
		builder.create().show();
	}

	private void addCustomer(final String custname, final String businessname, final String phoneNo,
			final String lic, final String address, final String city, final String state,
			final String zipcode, final boolean isMsaChecked, final String price,
			final String status) {
		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("Processesing..");
		progressDialog.show();

		NewTecCallback<Object> callback = new NewTecCallback<Object>() {
			public void onSuccess(Object object) {
				Log.v(TAG,"Success");
				progressDialog.dismiss();
				List<AddCustomer> addCustomers = (List<AddCustomer>) object;

				for(AddCustomer addCustomer : addCustomers) {
					String customerId = addCustomer.getCustomerId();
					Log.v(TAG, customerId);
					Customer customer = new Customer(customerId, custname, businessname,phoneNo, lic, address, city, state, zipcode,isMsaChecked, price,status, ""+System.currentTimeMillis() , ""+System.currentTimeMillis(),false);
					customer.setCustomername(custname);
					app.shareDatabaseInstance().insertCustomer(customer, app.getUserId().trim());
					getCustomers();
					Toast.makeText(getActivity(), "Customer added successfully.", Toast.LENGTH_SHORT).show();
				}
			}
			public void onError(Exception exception) {
				Log.v(TAG,"Failed   "+exception.getMessage());
				progressDialog.dismiss();
				String custId = "S"+app.shareDatabaseInstance().getLastInsertCustomerId();
				Log.v(TAG, custId+"  "+custname);
				Customer customer = new Customer(custId, custname, businessname, phoneNo, lic, address, city, state, zipcode,isMsaChecked, price,status, ""+System.currentTimeMillis() , ""+System.currentTimeMillis(),true);
				customer.setSaved(true);
				customer.setCustomername(custname);
				app.shareDatabaseInstance().insertCustomer(customer, app.getUserId().trim());
				getCustomers();
				Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), "Unable to connect to server", Toast.LENGTH_SHORT).show();
			}
		};
		if(app.getUserId() != null) {
			ApiManager apiManager = ApiManager.getInstance(getActivity());
			apiManager.addCustomer(getActivity(),custname,
					"",  businessname, lic,
					address, city, state,
					zipcode, phoneNo,
					isMsaChecked, price,
					status, app.getUserId(), 100,
					callback);
		}
	}

	private void refreshCustomers() {
		/*if(!com.logictree.supply.network.Util.isNetworkAvailable(getActivity())){
			activity.showProgress(false);
			menu.findItem(MENU_REFRESH).setEnabled(true);
			return;
		}*/
		if(app.getUserId() != null) {
			List<Customer> cacheCustomers = app.shareDatabaseInstance().getCacheCustomers(app.getUserId());
			if(cacheCustomers != null && cacheCustomers.size() > 0) {
				for(final Customer customer: cacheCustomers) {
					final ApiManager apiManager = ApiManager.getInstance(getActivity());
					apiManager.addCustomer(getActivity(),customer.getCustomername(),
							"", customer.getBusinessName(), customer.getAddress(),
							customer.getLicence(), customer.getCity(), customer.getState(),
							customer.getZipcode(), customer.getPhoneNo(),
							customer.isMsaFlagOn(), customer.getPrice_level(),
							customer.getStatus(), app.getUserId(), 100,
							new NewTecCallback<Object>() {

						public void onSuccess(Object object) {
							Log.v(TAG,"customer id  "+customer.getCustomerId());
						    app.shareDatabaseInstance().deleteCacheCustomerId(app.getUserId(), customer.getCustomerId());
						   
						    List<AddCustomer> addCustomers = (List<AddCustomer>) object;

							for(AddCustomer addCustomer : addCustomers) {
								String customerId = addCustomer.getCustomerId();
								customer.setCustomerId(customerId);
								customer.setSaved(false);
								Log.v(TAG, customerId+"  "+customer.getCustomerId());
								app.shareDatabaseInstance().insertCustomer(customer, app.getUserId().trim());
								if(app != null) getCustomers();
							}
							
//						    if(app != null) getCustomers();
//							menu.findItem(MENU_REFRESH).setEnabled(true);
//							activity.showProgress(false);
						    if(app != null) loadCustomers();
						}
						
						public void onError(Exception exception) {
							Log.v(TAG,"Failed"); 
							if(app != null) getCustomers();
							activity.showProgress(false);
							menu.findItem(MENU_REFRESH).setEnabled(true);
						}
					});
				}
			} else {
				Log.v(TAG, "cache customers empty");
				 if(app != null) loadCustomers();
//				if(app != null) getCustomers();
//				activity.showProgress(false);
//				menu.findItem(MENU_REFRESH).setEnabled(true);
			}
		}
	}

	private void loadCustomers() {
		if(app.getUserId() != null) {
			ApiManager.getInstance(getActivity()).getCustomerList(getActivity(), 90, new NewTecCallback<Object>() {

				public void onSuccess(Object object) {
					if(app != null) getCustomers();
					activity.showProgress(false);
					menu.findItem(MENU_REFRESH).setEnabled(true);
				}

				public void onError(Exception exception) {
					if(app != null) getCustomers();
					activity.showProgress(false);
					menu.findItem(MENU_REFRESH).setEnabled(true);
				}
			});
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		activity.showProgress(false);
		menu.findItem(MENU_REFRESH).setEnabled(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			selectedListener = (CustomerSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ListItemSelectedListener in Activity");
		}
	}

	public interface CustomerSelectedListener {
		public void onListItemSelected(Customer customer);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		getListView().setItemChecked(arg2, true);
		Log.v(TAG, customerAdapter.getItem(arg2).getCustomerId());
		selectedListener.onListItemSelected(customerAdapter.getItem(arg2));
	}
	
	private void getCustomers() {
		Log.v(TAG,"getCustomers");
		customerList = app.shareDatabaseInstance().getCustomers(app.getUserId().trim());
		if(customerList != null && customerList.size() > 0){
			customerAdapter = new CustomerAdapter(getActivity(),
					R.layout.list_item_customer, customerList);
			getListView().setSelection(0);
			setListAdapter(customerAdapter);
			selectedListener.onListItemSelected(customerList.get(0));
			Log.v(TAG, "adapter count  "+customerAdapter.getCount());
		} else {
			
			selectedListener.onListItemSelected(null);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG,"ondestroy");
		activity.showProgress(false);
		menu.findItem(MENU_REFRESH).setEnabled(true);
		app = null;
	}
}
