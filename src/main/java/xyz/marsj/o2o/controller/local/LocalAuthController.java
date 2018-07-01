package xyz.marsj.o2o.controller.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xyz.marsj.o2o.dto.LocalAuthExecution;
import xyz.marsj.o2o.entity.LocalAuth;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.enums.LocalAuthStateEnum;
import xyz.marsj.o2o.exceptions.LocalAuthOperationException;
import xyz.marsj.o2o.service.ILocalAuthService;
import xyz.marsj.o2o.util.CodeUtil;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
@RestController
@RequestMapping("/local")
public class LocalAuthController {
	@Autowired
	private ILocalAuthService localAuthService;

	//绑定本地账户
	@RequestMapping(value="/bindlocalauth",method=RequestMethod.POST)
	private Map<String,Object> bindLocalAuth(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password=HttpServletRequestUtil.getString(request, "password");
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		//判空
		if(userName!=null&&password!=null&&personInfo!=null&&personInfo.getUserId()!=null){
			LocalAuth localAuth=new LocalAuth();
			localAuth.setUserName(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(personInfo);
			LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
			if(lae.getState()==LocalAuthStateEnum.SUCCESS.getState()){
				modelMap.put("success",true);
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", lae.getStateInfo());
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码不能为空");
		}
		return modelMap;
		
	}
	//修改密码
	@RequestMapping(value="/updatelocalpwd",method=RequestMethod.POST)
	private Map<String,Object> updateLocalPWD(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		
		if(!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password=HttpServletRequestUtil.getString(request, "password");
		String newPassword=HttpServletRequestUtil.getString(request, "newPassword");
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		//判空，新旧密码需要不同
		if(userName!=null&&password!=null&&personInfo!=null&&personInfo.getUserId()!=null&&!password.equals(newPassword)){
			try {
				LocalAuth localAuth = localAuthService.getLocalByUserId(personInfo.getUserId());
			
				//输入的账号是否为本次登录的账号
				if(localAuth!=null&&!localAuth.getUserName().equals(userName)){
					modelMap.put("success", false);
					modelMap.put("errMsg", "请输入本次登录的账号");
					return modelMap;
				}
				LocalAuthExecution lae = localAuthService.modifyLocalAuth(personInfo.getUserId(), userName, password, newPassword);
				if(lae.getState()==LocalAuthStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", "修改密码失败");
				}
			} catch (LocalAuthOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入密码");
		}
		return modelMap;
	}
	//用户登录
	@RequestMapping(value="/logincheck",method=RequestMethod.POST)
	private Map<String,Object> loginCheck(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		//输错三次，需要输入验证码
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		
		if(needVerify&&!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误");
			return modelMap;
		}
		
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password=HttpServletRequestUtil.getString(request, "password");
		if(userName!=null&&password!=null){
			LocalAuth localAuth = localAuthService.getLocalByUserNameAndPSW(userName, password);
			if(localAuth!=null){
				modelMap.put("success", true);
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", "账号或密码输入错误");
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "账号或密码不能为空");
		}
		return modelMap;
	}
	
	//用户注销
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	private Map<String,Object> logOut(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
		
	}
}
