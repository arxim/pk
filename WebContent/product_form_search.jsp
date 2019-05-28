<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>รายการข้อมูลหลักสินค้า</title>
<jsp:include page="header_form.jsp"></jsp:include>
<script src="jsform/product_form_search.js"></script>
</head>
<body>
	<form role="form" id="product_form_search" name="product_form_search" method="post" action="product_form_detail.jsp"  target="product_form">
	
	<!-- hidden form value -->
    <input id="prd_id" name="prd_id" type="text" class="hidden" />
    <input id="part_no" name="part_no" type="text" class="hidden" />
    <input id="mode" name="mode" type="text" class="hidden" />

	<div class="container">
	<div class="row form-group"></div>
		<div class="panel panel-primary">
			<div class="panel-heading">รายละเอียดข้อมูลสินค้า</div>
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
						<button type="button" id="btn_new" name="btn_new" class="btn btn-success pull-left"><span class="glyphicon glyphicon-plus-sign"></span> เพิ่มสินค้าใหม่</button>
					</div>
					<div class="col-xs-6 col-sm-3"></div>
				</div>

				<!-- Table -->
					<div class="panel panel-info">
						<div class="panel-body form-horizontal">
							<table id="product_table" class="display responsive" style="width: 100%">
								<thead>
									<tr>
										<th width="10%">Part No.</th>
										<th width="15%">ชื่อสินค้า</th>
										<th width="25">รุ่นรถ</th>
										<th width="5%">หน่วยนับ</th>
										<th width="5%">หน่วย</th>
										<th width="5">ขนาด</th>
										<th width="10%">ยี่ห้อสินค้า</th>
										<th width="5%">ยี่ห้อรถ</th>
										<th width="5%">จัดเก็บ</th>
										<th width="5%">ขั้นต่ำ</th>
										<th width="5%">คงเหลือ</th>
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