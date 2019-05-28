function loadDataTable(){
	$('#claim_table').dataTable().fnDestroy();
	$.ajax({
		 type: "POST",
		 url: "./DataTableRetriveSrvl",
		 dataSrc : "data",
		 data: {
			 tb: "CLAIM_PRODUCT",
			 state: "load",
			 cust_id: $("#cust_id").val(),
			 sup_id: $("#sup_id").val(),
			 start_date: $("#doc_date_from").val(),
			 end_date: $("#doc_date_to").val(),
			 claim_type: $("#claim_type").prop("selected", true).val(),
		 },
		 success:function(json) {
			 var jsonData = [];
			 // Pass by reference JsonObject
			 $.each(json.data, function(key, value) {
				 
				 // Check cust_doc_date is empty
				 if (value[4] === '') {
					 value[4] = '<span class="glyphicon glyphicon glyphicon-remove" aria-hidden="true" style="color: red;"></span>';
				 }else {
					 value[4] = '<span class="glyphicon glyphicon-ok" aria-hidden="true" style="color: #5cb85c;"></span>';
				 }
				 
				// check Always
				 value[6] = '<span class="glyphicon glyphicon-ok" aria-hidden="true" style="color: #5cb85c;"></span>'; 
				 
				 // check sup_doc_date
				 if (value[7] === '') {
					 value[7] = '<span class="glyphicon glyphicon glyphicon-remove" aria-hidden="true" style="color: red;"></span>';
				 }else {
					 value[7] = '<span class="glyphicon glyphicon-ok" aria-hidden="true" style="color: #5cb85c;"></span>';
				 }
			 });
			 
			 // render datatable
			 var table = $('#claim_table').dataTable( {
				 "order": [[ 2, "desc" ]],
			     "responsive" : true,
			     "aaData" : json.data,
			     "aoColumns": [ 
		         	{"sClass": "center"},
		         	{"sClass": "left"},
		         	{"sClass": "left"},
		         	{"sClass": "left"},
		         	{"sClass": "center"},
		         	{"sClass": "left"},
		         	{"sClass": "center"},
		         	{"sClass": "center"},
		         	{"sClass": "left"},
		         	{"sClass": "left"},
		         	{"sClass": "center"}
		         ]
			 });
		 }
		 
		 // old code for render datatable
//		 var table = $('#claim_table').dataTable( {
//			 "order": [[ 2, "desc" ]],
//		     "responsive" : true,
//			 "ajax": {
//				 type: "POST",
//				 url: "./DataTableRetriveSrvl",
//				 dataSrc : "data",
//				 data: {
//					 tb: "CLAIM_PRODUCT",
//					 state: "load",
//					 cust_id: $("#cust_id").val(),
//					 sup_id: $("#sup_id").val(),
//					 start_date: $("#doc_date_from").val(),
//					 end_date: $("#doc_date_to").val(),
//					 claim_type: $("#claim_type").prop("selected", true).val(),
//				 }
//			 }
//		 });
	 });
	
	$('#claim_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
	    $("#item_no").val(cell1text);
	    $("#doc_no").val(cell2text);
	    $("#mode").val("edit");
	
		window.open("","claim_form_detail");
		document.getElementById('claim_form_search').submit();
	});
}
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

//export excel
function getExcel() {
	$('#claim_form_excel').attr('action', './WriteExcelFileSrvl');
	$('#claim_form_excel').attr('target', '_blank');
	
	// binding values
	$('#frm_excel_cust_id').val($("#cust_id").val());
	$('#frm_excel_sup_id').val($("#sup_id").val());
	$('#frm_excel_start_date').val($("#doc_date_from").val());
	$('#frm_excel_end_date').val($("#doc_date_to").val());
	$('#frm_excel_claim_type').val($("#claim_type").prop("selected", true).val());
	
	$('#claim_form_excel').submit();
}
function closePage(e){
	window.close();
}
function newReturnProduct() {
	window.open("claim_form_detail.jsp","_blank");
}
