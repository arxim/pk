package com.beans.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.beans.utils.DBConn;
import com.beans.utils.JDate;
import com.beans.utils.JNumber;

public class BuyProductDAO {
	DBConn conn = null;
	String userId = "";
	String hospital = "";
	String month = "";
	String year = "";
	String startDate = "";
	String endDate = "";

	public BuyProductDAO(){
		conn = new DBConn();
	}
	
	public JSONObject getItemNo(String businessId, String tempInvoiceNo){
		String sql = "SELECT MAX(ITEM_NO)+1 AS ITEM_NO FROM BUY_PRODUCT WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"'";
		try{
			return conn.getJsonData(sql);
		}catch(Exception e){
			System.out.println(e);
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
	public JSONObject getBuyProductTable(String businessId){
		String sql =  "SELECT BUY_PRODUCT.TEMP_INVOICE_NO, BUY_PRODUCT.INVOICE_NO, BUY_PRODUCT.INVOICE_DATE, BUY_PRODUCT.SUP_ID, "+
					  "SUPPLIER.SUP_NAME, BUY_PRODUCT.TOTAL_BILL_AMOUNT, BUY_PRODUCT.CREATE_DATE+BUY_PRODUCT.CREATE_TIME "+
					  "FROM BUY_PRODUCT LEFT OUTER JOIN SUPPLIER ON BUY_PRODUCT.SUP_ID=SUPPLIER.SUP_ID "+
					  "WHERE ITEM_NO = '1' "+
					  "ORDER BY BUY_PRODUCT.TEMP_INVOICE_NO DESC";
		 try {
			 return conn.getJsonArrayData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getCurrentDateBuyProduct(String date_from, String date_to, String invdate_from, String invdate_to, String method){
		System.out.println(date_from+"<>"+date_to+"<>"+invdate_from+"<>"+invdate_to);
		date_from = "datatable".equals(method) && date_from != null && !date_from.isEmpty() ? JDate.saveDate(date_from) : "00000000";
		date_to = "datatable".equals(method) && date_to != null && !date_to.isEmpty() ? JDate.saveDate(date_to) : "99999999";
		invdate_from = "datatable".equals(method) && invdate_from != null && !invdate_from.isEmpty() ? JDate.saveDate(invdate_from) : "00000000";
		invdate_to = "datatable".equals(method) && invdate_to != null && !invdate_to.isEmpty() ? JDate.saveDate(invdate_to) : "99999999";
		
		String sql =  "SELECT TEM.* FROM ("+
					  "SELECT BUY_PRODUCT.TEMP_INVOICE_NO, BUY_PRODUCT.INVOICE_NO, BUY_PRODUCT.INVOICE_DATE, BUY_PRODUCT.SUP_ID, "+
					  "SUPPLIER.SUP_NAME, BUY_PRODUCT.TOTAL_BILL_AMOUNT, BUY_PRODUCT.CREATE_DATE+BUY_PRODUCT.CREATE_TIME AS DT, "+
					  "row_number() over (partition by TEMP_INVOICE_NO order by ITEM_NO) as SEQ, BUY_PRODUCT.TEMP_INVOICE_DATE "+
					  "FROM BUY_PRODUCT LEFT OUTER JOIN SUPPLIER ON BUY_PRODUCT.SUP_ID=SUPPLIER.SUP_ID) AS TEM "+
					  "WHERE TEM.SEQ = '1' ";
		
		if ("datatable".equals(method)) {
			sql += "AND TEM.TEMP_INVOICE_DATE BETWEEN '"+ date_from + "' AND '" + date_to + "' ";
			sql += "AND TEM.INVOICE_DATE BETWEEN '"+invdate_from+"' AND '" +invdate_to+ "' ";
		}else {
			sql += "AND TEM.TEMP_INVOICE_DATE = '" + JDate.getDate() + "' ";
		}
		
		sql += "ORDER BY TEM.TEMP_INVOICE_NO DESC";
		System.out.println(sql);
		 try {
			 return conn.getJsonArrayData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	/*
	public void setCostPrice(String partNo){
		String sql = "UPDATE PRODUCT "+
					 "SET PRODUCT.COST_PRICE = B.TOTAL "+
					 "FROM "+
					 "(SELECT PART_NO, SUM(TOTAL_NET_AMOUNT)/SUM(QUANTITY) AS TOTAL FROM ( "+
					 "SELECT ROW_NUMBER() OVER (PARTITION BY PART_NO "+
					 "ORDER BY TEMP_INVOICE_DATE DESC) AS A, * "+
					 "FROM BUY_PRODUCT ) A "+
					 "WHERE A < 4 "+
					 "GROUP BY PART_NO) B "+
					 "WHERE PRODUCT.PART_NO = B.PART_NO "+
					 "AND B.PART_NO = '"+partNo+"'";
		System.out.println(sql);
		conn.doConnect();
		if(conn.doSave(sql)){
			conn.doCommit();
		}
		conn.doDisconnect();
	}
	*/
	public void setNewCostPrice(String partNo){
		ArrayList<HashMap<String, String>> productList = this.getListBillByPartNo(partNo);
		double cost = 0;
		int stock = Integer.parseInt(this.getProductOnhand(partNo));
		int dataSize = productList.size()>3 ? 3 : productList.size();
		int count = 0;
		int divCost = 0;
		while(dataSize > 0){
			System.out.println("Data Size : "+dataSize+" Stock : "+stock);
			if(stock <= Integer.parseInt(productList.get(count).get("QUANTITY"))){
				System.out.println("in stock < "+stock+" : "+Integer.parseInt(productList.get(count).get("QUANTITY")));
				cost = cost + Double.parseDouble(productList.get(count).get("COST")) * stock;
				divCost = divCost + stock;
				stock = 0;
				dataSize = 0;					
			}else{
				cost = cost + Double.parseDouble(productList.get(count).get("COST")) * Integer.parseInt(productList.get(count).get("QUANTITY"));
				stock = stock - Integer.parseInt(productList.get(count).get("QUANTITY"));
				divCost = divCost + Integer.parseInt(productList.get(count).get("QUANTITY"));
				count++;
				dataSize--;
			}
		}			
		cost = cost/divCost;
		System.out.println(stock +" : "+cost);
		conn.doConnect();
		if(conn.doSave("UPDATE PRODUCT SET COST_PRICE = '"+cost+"' WHERE PART_NO = '"+partNo+"'")){
			conn.doCommit();
		}
		conn.doDisconnect();
	}
	private String getProductOnhand(String partNo){
		String sql =  "SELECT PART_NO, "+					  
				  "((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) AS "+
				  "PRD_REMAIN "+
				  "FROM PRODUCT "+
				  "WHERE PART_NO = '"+partNo+"'";
		//System.out.println(sql);
		try {
			return conn.getData(sql).get(0).get("PRD_REMAIN");
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return "0";
		}
	}
	private ArrayList<HashMap<String, String>> getListBillByPartNo(String partNo){
		ArrayList<HashMap<String, String>> a = null;
		String sql = "SELECT PART_NO, QUANTITY, SUM(TOTAL_NET_AMOUNT)/SUM(QUANTITY) AS COST FROM BUY_PRODUCT "+
					 "WHERE PART_NO = '"+partNo+"' AND (SUP_ID != '00163' OR AMOUNT > 0) "+
					 "GROUP BY PART_NO, QUANTITY, TEMP_INVOICE_NO, TEMP_INVOICE_DATE "+
					 "ORDER BY TEMP_INVOICE_DATE DESC";
		System.out.println(sql);
		try{
			a = conn.getData(sql);
		}catch(Exception e){
			System.out.println(e);
			a = null;
		}
		return a;
	}

	public JSONObject getLastBuyPrice(String partNo, String supId){
		JSONObject a = new JSONObject();
		String sql = "SELECT TOP(1) CAST((TOTAL_NET_AMOUNT/QUANTITY) AS NUMERIC(15,2)) AS AMOUNT, "+
					 //"CAST(AMOUNT-(TOTAL_NET_AMOUNT/QUANTITY) AS NUMERIC(15,2)) AS DISCOUNT "+// Discount Include Tax
					 "CAST(AMOUNT-(TOTAL_DISCOUNT_AMOUNT/QUANTITY) AS NUMERIC(15,2)) AS DISCOUNT "+
				     "FROM BUY_PRODUCT "+ 
					 "WHERE PART_NO = '"+partNo+"' AND SUP_ID = '"+supId+"' "+
					 "ORDER BY INVOICE_DATE DESC";
		System.out.println(sql);
		 try {
			 a = conn.getJsonData(sql);
			 if(a.length()<1){
				 return null;
			 }
			 return a;
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getBuyProductDetailTable(String tempInvoiceNo){
		String sql =  "SELECT BUY_PRODUCT.ITEM_NO, BUY_PRODUCT.PART_NO, PRODUCT.PRD_NAME, BUY_PRODUCT.QUANTITY, "+
					  "UNIT.UNIT_NAME, BUY_PRODUCT.AMOUNT, "+
					  "CASE WHEN CONVERT(varchar(10), BUY_PRODUCT.DISCOUNT_RATE) = '0.00' THEN '-' ELSE CONVERT(varchar(10), BUY_PRODUCT.DISCOUNT_RATE)+'% , ' END+"+
					  "CASE WHEN CONVERT(varchar(10), BUY_PRODUCT.EXTRA_DISCOUNT_RATE) = '0.00' THEN '-' ELSE CONVERT(varchar(10), BUY_PRODUCT.EXTRA_DISCOUNT_RATE)+'%' END AS DISCOUNT_RATE, "+
					  "CASE WHEN CONVERT(varchar(10), BUY_PRODUCT.DISCOUNT_AMOUNT) = '0.00' THEN '-' ELSE CONVERT(varchar(10), BUY_PRODUCT.DISCOUNT_AMOUNT)+' , ' END+"+
					  "CASE WHEN CONVERT(varchar(10), BUY_PRODUCT.EXTRA_DISCOUNT_AMOUNT) = '0.00' THEN '-' ELSE CONVERT(varchar(10), BUY_PRODUCT.EXTRA_DISCOUNT_RATE)+'' END AS DISCOUNT_AMOUNT, "+
					  "BUY_PRODUCT.TOTAL_NET_AMOUNT "+
					  "FROM BUY_PRODUCT "+
					  "LEFT OUTER JOIN PRODUCT ON BUY_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "LEFT OUTER JOIN UNIT ON PRODUCT.UNIT_ID = UNIT.UNIT_ID "+
					  "WHERE BUY_PRODUCT.TEMP_INVOICE_NO = '"+tempInvoiceNo+"' ";
		 try {
			 return conn.getJsonArrayData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	
	public String getBuyProductHistoryStm(String sup_id, String part_no, String prd_type_id, String prd_brand_id, String start_date, String end_date, String temp_start_date, String temp_end_date, String role) {
			sup_id = sup_id.equals("") ? "%" : sup_id;
			part_no = part_no.equals("") ? "%" : part_no;
			prd_type_id = prd_type_id.equals("") ? "%" : prd_type_id;
			prd_brand_id = prd_brand_id.equals("") ? "%" : prd_brand_id;
			start_date = start_date.equals("") ? "00000000" : start_date;
			end_date = end_date.equals("") ? "99999999" : end_date;
			temp_start_date = temp_start_date.equals("") ? "00000000" : temp_start_date;
			temp_end_date = temp_end_date.equals("") ? "99999999" : temp_end_date;
			String qrole = role.equals("03") ? "AND PRODUCT.PRD_CATEGORY_ID = '1' " : "";
			String sql =  "SELECT BUY_PRODUCT.TEMP_INVOICE_DATE, BUY_PRODUCT.INVOICE_DATE, BUY_PRODUCT.SUP_ID+':'+SUPPLIER.SUP_NAME AS SUPPLIER, "+
						  "BUY_PRODUCT.PART_NO, PRODUCT.PRD_NAME, VEHICLE_MODEL.VEHICLE_MODEL_NAME, PRODUCT_BRAND.PRD_BRAND_NAME, "+
						  "BUY_PRODUCT.QUANTITY, BUY_PRODUCT.AMOUNT, BUY_PRODUCT.QUANTITY*BUY_PRODUCT.AMOUNT AS TOTAL_AMOUNT, "+
						  "CASE WHEN CONVERT(varchar(10), BUY_PRODUCT.DISCOUNT_RATE) = '0.00' THEN '-' ELSE CONVERT(varchar(10), BUY_PRODUCT.DISCOUNT_RATE)+'% , ' END+ "+
						  "CASE WHEN CONVERT(varchar(10), BUY_PRODUCT.EXTRA_DISCOUNT_RATE) = '0.00' THEN '-' ELSE CONVERT(varchar(10), BUY_PRODUCT.EXTRA_DISCOUNT_RATE)+'%' END AS DISCOUNT_RATE, "+
						  "CASE WHEN CONVERT(varchar(10), BUY_PRODUCT.DISCOUNT_AMOUNT) = '0.00' THEN '-' ELSE CONVERT(varchar(10), BUY_PRODUCT.DISCOUNT_AMOUNT)+' , ' END+ "+
						  "CASE WHEN CONVERT(varchar(10), BUY_PRODUCT.EXTRA_DISCOUNT_AMOUNT) = '0.00' THEN '-' ELSE CONVERT(varchar(10), BUY_PRODUCT.EXTRA_DISCOUNT_RATE)+'' END AS DISCOUNT_AMOUNT, "+
						  "BUY_PRODUCT.TAX_RATE, "+
						  "CONVERT(DECIMAL(15,2),BUY_PRODUCT.TOTAL_NET_AMOUNT/BUY_PRODUCT.QUANTITY,2) AS NET_PER_AMOUNT, "+
						  "BUY_PRODUCT.TOTAL_NET_AMOUNT, PRODUCT_CATEGORY.PRD_CATEGORY_NAME, "+
						  "PRODUCT_TYPE.PRD_TYPE_NAME, BUY_PRODUCT.TEMP_INVOICE_NO "+
						  "FROM BUY_PRODUCT "+
						  "LEFT OUTER JOIN PRODUCT ON BUY_PRODUCT.PART_NO = PRODUCT.PART_NO "+
						  "LEFT OUTER JOIN SUPPLIER ON BUY_PRODUCT.SUP_ID = SUPPLIER.SUP_ID "+
						  "LEFT OUTER JOIN VEHICLE_MODEL ON PRODUCT.VEHICLE_MODEL_ID = VEHICLE_MODEL.VEHICLE_MODEL_ID "+
						  "LEFT OUTER JOIN PRODUCT_BRAND ON PRODUCT_BRAND.PRD_BRAND_ID = PRODUCT.PRD_BRAND_ID "+
						  "LEFT OUTER JOIN PRODUCT_CATEGORY ON PRODUCT.PRD_CATEGORY_ID = PRODUCT_CATEGORY.PRD_CATEGORY_ID "+
						  "LEFT OUTER JOIN PRODUCT_TYPE ON PRODUCT.PRD_TYPE_ID = PRODUCT_TYPE.PRD_TYPE_ID "+
						  "WHERE BUY_PRODUCT.SUP_ID LIKE '"+sup_id+"' "+
						  "AND BUY_PRODUCT.PART_NO LIKE '"+part_no+"' "+
						  "AND PRODUCT.PRD_TYPE_ID LIKE '"+prd_type_id+"' "+
						  "AND PRODUCT.PRD_BRAND_ID LIKE '"+prd_brand_id+"' "+
						  qrole+
						  "AND BUY_PRODUCT.TEMP_INVOICE_DATE BETWEEN '"+temp_start_date+"' AND '"+temp_end_date+"' "+
						  "AND BUY_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"'";
			System.out.println(sql);
			return sql;
	}
	
	public JSONObject getBuyProductHistoryTable(String sup_id, String part_no, String prd_type_id, String prd_brand_id, String start_date, String end_date, String temp_start_date, String temp_end_date, String role) {
		 try {
			 return conn.getJsonArrayData(getBuyProductHistoryStm(sup_id, part_no, prd_type_id, prd_brand_id, start_date, end_date, temp_start_date, temp_end_date, role));
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	
	// sql for benefit_record
	public String getBenefitStm(String start_date, String end_date, String sup_name, String cust_name, String part_no) {
		
		// where condition
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		
		sup_name = sup_name.equals("") ? "%" : sup_name;
		cust_name = cust_name.equals("") ? "%" : cust_name;
		part_no = part_no.equals("") ? "%" : part_no;
		
		String sql =  "SELECT DISTINCT TOP 100 BUY_PRODUCT.TEMP_INVOICE_DATE, BUY_PRODUCT.PART_NO, "+
					  "PRODUCT.COST_PRICE*BUY_PRODUCT.QUANTITY, "+
					  "BUY_PRODUCT.TOTAL_NET_AMOUNT, BUY_PRODUCT.TOTAL_NET_AMOUNT-(PRODUCT.COST_PRICE*BUY_PRODUCT.QUANTITY), "+
					  "SUPPLIER.SUP_NAME, CUSTOMER.CUST_NAME " +
					  "FROM BUY_PRODUCT "+
					  "LEFT OUTER JOIN PRODUCT ON BUY_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "LEFT OUTER JOIN SELL_PRODUCT ON BUY_PRODUCT.PART_NO = SELL_PRODUCT.PART_NO " +
					  "LEFT OUTER JOIN SUPPLIER ON BUY_PRODUCT.SUP_ID = SUPPLIER.SUP_ID " +
					  "LEFT OUTER JOIN CUSTOMER ON SELL_PRODUCT.CUST_ID = CUSTOMER.CUST_ID " +
					  "WHERE SUPPLIER.SUP_NAME LIKE '" + sup_name + "' "+
					  "AND CUSTOMER.CUST_NAME LIKE '" + cust_name + "' "+
					  "AND BUY_PRODUCT.PART_NO LIKE '" + part_no + "' "+
					  "AND BUY_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"' ";
		System.out.println(sql);
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
	
	// supplier_billing
	public String getSupplierBillingStm(String sup_id, String start_date, String end_date) {
		// where condition
		sup_id = sup_id.equals("") ? "%" : sup_id;
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		
		String sql =  "SELECT ROW_NUMBER() OVER (ORDER BY BUY_PRODUCT.INVOICE_DATE, BUY_PRODUCT.INVOICE_NO) AS ROW, " +
					  "SUPPLIER.SUP_NAME, BUY_PRODUCT.TEMP_INVOICE_NO, BUY_PRODUCT.INVOICE_NO, BUY_PRODUCT.INVOICE_DATE, " +
					  "COUNT(*) AS ITEM_AMOUNT, SUM(BUY_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT  " +
					  "FROM BUY_PRODUCT  " +
					  "LEFT OUTER JOIN SUPPLIER ON BUY_PRODUCT.SUP_ID = SUPPLIER.SUP_ID  " +
					  "WHERE BUY_PRODUCT.SUP_ID LIKE '" + sup_id + "' "+
					  "AND BUY_PRODUCT.INVOICE_DATE BETWEEN '"+ start_date + "' AND '" + end_date + "' " +
					  "GROUP BY SUPPLIER.SUP_NAME, BUY_PRODUCT.TEMP_INVOICE_NO, BUY_PRODUCT.INVOICE_NO, BUY_PRODUCT.INVOICE_DATE " +
					  "ORDER BY ROW";
		System.out.println(sql);
		return sql;
	}
	
	public JSONObject getSupplierBilling(String sup_id, String start_date, String end_date) {
		 try {
			 return conn.getJsonArrayData(getSupplierBillingStm(sup_id, start_date, end_date));
		 } catch (Exception e) {
			 e.printStackTrace();
			 System.out.println("Error : " + e);
			 return null;
		 }
	}
	
	// sum buy product for supplier billing
	public JSONObject getSumBuyProduct(String sup_id, String start_date, String end_date){
		sup_id = sup_id.equals("") ? "%" : sup_id;
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		
		String sql = "SELECT SUM(BUY_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_AMOUNT, " +
				"(SELECT COUNT(DISTINCT BUY_PRODUCT.TEMP_INVOICE_NO) " +
				"FROM BUY_PRODUCT WHERE BUY_PRODUCT.SUP_ID LIKE '" + sup_id + "' " +
				"AND BUY_PRODUCT.INVOICE_DATE BETWEEN '" + start_date + "' AND '" + end_date + "') AS TOTAL_INVOICE, " +
				"SUM(BUY_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT, " +
				"0 AS TOTAL_CR_AMOUNT "+
				//"(SELECT SUM(TOTAL_AMOUNT) FROM RETURN_PRODUCT WHERE RETURN_PRODUCT.CUST_ID LIKE '" + sup_id + "' " +
				//"AND RETURN_PRODUCT.CN_DATE BETWEEN '" + start_date + "' AND '" + end_date +"') AS TOTAL_CR_AMOUNT " +
				"FROM BUY_PRODUCT " +
				"WHERE BUY_PRODUCT.SUP_ID LIKE '" + sup_id + "'" +
				"AND BUY_PRODUCT.INVOICE_DATE BETWEEN '" + start_date + "' AND '" + end_date +"'";
		System.out.println(sql);
		try {
			return conn.getJsonData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	
	//sum buy record of transaction
	public JSONObject getSummaryRecord(String sup_id, String part_no, String prd_type_id, String prd_brand_id, String start_date, String end_date, String temp_start_date, String temp_end_date) {
		sup_id = sup_id.equals("") ? "%" : sup_id;
		part_no = part_no.equals("") ? "%" : part_no;
		prd_type_id = prd_type_id.equals("") ? "%" : prd_type_id;
		prd_brand_id = prd_brand_id.equals("") ? "%" : prd_brand_id;
		start_date = start_date.equals("") ? "00000000" : start_date;
		end_date = end_date.equals("") ? "99999999" : end_date;
		temp_start_date = temp_start_date.equals("") ? "00000000" : temp_start_date;
		temp_end_date = temp_end_date.equals("") ? "99999999" : temp_end_date;
		String sql =  "SELECT SUM(BUY_PRODUCT.QUANTITY) AS QUANTITY, SUM(BUY_PRODUCT.QUANTITY*BUY_PRODUCT.AMOUNT) AS TOTAL_AMOUNT, "+
					  "SUM(BUY_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT "+
					  "FROM BUY_PRODUCT LEFT OUTER JOIN PRODUCT ON BUY_PRODUCT.PART_NO = PRODUCT.PART_NO "+
					  "WHERE BUY_PRODUCT.SUP_ID LIKE '"+sup_id+"' "+
					  "AND BUY_PRODUCT.PART_NO LIKE '"+part_no+"' "+
					  "AND PRODUCT.PRD_TYPE_ID LIKE '"+prd_type_id+"' "+
					  "AND PRODUCT.PRD_BRAND_ID LIKE '"+prd_brand_id+"' "+
					  "AND BUY_PRODUCT.TEMP_INVOICE_DATE BETWEEN '"+temp_start_date+"' AND '"+temp_end_date+"' "+
					  "AND BUY_PRODUCT.INVOICE_DATE BETWEEN '"+start_date+"' AND '"+end_date+"'";
		System.out.println("Get Sum : "+sql);
		try {
			return conn.getJsonData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	
	public boolean insertData(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "";
		sql = "INSERT INTO BUY_PRODUCT "+
			  "(TEMP_INVOICE_NO, TEMP_INVOICE_DATE, INVOICE_NO, ITEM_NO, INVOICE_DATE, SUP_ID, PART_NO, "+
			  "AMOUNT, LAST_BUY_PRICE, QUANTITY, PAYMENT_TERM, PAYMENT_DATE, TOTAL_AMOUNT, DISCOUNT_RATE, "+
			  "DISCOUNT_AMOUNT, TOTAL_DISCOUNT_AMOUNT, TAX_RATE, TAX_AMOUNT, TOTAL_NET_AMOUNT, REMARK, "+
			  "ACTIVE, CREATE_DATE, CREATE_TIME, CREATE_USER_ID, EXTRA_DISCOUNT_RATE, EXTRA_DISCOUNT_AMOUNT, "+
			  "TOTAL_EXTRA_DISCOUNT_AMOUNT) "+
			  "VALUES "+
			  "('"+a.get("TEMP_INVOICE_NO")+"','"+a.get("TEMP_INVOICE_DATE")+"','"+a.get("INVOICE_NO")+"','"+
			  a.get("ITEM_NO")+"','"+a.get("INVOICE_DATE")+"','"+a.get("SUP_ID")+"','"+a.get("PART_NO")+"','"+
			  a.get("AMOUNT")+"','"+a.get("LAST_BUY_PRICE")+"','"+a.get("QUANTITY")+"','"+a.get("PAYMENT_TERM")+"','"+
			  a.get("PAYMENT_DATE")+"','"+a.get("TOTAL_AMOUNT")+"','"+a.get("DISCOUNT_RATE")+"','"+a.get("DISCOUNT_AMOUNT")+"','"+
			  a.get("TOTAL_DISCOUNT_AMOUNT")+"','"+a.get("TAX_RATE")+"','"+a.get("TAX_AMOUNT")+"','"+a.get("TOTAL_NET_AMOUNT")+"','"+
			  a.get("REMARK")+"','"+a.get("ACTIVE")+"','"+a.get("CREATE_DATE")+"','"+a.get("CREATE_TIME")+"','"+
			  a.get("CREATE_USER_ID")+"','"+a.get("EXTRA_DISCOUNT_RATE")+"','"+a.get("EXTRA_DISCOUNT_AMOUNT")+"','"+
			  a.get("TOTAL_EXTRA_DISCOUNT_AMOUNT")+"')";
		System.out.println(sql);
		conn.doConnect();
		if(conn.doSave(sql)){
			conn.doCommit();
			return this.updateTotalBill(a.get("TEMP_INVOICE_NO"));
		}else{
			conn.doRollback();
			return false;
		}
	}
	public String updateTotalBillScript(String tempInvoiceNo){
		return  "UPDATE BUY_PRODUCT "+
				 "SET TOTAL_BILL_AMOUNT = (SELECT SUM(TOTAL_NET_AMOUNT) FROM BUY_PRODUCT WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"'), "+
				 "TOTAL_TAX_AMOUNT = (SELECT SUM(TAX_AMOUNT) FROM BUY_PRODUCT WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"') "+
				 "WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"'";
	}
	public boolean updateTotalBill(String tempInvoiceNo){
		boolean status = false;
		conn.doConnect();
		if(conn.doSave(updateTotalBillScript(tempInvoiceNo))){
			status = conn.doCommit();
		}else{
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
	public boolean updateHeader(HashMap<String,String> a){
		boolean status = false;
		String sql = "UPDATE BUY_PRODUCT SET "+
					 "INVOICE_NO = '"+a.get("INVOICE_NO")+"', "+
					 "INVOICE_DATE = '"+a.get("INVOICE_DATE")+"', "+
					 "PAYMENT_DATE = '"+a.get("PAYMENT_DATE")+"' "+
					 "WHERE TEMP_INVOICE_NO = '"+a.get("TEMP_INVOICE_NO")+"'";
		System.out.println(sql);
		conn.doConnect();
		if(conn.doSave(sql)){
			status = conn.doCommit();
		}else{
			status = false;
		}
		return status;
	}
	public boolean updateDetail(HashMap<String,String> a){
		conn = new DBConn();
		String sql = "UPDATE BUY_PRODUCT SET "+
					 "INVOICE_NO = '"+a.get("INVOICE_NO")+"', "+
					 "ITEM_NO = '"+a.get("ITEM_NO")+"', "+
					 "INVOICE_DATE = '"+a.get("INVOICE_DATE")+"', "+
					 "SUP_ID = '"+a.get("SUP_ID")+"', "+
					 "PART_NO = '"+a.get("PART_NO")+"', "+
					 "AMOUNT = '"+a.get("AMOUNT")+"', "+
					 "LAST_BUY_PRICE = '"+a.get("LAST_BUY_PRICE")+"', "+
					 "QUANTITY = '"+a.get("QUANTITY")+"', "+
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
					 "REMARK = '"+a.get("REMARK")+"', "+
					 "ACTIVE = '"+a.get("ACTIVE")+"', "+
					 "UPDATE_DATE = '"+a.get("UPDATE_DATE")+"', "+
					 "UPDATE_TIME = '"+a.get("UPDATE_TIME")+"', "+
					 "UPDATE_USER_ID = '"+a.get("UPDATE_USER_ID")+"' "+
					 "WHERE TEMP_INVOICE_NO = '"+a.get("TEMP_INVOICE_NO")+"' AND "+
					 "ITEM_NO = '"+a.get("ITEM_NO")+"'";
		System.out.println(sql);
		conn.doConnect();
		if(conn.doSave(sql)){
			conn.doCommit();
			return this.updateTotalBill(a.get("TEMP_INVOICE_NO"));
		}else{
			conn.doRollback();
			return false;
		}
	}	
	public boolean deleteDataItem(String tempInvoiceNo, String itemNo){
		String sql = "";
		boolean status = false;
    	sql = "DELETE FROM BUY_PRODUCT "+
      		  "WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"' AND "+
      		  "ITEM_NO = '"+itemNo+"'";
    	
		conn.doConnect();
		try{
			if(conn.doSave(sql)){
				conn.doCommit();
				status = this.updateTotalBill(tempInvoiceNo);				
			}
		}catch (Exception e){
			conn.doRollback();
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
	public boolean deleteInvoice(String tempInvoiceNo){
		String sql = "";
		boolean status = false;
    	sql = "DELETE FROM BUY_PRODUCT "+
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
}