$(document).ready(function() {
	onReady();
	loadEmpDropdown();
	$('.datepicker').datepicker({ 
		autoclose: true, 
		format: 'dd/mm/yyyy' 
	});
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
	
	// Part no, call ajax to verify
	$('#dialog-verify-partno').dialog({ 
		autoOpen: false, 
		resizable: false, 
		modal: true,
        title: "Part No",
        height: 150,
        width: 400,
        buttons: {
            "OK": function () {
                $(this).dialog('close');
            }
        }
	});
	
	$('#dialog-product-remain').dialog({ 
		autoOpen: false, 
		resizable: false, 
		modal: true,
        title: "สินค้าคงเหลือ",
        height: 150,
        width: 500,
        buttons: {
            "OK": function () {
                $(this).dialog('close');
            }
        }
	});
}

function openFormDetail(e){
	if($("#mode").val()=="new"){
		window.open("","sell_form_detail");
		document.getElementById('sell_form_detail').submit();		
	}else{
		$("#mode").val("newedit");
		resetDetail();
		loadInvoiceHeader();
		getItemNo(tempInvoiceNo);
		setReadOnlyHeader();
	}
}
function defaultPageLoad(){
	if(mode=="edit"){
		$("#mode").val("edit");
		getSellProductDetail(tempInvoiceNo, itemNo);
		setReadOnlyHeader();
	}else if(mode=="newedit"){
		$("#mode").val("newedit");
		$("#part_no").focus();
		$("#old_cost").val("0");
		$("#last_sell_price").val("0");
		$("#discount_rate").val("0");
		$("#discount_amount").val("0");
		$("#extra_discount_rate").val("0");
		$("#extra_discount_amount").val("0");
		loadInvoiceHeader();
		getItemNo(tempInvoiceNo);
		setReadOnlyHeader();
	}else{
		mode = "new";
		$("#mode").val("new");
		$("#item_no").val("1");
		$("#old_cost").val("0");
		$("#last_sell_price").val("0");
		$("#discount_rate").val("0");
		$("#discount_amount").val("0");
		$("#extra_discount_rate").val("0");
		$("#extra_discount_amount").val("0");
		getTempInvoiceNo();
	}
}

/*auto complete*/
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
			getProductDetail(ui.item.id);
			
			// verify duplicate part_no in temp_invoice_no
			verifyPartNo();
			
			// dialog Product remain for part no
			getProductRemain();
		}
    });
}

/*functional*/
function getTempInvoiceNo(){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
		data: {
			method: "getTempInvoiceNo"
		},
		success:function(data){
			$("#temp_invoice_no").val(data.TEMP_INVOICE_NO);
			$("#temp_invoice_date").val(showDate(data.TEMP_INVOICE_DATE));
		}
	});
}
function getItemNo(tempInvoiceNo){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
        data: {
        	method: "getItemNo",
            temp_invoice_no: tempInvoiceNo
        },
        success:function(data) {
			var temp_inv = data.TEMP_INVOICE_NO;
			if(temp_inv==""){
				temp_inv = "1";
			}
            $("#item_no").val(data.ITEM_NO);
         }
	 });	
}
function calTotalNetAmount(){
	var cost = $("#cost").val()*1;
	var amount = $("#amount").val()*1;
	if(cost > amount ){
		alert("สินค้าขายราคาต่ำกว่าทุน! ราคาทุน="+$("#cost").val());
	}
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
	var amount_before_tax = total_amount - total_discount_amount - total_extra_discount_amount; // before summary tax_amount
	var total_net_amount = (total_amount - total_discount_amount - total_extra_discount_amount) + tax_amount;
	
	$('#amount_before_tax').val(amount_before_tax.toFixed(2));
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
}

