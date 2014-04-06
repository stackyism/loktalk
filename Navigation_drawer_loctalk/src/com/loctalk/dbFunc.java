package com.loctalk;
import static com.loctalk.Constant.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dbFunc {
	
	
	
	
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
