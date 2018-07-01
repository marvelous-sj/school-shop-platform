package xyz.marsj.o2o.controller.wechat;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.marsj.o2o.dto.UserAccessToken;
import xyz.marsj.o2o.dto.WechatAuthExecution;
import xyz.marsj.o2o.dto.WechatUser;
import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.WechatAuth;
import xyz.marsj.o2o.enums.WechatAuthStateEnum;
import xyz.marsj.o2o.service.IPersonInfoService;
import xyz.marsj.o2o.service.IWechatAuthService;
import xyz.marsj.o2o.util.wechat.WechatUtil;

@Controller
@RequestMapping("wechatlogin")
/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * wx6d1798a60b4a990f
 * wx9eedbded219c38e6
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6d1798a60b4a990f&redirect_uri=http://120.79.194.248/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 */
public class WechatLoginController {
	@Autowired
	private IWechatAuthService wechatAuthService;
	@Autowired
	private IPersonInfoService personinfoService;
	
    private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);

    @RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");
        // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
        String code = request.getParameter("code");
        // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
		String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatUser user = null;
        String openId = null;
        WechatAuth auth =null;
        if (null != code) {
            UserAccessToken token;
            try {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
                auth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (IOException e) {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }
        	if(auth==null){
        		PersonInfo personInfo=WechatUtil.getPersonInfoFromRequest(user);
        		auth=new WechatAuth();
        		auth.setOpenId(openId);
        		auth.setPersonInfo(personInfo);
        		WechatAuthExecution wae = wechatAuthService.register(auth);
        		if(wae.getState()!=WechatAuthStateEnum.SUCCESS.getState()){
        			return null;
        		}else{
        			personInfo= personinfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
        			request.getSession().setAttribute("user", personInfo);
        		}
        		
        		
        	}
        	//TODO 判断是进入前端展示还是商铺列表
        	if(roleType.equals("2")) {
        		return "shopadmin/shoplist";
        	}else {
        		return "frontend/index";
        	}
    }
}