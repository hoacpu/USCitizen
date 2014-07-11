package com.example.uscitizen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.uscitizen.DisplayChapterActivity;
import com.example.uscitizen.MainActivity;
import com.example.uscitizen.MyArrayAdapter;
import com.example.uscitizen.R;
import com.example.uscitizen.DBAdapter;
import com.example.uscitizen.PlayAudio;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	public static final int DIALOG_ALERT = 10;
	private String m_dlgMsg;
	public static final String EXTRA_MESSAGE = "com.example.uscitizen";
	public static final String EXTRA_MESSAGE2 = "com.example.uscitizen2";
	public static final String EXTRA_MESSAGE3 = "com.example.uscitizen3";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		ListView lv = (ListView) findViewById(R.id.listView);		
         
        DBAdapter db = DBSessionMgr.getDatabaseSes(this); 
        
//        //---get all contacts---
        
        ArrayList<String> items = new ArrayList<String>();
        final Map<String,String> answers = new HashMap<String,String>();
        final Map<String,String> questions = new HashMap<String,String>();
        final Map<String,String> vnanswers = new HashMap<String,String>();
        final Map<String,String> vnquestions = new HashMap<String,String>();
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {          
//              DisplayContact(c);              
            	String title = c.getString(1);
            	items.add(title);
            	questions.put(c.getString(0),c.getString(1));
            	answers.put(c.getString(0),c.getString(2));           	
            	vnquestions.put(c.getString(0),c.getString(4));
            	vnanswers.put(c.getString(0),c.getString(5));
            	
//            	Log.d("INFO", c.getString(1));
            } while (c.moveToNext());
        }
        

        db.close();
        
        String[] dataSource = new String[items.size()];
        dataSource = items.toArray(dataSource);
//	    String[] dataSource = {"test1","test2"};    
	    ListAdapter theAdapter = new MyArrayAdapter(this, dataSource);	    
	    lv.setAdapter(theAdapter);
	   	 
	 // React to user clicks on item
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


			public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
	                                 long id) {
	        	Intent intent = new Intent(MainActivity.this, DisplayChapterActivity.class);
	        	 //convert int to String
	        	Integer num = new Integer(++position);
		     	String message = Integer.toString(position);	     	
		     	String answerValue = answers.get(message)+ "\n\n" +  vnanswers.get(message) ;
		     	String questionValue = questions.get(message) + "\n\n" + vnquestions.get(message);
		     	
		    	intent.putExtra(EXTRA_MESSAGE, message);
		    	intent.putExtra(EXTRA_MESSAGE2, answerValue);	
		    	intent.putExtra(EXTRA_MESSAGE3, questionValue);
		    	
		    	Intent objIntent = new Intent(MainActivity.this, PlayAudio.class);
			    Bundle myBundle = new Bundle();  	
		   	  	myBundle.putString(EXTRA_MESSAGE,message);
		   	  	objIntent.putExtras(myBundle);	
		   	  	startService(objIntent);
		 	    // startActivity causes the Activity to start
		   	  	
		   	  	
//		    	startActivity(intent);
		    	//we use startActivityResult when we expect a result to be sent back
		    	final int result = 1;
		    	startActivityForResult(intent,result);
	        	     		
	         }
	    });
	}

	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);		
		TextView resultTxtView = (TextView) findViewById(R.id.textView3);		
		resultTxtView.setText("Done");
//		Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
	}



	public void playAudio(View view,String message) {
	    Intent objIntent = new Intent(this, PlayAudio.class);

	    startService(objIntent);
    }
    
    public void stopAudio(View view) {
    	Intent objIntent = new Intent(this, PlayAudio.class);
	    stopService(objIntent);    
    }
    
    public void DisplayContact(Cursor c)
    {
        Toast.makeText(this, "test",Toast.LENGTH_LONG).show();        
    }
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.enable_voice){
			return true;
		} else if (id == R.id.enable_vietnam){
			return true;
		}
		
		
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
