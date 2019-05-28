<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="header_form.jsp"></jsp:include>
<script type="text/javascript">	
	var mode = '${param.mode}';
	var supId = '${param.sup_id}';
</script>
<script src="jsform/supplier_form_detail.js" type="text/javascript"></script>
<title>ข้อมูลหลักเจ้าหนี้</title>
</head>
<body>
	<form role="form" id="supplier_form">

	<div class="container">
		<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">ข้อมูลหลักเจ้าหนี้</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">รหัสเจ้าหนี้</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="sup_id" name="sup_id" class="form-control" readonly="readonly"> 
					</div>
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">Tax ID</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="tax_id" name="tax_id" class="form-control"> 
					</div>	
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">รหัสเจ้าหนี้</label>
					</div>
					<div class="col-xs-6 col-sm-9">
		  				<input type="text" id="sup_name" name="sup_name" class="form-control" placeholder="ชื่อเจ้าหนี้">
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ที่อยู่เจ้าหนี้</label>
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
					<div class="col-xs-6 col-sm-6">
						<div class="input-group">
							<input type="text" id="telephone" name="telephone" class="form-control" placeholder="Telephone">
							<span class="input-group-addon"><span class="glyphicon glyphicon-phone-alt" aria-hidden="true"></span></span>
						</div>
					</div>			
					<div class="col-xs-6 col-sm-3"></div>
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
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">จำนวนวันเครดิต</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="payment_term" name="payment_term" class="form-control" placeholder="จำนวนวันเครดิต"> 
					</div>
					<div class="col-xs-6 col-sm-6"></div>
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
						<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)">กลับหน้าหลัก</button>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_delete" name="btn_delete" class="btn btn-danger pull-left">
							<span class="glyphicon glyphicon-remove-sign"></span> ลบรายการ
						</button>
					</div>
					<div class="col-xs-6 col-sm-3"></div>
					
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_save" name="btn_save" class="btn btn-primary pull-right" onclick="confirmSave(event)">
							<span class="glyphicon glyphicon-floppy-disk"></span> บันทึกรายการ</button>
						<div id="save-confirm"></div>
					</div>
				</div>
				<!-- End Row -->

			</div>
			<!--panel-body  -->
		</div>
		<!--  panel-primary -->
	</div>
	<!-- container  -->
	</form>
</body>
</html>