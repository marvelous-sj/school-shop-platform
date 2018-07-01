/**
 * 
 */
$(function(){
	var addUrl='/o2o/shopadmin/addproductcategorys';
	var deleteUrl='/o2o/shopadmin/deleteproductcategory';
	getlist();
	function getlist(e){
		$.ajax({
		url:"/o2o/shopadmin/getproductcategorylist",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.success){
				handleList(data.data);
			}
		}
		
	});
	}
	
	function handleList(data){
		var html='';
		data.map(function(item,index){
			html+='<div class="row align-items-start table-bordered now"><div class="col">'
				+item.productCategoryName+'</div> <div class="col">'
				+item.priority+'</div><div class="col"><a class="delete" data-toggle="modal" data-target="#myModal" data-id="'
				+item.productCategoryId+'" href="#">删除</a></div></div>';
		});
		$('.category-wrap').html(html);
	}

	$('#add').click(function(){
		var tempHtml='<div class="row align-items-start table-bordered temp"><div class="col">'
			+'<input class="form-control category" type="text" placeholder="商品类别">'
			+'</div> <div class="col">'
			+'<input class="form-control priority" type="number" placeholder="优先级">'
			+'</div><div class="col"><a class="delete" href="#">删除</a></div></div>';
		$('.category-wrap').append(tempHtml);
	});

	$('#submit').click(function(){
		var tempArr=$('.temp');
		var productCategoryList=[];
		tempArr.map(function(index,item){
			var tempObj={};
			tempObj.productCategoryName=$(item).find('.category').val();
			tempObj.priority=$(item).find('.priority').val();
			if(tempObj.productCategoryName&&tempObj.priority){
				productCategoryList.push(tempObj);
			}
		});
		
		$.ajax({
			url:addUrl,
			type:'POST',
			data:JSON.stringify(productCategoryList),
			contentType: 'application/json',
			success:function(data){
				if(data.success){
					alert("提交成功!");
					getlist();
				}else{
					alert("提交失败!");
				}
			}
		});
		
	});
	
	$('.category-wrap').on('click','.align-items-start.table-bordered.temp .delete',function(e){
		$(this).parent().parent().remove();
	});
	
	$('.category-wrap').on('click','.align-items-start.table-bordered.now .delete',function(e){
		$('#confirm').unbind('click').bind('click',function(){
			var target=e.currentTarget;
			$.ajax({
				url:deleteUrl,
				type:'POST',
				data:{productCategoryId:target.dataset.id},
				dataType:'json',
				success:function(data){
					if(!data.success){
						alert("删除失败!");
					}else{
						alert("删除成功!");
						getlist();
					}
					return ;
				}
			});
			});
	});
	
	$('#return').click(function() {
		window.location.href = '/o2o/shopadmin/shopmanage';
	});
	
});