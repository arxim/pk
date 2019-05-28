$(document).ready(function() {	
	//display data table on load page
	$('#return_table').dataTable( {
		 "order": [[ 2, "desc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "RETURN_PRODUCT",
				 state: "page_load"
			 },
		 }
	 });
	 dbClickDataTable();
});

function dbClickDataTable(){
	$('#return_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
	    $("#item_no").val(cell1text);
	    $("#doc_no").val(cell2text);
	    $("#mode").val("edit");
	
		window.open("","return_form_detail");
		document.getElementById('return_form_search').submit();
	});
}

function loadDataTable(){
	$('#return_table').dataTable().fnDestroy();
	var table = $('#return_table').dataTable( {
		 "order": [[ 2, "desc" ]],
	     "responsive" : true,
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "RETURN_PRODUCT",
				 state: "load",
				 cust_id: $("#cust_id").val(),
				 start_date: $("#doc_date_from").val(),
				 end_date: $("#doc_date_to").val(),
			 },
		 }
	 });
	
	dbClickDataTable();
	/*
	$('#return_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
	    $("#item_no").val(cell1text);
	    $("#doc_no").val(cell2text);
	    $("#mode").val("edit");
	
		window.open("","return_form_detail");
		document.getElementById('return_form_search').submit();
	});
	*/
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
		}
    });
}
function newReturnProduct() {
	window.open("return_form_detail.jsp","_blank");
}
