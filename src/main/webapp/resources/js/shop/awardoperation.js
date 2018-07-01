/**
 * 
 */
$(function(){
		var i=0;
		var awardId=getQueryString('awardId');
		var isEdit=awardId ? true:false;
		var awardInfoUrl = '/o2o/shopadmin/getawardbyid?awardId=' + awardId;
		var awardPostUrl = '/o2o/shopadmin/modifyaward';
		var statusChange=false;
		if(isEdit){
			getawardInfo(awardId);
		}else{
			awardPostUrl = '/o2o/shopadmin/addaward';
		}
		
		function getawardInfo(awardId){
			
			$.getJSON(awardInfoUrl,function(data){
				if(data.success){
					var award=data.award;
					$('#award-name').val(award.awardName);
					$('#award-desc').val(award.awardDesc);
					$('#priority').val(award.priority);
					$('#point').val(award.point);
				}
				});
			
		}
		
		

		
		$('#submit').click(function(){
				var award = {};
				
				award.awardName = $('#award-name').val();
				award.awardDesc = $('#award-desc').val();
				award.priority = $('#priority').val();
				award.point = $('#point').val();
				award.awardId = awardId;
				var thumbnail = $('#award-img')[0].files[0];
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				formData.append('awardStr', JSON.stringify(award));
				var verifyCode=$('#verification-code').val();
				if(!verifyCode){
					alert('请输入验证码！');
					return;
				}
				formData.append('verifyCode',verifyCode);
				formData.append('statusChange',statusChange);
				$.ajax({
					url : awardPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if(data.success){
							alert('提交成功！');
							history.go(-1);
						}else{
							alert('提交失败！'+data.errMsg);
						}
						$('#verification-img').click();
					}
				});
			});
		
		
	
});