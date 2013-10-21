package com.example.l;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.util.MidiEventListener;

//import javax.sound.midi.*;

public class Runner implements MidiEventListener {

        private String mLabel;
        public Runner(String label) {
                mLabel = label;
        }

        // 0. Implement the listener functions that will be called by the MidiProcessor
        @Override
        public void onStart(boolean fromBeginning) {
                
                // Note that you will receive this once for each event you've registered for
                if(fromBeginning) {
                        System.out.println(mLabel + " Begin!");
                }
                
        }

        @Override
        public void onEvent(MidiEvent event, long ms) {
                System.out.println(mLabel + " received event: " + event);
        }

        @Override
        public void onStop(boolean finished) {
                
                // Note that you will receive this once for each event you've registered for
                if(finished) {
                        System.out.println(mLabel + " Finished!");
                }
        }
        
        public static void asd() {
                
                // 1. Read in a MidiFile
        	try {
                //Synthesizer synth = MidiSystem.getSynthesizer();
                //synth.open();
                //final MidiChannel[] mc    = synth.getChannels();
                //Instrument[]        instr = synth.getDefaultSoundbank().getInstruments();
                //synth.loadInstrument(instr[1]); 
        	
                MidiFile midi = null;
                try {
                	
                        midi = new MidiFile(new File("/home/k/Desktop/bwv776.mid"));
                        Iterator<MidiEvent> it = midi.getTracks().get(1).getEvents().iterator();
                        String[] keys = new String[128]; 
                		Arrays.fill(keys, "0"); 
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
                                	for( int key : action ) {
                                		if (key<0){
                                			keys[-key]="0";
                                		}
                                		if (key>0){
                                			keys[key]="1";
                                			
                                		}
                                	     
                                	}
                                	if (action.size()>0){
                                		System.out.println( action);
                                		 
                                		//String out[]=Arrays.copyOfRange(keys, 36,100); 
                                		//byte[] response = new byte[8];
                                		//for (int i = 0; i <8; i++) {
                                		//	System.out.println( i	);
                                		//	response[i]=(byte) Integer.parseInt(Arrays.copyOfRange(out, (i)*8,(i+1)*8).toString(), 2);
                                	//}
                                	}
                                	
                                	action.clear(); 
                                	if (say!=0){
                                	action.add(next);
                                	}
                                	try {
                                	    Thread.sleep(1);
                                	} catch(InterruptedException ex) {
                                	    Thread.currentThread().interrupt();
                                	}
                                		
                                 
                                //if(!(e instanceof NoteOff)) continue;
                                //NoteOff E = (NoteOff)e;
                                //if(E.getNoteValue() == 60) {
                                //        System.out.println(E.getDelta());
                                //}
                                 
                        }
                        }} catch(IOException e) {
                        System.err.println(e);
                        return;
                }
        	}catch (Exception e) {}
        }}