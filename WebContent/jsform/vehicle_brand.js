$(document).ready(function() {
	var mode = "new";
	getVehicleBrandId();
});
function getVehicleBrandId(){
	$.ajax({
		type: "POST",
		url: "./VehicleBrandSrvl",
        data: {
        	method: "getid",
        },
        success:function(data) {
            $("#vehicle_brand_id").val(data.VEHICLE_BRAND_ID);
            mode = "new";
         }
	 });
}
function vehicleBrandKeyPress(e) {
    $("input#vehicle_brand_name").autocomplete({
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
                    tb:"VEHICLE_BRAND"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#vehicle_brand_id").val(ui.item.id);
			$("#vehicle_brand_name").val(ui.item.value);
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
		url: "./VehicleBrandSrvl",
		data: {
			method: "save",
			mode: mode,
			vehicle_brand_id: $("#vehicle_brand_id").val(),
			vehicle_brand_name: $("#vehicle_brand_name").val()
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
	getVehicleBrandId();
	$("#vehicle_brand_name").val("");
}
function closePage(event){
	window.close();
}