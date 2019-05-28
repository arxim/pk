$(document).ready(function() {
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
});

function loadDataTable(){
    $('#benefit_main_table').dataTable().fnDestroy();
	var table = $('#benefit_main_table').dataTable( {
		 "order": [[ 0, "desc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "BENEFIT_RECORD",
				 emp_id: $("#emp_id").val(),
				 cust_id: $("#cust_id").val(),
				 part_no: $("#part_no").val(),
				 start_date: $("#invoice_date_from").val(),
				 end_date: $("#invoice_date_to").val(),
				 state: "record"
			 }
		 },
		 "aoColumns": [ 
		    {"sClass": "center", sWidth: '15%'},
			{"sClass": "left", sWidth: '15%'},
			{"sClass": "right", sWidth: '10%'},
			{"sClass": "right", sWidth: '10%'},
			{"sClass": "right", sWidth: '10%'},
			{"sClass": "left", sWidth: '20%'},
			{"sClass": "left", sWidth: '20%'},
		 ]
	 });
	getSum();
}

function loadSumDataTable(){
    $('#benefit_main_table').dataTable().fnDestroy();
	var table = $('#benefit_main_table').dataTable( {
		 "order": [[ 0, "desc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "BENEFIT_RECORD",
				 emp_id: $("#emp_id").val(),
				 cust_id: $("#cust_id").val(),
				 part_no: $("#part_no").val(),
				 start_date: $("#invoice_date_from").val(),
				 end_date: $("#invoice_date_to").val(),
				 state: "summary"
			 }
		 },
		 "aoColumns": [ 
		    {"sClass": "center", sWidth: '15%'},
			{"sClass": "left", sWidth: '15%'},
			{"sClass": "right", sWidth: '10%'},
			{"sClass": "right", sWidth: '10%'},
			{"sClass": "right", sWidth: '10%'},
			{"sClass": "left", sWidth: '20%'},
			{"sClass": "left", sWidth: '20%'},
		 ]
	 });
	getSum();
}
/*auto complete*/
function empKeyPress(e) {
    $("input#emp_name").autocomplete({
       // width: 300,
        max: 10,
        minLength: 1,
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        highlight: false,
        source: function(request, response) {
            $.ajax({
            	type: "POST",
                url: "./AutoCompleteRetriveSrvl",
                dataType: "json",
                data: {
                    term: request.term, //keyin textfield
                    tb:"USERS"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#emp_name").val(ui.item.value);
			$("#emp_id").val(ui.item.id);
		}
    });
}

/*auto complete*/
function custKeyPress(e) {
    $("input#cust_name").autocomplete({
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
			$("#cust_name").val(ui.item.value);
			$("#cust_id").val(ui.item.id);
		}
    });
}

function productKeyPress(e) {
    $("input#part_no").autocomplete({
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        highlight: false,
        source: function(request, response) {
            $.ajax({
            	type: "POST",
                url: "./AutoCompleteRetriveSrvl",
                dataType: "json",
                data: {
                    term: request.term,
                    tb:"PRODUCT"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#part_no").val(ui.item.id);
			$("#part_detail").val(ui.item.value);
			getStock();
		}
    });
}
function getStock(){
	$.ajax({
		type: "POST",
		url: "./ProductSrvl",
        data: {
        	method: "getStock",
            part_no: $("#part_no").val(),
        },
        success:function(data) {
        	$("#prd_remain").val(data.PRD_REMAIN);
        }
	 });
}
function getSum(){
	$.ajax({
		 type: "POST",
		 url: "./SellProductSrvl",
		 data: {
			 method: "sumBenefit",
			 emp_id: $("#emp_id").val(),
			 cust_id: $("#cust_id").val(),
			 part_no: $("#part_no").val(),
			 start_date: $("#invoice_date_from").val(),
			 end_date: $("#invoice_date_to").val(),
		 },
	     success:function(data) {
	    	 $("#benefit_amount").val(data.BENEFIT_AMOUNT);
	    	 $("#sell_amount").val(data.SELL_AMOUNT);
	    	 $("#cost_amount").val(data.COST_AMOUNT);
	     }
	})
}

//export excel
function getExcel() {
	$('#claim_form_excel').attr('action', './WriteExcelFileSrvl');
	$('#claim_form_excel').attr('method', 'post');
	$('#claim_form_excel').attr('target', '_blank');
	
	// binding values
	$('#frm_excel_cust_id').val($("#cust_id").val());
	$('#frm_excel_emp_id').val($("#emp_id").val());
	$('#frm_excel_part_no').val($("#part_no").val());
	$('#frm_excel_start_date').val($("#invoice_date_from").val());
	$('#frm_excel_end_date').val($("#invoice_date_to").val());
	
	$('#claim_form_excel').submit();
}

function clearScreen(){
	location.reload();
}
function closePage(e){
	window.close();
}