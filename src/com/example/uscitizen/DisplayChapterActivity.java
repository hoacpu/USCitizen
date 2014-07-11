package com.example.uscitizen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;





import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DisplayChapterActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_display_chapter);
	    // Create the text view
	    TextView textView = (TextView) this.findViewById(R.id.textView1);
	    textView.setMovementMethod(new ScrollingMovementMethod());
	    
	    TextView textView3 = (TextView) this.findViewById(R.id.textView3);
	    textView.setMovementMethod(new ScrollingMovementMethod());
	    
	    Intent i = getIntent();
	    String message = i.getExtras().getString(MainActivity.EXTRA_MESSAGE);
	    String messageTxt = i.getExtras().getString(MainActivity.EXTRA_MESSAGE2);
	    String messageTxt3 = i.getExtras().getString(MainActivity.EXTRA_MESSAGE3);
	    
////	    Log.d("INFO","message "+message);
//	    //some how mappy message to ke1 file
//	    
//	    Resources res = getResources();	    
//	    int id = Integer.parseInt(message);
//		id++;		;
//		message = Integer.toString(id); 
	    

//	    row.moveToFirst();
	    
//	    textView.setText(row.getString(2));
	    textView.setText(messageTxt);
	    textView3.setText(messageTxt3);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_chapter, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
		    	Intent i = getIntent();
			    String message = i.getExtras().getString(MainActivity.EXTRA_MESSAGE);
		    	Intent objIntent = new Intent(DisplayChapterActivity.this, PlayAudio.class);
			    Bundle myBundle = new Bundle();  	
		   	  	myBundle.putString(MainActivity.EXTRA_MESSAGE,message);
		   	  	objIntent.putExtras(myBundle);	
		   	  	stopService(objIntent);
		   	  	
		   	  	setResult(RESULT_OK, i);
		        finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
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
			View rootView = inflater.inflate(R.layout.fragment_display_chapter,
					container, false);
			return rootView;
		}
	}

}
