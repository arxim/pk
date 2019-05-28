<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายละเอียดขายสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script type="text/javascript">	
	var mode = '${param.mode}';
	var userId = '${param.user_id}';
	var itemNo = '${param.item_no}';
	var tempInvoiceNo = '${param.temp_invoice_no}';
</script>
<script src="jsform/jsUtils.js" type="text/javascript"></script>
<script src="jsform/sell_form_detail.js" type="text/javascript"></script>
</head>
<body>
	<form role="form" id="sell_form_detail" name="sell_form_detail" method="post" action="sell_form_detail.jsp" target="sell_form_detail">
	
	<!-- hidden form value -->
	<input type="text" id="cost" name="cost" class="hidden"/>
    <input type="text" id="mode" name="mode" class="hidden"/>
    <input type="text" id="user_id" name="user_id" class="hidden"/>
    <input type="text" id="tax_amount" name="tax_amount" class="hidden"/>
    <input type="text" id="total_discount_amount" name="total_discount_amount" class="hidden"/>
    <input type="text" id="total_extra_discount_amount" name="total_extra_discount_amount" class="hidden"/>
    <input type="text" id="old_cost" name="old_cost" class="hidden"/>
    <input type="text" id="role" name="role" value="<%= session.getAttribute("ROLE") %>" class="hidden" />
    

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
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่ขายสินค้าชั่วคราว</label></div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="temp_invoice_date" name="temp_invoice_date" class="form-control datepicker" data-provide="datepicker" placeholder="dd/mm/yyyy"> 
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
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
						<div class="input-group">
							<input type="text" id="invoice_date" name="invoice_date" class="form-control datepicker" data-provide="datepicker" placeholder="dd/mm/yyyy" onchange="getPaymentDate();"> 
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">รหัสลูกค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="cust_id" name="cust_id" class="form-control" onkeypress="return custKeyPress(event);"> 
							<span class="input-group-addon"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></span>
						</div>
					</div>
					<div class="col-xs-6"></div>
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="cust_name" name="cust_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ที่อยู่ลูกค้า</label>
					</div>
					<div class="col-xs-6 col-sm-9">
						<input type="text" id="cust_addr" name="cust_addr" class="form-control" readonly="readonly">
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
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนวันเครดิต</label></div>					
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="payment_term" name="payment_term" class="form-control" readonly="readonly">
							<span class="input-group-addon">วัน</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">วันเรียกเก็บเงิน</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="payment_date" name="payment_date" class="form-control datepicker" data-provide="datepicker" placeholder="dd/mm/yyyy"> 
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ภาษีมูลค่าเพิ่ม</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="tax_rate" name="tax_rate" class="form-control" onchange="calTotalNetAmount()">
							<option value="0">--- อัตราภาษี ---</option>
							<option value="7">7%</option>
							<!-- <option value="10">10%</option> -->
						</select>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ประเภทบิล</label></div>					
					<div class="col-xs-6 col-sm-3">
						<select id="invoice_type" name="invoice_type" class="form-control">
							<option value="1">บิลเครดิต</option>
							<option value="2">บิลเงินสด</option>
							<option value="3">บิลภาษีมูลค่าเพิ่ม</option>
						</select>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">หมายเหตุ</label></div>
					<div class="col-xs-6 col-sm-9">			
    					<input type="text" id="remark" name="remark" class="form-control" readonly="readonly">
  					</div>
				</div>
				<!-- End Row -->
								
				<ul class="breadcrumb"><li class="active">รายละเอียดการขายสินค้า</li></ul>
				
				<div class="row form-group">	
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่ขาย/ส่วนลด/ราคาขาย ล่าสุด</label></div>				
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="last_sell_date" name="last_sell_date" class="form-control datepicker" data-provide="datepicker" placeholder="dd/mm/yyyy" disabled="disabled"> 
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
				  		<input type="text" id="last_discount_price" name="last_discount_price" class="form-control" readonly="readonly"/>
  						<span class="input-group-addon">บาท</span>
						</div>
					</div>
					<!-- <div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ราคาขายล่าสุด</label></div> -->
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
				  		<input type="text" id="last_sell_price" name="last_sell_price" class="form-control" readonly="readonly"/>
  						<span class="input-group-addon">บาท</span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ลำดับรายการ</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="item_no" name="item_no" class="form-control" />
								<span class="input-group-addon" id="basic-addon2">
								<span class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span>
								</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-6">
						<div class="input-group">
				  		<input type="text" id="create_date" name="create_date" class="form-control" placeholder="Create Date..." readonly="readonly"/>
  						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				  		<input type="text" id="create_time" name="create_time" class="form-control" placeholder="Create Time..." readonly="readonly"/>
				  		<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">Part No.</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="part_no" name="part_no" class="form-control" onkeypress="return productKeyPress(event);" required="required">
							<span class="input-group-addon" id="basic-addon2">
								<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
							</span>
							<div id="dialog-verify-partno">ตรวจสอบพบ Part No ในเลขที่ใบขายสินค้านี้</div>
							<div id="dialog-product-remain">
								<div>สินค้าคงเหลือ <span id="lb_product_remain"></span> ชุด</div>
							</div>
						</div>
					</div>
					
					<div class="col-xs-6 col-sm-6">
						<div class="input-group">
		  					<input type="text" id="prd_name" name="prd_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
		  					<span class="input-group-addon"><span class="glyphicon glyphicon glyphicon-tag"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ราคาสินค้า/หน่วย</label></div>					
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="amount" name="amount" class="form-control" onchange="calTotalNetAmount()" />
  							<span class="input-group-addon">บาท</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
				  		<input type="text" id="prd_quantity" name="prd_quantity" class="form-control" placeholder="จำนวนสินค้า" onchange="calTotalNetAmount()" required="required"/>
  						<span class="input-group-addon">@</span>
				  		<input type="text" id="unit_name" name="unit_name" class="form-control" placeholder="หน่วยนับ" readonly="readonly"/>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3">
				  		<input type="text" id="unit_type_name" name="unit_type_name" class="form-control" readonly="readonly"/>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">	
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ราคาทุน</label></div>				
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
					  		<input type="text" id="real_cost" name="real_cost" class="form-control" readonly="readonly"/>
	  						<span class="input-group-addon">บาท</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"></div>				
					<div class="col-xs-6 col-sm-3"></div>
				</div>
				<!-- End Row -->
				
				
				<div class="row form-group">	
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ราคาทุนมาตรฐาน</label></div>				
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
					  		<input type="text" id="std_cost" name="std_cost" class="form-control" readonly="readonly"/>
	  						<span class="input-group-addon">บาท</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">เป็นเงิน</label></div>				
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="total_amount" name="total_amount" class="form-control" readonly="readonly"> 
  							<span class="input-group-addon">บาท</span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ส่วนลดต่อหน่วย</label></div>					
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
				  		<input type="text" id="discount_rate" name="discount_rate" class="form-control" placeholder="ส่วนลด" onchange="calTotalNetAmount()"/>
  						<span class="input-group-addon">%</span>
				  		<input type="text" id="discount_amount" name="discount_amount" class="form-control" placeholder="เป็นเงิน" onchange="calTotalNetAmount()"/>
	  					<span class="input-group-addon">บาท</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ส่วนลดเพิ่มต่อหน่วย</label></div>					
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
				  		<input type="text" id="extra_discount_rate" name="extra_discount_rate" class="form-control" placeholder="ส่วนลดเพิ่ม" onchange="calTotalNetAmount()"/>
  						<span class="input-group-addon">%</span>
				  		<input type="text" id="extra_discount_amount" name="extra_discount_amount" class="form-control" placeholder="เป็นเงิน" onchange="calTotalNetAmount()"/>
	  					<span class="input-group-addon">บาท</span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">	
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">ราคารวมก่อน VAT</label></div>				
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
					  		<input type="text" id="amount_before_tax" name="amount_before_tax" class="form-control" placeholder="0.00" readonly="readonly"/>
	  						<span class="input-group-addon">บาท</span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">รวมเป็นเงิน</label></div>				
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="total_net_amount" name="total_net_amount" class="form-control" placeholder="0.00" readonly="readonly"/>
	  						<span class="input-group-addon">บาท</span>
						</div>
					</div>
				</div>
				<!-- End Row -->
				
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3"></div>
					<div class="col-xs-6 col-sm-9">			
    				<label>หมายเหตุ</label>
    					<input type="text" id="note" name="note" class="form-control" placeholder="หมายเหตุ">
  					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left">กลับหน้าหลัก</button>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_delete" name="btn_delete" class="btn btn-danger pull-left" onclick="confirmDelete(event)"><span class="glyphicon glyphicon-remove-sign"></span> ลบรายการ</button>
						<div id="delete-confirm"></div>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left" onclick="openFormDetail(event)"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่มรายการใหม่</button>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_save" name="btn_save" class="btn btn-primary pull-right" onclick="confirmSave(event)"><span class="glyphicon glyphicon-floppy-disk"></span> บันทึกรายการ</button>
						<div id="save-confirm"></div>
						<div id="dialog-validate">กรุณากรอกข้อมูลให้ครบถ้วน</div>
					</div>
				</div>

			</div>
			<!-- End Panel Body -->
		</div>
	</div>
	</form>
</body>
</html>