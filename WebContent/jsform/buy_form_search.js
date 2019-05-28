$(document).ready(function() {	
	//display data table on load page
	 $('#buy_table').dataTable( {
		 "order": [[ 6, "desc" ]],
		 "columnDefs": [
	        {
	            "targets": [ 6 ],
	            "visible": false,
	            "searchable": false
	        }
		 ],
		 "ajax": {
			 type: "POST",
			 url: "./BuyProductSrvl",
			 dataSrc : "data",
			 data: {
				 method: "loaddatatable",
				 temp_invoice_date_from: $("#temp_invoice_date_from").val(),
				 temp_invoice_date_to: $("#temp_invoice_date_to").val(),
				 invoice_date_from: $("#invoice_date_from").val(),
				 invoice_date_to: $("#invoice_date_to").val(),
			 },
		 }
	 });

	//data table double click
	$('#buy_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
        $("#temp_invoice_no").val(cell1text);
        $("#mode").val("edit");

		window.open("","buy_form_main");
		document.getElementById('buy_form_search').submit();
	});
	
	$('#btn_new').click(function() {
		window.open("buy_form_detail.jsp","_blank");
	});
	
	$('#btn_close').click(function() {
		 window.close();
	})
});

function loadDataTable(){
    $('#buy_table').dataTable().fnDestroy();
	var table = $('#buy_table').dataTable( {
		 "order": [[ 0, "desc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./BuyProductSrvl",
			 dataSrc : "data",
			 data: {
				 method: "datatable",
				 temp_invoice_date_from: $("#temp_invoice_date_from").val(),
				 temp_invoice_date_to: $("#temp_invoice_date_to").val(),
				 invoice_date_from: $("#invoice_date_from").val(),
				 invoice_date_to: $("#invoice_date_to").val()
			 },
		 }
	 });
	$('#buy_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
        $("#temp_invoice_no").val(cell1text);
        $("#mode").val("edit");

		//window.open("","sell_form_main");
		document.getElementById('buy_form_search').submit();
	});
}