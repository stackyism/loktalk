package com.loctalk.database;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;

public class AppDB extends DBConnect {
	/*
	 * Has all the methods to carryout database related activities.
	 */
	
	public AppDB(Context context) {
		super(context);
	}

	/**
	 * To insert Message into the table - "Messages"
	 *  
	 */
	public void insertMsg(JSONObject objStudent) {
		
		String sqlCards;
		
		try {
			sqlCards = String.format(ISql.INSERT_MSG, Integer.parseInt(objStudent.getString("ID")), 
					Integer.parseInt(objStudent.getString("AppID")), 
					objStudent.getString("Content"),
					objStudent.getString("Time"));
					
			
			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
 
	/**
	 * To insert peer
	 * @param value
	 */
	public void insertPeer(JSONObject objPeer) {
		String sqlCards;
		System.out.println("Insertpeer AppDB called via db.insertpeer()");
		try {
			sqlCards = String.format(ISql.INSERT_PEER, Integer.parseInt(objPeer.getString("AppID")), 
					objPeer.getString("Nick"),
					objPeer.getString("MAC"),
					objPeer.getString("IP"),
					Integer.parseInt(objPeer.getString("PC")),
					Integer.parseInt(objPeer.getString("Block")));
			
			System.out.println("Inserted into peer DB==="+objPeer.getString("Nick"));
			
			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void insertChatReq(JSONObject objPeer) {
		String sqlCards;
		System.out.println("Insertpeer AppDB called via db.insertChatReq()");
		try {
			sqlCards = String.format(ISql.INSERT_CHATREQ, Integer.parseInt(objPeer.getString("AppID")), 
					objPeer.getString("Nick"),
					objPeer.getString("MAC"),
					objPeer.getString("IP"),
					Integer.parseInt(objPeer.getString("PC")),
					Integer.parseInt(objPeer.getString("Block")));
			
			System.out.println("Inserted into ChatReq DB==="+objPeer.getString("Nick"));
			
			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 
	 * 
	 * To remove Student
	 */
	public void removeMsg(int ID)
	{
		if(ID>0)
		{
			String sqlRemoveRegCard = String.format(ISql.REMOVE_MSG, ID);
			execNonQuery(sqlRemoveRegCard);
		}
	}
	public String[] getOnePeer(int ID)
	{	//JSONObject objPeer;
		String peerRet[] =  new String[5];
		if(ID>0)
		{
			String sqlRemoveRegCard = String.format(ISql.GET_ONEPEER, ID);
			Cursor cursor = execQuery(sqlRemoveRegCard);
			if(cursor!=null){
				//objPeer = new JSONObject();
				
				if (cursor.moveToNext()) {
				try {
					peerRet[0] = String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID")));
					peerRet[1] = cursor.getString(cursor.getColumnIndex("Nick"));
					//cursor.getString(cursor.getColumnIndex("MAC"));
					peerRet[2] = cursor.getString(cursor.getColumnIndex("IP"));
					peerRet[3] = String.valueOf(cursor.getInt(cursor.getColumnIndex("PC")));
					peerRet[4] = String.valueOf(cursor.getInt(cursor.getColumnIndex("Block")));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}	
			}
		}
		return peerRet;
		
	}
	/**
	 * To count no. of Students
	 * @return
	 */
	public int countMsg()
	{
		Cursor cursor = execQuery(ISql.COUNT_MSG);
		int cntCards = 0;
		
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToNext();
			cntCards = Integer.parseInt(cursor.getString(0));
		}
		
		if(cursor!= null) {
			cursor.close();
		}
			
		return cntCards;
	}
	
	
	/**
	 * To count no. of Peers
	 * @return
	 */
	public int countPeer()
	{
		Cursor cursor = execQuery(ISql.COUNT_PEER);
		int cntCards = 0;
		
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToNext();
			cntCards = Integer.parseInt(cursor.getString(0));
		}
		
		if(cursor!= null) {
			cursor.close();
		}
			
		return cntCards;
	}
	
	/**
	 * To count no. of Chat requests
	 * @return
	 */
	public int countChatReq()
	{
		Cursor cursor = execQuery(ISql.COUNT_CHATREQ);
		int cntCards = 0;
		
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToNext();
			cntCards = Integer.parseInt(cursor.getString(0));
		}
		
		if(cursor!= null) {
			cursor.close();
		}
			
		return cntCards;
	}
	/**
	 * To get all the Students
	 * @return
	 */
	public ArrayList<JSONObject> getMsgs() {
		Cursor cursor = execQuery(ISql.GET_MSG);

		ArrayList<JSONObject> listMsg = new ArrayList<JSONObject>();
		JSONObject objStudent;
		
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {
					objStudent = new JSONObject();
					
					try {
						objStudent.put("ID", String.valueOf(cursor.getInt(cursor.getColumnIndex("ID"))));
						objStudent.put("AppID", cursor.getString(cursor.getColumnIndex("AppID")));
						objStudent.put("Content", cursor.getString(cursor.getColumnIndex("Content")));
						objStudent.put("Time", cursor.getString(cursor.getColumnIndex("Time")));
						listMsg.add(objStudent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listMsg;
	}
	
	public ArrayList<JSONObject> getPeers() {
		Cursor cursor = execQuery(ISql.GET_PEER);

		ArrayList<JSONObject> listPeer = new ArrayList<JSONObject>();
		JSONObject objPeer;
		
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {
					objPeer = new JSONObject();
					
					try {
						objPeer.put("AppID", String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID"))));
						objPeer.put("Nick", cursor.getString(cursor.getColumnIndex("Nick")));
						objPeer.put("MAC", cursor.getString(cursor.getColumnIndex("MAC")));
						objPeer.put("IP", cursor.getString(cursor.getColumnIndex("IP")));
						objPeer.put("PC", String.valueOf(cursor.getInt(cursor.getColumnIndex("PC"))));
						objPeer.put("Block", String.valueOf(cursor.getInt(cursor.getColumnIndex("Block"))));
						listPeer.add(objPeer);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listPeer;
	}
}
