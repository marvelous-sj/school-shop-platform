/**
 * 
 */
$(function() {
	var shopId = getQueryString('shopId');
	
	var maxItems = 6;
	var totalPage = 1;
	var productName = '';
	var pageNum = 1;

	$('#search').on('click', function() {
		productName = $('#search-info').val();
		pageNum = 1;
		addItems(2, pageNum);
	});
	
	function addItems(pageSize, pageIndex) {
		var listUrl = '/o2o/frontend/listusershopmapsbycustomer?pageIndex='+pageIndex+'&pageSize='+pageSize+'&shopId='+shopId;
		pageNum = pageIndex;
		$.getJSON(listUrl,function(data) {
							if (data.success) {
								maxItems = data.count;
								totalPage = maxItems % 2== 0? maxItems / 2:maxItems / 2 + 1;
								var html = '';
								data.userShopMapList
										.map(function(item, index) {
											html += ''
													+ '<li class="list-group-item"><div class="card" style="background-color: #e2e8f4;">'
													+ '<div class="card-body">'
													+ '<div class="row">'
													+ '<div class="col">'
													+ item.shop.shopName
													+ '</div>'
													+ '<div class="col">'
													+ '积分'+item.point
													+ '</div>'
													+ '<div class="col">'
													+ '<p style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">'
													+ new Date(item.createTime).format("yyyy-MM-dd hh:mm:ss")+ '</p>'
													+ '</div>' + '</div></li>';
										});
								$('.list-group').html(html);

							}
							a();

						});
	}
	addItems(2, 1);
	
	function a() {
		$('#box').paging({
			initPageNo : pageNum, // 初始页码
			totalPages : totalPage, // 总页数
			slideSpeed : 600, // 缓动速度。单位毫秒
			jump : true, // 是否支持跳转
			callback : function(page) { // 回调函数
				if (page != pageNum) {
					addItems(2, page);
				}
			}
		});

	}
});