package com.loctalk;

import static com.loctalk.Constant.db;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.loctalk.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
//import com.color.speechbubble.MessageActivity.IncomingHandler;

/**
 * MessageActivity is a main Activity to carry out group chat. The chat is being stored into database so
 * it can be fetched whenever required.
 * 
 * 
 * 
 *
 */
public class MessageActivity extends BaseActivity {
	/** Called when the activity is first created. */

	ArrayList<Message1> messages;				//to  input messages to listview
	AwesomeAdapter adapter;						
	EditText text;
	static boolean rec_ser=false;
	static String sender;
	String apppid; 								// apppid is the App ID which will be unique for each user.
	String broad = "192.168.71.255" ;			//broadcast IP
	static int counter =0;						//used as by getID(), which returns the message ID(PK)
	sender1 sen;									// Sender thread object
	TextView textv;							
	JSONObject jsonObject;
	ArrayList<String> arp = new ArrayList<String>(); //stores messages that were 
													 // broadcasted when the user was in peerActivity
  public MessageActivity(String n, String i) {
		// TODO Auto-generated constructor stub
	  //Storing values passed on by the start-screen
		sender = n;	//Storing nick
		apppid = i;	//storing application ID
	}
  public MessageActivity(){
  }
	public String getID(){
		/*Method that returns a unique incremented number, used as primary key
		 * in the Messages table(containing messages).
		 */
		counter++;
		String sd = Integer.toString(counter);
		return sd;
	}
	public String getNick(){
		/* returns nick of the app user.*/
		return sender;
	}
	public String createUltiJSON(String AppID, String Nick,  String Msg, String Flag){
		/* 
		 * Method used for creating a JSON string.
		 */
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
		/* 
		 * Method used for parsing a JSON string.
		 */
		JSONObject jsonObj = new JSONObject(strJSON);
		String toret[] = new String[4];
		toret[0] = jsonObj.getString("AppID");
		toret[1] = jsonObj.getString("Nick");
		toret[2] = jsonObj.getString("Msg");
		toret[3] = jsonObj.getString("Flag");
		
		return toret;
		
		
	}
	/*
	 * old functions that were used earlier.
	 */
	/*public String [] parseJSON(String msgJSONValue) throws JSONException
	{
		jsonObject = new JSONObject(msgJSONValue);

		JSONObject object = jsonObject.getJSONObject("Message");
		String sender = object.getString("Sender");
		String time = object.getString("Time");
	    String msg_text = object.getString("Msg");
	    String flag = object.getString("Flag");
	    String temp[] = {sender,time,msg_text,flag};
	    System.out.println("Parse===>>	"+sender+time+msg_text);
	    return (temp);
		
	}
	
	public String createJSON(String msg_value, String flag) throws JSONException{
		
		//String temp = "{\"Message\": {\"Msg\": \""+msg_value+"\",\"Time\": \""+"10:10"+"\",\"Sender\"";
	    //String temp = "{\"Message\": {\"Sender\": \""+sender_nick+"\",\"Time\": \""+time_value+"\",\"Msg\"   ":\""+nick+"\"}}";
		// replace by uncommented string by commented and call get_nick() to get nick;
	    
		Time now = new Time();
		now.setToNow();
		String temp = "{\"Message\": {\"Sender\": \""+sender+"\",\"Time\": \""+now.toString()+"\",\"Msg\":\""+msg_value+"\",\"Flag\":\""+flag+"\"}}";
	    return temp;
	}*/

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		 * The tasks to be carried on when the 
		 * MessageActivity is being created. 
		 */
		super.onCreate(savedInstanceState);
		System.out.println("oncreate ma!!");
		setContentView(R.layout.main); //Setting the layout.
	
		text = (EditText) this.findViewById(R.id.text);
		
		this.setTitle(sender);
		
		//Send request for add DB
		messages = new ArrayList<Message1>();
		/*
		 * Set of preinitialized messages for testing purpose.
		 */
		messages.add(new Message1("Hello", false));
		messages.add(new Message1("Hi!", true));
		messages.add(new Message1("Wassup??", false));
		messages.add(new Message1("nothing much, working for SEN Project.", true));
		messages.add(new Message1("you say!", true));
		messages.add(new Message1("oh thats great. how is it coming along", false));
		

