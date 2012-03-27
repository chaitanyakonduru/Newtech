package com.logictree.supply.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

import com.logictree.newtec.handlers.AddCustomerHandler;
import com.logictree.newtec.handlers.AddOrderHandler;
import com.logictree.newtec.handlers.CustomerHandler;
import com.logictree.newtec.handlers.DepartmentHandler;
import com.logictree.newtec.handlers.EditOrderHanlder;
import com.logictree.newtec.handlers.MyAccountHandler;
import com.logictree.newtec.handlers.NewTecHandler;
import com.logictree.newtec.handlers.OrderHandler;
import com.logictree.newtec.handlers.ProductHandler;

/**
 * @author Srinivas
 */

public class NewTecResponseHandler {

	private static final String TAG = "NewTecResponseHandler";
	int reqCode;
	private Context context;
	
	
	private int priority;

	public NewTecResponseHandler(int reqCode, Context context , int priority) {
		this.reqCode = reqCode;
		this.context = context;
		this.priority = priority;
	}

	public Object Parse(InputStream inputStream, int len) {
		NewTecHandler defaultHandler = null;
		switch (reqCode) {
		case Util.REQ_CUSTOMERS:
			defaultHandler = new CustomerHandler(context , priority);
			break;
		case Util.REQ_DEPARTMENTS:
			defaultHandler = new DepartmentHandler(context , priority);
			break;
		case Util.REQ_MYACC:
			defaultHandler = new MyAccountHandler(context);
			break;
		case Util.REQ_PRODUCTS:
			defaultHandler = new ProductHandler(context, priority);
			break;
		case Util.REQ_LOGIN:
			defaultHandler = new MyAccountHandler(context);
			break;
		case Util.REQ_ORDERS:
			defaultHandler = new OrderHandler(context, priority);
			break;
		/*case Util.REQ_ORDER_INFO:
			defaultHandler = new OrderInfoHandler();
			break;*/
		case Util.REQ_EDIT_ORDER:
			defaultHandler = new EditOrderHanlder();
			break;
		case Util.REQ_ADD_ORDER:
			defaultHandler = new AddOrderHandler(context);
			break;
		case Util.REQ_ADD_CUSTOMER:
			defaultHandler = new AddCustomerHandler(context);
			break;
		default:
			break;
		}
		if (defaultHandler != null) {
			try {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(inputStream, defaultHandler);
				
				return defaultHandler.getContent();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			/*char[] buf = null;
			if (len < 1024) {
				buf = new char[len];
			} else {
				buf = new char[1024];
			}*/
			
			try {
				StringBuffer buffer = new StringBuffer();
				InputStreamReader stream = new InputStreamReader(inputStream);
				char[] buff = new char[len];
				/*int count = 0;
				int c = 0;
				while (c != -1) {
					c = stream.read(buf);
					if(c!= -1){
						buffer.append(buf);
						count += c;
					}
				}*/
				int cnt;
		        while ( ( cnt = stream.read( buff, 0, len - 1 ) ) > 0 )
		        {
		          buffer.append( buff, 0, cnt );
		        }
		         stream.close();
				Log.v(TAG, "buffer - " + buffer.toString());
				return buffer.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// private List<Order> getList(InputStream inputstream){
	// try {
	// OrderHandler handler = new OrderHandler();
	// SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	// SAXParser parser = saxParserFactory.newSAXParser();
	// parser.parse(inputstream,handler);
	// return handler.getOrderList();
	// } catch (ParserConfigurationException e) {
	// e.printStackTrace();
	// } catch (SAXException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	//
	// }

}
