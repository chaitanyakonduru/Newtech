package com.logictree.newtec.handlers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.logictree.supply.models.OrderInfo;

public class OrderInfoHandler extends NewTecHandler {
	
	private StringBuffer buffer;
	private boolean in = false;
	private OrderInfo orderInfo;
	private List<OrderInfo> orderInfos;
	
//	<orders><order>
//	<order_date>11/18/2011</order_date>
//	<amount>20</amount>
//	<customer_id>7</customer_id>
//	<customer_name>Neelima Paruchuri</customer_name>
//	<orderby>John</orderby></order></orders>
//	<products>
//	<product><id>131</id>
//	<upc_code>12756458</upc_code>
//	<product_description>DOPEN Winter Pouches</product_description>
//	<quantity>1</quantity>
//	<total_unit>1</total_unit>
//	<srp>10</srp>
//	<sub_retail>10</sub_retail>
//	<price>20</price>
//	<total_cost>20</total_cost></product></products>
	
	public OrderInfoHandler() {
		buffer = new StringBuffer();
		orderInfos = new ArrayList<OrderInfo>();
	}
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}
	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		if(in) {
			buffer.append(ch, start, length);
		}
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		if(localName.equalsIgnoreCase("order")){
			orderInfo = new OrderInfo();
			buffer.setLength(0);
		} else if("order_date".equals(localName)){
			in = true;
			buffer.setLength(0);
		}else if("amount".equals(localName)){
			in = true;
			buffer.setLength(0);
		}else if("customer_id".equals(localName)){
			in = true;
			buffer.setLength(0);
		}else if("customer_name".equals(localName)){
			in = true;
			buffer.setLength(0);
		}else if("orderby".equals(localName)){
			in = true;
			buffer.setLength(0);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
		if(localName.equalsIgnoreCase("order")){
			orderInfos.add(orderInfo);
		}else if("order_date".equals(localName)){
			in = false;
			orderInfo.setOrderDate(buffer.toString());
		}else if("amount".equals(localName)){
			in = false;
			orderInfo.setAmount(buffer.toString());
		}else if("customer_id".equals(localName)){
			in = false;
			orderInfo.setCustId(buffer.toString());
		}else if("customer_name".equals(localName)){
			in = false;
			orderInfo.setCustomerName(buffer.toString());
		}else if("orderby".equals(localName)){
			in = false;
			orderInfo.setOrderBy(buffer.toString());
		}else if("products".equals(localName)){
			
		}else if("product".equals(localName)){
			
		}
	}
	
	/*public Object getContent() {
		return orders;
	}*/
}