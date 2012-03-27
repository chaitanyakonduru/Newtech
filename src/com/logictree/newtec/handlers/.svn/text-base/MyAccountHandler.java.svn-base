package com.logictree.newtec.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.content.Context;

import com.logictree.newtec.databases.NewTecDatabase;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.models.LoginInfo;

public class MyAccountHandler extends NewTecHandler {

	private StringBuffer buffer;
	private boolean in = false;
	private LoginInfo info;
	private NewTecApp app;

	public MyAccountHandler(Context context) {
		info = new LoginInfo();
		buffer = new StringBuffer();
		app = (NewTecApp) context.getApplicationContext();
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

		if (localName.equalsIgnoreCase("userinfo")) {

			buffer.setLength(0);
		} else if ("id".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("name".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("username".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("password".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("phone".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("address".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("city".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("state_zip".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("licence".equals(localName)) {
			in = true;
			buffer.setLength(0);
		}else if ("status".equals(localName)) {
			in = true;
			buffer.setLength(0);
		}else if ("error".equals(localName)) {
			in = true;
			buffer.setLength(0);
		}
	}

	/*
	 * <userinfo> <id>38</id> <name>Krishna Vittal</name>
	 * <username>vittal.banda@logictreeit.com</username>
	 * <password>vittal</password> <phone>9945945944</phone>
	 * <address>Mothinagar</address> <city>Hyderabad</city> <state_zip>Andhra
	 * pradesh - 565421</state_zip> <licence>Lic1234356</licence> </userinfo>
	 */

//	<userinfo><id>40</id>
//	<name>Burugu Sandhya</name>
//	<username>sandhyarani.burugu@gmail.com</username>
//	<password>password</password>
//	<phone>789-234-4567</phone>
//	<status>success</status>
//	</userinfo>
	
	
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if (localName.equalsIgnoreCase("userinfo")) {

		} else if ("id".equals(localName)) {
			in = false;
			info.setUserId(buffer.toString());
		} else if ("name".equals(localName)) {
			in = false;
			info.setName(buffer.toString());
		} else if ("username".equals(localName)) {
			in = false;
			info.setUsername(buffer.toString());
		} else if ("password".equals(localName)) {
			in = false;
			info.setPassword(buffer.toString());
		} else if ("phone".equals(localName)) {
			in = false;
			info.setPhone(buffer.toString());
		} else if ("address".equals(localName)) {
			in = false;
			info.setAddress(buffer.toString());
		} else if ("city".equals(localName)) {
			in = false;
			info.setCity(buffer.toString());
		} else if ("state_zip".equals(localName)) {
			in = false;
			info.setState(buffer.toString());
		} else if ("licence".equals(localName)) {
			in = false;
			info.setLicence(buffer.toString());
		}else if ("status".equals(localName)) {
			in = false;
			info.setStatus(buffer.toString());
		}else if ("error".equals(localName)) {
			in = false;
			info.setError(buffer.toString());
		}
	}

	private void insertLoginInfo() {
		new Thread(new Runnable() {
			public void run() {
				NewTecDatabase database = app.shareDatabaseInstance();
				database.insertUserInfo(info);
			}
		}).start();
	}

	@Override
	public Object getContent() {
		return info;
	}
}
