$(document).ready(function() {
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
	//$('.datepicker').datepicker({});	
	//loadDataTable();
});
function loadDataTable(){
    $('#buy_main_table').dataTable().fnDestroy();
	var table = $('#buy_main_table').dataTable( {
		 "order": [[ 0, "desc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "SELL_PRODUCT",
				 cust_id: $("#cust_id").val(),
				 part_no: $("#part_no").val(),
				 prd_type_id: $("#prd_type_id").val(),
				 prd_brand_id: $("#prd_brand_id").val(), 
				 start_date: $("#invoice_date_from").val(),
				 end_date: $("#invoice_date_to").val(),
				 temp_start_date: $("#temp_invoice_date_from").val(),
				 temp_end_date: $("#temp_invoice_date_to").val(),
				 state: "record"
			 },
		 }
	 });
	getSummary();
	/*
	 $('#buy_main_table').dataTable( {
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "SELL_PRODUCT",
				 cust_id: $("#cust_id").val(),
				 part_no: $("#part_no").val(),
				 prd_type_id: $("#prd_type_id").val(),
				 prd_brand_id: $("#prd_brand_id").val(), 
				 start_date: $("#invoice_date_from").val(),
				 end_date: $("#invoice_date_to").val(),
				 temp_start_date: $("#temp_invoice_date_from").val(),
				 temp_end_date: $("#temp_invoice_date_to").val(),
				 state: "record"
			 },
		 }
	 });	 
	 */
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
function getSummary(){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
        data: {
        	method: "getSummaryRecord",
        	cust_id: $("#cust_id").val(),
			part_no: $("#part_no").val(),
			prd_type_id: $("#prd_type_id").val(),
			prd_brand_id: $("#prd_brand_id").val(), 
			start_date: $("#invoice_date_from").val(),
			end_date: $("#invoice_date_to").val(),
			temp_start_date: $("#temp_invoice_date_from").val(),
			temp_end_date: $("#temp_invoice_date_to").val(),
        },
        success:function(data) {
        	$("#sum_quantity").val(numberFormat(data.QUANTITY));
        	$("#sum_amount").val(numberFormat(data.TOTAL_AMOUNT));
        	$("#sum_net_amount").val(numberFormat(data.TOTAL_NET_AMOUNT));
        }
	});
}
function productTypeKeyPress(e) {
    $("input#prd_type_id").autocomplete({
       // width: 300,
        //max: 10,
        minLength: 1,
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        source: function(request, response) {
            $.ajax({
            	type: "POST",
                url: "./AutoCompleteRetriveSrvl",
                dataType: "json",
                data: {
                    term: request.term, //keyin textfield
                    tb:"PRODUCT_TYPE"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#prd_type_id").val(ui.item.id);
			$("#prd_type_name").val(ui.item.value);
		}
    });
}
function productBrandKeyPress(e) {
    $("input#prd_brand_id").autocomplete({
       // width: 300,
        //max: 10,
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
                    tb:"PRODUCT_BRAND"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#prd_brand_id").val(ui.item.id);
			$("#prd_brand_name").val(ui.item.value);
		}
    });
}
function closePage(e){
	window.close();
}
function clearScreen(){
	$("#cust_id").val("");
	$("#cust_name").val("");
	$("#part_no").val("");
	$("#part_detail").val("");
	$("#prd_type_id").val("");
	$("#prd_type_name").val("");
	$("#prd_remain").val("0");
	$("#prd_brand_id").val("");
	$("#prd_brand_name").val("");
	$("#invoice_date_from").val("");
	$("#invoice_date_to").val("");
	$("#temp_invoice_date_from").val("");
	$("#temp_invoice_date_to").val("");
}