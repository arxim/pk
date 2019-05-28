package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.VehicleModelDAO;

@WebServlet("/VehicleModelSrvl")
public class VehicleModelSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VehicleModelSrvl() {
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
        HashMap<String,String> vehicleModel = new HashMap<String, String>();
        VehicleModelDAO vModel = new VehicleModelDAO();

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	//out.println(prd.getProductTypeByID(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getinfo")){
	        	//out.println(prd.getProductTypeInfo(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getid")){
	        	out.println(vModel.getVehicleModelId());
	        }else if(request.getParameter("method").equals("save")){
	        	vehicleModel.put("VEHICLE_MODEL_ID", request.getParameter("vehicle_model_id"));
	        	vehicleModel.put("VEHICLE_MODEL_NAME", request.getParameter("vehicle_model_name"));
	        	if(request.getParameter("mode").equals("edit")){
	        		vModel.updateData(vehicleModel);
	        	}else if(request.getParameter("mode").equals("new")){
	        		vModel.insertData(vehicleModel);
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