package com.logictree.supply.fragments;

import com.logictree.supply.R;
import com.logictree.supply.models.Product;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductDetailFragment extends Fragment {

	private static final String TAG = "ProductDetailFragment";
	private TextView productIdView;
	private TextView deptNameView;
	private TextView upcCodeView;
	private TextView unitView;
	private TextView srpView;
	private TextView priceView;
	private TextView statusView;
	private LinearLayout productdetailsLayout;
	private TextView emptytextView;

	public static ProductDetailFragment newInstance(){
		ProductDetailFragment f = new ProductDetailFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_product_detail, container);
		productdetailsLayout = (LinearLayout)view.findViewById(R.id.product_details_layout);
		productIdView = (TextView)view.findViewById(R.id.product_detail_id);
		deptNameView = (TextView)view.findViewById(R.id.product_detail_deptName);
		upcCodeView = (TextView)view.findViewById(R.id.product_detail_upcCode);
		unitView = (TextView)view.findViewById(R.id.product_detail_unit);
		srpView = (TextView)view.findViewById(R.id.product_detail_srp);
		priceView = (TextView)view.findViewById(R.id.product_detail_price);
		statusView = (TextView)view.findViewById(R.id.product_detail_status);
		emptytextView = (TextView)view.findViewById(R.id.products_empty);
		return view;
	}


	public void update(Product product) {
		if(product != null) {
			emptytextView.setVisibility(View.GONE);
			productdetailsLayout.setVisibility(View.VISIBLE);
			productIdView.setText(product.getProductId());
			deptNameView.setText(product.getDepartmentName());
			upcCodeView.setText(product.getUpcCode());
			unitView.setText(product.getUnit());
			srpView.setText(product.getSuggestedPrice());
			priceView.setText(product.getCost());
			statusView.setText(product.getStatus());
		} else {
			Log.v(TAG,"null");
			emptytextView.setVisibility(View.VISIBLE);
			productdetailsLayout.setVisibility(View.GONE);
		}
	}
}
