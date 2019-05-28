<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ข้อมูลยี่ห้อรถ</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/vehicle_brand.js" type="text/javascript"></script>
</head>
<body>
	<div class="container"><p>
	<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">ข้อมูลยี่ห้อรถ</div>
			<div class="panel-body form-horizontal">
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">รหัสยี่ห้อรถ</label>
					</div>
					<div class="col-xs-6 col-sm-3">
						<input type="text" id="vehicle_brand_id" name="vehicle_brand_id" class="form-control" readonly="readonly"> 
					</div>			
					<div class="col-xs-6 col-sm-6" ></div>
				</div>
				<!-- End Row -->
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right">
						<label class="control-label">ชื่อยี่ห้อรถ</label>
					</div>
					<div class="col-xs-6 col-sm-9">
		  				<input type="text" id="vehicle_brand_name" name="vehicle_brand_name" class="form-control" onkeypress="vehicleBrandKeyPress()"/>
					</div>
				</div>
				<!-- End Row -->
			
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3"><button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)">กลับหน้าหลัก</button></div>
					<div class="col-xs-6 col-sm-3"></div>
					<div class="col-xs-6 col-sm-3"><button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left" onclick="newPage()"><span class="glyphicon glyphicon-plus-sign"></span> เริ่มรายการใหม่</button></div>
					<div class="col-xs-6 col-sm-3"><button type="button" id="btn_save" name="btn_save" class="btn btn-primary pull-right" onclick="confirmSave(event)"><span class="glyphicon glyphicon-floppy-disk"></span> บันทึกรายการ</button></div>
					<div id="save-confirm"></div>
				</div>
				<!-- End Row -->
			
			</div>
		</div>
	</div>
</body>
</html>