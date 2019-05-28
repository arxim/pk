package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.ClaimProductDAO;
import com.beans.utils.JDate;

/**
 * Servlet implementation class ClaimProductSrvl
 */
@WebServlet("/ClaimProductSrvl")
public class ClaimProductSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClaimProductSrvl() {
        super();
        // TODO Auto-generated constructor stub
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
        HashMap<String,String> claimProduct = new HashMap<String, String>();
        ClaimProductDAO cpd = new ClaimProductDAO();
        boolean status = false;
        
        if(request.getParameter("method").equals("getDocNo")){
        	out.println(cpd.getDocNo());

        }else if(request.getParameter("method").equals("getItemNo")){
        	out.println(cpd.getItemNo(request.getParameter("doc_no")));
        
        }else if(request.getParameter("method").equals("fillform")){
        	out.println(cpd.getClaimProductFillForm(request.getParameter("doc_no"), request.getParameter("item_no")));
        
        }else if(request.getParameter("method").equals("save")){
        	claimProduct.put("DOC_NO", request.getParameter("doc_no"));
        	claimProduct.put("DOC_DATE", JDate.saveDate(request.getParameter("doc_date")));
        	claimProduct.put("CUST_ID", request.getParameter("cust_id"));
        	claimProduct.put("SUP_ID", request.getParameter("sup_id"));
        	claimProduct.put("CUST_DOC_NO", request.getParameter("cust_doc_no"));
        	claimProduct.put("CUST_DOC_DATE", JDate.saveDate(request.getParameter("cust_doc_date")));
        	claimProduct.put("SUP_DOC_NO", request.getParameter("sup_doc_no"));
        	claimProduct.put("SUP_DOC_DATE", JDate.saveDate(request.getParameter("sup_doc_date")));
        	claimProduct.put("SUP_SEND_DATE", JDate.saveDate(request.getParameter("sup_send_date")));
        	claimProduct.put("ITEM_NO", request.getParameter("item_no"));
        	claimProduct.put("PART_NO", request.getParameter("part_no"));
        	claimProduct.put("QUANTITY", request.getParameter("quantity"));
        	claimProduct.put("NOTE", request.getParameter("note"));
        	claimProduct.put("USER_ID", request.getParameter("user_id"));
        	if(request.getParameter("mode").equals("edit")){
        		claimProduct.put("UPDATE_DATE", JDate.getDate());
        		claimProduct.put("UPDATE_TIME", JDate.getTime());
        		status = cpd.updateData(claimProduct);
        	}else if(request.getParameter("mode").equals("new")){
        		claimProduct.put("CREATE_DATE", JDate.getDate());
        		claimProduct.put("CREATE_TIME", JDate.getTime());
        		status = cpd.insertData(claimProduct);
        	}else{
        		System.out.println(request.getParameter("mode"));
        		status = cpd.insertData(claimProduct);
        	}
        	
        	if(status){
        		out.print("1");	        			
        	}else{
        		out.print("0");
        	}
        
        }else if(request.getParameter("method").equals("delete")){
        	if(cpd.delete(request.getParameter("doc_no"), request.getParameter("item_no"))){
        		out.println("1");
        	}else{
        		out.println("0");		        	
        	}
        }else{
        	
        }
        System.out.println(claimProduct);
	}
}