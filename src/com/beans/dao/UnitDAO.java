package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class UnitDAO {
	DBConn conn = null;

	public UnitDAO(){
		conn = new DBConn();
	}
	public JSONObject getUnitId(){
		JSONObject unitId = null;
		String sql = "SELECT MAX(UNIT_ID)+1 AS UNIT_ID FROM UNIT";
		unitId = conn.getJsonData(sql);
		try{
	    	for(int i = unitId.get("UNIT_ID").toString().length(); i<2; i++){
	    		unitId.put("UNIT_ID", "0"+unitId.get("UNIT_ID"));
	    	}
			return unitId;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO UNIT "+
					 "(UNIT_ID, UNIT_NAME) "+
					 "VALUES "+
					 "('"+a.get("UNIT_ID")+"','"+a.get("UNIT_NAME")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE UNIT SET "+
					 "UNIT_NAME = '"+a.get("UNIT_NAME")+"' "+
					 "WHERE UNIT_ID = '"+a.get("UNIT_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
}
