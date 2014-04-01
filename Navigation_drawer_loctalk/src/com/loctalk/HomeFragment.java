package com.loctalk;

import static com.loctalk.Constant.db;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.loctalk.R;

import com.loctalk.*;
import com.loctalk.database.AppDB;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeFragment extends ListFragment {
	
	public HomeFragment(){}
	
	
	ArrayList<Message1> messages;
	AwesomeAdapter adapter;
	EditText text;
	static boolean rec_ser=false;
	Boolean chose= false,data = false;
	static Random rand = new Random();	
	static String sender = "Paliwal";
	String broad = "192.168.71.255" ;
	//MyReceiver myReceiver;
	static int counter =0;
	//Yeh aya hamare wale se...
	String s;
	//DatagramSocket socket=null;
	sender sen;
	//receiver re;
	TextView textv;
	JSONObject jsonObject;
	SendMessage receiver;
	
	

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.main, container, false);
        
        if (db == null)
			db = new AppDB(getActivity());
        
        
         
        return rootView;
    }
	
	
	
	public String getID(){
		counter++;
		String sd = Integer.toString(counter);
		return sd;
	}
	public String getNick(){
		return sender;
	}
	public String [] parseJSON(String msgJSONValue) throws JSONException
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
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		super.onStart();
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
		receiver= new SendMessage();
		super.onCreate(savedInstanceState);
	}
	
	
	public String createJSON(String msg_value, String flag) throws JSONException{
		
		//String temp = "{\"Message\": {\"Msg\": \""+msg_value+"\",\"Time\": \""+"10:10"+"\",\"Sender\"";
	    //String temp = "{\"Message\": {\"Sender\": \""+sender_nick+"\",\"Time\": \""+time_value+"\",\"Msg\"   ":\""+nick+"\"}}";
		// replace by uncommented string by commented and call get_nick() to get nick;
	    
		String temp = "{\"Message\": {\"Sender\": \""+sender+"\",\"Time\": \""+"time_value"+"\",\"Msg\":\""+msg_value+"\",\"Flag\":\""+flag+"\"}}";
	    return temp;
	}
	
	
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//setRetainInstance(true);
		System.out.println("oncreate homefragment!!");
		
		//getActivity().setContentView(R.layout.main);
	//	if(!rec_ser){
		//receiversr2 rese= new receiversr2();
		//receiversr2 reserv = new receiversr2(sender,broad);
		
		//int flagser = rese.getflag();
//		rese.setmyacflagto1();
//		if(flagser==0){
//			Intent in = new Intent(MessageActivity.this,receiverser.class);
//			MessageActivity.this.startService(in);
//			//Toast.makeText(MessageActivity.this, "Service intented",Toast.LENGTH_SHORT).show();
//		}
//		
		text = (EditText) getActivity().findViewById(R.id.text);
		Button button = (Button)getActivity().findViewById(R.id.sendButton);
		
		//sender = Utility.sender[rand.nextInt( Utility.sender.length-1)];
		getActivity().setTitle(sender);
		messages = new ArrayList<Message1>();

		messages.add(new Message1("Hello", false));
		messages.add(new Message1("Hi!", true));
		messages.add(new Message1("Wassup??", false));
		messages.add(new Message1("nothing much, working on speech bubbles.", true));
		messages.add(new Message1("you say!", true));
		messages.add(new Message1("oh thats great. how are you showing them", false));
		

		adapter = new AwesomeAdapter(getActivity(), messages);
		setListAdapter(adapter);
		
		receiver.execute();
		//addNewMessage(new Message1("mmm, well, using 9 patches png to show them.", true));
		
		
		/*try {
			String se = createJSON("","Request for dataads");
			sen = new sender(se,broad);
			sen.start();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Yeh hai request wala exception" + e.getMessage());
			e.printStackTrace();
		}*/
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sendMessage(v);
			}
		});
		
		
	}
	
	
	public void addtodb(String id, String nick, String msg, String flag){
		JSONObject objStudent = new JSONObject();
		String strID = id;
		String strMsg = msg;
		
			// getNick(), getTime(), getFlag(), getID()
		String strNick = nick ;
		//Date d = new Date();
		//CharSequence s  = DateFormat.format("hh:mm:ss", d.getTime());
		//String strTime = s.toString();//SystemClock.currentThreadTimeMillis() ;
		String strTime = "10:10 AM";
		String strFlag= flag ;
		
		try {
			objStudent.put("ID", strID);
			objStudent.put("nick", strNick);
			
			objStudent.put("time", strTime);
			objStudent.put("msg", strMsg);
			objStudent.put("flag", strFlag);
			System.out.println("Jason string for db==>>"+"\n"+objStudent.toString());
			db.insertMsg(objStudent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error"+ e.getMessage().toString());
		}
	}
	public void sendMessage(View v)
	{
		String newMessage = text.getText().toString().trim(); 
		if(newMessage.length() > 0)
		{
			text.setText("");
			addNewMessagessend(new Message1(newMessage, true));
			//new SendMessage().execute();
		}
		
		//addtodb(getID(),getNick(),newMessage,"Yeh msg hai");
		//Toast t = Toast.makeText(MessageActivity.this,"Hey"+newMessage,Toast.LENGTH_LONG);
		//t.show();
		//int i = db.countStudent();
		//Toast to = Toast.makeText(MessageActivity.this,"count="+i,Toast.LENGTH_LONG);
		//to.show();
		//System.out.println("added to db!!===>>>"+i);
		try{
			String se = createJSON(newMessage,"Yeh msg hai");
			sen= new sender(se,broad);
			sen.start();}
			
			catch(Exception e){
				System.out.println("JSON string"+e.getMessage());
			}
	}
	
	public void sendMessage(View v,String message)
	{
		String newMessage = message; 
		if(newMessage.length() > 0)
		{
			text.setText("");
			addNewMessagessend(new Message1(newMessage, true));
			//new SendMessage().execute();
		}
		
		//addtodb(getID(),getNick(),newMessage,"Yeh msg hai");
		//Toast t = Toast.makeText(MessageActivity.this,"Hey"+newMessage,Toast.LENGTH_LONG);
		//t.show();
		//int i = db.countStudent();
		//Toast to = Toast.makeText(MessageActivity.this,"count="+i,Toast.LENGTH_LONG);
		//to.show();
		//System.out.println("added to db!!===>>>"+i);
		try{
			String se = createJSON(newMessage,"Yeh msg hai");
			sen= new sender(se,broad);
			sen.start();}
			catch(Exception e){
				System.out.println("JSON string"+e.getMessage());
			}
	}
	
	/*class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
		System.out.println("incoming handler main acitvity===>"+msg.getData().getString("name"));
		if(msg.what==receiverser.MSG_SET_VALUE){
		try{
		//String s = createJSON(msg.getData().getString("message"), msg.getData().getString("Sender"), "maa chudaye time abhi :p");
		//String pmessage[] = new String[4];
		//pmessage = parseJSON(msg.getData().getString("message"));
		//String ip = msg.getData().getString("Sender");
		}catch(Exception e){
			
		}
		}
		}
	}*/
	
//	private class MyReceiver extends BroadcastReceiver{
//		 
//		 @Override
//		 public void onReceive(Context arg0, Intent arg1) {
//		  // TODO Auto-generated method stub
//		  
//		 String data = arg1.getStringExtra("DATAPASSED"); 
//		 //int datapassed = arg1.getIntExtra("DATAPASSED", 0);
//		  System.out.println("Bcr me aya"+data);
//		  //MessageActivity ma = new MessageActivity();
//		  addmsg(data);
//		  
//		 }
//		 
//		}

	
	public boolean accessAct(int i){
		System.out.println("====Service--->Activity:::::::: "+i);
		return true;
	}
	public void addmsg(String msg){
		//addNewMessage(new Message1(msg, false));
	}
	
	public void onDestroy(){
		super.onDestroy();
//		receiverser rese= new receiverser();
//		rese.setmyacflagtoz();
		}
	
	//Message1 forreceiver;
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
			System.out.println("receiving from"+Thread.currentThread().getId());
			try
			{
			
			socket = new DatagramSocket(4444);
			socket.receive(packet);
			System.out.println("received"+Thread.currentThread().getId());
			//temo_c++;
			String message =new String(packet.getData(), 0, packet.getLength());
			sendadd = packet.getAddress().toString();
			System.out.println("\t"+message);
			pass=message;
			String[] fin = parseJSON(pass);
			if(!(fin[2].length()==0)){
			tobesent = fin[0]+" : "+fin[2]+" ===="+Thread.currentThread().getId();
			
			}
			
			//state=true;

			//ma.getstring(message);
			//textv.setText(message);
			//textv.setText("fuck"+textv.getText().toString()+message);


			}
			catch(Exception e)
			{
			System.out.println("809080980"+e);
			}			
			socket.close();
			System.out.println("Socket closed"+Thread.currentThread().getId());
			return tobesent;

		}
		@Override
