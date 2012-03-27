package com.logictree.newtec.handlers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.content.Context;

import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.models.Department;
import com.logictree.supply.network.DatabaseThread;

public class DepartmentHandler extends NewTecHandler {

	private List<Department> departments;
	private StringBuffer buffer;
	private boolean in = false;
	private Department department;
	private NewTecApp app;
	private int priority;
	
	public DepartmentHandler(Context context, int priority) {
		departments = new ArrayList<Department>();
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

	/*
	 * <departments> <id>5</id> <departments_name>Testing</departments_name>
	 * <status>Active</status> </departments>
	 */

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		//		Log.v("Handler", "" + localName);
		if (localName.equalsIgnoreCase("departments")) {
			department = new Department();
			buffer.setLength(0);
		} else if ("id".equals(localName)) {
			in = true;
			buffer.setLength(0);
		} else if ("departments_name".equals(localName)) {
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

		if (localName.equalsIgnoreCase("departments")) {
			departments.add(department);
			department.setPriority(priority);
			insertDepartment(department);
		} else if ("id".equals(localName)) {
			in = false;
			department.setDeptId(buffer.toString());
		} else if ("departments_name".equals(localName)) {
			in = false;
			department.setDeptName(buffer.toString());
		} else if ("status".equals(localName)) {
			in = false;
			department.setStatus(buffer.toString());
		}
	}

	private void insertDepartment(final Department department) {
		if (department != null) {
			if(app.getUserId() != null){
				DatabaseThread databaseThread  = app.shareDatabaseThread();
				databaseThread.addJob(department);
			}
		}

	}

	@Override
	public Object getContent() {
		return departments;
	}
}
