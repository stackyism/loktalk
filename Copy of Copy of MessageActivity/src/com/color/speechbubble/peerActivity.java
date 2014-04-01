package com.color.speechbubble;

import static com.color.speechbubble.Constant.db;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.color.speechbubble.database.AppDB;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class peerActivity extends ListActivity {
	/*
	 * The task of getting the peers that are currently connected to the Wi-Fi
	 * and doing the needed there after(for e.g sending chat request or blocking the user
	 * or simply starting the personal chat. 
	 */

	JSONObject jsonObject;
	static String apppid,sender,broad="192.168.62.255";
	sender1 sen;
	ArrayList<String> ar = new ArrayList<String>();
	ArrayList<String> id = new ArrayList<String>();
	public peerActivity(){
	
	}
	
	public peerActivity(String s,String ip){
		peerActivity.sender =s;
		peerActivity.apppid = ip;
		
	}
	
	/*old methods
	 * 
	 * public String [] parseJSON(String msgJSONValue) throws JSONException
	{
		System.out.println("Hua kuch toh Parse me");

		jsonObject = new JSONObject(msgJSONValue);

		JSONObject object = jsonObject.getJSONObject("Message");
		String sender = object.getString("Sender"); //nick
		String appID = object.getString("Time"); // appID
	  //  String msg_text = object.getString("Msg"); //msg
	   String flag = object.getString("Flag"); //flag
		
	    String temp[] = {appID,sender,flag};
	    System.out.println("Parse===>>	"+appID+sender);
	    return (temp);
		
			
		
	}*/
	public String createUltiJSON(String AppID, String Nick,  String Msg, String Flag){
		JSONObject objCreate = new JSONObject();
		String toreturn = "";
		try{
		objCreate.put("AppID", AppID);
		objCreate.put("Nick", Nick);
		objCreate.put("Msg", Msg);
		objCreate.put("Flag", Flag);
		System.out.println("Jason string after creatingUltiJSON==="+"\n"+objCreate.toString());
		toreturn = objCreate.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JSONCreate error"+ e.getMessage().toString());
			//return false;
			
		}
		return toreturn;
	}
	
	public String[] parseUltiJSON(String strJSON) throws JSONException{
		JSONObject jsonObj = new JSONObject(strJSON);
		String toret[] = new String[5];
		toret[0] = jsonObj.getString("AppID");
		toret[1] = jsonObj.getString("Nick");
		toret[2] = jsonObj.getString("Msg");
		toret[3] = jsonObj.getString("Flag");
		
		return toret;
		
		
	}
	/*
	 * old methods
	 * public String createJSON(String msg_value, String flag) throws JSONException{
		
		System.out.println("Hua kuch toh create me===="+msg_value);

		String temp = "{\"Message\": {\"Sender\": \""+sender+"\",\"Time\": \""+"time_value"+"\",\"Msg\":\""+msg_value+"\",\"Flag\":\""+flag+"\"}}";
	    return temp;
	}
	public String createPeerJSON(String msg_value, String flag, String apid) throws JSONException{
		
		System.out.println("CreatePeerjsonS===="+msg_value);

		String temp = "{\"Message\": {\"Sender\": \""+sender+"\",\"Time\": \""+apid+"\",\"Msg\":\""+msg_value+"\",\"Flag\":\""+flag+"\"}}";
	    return temp;
	}*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (db == null) //initializing the database handler object.
			db = new AppDB(this);
		
		System.out.println("PeerActivity oncreate!!"+apppid);
		String s;
		s = createUltiJSON(apppid,sender,"Hi peers","Peers request");
		sen = new sender1(s,broad);
		sen.start();
		/*
		 * broadcasting that the user wants all the peers available.
		 */
		new SendMessage().execute();		
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*
		 * Tasks that are to be carried out when a specific peer is clicked.
		 * (For now we are simply starting the personal chat.)
		 */
		super.onListItemClick(l, v, position, id);
		System.out.println("Onlistitemclick!!!");
		final int index = position;
		String tempid = this.id.get(index);
		int ti = Integer.parseInt(tempid);
		final String peerInfo[] = db.getOnePeer(ti);
		System.out.println("Args for pm, nick="+sender+"/tpeer="+ar.get(index)+"/tappId"+apppid+"/tIP==="+peerInfo[2]);
		System.out.println(peerInfo[2]);
		pmchat pc = new pmchat(sender,ar.get(index),apppid,peerInfo[2]);
		Intent in = new Intent(peerActivity.this,pmchat.class);
		peerActivity.this.startActivity(in);
		/*
		 * below, the code for asking the user if he wants to request the selected peer
		 * for personal chat(if the request has not been sent before). 
		 */
//		AlertDialog alert = new AlertDialog.Builder(peerActivity.this).create();
//		alert.setTitle("Send request"+ar.get(index));
//		alert.setMessage("Need to send request For Chat first!!!");
//		alert.setButton(alert.BUTTON_NEUTRAL, "Send Request", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//					String req = createUltiJSON(apppid,sender,"Lets Talk", "Add me!");
//					sen = new sender(req,peerInfo[2]);
//					sen.start();
//					
//			}
//		});
//		alert.setButton(alert.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//
//			}
//		});
//		alert.show();

	}
	
	
	private class SendMessage extends AsyncTask<Void, String, String>
	{
		DatagramSocket socket=null;
		byte[] data = new byte[100000];
		DatagramPacket packet =new DatagramPacket(data, data.length);
		String pass,sendadd;
		String tobesent="";			
		

		@Override
		protected String doInBackground(Void... params) {
			System.out.println("doinBG peerAsync me aya");
			
			try
			{
			System.out.println("peer try of doinBG!!!");
			socket = new DatagramSocket(5555);
			socket.receive(packet);
			System.out.println("peer received");
			//temo_c++;
			String message =new String(packet.getData(), 0, packet.getLength());
			sendadd = packet.getAddress().toString();
			System.out.println("\t"+message);
			pass=message;
			//System.out.println(pass);
			String [] temp_parse = parseUltiJSON(message);
			
			if(temp_parse[3].equals("Reply for Request Peers")){
				tobesent = pass;
				System.out.println("Reply aya request ka"+pass);

				}
			
			else if(temp_parse[3].equals("Yeh msg hai")){
				MessageActivity ma2 = new MessageActivity();
				ma2.addFromPeerToMsg(message);
			}
			
			else if(temp_parse[3].equals("Peers request")){
				System.out.println("Aayi request peers ke liye");

				String se = createUltiJSON(apppid,sender,"Hey","Reply for Request Peers");
				sen = new sender1(se,broad);
				sen.start();
				}
			else if(temp_parse[3].equals("Add me!")){
				System.out.println("Add me Request received.");
				addtoChatReq(temp_parse[0],temp_parse[1],sendadd,"0","0");
				
			}
			}
			catch(Exception e)
			{
			System.out.println("Peer activity Receiver socket==809080980"+e);
			
			}			
			//socket.close();
			return tobesent;

		}

		protected void onPostExecute(String text) {
			
			String[] parsed;
			if(text.length()!=0){
			try {
				parsed = parseUltiJSON(text);
				System.out.println("====OnPostExecute=="+parsed[0]+parsed[1]);
				addtopeerdb(parsed[0], parsed[1], sendadd, "0", "0");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			else{
				new SendMessage().execute();
			}			
		}
		

	}
	public void addtopeerdb(String appID, String nick, String ip, String pc, String block){
		/*
		 * method to add the peers in the table(contained in database-loctalk.sqlite)- Peers.
		 */
		System.out.println("function addtopeerdb() called!!!");
		JSONObject objPeer = new JSONObject();
				ip=ip.replaceFirst("/", "");
		try {
			System.out.println("ipdb"+ ip);
			objPeer.put("AppID", appID);
			objPeer.put("Nick", nick);
			objPeer.put("MAC", "MAC-ADD");
			objPeer.put("IP", ip);
			objPeer.put("PC", pc);
			objPeer.put("Block", block);
			System.out.println("Jason string for db==>>"+"\n"+objPeer.toString());
			db.insertPeer(objPeer);
			ar.add(nick);
			id.add(appID);
			setListAdapter(new ArrayAdapter<String>(peerActivity.this, android.R.layout.simple_list_item_1, ar));
			//return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PeerDB error"+ e.getMessage().toString());
					
		}
	}
	
	public void addtoChatReq(String appID, String nick, String ip, String pc, String block){
		
		/*
		 * method to add the chat requests in the table(contained in database-loctalk.sqlite)- ChatReq.
		 */
		System.out.println("function addtochatreq() called!!!");
		JSONObject objChatReq = new JSONObject();
				
		try {
			objChatReq.put("AppID", appID);
			objChatReq.put("Nick", nick);
			objChatReq.put("MAC", "MAC-ADD");
			objChatReq.put("IP", ip);
			
			objChatReq.put("PC", pc);
			objChatReq.put("Block", block);
			System.out.println("Jason string for db==>>"+"\n"+objChatReq.toString());
			db.insertChatReq(objChatReq);
			//return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PeerDB error"+ e.getMessage().toString());
	
	}
	}

}
