$(function() {
	
	var loginUrl = '/o2o/local/logincheck';
	var loginCount = 0;
	var userType = getQueryString('usertype');
	$('#login').click(function() {
		var userName = $('#username').val();
		var password = $('#password').val();
		var verifyCode = $('#verification-code').val();
		var needVerify = false;
		if (loginCount >= 1) {
			if (!verifyCode) {
				alert('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCode : verifyCode,
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					alert('登录成功！');
					if(userType==2){
					window.location.href = '/o2o/shopadmin/shoplist';
					}else{
						window.location.href = '/o2o/frontend/index';
					}
				} else {
					alert('登录失败！'+data.errMsg+'');
					loginCount++;
					if (loginCount >= 1) {
						$('#verifyPart').removeAttr("hidden");
					}
				}
			}
		});
	});

	$('#register').click(function() {
		window.location.href = '/o2o/shop/register';
	});
});