// calculate to amount_before_tax
function calAmountBeforeTax() {
	// binding
	var cost = $("#cost").val()*1;
	var amount = $("#amount").val()*1;
	var quantity = $("#prd_quantity").val();
	var discount_rate = $("#discount_rate").val();
	var discount_amount = $("#discount_amount").val();
	var extra_discount_rate = $("#extra_discount_rate").val();
	var extra_discount_amount = $("#extra_discount_amount").val();
	var tax_rate = $("#tax_rate").prop("selected", true).val();
	
	var tax_amount = 0, total_discount_amount = 0, total_extra_discount_amount = 0;
	
	var total_amount = amount * quantity;
	
	// calculate
	total_discount_amount = (discount_amount * quantity) + ((total_amount * discount_rate)/100);
	total_extra_discount_amount = (((total_amount-total_discount_amount) * extra_discount_rate)/100) + (extra_discount_amount * quantity);
	tax_amount = (((total_amount - total_discount_amount)-total_extra_discount_amount) * tax_rate )/100;
	var amount_before_tax = total_amount - total_discount_amount - total_extra_discount_amount; // before summary tax_amount
	
	return amount_before_tax;
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
	        title: "ยืนยันการบันทึกขายสินค้า "+$("#mode").val()+" : "+$("#temp_invoice_no").val(),
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
		url: "./SellProductSrvl",
		data: {
			method: "save",
			mode: $("#mode").val(),
			temp_invoice_no: $("#temp_invoice_no").val(),
			temp_invoice_date: $("#temp_invoice_date").val(),
			invoice_no: $("#invoice_no").val(),
			item_no: $("#item_no").val(),
			invoice_date: $("#invoice_date").val(),
			invoice_type: $("#invoice_type").prop("selected",true).val(),
			cust_id: $("#cust_id").val(),
			part_no: $("#part_no").val(),
			emp_id: $("#emp_id").prop("selected",true).val(),
			amount: $("#amount").val(),
			last_sell_date: $("#last_sell_date").val(),
			last_sell_price: $("#last_sell_price").val(),
			old_cost: $("#old_cost").val(),
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
			note: $("#note").val(),
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
		url: "./SellProductSrvl",
		data: {
			method: "delete",
			mode: $("#mode").val(),
			temp_invoice_no: $("#temp_invoice_no").val(),
			invoice_no: $("#invoice_no").val(),
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
	$("#temp_invoice_no").attr('readonly', true);
	$("#temp_invoice_date").attr('disabled', true);
	$("#invoice_no").attr('readonly', true);
	$("#invoice_date").attr('disabled', true);
	$("#cust_id").attr('readonly', true);
	$("#emp_id").attr('disabled', true);
	$("#payment_term").attr('readonly', true);
	$("#payment_date").attr('disabled', true);
	$("#tax_rate").attr('disabled', true);
	$("#invoice_type").attr('disabled',true);
}
function setEnableHeader(){
	$("#temp_invoice_no").attr('readonly', false);
	$("#temp_invoice_date").attr('disabled', false);
	$("#invoice_no").attr('readonly', false);
	$("#invoice_date").attr('disabled', false);
	$("#cust_id").attr('readonly', false);
	$("#emp_id").attr('disabled', false);
	$("#payment_term").attr('readonly', false);
	$("#payment_date").attr('disabled', false);
	$("#tax_rate").attr('disabled', false);
	$("$invoice_type").attr('disabled',false);
}
function getRole(userId){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
        data: {
        	method: "getUserRole",
            userId: UserId
        },
        success:function(data) {
        	alert(data);
         }
	 });	
}
function resetDetail(){
	$("#last_sell_date").val("");
	$("#last_sell_price").val("0");
	$("#create_date").val("");
	$("#create_time").val("");
	$("#part_no").val("");
	$("#part_no").focus();
	$("#prd_name").val("");
	$("#amount").val("0");
	$("#prd_quantity").val("0");
	$("#unit_name").val("");
	$("#unit_type_name").val("");
	$("#total_amount").val("0");
	$("#old_cost").val("0");
	$("#discount_rate").val("0");
	$("#discount_amount").val("0");
	$("#extra_discount_rate").val("0");
	$("#extra_discount_amount").val("0");
	$("#note").val("");
	$("#total_net_amount").val("0");
}

