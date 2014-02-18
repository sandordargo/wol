package com.dargo.wordsoflove.activities;

import com.dargo.wordsoflove.R;
import com.datgo.wordsoflove.datahandling.WolDB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertPhrase extends Activity 
{
		
	WolDB myDB;
	Spinner spinner;
    @SuppressLint("NewApi")
    
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new WolDB(this);
        setContentView(R.layout.insert_phrase);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
    

    
    public void insert(View view){
    	
    	EditText phraseTextET = (EditText) findViewById(R.id.phraseText);
		String phraseTextMsg = phraseTextET.getText().toString();
    	
    	myDB.insertPhrase(phraseTextMsg);
    	
    	Toast.makeText(this, "New phrase recorded", Toast.LENGTH_SHORT).show();
    	
    	phraseTextET.setText("");
    
    }
    
    public void cancel(View view){
    	myDB.close();
    	onBackPressed();
    }
    
    	    @Override
    
        
	protected void onDestroy() {
		
    	
    	super.onDestroy();
	}


}
