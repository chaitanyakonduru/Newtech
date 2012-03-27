package com.logictree.supply.models;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class Parser {
	
	private static final String TAG = "Parser";

	public static void parse(InputStream is) {
//		        Order order = null;
		        try {
		            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		            parser.setInput(is, "UTF-8");
		            int eventType = parser.getEventType();
		            while(eventType != XmlPullParser.END_DOCUMENT) {
		                switch(eventType) {
		                    case XmlPullParser.START_DOCUMENT:
//		                        order = new Order();
		                        break;
		 
		                    case XmlPullParser.START_TAG:
//		                        String tagName = parser.getName();
		                        Log.i(TAG,"START_TAG"+parser.getName());
		                        Log.i(TAG,"Attribute Name"+parser.getAttributeValue(null,"category"));
		                        
		                        break;
		                    case XmlPullParser.END_TAG:
		                    	Log.i(TAG,"END_TAG"+parser.getName());
		                    	break;
		                    case XmlPullParser.TEXT:
		                    	 Log.i(TAG,"TEXT");
		                         String output = parser.getText();
		                         Log.i("value",""+output);
		                    	break;
		                }
		                eventType = parser.next();
		            }
		        } catch (XmlPullParserException e) {
//		           	order = null;
		        	e.printStackTrace();
		        } catch (IOException e) {
//		            order = null;
		        	e.printStackTrace();
		        }
		 
//		        return order;
		    }


}
