package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.ProductTypeDAO;

@WebServlet("/ProductTypeSrvl")
public class ProductTypeSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductTypeSrvl() {
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
        HashMap<String,String> productType = new HashMap<String, String>();
        ProductTypeDAO prd = new ProductTypeDAO();

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	//out.println(prd.getProductTypeByID(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getinfo")){
	        	//out.println(prd.getProductTypeInfo(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getid")){
	        	out.println(prd.getProductTypeId());
	        }else if(request.getParameter("method").equals("save")){
	        	productType.put("PRD_TYPE_ID", request.getParameter("prd_type_id"));
	        	productType.put("PRD_TYPE_NAME", request.getParameter("prd_type_name"));
	        	if(request.getParameter("mode").equals("edit")){
	        		prd.updateData(productType);
	        	}else if(request.getParameter("mode").equals("new")){
	        		prd.insertData(productType);
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