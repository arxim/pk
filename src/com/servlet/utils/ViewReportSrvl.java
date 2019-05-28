package com.servlet.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;

import com.beans.utils.DBConn;
import com.beans.utils.JDate;

@WebServlet("/ViewReportSrvl")
public class ViewReportSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ViewReportSrvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
    private void doProcess(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        String reportfilename = "dfpayment.jasper";       
        String year = request.getParameter("YYYY");
        String month = request.getParameter("MM");
        String doctor = request.getParameter("DOCTOR_CODE");
        hm.put("doctor", doctor);
        hm.put("month", month);
        hm.put("year", year);
        this.reportGenerateView(hm, reportfilename, response , request);
    }
    private void reportGenerateView(HashMap<String, Object> hashM, String report_source_file, HttpServletResponse response , HttpServletRequest request){
    	byte[] b = null;
        DBConn cdb = new DBConn();
        File reportFile = null;
        String temp_invoice_no = "";
        String companyName = "";
        String remark = "";
        if(request.getParameter("invoiceType").equals("billing")){
        	System.out.println("Test:"+request.getParameter("employee")+" type:"+request.getParameter("invoiceType")+
        	" Tax Rate:"+request.getParameter("tax_rate")+
        	" Employee:"+request.getParameter("emp_id")+
        	" Start Date"+request.getParameter("invoice_date_from")+
        	" End Date"+request.getParameter("invoice_date_to"));
        	reportFile = new File(this.getServletConfig().getServletContext().getRealPath("/data/"+request.getParameter("report_name")+".jasper"));        	
            try{
            	remark = new String(request.getParameter("remark").getBytes("ISO-8859-1"), "UTF-8");
            }catch(Exception e){
            	System.out.println(e);
            }
        	hashM.put("note", remark);
        	hashM.put("invoice_type", request.getParameter("invoice_type_h"));
            hashM.put("emp_id",request.getParameter("employee").equals("") ? "%" : request.getParameter("employee"));
            hashM.put("cust_id",request.getParameter("cust_id").equals("") ? "%" : request.getParameter("cust_id"));
            hashM.put("start_date",request.getParameter("invoice_date_from").equals("") ? "00000000" : JDate.saveDate(request.getParameter("invoice_date_from")));
            hashM.put("end_date",request.getParameter("invoice_date_to").equals("") ? "99999999" : JDate.saveDate(request.getParameter("invoice_date_to")));
        }else if(request.getParameter("invoiceType").equals("supplier_billing")){
        	reportFile = new File(this.getServletConfig().getServletContext().getRealPath("/data/"+request.getParameter("report_name")+".jasper"));        	
            try{
            	remark = new String(request.getParameter("remark").getBytes("ISO-8859-1"), "UTF-8");
            }catch(Exception e){
            	System.out.println(e);
            }
        	hashM.put("note", remark);
            hashM.put("sup_id",request.getParameter("sup_id").equals("") ? "%" : request.getParameter("sup_id"));
            hashM.put("start_date",request.getParameter("invoice_date_from").equals("") ? "00000000" : JDate.saveDate(request.getParameter("invoice_date_from")));
            hashM.put("end_date",request.getParameter("invoice_date_to").equals("") ? "99999999" : JDate.saveDate(request.getParameter("invoice_date_to")));
        }else if(request.getParameter("invoiceType").equals("1")){
        	reportFile = new File(this.getServletConfig().getServletContext().getRealPath("/data/sellProductCredit.jasper"));
            temp_invoice_no = request.getParameter("rptTempInvoiceNo").toString();
            companyName = request.getParameter("company").toString();
            try{
            	remark = new String(request.getParameter("remark").getBytes("ISO-8859-1"), "UTF-8");
            }catch(Exception e){
            	System.out.println(e);
            }
            hashM.put("temp_invoice_no",temp_invoice_no);
            hashM.put("company", companyName);
            hashM.put("remark", remark);
            this.printInvoice(temp_invoice_no);
        }else if(request.getParameter("invoiceType").equals("2")){
        	reportFile = new File(this.getServletConfig().getServletContext().getRealPath("/data/sellProductCash.jasper"));        	
            temp_invoice_no = request.getParameter("rptTempInvoiceNo").toString();
            companyName = request.getParameter("company").toString();
            try{
            	remark = new String(request.getParameter("remark").getBytes("ISO-8859-1"), "UTF-8");
            }catch(Exception e){
            	System.out.println(e);
            }
            hashM.put("temp_invoice_no",temp_invoice_no);
            hashM.put("company", companyName);
            hashM.put("remark", remark);
            this.printInvoice(temp_invoice_no);
        }else{
        	reportFile = new File(this.getServletConfig().getServletContext().getRealPath("/data/sellProductTax.jasper"));        		        		
            temp_invoice_no = request.getParameter("rptTempInvoiceNo").toString();
            companyName = request.getParameter("company").toString();
            try{
            	remark = new String(request.getParameter("remark").getBytes("ISO-8859-1"), "UTF-8");
            }catch(Exception e){
            	System.out.println(e);
            }
            hashM.put("temp_invoice_no",temp_invoice_no);
            hashM.put("company", companyName);
            hashM.put("remark", remark);
            this.printInvoice(temp_invoice_no);
        }

        try {
            response.setContentType("application/pdf");
            System.out.println(hashM);
            b = JasperRunManager.runReportToPdf(reportFile.getPath(), hashM, cdb.getConnection());
            if (b != null && b.length > 0){
                response.setContentLength(b.length);
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(b, 0, b.length);
                ouputStream.flush();
                ouputStream.close();
            }else{
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<body>");
                out.println("Report is empty");
                out.println("</body>");
            }
        }catch(Exception e){
        	System.out.println(e);
			PrintWriter out = null;
            try {
                out = response.getWriter();
                response.setContentType("text/html");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>View Report Servlet</title>");  
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Report Error</h1>");
                out.println("Report Path :" + reportFile.getPath()+"<br>");
                out.println("Cause : "+e);
                out.println("</body>");
                out.println("</html>");
            } catch (IOException ex) {
            } 
        }finally{ 
        	cdb.doDisconnect();
        }
    }
	public boolean printInvoice(String tempInvoiceNo){
        DBConn conn = new DBConn();
		String sql = "";
		boolean status = false;
    	sql = "UPDATE SELL_PRODUCT SET PRINTED_DATE = '"+JDate.getDate()+"' "+
      		  "WHERE TEMP_INVOICE_NO = '"+tempInvoiceNo+"'";
		conn.doConnect();
		if(conn.doSave(sql)){
			status = conn.doCommit();
		}else{
			status = false;
		}
		conn.doDisconnect();
		return status;
	}
}