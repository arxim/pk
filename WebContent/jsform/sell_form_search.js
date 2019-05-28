$(document).ready(function() {	
	
	//display data table on load page
	var table = $('#sell_table').dataTable( {
		 "order": [[ 0, "desc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./SellProductSrvl",
			 dataSrc : "data",
			 data: {
				 method: "loaddatatable",
				 temp_invoice_date_from: $("#temp_invoice_date_from").val(),
				 temp_invoice_date_to: $("#temp_invoice_date_to").val(),
				 invoice_date_from: $("#invoice_date_from").val(),
				 invoice_date_to: $("#invoice_date_to").val(),
				 invoice_type: $("#invoice_type").prop("selected",true).val(),
			 },
		 }
	 });
	
	//data table double click
	$('#sell_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
        $("#temp_invoice_no").val(cell1text);
        $("#mode").val("edit");

		document.getElementById('sell_form_search').submit();
	});
	
	$('#btn_new').click(function() {
		window.open("sell_form_detail.jsp","_blank");
	});
	$('#btn_close').click(function() {
		 window.close();
	})
});
function loadDataTable(){
    $('#sell_table').dataTable().fnDestroy();
	var table = $('#sell_table').dataTable( {
		 "order": [[ 0, "desc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./SellProductSrvl",
			 dataSrc : "data",
			 data: {
				 method: "datatable",
				 temp_invoice_date_from: $("#temp_invoice_date_from").val(),
				 temp_invoice_date_to: $("#temp_invoice_date_to").val(),
				 invoice_date_from: $("#invoice_date_from").val(),
				 invoice_date_to: $("#invoice_date_to").val(),
				 invoice_type: $("#invoice_type").prop("selected",true).val(),
			 },
		 }
	 });
	$('#sell_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
        $("#temp_invoice_no").val(cell1text);
        $("#mode").val("edit");

		//window.open("","sell_form_main");
		document.getElementById('sell_form_search').submit();
	});
}
