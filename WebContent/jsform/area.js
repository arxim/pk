$(document).ready(function() {
	var mode = "new";
	getAreaId();
});
function getAreaId(){
	$.ajax({
		type: "POST",
		url: "./AreaSrvl",
        data: {
        	method: "getid",
        },
        success:function(data) {
            $("#area_id").val(data.AREA_ID);
            mode = "new";
         }
	 });
}
function routeNameKeyPress(e) {
    $("input#route_name").autocomplete({
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
                    tb:"AREA"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#area_id").val(ui.item.id);
			$("#route_name").val(ui.item.value);
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
		url: "./AreaSrvl",
		data: {
			method: "save",
			mode: mode,
			area_id: $("#area_id").val(),
			route_name: $("#route_name").val()
		},
		success:function(data) {
			if(data == "1"){
				alert("บันทึกข้อมูลเรียบร้อยแล้ว");
				window.location.reload();
			}else{
				alert("ไม่สามารถบันทึกข้อมูลได้");
			}
        }
	});
}

function newPage(){
	getAreaId();
	$("#route_name").val("");
}
function closePage(event){
	window.close();
}