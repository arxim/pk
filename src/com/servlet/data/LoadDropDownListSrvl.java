package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.utils.DBConn;

/**
 * Servlet implementation class LoadDropDownList
 */
@WebServlet("/LoadDropDownListSrvl")
public class LoadDropDownListSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoadDropDownListSrvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        DBConn db = new DBConn();
        ArrayList<HashMap<String,String>> dropdown = new ArrayList<HashMap<String,String>>();
        String list = "";
    	String options = "";

        if(request.getParameter("tb").equals("USERS")){
            list = "SELECT '' AS CODE, '--- เลือกพนักงาน ---' AS NAME FROM USERS "+
         		   "UNION "+
            	   "SELECT USER_ID AS CODE, NAME AS NAME FROM USERS";
        }else if(request.getParameter("tb").equals("AREA")){
        	list = "SELECT '' AS CODE, '--- เลือกเขตพื้นที่ ---' AS NAME FROM AREA "+
        		   "UNION "+
        		   "SELECT AREA_ID AS CODE, ROUTE_NAME AS NAME FROM AREA ORDER BY CODE";
        }else if(request.getParameter("tb").equals("COUNTRY")){
        	list = "SELECT '' AS CODE, '--- เลือกประเทศที่ผลิต ---' AS NAME FROM COUNTRY "+
        		   "UNION "+
        		   "SELECT COUNTRY_ID AS CODE, COUNTRY_NAME AS NAME FROM COUNTRY ORDER BY CODE";
        }else if(request.getParameter("tb").equals("VEHICLE_BRAND")){
        	list = "SELECT '' AS CODE, '--- เลือกยี่ห้อรถ ---' AS NAME FROM VEHICLE_BRAND "+
        		   "UNION "+
        		   "SELECT VEHICLE_BRAND_ID AS CODE, VEHICLE_BRAND_NAME AS NAME FROM VEHICLE_BRAND ORDER BY CODE";
        }else if(request.getParameter("tb").equals("VEHICLE_MODEL")){
        	list = "SELECT '' AS CODE, '--- เลือกรุ่นรถ ---' AS NAME FROM VEHICLE_MODEL "+
        		   "UNION "+
        		   "SELECT VEHICLE_MODEL_ID AS CODE, VEHICLE_MODEL_NAME AS NAME FROM VEHICLE_MODEL ORDER BY CODE";
        }else if(request.getParameter("tb").equals("UNIT")){
        	list = "SELECT '' AS CODE, '--- เลือกหน่วยนับ ---' AS NAME FROM UNIT "+
        		   "UNION "+
        		   "SELECT UNIT_ID AS CODE, UNIT_NAME AS NAME FROM UNIT ORDER BY CODE";
        }else if(request.getParameter("tb").equals("UNIT_TYPE")){
        	list = "SELECT '' AS CODE, '--- รายละเอียดหน่วยนับ ---' AS NAME FROM UNIT_TYPE "+
        		   "UNION "+
        		   "SELECT UNIT_TYPE_ID AS CODE, UNIT_TYPE_NAME AS NAME FROM UNIT_TYPE ORDER BY CODE";
        }else if(request.getParameter("tb").equals("PRODUCT_CATEGORY")){
        	list = "SELECT '' AS CODE, '--- เลือกชนิดสินค้า ---' AS NAME FROM UNIT_TYPE "+
         		   "UNION "+
         		   "SELECT PRD_CATEGORY_ID AS CODE, PRD_CATEGORY_NAME AS NAME FROM PRODUCT_CATEGORY ORDER BY CODE";
	    }else{
        	
        }
        db.doConnect();
        dropdown = db.getData(list);
        db.doDisconnect();
        
        for(int i = 0; i<dropdown.size(); i++){
        	options += "<option value=\""+dropdown.get(i).get("CODE")+"\"> "+dropdown.get(i).get("NAME")+" </option>";
        }
    	out.println(options);
    	out.close();
	}
}
