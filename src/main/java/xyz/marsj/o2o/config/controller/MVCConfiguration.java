
package xyz.marsj.o2o.config.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.code.kaptcha.servlet.KaptchaServlet;

import xyz.marsj.o2o.interceptor.shop.ShopLoginInterceptor;

@Configuration
@EnableWebMvc
public class MVCConfiguration implements ApplicationContextAware,WebMvcConfigurer {
	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**").addResourceLocations("file:/home/marsj/image/upload/");
		//registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/fproject/image/upload/");
	}
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	@Bean(name="viewResolver")
	//重定向前后缀
	public InternalResourceViewResolver createInternalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setApplicationContext(this.applicationContext);
		viewResolver.setCache(false);
		viewResolver.setPrefix("/WEB-INF/html/");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}
	@Bean(name="multipartResolver")
	//图片处理
	public CommonsMultipartResolver createCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		//20M
		multipartResolver.setMaxUploadSize(20971520);
		multipartResolver.setMaxInMemorySize(20971520);
		return multipartResolver;
	}
	@Value("${kaptcha.border}")
	private String border;
	@Value("${kaptcha.textproducer.font.color}")
	private String fontColor;
	@Value("${kaptcha.image.width}")
	private String width;
	@Value("${kaptcha.textproducer.char.string}")
	private String charString;
	@Value("${kaptcha.image.height}")
	private String height;
	@Value("${kaptcha.textproducer.font.size}")
	private String fontSize;
	@Value("${kaptcha.noise.color}")
	private String noiseColor;
	@Value("${kaptcha.textproducer.char.length}")
	private String charLength;
	@Value("${kaptcha.textproducer.font.names}")
	private String fontNames;

	@Bean
	//验证码
	public ServletRegistrationBean<KaptchaServlet> servletRegistrationBean() {
		ServletRegistrationBean<KaptchaServlet> servletRegistrationBean = new ServletRegistrationBean<KaptchaServlet>(new KaptchaServlet(),"/Kaptcha");
		servletRegistrationBean.addInitParameter("kaptcha.border", border);
		servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.color", fontColor);
		servletRegistrationBean.addInitParameter("kaptcha.image.width", width);
		servletRegistrationBean.addInitParameter("kaptcha.textproducer.char.string", charString);
		servletRegistrationBean.addInitParameter("kaptcha.image.height", height);
		servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.size", fontSize);
		servletRegistrationBean.addInitParameter("kaptcha.noise.color", noiseColor);
		servletRegistrationBean.addInitParameter("kaptcha.textproducer.char.length", charLength);
		servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.names", fontNames);
		return servletRegistrationBean;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String interceptPath="/shopadmin/**";
		//登录拦截器
		InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
		loginIR.addPathPatterns(interceptPath);
		loginIR.excludePathPatterns("/shopadmin/addshopauthmap");
//		//权限拦截器
//		InterceptorRegistration permissionIR=registry.addInterceptor(new ShopPermissionInterceptor());
//		permissionIR.addPathPatterns(interceptPath);
//		//配置不拦截路径
//		permissionIR.excludePathPatterns("/shopadmin/shopmanage");
//		permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
//		permissionIR.excludePathPatterns("/shopadmin/shoplist");
//		permissionIR.excludePathPatterns("/shopadmin/getshoplist");
//		permissionIR.excludePathPatterns("/shopadmin/shopoperation");
//		permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
//		permissionIR.excludePathPatterns("/shopadmin/registershop");
	} 
}
