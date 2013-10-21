package com.example.l;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class DisplayMessageActivity2 extends Activity {
	 MediaPlayer player = new MediaPlayer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		// Show the Up button in the action bar.
		//setupActionBar();
///////////////////// READ FILE FROM ASSETS
//		String x;
//		try{ 
//			InputStream in = getAssets().open("asd.txt");
//			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//			StringBuilder sb = new StringBuilder(512);
//			String line;
//			while((line = reader.readLine()) != null){
//			    sb.append(line);
//			}
//			reader.close();
//			x= sb.toString();
//		}
//		catch(IOException e){ e.printStackTrace(); 
//		x= "NONE";
//		}
///////////////////// FIND FILE LIST IN ASSETS
 		String joined;
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_display_message_activity2, null);
         
        // Find the ScrollView 
        ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView1);

        // Create a LinearLayout element
        LinearLayout lLayout = new LinearLayout(this);
        lLayout.setOrientation(LinearLayout.VERTICAL);
		 
 
			File root = android.os.Environment.getExternalStorageDirectory();               
			 
			File dir = new File (root.getAbsolutePath() + "/music_source");
			
			String[] filelist = dir.list();
 			int size = filelist.length;
			 
 			Arrays.sort(filelist);
	        for(int i=0;i<size;i++){
	        	/*ScrollView scrollView = (ScrollView)getLayoutInflater().inflate(R.layout.activity_display_message_activity2)
	        	RelativeLayout layout = (RelativeLayout) scrollView.findViewById(R.id.rel_all_football);
	            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            */
	        	final String nowfile=filelist[i];
	            Button b = (Button) new Button(this);
	             
	            b.setId(i);  
	            lLayout.addView(b); 
	            //Log.d("TAG","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
	            b.setText(nowfile);
	            b.setOnClickListener(new OnClickListener() {
	             
	            public void onClick(View a) {
	            	player.reset();
	            	player.stop();
	            	new AlertDialog.Builder(DisplayMessageActivity2.this)
	                .setTitle(nowfile)
	                .setMessage("Practice this song? :) ")
	                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) { 
	                        // continue with delete
	                    	player.stop();
	                    	Intent intent = new Intent(DisplayMessageActivity2.this, Play.class);
	    	            	intent.putExtra("This", nowfile);
	    	                startActivity(intent);
	                    }
	                 })
	                .setNegativeButton("No", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) { 
	                        // do nothing
	                    	player.stop();
	                    	
	                    }
	                 })
	                 .show();
	            	 
	             	FileInputStream input;
	            	 
	            	File root = android.os.Environment.getExternalStorageDirectory(); 
	            	String src= root.getAbsolutePath() + "/music_source/"+nowfile;

	                 try
	                {
	                	 player.setDataSource(src);
	                  
	                     player.prepare();
	                     player.start();
	                }
	                catch(IOException e)
	                { 
	                	}   
	            	 
	                 }
	        });
	        }
			joined = java.util.Arrays.toString( filelist );
			 
		//sc.addView(lLayout);
///////////////////// FIND FILE LIST IN ASSETS

/*        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(joined);
        setContentView(textView);*/
		sv.addView(lLayout);
		setContentView(v);
 		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message_activity2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	protected void onStop() {
		super.onStop();
		player.stop();
		}


}
