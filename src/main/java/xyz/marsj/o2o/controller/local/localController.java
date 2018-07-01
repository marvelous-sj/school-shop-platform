package xyz.marsj.o2o.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/local",method=RequestMethod.GET)
public class localController {
	@RequestMapping("/accountbind")
	public String index(){
		return "local/accountbind";
	}
	@RequestMapping("/login")
	public String login(){
		return "local/login";
	}
	@RequestMapping("/modifypwd")
	public String modifypwd(){
		return "local/modifypwd";
	}
	@RequestMapping("/shopdetail")
	public String shopDetail(){
		return "frontend/shopdetail";
	}
}
