package com.logictree.newtec.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.logictree.supply.models.LoginInfo;

public class LoginHandler extends NewTecHandler {
	
	private LoginInfo info;
	private StringBuffer buffer;
	private boolean in = false;
	
	public LoginHandler() {
		info = new LoginInfo();
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
		
		if(localName.equalsIgnoreCase("login")) {
		} else if(localName.equalsIgnoreCase("success")) {
			in = true;
			buffer.setLength(0);
		} else if(localName.equalsIgnoreCase("error")) {
			in = true;
			buffer.setLength(0);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if(localName.equalsIgnoreCase("login")) {
			
		} else if(localName.equalsIgnoreCase("success")) {
			in= false;
			info.setUserId(buffer.toString());
		} else if(localName.equalsIgnoreCase("error")) {
			in= false;
			info.setError(buffer.toString());
		}
	}
	
	public Object getContent() {
		return info;
	}
}
