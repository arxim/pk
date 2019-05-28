$(document).ready(function() {
	loadProductDropdown();
	
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});

	$('#btn_close').click(function() {
		window.close();			
	})
});

/*auto complete*/
function loadDataTable(){
    $('#product_report_table').dataTable().fnDestroy();
	 $('#product_report_table').dataTable( {
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "PRODUCT_REPORT",
				 part_no: $("#part_no").val(),
				 product_type: $("#prd_type_id").val(),
				 product_brand: $("#prd_brand_id").val(), 
				 stock: $("#stock option:selected").val(),
				 stock_status: $("#stock_status option:selected").val(),
				 product_category : $("#dwl_product_category option:selected").val(),
				 state: "load"
			 },
		 }
	 });
	 getProductOnhand();
}

function loadProductDropdown() {
	/*
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "PRODUCT_CATEGORY",
        },
        success:function(data) {
            $("select#dwl_product_category").html(data);
         }
	});
	*/
    if(	$("#role").val()=='03' ){
        $('#dwl_product_category option[value="1"]').prop("selected", true);
        $("#dwl_product_category").prop("disabled", true);
    }else{
        $("#dwl_product_category").prop("disabled", false);   
    }

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

function getProductOnhand(){
	$.ajax({
		type: "POST",
		url: "./ProductSrvl",
        data: {
        	method: "PRODUCT_ONHAND",
			part_no: $("#part_no").val(),
			product_type: $("#prd_type_id").val(),
			product_brand: $("#prd_brand_id").val(), 
			stock: $("#stock option:selected").val(),
			stock_status: $("#stock_status option:selected").val(),
			product_category : $("#dwl_product_category option:selected").val(),
        },
        success:function(data) {
        	$("#prd_onhand").val(data.PRD_ONHAND);
        }
	 });
}


function getExcel() {
	$('#product_report').attr('action', './WriteExcelFileSrvl');
	$('#product_report').attr('target', '_blank');
	$("#h_stock").val($("#stock option:selected").val());
	$("#h_stock_status").val($("#stock_status option:selected").val());
	$('#product_category').val($("#dwl_product_category option:selected").val());
	$('#product_report').submit();
}
function viewReport(){
	$("#employee").val($("#emp_id option:selected").val());
	$("#invoice_type_h").val($("#invoice_type option:selected").val());
	$("#customer_billing").attr('action','./ViewReportSrvl');
	$("#customer_billing").attr('target','_blank');
	$("#customer_billing").submit();
}