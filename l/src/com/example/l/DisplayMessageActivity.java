package com.example.l;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class DisplayMessageActivity extends Activity {

    String response;

	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DefaultHttpClient client = new DefaultHttpClient(); 
        try {
            
            String getURL = "http://ec2-75-101-223-173.compute-1.amazonaws.com/piano/2";
            HttpGet get = new HttpGet(getURL);
            HttpResponse responseGet = client.execute(get); 
            final HttpEntity resEntityGet = responseGet.getEntity(); 
            String result = EntityUtils.toString(resEntityGet);
            
            
			
            String[] result2 = result.split("123123,123123");
			File root = android.os.Environment.getExternalStorageDirectory();               

			final File dir = new File (root.getAbsolutePath() + "/music_source");
            Set<String> Result2 = new HashSet<String>(Arrays.asList(result2)); 
			String[] filelist = dir.list();
            Set<String>   Filelist = new HashSet<String>(Arrays.asList(filelist)); 
            Result2.removeAll(Filelist);
            String[] strArr = Result2.toArray(new String[Result2.size()]);
            Arrays.sort(strArr);
            
            Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", Arrays.toString( Result2.toArray())   ); //Integer.toString(newArray.length));
            
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            // Show the Up button in the action bar.
	            getActionBar().setDisplayHomeAsUpEnabled(true);
	        }
        String joined;
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_display_message_activity1, null);
        
        // Find the ScrollView 
        ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView1);

        // Create a LinearLayout element
        LinearLayout lLayout = new LinearLayout(this);
        lLayout.setOrientation(LinearLayout.VERTICAL);
        
		//sv.addView(lLayout);
		//setContentView(v); 
 
 			  
			 Result2.toArray();
  			int size = filelist.length;
			 
        	int i=1;
	        for(final String nowfile:strArr){/////RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
	        	/*ScrollView scrollView = (ScrollView)getLayoutInflater().inflate(R.layout.activity_display_message_activity2)
	        	RelativeLayout layout = (RelativeLayout) scrollView.findViewById(R.id.rel_all_football);
	            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            */
	        	//final String nowfile=filelist[i];
	            Button b = (Button) new Button(this);
	             
	            b.setId(i);  
	            i+=1;
	            lLayout.addView(b); 
	            //Log.d("TAG","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
	            b.setText(nowfile);
	            b.setOnClickListener(new OnClickListener() {
	             
	            public void onClick(View a) {
	        
	            	new AlertDialog.Builder(DisplayMessageActivity.this)
	                .setTitle(nowfile)
	                .setMessage("Download this song? :) ")
	                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) { 
	                        // continue with delete
 	                    	
	    	                try{
	    	                if (resEntityGet != null) { 
	    	                    // do something with the response
	    	                	 //response = EntityUtils.toString(resEntityGet);
	    	                	///////////////////// DOWNLOAD FILE////////////////////////////
	    	                	
	    	                    URL url = new URL("http://ec2-75-101-223-173.compute-1.amazonaws.com/piano/1/"+nowfile); //you can write here any link
	    	                    File file = new File(dir, nowfile);
	    	                    URLConnection ucon = url.openConnection();
	    	                    InputStream is = ucon.getInputStream();
	    	                    BufferedInputStream bis = new BufferedInputStream(is);
	    	                    ByteArrayBuffer baf = new ByteArrayBuffer(5000);
	    	                    int current = 0;
	    	                    while ((current = bis.read()) != -1) {
	    	                       baf.append((byte) current);
	    	                    }
	    	                    FileOutputStream fos = new FileOutputStream(file);
	    	                    fos.write(baf.toByteArray());
	    	                    fos.flush();
	    	                    fos.close();

	    	                    client.getConnectionManager().shutdown();
	    	                    response="ASDASD";
	    	                    Toast.makeText(getApplicationContext(), "Done downloading "+nowfile, Toast.LENGTH_SHORT).show();

	    	                	///////////////////// DOWNLOAD FILE////////////////////////////
	    	                    
	    	                 
	    	                }
	    	                else{
	    	                	response = new String("NONE");
	    	                }
	    	                }
	    	                catch(Exception e){
	    	                	Toast.makeText(getApplicationContext(), "Done failed "+nowfile, Toast.LENGTH_SHORT).show();

	    	                }
	    	                // 

                       	    Intent intent = getIntent();
	    	                finish();
	    	                startActivity(intent);
	                    }
	                 })
	                .setNegativeButton("No", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) { 
	                        // do nothing
 	                    	
	                    }
	                 })
	                 .show(); 
	            	 
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
         
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        
         
 

        } catch (Exception e) {
        	
             
            Toast.makeText(getApplicationContext(), "Check Internet Connectivity ", Toast.LENGTH_LONG).show();

        }      
        //textView.setText(response);
        //setContentView(textView); 

   
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}