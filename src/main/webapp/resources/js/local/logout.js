	$('#logout').click(function () {
		$.ajax({
			url : "/o2o/local/logout",
			type : 'POST',
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					var userType=$('#logout').attr("usertype");
					window.location.href = '/o2o/local/login?usertype='+userType;
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});