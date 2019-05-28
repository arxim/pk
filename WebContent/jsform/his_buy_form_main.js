$(document).ready(function() {
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
	//$('.datepicker').datepicker({});	
	loadInvoiceHeader();
});

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
            $("#address1").val(data.ADDRESS1);
            $("#telephone").val(data.TELEPHONE);
            $("#mobile").val(data.MOBILE);
            $("#total_tax_amount").val(data.TOTAL_TAX_AMOUNT);
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
}

function closePage(e){
	window.close();
}