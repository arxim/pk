<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="header_form.jsp"></jsp:include>
<script type="text/javascript">	
	var mode = '${param.mode}';
	var prdId = '${param.prd_id}';
	var partNo = '${param.part_no}';
</script>
<script src="jsform/product_form_detail.js" type="text/javascript"></script>
<title>ข้อมูลหลักสินค้า</title>
</head>
<body>
	<form role="form" id="product_form">
	
	<!-- hidden form value -->
    <input id="role" name="role" type="text" value="<%= session.getAttribute("ROLE") %>" class="hidden" />

	<div class="container">
		<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">ข้อมูลหลักสินค้า</div>
			<div class="panel-body form-horizontal">
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">รหัสสินค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="prd_id" name="prd_id" class="form-control" readonly="readonly"> 
					</div>					
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">Part No.</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="part_no" name="part_no" class="form-control">
							<span class="input-group-addon"><span class="glyphicon glyphicon-pencil"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ชื่อสินค้า</label>
					</div>
					<div class="col-xs-6 col-sm-9">
						<input type="text" id="prd_name" name="prd_name" class="form-control" onkeypress="return productNameKeyPress(event);"> 
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ประเภทสินค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="prd_type_id" name="prd_type_id" class="form-control" onkeypress="return productTypeKeyPress(event);"> 
							<span class="input-group-addon"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="prd_type_name" name="prd_type_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
					</div>
				</div>
				<!-- End Row -->
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ยี่ห้อสินค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="prd_brand_id" name="prd_brand_id" class="form-control" onkeypress="return productBrandKeyPress(event);"> 
							<span class="input-group-addon"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="prd_brand_name" name="prd_brand_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">สำหรับรุ่นรถ</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="vehicle_model_id" name="vehicle_model_id" class="form-control" onkeypress="return vehicleModelKeyPress(event);"> 
							<span class="input-group-addon"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-6">
		  				<input type="text" id="vehicle_model_name" name="vehicle_model_name" class="form-control" placeholder="Description ..." readonly="readonly"/>
					</div>
				</div>
				<!-- End Row -->
								
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ชนิดสินค้า</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<select class="form-control" id="prd_category_id" name="prd_category_id">
							<option value="">-- เลือกชนิดสินค้า --</option>
							<option value="1">นำเข้า/อื่นๆ</option>
							<option value="2">แท้</option>
							<option value="3">ของแท้</option>
						</select> 
					</div>
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">ประเทศที่ผลิต</label>
					</div>
					<div class="col-xs-6 col-sm-3">
				  		<select class="form-control" id="country_id" name="country_id"></select>
					</div>
				</div>
				<!-- End Row -->

				<div class="row form-group">	
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">สำหรับยี่ห้อรถ</label></div>				
					<div class="col-xs-6 col-sm-3">
						<select class="form-control" id="vehicle_brand_id" name="vehicle_brand_id"></select> 
					</div>
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">จัดเก็บสินค้า</label>
					</div>				
					<div class="col-xs-6 col-sm-3">
						<select class="form-control" id="stock">
							<option value="">-- เลือกจัดเก็บสินค้า --</option>
							<option value="Y">จัดเก็บ </option>
							<option value="N">ไม่จัดเก็บ </option>
							<option value="X">โป้วส่ง </option>
						</select>
					</div>					
				</div>
				<!-- End Row -->
								
				<div class="row form-group">	
					<div class="col-xs-3 col-sm-3 text-right">
						<label class="control-label ">หน่วยนับ</label>
					</div>
					<div class="col-xs-6 col-sm-3" >
				  		<select class="form-control" id="unit_id">
							<option value="">รหัสหน่วยนับ</option>
							<option value="1">ชิ้น</option>
							<option value="2">โหล</option>
							<option value="3">ลัง</option>
							<option value="4">กล่อง</option>
						</select>
					</div>
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label ">รายละเอียดหน่วยนับ</label>
					</div>				
					<div class="col-xs-6 col-sm-3">
						<select class="form-control" id="unit_type_id">
							<option value="">-- เลือกประเภทหน่วยนับ --</option>
							<option value="1">ลัง  12 ชิ้น </option>
							<option value="2">กล่อง 36 ชิ้น </option>
						</select>
					</div>					
				</div>
				<!-- End Row -->
				
				<div class="row form-group">	
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label "> ขนาดสินค้า</label></div>		
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="prd_size" name="prd_size" class="form-control">
							<span class="input-group-addon"><span class="glyphicon glyphicon-pencil"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label "> ราคาต้นทุน</label></div>		
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
				  			<input type="text" id="cost_price" name="cost_price" class="form-control">
  							<span class="input-group-addon"><span class="glyphicon glyphicon-usd"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->				

				<div class="row form-group">	
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ราคาขายมาตรฐาน</label></div>		
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
							<input type="text" id="sale_price" name="sale_price" class="form-control">
							<span class="input-group-addon"><span class="glyphicon glyphicon-usd"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">ราคาทุนมาตรฐาน</label></div>		
					<div class="col-xs-6 col-sm-3">
						<div class="input-group">
				  			<input type="text" id="real_sale_price" name="real_sale_price" class="form-control">
  							<span class="input-group-addon"><span class="glyphicon glyphicon-usd"></span></span>
						</div>
					</div>
				</div>
				<!-- End Row -->	
				
				<div class="row form-group">	
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label ">จำนวนสินค้าขั้นต่ำ</label></div>		
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="prd_min" name="prd_min" class="form-control">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">จำนวนสินค้าคงเหลือ</label></div>		
					<div class="col-xs-6 col-sm-3">
				  		<input type="text" id="prd_remain" name="prd_remain" class="form-control" readonly="readonly">
					</div>
				</div>
				<!-- End Row -->						
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3"><button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)">กลับหน้าหลัก</button></div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_delete" name="btn_delete" class="btn btn-danger pull-left" onclick="confirmDelete(event);">
							<span class="glyphicon glyphicon-remove-sign"></span> ลบรายการ
						</button>
						<div id="delete-confirm">ยืนยันการลบรายการสินค้า</div>
					</div>
					<div class="col-xs-6 col-sm-3"><button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left" onclick="newPage()"><span class="glyphicon glyphicon-plus-sign"></span> เริ่มรายการใหม่</button></div>
					<div class="col-xs-6 col-sm-3"><button type="button" id="btn_save" name="btn_save" class="btn btn-primary pull-right" onclick="confirmSave(event)"><span class="glyphicon glyphicon-floppy-disk"></span> บันทึกรายการ</button></div>
					<div id="save-confirm"></div>
				</div>
				<!-- End Row -->
				
				</div> 				
			</div>
		</div>
	</form>
</body>
</html>