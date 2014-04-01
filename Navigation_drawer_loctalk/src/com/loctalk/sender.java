package com.loctalk;

import java.lang.Thread;
import java.net.*;
public class sender extends Thread{
DatagramSocket socket=null;
boolean state=false;
byte[] data="yttyty".getBytes();
String ho;

public sender(String s, String host){
	ho = host;
	System.out.println(s);
	data=s.getBytes();//name.getBytes();
	try{
	socket = new DatagramSocket();
	}
	catch(Exception e)
	{
	System.out.println(""+e);
	}
}

public void setData(byte[] s)
{
System.out.println("data");
data=s;
}
public void send(boolean a){
state=a;

}

public void run(){
System.out.println("send started");
try{
System.out.println("packet send1");
//data ="This is the message".getBytes();
DatagramPacket packet =new DatagramPacket(data, data.length);
System.out.println("packet send2");
InetAddress destAddress =InetAddress.getByName(ho);
System.out.println("packet send3");
packet.setAddress(destAddress);
System.out.println("packet send4");
packet.setPort(4444);
System.out.println("packet send5");
socket.send(packet);
state=false;
System.out.println("packet send6");

}
catch(Exception e)
{
System.out.println("qweqwe"+e);
}

socket.close();
}
}