//		public void onProgressUpdate(String... v) {
//			
//			if(messages.get(messages.size()-1).isStatusMessage)//check wether we have already added a status message
//			{
//				messages.get(messages.size()-1).setMessage(v[0]); //update the status for that
//				adapter.notifyDataSetChanged(); 
//				getListView().setSelection(messages.size()-1);
//			}
//			else{
//				System.out.println("onProgress!!!!====>"+v+"==== length===="+v[0].length());
//				if(!(v[0].length()==0))
//				addNewMessage(new Message1(true,v[0])); //add new message, if there is no existing status message
//			}
//		}
		//@Override
		protected void onPostExecute(String text) {
	
			if(!(text.length()==0)){
			System.out.println("onPostExec!!!!====>"+text+"==== length===="+text.length());
			
			System.out.println("starting this thing from receivver"+Thread.currentThread().getId());
			addNewMessagesrec(new Message1(text, false)); // add the orignal message from server.
			}
			//socket.close();
		}
		

	}
	
	void addNewMessagesrec(Message1 m)
	{
		try{
			System.out.println("Enter add message"+m.getMessage());
			if(m.getMessage()=="doing this start it again on emptystring"){
				//receiver.cancel(true);
				receiver=new SendMessage();
				receiver.execute();
				
			}
			else if(m.getMessage()!=sender+" : "+"please kill every thing before you moveon")
			{
		
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
		
		receiver.cancel(true);
		receiver=new SendMessage();
		receiver.execute();
			
		System.out.println("Execute send message again");
			}
		}
		catch(Exception e)
		{
			
			System.out.println("Execute catch");
			
		}
	}
	
	void addNewMessagessend(Message1 m)
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
	
//	protected void onStart() {
//		 // TODO Auto-generated method stub
//		 	//if(!rec_ser){
//		      //Register BroadcastReceiver
//		      //to receive event from our service
//		      myReceiver = new MyReceiver();
//		      IntentFilter intentFilter = new IntentFilter();
//		      intentFilter.addAction(receiverser.MY_ACTION);
//		      registerReceiver(myReceiver, intentFilter);
//		     
//		      //Start our own service
//		      Intent intent = new Intent(MessageActivity.this,
//		        receiverser.class);
//		      startService(intent);
//		 	//}
//		 	rec_ser = true;
//		 super.onStart();
//		}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		
		sendMessage(getView(),"please kill every thing before you moveon");
		receiver.cancel(true);
		super.onDestroyView();
	}
	

	
	
}
