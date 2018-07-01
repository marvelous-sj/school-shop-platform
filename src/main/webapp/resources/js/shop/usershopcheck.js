$(function() {
    var userName = '';
	var maxItems = 6;
	var totalPage = 1;
	var pageNum = 1;
    function getList(pageSize, pageIndex) {
        var listUrl = '/o2o/shopadmin/getusershopmaplist?pageIndex='+pageIndex+'&pageSize='+pageSize+'&userName=' + userName;
        $.getJSON(listUrl, function (data) {
        	pageNum = pageIndex;
            if (data.success) {
				maxItems = data.count;
				totalPage = maxItems % 10== 0? maxItems / 10:maxItems / 10 + 1;
                var userShopMapList = data.userShopMapList;
                var tempHtml = '';
                userShopMapList.map(function (item, index) {
                	if(index%2==0){
                		tempHtml += '<div class="row align-items-start table-bordered" style="background-color: #e2e8f4;"><div class="col">'
        					+item.user.name+'</div> <div class="col">'
        					+ new Date(item.createTime).format("yyyy-MM-dd hh:mm:ss")
        					+'</div><div class="col">'
        					+item.point+'</div></div>';
                	}else{
                		tempHtml += '<div class="row align-items-start table-bordered" "><div class="col">'
        					+item.user.name+'</div> <div class="col">'
        					+ new Date(item.createTime).format("yyyy-MM-dd hh:mm:ss")
        					+'</div><div class="col">'
        					+item.point+'</div></div>';
                	}
                    
                });
                $('.user-shop-wrap').html(tempHtml);
            }
            a();
        });
    }

    getList(10,1);
	$('#search').on('click', function() {
		userName = $('#search-info').val();
		getList(10,1);
	});
	
	function a() {
		$('#box').paging({
			initPageNo : pageNum, // 初始页码
			totalPages : totalPage, // 总页数
			slideSpeed : 600, // 缓动速度。单位毫秒
			jump : true, // 是否支持跳转
			callback : function(page) { // 回调函数
				if (page != pageNum) {
					getList(10, page);
				}
			}
		});

	}

});