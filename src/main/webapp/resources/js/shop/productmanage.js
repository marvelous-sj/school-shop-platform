/**
 * 
 */
$(function(){
	var listUrl='/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=100';
	//商品下架
	var changeStatusUrl='/o2o/shopadmin/modifyproduct';
	
	function getList() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';
				productList.map(function(item, index) {
					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					
					tempHtml += '<div class="row align-items-start table-bordered now"><div class="col-4">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-3">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-4">'
							+ '<a href="#" class="edit" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="delete" data-target="#myModal" data-toggle="modal" data-id="'
							+ item.productId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.product-wrap').html(tempHtml);
			}
		});
	}
	getList();

	function deleteItem(id, enableStatus) {
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$('#confirm').bind('click',function(){
			$.ajax({
				url : changeStatusUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						alert('操作成功！');
						getList();
					} else {
						alert('操作失败！');
					}
					
				}
			});
		});
	}

	$('.product-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							window.location.href = '/o2o/shopadmin/productoperation?productId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('delete')) {
							deleteItem(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							window.location.href = '/o2o/frontend/productdetail?productId='
									+ e.currentTarget.dataset.id;
						}
					});

	$('#return').click(function() {
		window.location.href = '/o2o/shopadmin/shopmanage';
	});
	$('#submit').click(function() {
		window.location.href = '/o2o/shopadmin/productoperation';
	});
});

	
