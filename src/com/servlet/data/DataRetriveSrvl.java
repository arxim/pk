package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.BuyProductDAO;
import com.beans.dao.CustomerDAO;
import com.beans.utils.DBConn;

/**
 * Servlet implementation class DataRetriveSrvl
 */
@WebServlet("/DataRetriveSrvl")
public class DataRetriveSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataRetriveSrvl() {
        super();
        // TODO Auto-generated constructor stub
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
        CustomerDAO cust = null;
        System.out.println("DataRetriveSrvl");        
        System.out.println(request.getParameter("tb"));
        System.out.println(request.getParameter("cust_id"));

        if(request.getParameter("tb").equals("SELL_PRODUCT")){
        }else if(request.getParameter("tb").equals("BUY_PRODUCT")){
        	bpd = new BuyProductDAO();
            out.println(bpd.getBuyProductTable("") );        	
        }else if(request.getParameter("tb").equals("CUSTOMER")){
        	cust = new CustomerDAO();
            out.println(cust.getCustomerByID(request.getParameter("cust_id")));        	
        }else{
        	System.out.println("get json obj");
        	out.println(db.getJsonData(("SELECT CUST_ID, CUST_NAME, TELEPHONE, MOBILE, FAX, DISCOUNT_RATE, PAYMENT_TERM, EMP_ID FROM CUSTOMER WHERE CUST_ID = '01359'")));
        }
        out.close();
	} 

}
