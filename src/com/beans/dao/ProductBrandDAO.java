package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class ProductBrandDAO {
	DBConn conn = null;

	public ProductBrandDAO(){
		conn = new DBConn();
	}
	public JSONObject getProductBrandId(){
		JSONObject prdBrandId = null;
		String sql = "SELECT MAX(PRD_BRAND_ID)+1 AS PRD_BRAND_ID FROM PRODUCT_BRAND";
		prdBrandId = conn.getJsonData(sql);
		try{
	    	for(int i = prdBrandId.get("PRD_BRAND_ID").toString().length(); i<3; i++){
	    		prdBrandId.put("PRD_BRAND_ID", "0"+prdBrandId.get("PRD_BRAND_ID"));
	    	}
			return prdBrandId;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO PRODUCT_BRAND "+
					 "(PRD_BRAND_ID, PRD_BRAND_NAME) "+
					 "VALUES "+
					 "('"+a.get("PRD_BRAND_ID")+"','"+a.get("PRD_BRAND_NAME")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE PRODUCT_BRAND SET "+
					 "PRD_BRAND_NAME = '"+a.get("PRD_BRAND_NAME")+"' "+
					 "WHERE PRD_BRAND_ID = '"+a.get("PRD_BRAND_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
}