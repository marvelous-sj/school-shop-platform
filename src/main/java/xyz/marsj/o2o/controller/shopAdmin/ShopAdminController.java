package xyz.marsj.o2o.controller.shopAdmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/shopadmin",method=RequestMethod.GET)
public class ShopAdminController {
	
	@RequestMapping("/shopoperation")
	public String shopOperation(){
		return "shop/shopoperation";
	}
	@RequestMapping("/shoplist")
	public String shopList(){
		return "shop/shoplist";
	}
	@RequestMapping("/shopmanage")
	public String shopManage(){
		return "shop/shopmanage";
	}
	@RequestMapping("/shopauthmanage")
	public String shopAuthManage(){
		return "shop/shopauthmanage";
	}
	@RequestMapping("/shopauthedit")
	public String shopAuthEdit(){
		return "shop/shopauthedit";
	}
	@RequestMapping("/productbuycheck")
	public String productBuyCheck(){
		return "shop/productbuycheck";
	}
	@RequestMapping("/usershopcheck")
	public String userShopCheck(){
		return "shop/usershopcheck";
	}
	@RequestMapping("/awardreceivecheck")
	public String awardReceivecheck(){
		return "shop/awardreceivecheck";
	}
	@RequestMapping("/awardmanage")
	public String awardManage(){
		return "shop/awardmanage";
	}
	@RequestMapping("/awardoperation")
	public String awardOperation(){
		return "shop/awardoperation";
	}
	@RequestMapping("/productcategorymanage")
	public String productCategoryManage(){
		return "shop/productcategorymanage";
	}
	@RequestMapping("/productoperation")
	public String productOperation(){
		return "shop/productoperation";
	}
	@RequestMapping("/productmanage")
	public String productManage(){
		return "shop/productmanage";
	}
	@RequestMapping("/operationsuccess")
	public String operationSuccess(){
		return "shop/operationsuccess";
	}
	@RequestMapping("/operationfail")
	public String operationFail(){
		return "shop/operationfail";
	}
}
