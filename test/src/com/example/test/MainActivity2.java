package com.example.test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity2 extends Activity{
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);    
    }
    
    boolean canAddItem = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast toast;
        if(item.getItemId() == R.id.action_volume_muted){
            invalidateOptionsMenu();
        }
        else{
            toast = Toast.makeText(this, item.getTitle()+" Clicked!", Toast.LENGTH_SHORT);
            toast.show();
        }
 
        return super.onOptionsItemSelected(item);
    }
 
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
 
        if(canAddItem){
            menu.getItem(0).setIcon(R.drawable.ic_action_volume_on);
            canAddItem = false;
        }
        else{
            menu.getItem(0).setIcon(R.drawable.ic_action_volume_muted);
            canAddItem = true;
        }
 
        return super.onPrepareOptionsMenu(menu);
    }
}
