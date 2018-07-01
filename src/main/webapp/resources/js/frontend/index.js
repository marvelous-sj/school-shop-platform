/**
 * 
 */
$(function() {
	var url = "/o2o/frontend/listmainpageinfo";
		
	$.getJSON(url,function(data) {
						if (data.success) {
							var headLineList = data.headLineList;
							var html = '';
							var active = '';
							headLineList.map(function(item, index) {
								if (index == 0) {
									active = 'active';
								} else {
									active = '';
								}
								html += '<div class="carousel-item ' + active
										+ '">' + '<a href="' + item.lineLink
										+ '"><img class="d-block w-100" src="'
										+getContextPath()+ item.lineImg + '" height="220" alt="'
										+ item.lineName + '"></a></div>';
							});
							$('.carousel-inner').html(html);

							var shopCategoryList = data.shopCategoryList;
							var categoryHtml = '';
							shopCategoryList
									.map(function(item, index) {
										if(index%2==0){
											categoryHtml += '<li class="list-group-item" style="background-color: #fafcff;">'
												+ '<div class="row justify-content-around">'
												+ '<div class="col-4">'
												+ '<a href="/o2o/frontend/shoplist?parentId='+item.shopCategoryId+'" class="btn btn-info"'
												+ 'style="width: 5.5rem; height: 2.5rem;">'+item.shopCategoryName+'</a> <img '
												+ 'src="'+getContextPath()+item.shopCategoryImg+'" style="width: 3rem;">'
												+ '</div>'
											
										}else{
											categoryHtml +=  '<div class="col-4">'
											+ '	<img src="'+getContextPath()+item.shopCategoryImg+'" style="width: 3rem;"> <a'
											+ ' href="/o2o/frontend/shoplist?parentId='+item.shopCategoryId+'" class="btn btn-info"'
											+ 'style="width: 5.5rem; height: 2.5rem;">'+item.shopCategoryName+'</a>'
											+ '</div>' + '</div>' + '</li>'
											
										}
									});
							
							$('#shopCategory').html(categoryHtml);

						}
					});

});