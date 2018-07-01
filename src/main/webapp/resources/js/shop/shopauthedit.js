$(function() {
	var shopAuthId = getQueryString('shopAuthId');
	var infoUrl = '/o2o/shopadmin/getshopauthmapbyid?shopAuthId=' + shopAuthId;

	var shopAuthPostUrl = '/o2o/shopadmin/modifyshopauthmap';

	if (shopAuthId) {
		getInfo(shopAuthId);
	} else {
		alert('用户不存在！');
		window.location.href = '/o2o/shopadmin/shopmanage';
	}

	function getInfo(id) {
		$.getJSON(infoUrl, function(data) {
			if (data.success) {
				var shopAuthMap = data.shopAuthMap;
				$('#shop-auth-name').val(shopAuthMap.employee.name);
				$('#shop-auth-title').val(shopAuthMap.title);
			}
		});
	}

	$('#submit').click(function() {
		var shopAuth = {};
		shopAuth.name = $('#shop-auth-name').val();
		shopAuth.title = $('#shop-auth-title').val();
		shopAuth.shopAuthId = shopAuthId;
		var verifyCode = $('#verification-code').val();
		if (!verifyCode) {
			alert('请输入验证码！');
			return;
		}
		$.ajax({
			url : shopAuthPostUrl,
			type : 'POST',
			data : {
				shopAuthMapStr : JSON.stringify(shopAuth),
				verifyCode : verifyCode
			},
			success : function(data) {
				if (data.success) {
					alert('提交成功！');
					window.location.href="/o2o/shopadmin/shopauthmanage";
				} else {
					alert('提交失败！'+data.errMsg);
				}
				$('#verification-img').click();
				
			}
		});
	});

});