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
		addItems(2, pageNum);
	});
	
	function addItems(pageSize, pageIndex) {
		var listUrl = '/o2o/frontend/listawardspageinfo?pageIndex='+pageIndex+'&pageSize='+pageSize+'&shopId='+shopId+'&awardName='+awardName;
		pageNum = pageIndex;
		$.getJSON(listUrl,function(data) {
							if (data.success) {
								var point=data.point;
								if(point!=null){
								$('#point').html('当前积分'+point);
								}
								maxItems = data.count;
								totalPage = maxItems % 2== 0? maxItems / 2:maxItems / 2 + 1;
								var html = '';
								data.awardList
										.map(function(item, index) {
											html += ''
													+ '<li class="list-group-item"><div class="card" style="background-color: #e2e8f4;">'
													+ '<div class="card-body">'
													+ '<div class="row">'
													+ '<div class="col">'
													+ item.awardName
													+ '</div>'
													+ '<div class="col">'
													+ '<img class="card-img-top" src="'
													+ getContextPath()+item.awardImg
													+ '" '
													+ 'style="width: 4rem;" alt="Card image cap">'
													+ '</div>'
													+ '<div class="col">'
													+ '需要积分'+item.point+'<p><a href="#" class="exchange" data-target="#myModal" data-toggle="modal" data-id="'
													+ item.awardId
													+ '">点击兑换</a></p>'
													+ '</div>'
													+ '</div>'
													+ '<p style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">'
													+ item.awardDesc + '</p>'
													+ '</div>' + '</div></li>';
										});
								$('.list-group').html(html);

							}
							a();

						});
	}
	addItems(2, 1);
	function exchange(id) {
		var url='/o2o/frontend/adduserawardmap?awardId='+id;
		
		$('#confirm').unbind().bind('click',function(){
			$.ajax({
				url : url,
				type : 'GET',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						alert('兑换成功！');
						addItems(2, 1);
					} else {
						alert('兑换失败！'+data.errMsg);
					}
				}
			});
		});
		}
	
	$('.list-group').on('click','a',function(e) {
		var target = $(e.currentTarget);
		if (target.hasClass('exchange')) {
			exchange(e.currentTarget.dataset.id);
		}
	});
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