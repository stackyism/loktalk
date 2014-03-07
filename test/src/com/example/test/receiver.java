package com.example.test;
import java.lang.Thread;
import java.net.*;

import org.json.JSONObject;





public class receiver extends Thread {
	DatagramSocket socket=null;
	byte[] data = new byte[100000];
	JSONObject jo = new JSONObject();
	DatagramPacket packet =new DatagramPacket(data, data.length);
	//TextView textv;
	public boolean state=false;
	String pass;
	//MainActivity ma = new MainActivity();
	public receiver()
	{
		try{
		socket = new DatagramSocket(4444);
		}
		catch(Exception e)
		{
			System.out.println(""+e);
		}
	}
	
	public void run()
	{
		while(true)
		{
			
			//System.out.println("receive started");
			try
			{
				
				socket.receive(packet);
				System.out.println("received");
				String message =new String(packet.getData(), 0, packet.getLength());
				System.out.println(""+message);
				pass=message;
				state=true;
				//ma.getstring(message);
				//textv.setText(message);
				//textv.setText("fuck"+textv.getText().toString()+message);
				
			
			}
			catch(Exception e)
			{
				System.out.println("809080980"+e);
			}
			
		}
	}
	

	public String readd()
	{
		state=false;
		return pass;
	}
		//socket.close();
	}

