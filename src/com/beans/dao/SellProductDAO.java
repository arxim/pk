package com.beans.dao;

import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;
import com.beans.utils.JDate;
import com.beans.utils.JNumber;

public class SellProductDAO {
	DBConn conn = null;
	String userId = "";
	String hospital = "";
	String month = "";
	String year = "";
	String startDate = "";
	String endDate = "";

	public SellProductDAO(){
		conn = new DBConn();
	}
	public JSONObject getSellProductDetailTable(String tempInvoiceNo){
		JSONObject js = new JSONObject();
		String sql =  "SELECT SELL_PRODUCT.ITEM_NO, SELL_PRODUCT.PART_NO, PRODUCT.PRD_NAME, SELL_PRODUCT.QUANTITY, "+
					  "UNIT.UNIT_NAME, SELL_PRODUCT.AMOUNT, "+
					  "CASE WHEN CONVERT(varchar(10), SELL_PRODUCT.DISCOUNT_RATE) = '0.00' THEN '-' ELSE CONVERT(varchar(10), SELL_PRODUCT.DISCOUNT_RATE)+'% , ' END+"+
					  "CASE WHEN CONVERT(varchar(10), SELL_PRODUCT.EXTRA_DISCOUNT_RATE) = '0.00' THEN '-' ELSE CONVERT(varchar(10), SELL_PRODUCT.EXTRA_DISCOUNT_RATE)+'%' END AS DISCOUNT_RATE, "+
					  "CASE WHEN CONVERT(varchar(10), SELL_PRODUCT.DISCOUNT_AMOUNT) = '0.00' THEN '-' ELSE CONVERT(varchar(10), SELL_PRODUCT.DISCOUNT_AMOUNT)+' , ' END+"+
					  "CASE WHEN CONVERT(varchar(10), SELL_PRODUCT.EXTRA_DISCOUNT_AMOUNT) = '0.00' THEN '-' ELSE CONVERT(varchar(10), SELL_PRODUCT.EXTRA_DISCOUNT_RATE)+'' END AS DISCOUNT_AMOUNT, "+
					  "SELL_PRODUCT.TOTAL_NET_AMOUNT "+
					  "FROM SELL_PRODUCT LEFT OUTER JOIN PRODUCT ON SELL_PRODUCT.PART_NO=PRODUCT.PART_NO "+
					  "LEFT OUTER JOIN UNIT ON PRODUCT.UNIT_ID = UNIT.UNIT_ID "+
					  "WHERE SELL_PRODUCT.TEMP_INVOICE_NO = '"+tempInvoiceNo+"' ";
		js = conn.getJsonArrayData(sql);
		conn.doDisconnect();
		 try {
			 return js;
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getSellProductHistoryTable(String cust_id, String part_no, String prd_type_id, 
			String prd_brand_id, String start_date, String end_date, String temp_start_date, String temp_end_date){
		cust_id = cust_id.equals("") ? "%" : cust_id;
		part_no = part_no.equals("") ? "%" : part_no;
		prd_type_id = prd_type_id.equals("") ? "%" : prd_type_id;
		prd_brand_id = prd_brand_id.equals("") ? "%" : prd_brand_id;
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		temp_start_date = temp_start_date.equals("") ? "00000000" : temp_start_date;
		temp_end_date = temp_end_date.equals("") ? "99999999" : temp_end_date;
		String sql =  "SELECT SELL_PRODUCT.TEMP_INVOICE_DATE, SELL_PRODUCT.INVOICE_DATE, SELL_PRODUCT.CUST_ID+':'+CUSTOMER.CUST_NAME AS CUSTOMER, "+
					  "SELL_PRODUCT.PART_NO, PRODUCT.PRD_NAME, VEHICLE_MODEL.VEHICLE_MODEL_NAME, PRODUCT_BRAND.PRD_BRAND_NAME, "+
					  "SELL_PRODUCT.QUANTITY, SELL_PRODUCT.AMOUNT, SELL_PRODUCT.QUANTITY*SELL_PRODUCT.AMOUNT AS TOTAL_AMOUNT, "+
					  "CASE WHEN CONVERT(varchar(10), SELL_PRODUCT.DISCOUNT_RATE) = '0.00' THEN '-' ELSE CONVERT(varchar(10), SELL_PRODUCT.DISCOUNT_RATE)+'% , ' END+ "+
					  "CASE WHEN CONVERT(varchar(10), SELL_PRODUCT.EXTRA_DISCOUNT_RATE) = '0.00' THEN '-' ELSE CONVERT(varchar(10), SELL_PRODUCT.EXTRA_DISCOUNT_RATE)+'%' END AS DISCOUNT_RATE, "+
					  "CASE WHEN CONVERT(varchar(10), SELL_PRODUCT.DISCOUNT_AMOUNT) = '0.00' THEN '-' ELSE CONVERT(varchar(10), SELL_PRODUCT.DISCOUNT_AMOUNT)+' , ' END+ "+
					  "CASE WHEN CONVERT(varchar(10), SELL_PRODUCT.EXTRA_DISCOUNT_AMOUNT) = '0.00' THEN '-' ELSE CONVERT(varchar(10), SELL_PRODUCT.EXTRA_DISCOUNT_RATE)+'' END AS DISCOUNT_AMOUNT, "+
					  "SELL_PRODUCT.TAX_RATE, "+
					  "CONVERT(DECIMAL(15,2),SELL_PRODUCT.TOTAL_NET_AMOUNT/SELL_PRODUCT.QUANTITY,2) AS NET_PER_AMOUNT, "+
					  "SELL_PRODUCT.TOTAL_NET_AMOUNT, PRODUCT_CATEGORY.PRD_CATEGORY_NAME, "+
					  "PRODUCT_TYPE.PRD_TYPE_NAME, SELL_PRODUCT.TEMP_INVOICE_NO "+
					  "FROM SELL_PRODUCT "+
					  "LEFT OUTER JOIN PRODUCT ON SELL_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "LEFT OUTER JOIN CUSTOMER ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID "+
					  "LEFT OUTER JOIN VEHICLE_MODEL ON PRODUCT.VEHICLE_MODEL_ID = VEHICLE_MODEL.VEHICLE_MODEL_ID "+
					  "LEFT OUTER JOIN PRODUCT_BRAND ON PRODUCT_BRAND.PRD_BRAND_ID = PRODUCT.PRD_BRAND_ID "+
					  "LEFT OUTER JOIN PRODUCT_CATEGORY ON PRODUCT.PRD_CATEGORY_ID = PRODUCT_CATEGORY.PRD_CATEGORY_ID "+
					  "LEFT OUTER JOIN PRODUCT_TYPE ON PRODUCT.PRD_TYPE_ID = PRODUCT_TYPE.PRD_TYPE_ID "+
					  "WHERE SELL_PRODUCT.CUST_ID LIKE '"+cust_id+"' "+
					  "AND SELL_PRODUCT.PART_NO LIKE '"+part_no+"' "+
					  "AND PRODUCT.PRD_TYPE_ID LIKE '"+prd_type_id+"' "+
					  "AND PRODUCT.PRD_BRAND_ID LIKE '"+prd_brand_id+"' "+
					  "AND SELL_PRODUCT.TEMP_INVOICE_DATE BETWEEN '"+temp_start_date+"' AND '"+temp_end_date+"' "+
					  "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' "+
					  "ORDER BY SELL_PRODUCT.TEMP_INVOICE_DATE ASC";
		System.out.println(sql);
		 try {
			 return conn.getJsonArrayData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}	
	public JSONObject getSellBillingTable(String cust_id, String emp_id, String start_date, String end_date, String invoice_type){
		cust_id = cust_id.equals("") ? "%" : cust_id;
		emp_id = emp_id.equals("0")||emp_id.equals("") ? "%" : emp_id;
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		//System.out.println(cust_id+":"+emp_id+":"+start_date+":"+end_date);
		String sql = "SELECT ROW_NUMBER() OVER (ORDER BY SELL_PRODUCT.INVOICE_DATE, SELL_PRODUCT.INVOICE_NO) AS ROW, CUSTOMER.CUST_NAME, "+
					 "SELL_PRODUCT.TEMP_INVOICE_NO, SELL_PRODUCT.INVOICE_NO, SELL_PRODUCT.INVOICE_DATE, "+
					 "COUNT(*) AS ITEM_AMOUNT, SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT "+
					 "FROM SELL_PRODUCT "+
					 "LEFT OUTER JOIN CUSTOMER ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID " + // add
					 "WHERE SELL_PRODUCT.CUST_ID LIKE '"+cust_id+"' "+
					 "AND SELL_PRODUCT.EMP_ID LIKE '"+emp_id+"' "+
					 "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' "+
					 "AND SELL_PRODUCT.INVOICE_TYPE LIKE '"+invoice_type+"' "+
					 "GROUP BY CUSTOMER.CUST_NAME, SELL_PRODUCT.TEMP_INVOICE_NO, SELL_PRODUCT.INVOICE_NO, SELL_PRODUCT.INVOICE_DATE "+
					 "ORDER BY ROW";
		System.out.println(sql);
		try {
			return conn.getJsonArrayData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	public JSONObject getSumSellBillingTable(String cust_id, String emp_id, String start_date, String end_date, String invoice_type){
		cust_id = cust_id.equals("") ? "%" : cust_id;
		emp_id = emp_id.equals("0")||emp_id.equals("") ? "%" : emp_id;
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		String sql = "SELECT SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_AMOUNT, "+
					 "(SELECT COUNT(DISTINCT SELL_PRODUCT.TEMP_INVOICE_NO) FROM SELL_PRODUCT "+
					 "WHERE SELL_PRODUCT.CUST_ID LIKE '"+cust_id+"' "+
					 "AND SELL_PRODUCT.EMP_ID LIKE '"+emp_id+"' "+
					 "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' "+
					 "AND SELL_PRODUCT.INVOICE_TYPE LIKE '"+invoice_type+"') AS TOTAL_INVOICE, "+
					 "SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT, "+
					 "(SELECT SUM(TOTAL_AMOUNT) FROM RETURN_PRODUCT "+
					 "WHERE RETURN_PRODUCT.CUST_ID LIKE '"+cust_id+"' "+
					 "AND RETURN_PRODUCT.CN_DATE BETWEEN '"+start_date+"' AND '"+end_date+"') AS TOTAL_CR_AMOUNT "+
					 "FROM SELL_PRODUCT "+
					 "WHERE SELL_PRODUCT.CUST_ID LIKE '"+cust_id+"' "+
					 "AND SELL_PRODUCT.EMP_ID LIKE '"+emp_id+"' "+
					 "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' "+
					 "AND SELL_PRODUCT.INVOICE_TYPE LIKE '"+invoice_type+"' ";
		System.out.println(sql);
		try {
			return conn.getJsonData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	public JSONObject getLastSellPrice(String partNo, String custId){
		
		String sql = "SELECT TOP(1) CAST(TOTAL_NET_AMOUNT/QUANTITY AS NUMERIC(15,2)) as AMOUNT, "+
					 "CAST((TOTAL_DISCOUNT_AMOUNT+TOTAL_EXTRA_DISCOUNT_AMOUNT)/QUANTITY AS NUMERIC(15,2)) AS DISCOUNT, "+
					 "TEMP_INVOICE_DATE FROM SELL_PRODUCT "+ 
					 "WHERE PART_NO = '"+partNo+"' AND CUST_ID = '"+custId+"' "+
					 "ORDER BY TEMP_INVOICE_DATE+CREATE_TIME DESC";
		 try {
			 System.out.println(sql);
			 return conn.getJsonData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getHeader(String tempInvoiceNo){
		String sql = "";
    	sql = "SELECT DISTINCT SELL_PRODUCT.TEMP_INVOICE_NO, SELL_PRODUCT.TEMP_INVOICE_DATE, "+
      		  "SELL_PRODUCT.INVOICE_NO, SELL_PRODUCT.INVOICE_DATE, SELL_PRODUCT.CUST_ID, "+
      		  "CUSTOMER.CUST_NAME, CUSTOMER.ADDRESS1+' '+CUSTOMER.ADDRESS2 AS ADDRESS1, "+
      		  "CUSTOMER.TELEPHONE, CUSTOMER.EMP_ID, CUSTOMER.REMARK, SELL_PRODUCT.PAYMENT_TERM, "+
      		  "SELL_PRODUCT.PAYMENT_DATE, SELL_PRODUCT.INVOICE_TYPE, "+
      		  "SELL_PRODUCT.TOTAL_BILL_AMOUNT, SELL_PRODUCT.TAX_RATE, SELL_PRODUCT.TOTAL_TAX_AMOUNT, "+
      		  "SELL_PRODUCT.TOTAL_AMOUNT, SELL_PRODUCT.TOTAL_NET_AMOUNT "+
      		  "FROM SELL_PRODUCT LEFT OUTER JOIN CUSTOMER "+
      		  "ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID "+
      		  "WHERE SELL_PRODUCT.TEMP_INVOICE_NO = '"+tempInvoiceNo+"'";
		 try {
			 return conn.getJsonData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getHeaderPage2(String tempInvoiceNo){
		String sql = "";
    	sql = "SELECT SELL_PRODUCT.TEMP_INVOICE_NO, SELL_PRODUCT.TEMP_INVOICE_DATE, "+
      		  "SELL_PRODUCT.INVOICE_NO, SELL_PRODUCT.INVOICE_DATE, SELL_PRODUCT.CUST_ID, "+
      		  "CUSTOMER.CUST_NAME, CUSTOMER.ADDRESS1+' '+CUSTOMER.ADDRESS2 AS ADDRESS1, "+
      		  "CUSTOMER.TELEPHONE, CUSTOMER.EMP_ID, CUSTOMER.REMARK, SELL_PRODUCT.PAYMENT_TERM, "+
      		  "SELL_PRODUCT.PAYMENT_DATE, SELL_PRODUCT.INVOICE_TYPE, "+
      		  "SELL_PRODUCT.TOTAL_BILL_AMOUNT, SELL_PRODUCT.TAX_RATE, SELL_PRODUCT.TOTAL_TAX_AMOUNT, "+
      		  "SUM(SELL_PRODUCT.TOTAL_AMOUNT) AS TOTAL_AMOUNT, "+
      		  "SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT "+
      		  "FROM SELL_PRODUCT LEFT OUTER JOIN CUSTOMER "+
      		  "ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID "+
      		  "WHERE SELL_PRODUCT.TEMP_INVOICE_NO = '"+tempInvoiceNo+"' "+
      		  "GROUP BY SELL_PRODUCT.TEMP_INVOICE_NO, SELL_PRODUCT.TEMP_INVOICE_DATE, "+
    		  "SELL_PRODUCT.INVOICE_NO, SELL_PRODUCT.INVOICE_DATE, SELL_PRODUCT.CUST_ID, "+
    		  "CUSTOMER.CUST_NAME, CUSTOMER.ADDRESS1+' '+CUSTOMER.ADDRESS2, "+
    		  "CUSTOMER.TELEPHONE, CUSTOMER.EMP_ID, CUSTOMER.REMARK, SELL_PRODUCT.PAYMENT_TERM, "+
    		  "SELL_PRODUCT.PAYMENT_DATE, SELL_PRODUCT.INVOICE_TYPE, "+
    		  "SELL_PRODUCT.TOTAL_BILL_AMOUNT, SELL_PRODUCT.TAX_RATE, SELL_PRODUCT.TOTAL_TAX_AMOUNT";
		 try {
			 //System.out.println(sql);
			 return conn.getJsonData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getSellProductFillForm(String tempInvoiceNo, String itemNo){
		String sql = "";
    	sql = "SELECT B.TEMP_INVOICE_NO, B.TEMP_INVOICE_DATE, B.INVOICE_NO, B.INVOICE_DATE, "+
      		  "B.CUST_ID, S.CUST_NAME, S.ADDRESS1, S.TELEPHONE, S.REMARK, B.EMP_ID, "+
      		  "B.LAST_SELL_DATE, B.LAST_SELL_PRICE, B.INVOICE_TYPE, P.PRD_CATEGORY_ID, "+
      		  "B.CREATE_DATE, B.CREATE_TIME, B.PAYMENT_TERM, B.PAYMENT_DATE, B.ITEM_NO, B.PART_NO, "+
      		  "P.PRD_NAME, B.AMOUNT, B.QUANTITY, U.UNIT_NAME, UT.UNIT_TYPE_NAME, B.TOTAL_AMOUNT, B.DISCOUNT_RATE, "+
      		  "B.DISCOUNT_AMOUNT, B.EXTRA_DISCOUNT_RATE, B.EXTRA_DISCOUNT_AMOUNT, "+
      		  "B.TAX_RATE, B.TAX_AMOUNT, B.TOTAL_NET_AMOUNT, B.NOTE, B.OLD_COST, P.REAL_SALE_PRICE, "+
      		  "P.PRD_NAME+' : '+VM.VEHICLE_MODEL_NAME+' : '+PB.PRD_BRAND_NAME AS PART_DETAIL "+
      		  "FROM SELL_PRODUCT B "+
      		  "LEFT OUTER JOIN CUSTOMER S ON B.CUST_ID = S.CUST_ID "+
      		  "LEFT OUTER JOIN PRODUCT P ON B.PART_NO = P.PART_NO "+
      		  "LEFT OUTER JOIN VEHICLE_MODEL VM ON P.VEHICLE_MODEL_ID = VM.VEHICLE_MODEL_ID "+
      		  "LEFT OUTER JOIN PRODUCT_BRAND PB ON P.PRD_BRAND_ID = PB.PRD_BRAND_ID "+
      		  "LEFT OUTER JOIN UNIT U ON P.UNIT_ID = U.UNIT_ID "+
      		  "LEFT OUTER JOIN UNIT_TYPE UT ON P.UNIT_TYPE_ID = UT.UNIT_TYPE_ID "+
      		  "WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"' "+
      		  "AND ITEM_NO = '"+itemNo+"'";
		 try {
			 return conn.getJsonData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getSellProductTable(String date_from, String date_to, String invdate_from, String invdate_to, String method, String invoice_type){
		JSONObject js = new JSONObject();
		date_from = date_from.equals("")? method.equals("loaddatatable") ? JDate.getDate() : "00000000" : JDate.saveDate(date_from);		
		date_to = date_to.equals("")? method.equals("loaddatatable") ? JDate.getDate() : "99999999" : JDate.saveDate(date_to);
		invdate_from = invdate_from.equals("")? method.equals("loaddatatable") ? "00000000" : "00000000" : JDate.saveDate(invdate_from);		
		invdate_to = invdate_to.equals("")? method.equals("loaddatatable") ? "99999999" : "99999999" : JDate.saveDate(invdate_to);

		String sql =  "SELECT *, ROW_NUMBER() OVER (ORDER BY A.TEMP_INVOICE_NO ASC) AS ROWS "+
					  "FROM (SELECT DISTINCT "+
					  "SELL_PRODUCT.TEMP_INVOICE_NO, SELL_PRODUCT.INVOICE_NO, SELL_PRODUCT.INVOICE_DATE, "+
					  "SELL_PRODUCT.CUST_ID, CUSTOMER.CUST_NAME, SELL_PRODUCT.TOTAL_BILL_AMOUNT, AREA.ROUTE_NAME, SELL_PRODUCT.PRINTED_DATE "+
					  "FROM SELL_PRODUCT LEFT OUTER JOIN CUSTOMER ON SELL_PRODUCT.CUST_ID=CUSTOMER.CUST_ID "+
					  "LEFT OUTER JOIN AREA ON CUSTOMER.AREA_ID = AREA.AREA_ID "+
					  "WHERE SELL_PRODUCT.CUST_ID = SELL_PRODUCT.CUST_ID AND SELL_PRODUCT.PART_NO = SELL_PRODUCT.PART_NO AND "+
					  "SELL_PRODUCT.TEMP_INVOICE_DATE BETWEEN '"+date_from+"' AND '"+date_to+"' AND "+
					  "SELL_PRODUCT.INVOICE_DATE BETWEEN '"+invdate_from+"' AND '"+invdate_to+"' AND "+
					  "SELL_PRODUCT.INVOICE_TYPE LIKE '"+invoice_type+"' "+
					  ") AS A "+
					  "ORDER BY ROWS DESC";
		try {
			System.out.println(sql);
			js = conn.getJsonArrayData(sql);
			conn.doDisconnect();
			return js;
		} catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getTempInvoiceNo(){
		JSONObject tempInvoiceNo = new JSONObject();
		try{
			tempInvoiceNo.put("TEMP_INVOICE_NO", ""+JDate.getDate()+JDate.getTime());
			tempInvoiceNo.put("TEMP_INVOICE_DATE", JDate.getDate());
		}catch(Exception e){
			System.out.println(e);
		}
		return tempInvoiceNo;
	}
	public JSONObject getItemNo(String tempInvoiceNo){
		JSONObject itemNo = null;
		String sql = "SELECT MAX(ITEM_NO)+1 AS ITEM_NO "+
					 "FROM SELL_PRODUCT WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"'";
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
	
	public String getSumSellBillingSql(String cust_id, String emp_id, String start_date, String end_date, String invoice_type){
		cust_id = cust_id.equals("") ? "%" : cust_id;
		emp_id = emp_id.equals("0")||emp_id.equals("") ? "%" : emp_id;
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		String sql = "SELECT CUSTOMER.CUST_NAME, SELL_PRODUCT.TEMP_INVOICE_NO, SELL_PRODUCT.INVOICE_NO, SELL_PRODUCT.INVOICE_DATE, "+
				 "COUNT(*) AS ITEM_AMOUNT, SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT "+
				 "FROM SELL_PRODUCT LEFT OUTER JOIN CUSTOMER ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID "+
				 "WHERE SELL_PRODUCT.CUST_ID LIKE '"+cust_id+"' "+
				 "AND SELL_PRODUCT.EMP_ID LIKE '"+emp_id+"' "+
				 "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' "+
				 "AND SELL_PRODUCT.INVOICE_TYPE LIKE '"+invoice_type+"' "+
				 "GROUP BY CUSTOMER.CUST_NAME, SELL_PRODUCT.TEMP_INVOICE_NO, SELL_PRODUCT.INVOICE_NO, SELL_PRODUCT.INVOICE_DATE "+
				 "ORDER BY SELL_PRODUCT.INVOICE_DATE, SELL_PRODUCT.INVOICE_NO";
		return sql;
	}
	public String getSumBenefitStm(String start_date, String end_date, String emp_id, String cust_id, String part_no) {
		// where condition
		start_date = start_date.equals("") ? "00000000" : JDate.saveDate(start_date);
		end_date = end_date.equals("") ? "99999999" : JDate.saveDate(end_date);
		emp_id = emp_id.equals("") ? "%" : emp_id;
		cust_id = cust_id.equals("") ? "%" : cust_id;
		part_no = part_no.equals("") ? "%" : part_no;
		
		String sql =  "SELECT SUM(SELL_PRODUCT.OLD_COST*SELL_PRODUCT.QUANTITY) AS COST_AMOUNT, "+
					  "SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT) AS SELL_AMOUNT, "+
					  "SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT-(SELL_PRODUCT.OLD_COST*SELL_PRODUCT.QUANTITY)) AS BENEFIT_AMOUNT "+
					  "FROM SELL_PRODUCT "+
					  "LEFT OUTER JOIN PRODUCT ON SELL_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "LEFT OUTER JOIN CUSTOMER ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID " +
					  "LEFT OUTER JOIN USERS ON CUSTOMER.EMP_ID = USERS.USER_ID "+
					  "WHERE USERS.USER_ID LIKE '" + emp_id + "' "+
					  "AND SELL_PRODUCT.CUST_ID LIKE '" + cust_id + "' "+
					  "AND SELL_PRODUCT.PART_NO LIKE '" + part_no + "' "+
					  "AND (SELL_PRODUCT.CUST_ID = '01329' OR SELL_PRODUCT.AMOUNT > 0) "+
					  "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' ";		
		System.out.println(sql);
		return sql;
	}
	public JSONObject getSumBenefit(String start_date, String end_date, String sup_name, String cust_name, String part_no) {
		 try {
			 System.out.println(conn.getJsonData(getSumBenefitStm(start_date, end_date, sup_name, cust_name, part_no)));
			 return conn.getJsonData(getSumBenefitStm(start_date, end_date, sup_name, cust_name, part_no));
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}

	public String getBenefitStm(String start_date, String end_date, String emp_id, String cust_id, String part_no) {
		
		// where condition
		start_date = start_date.equals("") ? "00000000" : JDate.saveDate(start_date);
		end_date = end_date.equals("") ? "99999999" : JDate.saveDate(end_date);
		emp_id = emp_id.equals("") ? "%" : emp_id;
		cust_id = cust_id.equals("") ? "%" : cust_id;
		part_no = part_no.equals("") ? "%" : part_no;
		
		String sql =  "SELECT SELL_PRODUCT.INVOICE_DATE, SELL_PRODUCT.PART_NO, SELL_PRODUCT.OLD_COST*SELL_PRODUCT.QUANTITY, "+
					  "SELL_PRODUCT.TOTAL_NET_AMOUNT, SELL_PRODUCT.TOTAL_NET_AMOUNT-(SELL_PRODUCT.OLD_COST*SELL_PRODUCT.QUANTITY), "+
					  "USERS.NAME, CUSTOMER.CUST_NAME " +
					  "FROM SELL_PRODUCT "+
					  "LEFT OUTER JOIN PRODUCT ON SELL_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "LEFT OUTER JOIN CUSTOMER ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID " +
					  "LEFT OUTER JOIN USERS ON CUSTOMER.EMP_ID = USERS.USER_ID "+
					  "WHERE USERS.USER_ID LIKE '" + emp_id + "' "+
					  "AND SELL_PRODUCT.CUST_ID LIKE '" + cust_id + "' "+
					  "AND SELL_PRODUCT.PART_NO LIKE '" + part_no + "' "+
					  "AND (SELL_PRODUCT.CUST_ID = '01329' OR SELL_PRODUCT.AMOUNT > 0) "+
					  "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' ";		
		System.out.println("This"+sql);
		return sql;
	}
	public JSONObject getSummaryRecord(String cust_id, String part_no, String prd_type_id, String prd_brand_id, String start_date, String end_date, String temp_start_date, String temp_end_date) {
		cust_id = cust_id.equals("") ? "%" : cust_id;
		part_no = part_no.equals("") ? "%" : part_no;
		prd_type_id = prd_type_id.equals("") ? "%" : prd_type_id;
		prd_brand_id = prd_brand_id.equals("") ? "%" : prd_brand_id;
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		temp_start_date = temp_start_date.equals("") ? "00000000" : temp_start_date;
		temp_end_date = temp_end_date.equals("") ? "99999999" : temp_end_date;
		String sql =  "SELECT SUM(SELL_PRODUCT.QUANTITY) AS QUANTITY, SUM(SELL_PRODUCT.QUANTITY*SELL_PRODUCT.AMOUNT) AS TOTAL_AMOUNT, "+
					  "SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT "+
					  "FROM SELL_PRODUCT LEFT OUTER JOIN PRODUCT ON SELL_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "WHERE SELL_PRODUCT.CUST_ID LIKE '"+cust_id+"' "+
					  "AND SELL_PRODUCT.PART_NO LIKE '"+part_no+"' "+
					  "AND PRODUCT.PRD_TYPE_ID LIKE '"+prd_type_id+"' "+
					  "AND PRODUCT.PRD_BRAND_ID LIKE '"+prd_brand_id+"' "+
					  "AND SELL_PRODUCT.TEMP_INVOICE_DATE BETWEEN '"+temp_start_date+"' AND '"+temp_end_date+"' "+
					  "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"'";
		System.out.println("Get Sum : "+sql);
		try {
			return conn.getJsonData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	
	public String getSumGroupBenefitStm(String start_date, String end_date, String emp_id, String cust_id, String part_no) {
		
		// where condition
		start_date = start_date.equals("") ? "00000000" : JDate.saveDate(start_date);
		end_date = end_date.equals("") ? "99999999" : JDate.saveDate(end_date);
		emp_id = emp_id.equals("") ? "%" : emp_id;
		cust_id = cust_id.equals("") ? "%" : cust_id;
		part_no = part_no.equals("") ? "%" : part_no;
		
		String sql =  "SELECT SELL_PRODUCT.BUSINESS_ID,USERS.LOGIN_NAME, SUM(SELL_PRODUCT.OLD_COST*SELL_PRODUCT.QUANTITY), "+
					  "SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT), SUM(SELL_PRODUCT.TOTAL_NET_AMOUNT-(SELL_PRODUCT.OLD_COST*SELL_PRODUCT.QUANTITY)), "+
					  "USERS.NAME, CUSTOMER.CUST_NAME " +
					  "FROM SELL_PRODUCT "+
					  "LEFT OUTER JOIN PRODUCT ON SELL_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "LEFT OUTER JOIN CUSTOMER ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID " +
					  "LEFT OUTER JOIN USERS ON CUSTOMER.EMP_ID = USERS.USER_ID "+
					  "WHERE USERS.USER_ID LIKE '" + emp_id + "' "+
					  "AND SELL_PRODUCT.CUST_ID LIKE '" + cust_id + "' "+
					  "AND SELL_PRODUCT.PART_NO LIKE '" + part_no + "' "+
					  "AND (SELL_PRODUCT.CUST_ID = '01329' OR SELL_PRODUCT.AMOUNT > 0) "+
					  "AND SELL_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' "+
					  "GROUP BY SELL_PRODUCT.BUSINESS_ID,USERS.LOGIN_NAME, USERS.NAME, CUSTOMER.CUST_NAME";		
		System.out.println("This"+sql);
		return sql;
	}
	
	public JSONObject getBenefit(String start_date, String end_date, String sup_name, String cust_name, String part_no) {
		 try {
			 return conn.getJsonArrayData(getBenefitStm(start_date, end_date, sup_name, cust_name, part_no));
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getSumGroupBenefit(String start_date, String end_date, String sup_name, String cust_name, String part_no) {
		 try {
			 return conn.getJsonArrayData(getSumGroupBenefitStm(start_date, end_date, sup_name, cust_name, part_no));
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}


	public boolean updateTotalBill(String tempInvoiceNo){
		JSONObject itemNo = null;
		String sql = "SELECT TEMP_INVOICE_NO, SUM(TOTAL_NET_AMOUNT) AS TOTAL_BILL_AMOUNT, SUM(TAX_AMOUNT) AS TOTAL_TAX_AMOUNT "+
					 "FROM SELL_PRODUCT "+
					 "WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"' "+
					 "GROUP BY TEMP_INVOICE_NO";
		itemNo = conn.getJsonData(sql);
		try{
			String totalBillAmount = itemNo.get("TOTAL_BILL_AMOUNT").toString();
			String totalTaxAmount = itemNo.get("TOTAL_TAX_AMOUNT").toString();
			String thaiBaht = JNumber.toThaiMoney(totalBillAmount);
			String s = "UPDATE SELL_PRODUCT SET TOTAL_BILL_AMOUNT = '"+totalBillAmount+"', "+
					   "TOTAL_TAX_AMOUNT = '"+totalTaxAmount+"', "+
					   "THAI_BAHT = '"+thaiBaht+"' "+
					   "WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"'";
			conn.doConnect();
			conn.doSave(s);
			conn.doCommit();
			return true;
		}catch (Exception e){
			conn.doRollback();
			return false;
		}
	}
	public boolean updateHeader(HashMap<String,String> a){
		boolean status = false;
		System.out.println("UPDATE hEADER");
		String sql = "UPDATE SELL_PRODUCT SET "+
				 	 "TEMP_INVOICE_DATE = '"+a.get("TEMP_INVOICE_DATE")+"', "+
					 "INVOICE_NO = '"+a.get("INVOICE_NO")+"', "+
					 "INVOICE_DATE = '"+a.get("INVOICE_DATE")+"', "+
					 "INVOICE_TYPE = '"+a.get("INVOICE_TYPE")+"', "+
					 "CUST_ID = '"+a.get("CUST_ID")+"', "+
					 "EMP_ID = '"+a.get("EMP_ID")+"', "+
					 "PAYMENT_TERM = '"+a.get("PAYMENT_TERM")+"', "+
					 "PAYMENT_DATE = '"+JDate.saveDate(a.get("PAYMENT_DATE"))+"', "+
					 "TAX_RATE = '"+a.get("TAX_RATE")+"' "+
					 "WHERE TEMP_INVOICE_NO = '"+a.get("TEMP_INVOICE_NO")+"'";
		System.out.println(sql);
		conn.doConnect();
		if(conn.doSave(sql)){
			conn.doSave(updateDetailForHeader(a.get("TEMP_INVOICE_NO")));
			status = conn.doCommit();
			conn.doDisconnect();
			status = updateTotalBill(a.get("TEMP_INVOICE_NO"));
		}else{
			status = false;
		}
		return status;
	}
	private String updateDetailForHeader(String tempInvoiceNo){
		String sql =
		"UPDATE SELL_PRODUCT "+
		"SET TAX_AMOUNT = CONVERT(numeric(15,2),(((AMOUNT*QUANTITY)-(TOTAL_DISCOUNT_AMOUNT+TOTAL_EXTRA_DISCOUNT_AMOUNT))*TAX_RATE)/100), "+
		"TOTAL_NET_AMOUNT = CONVERT(numeric(15,2),((AMOUNT*QUANTITY)-(TOTAL_DISCOUNT_AMOUNT+TOTAL_EXTRA_DISCOUNT_AMOUNT)) + "+
		"(((AMOUNT*QUANTITY)-(TOTAL_DISCOUNT_AMOUNT+TOTAL_EXTRA_DISCOUNT_AMOUNT))*TAX_RATE)/100) "+
		"WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"'";
		return sql;
	}
	public boolean deleteInvoice(String tempInvoiceNo){
		String sql = "";
		boolean status = false;
    	sql = "DELETE FROM SELL_PRODUCT "+
      		  "WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"'";
		conn.doConnect();
		if(conn.doSave(sql)){
			status = conn.doCommit();
		}else{
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
	public boolean insertData(HashMap<String,String> a){
		boolean status = true;
		String sql = "INSERT INTO SELL_PRODUCT "+
					 "(TEMP_INVOICE_NO, TEMP_INVOICE_DATE, INVOICE_NO, ITEM_NO, INVOICE_DATE, INVOICE_TYPE, CUST_ID, "+
					 "PART_NO, EMP_ID, AMOUNT, LAST_SELL_DATE, LAST_SELL_PRICE, OLD_COST, QUANTITY, "+
					 "PAYMENT_TERM, PAYMENT_DATE, TOTAL_AMOUNT, DISCOUNT_RATE, DISCOUNT_AMOUNT, TOTAL_DISCOUNT_AMOUNT, "+
					 "EXTRA_DISCOUNT_RATE, EXTRA_DISCOUNT_AMOUNT, TOTAL_EXTRA_DISCOUNT_AMOUNT, TAX_RATE, TAX_AMOUNT, "+
					 "TOTAL_NET_AMOUNT, NOTE, ACTIVE, CREATE_DATE, CREATE_TIME, CREATE_USER_ID) "+
					 "VALUES "+					 "('"+a.get("TEMP_INVOICE_NO")+"','"+a.get("TEMP_INVOICE_DATE")+"','"+a.get("INVOICE_NO")+
					 "','"+a.get("ITEM_NO")+"','"+a.get("INVOICE_DATE")+"','"+a.get("INVOICE_TYPE")+"','"+a.get("CUST_ID")+"','"+a.get("PART_NO")+
					 "','"+a.get("EMP_ID")+"','"+a.get("AMOUNT")+"','"+a.get("LAST_SELL_DATE")+"','"+a.get("LAST_SELL_PRICE")+
					 "','"+a.get("OLD_COST")+"','"+a.get("QUANTITY")+"','"+a.get("PAYMENT_TERM")+"','"+a.get("PAYMENT_DATE")+
					 "','"+a.get("TOTAL_AMOUNT")+"','"+a.get("DISCOUNT_RATE")+"','"+a.get("DISCOUNT_AMOUNT")+"','"+a.get("TOTAL_DISCOUNT_AMOUNT")+
					 "','"+a.get("EXTRA_DISCOUNT_RATE")+"','"+a.get("EXTRA_DISCOUNT_AMOUNT")+"','"+a.get("TOTAL_EXTRA_DISCOUNT_AMOUNT")+"','"+a.get("TAX_RATE")+
					 "','"+a.get("TAX_AMOUNT")+"','"+a.get("TOTAL_NET_AMOUNT")+"','"+a.get("NOTE")+"','"+a.get("ACTIVE")+
					 "','"+a.get("CREATE_DATE")+"','"+a.get("CREATE_TIME")+"','"+a.get("CREATE_USER_ID")+"')";
		conn.doConnect();
		try{
			conn.doSave(sql);
			conn.doCommit();
			this.updateTotalBill(a.get("TEMP_INVOICE_NO"));
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
		String sql = "UPDATE SELL_PRODUCT SET "+
					 "TEMP_INVOICE_NO = '"+a.get("TEMP_INVOICE_NO")+"', "+
					 "TEMP_INVOICE_DATE = '"+a.get("TEMP_INVOICE_DATE")+"', "+
					 "INVOICE_NO = '"+a.get("INVOICE_NO")+"', "+
					 "ITEM_NO = '"+a.get("ITEM_NO")+"', "+
					 "INVOICE_DATE = '"+a.get("INVOICE_DATE")+"', "+
					 "INVOICE_TYPE = '"+a.get("INVOICE_TYPE")+"', "+
					 "CUST_ID = '"+a.get("CUST_ID")+"', "+
					 "PART_NO = '"+a.get("PART_NO")+"', "+
					 "AMOUNT = '"+a.get("AMOUNT")+"', "+
					 "OLD_COST = '"+a.get("OLD_COST")+"', "+
					 "QUANTITY = '"+a.get("QUANTITY")+"', "+
					 "LAST_SELL_DATE = '"+a.get("LAST_SELL_DATE")+"', "+
					 "LAST_SELL_PRICE = '"+a.get("LAST_SELL_PRICE")+"', "+
					 "EMP_ID = '"+a.get("EMP_ID")+"', "+
					 "PAYMENT_TERM = '"+a.get("PAYMENT_TERM")+"', "+
					 "PAYMENT_DATE = '"+a.get("PAYMENT_DATE")+"', "+
					 "TOTAL_AMOUNT = '"+a.get("TOTAL_AMOUNT")+"', "+
					 "DISCOUNT_RATE = '"+a.get("DISCOUNT_RATE")+"', "+
					 "DISCOUNT_AMOUNT = '"+a.get("DISCOUNT_AMOUNT")+"', "+
					 "TOTAL_DISCOUNT_AMOUNT = '"+a.get("TOTAL_DISCOUNT_AMOUNT")+"', "+
					 "EXTRA_DISCOUNT_RATE = '"+a.get("EXTRA_DISCOUNT_RATE")+"', "+
					 "EXTRA_DISCOUNT_AMOUNT = '"+a.get("EXTRA_DISCOUNT_AMOUNT")+"', "+
					 "TOTAL_EXTRA_DISCOUNT_AMOUNT = '"+a.get("TOTAL_EXTRA_DISCOUNT_AMOUNT")+"', "+
					 "TAX_RATE = '"+a.get("TAX_RATE")+"', "+
					 "TAX_AMOUNT = '"+a.get("TAX_AMOUNT")+"', "+
					 "TOTAL_NET_AMOUNT = '"+a.get("TOTAL_NET_AMOUNT")+"', "+
					 "NOTE = '"+a.get("NOTE")+"', "+
					 "ACTIVE = '"+a.get("ACTIVE")+"', "+
					 "UPDATE_DATE = '"+a.get("UPDATE_DATE")+"', "+
					 "UPDATE_TIME = '"+a.get("UPDATE_TIME")+"', "+
					 "UPDATE_USER_ID = '"+a.get("UPDATE_USER_ID")+"' "+
					 "WHERE TEMP_INVOICE_NO = '"+a.get("TEMP_INVOICE_NO")+"' AND "+
					 "ITEM_NO = '"+a.get("ITEM_NO")+"'";
		conn.doConnect();
		try{
			conn.doSave(sql);
			conn.doCommit();
			this.updateTotalBill(a.get("TEMP_INVOICE_NO"));
			status = true;
		}catch (Exception e){
			System.out.println(e);
			conn.doRollback();
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
	
	public JSONObject getSellProductByPartNoAndInvoiceNo(String partNo, String tempInvoiceNo){
		JSONObject js = new JSONObject();
		String sql =  "SELECT * FROM SELL_PRODUCT WHERE TEMP_INVOICE_NO = '" + tempInvoiceNo + "' AND PART_NO = '" + partNo + "' ";
		try {
			js = conn.getJsonData(sql);
			conn.doDisconnect();
			return js;
		} catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	
	
	public boolean deleteData(String tempInvoiceNo, String itemNo){
		String sql = "";
		boolean status = false;
    	sql = "DELETE FROM SELL_PRODUCT "+
      		  "WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"' AND "+
      		  "ITEM_NO = '"+itemNo+"'";
    	
		conn.doConnect();
		try{
			conn.doSave(sql);
			conn.doCommit();
			this.updateTotalBill(tempInvoiceNo);
			status = true;
		}catch (Exception e){
			conn.doRollback();
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
}