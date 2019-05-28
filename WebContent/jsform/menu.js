function validateLogin(){
	if(token != ''){
		$("#token").val(token);
		getMenu();
	}else{
		window.open("index.html","_self");
	}		
}
function getMenu(){
	$.ajax({
		type: "POST",
		url: "./MenuSrvl",
        data: {
        	login_name: user_id,
        },
        success:function(data) {
        	if(Object.keys(data.data).length<=1){
            	$("#product_type").hide();
				$("#div_product_type").hide();
            	$("#product_brand").hide();
				$("#div_product_brand").hide();
            	$("#product_class").hide();
				$("#div_product_class").hide();
            	$("#product_form").hide();
				$("#div_product_form").hide();
            	$("#supplier_form_search").hide();
				$("#div_supplier_form_search").hide();
            	$("#customer_form_search").hide();
				$("#div_customer_form_search").hide();
            	$("#unit").hide();
				$("#div_unit").hide();
            	$("#unit_type").hide();
				$("#div_unit_type").hide();
            	$("#vehicle_brand").hide();
				$("#div_vehicle_brand").hide();
            	$("#vehicle_model").hide();
				$("#div_vehicle_model").hide();
            	$("#area").hide();
				$("#div_area").hide();
            	$("#world").hide();
				$("#div_world").hide();
            	$("#buy_form_search").hide();
				$("#div_buy_form_search").hide();
            	$("#sell_form_search").hide();
				$("#div_sell_form_search").hide();
            	$("#return_form_search").hide();
				$("#div_return_form_search").hide();
            	$("#claim_form_search").hide();
				$("#div_claim_form_search").hide();
            	$("#customer_billing").hide();
				$("#div_customer_billing").hide();
            	$("#supplier_billing").hide();
				$("#div_supplier_billing").hide();
            	$("#product_report").hide();
				$("#div_product_report").hide();
            	$("#benefit_record").hide();
				$("#div_benefit_record").hide();
            	$("#buy_record").hide();
				$("#div_buy_record").hide();
            	$("#sell_record").hide();
				$("#div_sell_record").hide();
            	$("#users").hide();
				$("#div_users").hide();
        		
        	}else{
        		if(data.data[0][3]=="1"){
	            	$("#product_type").attr("href", data.data[0][2]);
					$("#div_product_type").show();
        		}else{
	            	$("#product_type").hide();
					$("#div_product_type").hide();
        		}
        		if(data.data[1][3]=="1"){
	            	$("#product_brand").attr("href", data.data[1][2]);
					$("#div_product_brand").show();
        		}else{
	            	$("#product_brand").hide();
					$("#div_product_brand").hide();
        		}
        		if(data.data[2][3]=="1"){
	            	$("#product_class").attr("href", data.data[2][2]);
					$("#div_product_class").show();
        		}else{
	            	$("#product_class").hide();
					$("#div_product_class").hide();
        		}
        		if(data.data[3][3]=="1"){
	            	$("#product_form").attr("href", data.data[3][2]);
					$("#div_product_form").show();
        		}else{
	            	$("#product_form").hide();
					$("#div_product_form").hide();
        		}
        		if(data.data[4][3]=="1"){
	            	$("#supplier_form_search").attr("href", data.data[4][2]);
					$("#div_supplier_form_search").show();
        		}else{
	            	$("#supplier_form_search").hide();
					$("#div_supplier_form_search").hide();
        		}
        		if(data.data[5][3]=="1"){
	            	$("#customer_form_search").attr("href", data.data[5][2]);
					$("#div_customer_form_search").show();
        		}else{
	            	$("#customer_form_search").hide();
					$("#div_customer_form_search").hide();
        		}
        		if(data.data[6][3]=="1"){
	            	$("#unit").attr("href", data.data[6][2]);
					$("#div_unit").show();
        		}else{
	            	$("#unit").hide();
					$("#div_unit").hide();
        		}
        		if(data.data[7][3]=="1"){
	            	$("#unit_type").attr("href", data.data[7][2]);
					$("#div_unit_type").show();
        		}else{
	            	$("#unit_type").hide();
					$("#div_unit_type").hide();
        		}
        		if(data.data[8][3]=="1"){
	            	$("#vehicle_brand").attr("href", data.data[8][2]);
					$("#div_vehicle_brand").show();
        		}else{
	            	$("#vehicle_brand").hide();
					$("#div_vehicle_brand").hide();
        		}
        		if(data.data[9][3]=="1"){
	            	$("#vehicle_model").attr("href", data.data[9][2]);
					$("#div_vehicle_model").show();
        		}else{
	            	$("#vehicle_model").hide();
					$("#div_vehicle_model").hide();
        		}
        		if(data.data[10][3]=="1"){
	            	$("#area").attr("href", data.data[10][2]);
					$("#div_area").show();
        		}else{
	            	$("#area").hide();
					$("#div_area").hide();
        		}
        		if(data.data[11][3]=="1"){
	            	$("#world").attr("href", data.data[11][2]);
					$("#div_world").show();
        		}else{
	            	$("#world").hide();
					$("#div_world").hide();
        		}
        		if(data.data[12][3]=="1"){
	            	$("#buy_form_search").attr("href", data.data[12][2]);
					$("#div_buy_form_search").show();
        		}else{
	            	$("#buy_form_search").hide();
					$("#div_buy_form_search").hide();
        		}	        
        		if(data.data[13][3]=="1"){
	            	$("#sell_form_search").attr("href", data.data[13][2]);
					$("#div_sell_form_search").show();
        		}else{
	            	$("#sell_form_search").hide();
					$("#div_sell_form_search").hide();
        		}	        
        		if(data.data[14][3]=="1"){
	            	$("#return_form_search").attr("href", data.data[14][2]);
					$("#div_return_form_search").show();
        		}else{
	            	$("#return_form_search").hide();
					$("#div_return_form_search").hide();
        		}	        
        		if(data.data[15][3]=="1"){
	            	$("#claim_form_search").attr("href", data.data[15][2]);
					$("#div_claim_form_search").show();
        		}else{
	            	$("#claim_form_search").hide();
					$("#div_claim_form_search").hide();
        		}	        
        		if(data.data[16][3]=="1"){
	            	$("#customer_billing").attr("href", data.data[16][2]);
					$("#div_customer_billing").show();
        		}else{
	            	$("#customer_billing").hide();
					$("#div_customer_billing").hide();
        		}	        
        		if(data.data[17][3]=="1"){
	            	$("#supplier_billing").attr("href", data.data[17][2]);
					$("#div_supplier_billing").show();
        		}else{
	            	$("#supplier_billing").hide();
					$("#div_supplier_billing").hide();
        		}	        
        		if(data.data[18][3]=="1"){
	            	$("#product_report").attr("href", data.data[18][2]);
					$("#div_product_report").show();
        		}else{
	            	$("#product_report").hide();
					$("#div_product_report").hide();
        		}	        
        		if(data.data[19][3]=="1"){
	            	$("#benefit_record").attr("href", data.data[19][2]);
					$("#div_benefit_record").show();
        		}else{
	            	$("#benefit_record").hide();
					$("#div_benefit_record").hide();
        		}	        
        		if(data.data[20][3]=="1"){
	            	$("#buy_record").attr("href", data.data[20][2]);
					$("#div_sbuy_record").show();
        		}else{
	            	$("#buy_record").hide();
					$("#div_buy_record").hide();
        		}	        
        		if(data.data[21][3]=="1"){
	            	$("#sell_record").attr("href", data.data[21][2]);
					$("#div_sell_record").show();
        		}else{
	            	$("#sell_record").hide();
					$("#div_sell_record").hide();
        		}	        
        		if(data.data[22][3]=="1"){
	            	$("#users").attr("href", data.data[22][2]);
					$("#div_users").show();
        		}else{
	            	$("#users").hide();
					$("#div_users").hide();
        		}
        	}
    		$("#role").hide();
    		$("#div_role").hide();
    		$("#menu").hide();
    		$("#div_menu").hide();

        }
	 });
}

function Home(){
	$("#setting").show();
	$("#operation").show();
	$("#report").show();
	$("#admin").show();
}
function Setting(){
	$("#setting").show();
	$("#operation").hide();
	$("#report").hide();
	$("#admin").hide();
}
function Operation(){
	$("#setting").hide();
	$("#operation").show();
	$("#report").hide();
	$("#admin").hide();
}
function Report(){
	$("#setting").hide();
	$("#operation").hide();
	$("#report").show();
	$("#admin").hide();
}
function Admin(){
	$("#operation").hide();
	$("#setting").hide();
	$("#report").hide();
	$("#admin").show();
}