<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายการซื้อสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/buy_form_search.js"></script>
</head>
<body>
	<form role="form" id="buy_form_search" method="post" action="buy_form_main.jsp" target="buy_form_main">
	
	<!-- hidden form value -->
    <input id="temp_invoice_no" name="temp_invoice_no" type="text" class="hidden" />
    <input id="mode" name="mode" type="text" class="hidden" />
	
	<div class="container">
	<p>
		<div class="panel panel-primary">
			<div class="panel-heading">รายการซื้อสินค้า</div>
			<div class="panel-body form-horizontal">
			
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
			
				<!-- Row#01 -->
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left"><span class="glyphicon glyphicon-circle-arrow-left"></span> กลับหน้าหลัก</button>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_view" name="btn_view" class="btn btn-info pull-right" onclick="loadDataTable()"><span class="glyphicon glyphicon-refresh"></span> เรียกดูใหม่</button>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่มรายการซื้อสินค้า</button>
					</div>
					<div class="col-xs-6 col-sm-3"></div>
				</div>
				<!-- End Row#01 -->
								
				<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดรายการซื้อสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="buy_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
			            	<th width="15%">เลขที่ใบสั่งสินค้าชั่วคราว</th>
				            <th width="15%">เลขที่ใบสั่งสินค้า</th><th width="15%">วันที่ใบสั่งสินค้า</th><th width="10%">รหัสร้านค้า</th><th width="30%">ชื่อร้านค้า</th>
				            <th width="15%">จำนวนเงิน/บิล</th><th>create_date</th>
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