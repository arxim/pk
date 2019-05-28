<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายการขายสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/sell_form_search.js"></script>
</head>
<body>
	<form role="form" id="sell_form_search" method="post" action="sell_form_main.jsp" target="_blank">

	<!-- hidden form value -->
    <input id="temp_invoice_no" name="temp_invoice_no" type="text" class="hidden" />
    <input id="mode" name="mode" type="text" class="hidden" />

	<div class="container">
	<p>
		<div class="panel panel-primary">
			<div class="panel-heading">รายการขายสินค้า</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ประเภทบิล</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="invoice_type" name="invoice_type" class="form-control">
							<option value="%">ทั้งหมด</option>							
							<option value="1">บิลเครดิต</option>
							<option value="2">บิลเงินสด</option>
							<option value="3">บิลภาษีมูลค่าเพิ่ม</option>
						</select>
					</div>
					<div class="col-xs-6 col-sm-3"></div>					
					<div class="col-xs-6 col-sm-3"></div>
				</div>
				<!-- End Row -->
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เริ่มวันที่บันทึกขายสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="temp_invoice_date_from" name="temp_invoice_date_from" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ถึงวันที่บันทึกขายสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="temp_invoice_date_to" name="temp_invoice_date_to" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
				</div>
				<!-- End Row -->
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เริ่มวันที่ขายสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="invoice_date_from" name="invoice_date_from" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ถึงวันที่ขายสินค้า</label></div>
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
						<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่มบิลขายสินค้า</button>
					</div>
					<div class="col-xs-6 col-sm-3"></div>
				</div>
				<!-- End Row#01 -->
								
				<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดรายการขายสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="sell_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
				            <th width="15%">เลขที่ใบขายชั่วคราว</th>
				            <th width="10%">เลขที่ใบขาย</th>
				            <th width="10%">วันที่ขาย</th>
				            <th width="10%">รหัสร้านค้า</th>
				            <th width="20%">ชื่อร้านค้า</th>
				            <th width="10%">จำนวนเงิน/บิล</th>
				            <th width="10%">สายวิ่ง</th>
				            <th width="10%">วันที่พิมพ์</th>
				            <th width="5%">ลำดับ</th>			            
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