		adapter = new AwesomeAdapter(this, messages);
		setListAdapter(adapter);
		addNewMessage(new Message1("mmm, trying our best, giving our fullest.", true));
		new SendMessage().execute(); //carrying out the receiving process.
		
	}
	
	public void addFromPeerToMsg(String jsonStr){
		/*
		 * Adding messages from PeerActivity to MessageActivity 
		 */
		arp.add(jsonStr);
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
		
	new SendMessage().execute(); //For receiving the messages.
	
	}
	}
	public void addtomsgdb(String ID, String AppID, String Content, String Time){
		/*
		 * Method to add messages to the table-Messages.
		 */
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
		/*
		 * Method called when the "Send" button is clicked.
		 */
		String newMessage = text.getText().toString().trim();
		Time now = new Time();
		now.setToNow();
		if(newMessage.length() > 0)
		{
			text.setText("");
			
			
			String print = newMessage + "\n" +now.toString();
			addNewMessage(new Message1(print, true));
			new SendMessage().execute();
		}
		
		addtomsgdb(getID(), apppid, newMessage, now.toString());
		try{
			String se = createUltiJSON(apppid, sender, newMessage, "Yeh msg hai");
			sen= new sender1(se,broad);
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
		/*
		 * Method that carries out the task of receiving the messages
		 * and based on them(on flag) it carries out the required task.
		 */
		DatagramSocket socket=null;
		byte[] data = new byte[100000];
		DatagramPacket packet =new DatagramPacket(data, data.length);
		public boolean state=false;
		String pass,sendadd;
		String tobesent="";
					
		
		@Override
		protected String doInBackground(Void... params) {
			
			try
			{
			socket = new DatagramSocket(4444);
			socket.receive(packet);
			System.out.println("received");
			String message =new String(packet.getData(), 0, packet.getLength()); //extracting message from datagram packet.
			sendadd = packet.getAddress().toString(); // Extracting IP address of the sender from the dataram packet.
			System.out.println("\t"+message);
			pass=message;
			String[] fin = parseUltiJSON(pass); //Parsing the message received.
			/*
			 * fin[0] = AppID
			 * fin[1] = Nick
			 * fin[2] = Message
			 * fin[3] = Flag
			 */
			if(fin[3].equals("Peers request")){
				/*
				 * Replying with own data so that the sender, who requested the peer to send data,
				 * can store the data of online peers.
				 */
				String se = createUltiJSON(apppid, sender, "Hey", "Reply for Request Peers");
				sen = new sender1(se,sendadd);
				sen.start();
				
				}
			
				else if(fin[3].equals("Yeh msg hai")){
					/*
					 * if the incoming message is a chat message
					 * then it is being stored in database.
					 */
					if(!(fin[2].length()==0)){
						Time now1 = new Time();
						now1.setToNow();
						tobesent = fin[1]+" : "+fin[2]+"\n"+now1.toString();
						addtomsgdb(getID(), fin[0], fin[2],now1.toString());
						}
				}
				else if(fin[3].equals("Add me!")){
					/*
					 * if the sender requests the user to add him/her 
					 * for personal messages, add it to the ChatReq table.
					 */
					System.out.println("Add me Request received.");
					addtoChatReq(fin[0], fin[1], sendadd, "0", "0");
					
				}
			

			}
			catch(Exception e)
			{
			System.out.println("809080980"+e);
			}			
			socket.close();
			return tobesent;	//contains the chat message which has to be added in the custom listview.

		}

		//@Override
		protected void onPostExecute(String text) {
			/*
			 * carrying out the task of adding the message passed on by the doinbackground method.
			 * the message is added to the custom listview.
			 */
			if(!(text.length()==0)){
			System.out.println("onPostExec!!!!====>"+text+"==== length===="+text.length());
			addNewMessage(new Message1(text, false)); // add to the listview bubble.
			
			}
		}
		

	}
	void addNewMessage(Message1 m)
	{
		/*method for adding the message to the listview*/
		if(!(m.message.length()==0))
		{messages.add(m);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
		}
		new SendMessage().execute();
	}
	
	protected void onStart() {
		/*
		 * when this activity is again launched after it was replaced by some other activity
		 * (for e.g. peerActivty), carrying out the task of adding the messages that were received when
		 * some other activity was running.
		 */
		 // TODO Auto-generated method stub
		 for(int i=0;i<arp.size();i++){
			 String json = arp.get(i);
			 try {
				String parsed[] = parseUltiJSON(json);
				Time t = new Time();
				t.setToNow();
				String send = parsed[1]+": "+parsed[2]+"\n"+t.toString();
				addNewMessage(new Message1(send,false));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 arp.clear();
		 super.onStart();
		}
	
	//@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		//doUnbindService();
		//unregisterReceiver(myReceiver);
		super.onStop();
	}
	
	
}