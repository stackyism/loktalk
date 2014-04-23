package com.loctalk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;



public class NotificationBuilder {
	
	private NotificationManager notificationManager;
	Notification myNotification;
	Context notification_context;
	private int notificationID;
	
	public NotificationBuilder(){
		
	}
	public NotificationBuilder(int notification_ID,String notificationTitle,String notificationText, String notificationTicker, Context context, PendingIntent notificationPendingIndent){
		 
	    myNotification = new NotificationCompat.Builder(context)
	    .setContentTitle(notificationTitle)
	    .setContentText(notificationText)
	    .setTicker(notificationTicker)
	    .setWhen(System.currentTimeMillis())
	    .setContentIntent(notificationPendingIndent)
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

