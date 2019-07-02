$(function() {
	$("#passwordError").hide();
	$("#partyInformationError").hide();
});

function updatePwd() {
    var ok = $('#updatePwdForm').parsley().isValid({force: true});
	if(!ok){
		return;
	}
	var url = '/user/updatePassword';
	$.ajax({
		async: false,
		url : url,
		data : 'oldPassword='+$("#oldPassword").val()+'&newPassword='+$("#newPassword").val(),
		type : 'POST',
		dataType : "json",
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		},
		success : function(data, textStatus) {
			if(data.rspCode == '000000'){
				$("#passwordError").hide();
				$("#updatePwdBtn").attr("aria-hidden","true");
				$("#updatePwdBtn").attr("data-dismiss","modal");
				$("#updatePwdForm")[0].reset();
				toastr.success('密码修改成功！', '操作成功');
  	    	}else{
  	    		$("#passwordError").show();
  	    		$("#passwordError").html(data.rspMsg);
  	    		$("#updatePwdBtn").removeAttr("aria-hidden");
				$("#updatePwdBtn").removeAttr("data-dismiss");
  	    	}
		}
	});
}

function createParty() {
     var ok = $('#createPartyForm').parsley().isValid({force:true});
     if(!ok){
		return;
	}
	var url = '/user/createParty';
	$.ajax({
		async: false,
		url : url,
		data : 'partyStartTime='+$("#partyStartTime").val()
		      +'&partyEndTime='+$("#partyEndTime").val()
		      +'&partyAddress='+$("#partyAddress").val()
		      +'&headCount='+$("#headCount").val()
		      +'&partyType='+$("#partyType").val()
		      +'&estimateCost='+$("#estimateCost").val()
		      +'&partyDescription='+$("#partyDescription").val(),
		type : 'POST',
		dataType : "json",
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		},
		success : function(data, textStatus) {
			if(data.rspCode == '000000'){
				$("#partyInformationError").hide();
				$("#createPartyBtn").attr("aria-hidden","true");
				$("#createPartyBtn").attr("data-dismiss","modal");
				$("#createPartyForm")[0].reset();
				toastr.success('聚会创建成功！', '操作成功');
				window.location.reload()				
  	    	}else{
  	    		$("#partyInformationError").show();
  	    		$("#partyInformationError").html(data.rspMsg);
  	    		$("#createPartyBtn").removeAttr("aria-hidden");
				$("#createPartyBtn").removeAttr("data-dismiss");
  	    	}
		}
	});
}
