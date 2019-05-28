package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;
import com.beans.utils.JDate;

public class ReturnProductDAO {
	DBConn conn = null;

	public ReturnProductDAO(){
		conn = new DBConn();
	}
	
	public JSONObject getReturnProductCurrentDate() {
		JSONObject js = new JSONObject();
		String sql =  "SELECT ITEM_NO, DOC_NO, DOC_DATE, CUST_DOC_NO, RETURN_PRODUCT.PART_NO, "+
					  "PRODUCT.PRD_NAME, QUANTITY, AMOUNT, TOTAL_AMOUNT, CN_DATE "+
					  "FROM RETURN_PRODUCT LEFT OUTER JOIN PRODUCT "+
					  "ON RETURN_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "WHERE DOC_DATE = '" + JDate.getDate() + "' " +
					  "ORDER BY DOC_DATE, DOC_NO DESC";
		js = conn.getJsonArrayData(sql);
		conn.doDisconnect();
		 try {
			 return js;
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	
	public JSONObject getReturnProductDetailTable(String startDate, String endDate, String cust_id){
		JSONObject js = new JSONObject();
		String sql =  "SELECT ITEM_NO, DOC_NO, DOC_DATE, CUST_DOC_NO, RETURN_PRODUCT.PART_NO, "+
					  "PRODUCT.PRD_NAME, QUANTITY, AMOUNT, TOTAL_AMOUNT, CN_DATE "+
					  "FROM RETURN_PRODUCT LEFT OUTER JOIN PRODUCT "+
					  "ON RETURN_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "WHERE RETURN_PRODUCT.DOC_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' "+
					  "AND RETURN_PRODUCT.CUST_ID LIKE '"+cust_id+"'";
		js = conn.getJsonArrayData(sql);
		conn.doDisconnect();
		 try {
			 return js;
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getReturnProductFillForm(String docNo, String itemNo){
		String sql = "";
    	sql = "SELECT RP.DOC_NO, RP.DOC_DATE, RP.ITEM_NO, RP.CUST_ID, CUSTOMER.CUST_NAME, CUSTOMER.ADDRESS1, "+
    		  "RP.CUST_DOC_NO, RP.CN_DATE, RP.INVOICE_NO, RP.INVOICE_DATE, RP.PART_NO, PRODUCT.PRD_NAME, "+
    		  "RP.AMOUNT, RP.QUANTITY, RP.TOTAL_AMOUNT, RP.REMARK "+
    		  "FROM RETURN_PRODUCT AS RP "+
    		  "LEFT OUTER JOIN CUSTOMER ON RP.CUST_ID = CUSTOMER.CUST_ID "+
    		  "LEFT OUTER JOIN PRODUCT ON RP.PART_NO = PRODUCT.PART_NO "+
      		  "WHERE RP.DOC_NO = '"+docNo+"' "+
      		  "AND RP.ITEM_NO = '"+itemNo+"'";
		try {
			return conn.getJsonData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	public boolean insertData(HashMap<String,String> a){
		boolean status = true;
		String sql = "INSERT INTO RETURN_PRODUCT "+
					 "(DOC_NO, DOC_DATE, CUST_DOC_NO, INVOICE_NO, INVOICE_DATE, CN_DATE, CUST_ID, "+
					 "PART_NO, ITEM_NO, AMOUNT, QUANTITY, TOTAL_AMOUNT, REMARK, CREATE_DATE, CREATE_TIME, CREATE_USER_ID) "+
					 "VALUES "+
					 "('"+a.get("DOC_NO")+"','"+a.get("DOC_DATE")+"','"+a.get("CUST_DOC_NO")+"','"+a.get("INVOICE_NO")+
					 "','"+a.get("INVOICE_DATE")+"','"+a.get("CN_DATE")+"','"+a.get("CUST_ID")+"','"+a.get("PART_NO")+
					 "','"+a.get("ITEM_NO")+"','"+a.get("AMOUNT")+"','"+a.get("QUANTITY")+"','"+a.get("TOTAL_AMOUNT")+
					 "','"+a.get("REMARK")+"','"+JDate.getDate()+"','"+JDate.getTime()+"','"+a.get("USER_ID")+"')";
		conn.doConnect();
		try{
			conn.doSave(sql);
			conn.doCommit();
			status = true;
		}catch (Exception e){
			System.out.println(e);
			conn.doRollback();
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
	public boolean updateData(HashMap<String,String> a){
		boolean status = false;
		String sql = "UPDATE RETURN_PRODUCT SET "+
					 "DOC_NO = '"+a.get("DOC_NO")+"', "+
					 "DOC_DATE = '"+a.get("DOC_DATE")+"', "+
					 "CUST_DOC_NO = '"+a.get("CUST_DOC_NO")+"', "+
					 "INVOICE_NO = '"+a.get("INVOICE_NO")+"', "+
					 "INVOICE_DATE = '"+a.get("INVOICE_DATE")+"', "+
					 "CN_DATE = '"+a.get("CN_DATE")+"', "+
					 "CUST_ID = '"+a.get("CUST_ID")+"', "+
					 "PART_NO = '"+a.get("PART_NO")+"', "+
					 "AMOUNT = '"+a.get("AMOUNT")+"', "+
					 "QUANTITY = '"+a.get("QUANTITY")+"', "+
					 "TOTAL_AMOUNT = '"+a.get("TOTAL_AMOUNT")+"', "+
					 "REMARK = '"+a.get("REMARK")+"', "+
					 "UPDATE_DATE = '"+JDate.getDate()+"', "+
					 "UPDATE_TIME = '"+JDate.getTime()+"', "+
					 "UPDATE_USER_ID = '"+a.get("USER_ID")+"' "+
					 "WHERE DOC_NO = '"+a.get("DOC_NO")+"' "+
					 "AND ITEM_NO = '"+a.get("ITEM_NO")+"'";
		conn.doConnect();
		try{
			conn.doSave(sql);
			conn.doCommit();
			status = true;
		}catch (Exception e){
			System.out.println(e);
			conn.doRollback();
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
	public boolean delete(String docNo, String itemNo){
		String sql = "";
		boolean status = false;
    	sql = "DELETE FROM RETURN_PRODUCT "+
      		  "WHERE DOC_NO = '"+docNo+"' AND ITEM_NO = '"+itemNo+"'";
		conn.doConnect();
		if(conn.doSave(sql)){
			status = conn.doCommit();
		}else{
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
	public JSONObject getDocNo(){
		JSONObject docNo = new JSONObject();
		try{
			docNo.put("DOC_NO", ""+JDate.getDate()+JDate.getTime());
			docNo.put("DOC_DATE", JDate.getDate());
		}catch(Exception e){
			System.out.println(e);
		}
		return docNo;
	}
	public JSONObject getItemNo(String docNo){
		JSONObject itemNo = null;
		String sql = "SELECT MAX(ITEM_NO)+1 AS ITEM_NO "+
					 "FROM RETURN_PRODUCT WHERE DOC_NO = '"+docNo+"'";
		itemNo = conn.getJsonData(sql);
		try{
			if(itemNo.get("ITEM_NO").equals("") || itemNo.get("ITEM_NO").equals("NULL")){
				itemNo.put("ITEM_NO", "1");
			}
			return itemNo;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
}
