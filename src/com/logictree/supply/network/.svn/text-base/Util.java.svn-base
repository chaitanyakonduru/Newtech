package com.logictree.supply.network;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.logictree.supply.models.Product;


/**
 * @author Srinivas
 */
public class Util {

	public static final int REQ_LOGIN = 1;
	public static final int REQ_MYACC = 2;
	public static final int REQ_PRODUCTS=3;
	public static final int REQ_ORDERS =4;
	public static final int REQ_CUSTOMERS=5;
	public static final int REQ_DEPARTMENTS=6;
	public static final int REQ_ADD_CUSTOMER=7;
	public static final int REQ_ADD_ORDER=8;
	public static final int REQ_EDIT_ORDER=9;
//	public static final int REQ_ORDER_INFO=10;
	
	public static final int GET_SUCCESS = 200;
	public static final int PUT_SUCCESS = 202;
	public static final int POST_SUCCESS = 301;
	public static final int BAD_REQUEST = 400;
	public static final int NO_SUCH = 404;
	public static final int NOT_AUTHORIZED = 401;
	public static final int UNPROCESSABLE_ENTITY = 422;
	
	public static final char DELIM_HYPHEN= '-';
	public static final char DELIM_COMMA= ',';
	private static final String TAG = "Util";
	
	public static String getOrderPostString(List<Product> products) {
		StringBuilder builder = new StringBuilder();
		
		/*if(products != null && products.isEmpty()){
			return "0-0";
		}*/
		
		for(Product product : products){
			
			builder.append(product.getProductId());
			builder.append(DELIM_HYPHEN);
			builder.append(product.getQuantity());
			builder.append(DELIM_COMMA);
		}
		String string = builder.toString();
		int lastIndex = string.lastIndexOf(DELIM_COMMA);
		if(lastIndex != -1){
			return string.substring(0,lastIndex);	
		}
		return string;
	}
	
	public static boolean isNetworkAvailable(Context context) {
		Log.v(TAG,"isNetworkAvailable");
	    ConnectivityManager cm = (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null, otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	
}
