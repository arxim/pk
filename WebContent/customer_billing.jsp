<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายการวางบิลขายสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/customer_billing.js"></script>
<style>
	table thead th { text-align: right; }
	.right{ text-align: right;}
	.left{ text-align: left; }
	.center{ text-align: center; }
</style>
</head>
<body>
	<form role="form" id="customer_billing" name="customer_billing">
	<input id="invoiceType" name="invoiceType" type="hidden" value="billing"/>
	<input id="employee" name="employee" type="hidden"/>
	<input id="invoice_type_h" name="invoice_type_h" type="hidden"/>
	<input id="report_name" name="report_name" type="hidden" value="customer_billing"/>
	<input id="remark" name="remark" type="hidden" />

	<div class="container">
	<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">รายการวางบิลขายสินค้า</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">พนักงานขาย</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="emp_id" name="emp_id" class="form-control">
							<option value="0">--- พนักงานขาย ---</option>
						</select>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ประเภทบิล</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="invoice_type" name="invoice_type" class="form-control">
							<option value="%">ทุกประเภท</option>
							<option value="1">บิลเครดิต</option>
							<option value="2">บิลเงินสด</option>
						</select>
					</div>
				</div>
				<!-- End Row -->
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่ซื้อสินค้า</label></div>
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
				<!--  End Row -->
				<hr>

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนบิลทั้งสิ้น</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="total_invoice" name="total_invoice" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">รวมยอดขายทั้งสิ้น</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="total_amount" name="total_amount" class="form-control" readonly="readonly">
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนเงินหักลดหนี้</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="total_cr" name="total_cr" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">รวมยอดขายหลังลดหนี้</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="total_net_amount" name="total_net_amount" class="form-control" readonly="readonly">
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
						<button type="button" id="btn_report" name="btn_report" class="btn btn-success pull-left" onclick="viewReport(event)"><span class="glyphicon glyphicon-print"></span> เรียกดูรายงาน</button>
					</div>
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_print" name="btn_print" class="btn btn-success pull-left"><span class="glyphicon glyphicon-print"></span> พิมพ์ใบวางบิล</button>
						<!-- dialog -->
						<div id="dialog-remark" title="เพิ่มหมายเหตุ" style="overflow: hidden; padding-top: 2em;">
							<div class="form-horizontal">
							  <div class="form-group">
							    <label class="control-label col-xs-6 col-sm-2" for="txtRemark">หมายเหตุ:</label>
							    <div class="col-xs-6 col-sm-10"> 
							      <textarea id="txtRemark" rows="3" class="form-control" placeholder="หมายเหตุ ..." style="resize: none;"></textarea>
							    </div>
							  </div>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_export" name="btn_export" class="btn btn-default pull-left" onclick="getExcel()"><span class="glyphicon glyphicon-floppy-save"></span> สร้างไฟล์ข้อมูล</button>
					</div>
				</div>
				<!-- End Row -->

				<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดรายการวางบิลขายสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="billing_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
				            <th>ลำดับ</th>
				            <th>ชื่อร้านค้า</th>
				            <th>เลขที่ใบขายสินค้าชั่วคราว</th>
				            <th>เลขที่ใบขายสินค้า</th>
				            <th>วันที่ขายสินค้า</th>
				            <th>จำนวนรายการ/บิล</th>
				            <th>ยอดรวมของบิล</th>
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