package com.androidstorageapp.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author DEVEN Database Handler class to manage database activities
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	/**
	 * Database Version
	 */
	private static final int DATABASE_VERSION = 2;
	/**
	 * Database Name
	 */
	private static final String DATABASE_NAME = "sqlitestorage.db";

	/**
	 * Database Tables & Columns
	 */

	private static final String KEY_ID = "id";

	/**
	 * Table Data
	 */
	public static final String TABLE_DATA = "data";
	private static final String KEY_MESSAGE = "MESSAGE";

	/**
	 * Table My Past Data
	 */
	public static final String TABLE_MY_PAST_DATA = "my_past_data";
	private static final String KEY_PAST_DATA = "PAST_DATA";

	private Context context;

	private SQLiteDatabase m_db;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out
				.println("###############################Creating tables########################");
		String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_MESSAGE + " TEXT)";

		String CREATE_PAST_TABLE = "CREATE TABLE " + TABLE_MY_PAST_DATA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_PAST_DATA + " TEXT)";

		db.execSQL(CREATE_DATA_TABLE);
		db.execSQL(CREATE_PAST_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/**
		 * Drop older table if existed
		 */
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_PAST_DATA);

		/**
		 * Create tables again
		 */
		onCreate(db);
	}

	/**
	 * Opens the Database.
	 */
	public void openInternalDB() {
		if ((m_db == null) || (m_db.isOpen() == false)) {
			m_db = this.getWritableDatabase();
		}
	}

	/**
	 * Closes the Database.
	 */
	public void closeDB() {
		if (m_db != null) {
			m_db.close();
			m_db = null;
		}
	}

	/**
	 * Deletes one Record from the Table with given table Name & Condition.
	 * 
	 * @param tableName
	 * @param whereConition
	 * @param values
	 */
	public void deleteRow(String tableName, String whereConition,
			String[] values) {
		SQLiteDatabase db = this.getWritableDatabase();
		int deletedItems = 0;
		deletedItems = db.delete(tableName, whereConition, values);
		Log.d("", "===No. of deleted items===" + deletedItems);
		db.close();
	}

	/**
	 * Deletes all table entries of the table with given Table Name.
	 * 
	 * @param TABLE_NAME
	 */
	public void deleteTableEntries(String TABLE_NAME) {
		int rows = m_db.delete(TABLE_NAME, null, null);
	}

	/**
	 * Adds Message to Database.
	 */
	public void addMessage(String message) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_MESSAGE, message);
		db.insert(TABLE_DATA, null, values);
		db.close();
	}

	/**
	 * 
	 * @param pastData
	 *            Adds Past Data in DB
	 */
	public void addPastData(String pastData) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PAST_DATA, pastData);
		db.insert(TABLE_MY_PAST_DATA, null, values);
		db.close();
	}

	/**
	 * 
	 * @return numbers of rows in Table Data
	 */
	public int getCursorCount() {
		String selectQuery = "SELECT  * FROM " + TABLE_DATA;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}

	/**
	 * 
	 * @return Message list from database.
	 */
	public List<String> getMessageList() {

		List<String> messageList = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + TABLE_DATA;
		SQLiteDatabase db = this.getWritableDatabase();
		String message = "";

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				message = cursor.getString(1);
				messageList.add(message);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return messageList;
	}

	/**
	 * 
	 * @return List of stored data from Shared Preference and SQLite database
	 */
	public List<String> getPastDataList() {

		List<String> pastDataList = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + TABLE_MY_PAST_DATA;
		SQLiteDatabase db = this.getWritableDatabase();
		String pastData = "";

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				pastData = cursor.getString(1);
				pastDataList.add(pastData);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return pastDataList;
	}

}
