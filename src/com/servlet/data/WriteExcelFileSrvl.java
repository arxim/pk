package com.servlet.data;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.beans.dao.BuyProductDAO;
import com.beans.dao.ClaimProductDAO;
import com.beans.dao.ProductDAO;
import com.beans.dao.SellProductDAO;
import com.beans.utils.DBConn;
import com.beans.utils.JDate;

/**
 * Servlet implementation class WriteExcelFileSrvl
 */
@WebServlet("/WriteExcelFileSrvl")
public class WriteExcelFileSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String EMPTY = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteExcelFileSrvl() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SellProductDAO sellProductDAO = null;
		ProductDAO productDAO = null;
		BuyProductDAO bpd = null;
		ClaimProductDAO cpd = null;
		String fileName = "";
		DBConn conn = new DBConn();
		Map<String, Object[]> data = null;


		if(request.getParameter("report_name").equals("customer_billing")){
			sellProductDAO = new SellProductDAO();
			fileName = "Billing"+JDate.getDate()+JDate.getTime();
			data = conn.getDataMap(sellProductDAO.getSumSellBillingSql(
			        request.getParameter("cust_id"), request.getParameter("employee"),
			        JDate.saveDate(request.getParameter("invoice_date_from")), JDate.saveDate(request.getParameter("invoice_date_to")),
			        request.getParameter("invoice_type")) );
		}else if(request.getParameter("report_name").equals("product_report")){
			productDAO = new ProductDAO();
			fileName = "Product"+JDate.getDate()+JDate.getTime();			
        	String stock = request.getParameter("h_stock");
        	String stock_status = request.getParameter("h_stock_status");

        	/*
        	if(stock_status.equals("EQ")){
        		stock_status = "AND PRD_REMAIN <= PRD_MIN";
        	}else if(stock_status.equals("ZE")){
        		stock_status = "AND PRD_REMAIN = 0";
        	}else if(stock_status.equals("NE")){
        		stock_status = "AND PRD_REMAIN < 0";
        	}else{
        		stock_status = "";        		
        	}
        	*/

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
        	String product_type = request.getParameter("prd_type_id") == "" ? "%" : request.getParameter("prd_type_id");
        	String product_brand = request.getParameter("prd_brand_id") == "" ? "%" : request.getParameter("prd_brand_id");
        	String product_category = request.getParameter("product_category") == "" ? "%" : request.getParameter("product_category");
			data = conn.getDataMap(productDAO.getProductReportStm(stock, stock_status, product_type, product_brand, part_no, product_category));
		}else if (request.getParameter("report_name").equals("buy_product")) {
			bpd = new BuyProductDAO();
			fileName = "Buy_Product_" + JDate.getDate() + JDate.getTime();	
			data = conn.getDataMap(bpd.getBuyProductHistoryStm( 
	            	request.getParameter("sup_id"), request.getParameter("part_no"),
	            	request.getParameter("prd_type_id"), request.getParameter("prd_brand_id"),
	            	JDate.saveDate(request.getParameter("start_date")), 
	            	JDate.saveDate(request.getParameter("end_date")), 
	            	JDate.saveDate(request.getParameter("temp_start_date")), 
	            	JDate.saveDate(request.getParameter("temp_end_date")),
	            	request.getParameter("role")));
		}else if (request.getParameter("report_name").equals("claim_product")) {
			cpd = new ClaimProductDAO();
			fileName = "Claim_Product_" + JDate.getDate() + JDate.getTime();	
			
			String startDate = EMPTY.equals(request.getParameter("start_date")) ? "00000000" : JDate.saveDate(request.getParameter("start_date"));
        	String endDate = EMPTY.equals(request.getParameter("end_date")) ? "99999999" : JDate.saveDate(request.getParameter("end_date"));
        	String cust_id = EMPTY.equals(request.getParameter("cust_id")) ? "%" : request.getParameter("cust_id");
        	String sup_id = EMPTY.equals(request.getParameter("sup_id")) ? "%" : request.getParameter("sup_id");
        	
			data = conn.getDataMap(cpd.getClaimProductDetailStm(startDate, endDate, cust_id, sup_id, request.getParameter("claim_type")));
		}else if (request.getParameter("report_name").equals("benefit_product")) {
			sellProductDAO = new SellProductDAO();
			fileName = "Benefit_Product_" + JDate.getDate() + JDate.getTime();
        	
        	String cust_id = EMPTY.equals(request.getParameter("cust_id")) ? "%" : request.getParameter("cust_id");
        	String emp_id = EMPTY.equals(request.getParameter("emp_id")) ? "%" : request.getParameter("emp_id");
        	String part_no = EMPTY.equals(request.getParameter("part_no")) ? "%" : request.getParameter("part_no");
        	
        	String start_date = EMPTY.equals(request.getParameter("start_date")) ? "00000000" : request.getParameter("start_date");
        	String end_date = EMPTY.equals(request.getParameter("end_date")) ? "99999999" : request.getParameter("end_date");
			
        	// demo sql display
        	data = conn.getDataMap(sellProductDAO.getBenefitStm(start_date, end_date, emp_id, cust_id, part_no));
		}else if (request.getParameter("report_name").equals("supplier_billing")) {
			bpd = new BuyProductDAO();
			fileName = "Supplier_Billing_"+JDate.getDate()+JDate.getTime();
			
			data = conn.getDataMap(bpd.getSupplierBillingStm(request.getParameter("sup_id"), JDate.saveDate(request.getParameter("start_date")), JDate.saveDate(request.getParameter("end_date"))));
		}
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Billing Data");

		// Cell Style
		//XSSFCellStyle style = workbook.createCellStyle();
		//XSSFDataFormat format = workbook.createDataFormat();
		//style.setDataFormat(format.getFormat("#,##0.00"));			

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (int i = 0; i < objArr.length; i++) {
				Cell cell = row.createCell(cellnum++);
				//if (rownum != 0 && (i != 0 && cellnum == 6 || cellnum == 7 || cellnum == 8 || cellnum == 9 || cellnum == 11)) {
				//	cell.setCellStyle(style);
				//	String val = (String) objArr[i];
				//	cell.setCellValue(new Double(val));
				//}else {	
					cell.setCellValue((String) objArr[i]);
				//}
				
			}
			rownum++;
		}
		try (ServletOutputStream sos = response.getOutputStream()) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".xlsx");
			workbook.write(sos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
