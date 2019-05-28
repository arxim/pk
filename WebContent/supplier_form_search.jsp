<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ข้อมูลหลักเจ้าหนี้</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/supplier_form_search.js"></script>
</head>
<body>
	<form role="form" id="supplier_form_search" name="supplier_form_search" method="post" action="supplier_form_detail.jsp"  target="supplier_form">
	
	<!-- hidden form value -->
    <input id="sup_id" name="sup_id" type="text" class="hidden" />
    <input id="mode" name="mode" type="text" class="hidden" />
	
	<div class="container">
	<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">รายละเอียดข้อมูลเจ้าหนี้</div>
			<div class="panel-body form-horizontal">
				<!-- Row#01 -->
				<div class="row form-group">
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_close" name="btn_close" class="btn btn-default pull-left" onclick="closePage(event)"><span class="glyphicon glyphicon-circle-arrow-left"></span> กลับหน้าหลัก</button>
						<div id="dialog-confirm"></div>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_view" name="btn_view" class="btn btn-info pull-right" onclick="window.location.reload(true)"><span class="glyphicon glyphicon-refresh"></span> เรียกดูใหม่</button>
					</div>
					<div class="col-xs-6 col-sm-3">
						<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่มเจ้าหนี้ใหม่</button>
					</div>
					<div class="col-xs-6 col-sm-3"></div>
				</div>

				<!-- Table -->
					<div class="panel panel-info">
						<div class="panel-body form-horizontal">
							<table id="supplier_table" class="display responsive" style="width: 100%">
								<thead>
									<tr>
										<th>รหัส</th>
										<th>ชื่อ</th>
										<th>ที่อยู่</th>
										<th>เบอร์โทรศัพท์</th>
										<th>เครดิต</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
		</div>
	</div>
	</form>
</body>
</html>