package com.dargo.wordsoflove.activities;

import java.util.ArrayList;

import com.dargo.wordsoflove.R;
import com.dargo.wordsoflove.activities.MyReceiver;
import com.dargo.wordsoflove.adapters.MessageAdapter;
import com.dargo.wordsoflove.common.Message;
import com.dargo.wordsoflove.common.Utilities;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MessageActivity extends ListActivity {
	
	static String 				mySender;
	ArrayList<Message> 			myMessagesList;
	MessageAdapter 				myAdapter;
	EditText 					myTextInput;
	Context 					myContext;
	SharedPreferences 			mySettings;
	private PendingIntent 		myPendingIntent;
	Intent 						myIntent;
	AlarmManager 				myAlarmManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		mySettings = getSharedPreferences(Utilities.PREFS_NAME, 0);
		myTextInput = (EditText) findViewById(R.id.text);
		mySender = mySettings.getString("Sender", getString(R.string.sender));
		this.setTitle(mySender);
		myContext = this;
		
		myMessagesList = new ArrayList<Message>();
		myMessagesList.add(new Message(this, "Szia Micu", false));
		myAdapter = new MessageAdapter(this, myMessagesList);
		setListAdapter(myAdapter);
		
		myIntent = new Intent(MessageActivity.this, MyReceiver.class);
        myPendingIntent = PendingIntent.getBroadcast(MessageActivity.this, 0, myIntent,0);
        myAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	}
	
	
	private void setBackgroundRun() 
	{
		Boolean aRunInBackgroundState = mySettings.getBoolean(Utilities.BACKGROUNFSERVICE, true);
    	if (aRunInBackgroundState)
    	{
    		int aMsgFrequency = mySettings.getInt(Utilities.kMsgSendFrequency, Utilities.kDefaultMsgSendFrequency) * 1000;
            myAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + aMsgFrequency, aMsgFrequency  /*\* 60*/, myPendingIntent);
    	}
    	else
    	{
    		myAlarmManager.cancel(myPendingIntent);
    	}
	}
	
	
	public void sendMessage(View v)
	{
		String newMessage = myTextInput.getText().toString().trim(); 
		if(newMessage.length() > 0)
		{
			myTextInput.setText("");
			addNewMessage(new Message(this, newMessage, true));
			new SendMessage().execute();
		}
	}

	private class SendMessage extends AsyncTask<Void, String, String>
	{
		@Override
		protected String doInBackground(Void... params) 
		{
			try 
			{
				Thread.sleep(2000); //simulate a network call
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			this.publishProgress(String.format("%s started writing", mySender));
			try 
			{
				Thread.sleep(2000); //simulate a network call
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			this.publishProgress(String.format("%s has entered text", mySender));
			try 
			{
				Thread.sleep(3000);//simulate a network call
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			return "";
			
		}
		
		@Override
		public void onProgressUpdate(String... v) 
		{
			if(myMessagesList.get(myMessagesList.size()-1).isStatusMessage)//check wether we have already added a status message
			{
				myMessagesList.get(myMessagesList.size()-1).setMessage(v[0]); //update the status for that
				myAdapter.notifyDataSetChanged(); 
				getListView().setSelection(myMessagesList.size()-1);
			}
			else
			{
				addNewMessage(new Message(myContext, true,v[0])); //add new message, if there is no existing status message
			}
		}
		
		@Override
		protected void onPostExecute(String text) 
		{
			if(myMessagesList.get(myMessagesList.size()-1).isStatusMessage)//check if there is any status message, now remove it.
			{
				myMessagesList.remove(myMessagesList.size()-1);
			}
			addNewMessage(new Message(myContext, false)); // add the orignal message from server.
		}
	}
	
	void addNewMessage(Message m)
	{
		myMessagesList.add(m);
		myAdapter.notifyDataSetChanged();
		getListView().setSelection(myMessagesList.size()-1);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle item selection
	    switch (item.getItemId()) 
	    {
	        case R.id.settings:
	        	showSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void showSettings(){
		Intent intentShowSettings = new Intent(this, SettingsMenu.class);
		startActivity(intentShowSettings);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		myAlarmManager.cancel(myPendingIntent);
	}
	
	protected void onPause()
	{
		super.onPause();
		setBackgroundRun();
	}
	
}