package xyz.marsj.o2o.controller.frontend;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.entity.Product;
import xyz.marsj.o2o.service.IProductService;
import xyz.marsj.o2o.util.CodeUtil;
import xyz.marsj.o2o.util.HttpServletRequestUtil;
import xyz.marsj.o2o.util.ShortNetAddress;
@RestController
@RequestMapping("/frontend")
public class ProductDetailController {
	@Autowired
	private IProductService productService;
	
	@RequestMapping(value="/getproductinfo",method=RequestMethod.GET)
	private Map<String,Object> getProductInfo(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		long productId=HttpServletRequestUtil.getLong(request,"productId");
		if(productId!=-1L){
			try {
				Product product=productService.queryProductById(productId);
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				if(user==null) {
					modelMap.put("needQRCode", false);
				}else {
					modelMap.put("needQRCode", true);
				}
				modelMap.put("success", true);
				modelMap.put("product", product);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		
		return modelMap;
	}
	
	private static String urlPrefix;
	private static String urlMiddle;
	private static String urlSuffix;
	private static String productmapUrl;
	@Value("${wechat.prefix}")
	public  void setUrlPrefix(String urlPrefix) {
		ProductDetailController.urlPrefix = urlPrefix;
	}
	@Value("${wechat.middle}")
	public  void setUrlMiddle(String urlMiddle) {
		ProductDetailController.urlMiddle = urlMiddle;
	}
	@Value("${wechat.suffix}")
	public  void setUrlSuffix(String urlSuffix) {
		ProductDetailController.urlSuffix = urlSuffix;
	}
	@Value("${wechat.productmap.url}")
	public  void setAuthUrl(String productmapUrl) {
		ProductDetailController.productmapUrl = productmapUrl;
	}
	
	//生成购买记录二维码
	@RequestMapping(value="/generateqrcode4productmap",method=RequestMethod.GET)
	public void generateQRCode4ShopAuth(HttpServletRequest request,HttpServletResponse response){
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		if(user!=null&&user.getUserId()!=null&&productId!=-1) {
			long timeStamp=System.currentTimeMillis();
			String content="{aaaproductIdaaa:"+productId+",aaacustomerIdaaa:"+user.getUserId()+",aaacreateTimeaaa:"+timeStamp+"}";
			String longUrl;
			try {
				longUrl = urlPrefix+productmapUrl+urlMiddle+URLEncoder.encode(content, "UTF-8")+urlSuffix;
				String shortURL = ShortNetAddress.getShortURL(longUrl);
				BitMatrix bitMatrix = CodeUtil.generateQRCodeStream(shortURL, response);
				MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
}
