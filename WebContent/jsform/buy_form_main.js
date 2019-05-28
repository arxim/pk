$(document).ready(function() {
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
	//$('.datepicker').datepicker({});	
	loadInvoiceHeader();
});
function openFormDetail(e){
	$("#mode").val("newedit");
	window.open("","buy_form_detail");
	document.getElementById('buy_form_main').submit();
}
function loadInvoiceHeader(){
	$.ajax({
		type: "POST",
		url: "./BuyProductSrvl",
        data: {
        	method: "loadheader",
            temp_invoice_no: tempInvoiceNo
        },
        success:function(data) {
        	$("#mode").val("edit");
        	$("#temp_invoice_no").val(data.TEMP_INVOICE_NO);
            $("#temp_invoice_date").val(showDate(data.TEMP_INVOICE_DATE));
            $("#invoice_no").val(data.INVOICE_NO);
            $("#invoice_date").val(showDate(data.INVOICE_DATE));
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#payment_date").val(showDate(data.PAYMENT_DATE));
            $("#sup_id").val(data.SUP_ID);
            $("#sup_name").val(data.SUP_NAME);
            $("#sremark").val(data.SREMARK);
            $("#address1").val(data.ADDRESS1);
            $("#telephone").val(data.TELEPHONE);
            $("#mobile").val(data.MOBILE);
            $("#total_tax_amount").val(data.TOTAL_TAX_AMOUNT);
            $('#total_amount_bef').val(data.TOTAL_AMOUNT);
            $('#total_amount_aft').val(data.TOTAL_NET_AMOUNT);
            $('#tax_rate option[value="' + data.TAX_RATE +'"]').prop("selected", true);
            loadDataTable();
         }
	 });
}
function loadDataTable(){
     $('#buy_main_table').dataTable().fnDestroy();
	 $('#buy_main_table').dataTable( {
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "BUY_PRODUCT",
				 temp_invoice_no: $("#temp_invoice_no").val(),
				 temp_invoice_date: $("#temp_invoice_date").val(),
				 invoice_no: $("#invoice_no").val(),
				 invoice_date: $("#invoice_date").val(),
				 state: "load"
			 },
		 }
	 });
	 
	$('#buy_main_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
	    $("#item_no").val(cell1text);
	    $("#mode").val("edit");
	
		window.open("","buy_form_detail");
		document.getElementById('buy_form_main').submit();
	});
}

function confirmSave(e){
    $("#save-confirm").dialog({
        resizable: false,
        modal: true,
        title: "ยืนยันการแก้ไขบิล "+$("#mode").val()+" : "+$("#temp_invoice_no").val(),
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
		url: "./BuyProductSrvl",
		data: {
			method: "updateInvoice",
			temp_invoice_no: $("#temp_invoice_no").val(),
			temp_invoice_date: $("#temp_invoice_date").val(),
			invoice_no: $("#invoice_no").val(),
			invoice_date: $("#invoice_date").val(),
			sup_id: $("#sup_id").val(),
			payment_term: $("#payment_term").val(),
			payment_date: $("#payment_date").val(),
		},
		success:function(data) {
			if(data == "1"){
				$("#mode").val("edit");
				alert("บันทึกข้อมูลเรียบร้อยแล้ว");
			}else{
				alert("ไม่สามารถบันทึกข้อมูลได้");
			}
        }
	});
}
function confirmDelete(e){
    $("#delete-confirm").dialog({
        resizable: false,
        modal: true,
        title: "ยืนยันการลบบิล "+$("#mode").val()+" : "+$("#temp_invoice_no").val(),
        height: 250,
        width: 400,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
                deleteData(e);
            },
            "No": function () {
                $(this).dialog('close');
            }
        }
    });
}
function deleteData(e){
	$.ajax({
		type: "POST",
		url: "./BuyProductSrvl",
		data: {
			method: "deleteInvoice",
			temp_invoice_no: $("#temp_invoice_no").val(),
		},
		success:function(data) {
			if(data == "1"){
				$("#mode").val("edit");
				alert("ลบข้อมูลเรียบร้อยแล้ว");
				window.close();
			}else{
				alert("ไม่สามารถลบข้อมูลได้");
			}
        }
	});
}

function closePage(e){
	window.close();
}