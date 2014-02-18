package com.dargo.wordsoflove.common;

import com.datgo.wordsoflove.datahandling.WolDB;

import android.content.Context;
import android.database.Cursor;


/**
 * Message is a Custom Object to encapsulate message information/fields
 * 
 * @author Adil Soomro
 *
 */
public class Message {
	/**
	 * The content of the message
	 */
	String message;
	/**
	 * boolean to determine, who is sender of this message
	 */
	boolean isMine;
	/**
	 * boolean to determine, whether the message is a status message or not.
	 * it reflects the changes/updates about the sender is writing, have entered text etc
	 */
	public boolean isStatusMessage;
	
	WolDB db;
	/**
	 * Constructor to make a Message object
	 */
	public Message(Context context, String message, boolean isMine) {
		super();
		
		this.message = message;
		this.isMine = isMine;
		this.isStatusMessage = false;
	}
	/**
	 * Constructor to make a status Message object
	 * consider the parameters are swaped from default Message constructor,
	 *  not a good approach but have to go with it.
	 */
	public Message(Context context, boolean status, String message) {
		super();
		this.message = message;
		this.isMine = false;
		this.isStatusMessage = status;
		
	}
	public Message(Context myContext, boolean isMine) {
		
		super();
		this.isMine = isMine;
		this.isStatusMessage = false;
		db = new WolDB(myContext);
		if (!isMine)
		{
			this.getRandomMessage();
		}
		
	}
	public String getMessage() {
		return message;
	}
	
	public String getRandomMessage() {
		
		Cursor getCatCursor = db.getRandomPhrase(); 
        if (getCatCursor.moveToFirst())
        {
        	while (getCatCursor.isAfterLast() == false)
        	{
        		
	        	this.message= getCatCursor.getString(0);
	        	
	        	getCatCursor.moveToNext();
        	}
        		
        }
        getCatCursor.close();
		
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	public boolean isStatusMessage() {
		return isStatusMessage;
	}
	public void setStatusMessage(boolean isStatusMessage) {
		this.isStatusMessage = isStatusMessage;
	}
	
	
}
