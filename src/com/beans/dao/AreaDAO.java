package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class AreaDAO {
	DBConn conn = null;

	public AreaDAO(){
		conn = new DBConn();
	}
	public JSONObject getAreaId(){
		JSONObject areaId = null;
		String sql = "SELECT MAX(AREA_ID) + 1 AS AREA_ID FROM AREA";
		areaId = conn.getJsonData(sql);
		try{
//	    	for(int i = areaId.get("AREA_ID").toString().length(); i < 3; i++){
//	    		areaId.put("AREA_ID", "0" + areaId.get("AREA_ID"));
//	    	}
			return areaId;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO AREA (AREA_ID, ROUTE_NAME) VALUES "+
					 "('"+a.get("AREA_ID")+"','"+a.get("ROUTE_NAME")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE AREA SET " +
					 "ROUTE_NAME = '" + a.get("ROUTE_NAME")+"' "+
					 "WHERE AREA_ID = '" + a.get("AREA_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
}