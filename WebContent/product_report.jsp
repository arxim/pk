<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายงานข้อมูลสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/product_report.js"></script>
</head>
<body>
	<form role="form" id="product_report" name="product_report" method="post">
	
	<!-- hidden form value -->
    <input id="role" name="role" type="text" value="<%= session.getAttribute("ROLE") %>" class="hidden" />
	<input id="report_name" name="report_name" type="hidden" value="product_report"/>
	<input id="h_stock" name="h_stock" type="hidden"/>
	<input id="h_stock_status" name="h_stock_status" type="hidden"/>
	
	<!-- add product_category for filter -->
	<input id="product_category" name="product_category" type="hidden"/>
	
	<div class="container">
	<div class="row form-group"></div>
	<div class="panel panel-primary">
		<div class="panel-heading">รายการข้อมูลสินค้า</div>
		<div class="panel-body form-horizontal">
			<div class="row form-group">	
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนสินค้า</label></div>				
				<div class="col-xs-6 col-sm-3">
					<select class="form-control" id="stock_status">
						<option value="AL">-- แสดงทุกรายการ --</option>
						<option value="EQ">น้อยกว่าหรือเท่ากับจำนวนขั้นต่ำ </option>
						<option value="ZE">สินค้าเป็น 0 </option>
						<option value="NE">จำนวนติดลบ </option>
					</select>
				</div>
				<div class="col-xs-6 col-sm-3 text-right">
					<label class="control-label ">จัดเก็บสินค้า</label>
				</div>				
				<div class="col-xs-6 col-sm-3">
					<select class="form-control" id="stock">
						<option value="%">-- เลือกจัดเก็บสินค้า --</option>
						<option value="Y">จัดเก็บ </option>
						<option value="N">ไม่จัดเก็บ </option>
						<option value="X">โป้วส่ง </option>
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
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">มูลค่าสต๊อค</label></div>				
				<div class="col-xs-6 col-sm-3">
					<div class="input-group">
						<input type="text" id="prd_onhand" name="prd_onhand" class="form-control" readonly="readonly"> 
 							<span class="input-group-addon">บาท</span>
					</div>
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
					<button type="button" id="btn_export" name="btn_export" class="btn btn-default pull-right" onclick="getExcel()"><span class="glyphicon glyphicon-floppy-save"></span> สร้างไฟล์ข้อมูล</button>
				</div>
			</div>
			<!-- End Row -->
			
			<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดประวัติซื้อสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="product_report_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
				            <th>Part No.</th><th>ชื่อสินค้า</th><th>รุ่นรถ</th><th>หน่วยนับ</th><th>รายละเอียดหน่วยนับ</th>
				            <th>ขนาด</th><th>ยี้ห้อรถ</th><th>ยี้ห้อสินค้า</th><th>ชนิดสินค้า</th><th>สถานะจัดเก็บ</th>
				            <th>ขั้นต่ำ</th><th>คงเหลือ</th><th>ราคาทุน</th>
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