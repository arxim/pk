$(document).ready(function() {
	var mode = "new";
	getUnitTypeId();
});
function getUnitTypeId(){
	$.ajax({
		type: "POST",
		url: "./UnitTypeSrvl",
        data: {
        	method: "getid",
        },
        success:function(data) {
            $("#unit_type_id").val(data.UNIT_TYPE_ID);
            mode = "new";
         }
	 });
}
function unitTypeKeyPress(e) {
    $("input#unit_type_name").autocomplete({
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
                    tb:"UNIT_TYPE"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#unit_type_id").val(ui.item.id);
			$("#unit_type_name").val(ui.item.value);
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
		url: "./UnitTypeSrvl",
		data: {
			method: "save",
			mode: mode,
			unit_type_id: $("#unit_type_id").val(),
			unit_type_name: $("#unit_type_name").val()
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
	getUnitTypeId();
	$("#unit_type_name").val("");
}
function closePage(event){
	window.close();
}