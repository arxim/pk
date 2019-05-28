<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>หน้าบันทึกซื้อ/รับสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script type="text/javascript">	
	var mode = '${param.mode}';
	var invoiceNo = '${param.invoice_no}';
	var tempInvoiceNo = '${param.temp_invoice_no}';
</script>
<script src="jsform/his_buy_form_main.js"></script>
</head>
<body>	
	<form role="form" id="his_buy_form_main" name="his_buy_form_main">
	<!-- hidden form value -->
    <input id="item_no" name="item_no" type="text" class="hidden" />
    <input id="mode" name="mode" type="text" class="hidden" />

	<div class="container">
	<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">รายการซื้อ/รับเข้าสินค้า</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">เลขที่ใบซื้อชั่วคราว</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="temp_invoice_no" name="temp_invoice_no" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่บันทึกซื้อสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="temp_invoice_date" name="temp_invoice_date" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy" readonly="readonly"> 
					</div>
				</div>
				<!-- End Row -->				

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">เลขที่ใบซื้อสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="invoice_no" name="invoice_no" class="form-control" readonly="readonly">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่ใบซื้อสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
							<input type="text" id="invoice_date" name="invoice_date" data-provide="datepicker"  class="form-control datepicker" placeholder="dd/mm/yyyy" readonly="readonly"> 
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">รหัสร้านค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="sup_id" name="sup_id" class="form-control" readonly="readonly"> 
					</div>
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="sup_name" name="sup_name" class="form-control" placeholder="Description ..." readonly="readonly">
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ที่อยู่ร้านค้า</label>
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
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เบอร์มือถือ</label></div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="mobile" name="mobile" class="form-control" readonly="readonly">
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ภาษีมูลค่าเพิ่ม</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="tax_rate" name="tax_rate" class="form-control" disabled="disabled">
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
							<input type="text" id="payment_term" name="payment_term" class="form-control" readonly="readonly">
							<span class="input-group-addon">วัน</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">วันชำระเงิน</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="payment_date" data-provide="datepicker" class="form-control datepicker" placeholder="dd/mm/yyyy" readonly="readonly"> 
					</div>
				</div>
				<!-- End Row -->
								
				<div class="row form-group">
					<div class="col-xs-6 col-sm-2">
						<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)"><span class="glyphicon glyphicon-circle-arrow-left"></span> กลับหน้าหลัก</button>
					</div>
				</div>
				<!-- End Row -->
								
				<div class="panel panel-info">
				<div class="panel-heading">รายละเอียดรายการซื้อสินค้า</div>
				<div class="panel-body form-horizontal">
				    <table id="buy_main_table" class="display responsive" style="width: 100%">
			        <thead>
			            <tr>
				            <th>ลำดับ</th><th>Part No.</th><th>ชื่อสินค้า</th><th>จำนวน</th>
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