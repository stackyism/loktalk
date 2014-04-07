package com.loctalk;

import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import com.loctalk.R;
import com.loctalk.database.AppDB;
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

public class PeersFragment extends ListFragment implements dataTransferInterface{

	
	
	peerAdapter adapter;
	static String broadcastip;
	sender sen;
	ArrayList<peerData> peerName;
	ArrayList<String> id = new ArrayList<String>();
	 public PeersFragment() {
		// TODO Auto-generated constructor stub
	}
	
	 
	 
	 
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		broadcastip = getArguments().getString("broadip");
        View rootView = inflater.inflate(R.layout.peerlist, container, false);
        
        if (db == null)
			db = new AppDB(getActivity());
        
        
         
        return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		adapter = new peerAdapter(getActivity(), peerName);
		setListAdapter(adapter);
		System.out.println("peer fragment");
		String s;
		s = jsonFunctions1.createUltiJSON(myAppID,myNick,"Hi peers","peerReq");
		sen = new sender(s,broadcastip);
		sen.start();
		
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		final int index = position;
		String tempid = this.id.get(index);
		int ti = Integer.parseInt(tempid);
		final String peerInfo[] = db.getOnePeer(ti);
		AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
		alert.setTitle("Send request"+peerName.get(index));
		alert.setMessage("Need to send request For Chat first!!!");
		alert.setButton(alert.BUTTON_NEUTRAL, "Send Request", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
					String req = jsonFunctions1.createUltiJSON(myAppID,myNick,"Lets Talk", "chatReq");
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
	
	public void passdatatofragment(String id,String msg) {
		
	}





	@Override
	public void passdatatopeerfragment(String broadip, String[] data,
			String peerip) {
		// TODO Auto-generated method stub
		broadcastip = broadip;
		//dbFunctions.addtopeerdb(data[0], data[1], peerip, "0", "0");
		peerName.add(new peerData(data[1],0));
		id.add(data[0]);
		adapter.notifyDataSetChanged();
		//addtopeerlistview(peerName);
	}

		
}

	
	
	

