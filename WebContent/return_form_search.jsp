<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายการคืนสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/return_form_search.js"></script>
</head>
<body>
	<form role="form" id="return_form_search" method="post" action="return_form_detail.jsp" target="return_form_detail">
	<!-- hidden form value -->
    <input id="item_no" name="item_no" type="text" class="hidden" />
    <input id="doc_no" name="doc_no" type="text" class="hidden" />
    <input id="mode" name="mode" type="text" class="hidden" />
	
	<div class="container">
	<div class="row form-group"></div>
	<div class="panel panel-primary">
	<div class="panel-heading">รายการคืนสินค้า</div>
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
			<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เริ่มวันที่คืนสินค้า</label></div>
			<div class="col-xs-6 col-sm-3">
					<input type="text" id="doc_date_from" name="doc_date_from" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
			</div>
			<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ถึงวันที่คืนสินค้า</label></div>
			<div class="col-xs-6 col-sm-3">
					<input type="text" id="doc_date_to" name="doc_date_to" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
			</div>
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
				<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left" onclick="newReturnProduct()"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่มรายการคืนสินค้า</button>
			</div>
			<div class="col-xs-6 col-sm-3"></div>
		</div>
		<!-- End Row -->

		<div class="panel panel-info">
		<div class="panel-heading">รายละเอียดคืนสินค้า</div>
		<div class="panel-body form-horizontal">
		    <table id="return_table" class="display responsive" style="width: 100%">
	        <thead>
	            <tr>
		            <th>ลำดับ</th><th>เลขที่ใบคืนสินค้า</th><th>วันที่บันทึกคืนสินค้า</th><th>ใบคืนสินค้า(ลูกค้า)</th>
		            <th>Part No.</th><th>ชื่อสินค้า</th><th>จำนวน</th><th>ราคา/หน่วย</th>
		            <th>เป็นเงิน</th><th>วันที่ลดหนี้</th>
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