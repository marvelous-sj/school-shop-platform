$(function() {
	var userAwardId = getQueryString('userAwardId');
	var awardUrl = '/o2o/frontend/getawardbyuserawardid?userAwardId='+userAwardId;
	var detailHtml='';
	function getAwardDivData() {
		$.getJSON(awardUrl, function(data) {
			if (data.success) {
				
				 var award = data.award;
				 $('#awardName').html(award.awardName);
				 var html = '';
				 html =' <img class="card-img-top border border-primary"  src="' + getContextPath()+award.awardImg + '" width="200" height="200" alt="Card image cap">'
				    +'<div class="card-body">'
			   		 +'<small class="text-muted">'
								+ new Date(award.lastEditTime).toLocaleDateString()
								+'</small>'	
			     +' <p class="card-text text-info">' + award.awardDesc + '</p>'
			     +'消耗积分:'
					+ award.point 
					
			   +' </div>' ;
				 $('#award-introduce').html(html);
				 
		
				
				 if(data.usedStatus==0){
					 detailHtml += '<img alt="请刷新后获取二维码" src="/o2o/frontend/generateqrcode4saward?userAwardId='+userAwardId+'" style="width: 10rem;">';
				 }
				 $('#detail-img').html(detailHtml);
			}else{
				alert(data.errMsg);
			}
		});
	}
	getAwardDivData();

});