package com.color.speechbubble;

import static com.color.speechbubble.Constant.db;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.color.speechbubble.database.AppDB;
//import com.color.speechbubble.peerActivity.SendMessage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PeersFragment extends ListFragment {

	
	
	JSONObject jsonObject;
	AwesomeAdapter adapter;
	ArrayList<Message1> messages;
	static String apppid,sender,broad="192.168.71.255";
	sender sen;
	ArrayList<Message1> ar = new ArrayList<Message1>();
	ArrayList<String> id = new ArrayList<String>();
	 public PeersFragment() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/*public String [] parseJSON(String msgJSONValue) throws JSONException
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.peerlist, container, false);
        
        if (db == null)
			db = new AppDB(getActivity());
        
        
         
        return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("peer fragment");
		messages = new ArrayList<Message1>();

		messages.add(new Message1("Hello", false));
		messages.add(new Message1("Hi!", true));
		messages.add(new Message1("Wassup??", false));
		messages.add(new Message1("nothing much, working on speech bubbles.", true));
		messages.add(new Message1("you say!", true));
		messages.add(new Message1("oh thats great. how are you showing them", false));
		

		adapter = new AwesomeAdapter(getActivity(), messages);
		setListAdapter(adapter);
		super.onActivityCreated(savedInstanceState);
		
		if (db == null)
			db = new AppDB(getActivity());
		
		String s;
		s = createUltiJSON(apppid,sender,"Hi peers","Peers request");
		sen = new sender(s,broad);
		sen.start();
		new SendMessage().execute();
		//setListAdapter(new ArrayAdapter<String>(peerActivity.this, android.R.layout.simple_list_item_1, ar));		
		
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		final int index = position;
		String tempid = this.id.get(index);
		int ti = Integer.parseInt(tempid);
		final String peerInfo[] = db.getOnePeer(ti);
		AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
		alert.setTitle("Send request"+ar.get(index));
		alert.setMessage("Need to send request For Chat first!!!");
		alert.setButton(alert.BUTTON_NEUTRAL, "Send Request", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
					String req = createUltiJSON(apppid,sender,"Lets Talk", "Add me!");
					sen = new sender(req,peerInfo[2]);
					sen.start();
					
			}
		});
		alert.setButton(alert.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alert.show();

	}
	
	
	private class SendMessage extends AsyncTask<Void, String, String>
	{
		DatagramSocket socket=null;
		byte[] data = new byte[100000];
		JSONObject jo = new JSONObject();
		DatagramPacket packet =new DatagramPacket(data, data.length);
		String pass,sendadd;
		String tobesent="";			
		
		@Override
		protected String doInBackground(Void... params) {
			System.out.println("Async me aya");
			
			try
			{
			System.out.println("try of doinBG!!!");
			socket = new DatagramSocket(4444);
			socket.receive(packet);
			System.out.println("received");
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
				//MessageActivity ma2 = new MessageActivity();
				//ma2.addFromPeerToMsg(message);
			}
			
			else if(temp_parse[3].equals("Peers request")){
				System.out.println("Aayi request peers ke liye");

				String se = createUltiJSON(apppid,sender,"Hey","Reply for Request Peers");
				sen = new sender(se,broad);
				sen.start();
				}
			else if(temp_parse[3].equals("Add me!")){
				System.out.println("Add me Request received.");
				addtoChatReq(temp_parse[0],temp_parse[1],sendadd,"0","0");
				
			}
			}
			catch(Exception e)
			{
			System.out.println("Receiver socket==809080980"+e);
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
			
			 // add the orignal message from server.
			
		}
		

	}
	public void addtopeerdb(String appID, String nick, String ip, String pc, String block){
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
			//ar.add(nick);
			id.add(appID);
			//adapter=new AwesomeAdapter(getActivity(),);
			//setListAdapter(new ArrayAdapter<String>(getActivity(),R.layout.peerlist, ar));
			//return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PeerDB error"+ e.getMessage().toString());
					
		}
		
	
		//HMC_added++;
	new SendMessage().execute();
	
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
	
	}
	}

}

	
	
	

