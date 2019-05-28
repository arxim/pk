package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.ReturnProductDAO;
import com.beans.utils.JDate;

@WebServlet("/ReturnProductSrvl")
public class ReturnProductSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReturnProductSrvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String,String> returnProduct = new HashMap<String, String>();
        ReturnProductDAO rpd = new ReturnProductDAO();
        boolean status = false;
        
        if(request.getParameter("method").equals("getDocNo")){
        	out.println(rpd.getDocNo());
        }else if(request.getParameter("method").equals("getItemNo")){
        	out.println(rpd.getItemNo(request.getParameter("doc_no")));
        }else if(request.getParameter("method").equals("fillform")){
        	out.println(rpd.getReturnProductFillForm(request.getParameter("doc_no"), request.getParameter("item_no")));
        }else if(request.getParameter("method").equals("save")){
            returnProduct.put("DOC_NO", request.getParameter("doc_no"));
            returnProduct.put("DOC_DATE", JDate.saveDate(request.getParameter("doc_date")));
            returnProduct.put("CUST_ID", request.getParameter("cust_id"));
            returnProduct.put("CUST_DOC_NO", request.getParameter("cust_doc_no"));
            returnProduct.put("CN_DATE", JDate.saveDate(request.getParameter("cn_date")));
            returnProduct.put("INVOICE_NO", request.getParameter("invoice_no"));
            returnProduct.put("INVOICE_DATE", JDate.saveDate(request.getParameter("invoice_date")));
            returnProduct.put("ITEM_NO", request.getParameter("item_no"));
            returnProduct.put("PART_NO", request.getParameter("part_no"));
            returnProduct.put("AMOUNT", request.getParameter("amount"));
            returnProduct.put("QUANTITY", request.getParameter("quantity"));
            returnProduct.put("TOTAL_AMOUNT", request.getParameter("total_amount"));
            returnProduct.put("REMARK", request.getParameter("note"));
            returnProduct.put("USER_ID", request.getParameter("user_id"));
        	if(request.getParameter("mode").equals("edit")){
                returnProduct.put("UPDATE_DATE", JDate.getDate());
                returnProduct.put("UPDATE_TIME", JDate.getTime());
        		status = rpd.updateData(returnProduct);
        	}else if(request.getParameter("mode").equals("new")){
                returnProduct.put("CREATE_DATE", JDate.getDate());
                returnProduct.put("CREATE_TIME", JDate.getTime());
        		status = rpd.insertData(returnProduct);
        	}else{
        		status = rpd.insertData(returnProduct);
        	}
        	
        	if(status){
        		out.print("1");	        			
        	}else{
        		out.print("0");
        	}
        }else if(request.getParameter("method").equals("delete")){
        	if(rpd.delete(request.getParameter("doc_no"), request.getParameter("item_no"))){
        		out.println("1");
        	}else{
        		out.println("0");		        	
        	}
        }else{
        	
        }
        System.out.println(returnProduct);
	}
}