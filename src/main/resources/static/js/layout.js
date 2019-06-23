$(function() {
	$("#passwordError").hide();
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