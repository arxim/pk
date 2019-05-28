package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.UnitTypeDAO;

/**
 * Servlet implementation class UnitTypeSrvl
 */
@WebServlet("/UnitTypeSrvl")
public class UnitTypeSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UnitTypeSrvl() {
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
        HashMap<String,String> unitType = new HashMap<String, String>();
        UnitTypeDAO unitTypeDAO = new UnitTypeDAO();

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	//out.println(prd.getProductTypeByID(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getinfo")){
	        	//out.println(prd.getProductTypeInfo(request.getParameter("prd_id")));
	        }else if(request.getParameter("method").equals("getid")){
	        	out.println(unitTypeDAO.getUnitTypeId());
	        }else if(request.getParameter("method").equals("save")){
	        	unitType.put("UNIT_TYPE_ID", request.getParameter("unit_type_id"));
	        	unitType.put("UNIT_TYPE_NAME", request.getParameter("unit_type_name"));
	        	if(request.getParameter("mode").equals("edit")){
	        		unitTypeDAO.updateData(unitType);
	        	}else if(request.getParameter("mode").equals("new")){
	        		unitTypeDAO.insertData(unitType);
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