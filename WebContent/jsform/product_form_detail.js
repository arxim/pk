$(document).ready(function() {
	// initial - assign default
	onReady();
	loadCountryDropdown();
});

function onReady() {
	// Require field
	$('#delete-confirm').dialog({
		autoOpen: false, 
		resizable: false, 
		modal: true,
        title: "แจ้งลบรายการ",
        height: 150,
        width: 400,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
                deleteData();
            },
            "No": function () {
                $(this).dialog('close');
            }
        }
	});
	
	// display button delete
	if (mode !== 'edit') {
		$("#btn_delete").hide();
	}
}

function closePage(e){
	window.close();
}
function newPage(){
	window.open("product_form_detail.jsp","_self");
}

function loadCountryDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "COUNTRY"
        },
        success:function(data) {
            $("select#country_id").html(data);
            loadVehicleBrandDropdown();
         }
	 });
}
function loadVehicleBrandDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "VEHICLE_BRAND"
        },
        success:function(data) {
            $("select#vehicle_brand_id").html(data);
            loadVehicleModelDropdown();
         }
	 });
}
function loadVehicleModelDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "VEHICLE_MODEL"
        },
        success:function(data) {
            $("select#vehicle_model_id").html(data);
            loadUnitDropdown();
         }
	 });
}
function loadUnitDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "UNIT"
        },
        success:function(data) {
            $("select#unit_id").html(data);
            loadUnitTypeDropdown();
         }
	 });
}
function loadUnitTypeDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "UNIT_TYPE"
        },
        success:function(data) {
            $("select#unit_type_id").html(data);
            defaultPageLoad();
         }
	 });
}

function defaultPageLoad(){
	//enable and disable object
	if(mode=="edit"){
		$("#btn_new").hide();
		getDataFillForm();
	}else{
		mode = "new";
		$("#cost_price").val("0");
		$("#sale_price").val("0");
		$("#real_sale_price").val("0");
		$("#prd_min").val("0");
		$("#prd_remain").val("0");
		$("#btn_new").show();
		$.ajax({
			type: "POST",
			url: "./ProductSrvl",
	        data: {
	            method: "getid"
	        },
	        success:function(data) {
	            $("#prd_id").val(data.PRD_ID);
	         }
		 });
		$("#btn_new").show();
	    if(	$("#role").val()=='03' ){
            $('#prd_category_id option[value="1"]').prop("selected", true);
	        $("#prd_category_id").prop("disabled", true);            	
	    }else{
	        $("#prd_category_id").prop("disabled", false);            	
	    }

	}
}

function getDataFillForm(){
	$.ajax({
		type: "POST",
		url: "./ProductSrvl",
        data: {
        	method: "fillform",
            part_no: partNo
        },
        success:function(data) {
        	$("#mode").val("edit");
            $("#prd_id").val(data.PRD_ID);
            $("#prd_name").val(data.PRD_NAME);
            $("#part_no").val(data.PART_NO);
            $("#prd_type_id").val(data.PRD_TYPE_ID);
            $("#prd_type_name").val(data.PRD_TYPE_NAME);
            $("#prd_brand_id").val(data.PRD_BRAND_ID);
            $("#prd_brand_name").val(data.PRD_BRAND_NAME);
            $('#prd_category_id option[value="' + data.PRD_CATEGORY_ID +'"]').prop("selected", true);
            $('#country_id option[value="' + data.COUNTRY_ID +'"]').prop("selected", true);
            $('#vehicle_brand_id option[value="' + data.VEHICLE_BRAND_ID +'"]').prop("selected", true);
            $('#stock option[value="' + data.STOCK +'"]').prop("selected", true);
            $("#vehicle_model_id").val(data.VEHICLE_MODEL_ID);
            $("#vehicle_model_name").val(data.VEHICLE_MODEL_NAME);
            $('#unit_id option[value="' + data.UNIT_ID +'"]').prop("selected", true);
            $('#unit_type_id option[value="' + data.UNIT_TYPE_ID +'"]').prop("selected", true);
            $("#prd_size").val(data.PRD_SIZE);
            $("#cost_price").val(data.COST_PRICE);
            $("#sale_price").val(data.SALE_PRICE);
            $("#real_sale_price").val(data.REAL_SALE_PRICE);
            $("#prd_min").val(data.PRD_MIN);
            $("#prd_remain").val(data.PRD_REMAIN);
            if(	$("#role").val()=='03' ){
                $("#prd_category_id").prop("disabled", true);
                $("#part_no").prop("readonly", true);
                if(data.PRD_CATEGORY_ID != '1'){
                    $("#cost_price").hide();
                }else{
                    $("#cost_price").show();
                }
            }else{
                $("#prd_category_id").prop("disabled", false);   
                $("#part_no").prop("readonly", false);
                $("#cost_price").show();
            }
         }
	 });
}
function confirmSave(e){
    $("#save-confirm").dialog({
        resizable: false,
        modal: true,
        title: "Modal",
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
		url: "./ProductSrvl",
		data: {
			method: "save",
			mode: mode,
			prd_id: $("#prd_id").val(),
			prd_name: $("#prd_name").val(),
			part_no: $("#part_no").val(),
			prd_type_id: $("#prd_type_id").val(),
			prd_brand_id: $("#prd_brand_id").val(),
			prd_category_id: $("#prd_category_id option:selected").val(),
			country_id: $("#country_id option:selected").val(),
			vehicle_brand_id: $("#vehicle_brand_id option:selected").val(),
			vehicle_model_id: $("#vehicle_model_id").val(),
			stock: $("#stock option:selected").val(),
			unit_id: $("#unit_id option:selected").val(),
			unit_type_id: $("#unit_type_id option:selected").val(),
			prd_size: $("#prd_size").val(),
			cost_price: $("#cost_price").val(),
			sale_price: $("#sale_price").val(),
			real_sale_price: $("#real_sale_price").val(),
			prd_min: $("#prd_min").val(),
			prd_remain: $("#prd_remain").val()
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

function productNameKeyPress(e){
    $("input#prd_name").autocomplete({
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
                     tb:"PRODUCT_NAME"
                 },
                 success: function(data) {
                     response(data);
                 }
             });
         },
 	    select: function(event, ui) {
 			event.preventDefault();
 			$("#prd_name").val(ui.item.value);
 		}
     });
	
}
function productBrandKeyPress(e) {
    $("input#prd_brand_id").autocomplete({
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
		}
    });
}
function productTypeKeyPress(e) {
    $("input#prd_type_id").autocomplete({
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
		}
    });
}
function vehicleModelKeyPress(e) {
    $("input#vehicle_model_id").autocomplete({
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
		}
    });
}

// btn_delete on click
function confirmDelete(){
	// binding detail to dialog
    var title = "ยืนยันการลบรายการ " + mode + " : " + $("#prd_id").val();
    $("#delete-confirm").dialog('option', 'title', title);
    
    $("#delete-confirm").dialog('open');
}

// delete Product id, after perform click delete confirm
function deleteData(){
	$.ajax({
		type: "POST",
		url: "./ProductSrvl",
		data: {
			method: "delete",
			mode: mode,
			prd_id: $("#prd_id").val()
		},
		success:function(data) {
			if(data == "1"){
				$("#mode").val("edit");
				alert("ลบข้อมูลเรียบร้อยแล้ว");
				window.close();
				
				// reload parent page, after perform delete and close page product details
				parent.window.opener.location.reload();
			}else{
				alert("ไม่สามารถลบข้อมูลได้");
			}
        }
	});
}