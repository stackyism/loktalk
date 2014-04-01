package com.loctalk.database;

public interface ISql {
	
	String INSERT_MSG = "INSERT OR REPLACE INTO Messages(ID, AppID, Content, Time) values (%d, %d, '%s', '%s')";
	String GET_MSG = "SELECT ID,AppID,Content,Time FROM Messages";
	String REMOVE_MSG = "DELETE FROM Messages WHERE ID = %d";
	String COUNT_MSG = "SELECT count(ID) from Messages";
	
	//String GET_STUDENTS = "Select * from tblStudent";
	
	String INSERT_PEER = "INSERT OR REPLACE INTO Peers(AppID, Nick, MAC, IP, PC, Block) values (%d, '%s', '%s', '%s', %d, %d)";
	String GET_PEER = "SELECT AppID,Nick,MAC,IP,PC,Block FROM Peers";
	String REMOVE_PEER = "DELETE FROM Peers WHERE AppID = %d";
	String COUNT_PEER = "SELECT count(AppID) from Peers";
	String GET_ONEPEER = "SELECT AppID,Nick,IP,PC,Block FROM Peers WHERE AppID = %d";
	String INSERT_CHATREQ = "INSERT OR REPLACE INTO ChatReq(AppID, Nick, MAC, IP, PC, Block) values (%d, '%s', '%s', '%s', %d, %d)";
	String COUNT_CHATREQ = "SELECT count(AppID) from ChatReq";
}
