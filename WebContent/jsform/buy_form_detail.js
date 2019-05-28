$(document).ready(function() {
	// initial - assign default
	onReady();
	
	defaultPageLoad();
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
	//$('.datepicker').datepicker({});	
	$('#btn_close').click(function() {
		 window.close();
	})
});
function onReady() {
	// Require field
	$('#dialog-validate').dialog({ 
		autoOpen: false, 
		resizable: false, 
		modal: true,
        title: "แจ้งเตือนข้อมูลเพื่อการบันทึก",
        height: 150,
        width: 400,
        buttons: {
            "OK": function () {
                $(this).dialog('close');
            }
        }
	});
}
function openFormDetail(e){
	if($("#mode").val()=="new"){
		window.open("","buy_form_detail");
		document.getElementById('buy_form_detail').submit();		
	}else{
		$("#mode").val("newedit");
		resetDetail();
		loadInvoiceHeader();
		getItemNo(tempInvoiceNo);
		setReadOnlyHeader();
	}
}
function defaultPageLoad(){
	$("#last_buy_price").val("0");
	if(mode=="edit"){
		$("#mode").val("edit");
		getBuyProductDetail(tempInvoiceNo, itemNo);
		setReadOnlyHeader();
	}else if(mode=="newedit"){
		$("#mode").val("newedit");
		$("#part_no").focus();
		$("#discount_rate").val("0");
		$("#discount_amount").val("0");
		$("#extra_discount_rate").val("0");
		$("#extra_discount_amount").val("0");
		$("#tax_amount").val("0");
		loadInvoiceHeader();
		getItemNo(tempInvoiceNo);
		setReadOnlyHeader();
	}else{
		mode = "new";
		$("#mode").val("new");
		$("#part_no").focus();
		$("#item_no").val("1");
		$("#discount_rate").val("0");
		$("#discount_amount").val("0");
		$("#extra_discount_rate").val("0");
		$("#extra_discount_amount").val("0");
		$("#tax_amount").val("0");
		getTempInvoiceNo();
	}
}
function resetDetail(){
	$("#part_no").val("");
	$("#part_detail").val("");
	$("#amount").val("0");
	$("#prd_quantity").val("0");
	$("#unit_name").val("");
	$("#unit_type_name").val("");
	$("#last_buy_price").val("0");
	$("#total_amount").val("0");
	$("#discount_rate").val("0");
	$("#discount_amount").val("0");
	$("#total_discount_amount").val("0");
	$("#extra_discount_rate").val("0");
	$("#extra_discount_amount").val("0");
	$("#total_extra_discount_amount").val("0");
	$("#tax_amount").val("0");
	$("#note").val("");
	$("#total_net_amount").val("0");
	$("#amount_before_tax").val("0");
}
function getTempInvoiceNo(){
	$.ajax({
		type: "POST",
		url: "./BuyProductSrvl",
		data: {
			method: "getTempInvoiceNo"
		},
		success:function(data){
			$("#temp_invoice_no").val(data.TEMP_INVOICE_NO);
			$("#temp_invoice_date").val(showDate(data.TEMP_INVOICE_DATE));
		}
	});
}

/*auto complete*/
function supKeyPress(e) {
    $("input#sup_id").autocomplete({
       // width: 300,
        max: 10,
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
                    tb:"SUPPLIER"
                },
                success: function(data) {
                    response(data);
                }
            });
        },
	    select: function(event, ui) {
			event.preventDefault();
			$("#sup_id").val(ui.item.id);
			$("#sup_name").val(ui.item.value);
			getSupplierDetail(ui.item.id);
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
			$("#part_detail").val(ui.item.value);
			getProductDetail(ui.item.id);
		}
    });
}

