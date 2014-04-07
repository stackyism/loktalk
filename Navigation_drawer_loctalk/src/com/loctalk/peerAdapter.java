package com.loctalk;

import java.util.ArrayList;





import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class peerAdapter extends BaseAdapter {
	
	private Context lcontext;
	private ArrayList<peerData> llist;
	
	
	
	public peerAdapter(Context context, ArrayList<peerData> list){
		this.lcontext = context;
		this.llist = list;
		}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return llist.size(); 
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return llist.get(position);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		
            vi = LayoutInflater.from(lcontext).inflate(R.layout.peerrow, parent, false);
		
          TextView nametv = (TextView) vi.findViewById(R.id.title);
          ImageView band = (ImageView) vi.findViewById(R.id.stripe);
          
          peerData list = llist.get(position);
          
          nametv.setText(list.name);
          switch(list.status){
          case 0 : band.setImageResource(R.color.red);
          			break;
          			
          case 1 : band.setImageResource(R.color.golden);
          			break;
          
          case 2 : band.setImageResource(R.color.green);
					break;
          			
          }
          
            
          return vi;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
	
class peerData{
	String name;
	int status;
		/*
		 * Status = 0 ==> nothing yet.
		 * Status = 1 ==> pending request, i.e. request is sent.
		 * Status = 2 ==> accepted request.
		 */
		
		
	public peerData(String peerNick , int stat) {
		// TODO Auto-generated constructor stub
		super();
		this.name = peerNick;
		this.status = stat;
	
	}

	public void add(String peerNick, int stat){
		this.name = peerNick;
		this.status = stat;
		return;
	}
		
		
}
	
	
