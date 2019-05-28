package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.BuyProductDAO;
import com.beans.dao.ProductDAO;
import com.beans.utils.DBConn;
import com.beans.utils.JDate;

/**
 * Servlet implementation class BuyProductSrvl
 */
@WebServlet("/BuyProductSrvl")
public class BuyProductSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyProductSrvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String,String> buyProduct = new HashMap<String, String>();
        DBConn db = null;
        String sql = "";
        ProductDAO prd = new ProductDAO();
        BuyProductDAO buypro = new BuyProductDAO();
        boolean status = false;

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	sql = "SELECT TEMP_INVOICE_NO, TEMP_INVOICE_DATE, INVOICE_NO, INVOICE_DATE, B.SUP_ID, S.SUP_NAME, S.ADDRESS1, S.TELEPHONE, "+
	        		  "S.REMARK AS SREMARK, B.CREATE_DATE, B.CREATE_TIME, B.PAYMENT_TERM, B.PAYMENT_DATE, B.ITEM_NO, B.PART_NO, "+
	        		  "P.PRD_NAME, B.AMOUNT, B.QUANTITY, U.UNIT_NAME, B.TOTAL_AMOUNT, B.DISCOUNT_RATE, "+
	        		  "B.DISCOUNT_AMOUNT, B.EXTRA_DISCOUNT_RATE, B.EXTRA_DISCOUNT_AMOUNT, B.TAX_RATE, "+
	        		  "B.TAX_AMOUNT, B.TOTAL_NET_AMOUNT, B.REMARK, B.LAST_BUY_PRICE, B.CREATE_DATE, B.CREATE_TIME, "+
	        		  "P.PRD_NAME+' : '+VM.VEHICLE_MODEL_NAME+' : '+PB.PRD_BRAND_NAME AS PART_DETAIL, "+
	        		  "B.TOTAL_NET_AMOUNT - B.TAX_AMOUNT AS AMOUNT_BEF_TAX "+
	        		  "FROM BUY_PRODUCT B "+
	        		  "LEFT OUTER JOIN SUPPLIER S ON B.SUP_ID = S.SUP_ID "+
	        		  "LEFT OUTER JOIN PRODUCT P ON B.PART_NO = P.PART_NO "+
	        		  "LEFT OUTER JOIN VEHICLE_MODEL VM ON P.VEHICLE_MODEL_ID = VM.VEHICLE_MODEL_ID "+
	        		  "LEFT OUTER JOIN PRODUCT_BRAND PB ON P.PRD_BRAND_ID = PB.PRD_BRAND_ID "+
	        		  "LEFT OUTER JOIN UNIT U ON P.UNIT_ID = U.UNIT_ID "+	        		  
	        		  "WHERE TEMP_INVOICE_NO = '"+request.getParameter("temp_invoice_no")+"' "+
	        		  "AND ITEM_NO = '"+request.getParameter("item_no")+"'";
	        	System.out.println(sql);
	        	db = new DBConn();
	        	out.println(db.getJsonData(sql));
	        }else if(request.getParameter("method").equals("loadheader")){
	        	System.out.println("Load Header");
	        	sql = "SELECT BUY_PRODUCT.TEMP_INVOICE_NO, BUY_PRODUCT.TEMP_INVOICE_DATE, BUY_PRODUCT.INVOICE_NO, "+
	        		  "BUY_PRODUCT.INVOICE_DATE, BUY_PRODUCT.PAYMENT_TERM, BUY_PRODUCT.PAYMENT_DATE, BUY_PRODUCT.SUP_ID, "+
	        		  "SUPPLIER.SUP_NAME, SUPPLIER.ADDRESS1, SUPPLIER.TELEPHONE, SUPPLIER.MOBILE, SUPPLIER.REMARK AS SREMARK, "+
	        		  "BUY_PRODUCT.TAX_RATE, BUY_PRODUCT.TOTAL_TAX_AMOUNT, SUM(BUY_PRODUCT.TOTAL_AMOUNT) AS TOTAL_AMOUNT, "+
	        		  "SUM(BUY_PRODUCT.TOTAL_NET_AMOUNT) AS TOTAL_NET_AMOUNT "+
	        		  "FROM BUY_PRODUCT LEFT OUTER JOIN SUPPLIER ON BUY_PRODUCT.SUP_ID = SUPPLIER.SUP_ID "+
	        		  "WHERE BUY_PRODUCT.TEMP_INVOICE_NO = '"+request.getParameter("temp_invoice_no")+"' "+
	        		  "GROUP BY BUY_PRODUCT.TEMP_INVOICE_NO, BUY_PRODUCT.TEMP_INVOICE_DATE, BUY_PRODUCT.INVOICE_NO, "+
	  	        	  "BUY_PRODUCT.INVOICE_DATE, BUY_PRODUCT.PAYMENT_TERM, BUY_PRODUCT.PAYMENT_DATE, BUY_PRODUCT.SUP_ID, "+
	  	        	  "SUPPLIER.SUP_NAME, SUPPLIER.ADDRESS1, SUPPLIER.TELEPHONE, SUPPLIER.MOBILE, SUPPLIER.REMARK, BUY_PRODUCT.TAX_RATE, "+
	  	        	  "BUY_PRODUCT.TOTAL_TAX_AMOUNT";
	        	System.out.println("a"+sql);
		        db = new DBConn();
		        out.println(db.getJsonData(sql));

	        }else if(request.getParameter("method").equals("datatable")){
	            out.println(buypro.getCurrentDateBuyProduct(request.getParameter("temp_invoice_date_from"), request.getParameter("temp_invoice_date_to"), request.getParameter("invoice_date_from"), request.getParameter("invoice_date_to"), request.getParameter("method")));     
	        }else if(request.getParameter("method").equals("loaddatatable")){
	            out.println(buypro.getCurrentDateBuyProduct(request.getParameter("temp_invoice_date_from"), request.getParameter("temp_invoice_date_to"), request.getParameter("invoice_date_from"), request.getParameter("invoice_date_to"), request.getParameter("method")));     
	        }else if(request.getParameter("method").equals("datatabledetail")){
	            out.println(buypro.getBuyProductTable(""));	            
	        }else if(request.getParameter("method").equals("getLastPrice")){
	            out.println(buypro.getLastBuyPrice(request.getParameter("part_no").toString(),request.getParameter("sup_id").toString()));        	

	        }else if(request.getParameter("method").equals("getItemNo")){
	        	sql = "SELECT MAX(ITEM_NO)+1 AS ITEM_NO FROM BUY_PRODUCT WHERE TEMP_INVOICE_NO = '"+request.getParameter("temp_invoice_no")+"'";
	        	db = new DBConn();
	        	out.println(db.getJsonData(sql));
	        	
	        }else if(request.getParameter("method").equals("getTempInvoiceNo")){
	        	out.println(buypro.getTempInvoiceNo());

	        }else if(request.getParameter("method").equals("delete")){
		        if(buypro.deleteDataItem(request.getParameter("temp_invoice_no"), request.getParameter("item_no"))){
		        	out.println("1");
		        }else{
		        	out.println("0");		        	
		        }
	        }else if(request.getParameter("method").equals("deleteInvoice")){
		        if(buypro.deleteInvoice(request.getParameter("temp_invoice_no"))){
		        	out.println("1");
		        }else{
		        	out.println("0");		        	
		        }
	        }else if(request.getParameter("method").equals("updateInvoice")){
	        	buyProduct.put("TEMP_INVOICE_NO", request.getParameter("temp_invoice_no"));
	        	buyProduct.put("TEMP_INVOICE_DATE", JDate.saveDate(request.getParameter("temp_invoice_date")));
	        	buyProduct.put("INVOICE_DATE", JDate.saveDate(request.getParameter("invoice_date")));
	        	buyProduct.put("INVOICE_NO", request.getParameter("invoice_no"));
	        	buyProduct.put("SUP_ID", request.getParameter("sup_id"));
	        	buyProduct.put("PAYMENT_DATE", JDate.saveDate(request.getParameter("payment_date")));
	        	status = buypro.updateHeader(buyProduct);
	        	if(status){
	        		out.println("1");
	        	}else{
	        		out.println("0");
	        	}
	        }else if(request.getParameter("method").equals("save")){
	        	String totalDiscAmt = request.getParameter("total_discount_amount");
	        	String totalExtDiscAmt = request.getParameter("total_extra_discount_amount");
	        	
	        	buyProduct.put("TEMP_INVOICE_NO", request.getParameter("temp_invoice_no"));
	        	buyProduct.put("TEMP_INVOICE_DATE", JDate.saveDate(request.getParameter("temp_invoice_date")));
	        	buyProduct.put("INVOICE_NO", request.getParameter("invoice_no"));
	        	buyProduct.put("ITEM_NO", request.getParameter("item_no"));
	        	buyProduct.put("INVOICE_DATE", JDate.saveDate(request.getParameter("invoice_date")));
	        	buyProduct.put("SUP_ID", request.getParameter("sup_id"));
	        	buyProduct.put("PART_NO", request.getParameter("part_no"));
	        	buyProduct.put("AMOUNT", request.getParameter("amount"));
	        	buyProduct.put("LAST_BUY_PRICE", request.getParameter("last_buy_price"));
	        	buyProduct.put("QUANTITY", request.getParameter("quantity"));
	        	buyProduct.put("PAYMENT_TERM", request.getParameter("payment_term"));
	        	buyProduct.put("PAYMENT_DATE", JDate.saveDate(request.getParameter("payment_date")));
	        	buyProduct.put("TOTAL_AMOUNT", request.getParameter("total_amount"));
	        	buyProduct.put("DISCOUNT_RATE", request.getParameter("discount_rate"));
	        	buyProduct.put("DISCOUNT_AMOUNT", request.getParameter("discount_amount"));
	        	buyProduct.put("TOTAL_DISCOUNT_AMOUNT", totalDiscAmt.equals("") ? "0" : totalDiscAmt);
	        	buyProduct.put("EXTRA_DISCOUNT_RATE", request.getParameter("extra_discount_rate"));
	        	buyProduct.put("EXTRA_DISCOUNT_AMOUNT", request.getParameter("extra_discount_amount"));
	        	buyProduct.put("TOTAL_EXTRA_DISCOUNT_AMOUNT", totalExtDiscAmt.equals("") ? "0" : totalExtDiscAmt);
	        	buyProduct.put("TAX_RATE", request.getParameter("tax_rate"));
	        	buyProduct.put("TAX_AMOUNT", request.getParameter("tax_amount"));
	        	buyProduct.put("TOTAL_NET_AMOUNT", request.getParameter("total_net_amount"));
	        	buyProduct.put("REMARK", request.getParameter("remark"));
	        	buyProduct.put("ACTIVE", "Y");
	        	buyProduct.put("CREATE_DATE", JDate.getDate());
	        	buyProduct.put("CREATE_TIME", JDate.getTime());
	        	buyProduct.put("CREATE_USER_ID", request.getParameter("create_user_id"));
	        	buyProduct.put("UPDATE_DATE", JDate.getDate());
	        	buyProduct.put("UPDATE_TIME", JDate.getTime());
	        	buyProduct.put("UPDATE_USER_ID", request.getParameter("update_user_id"));
	        	System.out.println("Mode : "+request.getParameter("mode")+buyProduct);
	        	buypro = new BuyProductDAO();
	        	if(request.getParameter("mode").equals("edit")){
	        		status = buypro.updateDetail(buyProduct);
	        	}else if(request.getParameter("mode").equals("new")){
	        		status = buypro.insertData(buyProduct);
	        	}else{
	        		status = buypro.insertData(buyProduct);
	        	}
	        	status = prd.updateStock(request.getParameter("part_no"));
	        	buypro.setNewCostPrice(request.getParameter("part_no"));
	        	if(status){
	        		if(buypro.updateTotalBill(request.getParameter("temp_invoice_no"))){
	    	        	out.print("1");	        			
	        		}else{
	    	        	out.print("0");
	        		}
	        	}else{
		        	out.print("0");
	        	}
	        }else if (request.getParameter("method").equals("getSumBuyProduct")) {
	        	// loadSumTable from supplier billing
	        	out.print(buypro.getSumBuyProduct(request.getParameter("sup_id"), JDate.saveDate(request.getParameter("start_date")), JDate.saveDate(request.getParameter("end_date"))));
	        }else if (request.getParameter("method").equals("getSummaryRecord")) {
	        	// loadSumTable from supplier billing
	        	out.print(buypro.getSummaryRecord( 
		            	request.getParameter("sup_id"), request.getParameter("part_no"),
		            	request.getParameter("prd_type_id"), request.getParameter("prd_brand_id"),
		            	JDate.saveDate(request.getParameter("start_date")), 
		            	JDate.saveDate(request.getParameter("end_date")), 
		            	JDate.saveDate(request.getParameter("temp_start_date")), 
		            	JDate.saveDate(request.getParameter("temp_end_date"))) );
	        }else{
	        	System.out.println("no method");
	        }
        }catch(Exception e){
        	System.out.println(e);
        }
        out.close();
	}
}