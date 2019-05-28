$(document).ready(function() {
	var mode = "new";
	getProductTypeId();
});
function getProductTypeId(){
	$.ajax({
		type: "POST",
		url: "./ProductTypeSrvl",
        data: {
        	method: "getid",
        },
        success:function(data) {
            $("#prd_type_id").val(data.PRD_TYPE_ID);
            mode = "new";
         }
	 });	
}
function productTypeKeyPress(e) {
    $("input#prd_type_name").autocomplete({
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
            mode = "edit";
		}
    });
}
function confirmSave(e){
    $("#save-confirm").dialog({
        resizable: false,
        modal: true,
        title: "ยืนยันบันทึกข้อมูล "+mode,
        height: 250,
        width: 400,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
                saveData(e);
            },
            "No": function () {
                $(this).dialog('close');
            }
        }
    });
}
function saveData(e){
	$.ajax({
		type: "POST",
		url: "./ProductTypeSrvl",
		data: {
			method: "save",
			mode: mode,
			prd_type_id: $("#prd_type_id").val(),
			prd_type_name: $("#prd_type_name").val()
		},
		success:function(data) {
			if(data == "1"){
				alert("บันทึกข้อมูลเรียบร้อยแล้ว");
			}else{
				alert("ไม่สามารถบันทึกข้อมูลได้");
			}
        }
	});
}

function newPage(){
	getProductTypeId();
	$("#prd_type_name").val("");
}
function closePage(event){
	window.close();
}