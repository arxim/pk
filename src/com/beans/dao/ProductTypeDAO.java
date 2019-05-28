package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class ProductTypeDAO {
	DBConn conn = null;

	public ProductTypeDAO(){
		conn = new DBConn();
	}
	public JSONObject getProductTypeId(){
		JSONObject prdTypeId = null;
		String sql = "SELECT MAX(PRD_TYPE_ID)+1 AS PRD_TYPE_ID FROM PRODUCT_TYPE";
		prdTypeId = conn.getJsonData(sql);
		try{
	    	for(int i = prdTypeId.get("PRD_TYPE_ID").toString().length(); i<5; i++){
	    		prdTypeId.put("PRD_TYPE_ID", "0"+prdTypeId.get("PRD_TYPE_ID"));
	    	}
			return prdTypeId;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO PRODUCT_TYPE "+
					 "(PRD_TYPE_ID, PRD_TYPE_NAME) "+
					 "VALUES "+
					 "('"+a.get("PRD_TYPE_ID")+"','"+a.get("PRD_TYPE_NAME")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE PRODUCT_TYPE SET "+
					 "PRD_TYPE_NAME = '"+a.get("PRD_TYPE_NAME")+"' "+
					 "WHERE PRD_TYPE_ID = '"+a.get("PRD_TYPE_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
}