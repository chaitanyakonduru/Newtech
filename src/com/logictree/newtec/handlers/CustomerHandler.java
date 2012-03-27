package com.logictree.newtec.handlers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.content.Context;

import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.models.Customer;
import com.logictree.supply.network.DatabaseThread;

public class CustomerHandler extends NewTecHandler {
	private List<Customer> customers;
	private StringBuffer buffer;
	private boolean in = false;
	private Customer customer;
	private NewTecApp app;
	private int priority;

	public CustomerHandler(Context context, int priority) {
		customers = new ArrayList<Customer>();
		buffer = new StringBuffer();
		this.priority = priority;
		this.app = (NewTecApp) context.getApplicationContext();
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
		if (in) {
			buffer.append(ch, start, length);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		if (localName.equalsIgnoreCase("customer")) {
			customer = new Customer();
			buffer.setLength(0);
		} else if ("id".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("name".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("business_name".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("phone".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("licence".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("address".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("city".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("state".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("zipcode".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("msa".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("price_level".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("status".equals(localName)) {
			in = true;
			buffer.setLength(0);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		super.endElement(uri, localName, qName);

		if (localName.equalsIgnoreCase("customer")) {
			customers.add(customer);
			customer.setPriority(priority);
			insertCustomer(customer);
		} else if ("id".equals(localName)) {
			in = false;
			customer.setCustomerId(buffer.toString());
		}  else if ("name".equals(localName)) {
			in = false;
			customer.setCustomername(buffer.toString());
		} else if ("business_name".equals(localName)) {
			in = false;
			customer.setBusinessName(buffer.toString());
		} else if ("phone".equals(localName)) {
			in = false;
			customer.setPhoneNo(buffer.toString());
		} else if ("licence".equals(localName)) {
			in = false;
			customer.setLicence(buffer.toString());
		} else if ("address".equals(localName)) {
			in = false;
			customer.setAddress(buffer.toString());
		} else if ("city".equals(localName)) {
			in = false;
			customer.setCity(buffer.toString());
		} else if ("state".equals(localName)) {
			in = false;
			customer.setState(buffer.toString());
		} else if ("zipcode".equals(localName)) {
			in = false;
			customer.setZipcode(buffer.toString());
		} else if ("msa".equals(localName)) {
			in = false;
			customer.setMsaFlagOn(buffer.toString().equals("Yes") ? true
					: false);
		} else if ("price_level".equals(localName)) {
			in = false;
			customer.setPrice_level(buffer.toString());
		} else if ("status".equals(localName)) {
			in = false;
			customer.setStatus(buffer.toString());
		}
	}

	private void insertCustomer(final Customer customer) {
		if (customer != null) {
			if(app.getUserId() != null) {
				DatabaseThread databaseThread  = app.shareDatabaseThread();
				databaseThread.addJob(customer);
			}
		}
	}

	@Override
	public Object getContent() {
		return customers;
	}
}
