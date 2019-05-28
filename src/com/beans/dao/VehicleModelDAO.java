package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class VehicleModelDAO {
	DBConn conn = null;

	public VehicleModelDAO(){
		conn = new DBConn();
	}
	public JSONObject getVehicleModelId(){
		JSONObject vehicleModelId = null;
		String sql = "SELECT MAX(VEHICLE_MODEL_ID)+1 AS VEHICLE_MODEL_ID FROM VEHICLE_MODEL";
		vehicleModelId = conn.getJsonData(sql);
		try{
	    	for(int i = vehicleModelId.get("VEHICLE_MODEL_ID").toString().length(); i<5; i++){
	    		vehicleModelId.put("VEHICLE_MODEL_ID", "0"+vehicleModelId.get("VEHICLE_MODEL_ID"));
	    	}
			return vehicleModelId;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO VEHICLE_MODEL "+
					 "(VEHICLE_MODEL_ID, VEHICLE_MODEL_NAME) "+
					 "VALUES "+
					 "('"+a.get("VEHICLE_MODEL_ID")+"','"+a.get("VEHICLE_MODEL_NAME")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE VEHICLE_MODEL SET "+
					 "VEHICLE_MODEL_NAME = '"+a.get("VEHICLE_MODEL_NAME")+"' "+
					 "WHERE VEHICLE_MODEL_ID = '"+a.get("VEHICLE_MODEL_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
}