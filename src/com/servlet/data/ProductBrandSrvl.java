package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.ProductBrandDAO;

@WebServlet("/ProductBrandSrvl")
public class ProductBrandSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductBrandSrvl() {
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
        HashMap<String,String> productBrand = new HashMap<String, String>();
        ProductBrandDAO prd = new ProductBrandDAO();

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	//out.println(prd.getProductTypeByID(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getinfo")){
	        	//out.println(prd.getProductTypeInfo(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getid")){
	        	out.println(prd.getProductBrandId());
	        }else if(request.getParameter("method").equals("save")){
	        	productBrand.put("PRD_BRAND_ID", request.getParameter("prd_brand_id"));
	        	productBrand.put("PRD_BRAND_NAME", request.getParameter("prd_brand_name"));
	        	if(request.getParameter("mode").equals("edit")){
	        		prd.updateData(productBrand);
	        	}else if(request.getParameter("mode").equals("new")){
	        		prd.insertData(productBrand);
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