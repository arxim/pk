$(document).ready(function() {
	var mode = "new";
	getVehicleModelId();
});
function getVehicleModelId(){
	$.ajax({
		type: "POST",
		url: "./VehicleModelSrvl",
        data: {
        	method: "getid",
        },
        success:function(data) {
            $("#vehicle_model_id").val(data.VEHICLE_MODEL_ID);
            mode = "new";
         }
	 });
}
function vehicleModelKeyPress(e) {
    $("input#vehicle_model_name").autocomplete({
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
                    tb:"VEHICLE_MODEL"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#vehicle_model_id").val(ui.item.id);
			$("#vehicle_model_name").val(ui.item.value);
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
		url: "./VehicleModelSrvl",
		data: {
			method: "save",
			mode: mode,
			vehicle_model_id: $("#vehicle_model_id").val(),
			vehicle_model_name: $("#vehicle_model_name").val()
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
	getVehicleModelId();
	$("#vehicle_model_name").val("");
}
function closePage(event){
	window.close();
}