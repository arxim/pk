<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ประวัติซื้อสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/buy_record.js"></script>
<style>
	table tbody tr {
		cursor: pointer;
	}
</style>
</head>
<body>	
	<!-- dummy form send values to his_buy_form_main page -->
	<form role="form" id="form_buy_record" name="buy_record" method="post" action="his_buy_form_main.jsp" target="his_buy_form_main">
		<!-- hidden form value -->
	    <input id="temp_invoice_no" name="temp_invoice_no" type="text" class="hidden" />
	    <input id="mode" name="mode" type="text" class="hidden" />
	</form>

	<form role="form" id="buy_record" name="buy_record">
		<!-- hidden form value -->
    	<input id="role" name="role" type="text" value="<%= session.getAttribute("ROLE") %>" class="hidden" />
	
	<!-- hidden form value excel -->
	<input id="tb" name="tb" type="hidden" value="BUY_PRODUCT"/>
	<input id="state" name="state" type="hidden" value="record"/>
	<input id="report_name" name="report_name" type="hidden" value="buy_product"/>
	
	<!-- date on search query statement - dynamic value from selected date picker -->
	<input id="start_date" name="start_date" type="hidden" />
	<input id="end_date" name="end_date" type="hidden" />
	<input id="temp_start_date" name="temp_start_date" type="hidden" />
	<input id="temp_end_date" name="temp_end_date" type="hidden" />
	
	<div class="container">
	<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">รายการประวัติซื้อสินค้า</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">รหัสร้านค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="sup_id" name="sup_id" class="form-control" onkeypress="return supKeyPress(event);"> 
					</div>
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="sup_name" name="sup_name" class="form-control" placeholder="Description ..." readonly="readonly">
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
					
					<div class="col-xs-6 col-sm-6">
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
						<label class="control-label ">ประเภทสินค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="prd_type_id" name="prd_type_id" class="form-control" onkeypress="return productTypeKeyPress(event);">
					</div>
					
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="prd_type_name" name="prd_type_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
					</div>
				</div>
				
				<!-- End Row -->
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ยี่ห้อสินค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="prd_brand_id" name="prd_brand_id" class="form-control" onkeypress="return productBrandKeyPress(event);">
					</div>
					
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="prd_brand_name" name="prd_brand_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เริ่มวันที่บันทึกซื้อสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="temp_invoice_date_from" name="temp_invoice_date_from" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ถึงวันที่บันทึกซื้อสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="temp_invoice_date_to" name="temp_invoice_date_to" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เริ่มวันที่ซื้อสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="invoice_date_from" name="invoice_date_from" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ถึงวันที่ซื้อสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="invoice_date_to" name="invoice_date_to" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"></div>
					<div class="col-xs-6 col-sm-3">
						<label>จำนวนซื้อรวม</label>
    					<input type="text" id="sum_quantity" name="sum_quantity" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-3">
						<label>เป็นเงิน</label>
    					<input type="text" id="sum_amount" name="sum_amount" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-3">
						<label>เป็นเงินสุทธิ</label>
    					<input type="text" id="sum_net_amount" name="sum_net_amount" class="form-control" readonly="readonly">
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
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_view" name="btn_view" class="btn btn-info pull-left" onclick="loadDataTable()"><span class="glyphicon glyphicon-refresh"></span> เรียกดูใหม่</button>
					</div>
					<div class="col-xs-6 col-sm-3">
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_export" name="btn_export" class="btn btn-default pull-right" onclick="getExcel()"><span class="glyphicon glyphicon-floppy-save"></span> สร้างไฟล์ข้อมูล</button>
					</div>
				</div>
				<!-- End Row -->
								
				<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดประวัติซื้อสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="buy_main_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
				            <th>วันที่บันทึกซื้อ</th><th>วันที่ซื้อ</th><th>ร้านค้า</th><th>Part No.</th><th>ชื่อสินค้า</th>
				            <th>รุ่นรถ</th><th>ยี้ห้อสินค้า</th><th>จำนวนซื้อ</th><th>ราคา/หน่วย</th><th>เป็นเงิน</th>
				            <th>ส่วนลด%</th><th>ส่วนลดบาท</th><th>ภาษี</th><th>ราคาสุทธิ/หน่วย</th><th>ราคารวมสุทธิ</th>
				            <th>ชนิดสินค้า</th><th>ประเภทสินค้า</th><th>ใบซื้อชั่วคราว</th>
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