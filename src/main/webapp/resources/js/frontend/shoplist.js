/**
 * 
 */
$(function() {
	var listUrl = '/o2o/frontend/listshops';
	var maxItems = 6;
	var totalPage = 1;
	var searchDivUrl = '/o2o/frontend/listshopspageinfo';
	var parentId = getQueryString('parentId');
	var areaId = '';
	var shopName = '';
	var pageNum = 1;
	function getSearchDivData() {
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$.getJSON(url, function(data) {
			if (data.success) {
				// var shopCategoryList = data.shopCategoryList;
				// var html = '';
				// html += '<div class="col-4"><a href="#" class="btn
				// btn-info">全部类别</a></div>';
				// shopCategoryList.map(function(item, index) {
				// html +='<div class="col-4">'
				// +'<a href="#" class="btn btn-info">'+ item.shopCategoryName +
				// '</a>'
				// +'</div>';
				// });
				// $('#shoplist-search').html(html);
				var selectOptions = '<option value="">全部街道</option>';
				var areaList = data.areaList;
				areaList.map(function(item, index) {
					selectOptions += '<option value="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#area-search').html(selectOptions);
			}
		});
	}
	getSearchDivData();

	$('#area-search').on('change', function() {
		areaId = $('#area-search').val();
		pageNum = 1;
		addItems(3, pageNum);

	});

	$('#search').on('click', function() {
		shopName = $('#search-info').val();
		pageNum = 1;
		addItems(3, pageNum);
	});

	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&areaId=' + areaId + '&shopName=' + shopName
				+ '&parentId=' + parentId;
		pageNum = pageIndex;

		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								maxItems = data.count;
								totalPage = maxItems % 3== 0? maxItems / 3:maxItems / 3 + 1;
								var html = '';
								data.shopList
										.map(function(item, index) {
											html += ''
													+ '<li class="list-group-item"><div class="card" style="background-color: #e2e8f4;">'
													+ '<div class="card-body">'
													+ '<div class="row">'
													+ '<div class="col">'
													+ item.shopName
													+ '</div>'
													+ '<div class="col">'
													+ '<img class="card-img-top" src="'
													+ getContextPath()+item.shopImg
													+ '" '
													+ 'style="width: 4rem;" alt="Card image cap">'
													+ '</div>'
													+ '<div class="col">'
													+ new Date(
															item.lastEditTime)
															.toLocaleDateString()
													+ '最后更新<a href="/o2o/frontend/shopdetail?shopId='+item.shopId+'">点击查看</a>'
													+ '</div>'
													+ '</div>'
													+ '<p style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">'
													+ item.shopDesc + '</p>'
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
		})

	}
});