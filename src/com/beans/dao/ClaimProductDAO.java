package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;
import com.beans.utils.JDate;

public class ClaimProductDAO {
	DBConn conn = null;

	public ClaimProductDAO(){
		conn = new DBConn();
	}
	
	public String getClaimProductDetailStm(String startDate, String endDate, String cust_id, String sup_id, String claimType){
		String ctype = "";
		
		if(claimType.equals("1")){
			ctype = "AND CLAIM_PRODUCT.CUST_DOC_DATE != '' ";
		}else if(claimType.equals("2")){
			ctype = "AND CLAIM_PRODUCT.CUST_DOC_DATE = '' ";			
		}else if(claimType.equals("3")){
			ctype = "AND CLAIM_PRODUCT.SUP_DOC_DATE = '' ";			
		}else if(claimType.equals("4")){
			ctype = "AND CLAIM_PRODUCT.SUP_DOC_DATE != '' ";			
		}else{
			
		}
		String sql =  "SELECT ITEM_NO, DOC_NO, DOC_DATE, CUSTOMER.CUST_NAME, CLAIM_PRODUCT.CUST_DOC_DATE, SUPPLIER.SUP_NAME, CLAIM_PRODUCT.SUP_SEND_DATE, CLAIM_PRODUCT.SUP_DOC_DATE, "+
					  "CLAIM_PRODUCT.PART_NO, PRODUCT.PRD_NAME, QUANTITY "+
					  "FROM CLAIM_PRODUCT "+
					  "LEFT OUTER JOIN PRODUCT ON CLAIM_PRODUCT.PART_NO = PRODUCT.PART_NO "+
		    		  "LEFT OUTER JOIN CUSTOMER ON CLAIM_PRODUCT.CUST_ID = CUSTOMER.CUST_ID "+
		    		  "LEFT OUTER JOIN SUPPLIER ON CLAIM_PRODUCT.SUP_ID = SUPPLIER.SUP_ID "+
					  "WHERE CLAIM_PRODUCT.DOC_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' "+ctype+
					  "AND CLAIM_PRODUCT.CUST_ID LIKE '"+cust_id+"' AND CLAIM_PRODUCT.SUP_ID LIKE '"+sup_id+"'";
		System.out.println(sql);
		return sql;
	}
	
	public JSONObject getClaimProductDetailTable(String startDate, String endDate, String cust_id, String sup_id, String claimType){
		 JSONObject js = new JSONObject();
		 try {
			 js = conn.getJsonArrayData(getClaimProductDetailStm(startDate, endDate, cust_id, sup_id, claimType));
			 conn.doDisconnect();
				
			 return js;
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	
	
	
	
	public JSONObject getClaimProductFillForm(String docNo, String itemNo){
		String sql = "";
    	sql = "SELECT CP.DOC_NO, CP.DOC_DATE, CP.ITEM_NO, CP.CUST_ID, CUSTOMER.CUST_NAME, "+
    		  "CP.SUP_ID, SUPPLIER.SUP_NAME, CP.CUST_DOC_NO, CP.CUST_DOC_DATE, "+
    		  "CP.SUP_DOC_NO, CP.SUP_DOC_DATE, CP.SUP_SEND_DATE, CP.PART_NO, PRODUCT.PRD_NAME, "+
    		  "CP.QUANTITY, CP.NOTE "+
    		  "FROM CLAIM_PRODUCT AS CP "+
    		  "LEFT OUTER JOIN CUSTOMER ON CP.CUST_ID = CUSTOMER.CUST_ID "+
    		  "LEFT OUTER JOIN SUPPLIER ON CP.SUP_ID = SUPPLIER.SUP_ID "+
    		  "LEFT OUTER JOIN PRODUCT ON CP.PART_NO = PRODUCT.PART_NO "+
      		  "WHERE CP.DOC_NO = '"+docNo+"' "+
      		  "AND CP.ITEM_NO = '"+itemNo+"'";
		try {
			return conn.getJsonData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	public boolean insertData(HashMap<String,String> a){
		boolean status = true;
		String sql = "INSERT INTO CLAIM_PRODUCT "+
					 "(DOC_NO, DOC_DATE, CUST_ID, SUP_ID, CUST_DOC_NO, CUST_DOC_DATE, SUP_DOC_NO, SUP_DOC_DATE, "+
					 "SUP_SEND_DATE, ITEM_NO, PART_NO, QUANTITY, NOTE, CREATE_DATE, CREATE_TIME, CREATE_USER_ID) "+
					 "VALUES "+
					 "('"+a.get("DOC_NO")+"','"+a.get("DOC_DATE")+"','"+a.get("CUST_ID")+"','"+a.get("SUP_ID")+
					 "','"+a.get("CUST_DOC_NO")+"','"+a.get("CUST_DOC_DATE")+"','"+a.get("SUP_DOC_NO")+
					 "','"+a.get("SUP_DOC_DATE")+"','"+a.get("SUP_SEND_DATE")+"','"+a.get("ITEM_NO")+"','"+a.get("PART_NO")+
					 "','"+a.get("QUANTITY")+"','"+a.get("NOTE")+"','"+JDate.getDate()+"','"+JDate.getTime()+"','"+a.get("USER_ID")+"')";
		System.out.println(sql);
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
		String sql = "UPDATE CLAIM_PRODUCT SET "+
					 "DOC_NO = '"+a.get("DOC_NO")+"', "+
					 "DOC_DATE = '"+a.get("DOC_DATE")+"', "+
					 "CUST_ID = '"+a.get("CUST_ID")+"', "+
					 "SUP_ID = '"+a.get("SUP_ID")+"', "+
					 "CUST_DOC_NO = '"+a.get("CUST_DOC_NO")+"', "+
					 "CUST_DOC_DATE = '"+a.get("CUST_DOC_DATE")+"', "+
					 "SUP_DOC_NO = '"+a.get("SUP_DOC_NO")+"', "+
					 "SUP_DOC_DATE = '"+a.get("SUP_DOC_DATE")+"', "+
					 "SUP_SEND_DATE = '"+a.get("SUP_SEND_DATE")+"', "+
					 "PART_NO = '"+a.get("PART_NO")+"', "+
					 "QUANTITY = '"+a.get("QUANTITY")+"', "+
					 "NOTE = '"+a.get("NOTE")+"', "+
					 "UPDATE_DATE = '"+JDate.getDate()+"', "+
					 "UPDATE_TIME = '"+JDate.getTime()+"', "+
					 "UPDATE_USER_ID = '"+a.get("USER_ID")+"' "+
					 "WHERE DOC_NO = '"+a.get("DOC_NO")+"' "+
					 "AND ITEM_NO = '"+a.get("ITEM_NO")+"'";
		System.out.println(sql);
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
    	sql = "DELETE FROM CLAIM_PRODUCT "+
      		  "WHERE DOC_NO = '"+docNo+"' AND ITEM_NO = '"+itemNo+"'";
		System.out.println(sql);
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
					 "FROM CLAIM_PRODUCT WHERE DOC_NO = '"+docNo+"'";
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