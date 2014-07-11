package com.example.uscitizen;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayAudio extends Service{
	private static final String LOGCAT = null;
	MediaPlayer objPlayer = null;

	public void onCreate(){
	    super.onCreate();
	    Log.d(LOGCAT, "Service Started!");       
	}

	public int onStartCommand(Intent intent, int flags, int startId){
		
		String message = intent.getExtras().getString(MainActivity.EXTRA_MESSAGE);
		Resources res = getResources();	    
		int id = Integer.parseInt(message);
		String fileName = "track"+String.format("%02d", id);
//	    Log.d("INFO",fileName);
	    int fileId = res.getIdentifier(fileName, "raw", getPackageName());
	    objPlayer = MediaPlayer.create(this,fileId);
	    if (!objPlayer.isPlaying()){
	    	objPlayer.start();
	    }
	    Log.d(LOGCAT, "Media Player started!");
	    if(objPlayer.isLooping() != true){
	        Log.d(LOGCAT, "Problem in Playing Audio");
	    }
	    return 1;
	}

	public void onStop(){
		objPlayer.stop();
		objPlayer.release();
	}
	
	public void onPause(){
		objPlayer.stop();
		objPlayer.release();
	}
	
	public void onDestroy(){
		objPlayer.stop();
		objPlayer.release();
	}

	@Override
	public IBinder onBind(Intent objIndent) {
	    return null;
	}
}
