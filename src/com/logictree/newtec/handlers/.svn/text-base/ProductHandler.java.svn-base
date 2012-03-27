package com.logictree.newtec.handlers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.content.Context;

import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.models.Product;
import com.logictree.supply.network.DatabaseThread;

public class ProductHandler extends NewTecHandler {

	private List<Product> products;
	private StringBuffer buffer;
	private boolean in = false;
	private Product product;
	private NewTecApp app;
	private int priority;
	/*
	 * <product> <id>4</id> <department_name>Mobile</department_name>
	 * <product_name>A6 Samsung</product_name> <upc_code>12345</upc_code>
	 * <unit>5</unit> <suggested_retail_price>400</suggested_retail_price>
	 * <price>400</price> <cost>420</cost> <status>Inactive</status> </product>
	 */

	public ProductHandler(Context context, int priority) {
		products = new ArrayList<Product>();
		buffer = new StringBuffer();
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

		if (localName.equalsIgnoreCase("product")) {
			product = new Product();
			buffer.setLength(0);
		} else if ("id".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("department_name".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("product_name".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("upc_code".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("unit".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("suggested_retail_price".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("price".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("cost".equals(localName)) {
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
		if (localName.equalsIgnoreCase("product")) {
			products.add(product);
			product.setPriority(priority);
			insertProduct(product);
		} else if ("id".equals(localName)) {
			in = false;
			product.setProductId(buffer.toString());
		} else if ("department_name".equals(localName)) {
			in = false;
			product.setDepartmentName(buffer.toString());
		} else if ("product_name".equals(localName)) {
			in = false;
			product.setProductName(buffer.toString());
		} else if ("upc_code".equals(localName)) {
			in = false;
			product.setUpcCode(buffer.toString());
		} else if ("unit".equals(localName)) {
			in = false;
			product.setUnit(buffer.toString());
		} else if ("suggested_retail_price".equals(localName)) {
			in = false;
			product.setSuggestedPrice(buffer.toString());
		} else if ("price".equals(localName)) {
			in = false;
			product.setPrice(buffer.toString());
		} else if ("cost".equals(localName)) {
			in = false;
			product.setCost(buffer.toString());
		} else if ("status".equals(localName)) {
			in = false;
			product.setStatus(buffer.toString());
		}
	}

	@Override
	public Object getContent() {
		return products;
	}

	public List<Product> getProductList() {
		return products;
	}

	private void insertProduct(final Product product) {
		if (product != null) {
			if (app.getUserId() != null) {
				DatabaseThread databaseThread = app.shareDatabaseThread();
				databaseThread.addJob(product);
			}
		}
	}

}
