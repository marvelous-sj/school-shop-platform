package xyz.marsj.o2o.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/frontend",method=RequestMethod.GET)
public class FrontendController {
	@RequestMapping("/index")
	public String index(){
		return "frontend/index";
	}
	@RequestMapping("/productdetail")
	public String productDetail(){
		return "frontend/productdetail";
	}
	@RequestMapping("/shoplist")
	public String shopList(){
		return "frontend/shoplist";
	}
	@RequestMapping("/shopdetail")
	public String shopDetail(){
		return "frontend/shopdetail";
	}
	@RequestMapping("/awardlist")
	public String awardList(){
		return "frontend/awardlist";
	}
	@RequestMapping("/pointrecord")
	public String pointRecord(){
		return "frontend/pointrecord";
	}
	@RequestMapping("/myawarddetail")
	public String myAwardDetail(){
		return "frontend/myawarddetail";
	}
	@RequestMapping("/myrecord")
	public String myRecord(){
		return "frontend/myrecord";
	}
	@RequestMapping("/mypoint")
	public String myPoint(){
		return "frontend/mypoint";
	}
}
