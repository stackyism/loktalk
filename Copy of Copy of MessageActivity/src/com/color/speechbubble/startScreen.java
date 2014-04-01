package com.color.speechbubble;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class startScreen extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	EditText enick;
	EditText eip;
	MessageActivity ma;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.start);
		enick = (EditText) this.findViewById(R.id.nick);
		eip = (EditText) this.findViewById(R.id.ip);
		super.onCreate(savedInstanceState);
		System.out.println("Start screen;;;;;______======");
	}
	
	public void submit(View v){
		String nick = enick.getText().toString().trim();
		String ip = eip.getText().toString().trim();
		System.out.println("nick:"+nick+"	ip:"+ip);
		ma = new MessageActivity(nick,ip);
		peerActivity pa1 = new peerActivity(nick,ip);
		Intent i = new Intent(startScreen.this,MainActivity.class);
		startScreen.this.startActivity(i);
		System.out.println("start k nichey intent k baad");
		startScreen.this.finish();//stopActivity();
	}
	

}
