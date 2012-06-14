package com.ntu.rose;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{

	static final String dbName = "ROSEDatabase";
	
	static final String tableSearch = "SearchHistoryTable";
	static final String colUsername = "Username";
	static final String colKeyword = "SearchKeyword";
	static final String colSearchTime = "SearchTime";
	
	static final String tableBrowse = "BrowseHistoryTable";
//	static final String colUsername = "Username";
	static final String colItemID = "ItemID";
	static final String colBrowseTime = "BrowseTime";
	
	static final String tableBookmark = "BookmarkTable";
//	static final String colUsername = "Username";
//	static final String colItemID = "ItemID";
	static final String colBookmarkTime = "BookmarkTime";
	
	public Database(Context context) {
		super(context, dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + tableSearch + 
				   " (" + colUsername + " TEXT, " + 
				   		  colKeyword + " TEXT, " +
				   		  colSearchTime + " TEXT NOT NULL, " +
				          "PRIMARY KEY (" + colUsername + ", " + colKeyword +"));");
		
		db.execSQL("CREATE TABLE " + tableBrowse +
				   " (" + colUsername + " TEXT, " + 
						  colItemID + " TEXT, " +
				   		  colBrowseTime + " TEXT, " +
				   		  "PRIMARY KEY (" + colUsername + ", " + colItemID +"));");
		
		db.execSQL("CREATE TABLE " + tableBookmark +
				   " (" + colUsername + " TEXT, " + 
						  colItemID + " TEXT, " +
						  colBookmarkTime + " TEXT, " +
				   		  "PRIMARY KEY (" + colUsername + ", " + colItemID +"));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + tableSearch);
		db.execSQL("DROP TABLE IF EXISTS " + tableBrowse);
		db.execSQL("DROP TABLE IF EXISTS " + tableBookmark);

		onCreate(db);
	}
	
	/**
	 * execute SQLite query to read data from ROSE database
	 * 
	 * @param query The query to be executed
	 * @param args The arguments for the query
	 * 
	 * @return Cursor pointing to the result of the query
	 * */
	public Cursor read(String query, String[] args){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery(query, args);
		db.close();
		return cur;
	}

	static final int ACTION_INSERT = 0;
	static final int ACTION_DELETE = 1;
	static final int ACTION_UPDATE = 2;

	/**
	 * write data to ROSE database
	 * 
	 * @param action The action to be performed: insert, delete or update 
	 * @param table The table to be written
	 * @param cv The ContentValues object if the action is insert or update
	 * @param clause The whereClause string if the action is delete or update
	 * @param args The whereArgs string if the action is delete or update
	 * 
	 * @return Row info corresponding to the return values of each action
	 * */
	public long write(int action, String table, ContentValues cv, String clause, String[] args){
		SQLiteDatabase db = this.getWritableDatabase();
		long row = 0;
		switch(action){
			case ACTION_INSERT:
				row = db.insert(table, colUsername, cv);
				break;
			case ACTION_DELETE:
				row = db.delete(table, clause, args);
				break;
			case ACTION_UPDATE:
				row = db.update(table, cv, clause, args);
				break;
		}
		db.close();
		return row;
	}
}
