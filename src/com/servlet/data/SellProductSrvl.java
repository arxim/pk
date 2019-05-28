package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.ProductDAO;
import com.beans.dao.SellProductDAO;
import com.beans.dao.UsersDAO;
import com.beans.utils.JDate;

@WebServlet("/SellProductSrvl")
public class SellProductSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SellProductSrvl() {
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
        HashMap<String,String> sellProduct = new HashMap<String, String>();
        UsersDAO userDao = new UsersDAO();
        SellProductDAO sellpro = new SellProductDAO();
        ProductDAO prd = new ProductDAO();
        boolean status = false;
    	System.out.println(request.getParameter("method").equals("loaddatatable"));
        try{
	        if(request.getParameter("method").equals("fillform")){
	        	out.println(sellpro.getSellProductFillForm(request.getParameter("temp_invoice_no"), request.getParameter("item_no")));
	        
	        }else if(request.getParameter("method").equals("getUserRole")){
	        	out.println(userDao.getUserRole(request.getParameter("usetId")));

	        }else if(request.getParameter("method").equals("loadheader")){
	        	out.println(sellpro.getHeader(request.getParameter("temp_invoice_no")));
	        
	        }else if(request.getParameter("method").equals("loadheaderpage2")){
	        	out.println(sellpro.getHeaderPage2(request.getParameter("temp_invoice_no")));
	        
	        }else if(request.getParameter("method").equals("loaddatatable")){
	            out.println(sellpro.getSellProductTable(request.getParameter("temp_invoice_date_from"), request.getParameter("temp_invoice_date_to"), request.getParameter("invoice_date_from"), request.getParameter("invoice_date_to"), request.getParameter("method"), request.getParameter("invoice_type")));
	        
	        }else if(request.getParameter("method").equals("datatable")){
	            out.println(sellpro.getSellProductTable(request.getParameter("temp_invoice_date_from"), request.getParameter("temp_invoice_date_to"), request.getParameter("invoice_date_from"), request.getParameter("invoice_date_to"), request.getParameter("method"), request.getParameter("invoice_type")));
	        
	        }else if(request.getParameter("method").equals("datatabledetail")){
	            out.println(sellpro.getSellProductDetailTable(request.getParameter("temp_invoice_no")));        	

	        }else if(request.getParameter("method").equals("getItemNo")){
	        	out.println(sellpro.getItemNo(request.getParameter("temp_invoice_no")));
	        
	        }else if(request.getParameter("method").equals("getLastPrice")){
	            out.println(sellpro.getLastSellPrice(request.getParameter("part_no").toString(),request.getParameter("cust_id").toString()));        	

	        }else if(request.getParameter("method").equals("sumBenefit")){
	        	out.println(sellpro.getSumBenefit(request.getParameter("start_date"), request.getParameter("end_date"), request.getParameter("emp_id"), request.getParameter("cust_id"), request.getParameter("part_no")));

	        }else if(request.getParameter("method").equals("getTempInvoiceNo")){
	        	out.println(sellpro.getTempInvoiceNo());

	        }else if(request.getParameter("method").equals("getSumBilling")){
	            out.println(sellpro.getSumSellBillingTable(
	            	request.getParameter("cust_id"), request.getParameter("emp_id"),
	            	JDate.saveDate(request.getParameter("start_date")), JDate.saveDate(request.getParameter("end_date")),
	            	request.getParameter("invoice_type")) );        	

	        }else if(request.getParameter("method").equals("delete")){
		        if(sellpro.deleteData(request.getParameter("temp_invoice_no"), request.getParameter("item_no"))){
		        	out.println("1");
		        }else{
		        	out.println("0");		        	
		        }
	        }else if(request.getParameter("method").equals("deleteInvoice")){
		        if(sellpro.deleteInvoice(request.getParameter("temp_invoice_no"))){
		        	out.println("1");
		        }else{
		        	out.println("0");		        	
		        }
	        }else if(request.getParameter("method").equals("updateInvoice")){
	        	sellProduct.put("TEMP_INVOICE_NO", request.getParameter("temp_invoice_no"));
	        	sellProduct.put("TEMP_INVOICE_DATE", JDate.saveDate(request.getParameter("temp_invoice_date")));
	        	sellProduct.put("INVOICE_DATE", JDate.saveDate(request.getParameter("invoice_date")));
	        	sellProduct.put("INVOICE_NO", request.getParameter("invoice_no"));
	        	sellProduct.put("CUST_ID", request.getParameter("cust_id"));
	        	sellProduct.put("EMP_ID", request.getParameter("emp_id"));
	        	sellProduct.put("PAYMENT_TERM", request.getParameter("payment_term"));
	        	sellProduct.put("PAYMENT_DATE", request.getParameter("payment_date"));
	        	sellProduct.put("INVOICE_TYPE", request.getParameter("invoice_type"));
	        	sellProduct.put("TAX_RATE", request.getParameter("tax_rate"));
	        	status = sellpro.updateHeader(sellProduct);
	        	if(status){
	        		out.println("1");
	        	}else{
	        		out.println("0");
	        	}
	        }else if(request.getParameter("method").equals("save")){
	        	sellProduct.put("TEMP_INVOICE_NO", request.getParameter("temp_invoice_no"));
	        	sellProduct.put("TEMP_INVOICE_DATE", JDate.saveDate(request.getParameter("temp_invoice_date")));
	        	sellProduct.put("INVOICE_NO", request.getParameter("invoice_no"));
	        	sellProduct.put("ITEM_NO", request.getParameter("item_no"));
	        	sellProduct.put("INVOICE_DATE", JDate.saveDate(request.getParameter("invoice_date")));
	        	sellProduct.put("INVOICE_TYPE", request.getParameter("invoice_type"));
	        	sellProduct.put("CUST_ID", request.getParameter("cust_id"));
	        	sellProduct.put("PART_NO", request.getParameter("part_no"));
	        	sellProduct.put("EMP_ID", request.getParameter("emp_id"));
	        	sellProduct.put("AMOUNT", request.getParameter("amount"));
	        	sellProduct.put("LAST_SELL_DATE", JDate.saveDate(request.getParameter("last_sell_date")));
	        	sellProduct.put("LAST_SELL_PRICE", request.getParameter("last_sell_price").equals("") ? "0" : request.getParameter("last_sell_price") );
	        	sellProduct.put("OLD_COST", request.getParameter("old_cost").equals("") ? "0" : request.getParameter("old_cost") );
	        	sellProduct.put("QUANTITY", request.getParameter("quantity"));
	        	sellProduct.put("PAYMENT_TERM", request.getParameter("payment_term"));
	        	sellProduct.put("PAYMENT_DATE", JDate.saveDate(request.getParameter("payment_date")));
	        	sellProduct.put("TOTAL_AMOUNT", request.getParameter("total_amount"));
	        	sellProduct.put("DISCOUNT_RATE", request.getParameter("discount_rate").equals("") ? "0" : request.getParameter("discount_rate") );
	        	sellProduct.put("DISCOUNT_AMOUNT", request.getParameter("discount_amount").equals("") ? "0" : request.getParameter("discount_amount") );
	        	sellProduct.put("TOTAL_DISCOUNT_AMOUNT", request.getParameter("total_discount_amount").equals("") ? "0" : request.getParameter("total_discount_amount") );
	        	sellProduct.put("EXTRA_DISCOUNT_RATE", request.getParameter("extra_discount_rate").equals("") ? "0" : request.getParameter("extra_discount_rate") );
	        	sellProduct.put("EXTRA_DISCOUNT_AMOUNT", request.getParameter("extra_discount_amount").equals("") ? "0" : request.getParameter("extra_discount_amount") );
	        	sellProduct.put("TOTAL_EXTRA_DISCOUNT_AMOUNT", request.getParameter("total_extra_discount_amount").equals("") ? "0" : request.getParameter("total_extra_discount_amount") );
	        	sellProduct.put("TAX_RATE", request.getParameter("tax_rate"));
	        	sellProduct.put("TAX_AMOUNT", request.getParameter("tax_amount").equals("") ? "0" : request.getParameter("tax_amount") );
	        	sellProduct.put("TOTAL_NET_AMOUNT", request.getParameter("total_net_amount"));
	        	sellProduct.put("NOTE", request.getParameter("note"));
	        	sellProduct.put("ACTIVE", "Y");
	        	sellProduct.put("CREATE_DATE", JDate.getDate());
	        	sellProduct.put("CREATE_TIME", JDate.getTime());
	        	sellProduct.put("CREATE_USER_ID", request.getParameter("create_user_id"));
	        	sellProduct.put("UPDATE_DATE", JDate.getDate());
	        	sellProduct.put("UPDATE_TIME", JDate.getTime());
	        	sellProduct.put("UPDATE_USER_ID", request.getParameter("update_user_id"));
	        	if(request.getParameter("mode").equals("edit")){
	        		status = sellpro.updateData(sellProduct);
	        	}else if(request.getParameter("mode").equals("new")){
	        		status = sellpro.insertData(sellProduct);
	        	}else{
	        		status = sellpro.insertData(sellProduct);
	        	}
	        	if(status){
	        		if(prd.updateStock(request.getParameter("prd_id"))){
		    	        out.print("1");	        			
	        		}else{
	        			out.print("0");
	        		}
	        	}else{
		        	out.print("0");
	        	}
	        }else if (request.getParameter("method").equals("getSellProductByPartNoAndInvoiceNo")) {
	        	out.println(sellpro.getSellProductByPartNoAndInvoiceNo(request.getParameter("part_no"), request.getParameter("temp_invoice_no")));
	        }else if (request.getParameter("method").equals("getSummaryRecord")) {
	        	// loadSumTable from supplier billing
	        	out.print(sellpro.getSummaryRecord( 
		            	request.getParameter("cust_id"), request.getParameter("part_no"),
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