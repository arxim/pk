$(document).ready(function() {
	defaultPageLoad();
	
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
function defaultPageLoad(){
	//enable and disable object
	if(mode=="edit"){
		$("#btn_new").hide();
		getDataFillForm();
	}else{
		$.ajax({
			type: "POST",
			url: "./SupplierSrvl",
	        data: {
	            method: "getid"
	        },
	        success:function(data) {
	            $("#sup_id").val(data.SUP_ID);
	    		mode = "new";
	         }
		 });
		$("#btn_new").show();
	}
}
function getDataFillForm(){
	$.ajax({
		type: "POST",
		url: "./SupplierSrvl",
        data: {
        	method: "fillform",
            sup_id: supId
        },
        success:function(data) {
        	$("#mode").val("edit");
            $("#sup_id").val(data.SUP_ID);
            $("#sup_name").val(data.SUP_NAME);
            $("#address1").val(data.ADDRESS1);
            $("#address2").val(data.ADDRESS2);
            $("#telephone").val(data.TELEPHONE);
            $("#mobile").val(data.MOBILE);
            $("#fax").val(data.FAX);
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#tax_id").val(data.TAX_ID);
            $("#remark").val(data.REMARK);
         }
	 });
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
		url: "./SupplierSrvl",
		data: {
			method: "save",
			mode: mode,
			sup_id: $("#sup_id").val(),
			sup_name: $("#sup_name").val(),
			address1: $("#address1").val(),
			address2: $("#address2").val(),
			telephone: $("#telephone").val(),
			mobile: $("#mobile").val(),
			fax: $("#fax").val(),
			tax_id: $("#tax_id").val(),
			payment_term: $("#payment_term").val(),
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
function closePage(e){
	window.close();
}
