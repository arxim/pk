$(document).ready(function() {
	onReady();
	loadEmpDropdown();
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
	
	$(document).on('click', '#btn_report', function() {
		$('#txtRemark').val("");
		$('#dialog-remark').dialog("open");
	});
});

function onReady() {
	//Provide Dialog Print Remark Function
	$("#dialog-remark").dialog({
		autoOpen: false,
        resizable: false,
        modal: true,
        width: "50%",
        buttons: {
            "พิมพ์บิล": function () {
                $('#remark').val($('#txtRemark').val());
                $(this).dialog('close');
                viewReport();
            },
            "ยกเลิก": function () {
                $(this).dialog('close');
            }
        }
    });
}

function openFormDetail(e){
	$("#mode").val("newedit");
	window.open("","sell_form_detail");
	document.getElementById('sell_form_main').submit();
}
function loadInvoiceHeader(){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
        data: {
        	method: "loadheaderpage2",
            temp_invoice_no: tempInvoiceNo
        },
        success:function(data) {
        	$("#mode").val("edit");
            $("#temp_invoice_no").val(data.TEMP_INVOICE_NO);
            $("#temp_invoice_date").val(showDate(data.TEMP_INVOICE_DATE));
            $("#invoice_no").val(data.INVOICE_NO);
            $("#invoice_date").val(showDate(data.INVOICE_DATE));
            $('#invoice_type option[value="' + data.INVOICE_TYPE +'"]').prop("selected", true);
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#payment_date").val(showDate(data.PAYMENT_DATE));
            $("#cust_id").val(data.CUST_ID);
            $("#cust_name").val(data.CUST_NAME);
            $("#note").val(data.REMARK);
            $("#address1").val(data.ADDRESS1);
            $("#telephone").val(data.TELEPHONE);
            $('#tax_rate option[value="' + data.TAX_RATE +'"]').prop("selected", true);
            $('#emp_id option[value="' + data.EMP_ID +'"]').prop("selected", true);
            $('#total_tax_amount').val(data.TOTAL_TAX_AMOUNT);
            $('#total_amount_bef').val(data.TOTAL_AMOUNT);
            $('#total_amount_aft').val(data.TOTAL_NET_AMOUNT);
            
        	if($("#invoice_no").val()!=""){
        		$("#temp_invoice_no").attr('readonly', true);
        		$("#temp_invoice_date").attr('disabled', true);
        		$("#invoice_no").attr('readonly', true);
        		$("#invoice_date").attr('disabled', true);
        		$("#cust_id").attr('readonly', true);
        		$("#emp_id").attr('disabled', true);
        		$("#payment_term").attr('readonly', true);
        		$("#payment_date").attr('disabled', true);
        		//$("#tax_rate").attr('disabled', true);
        		$("#invoice_type").attr('disabled', true);
        		$("#btn_save").hide();
        		$("#btn_new").hide();
        		$("#btn_delete").hide();
        	}else{
        		$("#invoice_no").attr('readonly', false);
        		$("#invoice_date").attr('disabled', false);
        		$("#cust_id").attr('readonly', false);
        		$("#emp_id").attr('disabled', false);
        		$("#payment_term").attr('readonly', false);
        		$("#payment_date").attr('disabled', false);
        		//$("#tax_rate").attr('disabled', true);
        		$("#invoice_type").attr('disabled', false);
        		$("#btn_save").show();
        		$("#btn_new").show();
        		$("#btn_delete").show();
        	}
            loadDataTable();
         }
	 });
}
function loadDataTable(){
    $('#sell_main_table').dataTable().fnDestroy();
	 $('#sell_main_table').dataTable( {
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "SELL_PRODUCT",
				 temp_invoice_no: $("#temp_invoice_no").val(),
				 temp_invoice_date: $("#temp_invoice_date").val(),
				 state: "load"
			 },
		 }
	 });
	 
	$('#sell_main_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
	    $("#item_no").val(cell1text);
	    $("#mode").val("edit");
	
		//window.open("","sell_form_detail");
		document.getElementById('sell_form_main').submit();
	});
}
function custKeyPress(e) {
    $("input#cust_id").autocomplete({
        minLength: 1,
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        highlight: true,
        source: function(request, response) {
            $.ajax({
            	type: "POST",
                url: "./AutoCompleteRetriveSrvl",
                dataType: "json",
                data: {
                    term: request.term, //keyin textfield
                    tb:"CUSTOMER"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#cust_id").val(ui.item.id);
			$("#cust_name").val(ui.item.value);
			getCustomerDetail(ui.item.id);
		}
    });
}
function getCustomerDetail(custId){
	$.ajax({
		type: "POST",
		url: "./CustomerSrvl",
        data: {
        	method: "getdata",
            cust_id: custId
        },
        success:function(data) {
            $("#address1").val(data.ADDRESS1);
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#telephone").val(data.TELEPHONE);
            $("#note").val(data.REMARK);
            $('#emp_id option[value="' + data.EMP_ID +'"]').prop("selected", true);
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
		url: "./SellProductSrvl",
		data: {
			method: "deleteInvoice",
			mode: mode,
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
		url: "./SellProductSrvl",
		data: {
			method: "updateInvoice",
			mode: mode,
			temp_invoice_no: $("#temp_invoice_no").val(),
			temp_invoice_date: $("#temp_invoice_date").val(),
			invoice_no: $("#invoice_no").val(),
			invoice_date: $("#invoice_date").val(),
			cust_id: $("#cust_id").val(),
			emp_id: $("#emp_id").prop("selected",true).val(),
			payment_term: $("#payment_term").val(),
			payment_date: $("#payment_date").val(),
			tax_rate: $("#tax_rate").prop("selected",true).val(),
			invoice_type: $("#invoice_type").prop("selected",true).val(),
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
function loadEmpDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "USERS"
        },
        success:function(data) {
            $("select#emp_id").html(data);
        	loadInvoiceHeader();
        }
	 });
}

function viewReport(){
	$("#rptTempInvoiceNo").val($("#temp_invoice_no").val());
	$("#invoiceType").val($("#invoice_type").prop("selected",true).val());
	$("#taxType").val($("#tax_rate").prop("selected",true).val());
	$("#company").val($("#companyName").prop("selected",true).val());
	$("#remark").val($("#remark").val());
	$("#report").attr('action','./ViewReportSrvl');
	$("#report").attr('target','_blank');
	$("#report").submit();
}

function closePage(e){
	window.close();
}