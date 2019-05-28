package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;

public class CustomerDAO {
	DBConn conn = null;
	String autoCompleteCondition = "";

	public CustomerDAO() {
		conn = new DBConn();
	}
	public void setAutoCompleteCondition(String a){
		this.autoCompleteCondition = a;
	}
	public JSONObject getCustomerTable(String businessId){
		String sql =  "SELECT CUSTOMER.CUST_ID, CUSTOMER.CUST_NAME, CUSTOMER.ADDRESS1, CUSTOMER.TELEPHONE, USERS.NAME "+
						  "FROM CUSTOMER LEFT OUTER JOIN USERS ON CUSTOMER.EMP_ID = USERS.USER_ID "+
						  "WHERE CUSTOMER.BUSINESS_ID = '"+businessId+"'";
		 try {
			 return conn.getJsonArrayData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	
	public JSONObject getCustomerArea(String businessId){
		String sql =  "SELECT CUSTOMER.CUST_ID, CUSTOMER.CUST_NAME, CUSTOMER.ADDRESS1, CUSTOMER.TELEPHONE, USERS.NAME, AREA.ROUTE_NAME "+
					"FROM CUSTOMER LEFT OUTER JOIN USERS ON CUSTOMER.EMP_ID = USERS.USER_ID "+
					"LEFT OUTER JOIN AREA ON CUSTOMER.AREA_ID = AREA.AREA_ID " +
					"WHERE CUSTOMER.BUSINESS_ID = '"+businessId+"'";
		 try {
			 return conn.getJsonArrayData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	
	public JSONObject getCustomerByID(String custID){
		String sql =  "SELECT * FROM CUSTOMER WHERE CUST_ID = '"+custID+"'";
		 try {
			 return conn.getJsonData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}

	public String getCustomerAutoComplete(){
		return "SELECT CUST_ID, CUST_NAME FROM CUSTOMER WHERE CUST_ID+CUST_NAME LIKE '%"+this.autoCompleteCondition+"%'";
	}
	public String insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "INSERT INTO CUSTOMER "+
					 "(BUSINESS_ID, CUST_ID, CUST_NAME, ADDRESS1, ADDRESS2, TELEPHONE, MOBILE, FAX, DISCOUNT_RATE, "+
					 "PAYMENT_TERM, EMP_ID, UPDATE_DATE, UPDATE_TIME, AREA_ID, TAX_ID, INVOICE_TYPE, REMARK, CR_LIMIT_AMOUNT) "+
					 "VALUES "+
					 "('"+a.get("BUSINESS_ID")+"','"+a.get("CUST_ID")+"','"+a.get("CUST_NAME")+"','"+
					 a.get("ADDRESS1")+"','"+a.get("ADDRESS2")+"','"+a.get("TELEPHONE")+"','"+
					 a.get("MOBILE")+"','"+a.get("FAX")+"','"+a.get("DISCOUNT_RATE")+"','"+a.get("PAYMENT_TERM")+"','"+
					 a.get("EMP_ID")+"','"+a.get("UPDATE_DATE")+"','"+a.get("UPDATE_TIME")+"','"+a.get("AREA_ID")+"', '"+
					 a.get("TAX_ID")+"', '"+a.get("INVOICE_TYPE")+"', '"+a.get("REMARK")+"', '"+a.get("CR_LIMIT_AMOUNT")+"')";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
	public String updateData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE CUSTOMER SET "+
					 "CUST_NAME = '"+a.get("CUST_NAME")+"', "+
					 "ADDRESS1 = '"+a.get("ADDRESS1")+"', "+
					 "ADDRESS2 = '"+a.get("ADDRESS2")+"', "+
					 "MOBILE = '"+a.get("MOBILE")+"', "+
					 "FAX = '"+a.get("FAX")+"', "+
					 "DISCOUNT_RATE = '"+a.get("DISCOUNT_RATE")+"', "+
					 "PAYMENT_TERM = '"+a.get("PAYMENT_TERM")+"', "+
					 "EMP_ID = '"+a.get("EMP_ID")+"', "+
					 "UPDATE_DATE = '"+a.get("UPDATE_DATE")+"', "+
					 "UPDATE_TIME = '"+a.get("UPDATE_TIME")+"', "+
					 "AREA_ID = '"+a.get("AREA_ID")+"', "+
					 "TAX_ID = '"+a.get("TAX_ID")+"', "+
					 "INVOICE_TYPE = '"+a.get("INVOICE_TYPE")+"', "+
					 "REMARK = '"+a.get("REMARK")+"', "+
					 "CR_LIMIT_AMOUNT = '"+a.get("CR_LIMIT_AMOUNT")+"' "+
					 "WHERE CUST_ID = '"+a.get("CUST_ID")+"'";
		System.out.println(sql);
		conn.doConnect();
		conn.doSave(sql);
		conn.doCommit();
		return "";
	}
}