/*functional*/
function getItemNo(invoiceNo){
	$.ajax({
		type: "POST",
		url: "./BuyProductSrvl",
        data: {
        	method: "getItemNo",
            temp_invoice_no: tempInvoiceNo
        },
        success:function(data) {
        	var tempItem = data.ITEM_NO;
        	if(tempItem==""){
        		tempItem = "1";
        	}
            $("#item_no").val(tempItem);
         }
	 });	
}
function getLastPrice(){
	$.ajax({
		type: "POST",
		url: "./BuyProductSrvl",
        data: {
        	method: "getLastPrice",
            part_no: $("#part_no").val(),
			sup_id: $("#sup_id").val(),
        },
        success:function(data) {
        	if(data!=null){
                $("#last_buy_price").val(data.AMOUNT);
                $("#last_discount").val(data.DISCOUNT);
        	}else{
        		$("#last_buy_price").val("0");
                $("#last_discount").val("0");
        	}
         }
	 });	
}
function calTotalNetAmount(){
	var amount = $("#amount").val();
	var quantity = $("#prd_quantity").val();
	var tax_amount = 0;
	var total_amount = amount*quantity;
	$("#total_amount").val(total_amount.toFixed(2));	
	var discount_rate = $("#discount_rate").val();
	var discount_amount = $("#discount_amount").val();
	var total_discount_amount = 0;
	var extra_discount_rate = $("#extra_discount_rate").val();
	var extra_discount_amount = $("#extra_discount_amount").val();
	var total_extra_discount_amount = 0;
	var tax_rate = $("#tax_rate").prop("selected", true).val();
	total_discount_amount = (discount_amount * quantity) + ((total_amount * discount_rate)/100);
	total_extra_discount_amount = (((total_amount-total_discount_amount) * extra_discount_rate)/100) + (extra_discount_amount * quantity);
	tax_amount = (((total_amount - total_discount_amount)-total_extra_discount_amount) * tax_rate )/100;
	var total_net_amount = (total_amount - total_discount_amount - total_extra_discount_amount) + tax_amount ;
	var total_amount_bef_tax = (total_amount - total_discount_amount - total_extra_discount_amount);
	$("#total_discount_amount").val(total_discount_amount);
	$("#total_extra_discount_amount").val(total_extra_discount_amount);
	$("#tax_amount").val(tax_amount.toFixed(2));
	$("#total_net_amount").val(total_net_amount.toFixed(2));
	if($("#extra_discount_rate").val() != "0" && $("#extra_discount_rate").val() != "0.00"){
		$("#extra_discount_amount").attr('readonly', true);
	}else{
		$("#extra_discount_amount").attr('readonly', false);		
	}
	if($("#discount_rate").val() != "0" && $("#discount_rate").val() != "0.00"){
		$("#discount_amount").attr('readonly', true);
	}else{
		$("#discount_amount").attr('readonly', false);		
	}
	$("#amount_before_tax").val(total_amount_bef_tax.toFixed(2));
}
function confirmSave(e){
	if (validateForm()) {
		$("#dialog-validate").dialog("open");
		return;
	}else{
		tempInvoiceNo = $("#temp_invoice_no").val();
	    $("#save-confirm").dialog({
	        resizable: false,
	        modal: true,
	        title: "ยืนยันการบันทึกซื้อสินค้า "+$("#mode").val()+" : "+$("#temp_invoice_no").val(),
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
}
function saveData(e){
	$.ajax({
		type: "POST",
		url: "./BuyProductSrvl",
		data: {
			method: "save",
			mode: $("#mode").val(),
			temp_invoice_no: $("#temp_invoice_no").val(),
			temp_invoice_date: $("#temp_invoice_date").val(),
			invoice_no: $("#invoice_no").val(),
			item_no: $("#item_no").val(),
			invoice_date: $("#invoice_date").val(),
			sup_id: $("#sup_id").val(),
			part_no: $("#part_no").val(),
			amount: $("#amount").val(),
			last_buy_price: $("#last_buy_price").val(),
			quantity: $("#prd_quantity").val(),
			payment_term: $("#payment_term").val(),
			payment_date: $("#payment_date").val(),
			total_amount: $("#total_amount").val(),
			discount_rate: $("#discount_rate").val(),
			discount_amount: $("#discount_amount").val(),
			total_discount_amount: $("#total_discount_amount").val(),
			extra_discount_rate: $("#extra_discount_rate").val(),
			extra_discount_amount: $("#extra_discount_amount").val(),
			total_extra_discount_amount: $("#total_extra_discount_amount").val(),
			tax_rate: $("#tax_rate").prop("selected", true).val(),
			tax_amount: $("#tax_amount").val(),
			total_net_amount: $("#total_net_amount").val(),
			remark: $("#remark").val(),
			active: $("#active").val(),
			create_user_id: $("#user_id").val(),
			update_user_id: $("#user_id").val(),
		},
		success:function(data) {
			if(data == "1"){
				$("#mode").val("edit");
				//if($("#mode").val()=="new"){
				//	$("#mode").val("newedit");					
				//}
				alert("บันทึกข้อมูลเรียบร้อยแล้ว");
			}else{
				alert("ไม่สามารถบันทึกข้อมูลได้");
			}
			$("#part_no").focus();
        }
	});
}
function confirmDelete(e){
    $("#delete-confirm").dialog({
        resizable: false,
        modal: true,
        title: "ยืนยันการลบรายการ "+$("#mode").val()+" : "+$("#item_no").val(),
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
		url: "./BuyProductSrvl",
		data: {
			method: "delete",
			mode: $("#mode").val(),
			temp_invoice_no: $("#temp_invoice_no").val(),
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
/*action*/
function setReadOnlyHeader(){
	$("#invoice_no").attr('readonly', true);
	$("#invoice_date").attr('disabled', true);
	$("#sup_id").attr('readonly', true);
	$("#sup_name").attr('readonly', true);
	$("#sup_addr").attr('readonly', true);
	$("#telephone").attr('readonly', true);
	$("#payment_term").attr('readonly', true);
	$("#payment_date").attr('disabled', false);
}
function setEnableHeader(){
	$("#invoice_no").attr('readonly', false);
	$("#invoice_date").attr('disabled', false);
	$("#sup_id").attr('readonly', false);
	$("#sup_name").attr('readonly', false);
	$("#sup_addr").attr('readonly', false);
	$("#telephone").attr('readonly', false);
	$("#payment_term").attr('readonly', false);
	$("#payment_date").attr('disabled', false);
}
function validateForm() {
	var amount = $('#amount').val();
	var prd_quantity = $('#prd_quantity').val();
	var invoice_date = $('#invoice_date').val();
	var payment_date = $('#payment_date').val();
	var temp_invoice_date = $('#temp_invoice_date').val();

	// is empty string
	if (amount === '' || prd_quantity === '' || invoice_date === '' || payment_date === '' || temp_invoice_date === '') {
		return true;
	}else{
		return false;
	}

}

/*retrieve data*/
function getSupplierDetail(supId){
	$.ajax({
		type: "POST",
		url: "./SupplierSrvl",
        data: {
        	method: "getdata",
            sup_id: supId
        },
        success:function(data) {
            $("#sup_addr").val(data.ADDRESS1);
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#telephone").val(data.TELEPHONE);
            $("#sremark").val(data.REMARK);
            
            getPaymentDate();
         }
	 });
}
function getPaymentDate(){
	var payment_term = $("#payment_term").val();
	var sell_date = $("#invoice_date").val();
	if(payment_term == ""){
		payment_term = 30;
	}
	if(sell_date == ""){
		sell_date = $("#temp_invoice_date").val();
	}
	$("#payment_date").datepicker("setDate", paymentDate(sell_date,(parseInt(payment_term) / 30)));
}

function getProductDetail(partNo){
	$.ajax({
		type: "POST",
		url: "./ProductSrvl",
        data: {
        	method: "getinfo",
            part_no: partNo
        },
        success:function(data) {
            $("#unit_name").val(data.UNIT_NAME);
            $("#unit_type_name").val(data.UNIT_TYPE_NAME);
            getLastPrice();
         }
	 });
}
function getBuyProductDetail(tempInvoiceNo, itemNo){
	$.ajax({
		type: "POST",
		url: "./BuyProductSrvl",
		data: {
			method: "fillform",
			temp_invoice_no: tempInvoiceNo,
			item_no: itemNo
		},
		success:function(data) {
			$("#temp_invoice_no").val(data.TEMP_INVOICE_NO);
            $("#temp_invoice_date").val(showDate(data.TEMP_INVOICE_DATE));
            $("#invoice_no").val(data.INVOICE_NO);
            $("#invoice_date").val(showDate(data.INVOICE_DATE));
            $("#sup_id").val(data.SUP_ID);
            $("#sup_name").val(data.SUP_NAME);
            $("#sup_addr").val(data.ADDRESS1);
            $("#sremark").val(data.SREMARK);
            $("#telephone").val(data.TELEPHONE);
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#payment_date").val(showDate(data.PAYMENT_DATE));
            $("#item_no").val(data.ITEM_NO);
            $("#part_no").val(data.PART_NO);
            $("#part_detail").val(data.PART_DETAIL);
            $("#amount").val(data.AMOUNT);
            $("#prd_quantity").val(data.QUANTITY);
            $("#unit_name").val(data.UNIT_NAME);
            $("#total_amount").val(data.TOTAL_AMOUNT);
            $("#discount_rate").val(data.DISCOUNT_RATE);
            if(data.DISCOUNT_RATE!="0" && data.DISCOUNT_RATE!="0.00" && data.DISCOUNT_RATE!=""){
            	$("#discount_amount").attr('readonly', true);
            }
            $("#discount_amount").val(data.DISCOUNT_AMOUNT);
            $("#extra_discount_rate").val(data.EXTRA_DISCOUNT_RATE);
            if(data.EXTRA_DISCOUNT_RATE != "0" && data.EXTRA_DISCOUNT_RATE != "0.00" && data.EXTR_DISCOUNT_RATE != ""){
            	$("#extra_discount_amount").attr('readonly', true);
            }
            $("#extra_discount_amount").val(data.EXTRA_DISCOUNT_AMOUNT);
            $("#tax_rate").val(data.TAX_RATE);   
            $("#tax_amount").val(data.TAX_AMOUNT);
            $("#total_net_amount").val(data.TOTAL_NET_AMOUNT);
            $("#remark").val(data.REMARK);
            $("#last_buy_price").val(data.LAST_BUY_PRICE);
            $("#create_date").val(showDate(data.CREATE_DATE));
            $("#create_time").val(data.CREATE_TIME);
        	$('#amount_before_tax').val(data.AMOUNT_BEF_TAX);

		}
	});
}
function loadInvoiceHeader(){
	$.ajax({
		type: "POST",
		url: "./BuyProductSrvl",
        data: {
        	method: "loadheader",
            temp_invoice_no: tempInvoiceNo
        },
        success:function(data) {
            $("#temp_invoice_no").val(data.TEMP_INVOICE_NO);
            $("#temp_invoice_date").val(showDate(data.TEMP_INVOICE_DATE));
            $("#invoice_no").val(data.INVOICE_NO);
            $("#invoice_date").val(showDate(data.INVOICE_DATE));
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#payment_date").val(showDate(data.PAYMENT_DATE));
            $("#sup_id").val(data.SUP_ID);
            $("#sup_name").val(data.SUP_NAME);
            $("#sup_addr").val(data.ADDRESS1);
            $("#sremark").val(data.SREMARK);
            $("#telephone").val(data.TELEPHONE);
            $("#mobile").val(data.MOBILE);
            $('#tax_rate option[value="' + data.TAX_RATE +'"]').prop("selected", true);            
         }
	 });
}