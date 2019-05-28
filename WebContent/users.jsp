<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ข้อมูลผู้ใช้งาน</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/users.js" type="text/javascript"></script>
</head>
<body>
	<input id="inputMode" type="hidden" class="form-control input-sm" value="New"  >
	<div class="container"><p>
	<div class="row form-group"></div>
	<div class="panel panel-primary">
		<div class="panel-heading ">Users Information</div>
			<div class="panel-body form-horizontal">
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">รหัสผู้ใช้งาน</label></div>
					<div class="col-xs-6 col-sm-3">
						<input id="inputID" type="text" class="form-control" onChange="genAll()">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">สิทธิ์ผู้ใช้งาน</label></div>
					<div class="col-xs-6 col-sm-3">
						<select class="form-control input-sm" id="dropdownRole"></select>
					</div>
				</div>
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">ชื่อผู้ใช้งาน</label></div>
					<div class="col-xs-6 col-sm-3">
						<input id="inputName" type="text" class="form-control">
					</div>
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">นามสกุล</label></div>
					<div class="col-xs-6 col-sm-3">
						<input id="inputSurname" type="text" class="form-control">
					</div>
				</div>

				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">ชื่อเข้าใช้ระบบ</label></div>
					<div class="col-xs-6 col-sm-3">
						<input id="inputLoginName" type="text" class="form-control">
					</div>
				</div>
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">รหัสผู้ใช้งาน</label></div>
					<div class="col-xs-6 col-sm-3"> 
						<input id="inputPassword" type="password" class="form-control">
					</div>
				</div>
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">ยืนยันรหัสผู้ใช้งาน</label></div>
					<div class="col-xs-6 col-sm-3"> 
						<input id="inputConPassword" type="password" class="form-control">
					</div>
				</div>
				
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">Email</label></div>
					<div class="col-xs-6 col-sm-6 ">
						<input id="inputEmail" type="text" class="form-control">
					</div>	
				</div>
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3 text-right"><label class="control-label">สถานะการใช้งาน</label></div>
					<div class="col-xs-6 col-sm-3">
						<div class="checkbox">
							<label><input type="checkbox" value="Active" id="checkbox" >ใช้งาน</label>
						</div>
					</div>
				</div>
				
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
		<div id="dialogDelete" title="Delete">ต้องการลบข้อมูล ?</div>
		<div id="dialogSave" title="Save">ต้องการบันทึกข้อมูล ?</div>
</body>
</html>