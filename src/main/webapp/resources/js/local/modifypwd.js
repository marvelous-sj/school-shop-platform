$(function() {
	var url = '/o2o/local/updatelocalpwd';
	var userType = getQueryString('usertype');
	$('#submit').click(function() {
		var userName = $('#username').val();
		var password = $('#password').val();
		var newPassword = $('#newPassword').val();
		var secondPassword = $('#secondPassword').val();
		if(newPassword!=secondPassword){
			alert('两次输入的密码不一致！');
			return;
		}
		
		var formData = new FormData();
		formData.append('userName', userName);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		var verifyCode = $('#verification-code').val();
		if (!verifyCode) {
			alert('请输入验证码！');
			return;
		}
		formData.append("verifyCode", verifyCode);
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					alert('提交成功！');
					if(userType==2){
						window.location.href = '/o2o/shopadmin/shoplist';
					}else{
						window.location.href = '/o2o/frontend/index';
					}
					
				} else {
					alert('提交失败！'+data.errMsg+'');
					$('#verification-img').click();
				}
			}
		});
	});


});
