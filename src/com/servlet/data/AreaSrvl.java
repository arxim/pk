package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.AreaDAO;
import com.beans.dao.VehicleBrandDAO;

@WebServlet("/AreaSrvl")
public class AreaSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AreaSrvl() {
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
        AreaDAO areaDao = new AreaDAO();

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	// 
	        }else if(request.getParameter("method").equals("getinfo")){
	        	// 
	        }else if(request.getParameter("method").equals("getid")){
	        	out.println(areaDao.getAreaId());
	        }else if(request.getParameter("method").equals("save")){
	        	vehicleBrand.put("AREA_ID", request.getParameter("area_id"));
	        	vehicleBrand.put("ROUTE_NAME", request.getParameter("route_name"));
	        	if(request.getParameter("mode").equals("edit")){
	        		areaDao.updateData(vehicleBrand);
	        	}else if(request.getParameter("mode").equals("new")){
	        		areaDao.insertData(vehicleBrand);
	        	}else{ }
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