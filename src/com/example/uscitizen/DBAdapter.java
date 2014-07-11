package com.example.uscitizen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    public static final String KEY_ROWID = "id";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_AUDIO = "audio";
    public static final String KEY_VNQUESTION = "vnquestion";
    public static final String KEY_VNANSWER = "vnanswer";
    public static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "USCitizenship";
    private static final String DATABASE_TABLE = "Test";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table Test (id integer primary key, "
        + "question text not null, answer text not null, audio text not null, vnquestion text not null, vnanswer text not null);";
        
    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
    	this.context = ctx;
        DBHelper = new DatabaseHelper(context);
        
        try {   
        	
       	
        	String destPath = "/data/data/"+ ctx.getPackageName() + "/databases/USCitizenship";
        	File f = new File(destPath);        	
        	boolean dbExist = DBHelper.checkDataBase(destPath);
            dbExist = false;
        	if(dbExist){
        		//do nothing - database already exist
        	}else{
        		//Open your local db as the input stream
            	InputStream myInput = ctx.getAssets().open("USCitizenship");
            	DBHelper.CopyDB( myInput, 
					new FileOutputStream(destPath));
        	}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	try {
        		
        		String tableName = SQLiteDatabase.findEditTable("Test");
        		if (!tableName.equals("Test")){
        			db.execSQL(DATABASE_CREATE);
        		}
        		
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
//            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
//                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Test");
            onCreate(db);
        }
        
        private boolean checkDataBase(String path){
         	 
        	SQLiteDatabase checkDB = null;
     
        	try{
        		String myPath = path;
        		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     
        	}catch(SQLiteException e){
     
        		//database does't exist yet.
     
        	}
     
        	if(checkDB != null){
     
        		checkDB.close();
     
        	}
     
        	return checkDB != null ? true : false;
        }
            
        public void CopyDB(InputStream inputStream, OutputStream outputStream) 
        throws IOException {
            //---copy 1K bytes at a time---
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        }
    }    

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a contact into the database---
    public long insertRecord(String question, String answer, String audio) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_QUESTION, question);
        initialValues.put(KEY_ANSWER, answer);
        initialValues.put(KEY_AUDIO, audio);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteContact(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllContacts() 
    {
//		ArrayList<HashMap<String, String>> contactArrayList = new ArrayList<HashMap<String, String>>();
		
		String selectQuery = "SELECT * FROM Test";
		
//		SQLiteDatabase database = this.getWritableDatabase();
		String pathDB = db.getPath();
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		

		return cursor;

    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_QUESTION, KEY_ANSWER, KEY_AUDIO,KEY_VNQUESTION, KEY_VNANSWER }, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateContact(long rowId, String question, String answer, String audio) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_ANSWER, answer);
        args.put(KEY_QUESTION, question);
        args.put(KEY_AUDIO,audio);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
