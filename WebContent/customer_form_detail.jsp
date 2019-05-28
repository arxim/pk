<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ข้อมูลลูกค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script type="text/javascript">	
	var mode = '${param.mode}';
	var custId = '${param.cust_id}';
</script>
<script src="jsform/customer_form_detail.js" type="text/javascript"></script>
</head>
<body>
	<form role="form" id="customer_form">
	
	<!-- hidden form value -->
    <input id="mode" name="mode" type="text" class="hidden"/>
	
	<div class="container"><p>
		<div class="panel panel-primary">
			<div class="panel-heading">รายละเอียดข้อมูลลูกค้า</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">รหัสลูกค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="cust_id" name="cust_id" class="form-control" readonly="readonly"> 
					</div>			
					<div class="col-xs-6 col-sm-3">
						<select id="invoice_type" name="invoice_type" class="form-control">
							<option value="">เลือกประเภทชำระเงิน</option>
							<option value="1">บิลเครดิต</option>
							<option value="2">บิลเงินสด</option>
						</select>
					</div>
					
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="tax_id" name="tax_id" class="form-control"> 
							<span class="input-group-addon">Tax ID</span>
						</div>
					
					</div>			
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">ชื่อลูกค้า</label>
					</div>
					<div class="col-xs-6 col-sm-9">
		  				<input type="text" id="cust_name" name="cust_name" class="form-control"/>
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ที่อยู่ลูกค้า</label>
					</div>
					<div class="col-xs-6 col-sm-9">
						<input type="text" id="address1" name="address1" class="form-control">
					</div>
				</div>
				<!-- End Row -->
								
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label "></label>
					</div>
					<div class="col-xs-6 col-sm-9">
						<input type="text" id="address2" name="address2" class="form-control">
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">เบอร์โทรศัพท์</label></div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="telephone" name="telephone" class="form-control" placeholder="Telephone">
							<span class="input-group-addon"><span class="glyphicon glyphicon-phone-alt" aria-hidden="true"></span></span>
						</div>
					</div>			
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">จำกัดจำนวนเงินซื้อสินค้า</label></div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="cr_limit_amount" name="cr_limit_amount" class="form-control">
							<span class="input-group-addon"><span class="glyphicon glyphicon-usd"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">โทรศัพท์มือถือ</label></div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="mobile" name="mobile" class="form-control">
							<span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">แฟกซ์</label></div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="fax" name="fax" class="form-control">
							<span class="input-group-addon"><span class="glyphicon glyphicon-transfer"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนวันเครดิต</label></div>					
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="payment_term" name="payment_term" class="form-control"><span class="input-group-addon">วัน</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ส่วนลด</label></div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="discount_rate" name="discount_rate" class="form-control">
							<span class="input-group-addon"><span >%</span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">พนักงานขาย</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="emp_id" name="emp_id" class="form-control"></select>
					</div>
										
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">สายลูกค้า</label></div>
					<div class="col-xs-6 col-sm-3">
						<select id="area_id" name="area_id" class="form-control"></select>
					</div>			
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">หมายเหตุ</label></div>
					<div class="col-xs-6 col-sm-9">			
    					<input type="text" id="remark" name="remark" class="form-control" placeholder="หมายเหตุ">
  					</div>
				</div>
				<!-- End Row -->				
				
				<div class="row form-group">
				<div class="col-xs-6 col-sm-3">
					<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)"><span class="glyphicon glyphicon-circle-arrow-left"></span> กลับหน้าหลัก</button>
				</div>
				<div class="col-xs-6 col-sm-3">
				</div>
				<div class="col-xs-6 col-sm-3">
					<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left" onclick="newPage()"><span class="glyphicon glyphicon-plus-sign"></span> เริ่มรายการใหม่</button>
				</div>
				<div class="col-xs-6 col-sm-3">
					<button type="button" id="btn_save" name="btn_save" class="btn btn-primary pull-right" onclick="confirmSave(event)"><span class="glyphicon glyphicon-floppy-disk"></span> บันทึกรายการ</button>
					<div id="save-confirm"></div>
				</div>
				</div>
				<!-- End Row -->
			</div>
		</div>
	</div>
	</form>
</body>
</html>