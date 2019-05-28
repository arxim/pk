$(document).ready(function() {
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
	//$('.datepicker').datepicker({});	
	loadEmpDropdown();
	
	// for keydown number only
	/*
	$("#cr_limit_amount, #tax_id").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
             // Allow: Ctrl+A, Command+A
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
             // Allow: home, end, left, right, down, up
            (e.keyCode >= 35 && e.keyCode <= 40)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
    */
});
function loadAreaDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "AREA",
        },
        success:function(data) {
            $("select#area_id").html(data);
         }
	 });
	defaultPageLoad();
}
function defaultPageLoad(){
	//enable and disable object
	if(mode=="edit"){
		$("#btn_new").hide();
		getDataFillForm();
	}else{
		$.ajax({
			type: "POST",
			url: "./CustomerSrvl",
	        data: {
	            method: "getid"
	        },
	        success:function(data) {
	            $("#cust_id").val(data.CUST_ID);
	    		mode = "new";
	         }
		 });
		$("#btn_new").show();
	}
}
function getDataFillForm(){
	$.ajax({
		type: "POST",
		url: "./CustomerSrvl",
        data: {
        	method: "fillform",
            cust_id: custId
        },
        success:function(data) {
        	$("#mode").val("edit");
            $("#cust_id").val(data.CUST_ID);
            $("#cust_name").val(data.CUST_NAME);
            $("#address1").val(data.ADDRESS1);
            $("#address2").val(data.ADDRESS2);
            $("#telephone").val(data.TELEPHONE);
            $("#mobile").val(data.MOBILE);
            $("#fax").val(data.FAX);
            $("#discount_rate").val(data.DISCOUNT_RATE);
            $("#payment_term").val(data.PAYMENT_TERM);
            $('#emp_id option[value="' + data.EMP_ID +'"]').prop("selected", true);
            $('#area_id option[value="' + data.AREA_ID +'"]').prop("selected", true);
            $("#tax_id").val(data.TAX_ID);
            $("#cr_limit_amount").val(data.CR_LIMIT_AMOUNT);
            $('#invoice_type option[value="' + data.INVOICE_TYPE +'"]').prop("selected", true);
            $("#remark").val(data.REMARK);
         }
	 });
}
function closePage(e){
	window.close();
}
function newPage(){
	window.open("customer_form_detail.jsp","_self");
}
function confirmSave(e){
    $("#save-confirm").dialog({
        resizable: false,
        modal: true,
        title: "Modal",
        height: 250,
        width: 400,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
                saveData(e);
            },
            "No": function () {
                $(this).dialog('close');
            }
        }
    });
}
function saveData(e){
	$.ajax({
		type: "POST",
		url: "./CustomerSrvl",
		data: {
			method: "save",
			mode: mode,
			cust_id: $("#cust_id").val(),
			cust_name: $("#cust_name").val(),
			address1: $("#address1").val(),
			address2: $("#address2").val(),
			telephone: $("#telephone").val(),
			mobile: $("#mobile").val(),
			fax: $("#fax").val(),
			discount_rate: $("#discount_rate").val(),
			payment_term: $("#payment_term").val(),
			emp_id: $("#emp_id option:selected").val(),
			area_id: $("#area_id option:selected").val(),
			tax_id: $("#tax_id").val(),
			cr_limit_amount : $('#cr_limit_amount').val(),
			invoice_type: $("#invoice_type option:selected").val(),
			remark: $("#remark").val()
		},
		success:function(data) {
			if(data == "1"){
				alert("บันทึกข้อมูลเรียบร้อยแล้ว");
			}else{
				alert("ไม่สามารถบันทึกข้อมูลได้");
			}
        }
	});
}
function loadEmpDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "USERS"
        },
        success:function(data) {
            $("select#emp_id").html(data);
        }
	 });
	loadAreaDropdown();
}