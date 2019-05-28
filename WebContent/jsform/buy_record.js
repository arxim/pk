$(document).ready(function() {
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
	
	//data table double click
	$(document).on('dblclick', '#buy_main_table tbody tr', function() {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var tempInvoiceNo = row.find('td:eq(17)').text();
	    $("#temp_invoice_no").val(tempInvoiceNo);
	    $("#mode").val("edit");

		window.open("","his_buy_form_main");
		document.getElementById('form_buy_record').submit();
	});
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
				 tb: "BUY_PRODUCT",
				 sup_id: $("#sup_id").val(),
				 part_no: $("#part_no").val(),
				 prd_type_id: $("#prd_type_id").val(),
				 prd_brand_id: $("#prd_brand_id").val(), 
				 start_date: $("#invoice_date_from").val(),
				 end_date: $("#invoice_date_to").val(),
				 temp_start_date: $("#temp_invoice_date_from").val(),
				 temp_end_date: $("#temp_invoice_date_to").val(),
				 role: $("#role").val(),
				 state: "record"
			 },
		 }
	 });
	getStock();
	getSummary();
}
/*auto complete*/
function supKeyPress(e) {
    $("input#sup_id").autocomplete({
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
                    tb:"SUPPLIER"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#sup_id").val(ui.item.id);
			$("#sup_name").val(ui.item.value);
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
		url: "./BuyProductSrvl",
        data: {
        	method: "getSummaryRecord",
			sup_id: $("#sup_id").val(),
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

// export excel
function getExcel() {
	$('#buy_record').attr('action', './WriteExcelFileSrvl');
	$('#buy_record').attr('target', '_blank');
	
	$('#start_date').val($("#invoice_date_from").val());
	$('#end_date').val($("#invoice_date_to").val());
	$('#temp_start_date').val($("#temp_invoice_date_from").val());
	$('#temp_end_date').val($("#temp_invoice_date_to").val());

	$('#buy_record').submit();
}
function clearScreen(){
	$("#sup_id").val("");
	$("#sup_name").val("");
	$("#part_no").val("");
	$("#part_detail").val("");
	$("#prd_type_id").val("");
	$("#prd_type_name").val("");
	$("#prd_remain").val("");
	$("#prd_brand_id").val("");
	$("#prd_brand_name").val("");
	$("#invoice_date_from").val("");
	$("#invoice_date_to").val("");
	$("#temp_invoice_date_from").val("");
	$("#temp_invoice_date_to").val("");
	$("#sum_quantity").val("");
	$("#sum_amount").val("");
	$("#sum_net_amount").val("");
}
function closePage(e){
	window.close();
}