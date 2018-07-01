/**
 * 
 */
$(function(){
		var shopId=getQueryString('shopId');
		var isEdit=shopId ? true:false;
		var initUrl='/o2o/shopadmin/getshopinitinfo';
		var registerShopUrl='/o2o/shopadmin/registershop';
		var shopInfoUrl='/o2o/shopadmin/getshopbyid?shopId='+shopId;
		var editShopUrl='/o2o/shopadmin/modifyshop';
		
		if(isEdit){
			getShopInfo(shopId);
			isEdit=true;
		}else{
			getShopInitInfo();
		}
		

		function getShopInfo(shopId){
			
			$.getJSON(shopInfoUrl,function(data){
				if(data.success){
					var shop=data.shop;
					$('#shop-name').val(shop.shopName);
					$('#shop-addr').val(shop.shopAddr);
					$('#phone').val(shop.phone);
					$('#shop-desc').val(shop.shopDesc);
					
					var shopCategory='<option id="'+shop.shopCategory.shopCategoryId+'" selected>'+
					shop.shopCategory.shopCategoryName+'</option>';
					var tempAreaHtml='';
					data.areaList.map(function(item,index){
						tempAreaHtml+='<option id="'+item.areaId+'">'+item.areaName+'</option>';
					});
					
					$('#shop-category').html(shopCategory);
					$('#shop-category').attr('disabled','disabled');
					$('#area').html(tempAreaHtml);
					$("#area option[id='"+shop.area.areaId+"']").attr('selected','selected');
				}
				});
			
		}
		function getShopInitInfo(){
			
			$.getJSON(initUrl,function(data){
				if(data.success){
					var tempHtml='';
					var tempAreaHtml='';
					data.shopCategoryList.map(function(item,index){
						tempHtml+='<option id="'+item.shopCategoryId+'">'+item.shopCategoryName+'</option>';
						});
					data.areaList.map(function(item,index){
						tempAreaHtml+='<option id="'+item.areaId+'">'+item.areaName+'</option>';
					});
					$('#shop-category').html(tempHtml);
					$('#area').html(tempAreaHtml);
				}
				});
			
		}
		
		$('#submit').click(function(){
			var shop={}; 
			if(isEdit){
				shop.shopId=shopId;
			}
			shop.shopName=$('#shop-name').val();
			shop.shopAddr=$('#shop-addr').val();
			shop.phone=$('#phone').val();
			shop.shopDesc=$('#shop-desc').val();
			shop.shopCategory={
				shopCategoryId : $('#shop-category').find('option:selected').attr('id')
			};
					
			shop.area={
					areaId : $('#area').find('option:selected').attr('id')
			};
			var shopImg=$('#shop-img')[0].files[0];
			var formData=new FormData();
			formData.append('shopImg',shopImg);
			formData.append('shopStr',JSON.stringify(shop));
			var verifyCode=$('#verification-code').val();
			if(!verifyCode){
				alert('请输入验证码！');
				return;
			}
			formData.append('verifyCode',verifyCode);
			$.ajax({
				url:isEdit?editShopUrl:registerShopUrl,
				type:'POST',
				data:formData,
				contentType:false,
				processData:false,
				cache:false,
				success:function(data){
					if(data.success){
						alert('提交成功！');
					}else{
						alert('提交失败！'+data.errMsg);
					}
					
					
					$('#verification-img').click();
					history.go(-1);
				}
				
			});
		});
		
		
	
});