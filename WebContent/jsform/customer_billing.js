$(document).ready(function() {
	onReady();
	loadEmpDropdown();
	
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});

	$('#btn_close').click(function() {
		window.close();			
	})
	
	$(document).on('click', '#btn_print', function() {
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
            "Yes": function () {
                $('#remark').val($('#txtRemark').val());
                $(this).dialog('close');
                printReport();
            },
            "No": function () {
                $(this).dialog('close');
            }
        }
    });
}

/*auto complete*/
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
        	defaultPageLoad();
        }
	 });
}
function loadDataTable(){
    $('#billing_table').dataTable().fnDestroy();
	var table = $('#billing_table').dataTable( {
		 "order": [[ 0, "asc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "SELL_PRODUCT",
				 emp_id: $("#emp_id option:selected").val(),
				 cust_id: $("#cust_id").val(),
				 start_date: $("#invoice_date_from").val(),
				 end_date: $("#invoice_date_to").val(),
				 invoice_type: $("#invoice_type option:selected").val(),
				 state: "billing"
			 },
		 },
		 "aoColumns": [ 
			{"sClass": "center", sWidth: '10%'},
			{"sClass": "left", sWidth: '25%'},
			{"sClass": "center", sWidth: '25%'},
			{"sClass": "center", sWidth: '10%'},
			{"sClass": "center", sWidth: '10%'},
			{"sClass": "right", sWidth: '10%'},
			{"sClass": "right", sWidth: '10%'},
		 ]
	 });
	loadSumTable();
}
function loadSumTable(){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
        data: {
        	method: "getSumBilling",
			emp_id: $("#emp_id option:selected").val(),
			cust_id: $("#cust_id").val(),
			start_date: $("#invoice_date_from").val(),
			end_date: $("#invoice_date_to").val(),
			invoice_type: $("#invoice_type option:selected").val(),
        },
        success:function(data) {
        	if(data!=null){
                $("#total_invoice").val(data.TOTAL_INVOICE); 
                $("#total_amount").val(data.TOTAL_AMOUNT); 
                $("#total_net_amount").val(data.TOTAL_NET_AMOUNT);
                $("#total_cr").val(data.TOTAL_CR_AMOUNT);
        	}
         }
	 });	
}
function getExcel() {
	$('#customer_billing').attr('action', './WriteExcelFileSrvl');
	$('#customer_billing').attr('target', '_blank');
	$("#employee").val($("#emp_id option:selected").val());
	$("#invoice_type_h").val($("#invoice_type option:selected").val());

	// method post
	//$('#hYYYY').val($("#year").val());
	//$('#hMM').val($("#month").val());
	//$('#hDoctorCode').val($("#user_id").val());
	$('#customer_billing').submit();
}
function viewReport(){
	$("#employee").val($("#emp_id option:selected").val());
	$("#invoice_type_h").val($("#invoice_type option:selected").val());
	$("#customer_billing").attr('action','./ViewReportSrvl');
	$("#customer_billing").attr('target','_blank');
	$("#report_name").val("billing");
	$("#customer_billing").submit();
}
function printReport(){
	$("#employee").val($("#emp_id option:selected").val());
	$("#invoice_type_h").val($("#invoice_type option:selected").val());
	$("#customer_billing").attr('action','./ViewReportSrvl');
	$("#customer_billing").attr('target','_blank');
	$("#report_name").val("BillingForm");
	$("#customer_billing").submit();
}
