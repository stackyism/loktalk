package com.color.speechbubble;

import static com.color.speechbubble.Constant.db;
import android.app.ListActivity;
import android.os.Bundle;

import com.color.speechbubble.database.AppDB;

public class BaseActivity extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if (db == null)
			db = new AppDB(this);
	}
}
