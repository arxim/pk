function showDate(d){
	if(d==""){
		return "";
	}else{
		var date = d.substr(6, 2);
		var month = d.substr(4, 2);
		var year = d.substr(0, 4);
		return date+"/"+month+"/"+year;
	}
}
function  numberFormat(n){
	return n.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function paymentDate(da, dy){
	var day = da.substring(0, 2); // 25 
	var month = da.substring(3, 5); // 10 
	var year = da.substring(6, 10); // 2016
	var dMonth = Number(month)+Number(dy);
	if(dMonth>12){
		dMonth = Number(dMonth) - 12;
		year = Number(year)+1;
	}
	var currDate = new Date(year,dMonth, 0);
	return currDate;
}