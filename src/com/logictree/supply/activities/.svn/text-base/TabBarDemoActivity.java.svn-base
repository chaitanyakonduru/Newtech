package com.logictree.supply.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.logictree.supply.R;
import com.logictree.supply.fragments.CustomerListFragment;
import com.logictree.supply.fragments.OrderListFragment;
import com.logictree.supply.fragments.ProductListFragment;

public class TabBarDemoActivity extends Activity  {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(0,ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("One").setTabListener(listener),0);
		actionBar.addTab(actionBar.newTab().setText("Two").setTabListener(listener),1);
		actionBar.addTab(actionBar.newTab().setText("Three").setTabListener(listener),2);

//		ProductListFragment fragment1 = new ProductListFragment();
		//		FragmentTransaction ft = getFragmentManager().beginTransaction();
		//			ft.replace(R.id.container1, fragment1);
		//			ft.commit();
	}


	TabListener listener = new TabListener() {

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			int pos = tab.getPosition();
			switch (pos) {
			case 0:
				ProductListFragment fragment1 = new ProductListFragment();
				ft.replace(R.id.container1, fragment1);
				//			ft.commit();
				break;
			case 1:
				CustomerListFragment fragment2 = new CustomerListFragment();
				ft.replace(R.id.container1,fragment2);
				//			ft.commit();
				break;
			case 2:
				OrderListFragment fragment3 = new OrderListFragment();
				ft.replace(R.id.container1,fragment3);
				//			ft.commit();
				break;
			default:
				break;
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}
	};

}