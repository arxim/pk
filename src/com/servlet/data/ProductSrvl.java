package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.beans.dao.CustomerDAO;
import com.beans.dao.ProductDAO;
import com.beans.utils.DBConn;
import com.beans.utils.JDate;

/**
 * Servlet implementation class ProductSrvl
 */
@WebServlet("/ProductSrvl")
public class ProductSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductSrvl() {
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
        HashMap<String,String> product = new HashMap<String, String>();
        ProductDAO prd = new ProductDAO();
        JSONObject prdId = new JSONObject();

        try{
	        if(request.getParameter("method").equals("fillform")){
	        	out.println(prd.getProductByID(request.getParameter("part_no")));
	        }else if(request.getParameter("method").equals("getinfo")){
	        	out.println(prd.getProductInfo(request.getParameter("part_no")));
	        }else if(request.getParameter("method").equals("datatable")){
	        }else if(request.getParameter("method").equals("getStock")){
	        	System.out.println("getstock");
	        	out.println(prd.getStock(request.getParameter("part_no")));	        	
	        }else if(request.getParameter("method").equals("getid")){
	        	String sql = "SELECT MAX(PRD_ID)+1 AS PRD_ID FROM PRODUCT";
	        	DBConn db = new DBConn();
	        	prdId = db.getJsonData(sql);
	        	for(int i = prdId.get("PRD_ID").toString().length(); i<5; i++){
	        		prdId.put("PRD_ID", "0"+prdId.get("PRD_ID"));
	        	}
	        	out.println(prdId);
	        }else if(request.getParameter("method").equals("PRODUCT_ONHAND")){
	        	System.out.println("hellonew1"+request.getParameter("tb"));
	        	prd = new ProductDAO();
	        	System.out.println("hellonew"+request.getParameter("tb"));
	        	String stock = request.getParameter("stock");
	        	System.out.println("hello"+request.getParameter("tb"));
	        	String stock_status = request.getParameter("stock_status");
	        	
	        	if(stock_status.equals("EQ")){
	        		stock_status = " AND ((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
	        			  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
	        			  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) <= PRD_MIN";
	        	}else if(stock_status.equals("ZE")){
	        		stock_status = " AND ((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
	      				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
	      				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) = 0";
	        	}else if(stock_status.equals("NE")){
	        		stock_status = " AND ((SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM BUY_PRODUCT WHERE PART_NO = PRODUCT.PART_NO)+"+
	      				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM RETURN_PRODUCT WHERE PART_NO = PRODUCT.PART_NO))-"+
	      				  "(SELECT CASE WHEN SUM(QUANTITY) IS NULL THEN 0 ELSE SUM(QUANTITY) END FROM SELL_PRODUCT WHERE PART_NO = PRODUCT.PART_NO) < 0";
	        	}else{
	        		stock_status = "";        		
	        	}
	        	String part_no = request.getParameter("part_no") == "" ? "%" : request.getParameter("part_no");
	        	String product_type = request.getParameter("product_type") == "" ? "%" : request.getParameter("product_type");
	        	String product_brand = request.getParameter("product_brand") == "" ? "%" : request.getParameter("product_brand");
	        	String product_category = request.getParameter("product_category") == "" ? "%" : request.getParameter("product_category");
	        	
	            out.println(prd.getProductOnhand(stock, stock_status, product_type, product_brand, part_no, product_category));        	

	        }else if(request.getParameter("method").equals("save")){
	        	product.put("PRD_ID", request.getParameter("prd_id"));
	        	product.put("PRD_NAME", request.getParameter("prd_name"));
	        	product.put("PART_NO", request.getParameter("part_no"));
	        	product.put("PRD_BRAND_ID", request.getParameter("prd_brand_id"));
	        	product.put("PRD_CATEGORY_ID", request.getParameter("prd_category_id"));
	        	product.put("PRD_TYPE_ID", request.getParameter("prd_type_id"));
	        	product.put("COUNTRY_ID", request.getParameter("country_id"));
	        	product.put("VEHICLE_BRAND_ID", request.getParameter("vehicle_brand_id"));
	        	product.put("VEHICLE_MODEL_ID", request.getParameter("vehicle_model_id"));
	        	product.put("STOCK", request.getParameter("stock"));
	        	product.put("UNIT_ID", request.getParameter("unit_id"));
	        	product.put("UNIT_TYPE_ID", request.getParameter("unit_type_id"));
	        	product.put("PRD_SIZE", request.getParameter("prd_size"));
	        	product.put("SALE_PRICE", request.getParameter("sale_price").equals("")? "0": request.getParameter("sale_price"));
	        	product.put("COST_PRICE", request.getParameter("cost_price").equals("")? "0": request.getParameter("cost_price"));
	        	product.put("REAL_SALE_PRICE", request.getParameter("real_sale_price").equals("")? "0": request.getParameter("real_sale_price"));
	        	product.put("PRD_MIN", request.getParameter("prd_min").equals("")? "0": request.getParameter("prd_min"));
	        	product.put("PRD_REMAIN", request.getParameter("prd_remain").equals("")? "0": request.getParameter("prd_remain"));	        	
	        	product.put("UPDATE_DATE", JDate.getDate());
	        	product.put("UPDATE_TIME", JDate.getTime());
	        	product.put("UPDATE_USER_ID", request.getParameter(""));
	        	if(request.getParameter("mode").equals("edit")){
	        		out.print(prd.updateData(product));
	        	}else if(request.getParameter("mode").equals("new")){
	        		System.out.println(product);
	        		out.print(prd.insertData(product));
	        	}else{}
	        }else if(request.getParameter("method").equals("delete")){
		        if(prd.deleteDataItemByPrdId(request.getParameter("prd_id"))){
		        	out.println("1");
		        }else{
		        	out.println("0");		        	
		        }
	        }
	        else{
	        	System.out.println("no method");
	        }
        }catch(Exception e){
        	System.out.println(e);
        }
        out.close();
	}
}