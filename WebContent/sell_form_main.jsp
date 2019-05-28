<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>หน้าบันทึกขายสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script type="text/javascript">	
	var mode = '${param.mode}';
	var tempInvoiceNo = '${param.temp_invoice_no}';
</script>
<script src="jsform/sell_form_main.js"></script>
</head>
<body>
	<form id="report" name="report" method="post">
		<input id="rptTempInvoiceNo" name="rptTempInvoiceNo" type="hidden" />
		<input id="invoiceType" name="invoiceType" type="hidden" />
		<input id="taxType" name="taxType" type="hidden" />
		<input id="company" name="company" type="hidden" />
		<input id="remark" name="remark" type="hidden" />
	</form>

	<form role="form" id="sell_form_main" name="sell_form_main" method="post" action="sell_form_detail.jsp" target="sell_form_detail">
	<!-- hidden form value -->
    <input id="item_no" name="item_no" type="text" class="hidden" />
    <input id="mode" name="mode" type="text" class="hidden" />

	<div class="container">
	<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">รายการขายสินค้า</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">เลขที่ใบขายสินค้าชั่วคราว</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="temp_invoice_no" name="temp_invoice_no" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่บันทึกขายสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="temp_invoice_date" name="temp_invoice_date" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">เลขที่ใบขายสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="invoice_no" name="invoice_no" class="form-control">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่ขายสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="invoice_date" name="invoice_date" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy"> 
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">รหัสลูกค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="cust_id" name="cust_id" class="form-control" onkeypress="custKeyPress(event)"> 
					</div>
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="cust_name" name="cust_name" class="form-control" readonly="readonly">
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ที่อยู่ลูกค้า</label>
					</div>
					<div class="col-xs-6 col-sm-9">
						<input type="text" id="address1" name="address1" class="form-control" readonly="readonly">
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เบอร์โทรศัพท์</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="telephone" name="telephone" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">พนักงานขาย</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="emp_id" name="emp_id" class="form-control">
							<option value="0">--- พนักงานขาย ---</option>
						</select>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ภาษีมูลค่าเพิ่ม</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="tax_rate" name="tax_rate" class="form-control">
							<option value="0">--- อัตราภาษี ---</option>
							<option value="7">7%</option>
							<option value="10">10%</option>
						</select>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนเงินภาษี</label></div>				
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
				  		<input type="text" id="total_tax_amount" name="total_tax_amount" class="form-control" readonly="readonly"/>
  						<span class="input-group-addon">บาท</span>
						</div>
					</div>
				</div>
				<!-- End Row -->
								
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนวันเครดิต</label></div>					
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="payment_term" name="payment_term" class="form-control">
							<span class="input-group-addon">วัน</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">วันเรียกเก็บเงิน</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="payment_date" data-provide="datepicker"  class="form-control datepicker"> 
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ประเภทบิล</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="invoice_type" name="invoice_type" class="form-control">
							<option value="1">บิลเครดิต</option>
							<option value="2">บิลเงินสด</option>
							<option value="3">บิลภาษีมูลค่าเพิ่ม</option>
						</select>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ชื่อร้าน</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="companyName" name="companyName" class="form-control">
							<option value="PK Auto">PK Auto</option>
							<option value="SPK">SPK</option>
							<option value="KAI">KAI</option>
							<option value="CZ Autopart">CZ Autopart</option>
						</select>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนเงินรวม ก่อนลด/VAT</label></div>					
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="total_amount_bef" name="total_amount_bef" class="form-control" readonly="readonly">
							<span class="input-group-addon">บาท</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนเงินรวม หลังลด/VAT</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="total_amount_aft" name="total_amount_aft" class="form-control" readonly="readonly">
							<span class="input-group-addon">บาท</span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">หมายเหตุ</label></div>
					<div class="col-xs-6 col-sm-9">			
    					<input type="text" id="note" name="note" class="form-control" readonly="readonly">
  					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)"><span class="glyphicon glyphicon-circle-arrow-left"></span> กลับหน้าหลัก</button>
					</div>
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_delete" name="btn_delete" class="btn btn-danger pull-right" onclick="confirmDelete(event)"><span class="glyphicon glyphicon-remove-sign"></span> ลบรายการ</button>
						<div id="delete-confirm"></div>
					</div>
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_view" name="btn_view" class="btn btn-info pull-right" onclick="loadDataTable()"><span class="glyphicon glyphicon-refresh"></span> เรียกดูใหม่</button>
					</div>
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_report" name="btn_report" class="btn btn-warning pull-right"><span class="glyphicon glyphicon-print"></span> พิมพ์บิล</button>
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
						<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-right" onclick="openFormDetail(event)"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่มรายการ</button>
					</div>
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_save" name="btn_save" class="btn btn-primary pull-right" onclick="confirmSave(event)"><span class="glyphicon glyphicon-floppy-disk"></span> บันทึกบิล</button>
						<div id="save-confirm"></div>
					</div>
				</div>
				<!-- End Row -->
								
				<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดรายการขายสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="sell_main_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
				            <th>ลำดับ</th><th>รหัสสินค้า</th><th>ชื่อสินค้า</th><th>จำนวน</th>
				            <th>หน่วย</th><th>ราคา/หน่วย</th><th>ส่วนลด%</th><th>ส่วนลดบาท</th><th>ราคาสุทธิ</th>
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