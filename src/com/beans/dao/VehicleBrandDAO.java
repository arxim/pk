package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class VehicleBrandDAO {
	DBConn conn = null;

	public VehicleBrandDAO(){
		conn = new DBConn();
	}
	public JSONObject getVehicleBrandId(){
		JSONObject vehicleBrandId = null;
		String sql = "SELECT MAX(VEHICLE_BRAND_ID)+1 AS VEHICLE_BRAND_ID FROM VEHICLE_BRAND";
		vehicleBrandId = conn.getJsonData(sql);
		try{
	    	for(int i = vehicleBrandId.get("VEHICLE_BRAND_ID").toString().length(); i<3; i++){
	    		vehicleBrandId.put("VEHICLE_BRAND_ID", "0"+vehicleBrandId.get("VEHICLE_BRAND_ID"));
	    	}
			return vehicleBrandId;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO VEHICLE_BRAND "+
					 "(VEHICLE_BRAND_ID, VEHICLE_BRAND_NAME) "+
					 "VALUES "+
					 "('"+a.get("VEHICLE_BRAND_ID")+"','"+a.get("VEHICLE_BRAND_NAME")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE VEHICLE_BRAND SET "+
					 "VEHICLE_BRAND_NAME = '"+a.get("VEHICLE_BRAND_NAME")+"' "+
					 "WHERE VEHICLE_BRAND_ID = '"+a.get("VEHICLE_BRAND_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
}