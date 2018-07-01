/**
 * 
 */
$(function(){
	getlist();
	function getlist(e){
		$.ajax({
		url:"/o2o/shopadmin/getshoplist",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.success){
				handleList(data.shopList);
				handleUser(data.user);
			}
		}
		
	});
	}
	function handleUser(data){
		$('#user-name').text(data.name);
	}
	function handleList(data){
		var html='';
		data.map(function(item,index){
			if(index%2==0){
				html+='<div class="row align-items-start table-bordered" style="background-color: #e2e8f4;"><div class="col">'
					+item.shopName+'</div> <div class="col">'
					+shopStatus(item.enableStatus)+'</div><div class="col">'
					+goShop(item.enableStatus,item.shopId)+'</div></div>';
			}else{
			html+='<div class="row align-items-start table-bordered" ><div class="col">'
				+item.shopName+'</div> <div class="col">'
				+shopStatus(item.enableStatus)+'</div><div class="col">'
				+goShop(item.enableStatus,item.shopId)+'</div></div>';
			}
		});
		$('.shop-wrap').html(html);
	}
	function shopStatus(status){
		if(status==0){
			return '审核中';
		}else if(status==-1){
			return '店铺非法';			
		}else if(status==1){
			return '审核通过';
		}
		return '未知';
			
	}
	
	function goShop(status,shopId){
		if(status==1){
			return '<a href="/o2o/shopadmin/shopmanage?shopId='+shopId+'">进入</a>';
		}else{
			return '';
		}
	}
	$('#bindauth').click(function(){
		window.location.href = '/o2o/local/accountbind?usertype=2';
	});
	
	
});