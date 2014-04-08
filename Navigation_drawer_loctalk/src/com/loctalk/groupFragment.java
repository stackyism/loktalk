package com.loctalk;

import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;
import java.util.ArrayList;
import android.widget.ImageButton;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class groupFragment extends ListFragment implements dataTransferInterface {
	ArrayList<Message1> messages;
	AwesomeAdapter adapter;
	EditText text;
	String broad;
	String groupFlag;
	sender sen;
	TextView textv;
	JSONObject jsonObject;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		groupFlag=getArguments().getString("flag");
        View rootView = inflater.inflate(R.layout.main, container, false);
        return rootView;
    }
	public void onAttach(Activity activity){
		super.onAttach(activity);  
		}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Activity context = getActivity();
		((MainActivity)context).datatofragment = this;
		((MainActivity)context).passdatatoActivity();
		System.out.println("from fragment"+((MainActivity)context).datatofragment.getClass());
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		System.out.println("oncreate homefragment!!");
		text = (EditText) getActivity().findViewById(R.id.text);
		ImageButton button = (ImageButton)getActivity().findViewById(R.id.sendButton);	
		ArrayList<JSONObject> Posts;
		int i;
		if(db.countPost()>0)
		{
		messages = dbFunctions.getpostdb(groupFlag);
		for(i=0;i<messages.size()-1;i++)
		{
			System.out.println("###############>"+messages.get(i).getMessage());
		}
		adapter = new AwesomeAdapter(getActivity(), messages);
		setListAdapter(adapter);
		}
		else
		{
			messages = new ArrayList<Message1>();
			messages.add(new Message1("Hello", false));
			adapter = new AwesomeAdapter(getActivity(), messages);
			setListAdapter(adapter);
		}
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendMessage(v);
			}
		});
		
		
	}
	public void sendMessage(View v)
	{
		String newMessage = text.getText().toString().trim(); 
		if(newMessage.length() > 0)
		{
			text.setText("");
			addNewMessages(new Message1(newMessage, true));
			//new SendMessage().execute();
		}
		try{
			String se = jsonFunctions1.createUltiJSON(myAppID,myNick, newMessage, groupFlag);
			sen= new sender(se,broad);
			sen.start();
			}
			
			catch(Exception e){
				System.out.println("JSON string"+e.getMessage());
			}
	}
	public boolean accessAct(int i){
		System.out.println("====Service--->Activity:::::::: "+i);
		return true;
	}

	
	public void onDestroy(){
		super.onDestroy();
		}
	
	 void addNewMessages(Message1 m)
	{
		
		System.out.println("Enter add message for send"+m.getMessage());
		if(!(m.message.length()==0))
		{
			System.out.println("Enter add message 1");
		messages.add(m);
		System.out.println("Enter add message 2");
		adapter.notifyDataSetChanged();
		System.out.println("Enter add message 3");
		getListView().setSelection(messages.size()-1);
		System.out.println("Enter add message 4");
		System.out.println("added message");
		}
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	public void addmsg(String msg){
		addNewMessages(new Message1(msg, false));
		return;
	}
	@Override
	public void passdatatofragment(String id,String msg) {
		if(id=="ip")
		{
			broad=msg;
		}
		else
		{
			addNewMessages(new Message1(msg, false));
		}
		return;
	}
	@Override
	public void passdatatopeerfragment(int PCstatus, String[] data,
			String peerip) {
		// TODO Auto-generated method stub
		
	}
}
