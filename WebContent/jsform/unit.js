$(document).ready(function() {
	var mode = "new";
	getUnitId();
});
function getUnitId(){
	$.ajax({
		type: "POST",
		url: "./UnitSrvl",
        data: {
        	method: "getid",
        },
        success:function(data) {
            $("#unit_id").val(data.UNIT_ID);
            mode = "new";
         }
	 });
}
function unitKeyPress(e) {
    $("input#unit_name").autocomplete({
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
                    tb:"UNIT"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#unit_id").val(ui.item.id);
			$("#unit_name").val(ui.item.value);
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
		url: "./UnitSrvl",
		data: {
			method: "save",
			mode: mode,
			unit_id: $("#unit_id").val(),
			unit_name: $("#unit_name").val()
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
	getUnitId();
	$("#unit_name").val("");
}
function closePage(event){
	window.close();
}