package com.loctalk;

import static com.loctalk.Constant.db;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.loctalk.R;

//import com.loctalk.MessageActivity.SendMessage;

//simport com.loctalk.MessageActivity.SendMessage;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class pmchat extends ListActivity {
	/*
	 *	This activity carries out the personal chat tasks. 
	 */

	ArrayList<Message1> messages;
	AwesomeAdapter adapter; 	//to display the messages in the speechbubbles listview
	EditText text;				
	static String sender,peer;
	static String pmapppid ;
	static String broad = "192.168.71.255";
	static int counter =0;
	String s;
	pmsender sen;
	TextView textv;
	JSONObject jsonObject;
	
	public pmchat(String nick,String peer,String appid,String ip){
		/*
		 * receiving data from peerActivity
		 */
		pmchat.broad = ip;	//IP of the counter-part.
		sender = nick;
		pmchat.peer = peer;
		pmchat.pmapppid= appid;
	}
	
	public pmchat(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		text = (EditText) this.findViewById(R.id.text);
		messages = new ArrayList<Message1>();
		adapter = new AwesomeAdapter(this, messages);
		setListAdapter(adapter);
		this.setTitle(peer);
		//addNewMessage(new Message1("hello",true));
		System.out.println("in pmchat!!");
		System.out.println("PM chat args nick"+sender+"peer"+pmchat.peer+"appID"+pmapppid);
	}
	
	public String createUltiJSON(String AppID, String Nick,  String Msg, String Flag){
		JSONObject objCreate = new JSONObject();
		String toreturn = "";
		try{
		objCreate.put("AppID", AppID);
		objCreate.put("Nick", Nick);
		objCreate.put("Msg", Msg);
		objCreate.put("Flag", Flag);
		System.out.println("CreatUltiJson appid===="+AppID);
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
	
	
	public void addtoChatReq(String appID, String nick, String ip, String pc, String block){
		System.out.println("function addtopeerdb() called!!!");
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
		
	new SendMessage().execute();
	
	}
	}
	public void addtomsgdb(String ID, String AppID, String Content, String Time){
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("ID", ID);
			obj.put("AppID", AppID);
			obj.put("Content", Content);
			obj.put("Time", Time);
			System.out.println("Jason string for Msg DB==>>"+"\n"+obj.toString());
			db.insertMsg(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error"+ e.getMessage().toString());
		}
	}
	public void sendMessage(View v)
	{
		String newMessage = text.getText().toString().trim();
		Time now = new Time();
		now.setToNow();
		System.out.println("pm button click me aya"+now.toString());
		//String now = "10:10 A.M";
		if(newMessage.length() > 0)
		{
			text.setText("");
			System.out.println("pm daal rahe hain list me");
			
			String print = newMessage + "\n" +now.toString().substring(9, 12);
			addNewMessage(new Message1(print, true));
			new SendMessage().execute();
		}
		
		//addtomsgdb(getID(), apppid, newMessage, now.toString());
		try{
			String se = createUltiJSON(pmapppid, sender, newMessage, "Yeh msg hai");
			sen= new pmsender(se,broad);
			sen.start();
			}
			catch(Exception e){
				System.out.println("JSON string"+e.getMessage());
			}
	}
		
	public void onDestroy(){
		super.onDestroy();

		}
		
	private class SendMessage extends AsyncTask<Void, String, String>
	{
		DatagramSocket socket=null;
		byte[] data = new byte[100000];
		//static int temo_c = 0;
		JSONObject jo = new JSONObject();
		DatagramPacket packet =new DatagramPacket(data, data.length);
		//TextView textv;
		public boolean state=false;
		String pass,sendadd;
		String tobesent="";
		//socket = new DatagramSocket(4444);
			
		
		@Override
		protected String doInBackground(Void... params) {
			
			try
			{
			socket = new DatagramSocket(6666);
			socket.receive(packet);
			System.out.println("received");
			//temo_c++;
			String message =new String(packet.getData(), 0, packet.getLength());
			sendadd = packet.getAddress().toString();
			System.out.println("\t"+message);
			pass=message;
			String[] fin = parseUltiJSON(pass);
			/*
			 * fin[0] = AppID
			 * fin[1] = Nick
			 * fin[2] = Message
			 * fin[3] = Flag
			 */
			if(fin[3].equals("Peers request")){
				
				String se = createUltiJSON(pmapppid, sender, "Hey", "Reply for Request Peers");
				sen = new pmsender(se,sendadd);
				sen.start();
				
				}
			
				else if(fin[3].equals("Yeh msg hai")){
					if(!(fin[2].length()==0)){
						Time now1 = new Time();
						now1.setToNow();
						tobesent = fin[1]+" : "+fin[2]+"\n"+now1.toString();
						}
				}
				else if(fin[3].equals("Add me!")){
					System.out.println("Add me Request received.");
					addtoChatReq(fin[0], fin[1], sendadd, "0", "0");
					
				}
			

			}
			catch(Exception e)
			{
			System.out.println("Receiver pm chat====809080980"+e);
			}			
			socket.close();
			return tobesent;

		}

		//@Override
		protected void onPostExecute(String text) {
	
			if(!(text.length()==0)){
			System.out.println("pm onPostExec!!!!====>"+text+"==== length===="+text.length());
			addNewMessage(new Message1(text, false)); // add to the listview bubble.
			
			}
		}
		

	}
	void addNewMessage(Message1 m)
	{
		//if(!(m.message.length()==0))
		messages.add(m);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
		//}
		new SendMessage().execute();
	}
}
	