<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายงานผลกำไร</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/benefit_record.js"></script>
<style>
	table thead th { text-align: center; }
	.right{ text-align: right;}
	.left{ text-align: left; }
	.center{ text-align: center; }
</style>
</head>
<body>	
	<!-- dummy form send values to WriteExcelFileSrvl for excel file -->
	<form role="form" id="claim_form_excel">
		<input type="hidden" id="frm_excel_tb" name="tb" value="BENEFIT_PRODUCT"/>
		<input type="hidden" id="frm_excel_state" name="state" value="load" />
		<input type="hidden" id="report_name" name="report_name" value="benefit_product"/>
		
		<input type="hidden" id="frm_excel_cust_id" name="cust_id" />
		<input type="hidden" id="frm_excel_emp_id" name="emp_id" />
		<input type="hidden" id="frm_excel_part_no" name="part_no" />
		<input type="hidden" id="frm_excel_start_date" name="start_date" />
		<input type="hidden" id="frm_excel_end_date" name="end_date" />
	</form>

	<form role="form" id="benefit_record" name="benefit_record">

	<div class="container">
	<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">รายงานผลกำไร</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่ขายสินค้าเริ่มต้น</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="invoice_date_from" name="invoice_date_from" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
					<div class="col-xs-6 col-sm-2 text-right"><label class="control-label ">วันที่ขายสินค้าสิ้นสุด</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="invoice_date_to" name="invoice_date_to" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
				</div>
				<!-- End Row -->
				<hr>
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">ชื่อเซลล์</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="emp_name" name="emp_name" class="form-control" onkeypress="return empKeyPress(event);"> 
					</div>
					<div class="col-xs-2 col-sm-2">
						<input type="hidden" id="emp_id" name="emp_id" />
<!-- 		  				<input type="text" id="sup_id" name="sup_id" class="form-control" placeholder="ID ..." readonly="readonly"> -->
					</div>
				</div>
				<!-- End Row -->
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">ชื่อลูกค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="cust_name" name="cust_name" class="form-control" onkeypress="return custKeyPress(event);"> 
					</div>
					<div class="col-xs-2 col-sm-2">
						<input type="hidden" id="cust_id" name="cust_id" />
<!-- 		  				<input type="text" id="cust_id" name="cust_id" class="form-control" placeholder="ID ..." readonly="readonly"> -->
					</div>
				</div>
				<!-- End Row -->
				<hr>
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">Part No.</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="part_no" name="part_no" class="form-control" onkeypress="return productKeyPress(event);">
					</div>
					
					<div class="col-xs-6 col-sm-5">
		  				<input type="text" id="part_detail" name="part_detail" class="form-control" placeholder="Description ..." readonly="readonly"/>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">จำนวนคงเหลือ</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="prd_remain" name="prd_remain" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-6"></div>
				</div>
				<!-- End Row -->
				<hr>
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ต้นทุน / ยอดขาย / กำไร</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="cost_amount" name="cost_amount" class="form-control" onkeypress="return productKeyPress(event);">
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="sell_amount" name="sell_amount" class="form-control" onkeypress="return productKeyPress(event);">
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="benefit_amount" name="benefit_amount" class="form-control" onkeypress="return productKeyPress(event);">
					</div>
				</div>
				<!-- End Row -->				

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)"><span class="glyphicon glyphicon-circle-arrow-left"></span> กลับหน้าหลัก</button>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_clear" name="btn_clear" class="btn btn-warning pull-right" onclick="clearScreen()"><span class="glyphicon glyphicon-refresh"></span> ล้างหน้าจอ</button>
					</div>
					
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_view" name="btn_view" class="btn btn-info pull-left" onclick="loadDataTable()"><span class="glyphicon glyphicon-refresh"></span> ดูรายละเอียด</button>
					</div>
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_viewsum" name="btn_viewsum" class="btn btn-info pull-left" onclick="loadSumDataTable()"><span class="glyphicon glyphicon-refresh"></span> เรียกดูสรุป</button>
					</div>
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_export" name="btn_export" class="btn btn-default pull-right" onclick="getExcel()"><span class="glyphicon glyphicon-floppy-save"></span> สร้างไฟล์ข้อมูล</button>
					</div>
				</div>
				<!-- End Row -->
								
				<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดประวัติขายสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="benefit_main_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
				            <th>วันที่ออกบิล</th>
				            <th>Part No</th>
				            <th>ทุน</th>
				            <th>ยอดขาย</th>
				            <th>กำไร</th>
				            <th>ชื่อเซลล์</th>
				            <th>ชื่อลูกค้า</th>
			            </tr>
			        </thead>
			    	</table>
				</div>
				</div>
				<!-- End Table -->
			</div>
		</div>
	</div>
	</form>
</body>
</html>