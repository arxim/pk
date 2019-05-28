$(document).ready(function() {
	if(mode=="new" || mode==""){
		$("#mode").val("new");
		getDocNo();
		if($("#prd_quantity").val()==""){
			$("#prd_quantity").val("1");			
		}
	}else{
		defaultPageLoad()
	}
});
function closePage(e){
	window.close();
}
function newPage(){
	window.open("return_form_detail.jsp","_self");
}
function editNewPage(){
	getItemNo($("#doc_no").val());
    $("#cust_doc_no").val("");
    $("#cn_date").val("");
    $("#invoice_no").val("");
    $("#invoice_date").val("");
    $("#part_no").val("");
    $("#prd_name").val("");
    $("#amount").val("");
    $("#prd_quantity").val("");
    $("#total_amount").val("");
    $("#note").val("");
	$("#mode").val("new");
	mode = "new";
}
function defaultPageLoad(){
	if(mode=="edit"){
		$("#mode").val("edit");
		getBuyProductDetail(docNo, itemNo);
	}else if(mode=="newedit"){
		alert("newEdit : "+mode);
	}else{
		alert("Non");
	}
}
function getBuyProductDetail(docNo, itemNo){
	$.ajax({
		type: "POST",
		url: "./ReturnProductSrvl",
		data: {
			method: "fillform",
			doc_no: docNo,
			item_no: itemNo
		},
		success:function(data) {
			$("#doc_no").val(data.DOC_NO);
            $("#doc_date").val(showDate(data.DOC_DATE));
            $("#cust_id").val(data.CUST_ID);
            $("#cust_name").val(data.CUST_NAME);
            $("#cust_addr").val(data.ADDRESS1);
            $("#cust_doc_no").val(data.CUST_DOC_NO);
            $("#cn_date").val(showDate(data.CN_DATE));
            $("#invoice_no").val(data.INVOICE_NO);
            $("#invoice_date").val(showDate(data.INVOICE_DATE));
            $("#part_no").val(data.PART_NO);
            $("#prd_name").val(data.PRD_NAME);
            $("#item_no").val(data.ITEM_NO);
            $("#amount").val(data.AMOUNT);
            $("#prd_quantity").val(data.QUANTITY);
            $("#total_amount").val(data.TOTAL_AMOUNT);
            $("#note").val(data.REMARK);
		}
	});
}
function confirmSave(e){
    $("#save-confirm").dialog({
        resizable: false,
        modal: true,
        title: "ยืนยันการบันทึกคืนสินค้า "+$("#mode").val(),
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
		url: "./ReturnProductSrvl",
		data: {
			method: "save",
			mode: mode,
			doc_no: $("#doc_no").val(),
			doc_date: $("#doc_date").val(),
			item_no: $("#item_no").val(),
			cust_id: $("#cust_id").val(),
			cust_doc_no: $("#cust_doc_no").val(),
			cn_date: $("#cn_date").val(),
			invoice_no: $("#invoice_no").val(),
			invoice_date: $("#invoice_date").val(),
			part_no: $("#part_no").val(),
			amount: $("#amount").val(),
			quantity: $("#prd_quantity").val(),
			total_amount: $("#total_amount").val(),
			note: $("#note").val(),
			user_id: $("#user_id").val(),
		},
		success:function(data) {
			if(data == "1"){
				$("#mode").val("edit");
				alert("บันทึกข้อมูลเรียบร้อยแล้ว");
			}else{
				alert("ไม่สามารถบันทึกข้อมูลได้");
			}
        }
	});
}
function confirmDelete(e){
    $("#delete-confirm").dialog({
        resizable: false,
        modal: true,
        title: "ยืนยันลบรายการ "+$("#mode").val()+" : "+$("#item_no").val()+" : "+$("#doc_no").val(),
        height: 250,
        width: 400,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
                deleteData(e);
            },
            "No": function () {
                $(this).dialog('close');
            }
        }
    });
}
function deleteData(e){
	$.ajax({
		type: "POST",
		url: "./ReturnProductSrvl",
		data: {
			method: "delete",
			mode: mode,
			doc_no: $("#doc_no").val(),
			item_no: $("#item_no").val(),
		},
		success:function(data) {
			if(data == "1"){
				$("#mode").val("edit");
				alert("ลบข้อมูลเรียบร้อยแล้ว");
				window.close();
			}else{
				alert("ไม่สามารถลบข้อมูลได้");
			}
        }
	});
}
function getDocNo(){
	$.ajax({
		type: "POST",
		url: "./ReturnProductSrvl",
		data: {
			method: "getDocNo"
		},
		success:function(data){
			$("#doc_no").val("R"+data.DOC_NO);
			$("#doc_date").val(showDate(data.DOC_DATE));
			getItemNo("R"+data.DOC_NO);
		}
	});
}
function getItemNo(docNo){
	$.ajax({
		type: "POST",
		url: "./ReturnProductSrvl",
        data: {
        	method: "getItemNo",
            doc_no: docNo
        },
        success:function(data) {
			var temp_doc = data.DOC_NO;
			if(temp_doc==""){
				temp_doc = "1";
			}
            $("#item_no").val(data.ITEM_NO);
         }
	 });	
}
function productKeyPress(e) {
    $("input#part_no").autocomplete({
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
                    tb:"PRODUCT"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#part_no").val(ui.item.id);
			$("#prd_name").val(ui.item.value);
		}
    });
}
function custKeyPress(e) {
    $("input#cust_id").autocomplete({
        minLength: 1,
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        highlight: true,
        source: function(request, response) {
            $.ajax({
            	type: "POST",
                url: "./AutoCompleteRetriveSrvl",
                dataType: "json",
                data: {
                    term: request.term, //keyin textfield
                    tb:"CUSTOMER"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#cust_id").val(ui.item.id);
			$("#cust_name").val(ui.item.value);
			getCustomerDetail(ui.item.id);
		}
    });
}
function getCustomerDetail(custId){
	$.ajax({
		type: "POST",
		url: "./CustomerSrvl",
        data: {
        	method: "getdata",
            cust_id: custId
        },
        success:function(data) {
            $("#cust_addr").val(data.ADDRESS1);
         }
	 });
}
function calTotalNetAmount(){
	var amount = $("#amount").val()*1;
	var quantity = $("#prd_quantity").val();
	var total_amount = amount*quantity;
	$("#total_amount").val(total_amount.toFixed(2));	
}