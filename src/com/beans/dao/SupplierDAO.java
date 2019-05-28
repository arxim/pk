package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class SupplierDAO {
	DBConn conn = null;

	public SupplierDAO(){
		conn = new DBConn();
	}
	public JSONObject getSupplierTable(String businessId){
		String sql =  "SELECT SUP_ID, SUP_NAME, ADDRESS1+ADDRESS2, TELEPHONE, PAYMENT_TERM "+
					  "FROM SUPPLIER";
		 try {
			 return conn.getJsonArrayData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO SUPPLIER "+
					 "(SUP_ID, SUP_NAME, ADDRESS1, ADDRESS2, TELEPHONE, MOBILE, FAX, PAYMENT_TERM, REMARK, TAX_ID) "+
					 "VALUES "+
					 "('"+a.get("SUP_ID")+"','"+a.get("SUP_NAME")+"','"+
					 a.get("ADDRESS1")+"','"+a.get("ADDRESS2")+"','"+a.get("TELEPHONE")+"','"+
					 a.get("MOBILE")+"','"+a.get("FAX")+"','"+a.get("PAYMENT_TERM")+"', '"+a.get("REMARK")+"', '"+a.get("TAX_ID")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE SUPPLIER SET "+
					 "SUP_NAME = '"+a.get("SUP_NAME")+"', "+
					 "ADDRESS1 = '"+a.get("ADDRESS1")+"', "+
					 "ADDRESS2 = '"+a.get("ADDRESS2")+"', "+
					 "TELEPHONE = '"+a.get("TELEPHONE")+"', "+
					 "MOBILE = '"+a.get("MOBILE")+"', "+
					 "FAX = '"+a.get("FAX")+"', "+
					 "PAYMENT_TERM = '"+a.get("PAYMENT_TERM")+"', "+
					 "REMARK = '"+a.get("REMARK")+"', "+
					 "TAX_ID = '"+a.get("TAX_ID")+"' "+
					 "WHERE SUP_ID = '"+a.get("SUP_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}

}
