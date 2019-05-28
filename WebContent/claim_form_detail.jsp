<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายละเอียดเปลี่ยนสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script type="text/javascript">	
	var mode = '${param.mode}';
	var docNo = '${param.doc_no}';
	var userId = '${param.user_id}';
	var itemNo = '${param.item_no}';
</script>
<script src="jsform/claim_form_detail.js" type="text/javascript"></script>
</head>
<body>
	<form role="form" id="return_form_detail" name="return_form_detail" method="post" action="return_form_detail.jsp" target="return_form_detail">
	<input type="text" id="item_no" name="item_no" class="hidden"/>
	<input type="text" id="mode" name="mode" class="hidden"/>

	<div class="container">
	<div class="row form-group"></div>
	<div class="panel panel-primary">
	<div class="panel-heading">รายการเปลี่ยนสินค้า</div>
		<div class="panel-body form-horizontal">
		
			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">เลขที่ใบเปลี่ยนสินค้า</label></div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="doc_no" name="doc_no" class="form-control" readonly="readonly">
				</div>
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่รับเคลมสินค้า</label></div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="doc_date" name="doc_date" class="form-control datepicker" data-provide="datepicker" placeholder="dd/mm/yyyy">
				</div>
			</div>
			<!-- End Row -->
		
			<ul class="breadcrumb"><li class="active">รายละเอียดการเปลี่ยนสินค้า</li></ul>
			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right">
					<label class="control-label ">รหัสลูกค้า</label>
				</div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="cust_id" name="cust_id" class="form-control" onkeypress="return custKeyPress(event);"> 
				</div>
				<div class="col-xs-6"></div>
				<div class="col-xs-6 col-sm-6">
		  			<input type="text" id="cust_name" name="cust_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
				</div>
			</div>
			<!-- End Row -->
			
			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">เลขที่ใบเปลี่ยนสินค้า (ลูกค้า)</label></div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="cust_doc_no" name="cust_doc_no" class="form-control">
				</div>
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่ส่งคืนลูกค้า</label></div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="cust_doc_date" name="cust_doc_date" class="form-control datepicker" data-provide="datepicker" placeholder="dd/mm/yyyy">
				</div>
			</div>
			<!-- End Row -->

			<hr>
			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right">
					<label class="control-label ">รหัสร้านค้า</label>
				</div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="sup_id" name="sup_id" class="form-control" onkeypress="return supKeyPress(event);"> 
				</div>
				
				<div class="col-xs-6 col-sm-6">
	  				<input type="text" id="sup_name" name="sup_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
				</div>
			</div>
			<!-- End Row -->
			
			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">เลขที่ใบเปลี่ยนสินค้า (เจ้าหนี้)</label></div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="sup_doc_no" name="sup_doc_no" class="form-control">
				</div>
				<div class="col-xs-6 col-sm-3 text-right"></div>
				<div class="col-xs-6 col-sm-3"></div>
			</div>
			<!-- End Row -->

			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">วันที่ส่งเปลี่ยนสินค้า</label></div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="sup_send_date" name="sup_send_date" class="form-control datepicker" data-provide="datepicker" placeholder="dd/mm/yyyy">
				</div>
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">วันที่รับคืนสินค้า</label></div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="sup_doc_date" name="sup_doc_date" class="form-control datepicker" data-provide="datepicker" placeholder="dd/mm/yyyy"> 
				</div>
			</div>
			<!-- End Row -->
			
			<hr>
			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right">
					<label class="control-label">Part No.</label>
				</div>
				<div class="col-xs-6 col-sm-3">
					<input type="text" id="part_no" name="part_no" class="form-control" onkeypress="return productKeyPress(event);" required="required">
				</div>
				<div class="col-xs-6"></div>
				<div class="col-xs-6 col-sm-6">
	  				<input type="text" id="prd_name" name="prd_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
				</div>
			</div>
			<!-- End Row -->

			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวน</label></div>					
				<div class="col-xs-6 col-sm-3">
					<div class="input-group">
			  		<input type="text" id="prd_quantity" name="prd_quantity" class="form-control" placeholder="จำนวนสินค้า" onchange="calTotalNetAmount()"/>
 						<span class="input-group-addon">@</span>
					</div>
				</div>
				<div class="col-xs-6 col-sm-3"></div>
				<div class="col-xs-6 col-sm-3"></div>
			</div>
			<!-- End Row -->

			<div class="row form-group">
				<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">หมายเหตุ</label></div>					
				<div class="col-xs-6 col-sm-9">			
   					<input type="text" id="note" name="note" class="form-control" placeholder="หมายเหตุ">
 				</div>
			</div>
			<!-- End Row -->

			<div class="row form-group">
				<div class="col-xs-6 col-sm-3"><button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)">กลับหน้าหลัก</button></div>
				<div class="col-xs-6 col-sm-3"><button type="button" id="btn_delete" name="btn_delete" class="btn btn-danger pull-left" onclick="confirmDelete(event)"><span class="glyphicon glyphicon-remove-sign"></span> ลบ</button></div>
				<div id="delete-confirm"></div>
				<div class="col-xs-6 col-sm-2"><button type="button" id="btn_new" name="btn_new" class="btn btn-default pull-left" onclick="newPage()"><span class="glyphicon glyphicon-file"></span> เริ่มใหม่</button></div>
				<div class="col-xs-6 col-sm-2"><button type="button" id="btn_edit_new" name="btn_edit_new" class="btn btn-success pull-left" onclick="editNewPage()"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่ม</button></div>
				<div class="col-xs-6 col-sm-2"><button type="button" id="btn_save" name="btn_save" class="btn btn-primary pull-right" onclick="confirmSave(event)"><span class="glyphicon glyphicon-floppy-disk"></span> บันทึก</button></div>
				<div id="save-confirm" ></div>
			</div>
			<!-- End Row -->
		</div>
	</div>
	</div>
	</form>
</body>
</html>