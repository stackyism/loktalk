package com.loctalk;

import org.json.JSONException;
import org.json.JSONObject;

public class jsonSolver {

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
		String toret[] = new String[4];
		toret[0] = jsonObj.getString("AppID");
		toret[1] = jsonObj.getString("Nick");
		toret[2] = jsonObj.getString("Msg");
		toret[3] = jsonObj.getString("Flag");
		
		return toret;
		
		
	}
}
