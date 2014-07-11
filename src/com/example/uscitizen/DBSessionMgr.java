package com.example.uscitizen;

import android.content.Context;

public class DBSessionMgr {
	
	static DBAdapter db = null;
	
	public static DBAdapter getDatabaseSes (Context context)
	{
		if (db == null) {
			return new DBAdapter(context);
		}
		
		return db;
	}

}
