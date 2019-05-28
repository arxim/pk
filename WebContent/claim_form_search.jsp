<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายการเปลี่ยนสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/claim_form_search.js"></script>
<style>
	table tbody tr { cursor: pointer; }
	.right{ text-align: right;}
	.left{ text-align: left; }
	.center{ text-align: center; }
</style>
</head>
<body>

	<!-- dummy form send values to WriteExcelFileSrvl for excel file -->
	<form role="form" id="claim_form_excel">
		<input type="hidden" id="frm_excel_tb" name="tb" value="CLAIM_PRODUCT"/>
		<input type="hidden" id="frm_excel_state" name="state" value="load" />
		<input id="report_name" name="report_name" type="hidden" value="claim_product"/>
		
		<input type="hidden" id="frm_excel_cust_id" name="cust_id" />
		<input type="hidden" id="frm_excel_sup_id" name="sup_id" />
		
		<input type="hidden" id="frm_excel_start_date" name="start_date" />
		<input type="hidden" id="frm_excel_end_date" name="end_date" />
		
		<input type="hidden" id="frm_excel_claim_type" name="claim_type" />
	</form>

	<form role="form" id="claim_form_search" method="post" action="claim_form_detail.jsp" target="claim_form_detail">
	
	<!-- hidden form value -->
    <input id="item_no" name="item_no" type="text" class="hidden" />
    <input id="doc_no" name="doc_no" type="text" class="hidden" />
    <input id="mode" name="mode" type="text" class="hidden" />
	
	<div class="container">
	<div class="row form-group"></div>
	<div class="panel panel-primary">
	<div class="panel-heading">รายการเปลี่ยนสินค้า</div>
	<div class="panel-body form-horizontal">

		<div class="row form-group">
			<div class="col-xs-6 col-sm-3 text-right">
				<label class="control-label">รหัสลูกค้า</label>
			</div>
			<div class="col-xs-6 col-sm-3">
				<input type="text" id="cust_id" name="cust_id" class="form-control" onkeypress="return custKeyPress(event);"> 
			</div>
			<div class="col-xs-6 col-sm-6">
  				<input type="text" id="cust_name" name="cust_name" class="form-control" placeholder="Description ..." readonly="readonly">
			</div>
		</div>
		<!-- End Row -->

		<div class="row form-group">
			<div class="col-xs-6 col-sm-3 text-right">
				<label class="control-label">รหัสเจ้าหนี้</label>
			</div>
			<div class="col-xs-6 col-sm-3">
				<input type="text" id="sup_id" name="sup_id" class="form-control" onkeypress="return supKeyPress(event);"> 
			</div>
			<div class="col-xs-6 col-sm-6">
  				<input type="text" id="sup_name" name="sup_name" class="form-control" placeholder="Description ..." readonly="readonly">
			</div>
		</div>
		<!-- End Row -->
		
		<div class="row form-group">
			<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เริ่มวันที่รับเคลมสินค้า</label></div>
			<div class="col-xs-6 col-sm-3">
					<input type="text" id="doc_date_from" name="doc_date_from" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
			</div>
			<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ถึงวันที่รับเคลมสินค้า</label></div>
			<div class="col-xs-6 col-sm-3">
					<input type="text" id="doc_date_to" name="doc_date_to" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
			</div>
		</div>
		<!-- End Row -->

		<div class="row form-group">
			<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">สถานะการเคลมสินค้า</label></div>					
			<div class="col-xs-6 col-sm-3">
				<select id="claim_type" name="claim_type" class="form-control">
					<option value="0">--- ทุกสถานะ ---</option>
					<option value="1">ส่งคืนลูกค้า</option>
					<option value="2">ยังไม่ส่งคืนลูกค้า</option>
					<option value="3">ส่งเคลมสินค้า</option>
					<option value="4">รับเคลมจากเจ้าหนี้</option>
				</select>
			</div>
			<div class="col-xs-6 col-sm-3"></div>				
			<div class="col-xs-6 col-sm-3"></div>
		</div>
		<!-- End Row -->

		<div class="row form-group">
			<div class="col-xs-6 col-sm-3">
				<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)"><span class="glyphicon glyphicon-circle-arrow-left"></span> กลับหน้าหลัก</button>
			</div>
			<div class="col-xs-6 col-sm-3">
				<button type="button" id="btn_view" name="btn_view" class="btn btn-info pull-right" onclick="loadDataTable()"><span class="glyphicon glyphicon-refresh"></span> เรียกดูใหม่</button>
			</div>
			<div class="col-xs-6 col-sm-3">
				<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left" onclick="newReturnProduct()"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่มรายการเคลมสินค้า</button>
			</div>
			<div class="col-xs-6 col-sm-offset-1 col-sm-2">
				<button type="button" id="btn_export" name="btn_export" class="btn btn-default pull-left" onclick="getExcel()"><span class="glyphicon glyphicon-floppy-save"></span> สร้างไฟล์ข้อมูล</button>
			</div>
		</div>
		<!-- End Row -->

		<div class="panel panel-info">
		<div class="panel-heading">รายละเอียดเคลมสินค้า</div>
		<div class="panel-body form-horizontal">
		    <table id="claim_table" class="display responsive" style="width: 100%">
	        <thead>
	            <tr>
		            <th>ลำดับ</th>
		            <th>เลขที่ใบเปลี่ยนสินค้า</th>
		            <th>วันที่รับเคลม</th>
		            <th>ชื่อลูกค้า</th>
		            <th>(ลูกค้า)<br/>ได้รับสินค้า</th> <!-- new -->
		            <th>ชื่อเจ้าหนี้</th>
		            <th>ส่งสินค้าเคลม</th> <!-- new -->
		            <th>รับสินค้าเคลมคืน</th> <!-- new -->
		            <th>Part No.</th>
		            <th>ชื่อสินค้า</th>
		            <th>จำนวน</th>
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