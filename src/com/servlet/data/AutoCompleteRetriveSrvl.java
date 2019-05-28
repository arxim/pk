package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.utils.DBConn;


/**
 * Servlet implementation class AutoCompleteRetriveSrvl
 */
@WebServlet("/AutoCompleteRetriveSrvl")
public class AutoCompleteRetriveSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AutoCompleteRetriveSrvl() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        DBConn db = new DBConn();
        String keyWord = request.getParameter("term").replace(" ", "%");
        if(request.getParameter("tb").equals("CUSTOMER")){
        	String query = "SELECT CUST_ID, CUST_NAME FROM CUSTOMER WHERE "
        			+"CUST_ID+CUST_NAME LIKE '%"+keyWord.toLowerCase()+"%' "
        			+"OR CUST_ID+CUST_NAME LIKE '%"+keyWord.toUpperCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("SUPPLIER")){
            String query = "SELECT SUP_ID, SUP_NAME FROM SUPPLIER WHERE "
        			+"SUP_ID+SUP_NAME LIKE '%"+keyWord.toLowerCase()+"%' "
        			+"OR SUP_ID+SUP_NAME LIKE '%"+keyWord.toUpperCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("PRODUCT")){
            String query = "SELECT PART_NO, PART_NO+' : '+PRODUCT.PRD_NAME+' : '+VEHICLE_MODEL.VEHICLE_MODEL_NAME+' : '+ "
            		+"PRODUCT.PRD_SIZE+' : '+PRODUCT_BRAND.PRD_BRAND_NAME "
            		+"FROM PRODUCT LEFT OUTER JOIN VEHICLE_MODEL ON PRODUCT.VEHICLE_MODEL_ID = VEHICLE_MODEL.VEHICLE_MODEL_ID "
            		+"LEFT OUTER JOIN PRODUCT_BRAND ON PRODUCT.PRD_BRAND_ID = PRODUCT_BRAND.PRD_BRAND_ID "
            		+"WHERE "
        			+"PART_NO+PRD_NAME+VEHICLE_MODEL.VEHICLE_MODEL_NAME LIKE '%"+keyWord.toUpperCase()+"%' "
        			+"OR PART_NO+PRD_NAME+VEHICLE_MODEL.VEHICLE_MODEL_NAME LIKE '%"+keyWord.toUpperCase()+"%' "
        			+"ORDER BY PART_NO ASC";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("PRODUCT_NAME")){
            String query = "SELECT DISTINCT '', PRD_NAME FROM PRODUCT  "
            		+"WHERE "
        			+"PRD_NAME LIKE '%"+keyWord.toLowerCase()+"%' "
        			+"OR PRD_NAME LIKE '%"+keyWord.toUpperCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("PRODUCT_BRAND")){
            String query = "SELECT PRD_BRAND_ID, PRD_BRAND_NAME FROM PRODUCT_BRAND WHERE "
            		+ "PRD_BRAND_ID+PRD_BRAND_NAME LIKE '%"+keyWord.toLowerCase()+"%' "
                    + "OR PRD_BRAND_ID+PRD_BRAND_NAME LIKE '%"+keyWord.toUpperCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("PRODUCT_TYPE")){
            String query = "SELECT PRD_TYPE_ID, PRD_TYPE_NAME FROM PRODUCT_TYPE WHERE "
            		+ "PRD_TYPE_ID+PRD_TYPE_NAME LIKE '%"+keyWord.toLowerCase()+"%' OR "
    				+ "PRD_TYPE_ID+PRD_TYPE_NAME LIKE '%"+keyWord.toUpperCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("USERS")){
            String query = "SELECT USER_ID, NAME FROM USERS WHERE "
            		+ "USER_ID+NAME LIKE '%"+keyWord.toUpperCase()+"%' OR "
            		+ "USER_ID+NAME LIKE '%"+keyWord.toLowerCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("UNIT")){
            String query = "SELECT UNIT_ID, UNIT_NAME FROM UNIT WHERE "
            		+ "UNIT_ID+UNIT_NAME LIKE '%"+keyWord.toUpperCase()+"%' OR "
            		+ "UNIT_ID+UNIT_NAME LIKE '%"+keyWord.toLowerCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("UNIT_TYPE")){
            String query = "SELECT UNIT_TYPE_ID, UNIT_TYPE_NAME FROM UNIT_TYPE WHERE "
            		+ "UNIT_TYPE_ID+UNIT_TYPE_NAME LIKE '%"+keyWord.toUpperCase()+"%' OR "
            		+ "UNIT_TYPE_ID+UNIT_TYPE_NAME LIKE '%"+keyWord.toLowerCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("VEHICLE_BRAND")){
            String query = "SELECT VEHICLE_BRAND_ID, VEHICLE_BRAND_NAME FROM VEHICLE_BRAND WHERE "
            		+ "VEHICLE_BRAND_ID+VEHICLE_BRAND_NAME LIKE '%"+keyWord.toUpperCase()+"%' OR "
            		+ "VEHICLE_BRAND_ID+VEHICLE_BRAND_NAME LIKE '%"+keyWord.toLowerCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("VEHICLE_MODEL")){
            String query = "SELECT VEHICLE_MODEL_ID, VEHICLE_MODEL_NAME FROM VEHICLE_MODEL WHERE "
            		+ "VEHICLE_MODEL_ID+VEHICLE_MODEL_NAME LIKE '%"+keyWord.toUpperCase()+"%' OR "
            		+ "VEHICLE_MODEL_ID+VEHICLE_MODEL_NAME LIKE '%"+keyWord.toLowerCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }else if(request.getParameter("tb").equals("AREA")){
            String query = "SELECT AREA_ID, ROUTE_NAME FROM AREA WHERE "
            		+ "AREA_ID+ROUTE_NAME LIKE '%" + keyWord.toUpperCase() + "%' OR "
            		+ "AREA_ID+ROUTE_NAME LIKE '%" + keyWord.toLowerCase() + "%'";
            out.println(db.getJsonAutoComplete(query));
        }else{
            String query = "SELECT SUP_ID, SUP_NAME FROM SUPPLIER WHERE SUP_ID+SUP_NAME LIKE '%"
            		+request.getParameter("term")+"%' OR SUP_ID+SUP_NAME LIKE '%"+request.getParameter("term").toLowerCase()+"%'";
            out.println(db.getJsonAutoComplete(query));
        }
        db.doDisconnect();
        out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
