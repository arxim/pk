package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class UnitTypeDAO {
	DBConn conn = null;

	public UnitTypeDAO(){
		conn = new DBConn();
	}

	public JSONObject getUnitTypeId(){
		JSONObject unitTypeId = null;
		String sql = "SELECT MAX(UNIT_TYPE_ID)+1 AS UNIT_TYPE_ID FROM UNIT_TYPE";
		unitTypeId = conn.getJsonData(sql);
		try{
	    	for(int i = unitTypeId.get("UNIT_TYPE_ID").toString().length(); i<3; i++){
	    		unitTypeId.put("UNIT_TYPE_ID", "0"+unitTypeId.get("UNIT_TYPE_ID"));
	    	}
			return unitTypeId;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO UNIT_TYPE "+
					 "(UNIT_TYPE_ID, UNIT_TYPE_NAME) "+
					 "VALUES "+
					 "('"+a.get("UNIT_TYPE_ID")+"','"+a.get("UNIT_TYPE_NAME")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE UNIT_TYPE SET "+
					 "UNIT_TYPE_NAME = '"+a.get("UNIT_TYPE_NAME")+"' "+
					 "WHERE UNIT_TYPE_ID = '"+a.get("UNIT_TYPE_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
}