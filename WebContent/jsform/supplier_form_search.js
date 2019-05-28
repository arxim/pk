$(document).ready(function() {	
	//display data table on load page
	 $('#supplier_table').dataTable( {
		 "ajax": {
			 type: "POST",
			 url: "./SupplierSrvl",
			 dataSrc : "data",
			 data: {
				 method: "datatable",
			 },
		 }
	 });

	//data table double click
	$('#supplier_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
        $("#sup_id").val(cell1text);
        $("#mode").val("edit");

		window.open("","supplier_form");
		document.getElementById('supplier_form_search').submit();
	});
	
	$('#btn_new').click(function() {
		window.open("supplier_form_detail.jsp","_blank");
	});

});
function closePage(e){
    $("#dialog-confirm").dialog({
        resizable: false,
        modal: true,
        title: "Modal",
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