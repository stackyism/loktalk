package com.loctalk;
import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static  com.loctalk.Constant.myNick;
import static  com.loctalk.Constant.myAppID;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loctalk.database.AppDB;

import android.content.Context;

public class dbFunc {
	
	public dbFunc(AppDB con){
		if (db == null)
			db = con;
	}
	
	
	public String getAd(){
		return "";
	}
	
	public void addtoaddb(String s){
		
		
	}
	
	public void incrementVote(String adID){
		//adding to table Premium the vote count
	}
	
	public void removeAd(String adID){
		
	}
	
	public void addtopeerdb(String appID, String nick, String ip, String pc, String block){
		/*
		 * method to add the peers in the table(contained in database-loctalk.sqlite)- "Peers".
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
			//return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PeerDB error"+ e.getMessage().toString());
					
		}
	}
	
	public void addtoChatReq(String appID, String nick, String ip, String pc, String block){
		
		/*
		 * method to add the chat requests in the table(contained in database-loctalk.sqlite)- "ChatReq".
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
public void addtopostdb(String ID, String AppID, String Content, String Time,String Category){
		
		/*
		 * Method to add post to the table-"Post".
		 */
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("ID", ID);
			obj.put("AppID", AppID);
			obj.put("Content", Content);
			obj.put("Time", Time);
			obj.put("Category", Category);
			System.out.println("Jason string for post DB==>>"+"\n"+obj.toString());
			db.insertPost(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error"+ e.getMessage().toString());
		}
	}
public ArrayList<Message1> getpostdb(String Category){
	
	/*
	 * Method to get post to the table-"Post".
	 */
	ArrayList<JSONObject> Post=new ArrayList<JSONObject>();
	ArrayList<Message1> Posttoreturn=new ArrayList<Message1>();
	Message1 pmessage=null;
	String AppID;
	int Arraylength;
	int j;
	int i;
	try {
		Post=db.getPost(Category);
		Arraylength=Post.size();
		for(i=0;i<=Arraylength-1;i++)
		{
			AppID=Post.get(i).get("AppID").toString();
			System.out.println("===========>"+Post.get(i).get("Content")+Post.get(i).get("Time"));
			if(AppID.equals(myAppID))
			{
					pmessage=new Message1(myNick+Post.get(i).get("Content")+Post.get(i).get("Time"),true);//add time here
				
			}
			else
			{
					pmessage=new Message1(db.getOnePeer(Integer.parseInt(AppID))[1]+Post.get(i).get("Content")+Post.get(i).get("Time"),false);//add time here
			}
			Posttoreturn.add(Posttoreturn.size(),pmessage);
			for(j=0;j<=Posttoreturn.size()-1;j++)
			{
				System.out.println(";;;;;;;;;;;;;>"+Posttoreturn.get(i).getMessage());
			}
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("DB error"+ e.getMessage().toString());
	}
	
	return Posttoreturn;
}
	public void addtomsgdb(String ID, String AppID, String Content, String Time){
		
		/*
		 * Method to add messages to the table-"Messages".
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
}
