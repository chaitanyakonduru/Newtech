package com.logictree.newtec.handlers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.logictree.supply.models.AddCustomer;

import android.content.Context;

public class AddCustomerHandler extends NewTecHandler {

	private List<AddCustomer> addCustomers;
	private StringBuffer buffer;
	private AddCustomer addCustomer;
	private boolean in = false;
	
	public AddCustomerHandler(Context context) {
		addCustomers = new ArrayList<AddCustomer>();
		buffer = new StringBuffer();
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
		if(localName.equalsIgnoreCase("addcustomers")) {
			addCustomer = new AddCustomer();
			buffer.setLength(0);
		} else	if(localName.equalsIgnoreCase("success")) {
			in = true;
			buffer.setLength(0);
		} else if(localName.equalsIgnoreCase("customer_id")) {
			in = true;
			buffer.setLength(0);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if(localName.equalsIgnoreCase("addcustomers")) {
			addCustomers.add(addCustomer);
		} else 	if(localName.equalsIgnoreCase("success")) {
			in = false;
			addCustomer.setSuccess(buffer.toString());
		} else if(localName.equalsIgnoreCase("customer_id")) {
			in = false;
			addCustomer.setCustomerId(buffer.toString());
		}
	}
	
	public Object getContent() {
		return addCustomers;
	}

}
