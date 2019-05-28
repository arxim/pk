package com.beans.dao;

import java.util.HashMap;
import org.json.JSONObject;
import com.beans.utils.DBConn;
import com.beans.utils.JDate;

public class ProductDAO {
	DBConn conn = null;

	public ProductDAO(){
		conn = new DBConn();
	}
	public JSONObject getProductTable(String businessId){
		String sql =  "SELECT PART_NO, PRD_NAME, VEHICLE_MODEL_NAME, UNIT.UNIT_NAME, UNIT_TYPE.UNIT_TYPE_NAME, "+
					  "PRD_SIZE, PRODUCT_BRAND.PRD_BRAND_NAME, VEHICLE_BRAND.VEHICLE_BRAND_NAME, "+
					  "CASE WHEN PRODUCT.STOCK = 'Y' THEN 'จัดเก็บ' ELSE "+
					  "CASE WHEN PRODUCT.STOCK = 'N' THEN 'ไม่จัดเก็บ' ELSE "+
					  "CASE WHEN PRODUCT.STOCK = 'X' THEN 'โป้วส่ง' ELSE '' END "+
					  "END "+
					  "END AS STOCK, PRD_MIN, "+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)-"+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) AS "+
					  "PRD_REMAIN "+
					  "FROM PRODUCT "+
					  "LEFT OUTER JOIN UNIT ON PRODUCT.UNIT_ID = UNIT.UNIT_ID "+
					  "LEFT OUTER JOIN UNIT_TYPE ON PRODUCT.UNIT_TYPE_ID = UNIT_TYPE.UNIT_TYPE_ID "+
					  "LEFT OUTER JOIN VEHICLE_MODEL ON PRODUCT.VEHICLE_MODEL_ID = VEHICLE_MODEL.VEHICLE_MODEL_ID "+
					  "LEFT OUTER JOIN PRODUCT_BRAND ON PRODUCT.PRD_BRAND_ID = PRODUCT_BRAND.PRD_BRAND_ID "+
					  "LEFT OUTER JOIN VEHICLE_BRAND ON PRODUCT.VEHICLE_BRAND_ID = VEHICLE_BRAND.VEHICLE_BRAND_ID";
		 try {
			 System.out.println(sql);
			 return conn.getJsonArrayData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public String getProductReportStm(String stock, String stock_status, String product_type, String product_brand, String part_no, String product_category){
		String sql =  "SELECT PART_NO, PRD_NAME, VEHICLE_MODEL_NAME, UNIT.UNIT_NAME, UNIT_TYPE.UNIT_TYPE_NAME, "+
					  "PRD_SIZE, VEHICLE_BRAND.VEHICLE_BRAND_NAME, PRODUCT_BRAND.PRD_BRAND_NAME, "+
					  "PRODUCT_CATEGORY.PRD_CATEGORY_NAME, "+
					  "CASE WHEN PRODUCT.STOCK = 'Y' THEN 'จัดเก็บ' ELSE "+
					  "CASE WHEN PRODUCT.STOCK = 'N' THEN 'ไม่จัดเก็บ' ELSE "+
					  "CASE WHEN PRODUCT.STOCK = 'X' THEN 'โป้วส่ง' ELSE '' END "+
					  "END "+
					  "END AS STOCK, PRD_MIN, "+
					  "((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) AS "+
					  "PRD_REMAIN, PRODUCT.COST_PRICE "+
					  "FROM PRODUCT "+
					  "LEFT OUTER JOIN UNIT ON PRODUCT.UNIT_ID = UNIT.UNIT_ID "+
					  "LEFT OUTER JOIN UNIT_TYPE ON PRODUCT.UNIT_TYPE_ID = UNIT_TYPE.UNIT_TYPE_ID "+
					  "LEFT OUTER JOIN VEHICLE_MODEL ON PRODUCT.VEHICLE_MODEL_ID = VEHICLE_MODEL.VEHICLE_MODEL_ID "+
					  "LEFT OUTER JOIN PRODUCT_BRAND ON PRODUCT.PRD_BRAND_ID = PRODUCT_BRAND.PRD_BRAND_ID "+
					  "LEFT OUTER JOIN VEHICLE_BRAND ON PRODUCT.VEHICLE_BRAND_ID = VEHICLE_BRAND.VEHICLE_BRAND_ID "+
					  "LEFT OUTER JOIN PRODUCT_CATEGORY ON PRODUCT.PRD_CATEGORY_ID = PRODUCT_CATEGORY.PRD_CATEGORY_ID "+
					  "WHERE PRODUCT.PART_NO LIKE '"+part_no+"' AND PRODUCT.PRD_TYPE_ID LIKE '"+product_type+"' "+
					  "AND PRODUCT.PRD_CATEGORY_ID LIKE '" + product_category + "' " + 
					  "AND PRODUCT.PRD_BRAND_ID LIKE '"+product_brand+"' AND PRODUCT.STOCK LIKE '"+stock+"' "+stock_status;
		System.out.println(sql);
		return sql;
	}
	public JSONObject getProductOnhand(String stock, String stock_status, String product_type, String product_brand, String part_no, String product_category){
		String sql =  "SELECT SUM(PRD_ONHAND) AS PRD_ONHAND FROM ( "+
					  "SELECT (((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+ "+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))- "+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))* PRODUCT.COST_PRICE AS PRD_ONHAND "+
					  "FROM PRODUCT "+
					  "WHERE PRODUCT.PART_NO LIKE '"+part_no+"' AND PRODUCT.PRD_TYPE_ID LIKE '"+product_type+"' "+
					  "AND PRODUCT.PRD_CATEGORY_ID LIKE '" + product_category + "' " + 
					  "AND PRODUCT.PRD_BRAND_ID LIKE '"+product_brand+"' AND PRODUCT.STOCK LIKE '"+stock+"' "+stock_status+
					  ") Q";
		System.out.println(sql);
		try {
			return conn.getJsonData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	public JSONObject getProductBestSeller(String stock, String order_status, String product_type, String product_brand, String part_no, String product_category, String term_start, String term_end){
		System.out.println(term_start+":"+term_end);
		term_start = term_start.length()==2 ? "0000"+term_start : term_start;
		term_end = term_end.length()==2 ? "9999"+term_end : term_end;
		double yearStart = Double.parseDouble(term_start.substring(0, 4));
		double monthStart = Double.parseDouble(term_start.substring(4, 6));
		double yearEnd = Double.parseDouble(term_end.substring(0, 4));
		double monthEnd = Double.parseDouble(term_end.substring(4, 6));
		double month = JDate.GetDiffMonth(monthStart, yearStart, monthEnd, yearEnd);
		System.out.println(month+"="+yearStart+":"+monthStart+"-"+yearEnd+"-"+monthEnd);

    	//String month = ""+((Integer.parseInt(term_end) - Integer.parseInt(term_start))+1);
		String sql=	"SELECT *, CASE WHEN PRD_REMAIN < SALE_PER_MONTH THEN 'ORDER' ELSE 'HOLD' END AS STATUS_ORDER FROM "+
					"(SELECT PART_NO, PRD_NAME, VEHICLE_MODEL_NAME, UNIT.UNIT_NAME, "+
					"VEHICLE_BRAND.VEHICLE_BRAND_NAME, PRODUCT_BRAND.PRD_BRAND_NAME, "+
					"PRODUCT_CATEGORY.PRD_CATEGORY_NAME, PRODUCT.COST_PRICE, "+
					"((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+ "+
					"(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))- "+
					"(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) AS PRD_REMAIN, "+
					"CAST(((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO AND "+
					"TEMP_INVOICE_DATE BETWEEN '"+term_start+"01' AND '"+term_end+"31'))/"+month+" AS DECIMAL(16,2)) AS SALE_PER_MONTH "+
					"FROM PRODUCT  "+
					"LEFT OUTER JOIN UNIT ON PRODUCT.UNIT_ID = UNIT.UNIT_ID  "+
					"LEFT OUTER JOIN UNIT_TYPE ON PRODUCT.UNIT_TYPE_ID = UNIT_TYPE.UNIT_TYPE_ID "+
					"LEFT OUTER JOIN VEHICLE_MODEL ON PRODUCT.VEHICLE_MODEL_ID = VEHICLE_MODEL.VEHICLE_MODEL_ID "+
					"LEFT OUTER JOIN PRODUCT_BRAND ON PRODUCT.PRD_BRAND_ID = PRODUCT_BRAND.PRD_BRAND_ID "+
					"LEFT OUTER JOIN VEHICLE_BRAND ON PRODUCT.VEHICLE_BRAND_ID = VEHICLE_BRAND.VEHICLE_BRAND_ID "+
					"LEFT OUTER JOIN PRODUCT_CATEGORY ON PRODUCT.PRD_CATEGORY_ID = PRODUCT_CATEGORY.PRD_CATEGORY_ID "+
					"WHERE PRODUCT.PART_NO LIKE '"+part_no+"' AND PRODUCT.PRD_TYPE_ID LIKE '"+product_type+"' "+
					"AND PRODUCT.PRD_CATEGORY_ID LIKE '"+product_category+"'  "+
					"AND PRODUCT.PRD_BRAND_ID LIKE '"+product_brand+"' AND PRODUCT.STOCK LIKE '"+stock+"') A ";
		if(order_status.equals("ORDER")){
			sql = sql + "WHERE PRD_REMAIN < SALE_PER_MONTH ";
		}else if(order_status.equals("HOLD")){
			sql = sql + "WHERE PRD_REMAIN > SALE_PER_MONTH ";
		}
		try {
			System.out.println(sql);
			return conn.getJsonArrayData(sql);
		} catch (Exception e) {
			System.out.println("Error : "+e);
			return null;
		}
	}
	public JSONObject getProductReportTable(String stock, String stock_status, String product_type, String product_brand, String part_no, String product_category){
		 try {
			 return conn.getJsonArrayData(this.getProductReportStm(stock, stock_status, product_type, product_brand, part_no, product_category));
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getProductByID(String prdId){
		String sql =  "SELECT PRODUCT.*, PRODUCT_BRAND.PRD_BRAND_NAME, PRODUCT_TYPE.PRD_TYPE_NAME, VEHICLE_MODEL.VEHICLE_MODEL_NAME "+
					  "FROM PRODUCT "+
					  "LEFT OUTER JOIN VEHICLE_MODEL ON PRODUCT.VEHICLE_MODEL_ID = VEHICLE_MODEL.VEHICLE_MODEL_ID "+
					  "LEFT OUTER JOIN PRODUCT_BRAND ON PRODUCT.PRD_BRAND_ID = PRODUCT_BRAND.PRD_BRAND_ID "+
					  "LEFT OUTER JOIN PRODUCT_TYPE ON PRODUCT.PRD_TYPE_ID = PRODUCT_TYPE.PRD_TYPE_ID "+
					  "WHERE PART_NO = '"+prdId+"'";
		 try {
			 return conn.getJsonData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getProductInfo(String partNo){
		String sql = "SELECT UNIT.UNIT_NAME, UNIT_TYPE.UNIT_TYPE_NAME, PRODUCT.*, PRODUCT.PRD_NAME+' : '+VEHICLE_MODEL_NAME+' : '+PRODUCT_BRAND.PRD_BRAND_NAME+' : '+PRODUCT.PRD_SIZE AS PART_DETAIL "+
					 "FROM PRODUCT "+
					 "LEFT OUTER JOIN UNIT ON PRODUCT.UNIT_ID = UNIT.UNIT_ID "+
					 "LEFT OUTER JOIN UNIT_TYPE ON PRODUCT.UNIT_TYPE_ID = UNIT_TYPE.UNIT_TYPE_ID "+
					 "LEFT OUTER JOIN VEHICLE_MODEL ON PRODUCT.VEHICLE_MODEL_ID = VEHICLE_MODEL.VEHICLE_MODEL_ID "+
					 "LEFT OUTER JOIN PRODUCT_BRAND ON PRODUCT.PRD_BRAND_ID = PRODUCT_BRAND.PRD_BRAND_ID "+
					 "LEFT OUTER JOIN VEHICLE_BRAND ON PRODUCT.VEHICLE_BRAND_ID = VEHICLE_BRAND.VEHICLE_BRAND_ID "+
					 "WHERE PART_NO = '"+partNo+"'";
		 try {
			 return conn.getJsonData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}
	public JSONObject getStock(String partNo){
		String sql =  "SELECT PART_NO, "+					  
					  "((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
					  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) AS "+
					  "PRD_REMAIN "+
					  "FROM PRODUCT "+
					  "WHERE PART_NO = '"+partNo+"'";
		 try {
			 System.out.println(sql);
			 return conn.getJsonData(sql);
		 } catch (Exception e) {
			 System.out.println("Error : "+e);
			 return null;
		 }
	}	
	public boolean updateStock(String partNo){
		String sql = "UPDATE PRODUCT SET PRD_REMAIN = (SELECT SUM(BUY_PRODUCT.QUANTITY) - SUM(SELL_PRODUCT.QUANTITY) AS REMAIN "+
					 "FROM BUY_PRODUCT LEFT OUTER JOIN SELL_PRODUCT "+
					 "ON BUY_PRODUCT.PART_NO = SELL_PRODUCT.PART_NO "+
					 "WHERE BUY_PRODUCT.PART_NO = '"+partNo+"') "+
					 "WHERE PART_NO = '"+partNo+"'";
		conn = new DBConn();
		conn.doConnect();
		conn.doSave(sql);
		return conn.doCommit();
	}
	public String insertData(HashMap<String,String> a){
		boolean status = false;
		String sql = "INSERT INTO PRODUCT "+
					 "(PRD_ID, PRD_NAME, PART_NO, PRD_BRAND_ID, PRD_CATEGORY_ID, "+
					 "PRD_TYPE_ID, COUNTRY_ID, VEHICLE_BRAND_ID, VEHICLE_MODEL_ID, STOCK, "+
					 "UNIT_ID, UNIT_TYPE_ID, PRD_SIZE, SALE_PRICE, COST_PRICE, "+
					 "REAL_SALE_PRICE, PRD_MIN, PRD_REMAIN) "+
					 "VALUES "+
					 "('"+a.get("PRD_ID")+"','"+a.get("PRD_NAME")+"','"+a.get("PART_NO")+"','"+
					 a.get("PRD_BRAND_ID")+"','"+a.get("PRD_CATEGORY_ID")+"','"+a.get("PRD_TYPE_ID")+"','"+
					 a.get("COUNTRY_ID")+"','"+a.get("VEHICLE_BRAND_ID")+"','"+a.get("VEHICLE_MODEL_ID")+"','"+a.get("STOCK")+"','"+
					 a.get("UNIT_ID")+"','"+a.get("UNIT_TYPE_ID")+"','"+a.get("PRD_SIZE")+"','"+a.get("SALE_PRICE")+"','"+
					 a.get("COST_PRICE")+"','"+a.get("REAL_SALE_PRICE")+"','"+a.get("PRD_MIN")+"','"+a.get("PRD_REMAIN")+"')";
		conn = new DBConn();
		conn.doConnect();
		status = conn.doSave(sql);
		status = conn.doCommit();
		if(status){
			return "1";			
		}else{
			return "0";			
		}
	}
	public String updateData(HashMap<String,String> a){
		boolean status = false;
		String sql = "UPDATE PRODUCT SET "+
					 "PRD_NAME = '"+a.get("PRD_NAME")+"', "+
					 "PART_NO = '"+a.get("PART_NO")+"', "+
					 "PRD_BRAND_ID = '"+a.get("PRD_BRAND_ID")+"', "+
					 "PRD_CATEGORY_ID = '"+a.get("PRD_CATEGORY_ID")+"', "+
					 "PRD_TYPE_ID = '"+a.get("PRD_TYPE_ID")+"', "+
					 "COUNTRY_ID = '"+a.get("COUNTRY_ID")+"', "+
					 "VEHICLE_BRAND_ID = '"+a.get("VEHICLE_BRAND_ID")+"', "+
					 "VEHICLE_MODEL_ID = '"+a.get("VEHICLE_MODEL_ID")+"', "+
					 "STOCK = '"+a.get("STOCK")+"', "+
					 "UNIT_ID = '"+a.get("UNIT_ID")+"', "+
					 "UNIT_TYPE_ID = '"+a.get("UNIT_TYPE_ID")+"', "+
					 "PRD_SIZE = '"+a.get("PRD_SIZE")+"', "+
					 "SALE_PRICE = '"+a.get("SALE_PRICE")+"', "+
					 "COST_PRICE = '"+a.get("COST_PRICE")+"', "+
					 "REAL_SALE_PRICE = '"+a.get("REAL_SALE_PRICE")+"', "+
					 "PRD_MIN = '"+a.get("PRD_MIN")+"', "+
					 "PRD_REMAIN = '"+a.get("PRD_REMAIN")+"', "+
					 "UPDATE_DATE = '"+a.get("UPDATE_DATE")+"', "+
					 "UPDATE_TIME = '"+a.get("UPDATE_TIME")+"' "+
					 "WHERE PRD_ID = '"+a.get("PRD_ID")+"'";
		conn = new DBConn();
		conn.doConnect();
		status = conn.doSave(sql);
		status = conn.doCommit();
		if(status){
			return "1";			
		}else{
			return "0";			
		}
	}
	public String deleteData(String partNo){
		String sql = "DELETE FROM PRODUCT WHERE PART_NO = '"+partNo+"'";
		conn = new DBConn();
		conn.doConnect();
		try{
			conn.doSave(sql);
			conn.doCommit();
			return "1";
		}catch(Exception e){
			System.out.println(sql);
			return "0";
		}
	}
	
	public boolean deleteDataItemByPrdId(String prdId){
		String sql = "DELETE FROM PRODUCT WHERE PRD_ID = '" + prdId + "'";
		boolean status = false;
    	
		conn.doConnect();
		try{
			if(conn.doSave(sql)){
				conn.doCommit();
				status = true;
			}
		}catch (Exception e){
			conn.doRollback();
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
}