package xyz.marsj.o2o.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
/**
 * 验证码校验
 * @author sj
 *
 */
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request){
		String verifyCodeReal = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeWrite = HttpServletRequestUtil.getString(request,"verifyCode");
		 
		if(verifyCodeWrite==null || !verifyCodeWrite.equals(verifyCodeReal) ){
			if(verifyCodeWrite.toUpperCase().equals(verifyCodeReal.toUpperCase())){
				return true;
			}
			return false;
		}
		return true;
	}
	//二维码
	public static BitMatrix generateQRCodeStream(String url,
			HttpServletResponse resp) {
		resp.setHeader("Cache-Control", "no-store");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/png");
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 0);
		BitMatrix bitMatrix;
		try {
			bitMatrix = new MultiFormatWriter().encode(url,
					BarcodeFormat.QR_CODE, 300, 300, hints);
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
		return bitMatrix;
	}
}
