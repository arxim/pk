<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ข้อมูลสินค้าขายดี</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/product_best_seller.js"></script>
</head>
<body>
	<form role="form" id="product_best_seller" name="product_best_seller" method="post">
	
	<!-- hidden form value -->
    <input id="role" name="role" type="text" value="<%= session.getAttribute("ROLE") %>" class="hidden" />
	<input id="report_name" name="report_name" type="hidden" value="product_best_seller"/>
	
	<!-- add product_category for filter -->
	<input id="product_category" name="product_category" type="hidden"/>
	
	<div class="container">
	<div class="row form-group"></div>
	<div class="panel panel-primary">
		<div class="panel-heading">ข้อมูลสินค้าขายดี</div>
		<div class="panel-body form-horizontal">
			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ปีเริ่มต้น</label></div>
				<div class="col-xs-6 col-sm-3">
						<input type="text" id="year_start" name="year_start" class="form-control" placeholder="ค.ศ. เช่น 2018"> 
				</div>
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เดือนเริ่มต้น</label></div>
				<div class="col-xs-6 col-sm-3">
					<select class="form-control" id="dwl_month_start">
						<option value="01">มกราคม</option>
						<option value="02">กุมภาพันธ์</option>
						<option value="03">มีนาคม</option>
						<option value="04">เมษายน</option>
						<option value="05">พฤษภาคม</option>
						<option value="06">มิถุนายน</option>
						<option value="07">กรกฎาคม</option>
						<option value="08">สิงหาคม</option>
						<option value="09">กันยายน</option>
						<option value="10">ตุลาคม</option>
						<option value="11">พฤศจิกายน</option>
						<option value="12">ธันวาคม</option>
					</select> 
				</div>
			</div>
			<!-- End Row -->
			
			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ปีสิ้นสุด</label></div>
				<div class="col-xs-6 col-sm-3">
						<input type="text" id="year_end" name="year_end" class="form-control" placeholder="ค.ศ. เช่น 2018"> 
				</div>
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เดือนสิ้นสุด</label></div>
				<div class="col-xs-6 col-sm-3">
					<select class="form-control" id="dwl_month_end">
						<option value="01">มกราคม</option>
						<option value="02">กุมภาพันธ์</option>
						<option value="03">มีนาคม</option>
						<option value="04">เมษายน</option>
						<option value="05">พฤษภาคม</option>
						<option value="06">มิถุนายน</option>
						<option value="07">กรกฎาคม</option>
						<option value="08">สิงหาคม</option>
						<option value="09">กันยายน</option>
						<option value="10">ตุลาคม</option>
						<option value="11">พฤศจิกายน</option>
						<option value="12">ธันวาคม</option>
					</select> 
				</div>
			</div>
			<!-- End Row -->
			
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
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ชนิดสินค้า</label></div>
				<div class="col-xs-6 col-sm-3">
					<select class="form-control" id="dwl_product_category">
						<option value="">-- เลือกชนิดสินค้า --</option>
						<option value="1">นำเข้า/อื่นๆ</option>
						<option value="2">แท้</option>
						<option value="3">ของแท้</option>
					</select> 
					
				</div>
				<div class="col-xs-6 col-sm-3">
					<select class="form-control" id="stock">
						<option value="%">-- เลือกจัดเก็บสินค้า --</option>
						<option value="Y">จัดเก็บ </option>
						<option value="N">ไม่จัดเก็บ </option>
						<option value="X">โป้วส่ง </option>
					</select>
				</div>					
				<div class="col-xs-6 col-sm-3">
					<select class="form-control" id="dwl_order_status">
						<option value="">-- ทุกสถานะการสั่งซื้อ --</option>
						<option value="ORDER">สั่งซื้อ</option>
						<option value="HOLD">ไม่สั่งซื้อ</option>
					</select> 					
				</div>
			</div>
			<!-- End Row -->
										
			<div class="row form-group">
				<div class="col-xs-6 col-sm-2">
					<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left"><span class="glyphicon glyphicon-circle-arrow-left"></span> ปิดหน้า</button>
				</div>
				<div class="col-xs-6 col-sm-2"></div>
				<div class="col-xs-6 col-sm-2">
					<button type="button" id="btn_view" name="btn_view" class="btn btn-info pull-right" onclick="loadDataTable()"><span class="glyphicon glyphicon-refresh"></span> เรียกดูใหม่</button>
				</div>
				<div class="col-xs-6 col-sm-2">
					<!-- <button type="button" id="btn_report" name="btn_report" class="btn btn-success pull-left" onclick="viewReport(event)"><span class="glyphicon glyphicon-print"></span> เรียกดูรายงาน</button> -->
				</div>
				<div class="col-xs-6 col-sm-2"></div>
				<div class="col-xs-6 col-sm-2">
					<!-- <button type="button" id="btn_export" name="btn_export" class="btn btn-default pull-right" onclick="getExcel()"><span class="glyphicon glyphicon-floppy-save"></span> สร้างไฟล์ข้อมูล</button> -->
				</div>
			</div>
			<!-- End Row -->
			
			<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดการซื้อสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="product_best_seller_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
				            <th>Part No.</th><th>ชื่อสินค้า</th><th>รุ่นรถ</th><th>หน่วยนับ</th>
				            <th>ยี้ห้อรถ</th><th>ยี้ห้อสินค้า</th><th>ชนิดสินค้า</th>
				            <th>ราคาทุน</th><th>คงเหลือ</th><th>ขายเฉลี่ย/เดือน</th><th>สถานะสั่งซื้อ</th>
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