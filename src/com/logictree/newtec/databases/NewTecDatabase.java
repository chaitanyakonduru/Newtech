package com.logictree.newtec.databases;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.logictree.supply.models.Customer;
import com.logictree.supply.models.Department;
import com.logictree.supply.models.LoginInfo;
import com.logictree.supply.models.Order;
import com.logictree.supply.models.Product;

public class NewTecDatabase {

	private static NewTecDatabase newTecDatabase;
	private static NewTecDbHelper newTecDbHelper;
	private static final String DB_NAME = "newtec.sqlite";
	private static final int DB_VERSION = 1;
	private static final String TAG = "NewTecDatabase";
	private Context mContext;

	public static NewTecDatabase getDbInstance(Context context) {
		if (newTecDatabase == null) {
			newTecDatabase = new NewTecDatabase(context);
		}
		return newTecDatabase;
	}

	private NewTecDatabase(Context context) {
		newTecDbHelper = new NewTecDbHelper(context, DB_NAME, null, DB_VERSION);
		this.mContext = context;
	}

	private static class NewTecDbHelper extends SQLiteOpenHelper {

		public NewTecDbHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, DB_NAME, factory, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(ProductColumns.DATABASE_CREATE);
			db.execSQL(CustomerColumns.DATABASE_CREATE);
			db.execSQL(OrderColumns.DATABASE_CREATE);
			db.execSQL(LoginColumns.DATABASE_CREATE);
			db.execSQL(DepartmentColumns.DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + ProductColumns.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + CustomerColumns.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + OrderColumns.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + LoginColumns.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + DepartmentColumns.TABLE_NAME);
			onCreate(db);
		}
	}

	/*
	 * public void open() { newTecDbHelper = new NewTecDbHelper(mcontext,
	 * DB_NAME, null, DB_VERSION); sqLiteDatabase =
	 * newTecDbHelper.getWritableDatabase(); }
	 */

	public boolean insertProduct(Product product, String orderId,
			String userId, String businessname) {
		long rowId = -1;
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		Cursor cursor = null;

		Date date = new Date();
		String dateString = Long.toString(date.getTime());

		if (orderId != null && businessname != null) {
			cursor = sqLiteDatabase
					.rawQuery(
							"SELECT count(*) FROM Products WHERE product_id = ? and order_id=? and user_id = ? and business_name = ?",
							new String[] {
									String.valueOf(product.getProductId()),
									orderId, userId, businessname });
		} else {
			cursor = sqLiteDatabase
					.rawQuery(
							"SELECT count(*) FROM Products WHERE product_id = ? and user_id = ?",
							new String[] {
									String.valueOf(product.getProductId()),
									userId });
		}

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}
		Log.v(TAG, "Inserting product = "
				+ (isUpdate ? " update " : " insert ")+ (orderId != null ? orderId : "-1"));
		if (product != null) {
			ContentValues productdata = new ContentValues();
			productdata.put(ProductColumns.PRODUCT_ID,
					String.valueOf(product.getProductId()));
			productdata.put(ProductColumns.DEPT_NAME,
					product.getDepartmentName());
			productdata.put(ProductColumns.PRODUCT_NAME,
					product.getProductName());
			productdata.put(ProductColumns.PRODUCT_UPC, product.getUpcCode());
			productdata.put(ProductColumns.PRODUCT_SRP,
					product.getSuggestedPrice());
			productdata.put(ProductColumns.PRODUCT_PRICE, product.getPrice());
			productdata.put(ProductColumns.PRODUCT_COST, product.getCost());
			productdata.put(ProductColumns.PRODUCT_STATUS, product.getStatus());
			if (orderId != null) {
				productdata.put(ProductColumns.ORDER_ID, orderId);
			} else {
				productdata.put(ProductColumns.ORDER_ID, "-1");
			}
			if (businessname != null) {
				productdata.put(ProductColumns.BUSINESS_NAME, businessname);
			} else {
				productdata.put(ProductColumns.BUSINESS_NAME, "-1");
			}
			productdata
					.put(ProductColumns.SAVED, product.isSaved() ? "1" : "0");
			productdata.put(ProductColumns.ISSEND, product.isSend() ? "1"
					: "0");
			productdata.put(ProductColumns.NewOrder, product.isNewOrder() ? "1"
					: "0");
			productdata.put(ProductColumns.USER_ID, userId);
			productdata.put(ProductColumns.QTY, product.getQuantity());
			productdata.put(ProductColumns.TOTAL_UNIT, product.getUnit());
			productdata.put(ProductColumns.TOTAL_COST, product.getTotalCost());

			if (isUpdate) {
				String whereClause = "product_id= ? and order_id = ? and user_id = ? and business_name = ?";
				productdata.put(ProductColumns.LAST_DATE, dateString);
				int result = sqLiteDatabase.update(ProductColumns.TABLE_NAME,
						productdata, whereClause,
						new String[] { product.getProductId(), orderId, userId,
								businessname });
				if (result == 1) {
					return true;
				}
			} else {
				productdata.put(ProductColumns.CREATED_DATE, dateString);
				productdata.put(ProductColumns.LAST_DATE, dateString);
				rowId = sqLiteDatabase.insert(ProductColumns.TABLE_NAME, null,
						productdata);
				if (rowId != -1) {
					return true;
				}
			}

		}
		return false;
	}

	public boolean insertOrder(Order order, String userId) {
	
		long rowId = -1;
		boolean isUpdate = false;
		DateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String dateString = Long.toString(date.getTime());

		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Orders WHERE order_id = ? and user_id = ?",
						new String[] { order.getOrderId(), userId });
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			Log.v(TAG, "Count - " + count);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		Log.v(TAG, "Inserting order = " + (isUpdate ? " update " : " insert ")+order.getOrderId());

		if (order != null) {
			
			ContentValues orderdata = new ContentValues();
			orderdata.put(OrderColumns.ORDER_ID, order.getOrderId());

			try {
				Date orderdate = parser.parse(order.getOrderDate());
				orderdata.put(OrderColumns.ORDER_DATE,
						orderdate.getTime() / 1000);
			} catch (ParseException e) {
				e.printStackTrace();
				Log.e(TAG, "Error parsing date: " + order.getOrderDate());
				orderdata.put(OrderColumns.ORDER_DATE, order.getOrderDate());
			}

			// orderdata.put(OrderColumns.ORDER_DATE, order.getOrderDate());
			orderdata.put(OrderColumns.ORDER_AMOUNT, order.getAmount());
			orderdata.put(OrderColumns.CUST_ID, order.getCustId());
			orderdata.put(OrderColumns.BUSINESS_NAME, order.getBusinessname());
			orderdata.put(OrderColumns.ORDER_BY, order.getOrderBY());
			orderdata.put(OrderColumns.ORDER_STATUS, order.getStatus());
			orderdata.put(OrderColumns.ORDER_NOTES, order.getOrderNotes());
			orderdata.put(OrderColumns.WAREHOUSE, order.isWarehouse());
			orderdata.put(OrderColumns.SAVED, order.isSaved() ? "1" : "0");
			orderdata.put(OrderColumns.ISSEND, order.isSend() ? "1" : "0");
			orderdata
					.put(OrderColumns.NewOrder, order.isNewOrder() ? "1" : "0");
			orderdata.put(OrderColumns.USER_ID, userId);
			List<Product> products = order.getProducts();
			
			deleteProducts(userId, order.getOrderId());
			
			for (Product product : products) {
				insertProduct(product, order.getOrderId(), userId,
						order.getBusinessname());
			}
			
			if (order.getStatus().equalsIgnoreCase("Inactive")) {
				deleteInactiveOrder(userId);
				deleteInactiveProduct(userId, order.getOrderId());
			}

			if (isUpdate) {
				orderdata.put(OrderColumns.LAST_DATE, dateString);
				final String whereClause = "order_id= ? and user_id = ?";
				int res = sqLiteDatabase.update(OrderColumns.TABLE_NAME,
						orderdata, whereClause,
						new String[] { order.getOrderId(), userId });
				if (res == 1) {
					return true;
				}
			} else {
				orderdata.put(OrderColumns.CREATED_DATE, dateString);
				orderdata.put(OrderColumns.LAST_DATE, dateString);
				rowId = sqLiteDatabase.insert(OrderColumns.TABLE_NAME, null,
						orderdata);
				if (rowId != -1) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean insertCustomer(Customer customer, String userId) {
		boolean isUpdate = false;
		long rowId = -1;

		Date date = new Date();
		String dateString = Long.toString(date.getTime());

		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Customers WHERE cust_id = ? and user_id = ?",
						new String[] { customer.getCustomerId(), userId });
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		Log.v(TAG, "Inserting customer = "
				+ (isUpdate ? " update " : " insert "));

		if (customer != null) {
			ContentValues customerdata = new ContentValues();
			customerdata.put(CustomerColumns.CUSTOMER_ID,
					customer.getCustomerId());
			customerdata.put(CustomerColumns.CUSTOMER_NAME,
					customer.getCustomername());
			customerdata.put(CustomerColumns.BUSINESS_NAME,
					customer.getBusinessName());
			customerdata.put(CustomerColumns.CUSTOMER_PHONENO,
					customer.getPhoneNo());
			customerdata.put(CustomerColumns.CUSTOMER_LIC,
					customer.getLicence());
			customerdata.put(CustomerColumns.CUSTOMER_ADDRESS,
					customer.getAddress());
			customerdata.put(CustomerColumns.CUSTOMER_CITY, customer.getCity());
			customerdata.put(CustomerColumns.CUSTOMER_STATE,
					customer.getState());
			customerdata.put(CustomerColumns.CUSTOMER_ZIPCODE,
					customer.getZipcode());
			customerdata.put(CustomerColumns.CUSTOMER_MSA,
					customer.isMsaFlagOn());
			customerdata.put(CustomerColumns.PRICE_LEVEL,
					customer.getPrice_level());
			customerdata.put(CustomerColumns.CUSTOMER_STATUS,
					customer.getStatus());
			customerdata.put(CustomerColumns.SAVED, customer.isSaved() ? "1"
					: "0");
			customerdata.put(CustomerColumns.USER_ID, userId);

			if (isUpdate) {
				customerdata.put(CustomerColumns.LAST_DATE, dateString);
				final String whereClause = "cust_id= ? and user_id = ?";
				int res = sqLiteDatabase.update(CustomerColumns.TABLE_NAME,
						customerdata, whereClause,
						new String[] { customer.getCustomerId(), userId });
				if (res == 1) {
					return true;
				}
			} else {
				customerdata.put(CustomerColumns.CREATED_DATE, dateString);
				customerdata.put(CustomerColumns.LAST_DATE, dateString);
				rowId = sqLiteDatabase.insert(CustomerColumns.TABLE_NAME, null,
						customerdata);
				if (rowId == -1) {
					return false;
				}
			}
		}
		return false;
	}

	public boolean insertUserInfo(LoginInfo loginInfo) {
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		long rowId = -1;
		boolean isUpdate = false;
		Date date = new Date();
		String dateString = Long.toString(date.getTime());

		Cursor cursor = sqLiteDatabase.rawQuery(
				"SELECT count(*) FROM Login WHERE  user_id = ?",
				new String[] { loginInfo.getUserId() });
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		Log.v(TAG, "Inserting userinfo = "
				+ (isUpdate ? " update " : " insert "));

		if (loginInfo != null) {
			ContentValues userdata = new ContentValues();
			userdata.put(LoginColumns.USERNAME, loginInfo.getUsername());
			userdata.put(LoginColumns.PASSWORD, loginInfo.getPassword());
			userdata.put(LoginColumns.NAME, loginInfo.getName());
			userdata.put(LoginColumns.USER_ID, loginInfo.getUserId());
			userdata.put(LoginColumns.ADDRESS, loginInfo.getAddress());
			userdata.put(LoginColumns.CITY, loginInfo.getCity());
			userdata.put(LoginColumns.STATE, loginInfo.getState());
			userdata.put(LoginColumns.PHONE, loginInfo.getPhone());
			userdata.put(LoginColumns.LICENCE, loginInfo.getLicence());

			userdata.put(LoginColumns.LAST_DATE, loginInfo.getLastLoginTime());

			if (isUpdate) {
				String whereClause = " user_id = ?";
				userdata.put(LoginColumns.LAST_DATE, dateString);
				int res = sqLiteDatabase.update(LoginColumns.TABLE_NAME,
						userdata, whereClause,
						new String[] { loginInfo.getUserId() });
				if (res >= 1) {
					return true;
				}
			} else {
				userdata.put(LoginColumns.CREATED_DATE, dateString);
				userdata.put(LoginColumns.LAST_DATE, dateString);
				rowId = sqLiteDatabase.insert(LoginColumns.TABLE_NAME, null,
						userdata);
				if (rowId != -1) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean insertDepartment(Department department, String userId) {
		boolean isUpdate = false;
		long rowId = -1;

		Date date = new Date();
		String dateString = Long.toString(date.getTime());

		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Departments WHERE dept_id = ? and user_id = ?",
						new String[] { String.valueOf(department.getDeptId()),
								userId });
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		Log.v(TAG, "Inserting department = "
				+ (isUpdate ? " update " : " insert "));

		if (department != null) {
			ContentValues customerdata = new ContentValues();
			customerdata.put(DepartmentColumns.DEPT_ID,
					String.valueOf(department.getDeptId()));
			customerdata.put(DepartmentColumns.DEPT_NAME,
					department.getDeptName());
			customerdata.put(DepartmentColumns.STATUS, department.getStatus());
			customerdata.put(DepartmentColumns.USER_ID, userId);

			if (isUpdate) {
				customerdata.put(DepartmentColumns.LAST_DATE, dateString);
				final String whereClause = "dept_id= ? and user_id = ?";
				int res = sqLiteDatabase.update(DepartmentColumns.TABLE_NAME,
						customerdata, whereClause,
						new String[] { department.getDeptId(), userId });
				if (res == 1) {
					return true;
				}
			} else {
				customerdata.put(DepartmentColumns.CREATED_DATE, dateString);
				customerdata.put(DepartmentColumns.LAST_DATE, dateString);
				rowId = sqLiteDatabase.insert(DepartmentColumns.TABLE_NAME,
						null, customerdata);
				if (rowId == -1) {
					return false;
				}
			}
		}
		return false;
	}

	public void close() {
		newTecDbHelper.close();
	}

	/*
	 * public boolean deleteProduct(String rowId) { return
	 * newTecDbHelper.getWritableDatabase().delete( ProductColumns.TABLE_NAME,
	 * ProductColumns.PRODUCT_ID + "=" + rowId, null) > 0; }
	 */
	public boolean deleteNewOrder(String userId, String orderId) {
		final String whereClause = "neworder = ? and user_id = ? and order_id = ? ";
		Log.v(TAG, "delete new order");
		return newTecDbHelper.getWritableDatabase().delete(
				OrderColumns.TABLE_NAME, whereClause,
				new String[] { "1", userId, orderId }) > 0;
	}

	private boolean deleteProducts(String userId, String orderId) {
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		Log.v(TAG, "delete product " + orderId);
		final String whereClause = "order_id = ? and user_id = ? ";
		return sqLiteDatabase.delete(ProductColumns.TABLE_NAME, whereClause,
				new String[] { orderId, userId }) > 0;
	}

	public boolean deleteNewProduct(String userId, String orderId) {
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		final String whereClause = "order_id = ? and user_id = ? and product_id = ? ";
		return sqLiteDatabase.delete(ProductColumns.TABLE_NAME, whereClause,
				new String[] { orderId, userId }) > 0;
	}

	public boolean deleteOrder(String rowId) {
		return newTecDbHelper.getWritableDatabase().delete(
				OrderColumns.TABLE_NAME, OrderColumns.USER_ID + " = " + rowId,
				null) > 0;
	}

	public boolean deleteInactiveOrder(String userId) {
		final String whereClause = "user_id = ? and status = ? ";
		Log.v(TAG,
				"delete inactive order "
						+ newTecDbHelper.getWritableDatabase().delete(
								OrderColumns.TABLE_NAME, whereClause,
								new String[] { userId, "Inactive" }));
		return newTecDbHelper.getWritableDatabase().delete(
				OrderColumns.TABLE_NAME, whereClause,
				new String[] { userId, "Inactive" }) > 0;
	}

	private boolean deleteInactiveProduct(String userId, String orderId) {
		final String whereClause = " user_id = ? and order_id = ? ";
		Log.v(TAG, "delete inactive product - order id " + orderId);
		return newTecDbHelper.getWritableDatabase().delete(
				ProductColumns.TABLE_NAME, whereClause,
				new String[] { userId, orderId }) > 0;
	}

	/*
	 * public boolean deleteCacheOrder(String userId) { final String whereClause
	 * = "saved = ? and user_id = ?"; Log.v(TAG, "delete cache order"); return
	 * newTecDbHelper.getWritableDatabase().delete( OrderColumns.TABLE_NAME,
	 * whereClause, new String[] { "1", userId }) > 0; }
	 */

	public boolean deleteCacheOrderId(String userId, String orderId) {
		final String whereClause = "user_id = ? and order_id = ? ";
		Log.v(TAG, "delete cache orderid  " + orderId);
		return newTecDbHelper.getWritableDatabase().delete(
				OrderColumns.TABLE_NAME, whereClause,
				new String[] {userId, orderId }) > 0;
	}

	public boolean deleteCacheProductId(String userId, String orderId) {
		final String whereClause = "user_id = ? and order_id = ?";
		Log.v(TAG, "delete cache productid " + orderId);
		return newTecDbHelper.getWritableDatabase().delete(
				ProductColumns.TABLE_NAME, whereClause,
				new String[] { userId, orderId }) > 0;
	}
	
	public boolean deleteCacheCustomerId(String userId, String custId) {
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		final String whereClause = "saved = ? and user_id = ? and cust_id = ? ";
		Log.v(TAG, "delete cache customer " + custId);
		return sqLiteDatabase.delete(CustomerColumns.TABLE_NAME, whereClause,
				new String[] { "1", userId, custId }) > 0;
	}

	/*
	 * public boolean deleteCacheProduct(String userId) { final String
	 * whereClause = "saved = ? and user_id = ?"; Log.v(TAG,
	 * "delete cache product"); return
	 * newTecDbHelper.getWritableDatabase().delete( ProductColumns.TABLE_NAME,
	 * whereClause, new String[] { "1", userId }) > 0; }
	 */

	public boolean deleteCustomer(String rowId) {
		Log.v(TAG, "deleted  " + rowId);
		return newTecDbHelper.getWritableDatabase().delete(
				CustomerColumns.TABLE_NAME,
				CustomerColumns.CUSTOMER_ID + "=" + rowId, null) > 0;
	}

	/*
	 * public boolean deleteCacheCustomer(String userId, boolean isSaved) {
	 * SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
	 * final String whereClause = "saved = ? and user_id = ?"; Log.v(TAG,
	 * "delete cache customer"); return
	 * sqLiteDatabase.delete(CustomerColumns.TABLE_NAME, whereClause, new
	 * String[] { "1", userId }) > 0; }
	 */

	public boolean deleteProduct(String userId, String orderId, String productId) {
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		final String whereClause = "order_id = ? and user_id = ? and product_id = ? ";
		return sqLiteDatabase.delete(ProductColumns.TABLE_NAME, whereClause,
				new String[] { orderId, userId, productId }) > 0;
	}

	public boolean deleteLoginDetails(String rowId) {
		return newTecDbHelper.getWritableDatabase().delete(
				LoginColumns.TABLE_NAME, BaseColumns._ID + "=" + rowId, null) > 0;
	}

	public long getLastInsertOrderId() {
		long index = 0;
		SQLiteDatabase db = newTecDbHelper.getWritableDatabase();
		Cursor cursor = db.query("sqlite_sequence", new String[] { "seq" },
				"name = ?", new String[] { OrderColumns.TABLE_NAME }, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			index = cursor.getLong(cursor.getColumnIndex("seq"));
		}
		cursor.close();
		return index;
	}

	public long getLastInsertCustomerId() {
		long index = 0;
		SQLiteDatabase db = newTecDbHelper.getWritableDatabase();
		Cursor cursor = db.query("sqlite_sequence", new String[] { "seq" },
				"name = ?", new String[] { CustomerColumns.TABLE_NAME }, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			index = cursor.getLong(cursor.getColumnIndex("seq"));
		}
		cursor.close();
		return index;
	}

	public List<Product> getProducts(String userId, String orderId) {
		if (orderId == null) {
			orderId = "-1";
		}

		List<Product> productlist = new ArrayList<Product>();

		Cursor cursor = newTecDbHelper.getReadableDatabase().query(
				ProductColumns.TABLE_NAME,
				null,
				ProductColumns.ORDER_ID + " =? and " + ProductColumns.USER_ID
						+ " =?", new String[] { orderId, userId },
				ProductColumns.PRODUCT_ID, null,
				"lower ( " + ProductColumns.PRODUCT_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return productlist;
		}

		int INDEX_PRODUCT_ID = cursor.getColumnIndex(ProductColumns.PRODUCT_ID);
		int INDEX_DEPT_NAME = cursor.getColumnIndex(ProductColumns.DEPT_NAME);
		int INDEX_PRODUCT_NAME = cursor
				.getColumnIndex(ProductColumns.PRODUCT_NAME);
		int INDEX_PRODUCT_UPC = cursor
				.getColumnIndex(ProductColumns.PRODUCT_UPC);
		int INDEX_PRODUCT_SRP = cursor
				.getColumnIndex(ProductColumns.PRODUCT_SRP);
		int INDEX_PRODUCT_PRICE = cursor
				.getColumnIndex(ProductColumns.PRODUCT_PRICE);
		int INDEX_PRODUCT_COST = cursor
				.getColumnIndex(ProductColumns.PRODUCT_COST);
		int INDEX_PRODUCT_STATUS = cursor
				.getColumnIndex(ProductColumns.PRODUCT_STATUS);
		int INDEX_ORDER_ID = cursor.getColumnIndex(ProductColumns.ORDER_ID);
		int INDEX_USER_ID = cursor.getColumnIndex(ProductColumns.USER_ID);
		int INDEX_QTY = cursor.getColumnIndex(ProductColumns.QTY);
		int INDEX_TOTAL_UNIT = cursor.getColumnIndex(ProductColumns.TOTAL_UNIT);
		int INDEX_SUB_RETAIL = cursor.getColumnIndex(ProductColumns.SUB_RETAIL);
		int INDEX_TOTAL_COST = cursor.getColumnIndex(ProductColumns.TOTAL_COST);
		cursor.moveToFirst();
		Product product = null;
		do {
			product = new Product();
			product.setProductId(String.valueOf(cursor.getInt(INDEX_PRODUCT_ID)));
			product.setDepartmentName(cursor.getString(INDEX_DEPT_NAME));
			product.setProductName(cursor.getString(INDEX_PRODUCT_NAME));
			product.setUpcCode(cursor.getString(INDEX_PRODUCT_UPC));
			product.setSuggestedPrice(cursor.getString(INDEX_PRODUCT_SRP));
			product.setPrice(cursor.getString(INDEX_PRODUCT_PRICE));
			product.setCost(cursor.getString(INDEX_PRODUCT_COST));
			product.setStatus(cursor.getString(INDEX_PRODUCT_STATUS));
			product.setOrderId(cursor.getString(INDEX_ORDER_ID));
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.NewOrder)) > 0 ? true
					: false;
			product.setSaved(isSaved);
			product.setSend(isSend);
			product.setNewOrder(isNewOrder);
			product.setUserId(cursor.getString(INDEX_USER_ID));
			product.setQuantity(cursor.getString(INDEX_QTY));
			product.setUnit(cursor.getString(INDEX_TOTAL_UNIT));
			product.setSubRetail(cursor.getString(INDEX_SUB_RETAIL));
			product.setTotalCost(cursor.getString(INDEX_TOTAL_COST));
			productlist.add(product);
		} while (cursor.moveToNext());
		cursor.close();
		return productlist;
	}

	public List<Product> getCacheProducts(String userId, String orderId,
			String businessname) {

		List<Product> productlist = new ArrayList<Product>();
		final String whereClause = "user_id = ? and order_id = ? and business_name = ?";
		Cursor cursor = newTecDbHelper.getReadableDatabase().query(
				ProductColumns.TABLE_NAME, null, whereClause,
				new String[] { userId, orderId, businessname}, null, null,
				"lower ( " + ProductColumns.PRODUCT_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return productlist;
		}

		int INDEX_PRODUCT_ID = cursor.getColumnIndex(ProductColumns.PRODUCT_ID);
		int INDEX_DEPT_NAME = cursor.getColumnIndex(ProductColumns.DEPT_NAME);
		int INDEX_PRODUCT_NAME = cursor
				.getColumnIndex(ProductColumns.PRODUCT_NAME);
		int INDEX_PRODUCT_UPC = cursor
				.getColumnIndex(ProductColumns.PRODUCT_UPC);
		int INDEX_PRODUCT_SRP = cursor
				.getColumnIndex(ProductColumns.PRODUCT_SRP);
		int INDEX_PRODUCT_PRICE = cursor
				.getColumnIndex(ProductColumns.PRODUCT_PRICE);
		int INDEX_PRODUCT_COST = cursor
				.getColumnIndex(ProductColumns.PRODUCT_COST);
		int INDEX_PRODUCT_STATUS = cursor
				.getColumnIndex(ProductColumns.PRODUCT_STATUS);
		int INDEX_ORDER_ID = cursor.getColumnIndex(ProductColumns.ORDER_ID);
		int INDEX_USER_ID = cursor.getColumnIndex(ProductColumns.USER_ID);
		int INDEX_QTY = cursor.getColumnIndex(ProductColumns.QTY);
		int INDEX_TOTAL_UNIT = cursor.getColumnIndex(ProductColumns.TOTAL_UNIT);
		int INDEX_SUB_RETAIL = cursor.getColumnIndex(ProductColumns.SUB_RETAIL);
		int INDEX_TOTAL_COST = cursor.getColumnIndex(ProductColumns.TOTAL_COST);
		cursor.moveToFirst();
		Product product = null;
		do {
			product = new Product();
			product.setProductId(String.valueOf(cursor.getInt(INDEX_PRODUCT_ID)));
			product.setDepartmentName(cursor.getString(INDEX_DEPT_NAME));
			product.setProductName(cursor.getString(INDEX_PRODUCT_NAME));
			product.setUpcCode(cursor.getString(INDEX_PRODUCT_UPC));
			product.setSuggestedPrice(cursor.getString(INDEX_PRODUCT_SRP));
			product.setPrice(cursor.getString(INDEX_PRODUCT_PRICE));
			product.setCost(cursor.getString(INDEX_PRODUCT_COST));
			product.setStatus(cursor.getString(INDEX_PRODUCT_STATUS));
			product.setOrderId(cursor.getString(INDEX_ORDER_ID));
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.NewOrder)) > 0 ? true
					: false;
			product.setSaved(isSaved);
			product.setSend(isSend);
			product.setNewOrder(isNewOrder);
			product.setUserId(cursor.getString(INDEX_USER_ID));
			product.setQuantity(cursor.getString(INDEX_QTY));
			product.setUnit(cursor.getString(INDEX_TOTAL_UNIT));
			product.setSubRetail(cursor.getString(INDEX_SUB_RETAIL));
			product.setTotalCost(cursor.getString(INDEX_TOTAL_COST));
			productlist.add(product);
		} while (cursor.moveToNext());
		cursor.close();
		return productlist;
	}

	public List<Order> getCacheOrders(String userId) {
		List<Order> orderlist = new ArrayList<Order>();
		Cursor cursor = newTecDbHelper.getReadableDatabase().query(
				OrderColumns.TABLE_NAME, null,
				OrderColumns.USER_ID + " =? and " + OrderColumns.ISSEND + " =? and "+OrderColumns.WAREHOUSE +" =?" ,
				new String[] { userId, "1", "1" }, null, null, null);

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return orderlist;
		}

		int INDEX_ORDER_ID = cursor.getColumnIndex(OrderColumns.ORDER_ID);
		int INDEX_ORDER_DATE = cursor.getColumnIndex(OrderColumns.ORDER_DATE);
		int INDEX_ORDER_AMOUNT = cursor
				.getColumnIndex(OrderColumns.ORDER_AMOUNT);
		int INDEX_CUST_ID = cursor.getColumnIndex(OrderColumns.CUST_ID);
		int INDEX_CUST_NAME = cursor.getColumnIndex(OrderColumns.BUSINESS_NAME);
		int INDEX_ORDER_BY = cursor.getColumnIndex(OrderColumns.ORDER_BY);
		int INDEX_ORDER_STATUS = cursor
				.getColumnIndex(OrderColumns.ORDER_STATUS);
		int INDEX_ORDER_NOTES = cursor.getColumnIndex(OrderColumns.ORDER_NOTES);
		int INDEX_WAREHOUSE = cursor.getColumnIndex(OrderColumns.WAREHOUSE);

		cursor.moveToFirst();
		Order order = null;
		do {
			order = new Order();
			order.setOrderId(cursor.getString(INDEX_ORDER_ID));

			DateFormat formatter = android.text.format.DateFormat
					.getDateFormat(mContext);
			long date = cursor.getLong(INDEX_ORDER_DATE);
			Date dateObj = new Date(date * 1000);
			order.setOrderDate(formatter.format(dateObj));

			// order.setOrderDate(cursor.getString(INDEX_ORDER_DATE));
			order.setAmount(cursor.getString(INDEX_ORDER_AMOUNT));
			order.setCustId(cursor.getString(INDEX_CUST_ID));
			order.setBusinessname(cursor.getString(INDEX_CUST_NAME));
			order.setOrderBY(cursor.getString(INDEX_ORDER_BY));
			order.setStatus(cursor.getString(INDEX_ORDER_STATUS));
			order.setOrderNotes(cursor.getString(INDEX_ORDER_NOTES));
			boolean isWarehouse = cursor.getInt(INDEX_WAREHOUSE) > 0 ? true
					: false;
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.NewOrder)) > 0 ? true : false;
			order.setWarehouse(isWarehouse);
			order.setSaved(isSaved);
			order.setSend(isSend);
			order.setNewOrder(isNewOrder);
			order.setProducts(getCacheProducts(userId,
					cursor.getString(INDEX_ORDER_ID), order.getBusinessname()));
			orderlist.add(order);
		} while (cursor.moveToNext());
		cursor.close();
		return orderlist;
	}

	/*public List<Order> getNewOrders(String userId) {
		List<Order> orderlist = new ArrayList<Order>();
		Cursor cursor = newTecDbHelper.getReadableDatabase()
				.query(OrderColumns.TABLE_NAME,
						null,
						OrderColumns.USER_ID + "=? and "
								+ OrderColumns.NewOrder + " =?",
						new String[] { userId, "1" }, null, null, null);

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return orderlist;
		}

		int INDEX_ORDER_ID = cursor.getColumnIndex(OrderColumns.ORDER_ID);
		int INDEX_ORDER_DATE = cursor.getColumnIndex(OrderColumns.ORDER_DATE);
		int INDEX_ORDER_AMOUNT = cursor
				.getColumnIndex(OrderColumns.ORDER_AMOUNT);
		int INDEX_CUST_ID = cursor.getColumnIndex(OrderColumns.CUST_ID);
		int INDEX_CUST_NAME = cursor.getColumnIndex(OrderColumns.BUSINESS_NAME);
		int INDEX_ORDER_BY = cursor.getColumnIndex(OrderColumns.ORDER_BY);
		int INDEX_ORDER_STATUS = cursor
				.getColumnIndex(OrderColumns.ORDER_STATUS);
		int INDEX_ORDER_NOTES = cursor.getColumnIndex(OrderColumns.ORDER_NOTES);
		int INDEX_WAREHOUSE = cursor.getColumnIndex(OrderColumns.WAREHOUSE);

		cursor.moveToFirst();
		Order order = null;
		do {
			order = new Order();
			order.setOrderId(cursor.getString(INDEX_ORDER_ID));
			order.setOrderDate(cursor.getString(INDEX_ORDER_DATE));
			order.setAmount(cursor.getString(INDEX_ORDER_AMOUNT));
			order.setCustId(cursor.getString(INDEX_CUST_ID));
			order.setBusinessname(cursor.getString(INDEX_CUST_NAME));
			order.setOrderBY(cursor.getString(INDEX_ORDER_BY));
			order.setStatus(cursor.getString(INDEX_ORDER_STATUS));
			order.setOrderNotes(cursor.getString(INDEX_ORDER_NOTES));
			boolean isWarehouse = cursor.getInt(INDEX_WAREHOUSE) > 0 ? true
					: false;
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.NewOrder)) > 0 ? true : false;
			order.setWarehouse(isWarehouse);
			order.setSaved(isSaved);
			order.setSend(isSend);
			order.setNewOrder(isNewOrder);
			order.setProducts(getNewProducts(userId,
					cursor.getString(INDEX_ORDER_ID)));
			orderlist.add(order);
		} while (cursor.moveToNext());
		cursor.close();
		return orderlist;
	}*/

	/*public List<Order> getUpdateOrders(String userId) {
		List<Order> orderlist = new ArrayList<Order>();
		Cursor cursor = newTecDbHelper.getReadableDatabase().query(
				OrderColumns.TABLE_NAME, null,
				OrderColumns.USER_ID + "=? and " + OrderColumns.ISSEND + " =?",
				new String[] { userId, "1" }, null, null, null);

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return orderlist;
		}

		int INDEX_ORDER_ID = cursor.getColumnIndex(OrderColumns.ORDER_ID);
		int INDEX_ORDER_DATE = cursor.getColumnIndex(OrderColumns.ORDER_DATE);
		int INDEX_ORDER_AMOUNT = cursor
				.getColumnIndex(OrderColumns.ORDER_AMOUNT);
		int INDEX_CUST_ID = cursor.getColumnIndex(OrderColumns.CUST_ID);
		int INDEX_CUST_NAME = cursor.getColumnIndex(OrderColumns.BUSINESS_NAME);
		int INDEX_ORDER_BY = cursor.getColumnIndex(OrderColumns.ORDER_BY);
		int INDEX_ORDER_STATUS = cursor
				.getColumnIndex(OrderColumns.ORDER_STATUS);
		int INDEX_ORDER_NOTES = cursor.getColumnIndex(OrderColumns.ORDER_NOTES);
		int INDEX_WAREHOUSE = cursor.getColumnIndex(OrderColumns.WAREHOUSE);

		cursor.moveToFirst();
		Order order = null;
		do {
			order = new Order();
			order.setOrderId(cursor.getString(INDEX_ORDER_ID));
			order.setOrderDate(cursor.getString(INDEX_ORDER_DATE));
			order.setAmount(cursor.getString(INDEX_ORDER_AMOUNT));
			order.setCustId(cursor.getString(INDEX_CUST_ID));
			order.setBusinessname(cursor.getString(INDEX_CUST_NAME));
			order.setOrderBY(cursor.getString(INDEX_ORDER_BY));
			order.setStatus(cursor.getString(INDEX_ORDER_STATUS));
			order.setOrderNotes(cursor.getString(INDEX_ORDER_NOTES));
			boolean isWarehouse = cursor.getInt(INDEX_WAREHOUSE) > 0 ? true
					: false;
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.NewOrder)) > 0 ? true : false;
			order.setWarehouse(isWarehouse);
			order.setSaved(isSaved);
			order.setSend(isSend);
			order.setNewOrder(isNewOrder);
			order.setProducts(getUpdateProducts(userId,
					cursor.getString(INDEX_ORDER_ID)));
			orderlist.add(order);
		} while (cursor.moveToNext());
		cursor.close();
		return orderlist;
	}

	public List<Product> getNewProducts(String userId, String orderId) {
		List<Product> productlist = new ArrayList<Product>();
		final String whereClause = "user_id = ? and neworder = ? and order_id = ?";
		Cursor cursor = newTecDbHelper.getReadableDatabase().query(
				ProductColumns.TABLE_NAME, null, whereClause,
				new String[] { userId, "1", orderId }, null, null,
				"lower ( " + ProductColumns.PRODUCT_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return productlist;
		}

		int INDEX_PRODUCT_ID = cursor.getColumnIndex(ProductColumns.PRODUCT_ID);
		int INDEX_DEPT_NAME = cursor.getColumnIndex(ProductColumns.DEPT_NAME);
		int INDEX_PRODUCT_NAME = cursor
				.getColumnIndex(ProductColumns.PRODUCT_NAME);
		int INDEX_PRODUCT_UPC = cursor
				.getColumnIndex(ProductColumns.PRODUCT_UPC);
		int INDEX_PRODUCT_SRP = cursor
				.getColumnIndex(ProductColumns.PRODUCT_SRP);
		int INDEX_PRODUCT_PRICE = cursor
				.getColumnIndex(ProductColumns.PRODUCT_PRICE);
		int INDEX_PRODUCT_COST = cursor
				.getColumnIndex(ProductColumns.PRODUCT_COST);
		int INDEX_PRODUCT_STATUS = cursor
				.getColumnIndex(ProductColumns.PRODUCT_STATUS);
		int INDEX_ORDER_ID = cursor.getColumnIndex(ProductColumns.ORDER_ID);
		int INDEX_USER_ID = cursor.getColumnIndex(ProductColumns.USER_ID);
		int INDEX_QTY = cursor.getColumnIndex(ProductColumns.QTY);
		int INDEX_TOTAL_UNIT = cursor.getColumnIndex(ProductColumns.TOTAL_UNIT);
		int INDEX_SUB_RETAIL = cursor.getColumnIndex(ProductColumns.SUB_RETAIL);
		int INDEX_TOTAL_COST = cursor.getColumnIndex(ProductColumns.TOTAL_COST);
		cursor.moveToFirst();
		Product product = null;
		do {
			product = new Product();
			product.setProductId(String.valueOf(cursor.getInt(INDEX_PRODUCT_ID)));
			product.setDepartmentName(cursor.getString(INDEX_DEPT_NAME));
			product.setProductName(cursor.getString(INDEX_PRODUCT_NAME));
			product.setUpcCode(cursor.getString(INDEX_PRODUCT_UPC));
			product.setSuggestedPrice(cursor.getString(INDEX_PRODUCT_SRP));
			product.setPrice(cursor.getString(INDEX_PRODUCT_PRICE));
			product.setCost(cursor.getString(INDEX_PRODUCT_COST));
			product.setStatus(cursor.getString(INDEX_PRODUCT_STATUS));
			product.setOrderId(cursor.getString(INDEX_ORDER_ID));
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.NewOrder)) > 0 ? true
					: false;
			product.setSaved(isSaved);
			product.setSend(isSend);
			product.setNewOrder(isNewOrder);
			product.setUserId(cursor.getString(INDEX_USER_ID));
			product.setQuantity(cursor.getString(INDEX_QTY));
			product.setUnit(cursor.getString(INDEX_TOTAL_UNIT));
			product.setSubRetail(cursor.getString(INDEX_SUB_RETAIL));
			product.setTotalCost(cursor.getString(INDEX_TOTAL_COST));
			productlist.add(product);
		} while (cursor.moveToNext());
		cursor.close();
		return productlist;
	}

	public List<Product> getUpdateProducts(String userId, String orderId) {
		List<Product> productlist = new ArrayList<Product>();
		final String whereClause = "user_id = ? and isupdate = ? and order_id = ?";
		Cursor cursor = newTecDbHelper.getReadableDatabase().query(
				ProductColumns.TABLE_NAME, null, whereClause,
				new String[] { userId, "1", orderId }, null, null,
				"lower ( " + ProductColumns.PRODUCT_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return productlist;
		}

		int INDEX_PRODUCT_ID = cursor.getColumnIndex(ProductColumns.PRODUCT_ID);
		int INDEX_DEPT_NAME = cursor.getColumnIndex(ProductColumns.DEPT_NAME);
		int INDEX_PRODUCT_NAME = cursor
				.getColumnIndex(ProductColumns.PRODUCT_NAME);
		int INDEX_PRODUCT_UPC = cursor
				.getColumnIndex(ProductColumns.PRODUCT_UPC);
		int INDEX_PRODUCT_SRP = cursor
				.getColumnIndex(ProductColumns.PRODUCT_SRP);
		int INDEX_PRODUCT_PRICE = cursor
				.getColumnIndex(ProductColumns.PRODUCT_PRICE);
		int INDEX_PRODUCT_COST = cursor
				.getColumnIndex(ProductColumns.PRODUCT_COST);
		int INDEX_PRODUCT_STATUS = cursor
				.getColumnIndex(ProductColumns.PRODUCT_STATUS);
		int INDEX_ORDER_ID = cursor.getColumnIndex(ProductColumns.ORDER_ID);
		int INDEX_USER_ID = cursor.getColumnIndex(ProductColumns.USER_ID);
		int INDEX_QTY = cursor.getColumnIndex(ProductColumns.QTY);
		int INDEX_TOTAL_UNIT = cursor.getColumnIndex(ProductColumns.TOTAL_UNIT);
		int INDEX_SUB_RETAIL = cursor.getColumnIndex(ProductColumns.SUB_RETAIL);
		int INDEX_TOTAL_COST = cursor.getColumnIndex(ProductColumns.TOTAL_COST);
		cursor.moveToFirst();
		Product product = null;
		do {
			product = new Product();
			product.setProductId(String.valueOf(cursor.getInt(INDEX_PRODUCT_ID)));
			product.setDepartmentName(cursor.getString(INDEX_DEPT_NAME));
			product.setProductName(cursor.getString(INDEX_PRODUCT_NAME));
			product.setUpcCode(cursor.getString(INDEX_PRODUCT_UPC));
			product.setSuggestedPrice(cursor.getString(INDEX_PRODUCT_SRP));
			product.setPrice(cursor.getString(INDEX_PRODUCT_PRICE));
			product.setCost(cursor.getString(INDEX_PRODUCT_COST));
			product.setStatus(cursor.getString(INDEX_PRODUCT_STATUS));
			product.setOrderId(cursor.getString(INDEX_ORDER_ID));
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.NewOrder)) > 0 ? true
					: false;
			product.setSaved(isSaved);
			product.setSend(isSend);
			product.setNewOrder(isNewOrder);
			product.setUserId(cursor.getString(INDEX_USER_ID));
			product.setQuantity(cursor.getString(INDEX_QTY));
			product.setUnit(cursor.getString(INDEX_TOTAL_UNIT));
			product.setSubRetail(cursor.getString(INDEX_SUB_RETAIL));
			product.setTotalCost(cursor.getString(INDEX_TOTAL_COST));
			productlist.add(product);
		} while (cursor.moveToNext());
		cursor.close();
		return productlist;
	}*/

	public List<Customer> getCacheCustomers(String userId) {
		List<Customer> customerlist = new ArrayList<Customer>();
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getReadableDatabase();

		Cursor cursor = sqLiteDatabase.query(CustomerColumns.TABLE_NAME, null,
				CustomerColumns.USER_ID + " =? and " + CustomerColumns.SAVED
						+ " =?", new String[] { userId, "1" }, null, null,
				"lower ( " + CustomerColumns.BUSINESS_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return customerlist;
		}

		int INDEX_CUSTOMER_ID = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ID);
		int INDEX_CUSTOMER_NAME = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_NAME);
		int INDEX_BUSINESS_NAME = cursor
				.getColumnIndex(CustomerColumns.BUSINESS_NAME);
		int INDEX_CUSTOMER_PHONENO = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_PHONENO);
		int INDEX_CUSTOMER_LIC = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_LIC);
		int INDEX_CUSTOMER_ADDRESS = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ADDRESS);
		int INDEX_CUSTOMER_CITY = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_CITY);
		int INDEX_CUSTOMER_STATE = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_STATE);
		int INDEX_CUSTOMER_ZIPCODE = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ZIPCODE);
		int INDEX_CUSTOMER_MSA = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_MSA);
		int INDEX_PRICELEVEL = cursor
				.getColumnIndex(CustomerColumns.PRICE_LEVEL);
		int INDEX_CUSTOMER_STATUS = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_STATUS);

		cursor.moveToFirst();
		Customer customer = null;
		do {
			customer = new Customer();
			customer.setCustomerId(cursor.getString(INDEX_CUSTOMER_ID));
			customer.setCustomername(cursor.getString(INDEX_CUSTOMER_NAME));
			customer.setBusinessName(cursor.getString(INDEX_BUSINESS_NAME));
			customer.setPhoneNo(cursor.getString(INDEX_CUSTOMER_PHONENO));
			customer.setLicence(cursor.getString(INDEX_CUSTOMER_LIC));
			customer.setAddress(cursor.getString(INDEX_CUSTOMER_ADDRESS));
			customer.setCity(cursor.getString(INDEX_CUSTOMER_CITY));
			customer.setState(cursor.getString(INDEX_CUSTOMER_STATE));
			customer.setZipcode(cursor.getString(INDEX_CUSTOMER_ZIPCODE));
			boolean isMsa = cursor.getInt(INDEX_CUSTOMER_MSA) > 0 ? true
					: false;
			customer.setMsaFlagOn(isMsa);
			customer.setPrice_level(cursor.getString(INDEX_PRICELEVEL));
			customer.setStatus(cursor.getString(INDEX_CUSTOMER_STATUS));
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(CustomerColumns.SAVED)) > 0 ? true : false;
			customer.setSaved(isSaved);
			customerlist.add(customer);
		} while (cursor.moveToNext());
		cursor.close();
		return customerlist;
	}

	public List<Customer> getActiveCustomers(String userId) {
		List<Customer> customerlist = new ArrayList<Customer>();
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getReadableDatabase();

		Cursor cursor = sqLiteDatabase
				.query(CustomerColumns.TABLE_NAME, null,
						CustomerColumns.USER_ID + " =? and "
								+ CustomerColumns.SAVED + " =? and "
								+ CustomerColumns.CUSTOMER_STATUS + " = ?",
						new String[] { userId, "0", "Active" }, null, null,
						"lower ( " + CustomerColumns.BUSINESS_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return customerlist;
		}

		int INDEX_CUSTOMER_ID = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ID);
		int INDEX_CUSTOMER_NAME = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_NAME);
		int INDEX_BUSINESS_NAME = cursor
				.getColumnIndex(CustomerColumns.BUSINESS_NAME);
		int INDEX_CUSTOMER_PHONENO = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_PHONENO);
		int INDEX_CUSTOMER_LIC = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_LIC);
		int INDEX_CUSTOMER_ADDRESS = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ADDRESS);
		int INDEX_CUSTOMER_CITY = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_CITY);
		int INDEX_CUSTOMER_STATE = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_STATE);
		int INDEX_CUSTOMER_ZIPCODE = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ZIPCODE);
		int INDEX_CUSTOMER_MSA = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_MSA);
		int INDEX_PRICELEVEL = cursor
				.getColumnIndex(CustomerColumns.PRICE_LEVEL);
		int INDEX_CUSTOMER_STATUS = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_STATUS);

		cursor.moveToFirst();
		Customer customer = null;
		do {
			customer = new Customer();
			customer.setCustomerId(String.valueOf(cursor.getInt(INDEX_CUSTOMER_ID)));
			customer.setCustomername(cursor.getString(INDEX_CUSTOMER_NAME));
			customer.setBusinessName(cursor.getString(INDEX_BUSINESS_NAME));
			customer.setPhoneNo(cursor.getString(INDEX_CUSTOMER_PHONENO));
			customer.setLicence(cursor.getString(INDEX_CUSTOMER_LIC));
			customer.setAddress(cursor.getString(INDEX_CUSTOMER_ADDRESS));
			customer.setCity(cursor.getString(INDEX_CUSTOMER_CITY));
			customer.setState(cursor.getString(INDEX_CUSTOMER_STATE));
			customer.setZipcode(cursor.getString(INDEX_CUSTOMER_ZIPCODE));
			boolean isMsa = cursor.getInt(INDEX_CUSTOMER_MSA) > 0 ? true
					: false;
			customer.setMsaFlagOn(isMsa);
			customer.setPrice_level(cursor.getString(INDEX_PRICELEVEL));
			customer.setStatus(cursor.getString(INDEX_CUSTOMER_STATUS));
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(CustomerColumns.SAVED)) > 0 ? true : false;
			customer.setSaved(isSaved);
			customerlist.add(customer);
		} while (cursor.moveToNext());
		cursor.close();
		return customerlist;
	}

	public List<Product> getActiveProducts(String userId) {
		List<Product> productlist = new ArrayList<Product>();

		Cursor cursor = newTecDbHelper.getReadableDatabase().query(
				ProductColumns.TABLE_NAME, null,
				"user_id = ? and lower( status ) = ?",
				new String[] { userId, "Active".toLowerCase() },
				ProductColumns.PRODUCT_ID, null,
				"lower ( " + ProductColumns.PRODUCT_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return productlist;
		}

		int INDEX_PRODUCT_ID = cursor.getColumnIndex(ProductColumns.PRODUCT_ID);
		int INDEX_DEPT_NAME = cursor.getColumnIndex(ProductColumns.DEPT_NAME);
		int INDEX_PRODUCT_NAME = cursor
				.getColumnIndex(ProductColumns.PRODUCT_NAME);
		int INDEX_PRODUCT_UPC = cursor
				.getColumnIndex(ProductColumns.PRODUCT_UPC);
		int INDEX_PRODUCT_SRP = cursor
				.getColumnIndex(ProductColumns.PRODUCT_SRP);
		int INDEX_PRODUCT_PRICE = cursor
				.getColumnIndex(ProductColumns.PRODUCT_PRICE);
		int INDEX_PRODUCT_COST = cursor
				.getColumnIndex(ProductColumns.PRODUCT_COST);
		int INDEX_PRODUCT_STATUS = cursor
				.getColumnIndex(ProductColumns.PRODUCT_STATUS);
		int INDEX_ORDER_ID = cursor.getColumnIndex(ProductColumns.ORDER_ID);
		int INDEX_USER_ID = cursor.getColumnIndex(ProductColumns.USER_ID);
		int INDEX_QTY = cursor.getColumnIndex(ProductColumns.QTY);
		int INDEX_TOTAL_UNIT = cursor.getColumnIndex(ProductColumns.TOTAL_UNIT);
		int INDEX_SUB_RETAIL = cursor.getColumnIndex(ProductColumns.SUB_RETAIL);
		int INDEX_TOTAL_COST = cursor.getColumnIndex(ProductColumns.TOTAL_COST);
		cursor.moveToFirst();
		Product product = null;
		do {
			product = new Product();
			product.setProductId(String.valueOf(cursor.getInt(INDEX_PRODUCT_ID)));
			product.setDepartmentName(cursor.getString(INDEX_DEPT_NAME));
			product.setProductName(cursor.getString(INDEX_PRODUCT_NAME));
			product.setUpcCode(cursor.getString(INDEX_PRODUCT_UPC));
			product.setSuggestedPrice(cursor.getString(INDEX_PRODUCT_SRP));
			product.setPrice(cursor.getString(INDEX_PRODUCT_PRICE));
			product.setCost(cursor.getString(INDEX_PRODUCT_COST));
			product.setStatus(cursor.getString(INDEX_PRODUCT_STATUS));
			product.setOrderId(cursor.getString(INDEX_ORDER_ID));
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(ProductColumns.NewOrder)) > 0 ? true
					: false;
			product.setSaved(isSaved);
			product.setSend(isSend);
			product.setNewOrder(isNewOrder);
			product.setUserId(cursor.getString(INDEX_USER_ID));
			product.setQuantity(cursor.getString(INDEX_QTY));
			product.setUnit(cursor.getString(INDEX_TOTAL_UNIT));
			product.setSubRetail(cursor.getString(INDEX_SUB_RETAIL));
			product.setTotalCost(cursor.getString(INDEX_TOTAL_COST));
			productlist.add(product);
		} while (cursor.moveToNext());
		cursor.close();
		return productlist;
	}

	public Order getOrder(String userId, String rowId) {
		Cursor cursor = newTecDbHelper.getReadableDatabase().query(
				OrderColumns.TABLE_NAME,
				null,
				OrderColumns.USER_ID + "=? and " + OrderColumns.ORDER_ID
						+ " =?", new String[] { userId, rowId }, null, null,
				null);

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return null;
		}

		int INDEX_ORDER_ID = cursor.getColumnIndex(OrderColumns.ORDER_ID);
		int INDEX_ORDER_DATE = cursor.getColumnIndex(OrderColumns.ORDER_DATE);
		int INDEX_ORDER_AMOUNT = cursor
				.getColumnIndex(OrderColumns.ORDER_AMOUNT);
		int INDEX_CUST_ID = cursor.getColumnIndex(OrderColumns.CUST_ID);
		int INDEX_CUST_NAME = cursor.getColumnIndex(OrderColumns.BUSINESS_NAME);
		int INDEX_ORDER_BY = cursor.getColumnIndex(OrderColumns.ORDER_BY);
		int INDEX_ORDER_STATUS = cursor
				.getColumnIndex(OrderColumns.ORDER_STATUS);
		int INDEX_ORDER_NOTES = cursor.getColumnIndex(OrderColumns.ORDER_NOTES);
		int INDEX_WAREHOUSE = cursor.getColumnIndex(OrderColumns.WAREHOUSE);

		cursor.moveToFirst();
		Order order = null;
		do {
			order = new Order();
			order.setOrderId(cursor.getString(INDEX_ORDER_ID));

			DateFormat formatter = android.text.format.DateFormat
					.getDateFormat(mContext);
			long date = cursor.getLong(INDEX_ORDER_DATE);
			Date dateObj = new Date(date * 1000);
			order.setOrderDate(formatter.format(dateObj));

			// order.setOrderDate(cursor.getString(INDEX_ORDER_DATE));
			order.setAmount(cursor.getString(INDEX_ORDER_AMOUNT));
			order.setCustId(cursor.getString(INDEX_CUST_ID));
			order.setBusinessname(cursor.getString(INDEX_CUST_NAME));
			order.setOrderBY(cursor.getString(INDEX_ORDER_BY));
			order.setStatus(cursor.getString(INDEX_ORDER_STATUS));
			order.setOrderNotes(cursor.getString(INDEX_ORDER_NOTES));
			boolean isWarehouse = cursor.getInt(INDEX_WAREHOUSE) > 0 ? true
					: false;
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.SAVED)) > 0 ? true : false;
			boolean isSend = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.ISSEND)) > 0 ? true : false;
			boolean isNewOrder = cursor.getInt(cursor
					.getColumnIndex(OrderColumns.NewOrder)) > 0 ? true : false;
			order.setWarehouse(isWarehouse);
			order.setSaved(isSaved);
			order.setSend(isSend);
			order.setNewOrder(isNewOrder);
			order.setProducts(getProducts(userId,
					cursor.getString(INDEX_ORDER_ID)));

		} while (cursor.moveToNext());
		cursor.close();
		return order;
	}

	public List<Order> getOrders(String userId, String sort, boolean bChilds) {
		List<Order> orderlist = new ArrayList<Order>();
		Cursor cursor = null;
		if (sort.equalsIgnoreCase("Sort by Order")) {
			cursor = newTecDbHelper.getReadableDatabase().query(
					OrderColumns.TABLE_NAME,
					null,
					OrderColumns.USER_ID + " =? and "
							+ OrderColumns.ORDER_STATUS + " =? and ("+OrderColumns.WAREHOUSE + " =? or "+OrderColumns.SAVED +" =? )",
					new String[] { userId, "Active", "1","1" }, null, null,
					OrderColumns.ORDER_ID + " desc");
		} else if (sort.equalsIgnoreCase("Sort by Business Name")) {
			cursor = newTecDbHelper.getReadableDatabase().query(
					OrderColumns.TABLE_NAME,
					null,
					OrderColumns.USER_ID + "=? and "
							+ OrderColumns.ORDER_STATUS + " =? and ("+OrderColumns.WAREHOUSE + " =? or "+OrderColumns.SAVED +" =? )",
					new String[] { userId, "Active", "1","1" }, null, null,
					"lower ( " + OrderColumns.BUSINESS_NAME + ")");
		} else if (sort.equalsIgnoreCase("Sort by Order Date")) {
			cursor = newTecDbHelper.getReadableDatabase().query(
					OrderColumns.TABLE_NAME,
					null,
					OrderColumns.USER_ID + "=? and "
							+ OrderColumns.ORDER_STATUS + " =? and ("+OrderColumns.WAREHOUSE + " =? or "+OrderColumns.SAVED +" =? )",
					new String[] { userId, "Active", "1","1" },
					null,
					null,
					OrderColumns.ORDER_DATE + " desc , "
							+ OrderColumns.ORDER_ID + " desc");
		} else if (sort.equalsIgnoreCase("Sort by Order Status")) {
			cursor = newTecDbHelper.getReadableDatabase().query(
					OrderColumns.TABLE_NAME,
					null,
					OrderColumns.USER_ID + "=? and "
							+ OrderColumns.ORDER_STATUS + " =? and ("+OrderColumns.WAREHOUSE + " =? or "+OrderColumns.SAVED +" =? )",
					new String[] { userId, "Active", "1","1" },
					null,
					null,
					OrderColumns.SAVED + " desc, " + OrderColumns.ISSEND
							+ " desc");
		}

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return orderlist;
		}

		int INDEX_ORDER_ID = cursor.getColumnIndex(OrderColumns.ORDER_ID);
		int INDEX_ORDER_DATE = cursor.getColumnIndex(OrderColumns.ORDER_DATE);
		int INDEX_ORDER_AMOUNT = cursor
				.getColumnIndex(OrderColumns.ORDER_AMOUNT);
		int INDEX_CUST_ID = cursor.getColumnIndex(OrderColumns.CUST_ID);
		int INDEX_CUST_NAME = cursor.getColumnIndex(OrderColumns.BUSINESS_NAME);
		int INDEX_ORDER_BY = cursor.getColumnIndex(OrderColumns.ORDER_BY);
		int INDEX_ORDER_STATUS = cursor
				.getColumnIndex(OrderColumns.ORDER_STATUS);
		int INDEX_ORDER_NOTES = cursor.getColumnIndex(OrderColumns.ORDER_NOTES);
		int INDEX_WAREHOUSE = cursor.getColumnIndex(OrderColumns.WAREHOUSE);

		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			Order order = null;
			do {
				order = new Order();
				order.setOrderId(cursor.getString(INDEX_ORDER_ID));

				DateFormat formatter = android.text.format.DateFormat
						.getDateFormat(mContext);
				long date = cursor.getLong(INDEX_ORDER_DATE);
				Date dateObj = new Date(date * 1000);
				order.setOrderDate(formatter.format(dateObj));

				// order.setOrderDate(cursor.getString(INDEX_ORDER_DATE));
				order.setAmount(cursor.getString(INDEX_ORDER_AMOUNT));
				order.setCustId(cursor.getString(INDEX_CUST_ID));
				order.setBusinessname(cursor.getString(INDEX_CUST_NAME));
				order.setOrderBY(cursor.getString(INDEX_ORDER_BY));
				order.setStatus(cursor.getString(INDEX_ORDER_STATUS));
				order.setOrderNotes(cursor.getString(INDEX_ORDER_NOTES));
				boolean isWarehouse = cursor.getInt(INDEX_WAREHOUSE) > 0 ? true
						: false;
				boolean isSaved = cursor.getInt(cursor
						.getColumnIndex(OrderColumns.SAVED)) > 0 ? true : false;
				boolean isSend = cursor.getInt(cursor
						.getColumnIndex(OrderColumns.ISSEND)) > 0 ? true
						: false;
				boolean isNewOrder = cursor.getInt(cursor
						.getColumnIndex(OrderColumns.NewOrder)) > 0 ? true
						: false;
				order.setWarehouse(isWarehouse);
				order.setSaved(isSaved);
				order.setSend(isSend);
				order.setNewOrder(isNewOrder);
				order.setProducts(bChilds ? getProducts(userId,
						cursor.getString(INDEX_ORDER_ID)) : null);
				orderlist.add(order);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return orderlist;
	}

	public List<Customer> getCustomers(String userId) {
		List<Customer> customerlist = new ArrayList<Customer>();
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getReadableDatabase();

		Cursor cursor = sqLiteDatabase.query(CustomerColumns.TABLE_NAME, null,
				CustomerColumns.USER_ID + "=?", new String[] { userId }, null,
				null, "lower ( " + CustomerColumns.BUSINESS_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return customerlist;
		}

		int INDEX_CUSTOMER_ID = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ID);
		int INDEX_CUSTOMER_NAME = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_NAME);
		int INDEX_BUSINESS_NAME = cursor
				.getColumnIndex(CustomerColumns.BUSINESS_NAME);
		int INDEX_CUSTOMER_PHONENO = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_PHONENO);
		int INDEX_CUSTOMER_LIC = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_LIC);
		int INDEX_CUSTOMER_ADDRESS = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ADDRESS);
		int INDEX_CUSTOMER_CITY = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_CITY);
		int INDEX_CUSTOMER_STATE = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_STATE);
		int INDEX_CUSTOMER_ZIPCODE = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_ZIPCODE);
		int INDEX_CUSTOMER_MSA = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_MSA);
		int INDEX_PRICELEVEL = cursor
				.getColumnIndex(CustomerColumns.PRICE_LEVEL);
		int INDEX_CUSTOMER_STATUS = cursor
				.getColumnIndex(CustomerColumns.CUSTOMER_STATUS);

		cursor.moveToFirst();
		Customer customer = null;
		do {
			customer = new Customer();
			customer.setCustomerId(cursor.getString(INDEX_CUSTOMER_ID));
			customer.setCustomername(cursor.getString(INDEX_CUSTOMER_NAME));
			customer.setBusinessName(cursor.getString(INDEX_BUSINESS_NAME));
			customer.setPhoneNo(cursor.getString(INDEX_CUSTOMER_PHONENO));
			customer.setLicence(cursor.getString(INDEX_CUSTOMER_LIC));
			customer.setAddress(cursor.getString(INDEX_CUSTOMER_ADDRESS));
			customer.setCity(cursor.getString(INDEX_CUSTOMER_CITY));
			customer.setState(cursor.getString(INDEX_CUSTOMER_STATE));
			customer.setZipcode(cursor.getString(INDEX_CUSTOMER_ZIPCODE));
			boolean isMsa = cursor.getInt(INDEX_CUSTOMER_MSA) > 0 ? true
					: false;
			customer.setMsaFlagOn(isMsa);
			customer.setPrice_level(cursor.getString(INDEX_PRICELEVEL));
			customer.setStatus(cursor.getString(INDEX_CUSTOMER_STATUS));
			boolean isSaved = cursor.getInt(cursor
					.getColumnIndex(CustomerColumns.SAVED)) > 0 ? true : false;
			customer.setSaved(isSaved);
			customerlist.add(customer);
		} while (cursor.moveToNext());
		cursor.close();
		return customerlist;
	}

	public List<Department> getDepartments(String userId) {
		List<Department> departments = new ArrayList<Department>();
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.query(DepartmentColumns.TABLE_NAME,
				null, DepartmentColumns.USER_ID + "=?",
				new String[] { userId }, DepartmentColumns.DEPT_NAME, null,
				"lower ( " + DepartmentColumns.DEPT_NAME + ")");

		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return departments;
		}

		int INDEX_DEPT_ID = cursor.getColumnIndex(DepartmentColumns.DEPT_ID);
		int INDEX_DEPT_NAME = cursor
				.getColumnIndex(DepartmentColumns.DEPT_NAME);
		int INDEX_DEPT_STATUS = cursor.getColumnIndex(DepartmentColumns.STATUS);

		cursor.moveToFirst();
		Department department = null;
		do {
			department = new Department();
			department.setDeptId(String.valueOf(cursor.getString(INDEX_DEPT_ID)));
			department.setDeptName(cursor.getString(INDEX_DEPT_NAME));
			department.setStatus(cursor.getString(INDEX_DEPT_STATUS));
			departments.add(department);
		} while (cursor.moveToNext());
		cursor.close();
		return departments;
	}

	public LoginInfo isUserExists(String userName) {
		LoginInfo loginInfo = null;
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getReadableDatabase();
		if (sqLiteDatabase != null) {
			Cursor cursor = sqLiteDatabase.query(LoginColumns.TABLE_NAME, null,
					"username=?", new String[] { userName }, null, null, null);
			if (cursor != null) {
				if (cursor.getCount() < 1) {
					cursor.close();
					return null;
				}
				cursor.moveToFirst();

				loginInfo = new LoginInfo(cursor.getColumnName(cursor
						.getColumnIndex(LoginColumns.USERNAME)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.PASSWORD)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.USER_ID)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.NAME)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.ADDRESS)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.CITY)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.STATE)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.PHONE)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.LICENCE)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.CREATED_DATE)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.LAST_DATE)));
				cursor.close();
			}
		}
		return loginInfo;
	}

	public LoginInfo getLoginInfo(String userId) {
		LoginInfo loginInfo = null;
		SQLiteDatabase sqLiteDatabase = newTecDbHelper.getReadableDatabase();
		if (sqLiteDatabase != null) {
			Cursor cursor = sqLiteDatabase.query(LoginColumns.TABLE_NAME, null,
					"user_id = ?", new String[] { userId }, null, null, null);
			if (cursor != null) {
				if (cursor.getCount() < 1) {
					cursor.close();
					return null;
				}
				cursor.moveToFirst();

				loginInfo = new LoginInfo(cursor.getString(cursor
						.getColumnIndex(LoginColumns.USERNAME)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.PASSWORD)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.USER_ID)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.NAME)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.ADDRESS)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.CITY)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.STATE)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.PHONE)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.LICENCE)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.CREATED_DATE)),
						cursor.getString(cursor
								.getColumnIndex(LoginColumns.LAST_DATE)));
				cursor.close();
			}
		}
		return loginInfo;
	}

	private static class ProductColumns implements BaseColumns {

		private static final String DEPT_NAME = "dept_name";
		private static final String PRODUCT_ID = "product_id";
		private static final String PRODUCT_NAME = "product_name";
		private static final String PRODUCT_UPC = "upc_code";
		private static final String PRODUCT_SRP = "srp";
		private static final String PRODUCT_PRICE = "price";
		private static final String PRODUCT_COST = "cost";
		private static final String PRODUCT_STATUS = "status";
		private static final String TABLE_NAME = "Products";
		private static final String ORDER_ID = "order_id";
		private static final String BUSINESS_NAME = "business_name";
		private static final String SAVED = "saved";
		private static final String ISSEND = "issend";
		private static final String NewOrder = "neworder";
		private static final String USER_ID = "user_id";
		private static final String QTY = "quantity";
		private static final String TOTAL_UNIT = "total_unit";
		private static final String SUB_RETAIL = "sub_retail";
		private static final String TOTAL_COST = "extn";
		private static final String CREATED_DATE = "created_date";
		private static final String LAST_DATE = "last_date";

		private static final String DATABASE_CREATE = "create table Products(_id integer primary key autoincrement,"
				+ "product_id integer not null,"
				+ "dept_name text,product_name text,"
				+ "upc_code text,srp text,"
				+ "price text ,cost text,"
				+ "status text ,order_id int, business_name text, saved text, issend text, neworder text, user_id text,"
				+ "quantity integer, total_unit integer, sub_retail integer, extn integer  , created_date text,last_date text);";
	}

	private static class CustomerColumns implements BaseColumns {
		private static final String TABLE_NAME = "Customers";
		private static final String CUSTOMER_ID = "cust_id";
		private static final String CUSTOMER_NAME = "cust_name";
		private static final String BUSINESS_NAME = "business_name";
		private static final String CUSTOMER_PHONENO = "phone";
		private static final String CUSTOMER_LIC = "lic";
		private static final String CUSTOMER_ADDRESS = "address";
		private static final String CUSTOMER_CITY = "city";
		private static final String CUSTOMER_STATE = "state";
		private static final String CUSTOMER_ZIPCODE = "zipcode";
		private static final String CUSTOMER_MSA = "msa";
		private static final String PRICE_LEVEL = "price_level";
		private static final String CUSTOMER_STATUS = "status";
		private static final String SAVED = "saved";
		private static final String USER_ID = "user_id";
		private static final String CREATED_DATE = "created_date";
		private static final String LAST_DATE = "last_date";

		private static final String DATABASE_CREATE = "create table Customers(_id integer primary key autoincrement,"
				+ "cust_id integer,"
				+ "cust_name text,business_name text, phone text,"
				+ "lic text,address text,"
				+ "city text ,"
				+ "state text,zipcode text,"
				+ "msa text,price_level text,"
				+ "status text, saved text, user_id text  , created_date text,last_date text);";
	}

	private static class OrderColumns implements BaseColumns {

		private static final String TABLE_NAME = "Orders";
		private static final String ORDER_ID = "order_id";
		private static final String ORDER_DATE = "order_date";
		private static final String ORDER_AMOUNT = "amount";
		private static final String CUST_ID = "cust_id";
		private static final String BUSINESS_NAME = "business_name";
		private static final String ORDER_BY = "order_by";
		private static final String ORDER_STATUS = "status";
		private static final String ORDER_NOTES = "order_notes";
		private static final String WAREHOUSE = "warehouse";
		private static final String USER_ID = "user_id";
		private static final String CREATED_DATE = "created_date";
		private static final String LAST_DATE = "last_date";
		private static final String SAVED = "saved";
		private static final String ISSEND = "issend";
		private static final String NewOrder = "neworder";

		private static final String DATABASE_CREATE = "create table Orders(_id integer primary key autoincrement,"
				+ "order_id integer,"
				+ "order_date integer,amount text,"
				+ "cust_id text,business_name text,"
				+ "order_by text,status text, order_notes txt,"
				+ "warehouse text, saved text, issend text, neworder text, user_id text , created_date text,last_date text);";
	}

	private static class LoginColumns implements BaseColumns {

		private static final String TABLE_NAME = "Login";
		private static final String USERNAME = "username";
		private static final String PASSWORD = "password";
		private static final String USER_ID = "user_id";
		private static final String NAME = "name";
		private static final String PHONE = "phone";
		private static final String ADDRESS = "address";
		private static final String CITY = "city";
		private static final String STATE = "state";
		private static final String LICENCE = "licence";
		private static final String CREATED_DATE = "created_date";
		private static final String LAST_DATE = "last_date";

		private static final String DATABASE_CREATE = "create table Login(_id integer primary key autoincrement,"
				+ "username text, password text, user_id text, name text , phone text, address text, city text,"
				+ "state text, licence text,created_date text,last_date text);";
	}

	private static class DepartmentColumns implements BaseColumns {

		private static final String TABLE_NAME = "Departments";
		private static final String DEPT_ID = "dept_id";
		private static final String DEPT_NAME = "dept_name";
		private static final String STATUS = "status";
		private static final String USER_ID = "user_id";
		private static final String CREATED_DATE = "created_date";
		private static final String LAST_DATE = "last_date";

		private static final String DATABASE_CREATE = "create table Departments(_id integer primary key autoincrement,"
				+ "dept_id integer, dept_name text, status text , user_id text , created_date text , last_date text);";
	}

	/*
	 * private static class NewOrderColumns implements BaseColumns { private
	 * static final String TABLE_NAME = "Neworder"; private static final String
	 * PRODUCT_ID = "product_id"; private static final String QTY = "quantity";
	 * private static final String STATUS = "status"; private static final
	 * String ISSAVED = "issaved";
	 * 
	 * private static final String DATABASE_CREATE =
	 * "create table Neworder(_id integer primary key autoincrement," +
	 * "product_id integer, quantity integer, status text, issaved integer ,  created_date text , last_date text);"
	 * ; }
	 */
}