/*retrieve data*/
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
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#telephone").val(data.TELEPHONE);
            $('#emp_id option[value="' + data.EMP_ID +'"]').prop("selected", true);
            $("#remark").val(data.REMARK);

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

function getProductDetail(prdId){
	$.ajax({
		type: "POST",
		url: "./ProductSrvl",
        data: {
        	method: "getinfo",
            part_no: prdId
        },
        success:function(data) {
            $("#unit_name").val(data.UNIT_NAME);
            $("#prd_name").val(data.PART_DETAIL);
            $("#unit_type_name").val(data.UNIT_TYPE_NAME);
            $("#cost").val(data.COST_PRICE);
            $("#old_cost").val(data.COST_PRICE);
            if(	$("#role").val()=='03' ){
            	if( data.PRD_CATEGORY_ID == '1' ){
                	$("#real_cost").val(data.COST_PRICE);
                    $("#std_cost").val(data.REAL_SALE_PRICE);
            	}else{
                	$("#real_cost").val(data.REAL_SALE_PRICE);
                	$("#std_cost").val(data.REAL_SALE_PRICE);
            	}
            }else{
            	$("#real_cost").val(data.COST_PRICE);
                $("#std_cost").val(data.REAL_SALE_PRICE);
            }
            $("#last_sell_price").val("0");
            getLastPrice();
         }
	 });
}
function getLastPrice(){
    $("#last_sell_date").val("");
	$("#last_sell_price").val("0");
	$("#last_discount_price").val("0");

	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
        data: {
        	method: "getLastPrice",
            part_no: $("#part_no").val(),
			cust_id: $("#cust_id").val(),
        },
        success:function(data) {
        	if(data!=null){
                $("#last_sell_date").val(showDate(data.TEMP_INVOICE_DATE));
                $("#last_sell_price").val(data.AMOUNT);        		
        		$("#last_discount_price").val(data.DISCOUNT);
        	}else{
        		$("#last_sell_price").val("10");
        		$("#last_discount_price").val("0");
        	}
         },
	 });	
}

