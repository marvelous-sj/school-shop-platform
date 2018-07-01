/**
 * 
 */
$(function(){
	var listUrl='/o2o/shopadmin/listawardsbyshop?pageIndex=1&pageSize=100';
	//奖品下架
	var changeStatusUrl='/o2o/shopadmin/modifyaward';
	
	function getList() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var awardList = data.awardList;
				var tempHtml = '';
				awardList.map(function(item, index) {
					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					
					tempHtml += '<div class="row align-items-start table-bordered now"><div class="col-4">'
							+ item.awardName
							+ '</div>'
							+ '<div class="col-3">'
							+ item.point
							+ '</div>'
							+ '<div class="col-4">'
							+ '<a href="#" class="edit" data-id="'
							+ item.awardId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="delete" data-target="#myModal" data-toggle="modal" data-id="'
							+ item.awardId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.shopId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.award-wrap').html(tempHtml);
			}
		});
	}
	getList();

	function deleteItem(id,enableStatus) {
		var award = {};
		award.awardId = id;
		award.enableStatus = enableStatus;
		$('#confirm').bind('click',function(){
			$.ajax({
				url : changeStatusUrl,
				type : 'POST',
				data : {
					awardStr : JSON.stringify(award),
					statusChange : true},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						getList();
					} else {
						alert('操作失败！');
					}
					
				}
			});
		});
	}

	$('.award-wrap').on('click','a',function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							window.location.href = '/o2o/shopadmin/awardoperation?awardId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('delete')) {
							deleteItem(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							window.location.href = '/o2o/frontend/awardlist?shopId='
									+ e.currentTarget.dataset.id;
						}
					});

	$('#return').click(function() {
		window.location.href = '/o2o/shopadmin/shopmanage';
	});
	$('#submit').click(function() {
		window.location.href = '/o2o/shopadmin/awardoperation';
	});
});

	
