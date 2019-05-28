$(document).ready(function() {
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});

	$('#btn_close').click(function() {
		window.close();			
	})
});

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
				 tb: "BUY_PRODUCT",
				 sup_id: $("#sup_id").val(),
				 start_date: $("#invoice_date_from").val(),
				 end_date: $("#invoice_date_to").val(),
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
		url: "./BuyProductSrvl",
        data: {
        	method: "getSumBuyProduct",
			sup_id: $("#sup_id").val(),
			start_date: $("#invoice_date_from").val(),
			end_date: $("#invoice_date_to").val(),
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
	$('#supplier_billing').attr('action', './WriteExcelFileSrvl');
	$('#supplier_billing').attr('target', '_blank');
	$("#employee").val($("#emp_id option:selected").val());
	
	$('#supplier_billing').submit();
}
function viewReport(){
	$("#supplier_billing").attr('action','./ViewReportSrvl');
	$("#supplier_billing").attr('target','_blank');
	$("#report_name").val("supplier_billing");
	$("#supplier_billing").submit();
}

//function viewReport(){
//	$("#employee").val($("#emp_id option:selected").val());
//	$("#invoice_type_h").val($("#invoice_type option:selected").val());
//	$("#customer_billing").attr('action','./ViewReportSrvl');
//	$("#customer_billing").attr('target','_blank');
//	$("#customer_billing").submit();
//}

