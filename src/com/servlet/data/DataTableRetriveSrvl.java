package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.BuyProductDAO;
import com.beans.dao.ClaimProductDAO;
import com.beans.dao.CustomerDAO;
import com.beans.dao.ProductDAO;
import com.beans.dao.ReturnProductDAO;
import com.beans.dao.SellProductDAO;
import com.beans.utils.DBConn;
import com.beans.utils.JDate;

@WebServlet("/DataTableRetriveSrvl")
public class DataTableRetriveSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataTableRetriveSrvl() {
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
        DBConn db = new DBConn();
        BuyProductDAO bpd = null;
        ReturnProductDAO rpd = null;
        ClaimProductDAO cpd = null;
        CustomerDAO cust = null;
        ProductDAO prd = null;
        SellProductDAO spd = null;

        if(request.getParameter("tb").equals("SELL_PRODUCT")){
        	spd = new SellProductDAO();
        	if(request.getParameter("state").equals("load")){
	            out.println(spd.getSellProductDetailTable(request.getParameter("temp_invoice_no")));
        	}else if(request.getParameter("state").equals("record")){
	            out.println(spd.getSellProductHistoryTable( 
	            	request.getParameter("cust_id"), request.getParameter("part_no"),
	            	request.getParameter("prd_type_id"), request.getParameter("prd_brand_id"),
	            	JDate.saveDate(request.getParameter("start_date")), 
	            	JDate.saveDate(request.getParameter("end_date")), 
	            	JDate.saveDate(request.getParameter("temp_start_date")), 
	            	JDate.saveDate(request.getParameter("temp_end_date"))) );
        	}else if(request.getParameter("state").equals("billing")){
        		out.println(spd.getSellBillingTable( 
	            	request.getParameter("cust_id"), request.getParameter("emp_id"),
	            	JDate.saveDate(request.getParameter("start_date")), 
	            	JDate.saveDate(request.getParameter("end_date")),
	            	request.getParameter("invoice_type")) );
        	}else{
	            out.println(spd.getSellProductTable("00000000","99999999", "00000000","99999999", "datatable", "%"));
        	}
        }else if(request.getParameter("tb").equals("BUY_PRODUCT")){
        	System.out.println("Test "+request.getParameter("role"));
        	bpd = new BuyProductDAO();
        	if(request.getParameter("state").equals("load")){
	            out.println(bpd.getBuyProductDetailTable(request.getParameter("temp_invoice_no")));
        	}else if(request.getParameter("state").equals("record")){
	            out.println(bpd.getBuyProductHistoryTable( 
	            	request.getParameter("sup_id"), request.getParameter("part_no"),
	            	request.getParameter("prd_type_id"), request.getParameter("prd_brand_id"),
	            	JDate.saveDate(request.getParameter("start_date")), 
	            	JDate.saveDate(request.getParameter("end_date")), 
	            	JDate.saveDate(request.getParameter("temp_start_date")), 
	            	JDate.saveDate(request.getParameter("temp_end_date")),
	            	request.getParameter("role")) );
        	}else if (request.getParameter("state").equals("billing")) {
        		out.println(bpd.getSupplierBilling(request.getParameter("sup_id"), JDate.saveDate(request.getParameter("start_date")), JDate.saveDate(request.getParameter("end_date"))));
        	}else{
	            out.println(bpd.getBuyProductTable("") );
        	}
        }else if(request.getParameter("tb").equals("RETURN_PRODUCT")){
        	String start_date = request.getParameter("start_date") == "" ? "00000000" : JDate.saveDate(request.getParameter("start_date"));
        	String end_date = request.getParameter("end_date") == "" ? "99999999" : JDate.saveDate(request.getParameter("end_date"));
        	String cust_id = request.getParameter("cust_id") == "" ? "%" : request.getParameter("cust_id");
        	rpd = new ReturnProductDAO();
        	if(request.getParameter("state").equals("load")){
            	System.out.println("return product");
	            out.println(rpd.getReturnProductDetailTable( start_date, end_date, cust_id));
        	}else if (request.getParameter("state").equals("page_load")) {
        		System.out.println("return product page load");
	            out.println(rpd.getReturnProductCurrentDate());
        	}else{
        	}
        }else if(request.getParameter("tb").equals("CLAIM_PRODUCT")){
        	String startDate = request.getParameter("start_date") == "" ? "00000000" : JDate.saveDate(request.getParameter("start_date"));
        	String endDate = request.getParameter("end_date") == "" ? "99999999" : JDate.saveDate(request.getParameter("end_date"));
        	String cust_id = request.getParameter("cust_id") == "" ? "%" : request.getParameter("cust_id");
        	String sup_id = request.getParameter("sup_id") == "" ? "%" : request.getParameter("sup_id");
        	cpd = new ClaimProductDAO();
        	if(request.getParameter("state").equals("load")){
            	System.out.println("claim product");
	            out.println(cpd.getClaimProductDetailTable(startDate, endDate, cust_id, sup_id, request.getParameter("claim_type")));
        	}else{
        	}
        }else if(request.getParameter("tb").equals("CUSTOMER")){
        	cust = new CustomerDAO();
            out.println(cust.getCustomerTable(""));        	
        }else if(request.getParameter("tb").equals("PRODUCT")){
        	prd = new ProductDAO();
            out.println(prd.getProductTable(""));        	
        }else if(request.getParameter("tb").equals("PRODUCT_REPORT")){
        	prd = new ProductDAO();
        	String stock = request.getParameter("stock");
        	String stock_status = request.getParameter("stock_status");
        	
        	if(stock_status.equals("EQ")){
        		stock_status = " AND ((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
        			  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
        			  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) <= PRD_MIN";
        	}else if(stock_status.equals("ZE")){
        		stock_status = " AND ((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
      				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
      				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) = 0";
        	}else if(stock_status.equals("NE")){
        		stock_status = " AND ((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
      				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
      				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) < 0";
        	}else{
        		stock_status = "";        		
        	}
        	String part_no = request.getParameter("part_no") == "" ? "%" : request.getParameter("part_no");
        	String product_type = request.getParameter("product_type") == "" ? "%" : request.getParameter("product_type");
        	String product_brand = request.getParameter("product_brand") == "" ? "%" : request.getParameter("product_brand");
        	String product_category = request.getParameter("product_category") == "" ? "%" : request.getParameter("product_category");
        	
            out.println(prd.getProductReportTable(stock, stock_status, product_type, product_brand, part_no, product_category));        	
        }else if(request.getParameter("tb").equals("PRODUCT_BEST_SELLER")){
        	prd = new ProductDAO();
        	String term_end = request.getParameter("year_end")+request.getParameter("month_end");
        	String term_start = request.getParameter("year_start")+request.getParameter("month_start");
        	String stock = request.getParameter("stock");
        	String order_status = request.getParameter("order_status");
        	String product_category = request.getParameter("product_category") == "" ? "%" : request.getParameter("product_category");
        	String part_no = request.getParameter("part_no") == "" ? "%" : request.getParameter("part_no");
        	String product_type = request.getParameter("product_type") == "" ? "%" : request.getParameter("product_type");
        	String product_brand = request.getParameter("product_brand") == "" ? "%" : request.getParameter("product_brand");
        	
            out.println(prd.getProductBestSeller(stock, order_status, product_type, product_brand, part_no, product_category, term_start, term_end));        	
        }else if (request.getParameter("tb").equals("BENEFIT_RECORD")) {
        	spd = new SellProductDAO();
        	// demo sql display
        	if(request.getParameter("state").equals("record")){
        		out.println(spd.getBenefit(request.getParameter("start_date"), request.getParameter("end_date"), request.getParameter("emp_id"), request.getParameter("cust_id"), request.getParameter("part_no")));
        	}else{
        		out.println(spd.getSumGroupBenefit(request.getParameter("start_date"), request.getParameter("end_date"), request.getParameter("emp_id"), request.getParameter("cust_id"), request.getParameter("part_no")));
        	}
        }else{
        	System.out.println("get json obj");
        	out.println(db.getJsonData(("SELECT CUST_ID, CUST_NAME, TELEPHONE, MOBILE, FAX, DISCOUNT_RATE, PAYMENT_TERM, EMP_ID FROM CUSTOMER WHERE CUST_ID = '01359'")));
        }
        out.close();
	}
}