package com.logictree.newtec.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.content.Context;

import com.logictree.supply.models.NewOrder;

public class AddOrderHandler extends NewTecHandler {
	
	private StringBuffer buffer;
	private NewOrder newOrder;
	private boolean in = false;
	
	public AddOrderHandler(Context context) {
		newOrder =new NewOrder();
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
		if(localName.equalsIgnoreCase("neworder")) {
			newOrder = new NewOrder();
			buffer.setLength(0);
		} else	if(localName.equalsIgnoreCase("success")) {
			in = true;
			buffer.setLength(0);
		} else if(localName.equalsIgnoreCase("order_id")) {
			in = true;
			buffer.setLength(0);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if(localName.equalsIgnoreCase("neworder")) {
		} else 	if(localName.equalsIgnoreCase("success")) {
			in = false;
			newOrder.setSuccess(buffer.toString());
		} else if(localName.equalsIgnoreCase("order_id")) {
			in = false;
			newOrder.setNewOrderId(buffer.toString());
		}
	}
	
	public NewOrder getContent() {
		return newOrder;
	}
}
