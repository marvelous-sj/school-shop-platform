$(function() {
	var bindUrl = '/o2o/local/bindlocalauth';
	var userType = getQueryString('usertype');
	$('#submit').click(function() {
		var userName = $('#username').val();
		var password = $('#password').val();
		var verifyCode = $('#verification-code').val();
		var needVerify = false;
		if (!verifyCode) {
			alert('请输入验证码！');
			return;
		}
		$.ajax({
			url : bindUrl,
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCode : verifyCode
			},
			success : function(data) {
				if (data.success) {
					alert('绑定成功！');
					if(userType==1){
						window.location.href = '/o2o/frontend/index';
					}else if(userType==2){
						window.location.href = '/o2o/shopadmin/shoplist';
					}
					
				} else {
					alert('绑定失败！'+data.errMsg+'');
					$('#verification-img').click();
				}
			}
		});
	});
});