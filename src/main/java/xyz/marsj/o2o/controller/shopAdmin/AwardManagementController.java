package xyz.marsj.o2o.controller.shopAdmin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.marsj.o2o.dto.AwardExecution;
import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.entity.Award;
import xyz.marsj.o2o.entity.Shop;
import xyz.marsj.o2o.enums.AwardStateEnum;
import xyz.marsj.o2o.exceptions.AwardOperationException;
import xyz.marsj.o2o.service.IAwardService;
import xyz.marsj.o2o.util.CodeUtil;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
@RestController
@RequestMapping("/shopadmin")
public class AwardManagementController {
	@Autowired 
	private IAwardService awardService;
	
	@RequestMapping(value="/listawardsbyshop",method=RequestMethod.GET)
	private Map<String, Object> listAwardsByShop(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		if(pageIndex>-1&&pageSize>-1&&currentShop!=null&&currentShop.getShopId()!=null){
			Award award=new Award();
			award.setShopId(currentShop.getShopId());
			String awardName=HttpServletRequestUtil.getString(request, "awardName");
			if(awardName!=null) {
				award.setAwardName(awardName);
			}
			AwardExecution ae = awardService.getAwardList(award, pageIndex, pageSize);
			modelMap.put("awardList", ae.getAwardList());
			modelMap.put("count", ae.getCount());
			modelMap.put("success", true);
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "传入参数有误");
		}
		return modelMap;
		
	}
	
	@RequestMapping(value="/addaward",method=RequestMethod.POST)
	private Map<String,Object> addAward(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		ObjectMapper mapper=new ObjectMapper();
		String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		Award award=null;
		ImgHolder thumbnail=null;
		try {
			if(multipartResolver.isMultipart(request)) {
				thumbnail = handleImg(request, thumbnail);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
			award = mapper.readValue(awardStr, Award.class);
		} catch (IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		if(award!=null&&thumbnail!=null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				award.setShopId(currentShop.getShopId());
				AwardExecution ae = awardService.addAward(award, thumbnail);
				if(ae.getState()==AwardStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);				
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入奖品信息");
		}	
		return modelMap;
		
	}
	
	
	
	@RequestMapping(value="/getawardbyid",method=RequestMethod.GET)
	private Map<String, Object> getAwardById(@RequestParam Long awardId){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		if(awardId>=1){
			Award award = awardService.getAwardByAwardId(awardId);
			modelMap.put("award", award);
			modelMap.put("success", true);
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty awardId");
		}
		return modelMap;
	}
	
	
	
	@RequestMapping(value="/modifyaward",method=RequestMethod.POST)
	private Map<String,Object> modifyAward(HttpServletRequest request) throws IOException{
		Map<String,Object> modelMap=new HashMap<String,Object>();
		//如果是更改上下架情况则不用核对验证码
		boolean statusChange=HttpServletRequestUtil.getBoolean(request, "statusChange");
		if(!statusChange&&!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		ObjectMapper mapper=new ObjectMapper();
		Award award=null;
		String awardStr = HttpServletRequestUtil.getString(request,"awardStr");
		ImgHolder thumbnail=null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//若存在文件流
		try {
			if(multipartResolver.isMultipart(request)){
			thumbnail = handleImg(request, thumbnail);
			}
			//把前端传来的awardStr转换为Award实体类
			award = mapper.readValue(awardStr, Award.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
			}
		if(award!=null){
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				award.setShopId(currentShop.getShopId());
				AwardExecution pe = awardService.modifyAward(award, thumbnail);
				if(pe.getState()==AwardStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (AwardOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}			
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入奖品信息");
		}
		return modelMap;
}
	
	private ImgHolder handleImg(HttpServletRequest request, ImgHolder thumbnail)
			throws IOException {
		MultipartHttpServletRequest	multipartRequest=(MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
		if(file!=null){
			thumbnail=new ImgHolder(file.getInputStream(),file.getOriginalFilename());			
		}
		return thumbnail;
	}
	
	
	
}