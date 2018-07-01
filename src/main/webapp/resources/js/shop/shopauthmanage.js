$(function() {
    var listUrl = '/o2o/shopadmin/listshopauthmapbyshopid';
    var modifyUrl = '/o2o/shopadmin/modifyshopauthmap';

    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var shopauthList = data.shopAuthMapList;
                var tempHtml = '';
                shopauthList.map(function (item, index) {
					var textOp = "删除";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						textOp = "恢复";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					
					if(item.titleFlag==0){
						tempHtml+='<div class="row align-items-start table-bordered" style="background-color: #e2e8f4;"><div class="col">'
        					+item.employee.name+'</div> <div class="col">'
        					+item.title+'</div><div class="col">'
        					+"不可操作"
        					+'</div></div>'
					}else{
        				tempHtml+='<div class="row align-items-start table-bordered" style="background-color: #e2e8f4;"><div class="col">'
        					+item.employee.name+'</div> <div class="col">'
        					+item.title+'</div><div class="col">'
        					+'<a href="/o2o/shopadmin/shopauthedit?shopAuthId='+item.shopAuthId+'">编辑</a>'
        					+ '<a href="#" class="delete" data-target="#myModal" data-toggle="modal" data-id="'
							+ item.shopAuthId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+'</div></div>';
        				}
        			
        		});
                $('.shopauth-wrap').html(tempHtml);
            }
        });
    }

    getList();
	function deleteItem(id, enableStatus) {
		var shopAuth = {};
		shopAuth.shopAuthId = id;
		shopAuth.enableStatus = enableStatus;
		$('#confirm').bind('click',function(){
			$.ajax({
				url : modifyUrl,
				type : 'POST',
				data : {
					shopAuthMapStr : JSON.stringify(shopAuth),
					statusChange : true},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
					    getList();
					} else {
						alert('操作失败！'+data.errMsg);
					}
					
				}
			});
		});
	}

	$('.shopauth-wrap').on('click','a',function(e) {
				var target = $(e.currentTarget);
				if (target.hasClass('delete')) {
					deleteItem(e.currentTarget.dataset.id,
							e.currentTarget.dataset.status);
				} 
			});

});