package com.dargo.wordsoflove.activities;

import com.dargo.wordsoflove.R;
import com.dargo.wordsoflove.common.Utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SetSender extends Activity 
{
	EditText senderName; 
		
    @SuppressLint("NewApi")
    
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_sender);
        
    	SharedPreferences settings = getSharedPreferences(Utilities.PREFS_NAME, 0);
    	String sender = settings.getString("Sender", getString(R.string.sender));
    	senderName = (EditText) findViewById(R.id.sender);
    	senderName.setText(sender);
    	
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
        {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void insert(View view)
    {
    	SharedPreferences settings = getSharedPreferences(Utilities.PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	String newSender = senderName.getText().toString();
        editor.putString("Sender", newSender);

        // Commit the edits!
        editor.commit();
        onBackPressed();
    }
    
    public void cancel(View view)
    {
    	onBackPressed();
    }

    @Override
	protected void onDestroy() 
    {
    	super.onDestroy();
	}
    
}
