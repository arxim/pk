<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript">
	var token = '${param.token}';
	var user_id = '${param.form_username}';
	var role = '${param.role}';
</script>
<jsp:include page="header_form.jsp"></jsp:include>
<% 
session.setAttribute("ROLE", request.getParameter("role"));
System.out.println(session.getAttribute("ROLE")); 
%>
<link href="css/customize-template.css" rel="stylesheet">
<script src="jsform/menu.js" type="text/javascript"></script>
</head>
<body onload="validateLogin()">
	<!-- hidden form value -->
    <input type="text" id="token" name="token" class="hidden"/>
    <input type="text" id="user_id" name="user_id" class="hidden"/>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container" style="margin-left: 0px;">
				<a href="#" class="brand">PK Auto System</a>
			</div>
		</div>
		<!-- END inner NAV BAR -->
	</div>
	<!-- END NAV BAR -->
	<div >
		<div id="body-content" style="padding-top: 0px;">
			<div class="body-nav body-nav-horizontal body-nav-fixed">
				<div class="container"style="margin-left: 0px;">
					<ul>
						<li><a href="javascript:void(0)" id="home_menu" onclick="Home();"><i class="fa fa-home fa-4x"></i></a></li>
						<li><a href="javascript:void(0)" id="setting_menu" onclick="Setting();"> <i class="fa fa-cogs fa-4x"></i></a></li>
						<li><a href="javascript:void(0)" id="operation_menu" onclick="Operation();"> <i class="fa fa-shopping-cart fa-4x"></i></a></li>
						<li><a href="javascript:void(0)" id="report_menu" onclick="Report()"> <i class="fa fa-file-text fa-4x"></i></a></li>
						<li><a href="javascript:void(0)" id="admin_menu" onclick="Admin()"> <i class="fa fa-user fa-4x"></i></a></li>
						<li><a href="index.html" id="logout"> <i class="fa fa-sign-out fa-4x"></i></a></li>
					</ul>
				</div>
			</div>
		</div>
		<br><br>
	</div>
	<!-- End navbar -->

		<div class="row form-group" id="setting">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">
			<div class="row "><h3 class="page-header">ข้อมูลหลัก</h3></div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="product_type" href="product_type.jsp" target="_blank">				
					<img src="images/producttype.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_product_type" align="center">ประเภทสินค้า</h5>
					<span class="text-muted"></span>
				</div>
				
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="product_brand" href="product_brand.jsp" target="_blank">				
					<img src="images/branding.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_product_brand" align="center">ยี่ห้อสินค้า</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="product_class" href="#" target="_blank">
					<img src="images/brandtype.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_product_class" align="center">ชนิดสินค้า</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="product_form" href="product_form_search.jsp?role=test" target="_blank">
					<img src="images/product.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_product_form" align="center">สินค้า</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="supplier_form_search" href="supplier_form_search.jsp" target="_blank">
					<img src="images/shop.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_supplier_form_search" align="center">เจ้าหนี้</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="customer_form_search" href="customer_form_search.jsp" target="_blank">
					<img src="images/customer.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_customer_form_search" align="center">ลูกค้า</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="unit" href="unit.jsp" target="_blank">
					<img src="images/unitcount.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_unit" align="center">หน่วยนับ</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="unit_type" href="unit_type.jsp" target="_blank">
					<img src="images/count_type.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_unit_type" align="center">รายละเอียดหน่วยนับ</h5>
					<span class="text-muted"></span>
				</div>

				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="vehicle_brand" href="vehicle_brand.jsp" target="_blank">
					<img src="images/carbrand.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_vehicle_brand" align="center">ยี่ห้อรถ</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="vehicle_model" href="vehicle_model.jsp" target="_blank">
					<img src="images/cartype.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_vehicle_model" align="center">รุ่นรถ</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="area" href="area.jsp" target="_blank">
					<img src="images/area.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_area" align="center">เขตพื้นที่</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="world" href="#" target="_blank">
					<img src="images/world.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_world" align="center">ประเทศ</h5>
					<span class="text-muted"></span>
				</div>
			</div>
		</div>
		<!-- End menu setting -->	
		
		<div class="row form-group" id="operation">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">
			<div class="row "><h3 class="page-header">OPERATION</h3></div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="buy_form_search" href="buy_form_search.jsp" target="_blank">
					<img src="images/buyproduct.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_buy_form_search" align="center">บันทึกซื้อสินค้า</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="sell_form_search" href="sell_form_search.jsp" target="_blank">
					<img src="images/sellproduct.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_sell_form_search" align="center">บันทึกขายสินค้า</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="return_form_search" href="return_form_search.jsp" target="_blank">
					<img src="images/refundproduct.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_return_form_search" align="center">คืนสินค้า</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="claim_form_search" href="claim_form_search.jsp" target="_blank">
					<img src="images/claim.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_claim_form_search" align="center">เปลี่ยนสินค้า</h5>
					<span class="text-muted"></span>
				</div>
			</div>
		</div>
		<!-- End menu product -->

		<div class="row form-group" id="report">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">
			<div class="row main"><h3 class="page-header">REPORT</h3></div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="customer_billing" href="customer_billing.jsp" target="_blank">
					<img src="images/billing.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_customer_billing" align="center">ใบวางบิลขาย</h5>
					<span class="text-muted"></span>
				</div>
				
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="supplier_billing" href="supplier_billing.jsp" target="_blank">
					<img src="images/billing.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_supplier_billing" align="center">ใบวางบิลซื้อ</h5>
					<span class="text-muted"></span>
				</div>
			
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="product_report" href="product_report.jsp" target="_blank">
					<img src="images/product_report.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_product_report" align="center">ข้อมูลสินค้า</h5>
					<span class="text-muted"></span>
				</div>
				
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="benefit_record" href="benefit_record.jsp" target="_blank">
					<img src="images/benefit.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_benefit_record" align="center">ผลกำไร</h5>
					<span class="text-muted"></span>
				</div>
				
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="buy_record" href="buy_record.jsp" target="_blank">
					<img src="images/history.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_buy_record" align="center">ประวัติซื้อ</h5>
					<span class="text-muted"></span>
				</div>
				
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="sell_record" href="sell_record.jsp" target="_blank">
					<img src="images/sell_history.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_sell_record" align="center">ประวัติขาย</h5>
					<span class="text-muted"></span>
				</div>
				
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="product_best_seller" href="product_best_seller.jsp" target="_blank">
					<img src="images/bestseller.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_product_best_seller" align="center">สินค้าขายดี</h5>
					<span class="text-muted"></span>
				</div>
				
			</div>
		</div>
		<!-- End menu report -->

		<div class="row form-group" id="admin">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">
			<div class="row "><h3 class="page-header">ADMIN</h3></div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="users" href="users.jsp" target="_blank">
					<img src="images/user.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_users" align="center">ผู้ใช้งาน</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="role" href="#" target="_blank">
					<img src="images/role.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_role" align="center">สิทธิ์</h5>
					<span class="text-muted"></span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<a id="menu" href="#" target="_blank">
					<img src="images/menu.png" width="200" height="100" class="img-responsive"
						alt="Generic placeholder thumbnail">
					</a>
					<h5 id="div_menu" align="center">จัดการเมนู</h5>
					<span class="text-muted"></span>
				</div>
			</div>
		</div>
	<!-- End menu admin -->

</body>
</html>