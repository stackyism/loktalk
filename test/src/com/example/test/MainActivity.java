package com.example.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/*public MainActivity(){
		super();
	}*/
	String s;
	//DatagramSocket socket=null;
	sender2 sen;
	receiver re;
	TextView textv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		doBindService();
		textv=(TextView)findViewById(R.id.display);
		try{
			
			//sen= new sender2(s);
			// re= new receiver(textv);
			 //re.start();
			//sen= new sender();
			// sen.start();
		}
		catch(Exception e)
		{
			System.out.print(""+e);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button broad = (Button)findViewById(R.id.broadcast);
		//textv=(TextView)findViewById(R.id.display);
		
		final EditText text  = (EditText) findViewById(R.id.message);
		broad.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				 System.out.println("asdadasd");
				 s = text.getText().toString();
				 sen= new sender2(s);
				 sen.start();
				/* try{
					
					 sen.setData(s.getBytes());
					 sen.send(true);
					 
				 }
				 catch(Exception e)
				 {
					 System.out.print(""+e); 
				 }*/
			}
		});
		
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		doUnbindService();
	}
	public void getstring(String s){
		String temp = textv.getText().toString();
		textv.setText("fuck"+temp+s);
		
	}
	
	public void settext(String s){
		textv.setText("fuck"+textv.getText().toString()+s);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	
	
	
	/** Messenger for communicating with service. */
	Messenger mService = null;
	/** Flag indicating whether we have called bind on the service. */
	boolean mIsBound;
	/** Some text view we are using to show state information. */
	

	/**
	 * Handler of incoming messages from service.
	 */
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("123123"+msg.getData().getString("name"));
			if(msg.what==receiverservice1.MSG_SET_VALUE){
				textv=(TextView)findViewById(R.id.display);
				textv.setText(textv.getText().toString()+ msg.getData().getString("name"));
			}
		}
	}

	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	final Messenger mMessenger = new Messenger(new IncomingHandler());

	/**
	 * Class for interacting with the main interface of the service.
	 */
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. We are communicating with our
			// service through an IDL interface, so get a client-side
			// representation of that from the raw service object.
			mService = new Messenger(service);

			// We want to monitor the service for as long as we are
			// connected to it.
			try {
				System.out.println("message");
				Message msg = Message.obtain(null,receiverservice1.MSG_REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);
			} catch (RemoteException e) {
				// In this case the service has crashed before we could even
				// do anything with it; we can count on soon being
				// disconnected (and then reconnected if it can be restarted)
				// so there is no need to do anything here.
			}

			// As part of the sample, tell the user what happened.
			Toast.makeText(MainActivity.this, "Service connected",Toast.LENGTH_SHORT).show();
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			mService = null;

			// As part of the sample, tell the user what happened.
			Toast.makeText(MainActivity.this, "Service disconnedcted",Toast.LENGTH_SHORT).show();
		}
	};

	public void doBindService() {
		System.out.println("binding");
		// Establish a connection with the service. We use an explicit
		// class name because there is no reason to be able to let other
		// applications replace our component.
		bindService(new Intent(MainActivity.this, receiverservice1.class),mConnection, Context.BIND_AUTO_CREATE);
		System.out.println("binded");
		mIsBound = true;
	}

	public void doUnbindService() {
		if (mIsBound) {
			// If we have received the service, and hence registered with
			// it, then now is the time to unregister.
			if (mService != null) {
				try {
					Message msg = Message.obtain(null,
							receiverservice1.MSG_UNREGISTER_CLIENT);
					msg.replyTo = mMessenger;
					mService.send(msg);
				} catch (RemoteException e) {
					// There is nothing special we need to do if the service
					// has crashed.
				}
			}

			// Detach our existing connection.
			unbindService(mConnection);
			mIsBound = false;
		}
	}
}
