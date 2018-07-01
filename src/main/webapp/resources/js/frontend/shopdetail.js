/**
 * 
 */
$(function() {
	var shopId = getQueryString('shopId');
	var listUrl = '/o2o/frontend/listshopdetailpageinfo?shopId='+shopId;
	var maxItems = 6;
	var totalPage = 1;
	var searchUrl = '/o2o/frontend/listproductsbyshop';
	var productCategoryId = '';
	var productName = '';
	var pageNum = 1;
	function getSearchDivData() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				
				 var shop = data.shop;
				 $('#shopName').html(shop.shopName);
				 
				 var html = '';
				 html =' <img class="card-img-top border border-primary"  src="' + getContextPath()+shop.shopImg + '" width="200" height="200" alt="Card image cap">'
				    +'<div class="card-body">'
			   		 +'<small class="text-muted">'
								+ new Date(shop.lastEditTime).toLocaleDateString()
								+'</small>'	
			     +' <p class="card-text text-info">' + shop.shopDesc + '</p>'
			     +'<div class="row justify-content-between" ><div class="col"><small class="text-muted">'+ shop.shopAddr
					+ '</small></div><div class="col"><small class="text-muted">联系:'
					+ shop.phone 
					+'</small></div></div>'
			   +' </div>' ;
				 $('#shop-introduce').html(html);
				
				var selectOptions = '<option value="">全部商品</option>';
				var productCategoryList = data.productCategoryList;
				productCategoryList.map(function(item, index) {
					
					selectOptions += '<option value="' + item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';
				});
				$('#productCategory-search').html(selectOptions);
			}
		});
	}
	getSearchDivData();

	$('#productCategory-search').on('change', function() {
		productCategoryId= $('#productCategory-search').val();
		pageNum = 1;
		addItems(3, pageNum);

	});

	$('#search').on('click', function() {
		productName = $('#search-info').val();
		pageNum = 1;
		addItems(3, pageNum);
	});
	$('#exchange').attr('href','/o2o/frontend/awardlist?shopId='+shopId);
	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = searchUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId + '&productName=' + productName
				+ '&shopId=' +shopId;
		pageNum = pageIndex;

		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								maxItems = data.count;
								
								totalPage = maxItems % 3== 0? maxItems / 3:maxItems / 3 + 1;
								var html = '';
								data.productList
										.map(function(item, index) {
											html += ''
													+ '<li class="list-group-item"><div class="card" style="background-color: #e2e8f4;">'
													+ '<div class="card-body">'
													+ '<div class="row">'
													+ '<div class="col">'
													+ item.productName
													+ '</div>'
													+ '<div class="col">'
													+ '<img class="card-img-top" src="'
													+ getContextPath()+item.imgAddr
													+ '" '
													+ 'style="width: 4rem;" alt="Card image cap">'
													+ '</div>'
													+ '<div class="col">'
													+ new Date(
															item.lastEditTime)
															.toLocaleDateString()
													+ '最后更新<a href="/o2o/frontend/productdetail?productId='+item.productId+'">点击查看</a>'
													+ '</div>'
													+ '</div>'
													+ '<p style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">'
													+ item.productDesc + '</p>'
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