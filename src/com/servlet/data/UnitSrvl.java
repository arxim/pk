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
import com.beans.dao.UnitDAO;

@WebServlet("/UnitSrvl")
public class UnitSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UnitSrvl() {
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
        HashMap<String,String> unit = new HashMap<String, String>();
        UnitDAO unitDao = new UnitDAO();

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	//out.println(prd.getProductTypeByID(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getinfo")){
	        	//out.println(prd.getProductTypeInfo(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getid")){
	        	out.println(unitDao.getUnitId());
	        }else if(request.getParameter("method").equals("save")){
	        	unit.put("UNIT_ID", request.getParameter("unit_id"));
	        	unit.put("UNIT_NAME", request.getParameter("unit_name"));
	        	if(request.getParameter("mode").equals("edit")){
	        		unitDao.updateData(unit);
	        	}else if(request.getParameter("mode").equals("new")){
	        		unitDao.insertData(unit);
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
