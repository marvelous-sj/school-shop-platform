/**
 * 
 */
$(function() {
	var shopId = getQueryString('shopId');
	
	var maxItems = 6;
	var totalPage = 1;
	var awardName = '';
	var pageNum = 1;

	$('#search').on('click', function() {
		awardName = $('#search-info').val();
		pageNum = 1;
		addItems(3, pageNum);
	});
	
	function addItems(pageSize, pageIndex) {
		var listUrl = '/o2o/frontend/listuserawardmapsbycustomer?pageIndex='+pageIndex+'&pageSize='+pageSize+'&shopId='+shopId+'&awardName='+awardName;
		pageNum = pageIndex;
		$.getJSON(listUrl,function(data) {
							if (data.success) {
								maxItems = data.count;
								totalPage = maxItems % 3== 0? maxItems / 3:maxItems / 3 + 1;
								var html = '';
								var temp='';
								var msg='';
								data.userAwardMapList
										.map(function(item, index) {
											if(item.usedStatus==0){
												temp='未领取';
												msg="<p><a href='/o2o/frontend/myawarddetail?userAwardId="+item.userAwardId+"'>领取</a></p>";
											}else{
												temp='已领取';
											}
											html += ''
													+ '<li class="list-group-item"><div class="card" style="background-color: #e2e8f4;">'
													+ '<div class="card-body">'
													+ '<div class="row">'
													+ '<div class="col">'
													+ item.award.awardName
													+ '</div>'
													+ '<div class="col">'
													+ temp
													+ '</div>'
													+ '<div class="col">'
													+ '消耗积分'+item.point
													+msg
													+ '</div>'
													+ '</div>'
													+ '<p style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">'
													+ item.award.awardDesc + '</p>'
													+ '</div>' + '</div></li>';
										});
								$('.list-group').html(html);

							}
							a();

						});
	}
	addItems(3, 1);

	function a() {
		$('#box').paging({
			initPageNo : pageNum, // 初始页码
			totalPages : totalPage, // 总页数
			slideSpeed : 600, // 缓动速度。单位毫秒
			jump : true, // 是否支持跳转
			callback : function(page) { // 回调函数
				if (page != pageNum) {
					addItems(3, page);
				}
			}
		});

	}
});