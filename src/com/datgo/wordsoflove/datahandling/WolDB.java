package com.datgo.wordsoflove.datahandling;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class WolDB extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "wolDB";
	private static final int DATABASE_VERSION = 2;
	
	public WolDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);	
		
		// you can use an alternate constructor to specify a database location
		// (such as a folder on the sd card)
		// you must ensure that this folder is available and you have permission
		// to write to it
		//super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
		
		// call this method to force a database overwrite if the version number 
		// is below a certain threshold
		//setForcedUpgradeVersion(2);
	}

	
	public void insertPhrase(String phraseString)
	{
		
		SQLiteDatabase db = getWritableDatabase();
		String sql = "INSERT INTO Phrases (Phrase) VALUES "
				+ "(\""+ phraseString +"\")";
		db.execSQL(sql);
			
	}


	public Cursor getRandomPhrase() {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"Phrase"}; 
		String sqlTables = "Phrases";
		String orderBy = "RANDOM()";
		String limit = "1"; 
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null,
				null, null, orderBy, limit);

		c.moveToFirst();
		return c;

	}



}


	

