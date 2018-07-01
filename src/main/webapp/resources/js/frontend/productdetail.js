$(function() {
	var productId = getQueryString('productId');
	var productUrl = '/o2o/frontend/getproductinfo?productId='+productId;

	function getProductDivData() {
		$.getJSON(productUrl, function(data) {
			if (data.success) {
				
				 var product = data.product;
				 $('#productName').html(product.productName);
				 var html = '';
				 html =' <img class="card-img-top border border-primary"  src="' + getContextPath()+product.imgAddr + '" width="200" height="200" alt="Card image cap">'
				    +'<div class="card-body">'
			   		 +'<small class="text-muted">'
								+ new Date(product.lastEditTime).toLocaleDateString()
								+'</small>'	
			     +' <p class="card-text text-info">' + product.productDesc + '</p>'
			     +'<div class="row justify-content-between" ><div class="col-4">'
			     +' <p class="card-text text-warning"><del>原价:' + product.normalPrice +'</del></p><p class="card-text text-danger">现价'+ product.promotionPrice +'</p>'
			     +'</div><div class="col-4">积分:'
					+ product.point 
					+'</div></div>'
			   +' </div>' ;
				 $('#product-introduce').html(html);
				 
		
				 var detailHtml='';
				 product.productImgList.map(function(item, index) {
					 detailHtml += '<li class="list-group-item" style="background-color: #fafcff;">'
						+'<div class="card">'
						+'<img class="card-img-top" src="'+ getContextPath()+item.imgAddr+'" alt="Card image cap">'
						+'</div>'
						+'</li>'
					});
				 if(data.needQRCode){
					 detailHtml += '<img alt="请刷新后获取二维码" src="/o2o/frontend/generateqrcode4productmap?productId='+productId+'" style="width: 10rem;">';
				 }
				 $('#detail-img').html(detailHtml);
			}
		});
	}
	getProductDivData();

});