function getSellProductDetail(tempInvoiceNo, itemNo){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
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
            $("#cust_id").val(data.CUST_ID);
            $("#cust_name").val(data.CUST_NAME);
            $("#cust_addr").val(data.ADDRESS1);
            $("#remark").val(data.REMARK);
            $("#telephone").val(data.TELEPHONE);
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#payment_date").val(showDate(data.PAYMENT_DATE));
            $('#emp_id option[value="' + data.EMP_ID +'"]').prop("selected", true);
            $('#invoice_type option[value="' + data.INVOICE_TYPE +'"]').prop("selected", true);
            $("#item_no").val(data.ITEM_NO);
            $("#part_no").val(data.PART_NO);
            $("#prd_name").val(data.PART_DETAIL);
            $("#last_sell_date").val(showDate(data.LAST_SELL_DATE));
            $("#last_sell_price").val(data.LAST_SELL_PRICE);
            $("#amount").val(data.AMOUNT);
            $("#prd_quantity").val(data.QUANTITY);
            $("#unit_name").val(data.UNIT_NAME);
            $("#unit_type_name").val(data.UNIT_TYPE_NAME);
            $("#total_amount").val(data.TOTAL_AMOUNT);
            $("#discount_rate").val(data.DISCOUNT_RATE);
            $("#discount_amount").val(data.DISCOUNT_AMOUNT);
            $("#total_discount_amount").val(data.TOTAL_DISCOUNT_AMOUNT);
            $("#extra_discount_rate").val(data.EXTRA_DISCOUNT_RATE);
            $("#extra_discount_amount").val(data.EXTRA_DISCOUNT_AMOUNT);
            $("#total_extra_discount_amount").val(data.TOTAL_EXTRA_DISCOUNT_AMOUNT);
            $("#tax_rate").val(data.TAX_RATE);   
            $("#tax_amount").val(data.TAX_AMOUNT);
            $("#total_net_amount").val(data.TOTAL_NET_AMOUNT);
            $("#note").val(data.NOTE);
            $("#old_cost").val(data.OLD_COST);
            if(	$("#role").val()=='03' ){
            	if( data.PRD_CATEGORY_ID == '1' ){
                	$("#real_cost").val(data.OLD_COST);
                    $("#std_cost").val(data.REAL_SALE_PRICE);
            	}else{
                	$("#real_cost").val(data.REAL_SALE_PRICE);
                	$("#std_cost").val(data.REAL_SALE_PRICE);            		
            	}
            }else{
            	$("#real_cost").val(data.OLD_COST);
                $("#std_cost").val(data.REAL_SALE_PRICE);
            }
            $("#create_date").val(showDate(data.CREATE_DATE));
            $("#create_time").val(data.CREATE_TIME);
        	if($("#invoice_no").val()!=""){
        		$("#btn_save").hide();
        		$("#btn_new").hide();
        		$("#btn_delete").hide();
        	}
        	
        	// amount_before_tax
        	var amount_before_tax = calAmountBeforeTax();
        	$('#amount_before_tax').val(amount_before_tax.toFixed(2));
		}
	});
}
function loadInvoiceHeader(){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
        data: {
        	method: "loadheader",
            temp_invoice_no: tempInvoiceNo
        },
        success:function(data) {
        	$("#temp_invoice_no").val(data.TEMP_INVOICE_NO);
        	$("#temp_invoice_date").val(showDate(data.TEMP_INVOICE_DATE));
            $("#invoice_no").val(data.INVOICE_NO);
            $("#invoice_date").val(showDate(data.INVOICE_DATE));
            $('#invoice_type option[value="' + data.INVOICE_TYPE +'"]').prop("selected", true);            
            $("#payment_term").val(data.PAYMENT_TERM);
            $("#payment_date").val(showDate(data.PAYMENT_DATE));
            $("#cust_id").val(data.CUST_ID);
            $("#cust_name").val(data.CUST_NAME);
            $("#cust_addr").val(data.ADDRESS1);
            $("#telephone").val(data.TELEPHONE);
            $("#remark").val(data.REMARK);
            $('#tax_rate option[value="' + data.TAX_RATE +'"]').prop("selected", true);
            $('#emp_id option[value="' + data.EMP_ID +'"]').prop("selected", true);            
         }
	 });
}
function loadEmpDropdown(){
	$.ajax({
		type: "POST",
		url: "./LoadDropDownListSrvl",
        data: {
        	tb: "USERS"
        },
        success:function(data) {
            $("select#emp_id").html(data);
        	defaultPageLoad();
        }
	 });
}

// verify duplicate part_no in temp_invoice_no
function verifyPartNo(){
	$.ajax({
		type: "POST",
		url: "./SellProductSrvl",
        data: {
        	method: "getSellProductByPartNoAndInvoiceNo",
            part_no: $("#part_no").val(),
            temp_invoice_no: $("#temp_invoice_no").val()
        },
        success:function(json) {
        	// json is not empty - show dialog [found data from database]
            if (!jQuery.isEmptyObject(json)) {
            	$('#dialog-verify-partno').dialog('open');
            }
        }
	 });
}

// after event selected auto complete
function getProductRemain(){
	$.ajax({
		type: "POST",
		url: "./ProductSrvl",
        data: {
        	method: "getStock",
            part_no: $("#part_no").val()
        },
        success:function(data) {
        	$('#lb_product_remain').text(data.PRD_REMAIN); // demo fix string value
        	$('#dialog-product-remain').dialog('open');
        }
	 });	
}

// Require field, before Save
function validateForm() {
	var amount = $('#amount').val();
	var prd_quantity = $('#prd_quantity').val();
	var invoice_date = $('#invoice_date').val();
	var temp_invoice_date = $('#temp_invoice_date').val();
	var payment_date = $('#payment_date').val();
	
	// is empty string
	if (amount === '' || prd_quantity === '' || invoice_date === '' || payment_date === '' || temp_invoice_date === '') {
		return true;
	}else{
		return false;
	}
}