package com.loctalk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;



public class NotificationBuilder {
	
	private NotificationManager notificationManager;
	Notification myNotification;
	Context notification_context;
	private int notificationID;
	private Intent myIntent;
	private PendingIntent notificationPendingIntent;
	public NotificationBuilder(){
		
	}
	public NotificationBuilder(int notification_ID,String notificationTitle,String notificationText, String notificationTicker, Context context, Intent notificationIntent){
		 
	    myIntent = new Intent();
	    notificationPendingIntent = PendingIntent.getActivity(
			      context,  
			      0, 
			      myIntent, 
			      PendingIntent.FLAG_ONE_SHOT);
		myNotification = new NotificationCompat.Builder(context)
	    .setContentTitle(notificationTitle)
	    .setContentText(notificationText)
	    .setTicker(notificationTicker)
	    .setWhen(System.currentTimeMillis())
	    .setContentIntent(notificationPendingIntent)
	    .setDefaults(Notification.DEFAULT_SOUND)
	    .setAutoCancel(true)
	    .setSmallIcon(R.drawable.ic_launcher)
	    .build();
	    notification_context = context;
	    notificationID = notification_ID;
	    
	    
	    
	}
 public void NotifyNotification(){
	 notificationManager = (NotificationManager)this.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
	    notificationManager.notify(this.getNotificationID(), myNotification);
 }
 public void CancelNotification(){
	 notificationManager = (NotificationManager)this.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
	    notificationManager.cancel(this.getNotificationID());
 }
 
 public Context getContext(){
	 	return notification_context;
 }
 public int getNotificationID(){
	 return notificationID;
 }

}

