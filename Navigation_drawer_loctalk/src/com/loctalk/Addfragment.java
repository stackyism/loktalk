package com.loctalk;

import java.util.ArrayList;



import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Addfragment  extends ListFragment {

	
	
	ArrayList<single_row> llist;
	AdAdapter adapter;
	ListView list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_main_tiles, container, false);
		//View list = (ListView) getActivity().findViewById(android.R.id.list);
		
		return rootView;
		
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//adapter = new AdAdapter(getActivity(), llist);
		//list = (ListView) getActivity().findViewById(android.R.id.list);
		llist = new ArrayList<single_row>();
		llist.add(new single_row("hello1", "aalo le lo hajfhahfahfafhahffaf kjdfklhafa hdafakhfasf ufajfkaf lkjfajf "+"\n"+"aalo le lo hajfhahfahfafhahffaf kjdfklhafa hdafakhfasf ufajfkaf lkjfajf "));
		llist.add(new single_row("hello2", "gobi le lo"));
		llist.add(new single_row("hello3", "pyaaz le lo"));
		llist.add(new single_row("hello4", "tamatar le lo"));
		
		//list.setAdapter(new AdAdapter(getActivity(), llist));
		adapter = new AdAdapter(getActivity(), llist);
		setListAdapter(adapter);
	}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

