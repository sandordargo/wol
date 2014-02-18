package com.dargo.wordsoflove.activities;

import com.dargo.wordsoflove.R;
import com.dargo.wordsoflove.common.Utilities;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class SettingsMenu extends Activity 
{
	
		CheckBox 		myRunBackgrounCB;
		private SeekBar myFrequencyControl = null;
		Context 		myContext;
		int 			myMsgFrequency = 0;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			myContext = this;
			setContentView(R.layout.settings_menu);
			myRunBackgrounCB = (CheckBox) findViewById(R.id.is_on_in_backgroung_CB);
			myFrequencyControl = (SeekBar) findViewById(R.id.frequency_bar);
			readPreferences();
			setFrequencyBarListener();
			setRunInBackgroundListener();

		}
		
		private void setRunInBackgroundListener() 
		{
			myRunBackgrounCB.setOnClickListener(new OnClickListener() 
			{
	            @Override
	            public void onClick(View arg0) 
	            {
	                final boolean isChecked = myRunBackgrounCB.isChecked();
	                if(!isChecked)
	                {
	                	myFrequencyControl.setEnabled(false);
	                }
	                else
	                {
	                	myFrequencyControl.setEnabled(true);
	                }
	            }
	        });			
		}

		private void setFrequencyBarListener() 
		{
			myFrequencyControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	            
	            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
	            	myMsgFrequency = progress;
	            }
	 
	            public void onStartTrackingTouch(SeekBar seekBar) {
	            }
	 
	            public void onStopTrackingTouch(SeekBar seekBar) {
	                Toast.makeText(myContext,"seek bar progress: " + myMsgFrequency,
	                        Toast.LENGTH_SHORT).show();
	            }
	        });
			
		}

		private void readPreferences() 
		{
			SharedPreferences aSettings = getSharedPreferences(Utilities.PREFS_NAME, 0);
	    	Boolean aRunInBackgroundState = aSettings.getBoolean(Utilities.BACKGROUNFSERVICE, true);
	    	myMsgFrequency = aSettings.getInt(Utilities.kMsgSendFrequency, Utilities.kDefaultMsgSendFrequency);
	    	myFrequencyControl.setProgress(myMsgFrequency);
	    	
	    	if (aRunInBackgroundState)
	    	{
	    		myRunBackgrounCB.setChecked(true);
	    		myFrequencyControl.setEnabled(true);
	    	}
	    	else
	    	{
	    		myRunBackgrounCB.setChecked(false);
	    		myFrequencyControl.setEnabled(false);
	    	}
	
		}

		public void insertPhrase(View view){
			Intent intentInsertPhrase = new Intent(this, InsertPhrase.class);
			startActivity(intentInsertPhrase);
		}
		
		public void setSender(View view){
			Intent intentSetSender = new Intent(this, SetSender.class);
			startActivity(intentSetSender);
		}
		
		@SuppressLint("NewApi")
		public void setBackgroundRunCB()
		{
			final boolean isChecked = myRunBackgrounCB.isChecked();
	    	
	    	SharedPreferences settings = getSharedPreferences(Utilities.PREFS_NAME, 0);
	    	SharedPreferences.Editor editor = settings.edit();
	    	Boolean runInBackgroundState;
	    	if (isChecked)
	    	{
	    		runInBackgroundState=true;
	    		editor.putInt(Utilities.kMsgSendFrequency, myMsgFrequency)
	    		.putBoolean(Utilities.BACKGROUNFSERVICE, runInBackgroundState)
	    		.apply();
	    	}
	    	else
	    	{
	    		runInBackgroundState=false;
	    		editor.putBoolean(Utilities.BACKGROUNFSERVICE, runInBackgroundState).apply();
	    	}

		}
		
		public void onBackPressed()
		{
			setBackgroundRunCB();
			finish();
		}
		
		public void onPause()
		{
			super.onPause();
			setBackgroundRunCB();
		}
}
