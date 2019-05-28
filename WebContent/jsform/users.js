$(document).ready(function(){
			genID();
			genRole();		
});

$( function() {
    $( "#dialogDelete" ).dialog({
      autoOpen: false,
      modal: true,
      buttons: {
        "Confirm": function() {
          $( this ).dialog(clickDelete());
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      }
      
    });
 
    $( "#btnDelete" ).on( "click", function() {
      $( "#dialogDelete" ).dialog( "open" );
    });
  } );


$( function() {
    $( "#dialogReset" ).dialog({
      autoOpen: false,
      modal: true,
      buttons: {
        "Confirm": function() {
          $( this ).dialog(clickReset());
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      }
      
    });
 
    $( "#btnReset" ).on( "click", function() {
      $( "#dialogReset" ).dialog( "open" );
    });
  } );

$( function() {
    $( "#dialogSave" ).dialog({
      autoOpen: false,
      modal: true,
      buttons: {
        "Confirm": function() {
          $( this ).dialog(clickSave());
          $( this ).dialog( "close" );
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      }
      
    });
 
    $( "#btnSave" ).on( "click", function() {
      $( "#dialogSave" ).dialog( "open" );
    });
  } );
			
function genAll(){
	
	var ID = $("#inputID").val();
	if(ID.length < 3){
		alert('Plese put number 3 diget.');
	}else{
		
		$.ajax({
			type: 'GET',
			url: './UserSrvl',
			data: 
			 {"method":"genAll",
				"id": ID } ,
			success: function(data){
				//alert(data.NAME);
				if(data.NAME == undefined){
					alert("New");
					$("#inputMode").val("New");
				}else{
					alert("Update");
					$("#inputMode").val("Update");
					$("#inputID").attr("disabled", "disabled");
					$("#btnDelete").removeAttr("disabled");
					$('#inputID').val(data.USER_ID);
					$('#inputName').val(data.NAME);
					$('#inputSurname').val(data.SURNAME);
					$('#inputLoginName').val(data.LOGIN_NAME);
					$('#inputPassword').val(data.PASSWORD);
					$('#inputConPassword').val(data.PASSWORD);
					$('#inputEmail').val(data.EMAIL_ADDRESS);
					$('#dropdownRole').val(data.USER_ROLE).attr("selected", "selected");
					
					if(data.ACTIVE == "Y"){
						$('#checkbox').attr('checked','checked');
					}
				}
			}
		})
	}
	
}
var idGen = {};
function genID(){	
	$.ajax({
		type: 'GET',
		url: './UserSrvl',
		data: 
		 {"method":"genID"} ,
		success: function(data){
			idGen = data.CUST_ID;
			$('#inputID').val(idGen);
			return idGen;
		}
	})
}
			
			
function genRole(){
	$.ajax({
		type: 'GET',
		url: './UserSrvl',
		data: 
		 {"method":"genRole"} ,
		success: function(data){
			//alert(data);
			$('#dropdownRole').append(data);
		}
	})
}
			
function clickSave(){
var n = "\n";
var id = $("#inputID").val();
var role = $("#dropdownRole").val();
var name = $("#inputName").val();
var surname = $("#inputSurname").val();
var loginName = $("#inputLoginName").val();
var password = $("#inputPassword").val();
var conPassword = $("#inputConPassword").val();
var email = $("#inputEmail").val();
var checkbox = $('#checkbox').is(':checked');

if(checkbox == false){
	var checkboxVal = "N";
	
}else{
	var checkboxVal = "Y";
	
}

var mode = $("#inputMode").val();


	
	
	if(mode == "New" && (name=="" || surname==""||loginName==""||password==""||
		conPassword==""|| email=="") ){
		alert('Plese fill all data!');
	}else{
		$.ajax({
			
			type: 'GET',
			url: './UserSrvl',
			data: 
			{"method":"clickSave",
			"id": id, "role": role,"name":name,
			"surname":surname,"loginName":loginName,
			"password":password,"email":email,
			"checkbox":checkboxVal,"mode":mode},
			success: function(data){
				alert(data);
				
				
			}
		})
		
	}
}
			function clickReset(){
				location.reload();
				
			}
			function clickDelete(){
				
				var id = $("#inputID").val();
				alert('ต้องการ DELETE '+id+" นี้ใช่ไหม?");
				
				$.ajax({
					
					type: 'GET',
					url: './UserSrvl',
					data: 
					{"method":"Delete",
					"id": id},
					success: function(data){
						alert(data);
						
					}
				})
				clickReset();
			}
	
			
			