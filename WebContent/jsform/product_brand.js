$(document).ready(function() {
	var mode = "new";
	getProductBrandId();
});
function getProductBrandId(){
	$.ajax({
		type: "POST",
		url: "./ProductBrandSrvl",
        data: {
        	method: "getid",
        },
        success:function(data) {
            $("#prd_brand_id").val(data.PRD_BRAND_ID);
            mode = "new";
         }
	 });	
}
function productBrandKeyPress(e) {
    $("input#prd_brand_name").autocomplete({
       // width: 300,
        //max: 10,
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
                    tb:"PRODUCT_BRAND"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#prd_brand_id").val(ui.item.id);
			$("#prd_brand_name").val(ui.item.value);
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
		url: "./ProductBrandSrvl",
		data: {
			method: "save",
			mode: mode,
			prd_brand_id: $("#prd_brand_id").val(),
			prd_brand_name: $("#prd_brand_name").val()
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
	getProductBrandId();
	$("#prd_brand_name").val("");
}
function closePage(event){
	window.close();
}