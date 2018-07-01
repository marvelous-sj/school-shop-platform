/**
 * 
 */
$(function(){
		var i=0;
		var productId=getQueryString('productId');
		var isEdit=productId ? true:false;
		var productInfoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
		var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
		var productPostUrl = '/o2o/shopadmin/modifyproduct';
		var statusChange=false;
		if(isEdit){
			getProductInfo(productId);
		}else{
			getCategory();
			productPostUrl = '/o2o/shopadmin/addproduct';
		}
		
		function getProductInfo(productId){
			
			$.getJSON(productInfoUrl,function(data){
				if(data.success){
					var product=data.product;
					$('#product-name').val(product.productName);
					$('#product-desc').val(product.productDesc);
					$('#priority').val(product.priority);
					$('#normalPrice').val(product.normalPrice);
					$('#promotionPrice').val(product.promotionPrice);
					
					var optionHtml = '';
					var optionArr = data.productCategoryList;
					var optionSelected = product.productCategory.productCategoryId;
					optionArr.map(function(item, index) {
								var isSelect = optionSelected === item.productCategoryId ? 'selected':'';
								optionHtml += '<option data-value="'
										+ item.productCategoryId
										+ '"'
										+ isSelect
										+ '>'
										+ item.productCategoryName
										+ '</option>';
							});
					$('#product-category').html(optionHtml);
				}
				});
			
		}
		
		
		
		function getCategory() {
			$.getJSON(categoryUrl, function(data) {
				if (data.success) {
					var productCategoryList = data.data;
					
					var optionHtml = '';
					productCategoryList.map(function(item, index) {
						optionHtml += '<option data-value="'
								+ item.productCategoryId + '">'
								+ item.productCategoryName + '</option>';
					});
					$('#product-category').html(optionHtml);
				}
			});
		}
		$('.detail-img-div').on('change', '.input-group:last-child', function() {
			if (i < 5) {
				i++;
				$('#detail-img').append('<div class="input-group mb-3">'
						+'<input type="file" class="custom-file-input" id="product-img-detail'+i+'">'
						+'<label class="custom-file-label" for="customFile">点击选择上传详情图片</label>'
					+'</div>');
			}
		});
		
		$('#submit').click(function(){
				var product = {};
				
				product.productName = $('#product-name').val();
				product.productDesc = $('#product-desc').val();
				product.priority = $('#priority').val();
				product.normalPrice = $('#normalPrice').val();
				product.promotionPrice = $('#promotionPrice').val();
				product.productCategory = {
					productCategoryId : $('#product-category').find('option').not(
							function() {
								return !this.selected;
							}).data('value')
				};
				product.productId = productId;
				var thumbnail = $('#product-img')[0].files[0];
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				for(var j=0;j<=i;j++){
							var tempImg=$('#product-img-detail'+j+'')[0].files[0];
							console.debug(tempImg);
							if (tempImg!=null) {
								formData.append('productImg' + j,tempImg);
							}
					}
				formData.append('productStr', JSON.stringify(product));
				var verifyCode=$('#verification-code').val();
				if(!verifyCode){
					alert('请输入验证码！');
					return;
				}
				formData.append('verifyCode',verifyCode);
				formData.append('statusChange',statusChange);
				$.ajax({
					url : productPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
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