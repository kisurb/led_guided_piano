package com.example.l;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.l.MESSAGE";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	File root = android.os.Environment.getExternalStorageDirectory(); 
	          
     
         
        super.onCreate(savedInstanceState); 
          
        
        File dir = new File (root.getAbsolutePath() + "/music_source");
         
        if(dir.exists()==false) {
             dir.mkdirs();
             copyAssets();
        } 
    	
        setContentView(R.layout.activity_main); 
    	
    	
    	
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }
    public void sendMessage2(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity2.class);
        startActivity(intent);
    }
    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open(filename);
              File root = android.os.Environment.getExternalStorageDirectory();     
              out = new FileOutputStream(root.getAbsolutePath() + "/music_source/"+ filename);
              copyFile(in, out);
              in.close();
              in = null;
              out.flush();
              out.close();
              out = null; 
            } catch(IOException e) { 
            }       
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }

}
 