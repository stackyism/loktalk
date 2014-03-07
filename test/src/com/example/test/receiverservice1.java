package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;


public class receiverservice1 extends Service {
	static final int MSG_REGISTER_CLIENT = 1;			// so that the service updates the main activity
	static final int MSG_UNREGISTER_CLIENT = 2;			// service will stop updating once the app closes i,e, unregisters
	static final int MSG_SET_VALUE = 3;					
	Messenger mClients=null;
	int mValue = 0;
	receiver re;
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("service"+msg.what);
			if(msg.what== MSG_REGISTER_CLIENT) 
			{
				mClients=msg.replyTo;
				doit();
				}
			else if(msg.what== MSG_UNREGISTER_CLIENT) {mClients=null;}
			
		}
	}

	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	final Messenger mMessenger = new Messenger(new IncomingHandler());

	@Override
	public void onCreate() {
		System.out.println("Service started");
		re= new receiver();
		re.start();	
		
		
	}
	public void doit()
	{
		while(true)
		{
			if(re.state)
			{
				Bundle bundle = new Bundle();
				bundle.putString("name", re.readd());
				
				try {
					Message message = new Message();
					message.what=MSG_SET_VALUE;
					message.setData(bundle);
					mClients.send(message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("656565"+e);
				}

			}
			
			
		}
	}
	@Override
	public void onDestroy() {

		// Tell the user we stopped.
		System.out.println("Service stoped");
	}
	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}
}
