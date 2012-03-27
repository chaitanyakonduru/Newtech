package com.logictree.supply.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.net.http.AndroidHttpClient;
import android.os.Message;
import android.util.Log;

public class HttpConn {

	private static final String TAG = "HttpConn";
	public static String POST = "POST";
	public static String GET = "GET";

	public static void call(String urlString, String method, String payload,
			
			NewTecResponseHandler responseHandler, MyHandler handler) {

		AndroidHttpClient androidHttpClient = null;
		InputStream inputStream = null;

		try {
			HttpGet httpGet = new HttpGet(urlString);
			androidHttpClient = AndroidHttpClient.newInstance("newtec");
			HttpParams httpParams = androidHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 8 *1000);
			HttpConnectionParams.setSoTimeout(httpParams, 8 * 1000);

			HttpResponse response = androidHttpClient.execute(httpGet);
			int responseCode = response.getStatusLine().getStatusCode();

			StringBuffer buffer = new StringBuffer();
			int len = 256;
			if (responseCode == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
				
				Object responseObject = responseHandler.Parse(inputStream, len);
				if (responseObject != null) {
					Log.i(TAG, "Response  -- " + responseObject.toString());
					Message.obtain(handler, SUCCESS, responseObject)
							.sendToTarget();
				} else {
					Message.obtain(
							handler,
							FAILURE,
							new NewTecException("Network Error",
									"Sorry ,Unable to fetch data"))
							.sendToTarget();
				}
			}

			Log.v(TAG, "" + buffer.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Message.obtain(handler, FAILURE, e).sendToTarget();
		} catch (IOException e) {
			e.printStackTrace();
			Message.obtain(handler, FAILURE, e).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
			Message.obtain(handler, FAILURE, e).sendToTarget();
		} finally {
			if (androidHttpClient != null) {
				androidHttpClient.close();
				androidHttpClient = null;
			}
		}
	}

	public void login(NewTecCallback<String> string) {

	}

	private static final int SUCCESS = 1;
	private static final int FAILURE = 2;

}
