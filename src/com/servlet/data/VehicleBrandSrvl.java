package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.VehicleBrandDAO;

@WebServlet("/VehicleBrandSrvl")
public class VehicleBrandSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VehicleBrandSrvl() {
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
        HashMap<String,String> vehicleBrand = new HashMap<String, String>();
        VehicleBrandDAO vBrand = new VehicleBrandDAO();

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	//out.println(prd.getProductTypeByID(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getinfo")){
	        	//out.println(prd.getProductTypeInfo(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getid")){
	        	out.println(vBrand.getVehicleBrandId());
	        }else if(request.getParameter("method").equals("save")){
	        	vehicleBrand.put("VEHICLE_BRAND_ID", request.getParameter("vehicle_brand_id"));
	        	vehicleBrand.put("VEHICLE_BRAND_NAME", request.getParameter("vehicle_brand_name"));
	        	if(request.getParameter("mode").equals("edit")){
	        		vBrand.updateData(vehicleBrand);
	        	}else if(request.getParameter("mode").equals("new")){
	        		vBrand.insertData(vehicleBrand);
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