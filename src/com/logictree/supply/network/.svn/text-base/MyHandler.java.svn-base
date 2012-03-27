package com.logictree.supply.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

 class MyHandler extends Handler{
	
	private static final int SUCCESS = 1;
	private static final int FAILURE = 2;
	private NewTecCallback<Object> callback = null;
	
	public MyHandler(NewTecCallback<Object> callback) {
		this.callback = callback;
	}
	
	@Override
	public void handleMessage(Message msg) {
		Log.v("MyHandler", msg.obj.toString());
		switch (msg.what) {
			case SUCCESS:
				Log.v("MyHandler", msg.obj.toString());
				if(callback != null){ 
					callback.onSuccess(msg.obj);
				}
				break;
			case FAILURE:
				Log.v("MyHandler Failure", msg.obj.toString());
				if(callback != null){
					callback.onError((Exception) msg.obj);
				}
				break;	

			default:
				break;
		}
	}
}