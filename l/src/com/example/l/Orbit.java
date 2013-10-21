package com.example.l;



import ioio.lib.api.DigitalOutput;
import ioio.lib.api.SpiMaster;
import ioio.lib.api.SpiMaster.Rate;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;

/**
* This is the main activity of the HelloIOIO example application.
*
* It displays a toggle button on the screen, which enables control of the
* on-board LED. This example shows a very simple usage of the IOIO, by using
* the {@link IOIOActivity} class. For a more advanced use case, see the
* HelloIOIOPower example.
*/
public class Orbit extends IOIOActivity {  

    List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    List<ArrayList<String>> DATA = new ArrayList<ArrayList<String>>();

private int speed2 ;
private int stop=0 ;
public void stop(View view) {
	stop=1;
	Intent intent = new Intent(this, DisplayMessageActivity2.class);
    startActivity(intent);
} 
/**
* Called when the activity is first created. Here we normally initialize
* our GUI.
*/
@Override
public void onCreate(Bundle savedInstanceState) { 
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_orbit); 
Intent intent = getIntent();
final String nowfile = intent.getStringExtra("File");
speed2 = Integer.parseInt(intent.getStringExtra("Speed"));
  
try { 
    MidiFile midi = null;
    ArrayList<Integer> times = new ArrayList<Integer>();// define actions

    File root = android.os.Environment.getExternalStorageDirectory(); 
    midi = new MidiFile(new File(root.getAbsolutePath() + "/music_source/"+ nowfile));
    int ntracks=midi.getTracks().size();
    for(int t=0; t<2;t++){
    Iterator<MidiEvent> it = midi.getTracks().get(t).getEvents().iterator();
    ArrayList<Integer> action = new ArrayList<Integer>();// define actions
    
    int schedule=0;
 
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
            	ArrayList<String> ent = new ArrayList<String>();
            	String entry=""; 
            	
            	for( int key : action ) {
            		if (key<0 && action.contains(-key)){continue;}
            		
                	entry+= Integer.toString(key);
                	entry+="space"; 
                	} 
            	times.add(schedule);
              	ent.add(entry); 
             	ent.add(Integer.toString(schedule));
             	data.add(ent); 
            	action.clear(); 
            	schedule+=d;

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
    Set set = new HashSet(times);
    ArrayList<Integer> list = new ArrayList<Integer>(set);
    Collections.sort(list);
    list.add(list.get(list.size() - 1));
    

    for (int i =0; i<list.size()-1;i++){
    	Integer now= list.get(i);
    	Integer d=list.get(i+1)-list.get(i);
        ArrayList<String> oneline = new ArrayList<String>();

    	String line ="";
    	
    	for (ArrayList x : data){ 
    		Integer timing= Integer.parseInt((String) x.get(1)) ;
    		if (timing-now==0){
    			
    			String go=(String) x.get(0);
    			line+=go;
    		}
    	}

    	
    	oneline.add(line);
    	oneline.add(Integer.toString(d));
    	DATA.add(oneline);
    	
    } 
    

} catch(IOException E) {
    System.err.println(E); 
}

}

/**
* This is the thread on which all the IOIO activity happens. It will be run
* every time the application is resumed and aborted when it is paused. The
* method setup() will be called right after a connection with the IOIO has
* been established (which might happen several times!). Then, loop() will
* be called repetitively until the IOIO gets disconnected.
*/

class Looper extends BaseIOIOLooper {
/** The on-board LED. */
private DigitalOutput led_;
private SpiMaster black;
private SpiMaster white;
private SpiMaster three;

/**
* Called every time a connection with IOIO has been established.
* Typically used to open pins.
*
* @throws ConnectionLostException
* When IOIO connection is lost.
*
* @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#setup()
*/
@Override
protected void setup() throws ConnectionLostException {
	white= ioio_.openSpiMaster (27,4,6,15 ,Rate.RATE_50K);
	black= ioio_.openSpiMaster (28,1,2,16 ,Rate.RATE_50K);
	three= ioio_.openSpiMaster (29,10,11,17 ,Rate.RATE_50K);
//SpiMaster spi = ioio.openSpiMaster(misoPin, mosiPin, clkPin, ssPins, SpiMaster.Rate.RATE_1M);
} 
/**
* Called repetitively while the IOIO is connected.
*
* @throws ConnectionLostException
* When IOIO connection is lost.
*
* @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#loop()
*/
@Override 
public void loop() throws ConnectionLostException {
 	byte[] flush={0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    white.writeReadAsync(0, flush, flush.length, flush.length, null, 0);
    black.writeReadAsync(0, flush, flush.length, flush.length, null, 0);
    three.writeReadAsync(0, flush, flush.length, flush.length, null, 0);
	

	String[] keys = new String[128]; 
	String[] postkeys = new String[128];

	
	Arrays.fill(keys, "0"); 
	Arrays.fill(postkeys, "0"); 
	int color=1;
	for( ArrayList<String> f : DATA ) { 

		 String one= (String) f.get(0);
		 String[] x = one.split("space"); 

	 	if (x[0]==""){
	 		continue;}

		 Integer wait = Integer.parseInt(f.get(1));
 	int colorchange=0;
 	int colorchangenow=0;
	for (int i =0; i<keys.length;i++){
    	if (keys[i]=="3"){
    		keys[i]="1";
    	};
    }; 

 	for( String k : x ) {
 		int key = Integer.parseInt(k);
 		if (0<key && postkeys[key]!="0"){
 			colorchangenow=1;
			}
 	}
	for( String k : x ) {
		if(k.length()>0){
		int key = Integer.parseInt(k);
			if (key<0){
				keys[-key]="0";
			}
		if (0<key){
			if (postkeys[key]!="0"){
			keys[key]="3";
			colorchange=1;
			}
			if (postkeys[key]=="0"){
				if (colorchangenow==1){
			keys[key]="3"; 	}
				if (colorchangenow==0){
					keys[key]="1"; 	}
				
			}
			 
		} 
		}}
	
	
	for (int i =0; i<keys.length;i++){
    	postkeys[i]=keys[i];
    }; 

	if (colorchange==1){
		if (color==0){
			color=1;
		}
		if (color==1){
			color=0;
		}
	}
	String [] q=Arrays.copyOfRange(keys, 36,96);
    Integer [] whitei={0, 2, 4, 5, 7, 9, 11, 12, 14, 16, 17, 19, 21, 23, 24, 26, 28, 29, 31, 33, 35};
   byte [] WHITE={0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   for (int i =0; i<21;i++){
       int in= Integer.parseInt(q[whitei[i]])*80;
       if (in<81){
       WHITE[i*3+2]= (byte) in;}
       else{
    	   WHITE[i*3+color]= (byte) in;
       }
   }
  
    Integer [] blacki={ 3, 6, 8, 10, 13, 15, 18, 20, 22, 25, 27, 30, 32, 34, 37, 39, 42, 44, 46};
    byte [] BLACK={0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    for (int i =0; i<19;i++){
        int in= Integer.parseInt(q[blacki[i]])*80;
        if (in<81){
            BLACK[i*3+2]= (byte) in;}
            else{
         	   BLACK[i*3+color]= (byte) in;
            }
      
   }
   
    Integer [] threei={49, 51, 54, 56, 55, 53, 52, 50, 48, 47, 45, 43, 41, 40, 38, 36};
    byte [] THREE={0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   
    for (int i =0; i<16;i++){
        int in= Integer.parseInt(q[threei[i]])*80;
        if (in<81){
            THREE[i*3+2]= (byte) in;}
            else{
         	   THREE[i*3+color]= (byte) in;
            }      
   }
    Integer Wait= Integer.parseInt(f.get(1));
 	 
 
 		try { 
		    white.writeReadAsync(0, WHITE, WHITE.length, WHITE.length, null, 0);
		    black.writeReadAsync(0, BLACK, BLACK.length, BLACK.length, null, 0);
		    three.writeReadAsync(0, THREE, THREE.length, THREE.length, null, 0);
		    
    		Thread.sleep(Wait*speed2);
		 
		} catch (InterruptedException e) {
		} 
 		
	}
	
	
		 	}  
}

/**
* A method to create our IOIO thread.
*
* @see ioio.lib.util.AbstractIOIOActivity#createIOIOThread()
*/
@Override
protected IOIOLooper createIOIOLooper() {

return new Looper();
}
}