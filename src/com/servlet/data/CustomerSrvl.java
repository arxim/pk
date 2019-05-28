package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import com.beans.dao.CustomerDAO;
import com.beans.utils.DBConn;
import com.beans.utils.JDate;

@WebServlet("/CustomerSrvl")
public class CustomerSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerSrvl() {
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
        HashMap<String,String> customer = new HashMap<String, String>();
        DBConn db = null;
        String sql = "";
        CustomerDAO cust = null;
        JSONObject custId = new JSONObject();
    	System.out.println("Customer "+request.getParameter("method"));

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	sql = "SELECT CUSTOMER.*, USERS.NAME AS EMP_NAME FROM CUSTOMER LEFT OUTER JOIN USERS ON CUSTOMER.EMP_ID = USERS.USER_ID "+
	        		  "WHERE CUST_ID = '"+request.getParameter("cust_id")+"'";
	        	db = new DBConn();
	        	out.println(db.getJsonData(sql));
	        }else if(request.getParameter("method").equals("datatable")){
	        	cust = new CustomerDAO();
	            out.println(cust.getCustomerArea("PK"));        	
	        }else if(request.getParameter("method").equals("getdata")){
	        	sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = '"+request.getParameter("cust_id")+"'";
		        	db = new DBConn();
		        	out.println(db.getJsonData(sql));	        	
	        }else if(request.getParameter("method").equals("getid")){
	        	sql = "SELECT MAX(CUST_ID)+1 AS CUST_ID FROM CUSTOMER";
	        	db = new DBConn();
	        	custId = db.getJsonData(sql);
	        	for(int i = custId.get("CUST_ID").toString().length(); i<5; i++){
	        		custId.put("CUST_ID", "0"+custId.get("CUST_ID"));
	        	}
	        	out.println(custId);
	        }else if(request.getParameter("method").equals("save")){
	        	cust = new CustomerDAO();
	        	customer.put("BUSINESS_ID", "PK");
	        	customer.put("CUST_ID", request.getParameter("cust_id"));
	        	customer.put("CUST_NAME", request.getParameter("cust_name"));
	        	customer.put("ADDRESS1", request.getParameter("address1"));
	        	customer.put("ADDRESS2", request.getParameter("address2"));
	        	customer.put("TELEPHONE", request.getParameter("telephone"));
	        	customer.put("MOBILE", request.getParameter("mobile"));
	        	customer.put("FAX", request.getParameter("fax"));
	        	customer.put("DISCOUNT_RATE", request.getParameter("discount_rate").equals("") ? "0" : request.getParameter("discount_rate") );
	        	customer.put("PAYMENT_TERM", request.getParameter("payment_term").equals("") ? "0" : request.getParameter("payment_term") );
	        	customer.put("EMP_ID", request.getParameter("emp_id"));
	        	customer.put("UPDATE_DATE", JDate.getDate());
	        	customer.put("UPDATE_TIME", JDate.getTime());
	        	customer.put("AREA_ID", request.getParameter("area_id"));
	        	customer.put("TAX_ID", request.getParameter("tax_id"));
	        	customer.put("CR_LIMIT_AMOUNT", request.getParameter("cr_limit_amount").equals("") ? "0" : request.getParameter("cr_limit_amount") );
	        	customer.put("INVOICE_TYPE", request.getParameter("invoice_type"));
	        	customer.put("MODE", request.getParameter("mode"));
	        	customer.put("REMARK", request.getParameter("remark"));
	        	System.out.println(customer);
	        	if(request.getParameter("mode").equals("edit")){
	        		cust.updateData(customer);
	        	}else if(request.getParameter("mode").equals("new")){
	        		cust.insertData(customer);
	        	}else{}

	        	out.print("1");
	        }else{
	        	System.out.println("no method");
	        }
        }catch(Exception e){
        	System.out.println(e);
        }
        out.close();
	}
}