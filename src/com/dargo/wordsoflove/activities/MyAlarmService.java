package com.dargo.wordsoflove.activities;

import com.dargo.wordsoflove.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

@SuppressLint("NewApi")
public class MyAlarmService extends Service 
{
      
   private NotificationManager mManager;
 
    @Override
    public IBinder onBind(Intent arg0)
    {
       // TODO Auto-generated method stub
        return null;
    }
 
    @Override
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }
 
   @Override
   public int onStartCommand(Intent intent, int flags, int startId)
   {
       super.onStartCommand(intent,flags,startId);
      
       this.getApplicationContext();
	mManager = (NotificationManager) this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
       Intent intent1 = new Intent(this.getApplicationContext(),MessageActivity.class);
       
       intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP );
 
       PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
       Notification notification = new NotificationCompat.Builder(this.getApplicationContext())
       .setContentTitle("Picu is here!")
       .setContentText("uzenetem")
       .setAutoCancel(true)
       .setContentIntent(pendingNotificationIntent)
       .setSmallIcon(R.drawable.ic_launcher)
       .build();
 
       mManager.notify(0, notification);
	return START_NOT_STICKY;
    }
 
    @Override
    public void onDestroy() 
    {
        super.onDestroy();
    }
 
}