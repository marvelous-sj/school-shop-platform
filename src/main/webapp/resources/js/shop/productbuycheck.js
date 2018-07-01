$(function() {
    var productName = '';

    function getList() {
        var listUrl = '/o2o/shopadmin/listuserproductmapsbyshop?pageIndex=1&pageSize=9999&productName=' + productName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userProductMapList = data.userProductMapList;
                var tempHtml = '';
                userProductMapList.map(function (item, index) {
                	if(index%2==0){
                		tempHtml += '<div class="row align-items-start table-bordered" style="background-color: #e2e8f4;"><div class="col">'
        					+item.product.productName+'</div> <div class="col">'
        					+ new Date(item.createTime).format("yyyy-MM-dd hh:mm:ss")+'</div><div class="col">'
        					+item.user.name+'</div><div class="col">'
        					+item.point+'</div><div class="col">'
        					+item.operator.name+'</div></div>';
                	}else{
                		tempHtml += '<div class="row align-items-start table-bordered" "><div class="col">'
        					+item.product.productName+'</div> <div class="col">'
        					+ new Date(item.createTime).format("yyyy-MM-dd hh:mm:ss")+'</div><div class="col">'
        					+item.user.name+'</div><div class="col">'
        					+item.point+'</div><div class="col">'
        					+item.operator.name+'</div></div>';
                	}
                    
                });
                $('.user-product-wrap').html(tempHtml);
            }
        });
    }

    getList();

    getProductSellDailyList() ;
    
    function getProductSellDailyList() {
        var productSellDailyUrl = '/o2o/shopadmin/listproductselldailyinfobyshop';
        $.getJSON(productSellDailyUrl, function (data) {
            if (data.success) {
            	var myChart = echarts.init(document.getElementById('main'));
            	var option =generateStaticEchartPart();
            	option.legend.data=data.legendData;
            	option.xAxis=data.xAxis;
            	option.series=data.series;
            	
            	myChart.setOption(option);
            }
        });
    }
    function generateStaticEchartPart(){
    	var option = {
    			title: {
                    text: '七日销量'
                },
    	        tooltip : {
    	            trigger: 'axis',
    	            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    	                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    	            }
    	        },
    	        legend: {
    	            data:['a','b','c']
    	        },
    	        grid: {
    	            left: '3%',
    	            right: '4%',
    	            bottom: '3%',
    	            containLabel: true
    	        },
    	        xAxis : [
    	            {
    	                type : 'category',
    	                data : ['周一','周二','周三','周四','周五','周六','周日']
    	            }
    	        ],
    	        yAxis : [
    	            {
    	                type : 'value'
    	            }
    	        ],
    	        series : [
    	            {
    	                name:'a',
    	                type:'bar',
    	                data:[120, 132, 101, 134, 290, 230, 220]
    	            },
    	            {
    	                name:'b',
    	                type:'bar',
    	                data:[60, 72, 71, 74, 190, 130, 110]
    	            },
    	            {
    	                name:'c',
    	                type:'bar',
    	                data:[62, 82, 91, 84, 109, 110, 120]
    	            }
    	        ]
    	    };
    return option;
    }
    
});