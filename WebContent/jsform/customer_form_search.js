$(document).ready(function() {	
	//display data table on load page
	 $('#customer_table').dataTable( {
		 "ajax": {
			 type: "POST",
			 url: "./CustomerSrvl",
			 dataSrc : "data",
			 data: {
				 method: "datatable",
			 },
		 }
	 });

	//data table double click
	$('#customer_table tbody').on('dblclick', 'tr', function () {
	    var $this = $(this);
	    var row = $this.closest("tr");
	    var cell1text = row.find('td:eq(0)').text();
	    var cell2text = row.find('td:eq(1)').text();
        $("#cust_id").val(cell1text);
        $("#mode").val("edit");

		window.open("","customer_form");
		document.getElementById('customer_form_search').submit();
	});
	
	$('#btn_new').click(function() {
		window.open("customer_form_detail.jsp","_blank");
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