package com.example.l;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
 
public class Play extends Activity {
	 
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_display_message_activity2, null);
         
        // Find the ScrollView 
        ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView1);

        // Create a LinearLayout element
        LinearLayout lLayout = new LinearLayout(this);
        lLayout.setOrientation(LinearLayout.VERTICAL);
        for(int i=1;i<11;i++){
        	final int i2=i;
        	/*ScrollView scrollView = (ScrollView)getLayoutInflater().inflate(R.layout.activity_display_message_activity2)
        	RelativeLayout layout = (RelativeLayout) scrollView.findViewById(R.id.rel_all_football);
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            */ 
            Button b = (Button) new Button(this);
             
            b.setId(i);  
            lLayout.addView(b); 
            //Log.d("TAG","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            b.setText("Speed "+Integer.toString(i));
            b.setOnClickListener(new OnClickListener() {
             
            public void onClick(View a) {
            	Intent intent2 = getIntent();
         		 
        	    final String nowfile = intent2.getStringExtra("This");
            	Intent intent = new Intent(Play.this, Orbit.class);
            	intent.putExtra("Speed", Integer.toString(i2));
            	intent.putExtra("File", nowfile);
                startActivity(intent);
                 }
        });
        }
        sv.addView(lLayout);
		setContentView(v);
  	    //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
		


	}  
    public void hardware2(View view) { 
    	ArrayList<String> data = new ArrayList<String>();
    	try {
    		Intent intent = getIntent();
   		 
    	    final String nowfile = intent.getStringExtra("This");
    	    MidiFile midi = null;
    	     
    	    File root = android.os.Environment.getExternalStorageDirectory(); 
            midi = new MidiFile(new File(root.getAbsolutePath() + "/music_source/"+ nowfile));
            int ntracks=midi.getTracks().size();
            for(int t=0; t<ntracks;t++){
            Iterator<MidiEvent> it = midi.getTracks().get(t).getEvents().iterator();
            //String[] keys = new String[128]; 
    		//Arrays.fill(keys, "0"); 
            ArrayList<Integer> action = new ArrayList<Integer>();// define actions
            
            while(it.hasNext()) {
            		int say = 0;
            		int next=-1;
            		long d=0;
            		NoteOn N;
            		NoteOff F;
                    MidiEvent e = it.next();
                    if(!(e instanceof NoteOn) && !(e instanceof NoteOff)){
                    	d=e.getDelta();
                    	say=0;  
                    	
                    };
                    if(e instanceof NoteOn){
                    	 N = (NoteOn)e;
                    	 d=N.getDelta();
                    	 say=1;
                    	 if(d==0){
                    		 action.add(N.getNoteValue());
                    	 }
                    	 if(d!=0){
                    		 next=N.getNoteValue();
                    	 }
                    	 
                    }
                    if(e instanceof NoteOff){
                    	 F = (NoteOff)e;
                    	 d=F.getDelta();
                    	 say=2;
                    	 if(d==0){
                    		  action.add(-F.getNoteValue());
                    	 }
                    	 if(d!=0){
                    		 next=-F.getNoteValue();
                    	 }
                    }
                    if (d!=0){
                    	String entry="";
                    	for( int key : action ) {
                        	entry+= Integer.toString(key);
                        	entry+="space"; 
                        	}
                    	entry+=Long.toString(d);
                    	data.add(entry);
                    	//for( int key : action ) {
                    	//	if (key<0){
                    	//		keys[-key]="0";
                    	//	}
                    	//	if (key>0){
                    	//		keys[key]="1";
                    			
                    	//	}
                    	     
                    	//} 
                    	
                    	action.clear(); 
                    	if (say!=0){
                    	action.add(next);
                    	}
                    	 //try {
                    	 //    Thread.sleep(d);
                    	 //} catch(InterruptedException ex) {
                    	 //Thread.currentThread().interrupt();
                    	 //}  
            }
            }
            }
    	
    	
    	} catch(IOException E) {
            System.err.println(E); 
    } 
    }
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
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

}
