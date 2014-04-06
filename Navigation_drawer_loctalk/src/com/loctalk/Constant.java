package com.loctalk;

import com.loctalk.database.AppDB;

public class Constant {

	/*
	 * Global objects
	 */
	static profileSolver profileS1 = new profileSolver();
	public static AppDB db;
	public static String myAppID = profileS1.getAppID();
	public static String myNick = profileS1.getNick();
	public static dbFunc dbFunctions;
	public static jsonSolver jsonFunctions1;
	
	
}
