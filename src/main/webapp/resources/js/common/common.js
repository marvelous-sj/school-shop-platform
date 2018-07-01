/**
 * 
 */
html='<div class="pos-f-t"><div class="collapse" id="p2"><div class=" p-4" style="background-color: #fafcff;"><div class="row"><div class="col-4">'
	+'<a href="/o2o/local/accountbind?usertype=1" class="btn btn-info">绑定账户</a></div><div class="col-4"><a href="/o2o/local/modifypwd?usertype=1" class="btn btn-info">修改密码</a></div><div class="col-4"><a href="#" id="logout" usertype="1" class="btn btn-info">退出登录</a></div></div><hr><div class="row"><div class="col-4"><a href="/o2o/frontend/myrecord" class="btn btn-info">消费记录</a></div><div class="col-4"><a href="mypoint" class="btn btn-info">我的积分</a></div><div class="col-4"><a href="/o2o/frontend/pointrecord" class="btn btn-info">兑换记录</a></div></div></div></div></div>';
$('#myInfo').html(html);
function changeVerifyCode(img){
	img.src="../Kaptcha?"+Math.floor(Math.random()*100);
}
function getQueryString(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
	var r=window.location.search.substr(1).match(reg);
	if(r!=null){
		return decodeURIComponent(r[2]);
	}
	return '';
}
function getContextPath(){
	return 	"/o2o";
}
Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}        
