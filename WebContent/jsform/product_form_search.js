$(document).ready(function() {
	//display table 
	loadDataTable();
	$('#btn_new').click(function() {
		window.open("product_form_detail.jsp","_blank");
	});
});
function loadDataTable(){
    $('#product_table').dataTable().fnDestroy();
	 $('#product_table').dataTable( {
		 "ajax": {
			 type: "POST",
			 url: "./DataTableRetriveSrvl",
			 dataSrc : "data",
			 data: {
				 tb: "PRODUCT",
				state: "load"
			 },
		 }
	 });
	 
	$('#product_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
	    $("#part_no").val(cell1text);
	    $("#mode").val("edit");
	
		window.open("","product_form");
		document.getElementById('product_form_search').submit();
	});
}
function closePage(e){
    $("#dialog-confirm").dialog({
        resizable: false,
        modal: true,
        title: "Close Product Form",
        height: 250,
        width: 400,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
                self.close();
            },
            "No": function () {
                $(this).dialog('close');
            }
        }
    });
}