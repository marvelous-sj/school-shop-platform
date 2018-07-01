package xyz.marsj.o2o.controller.superAdmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.marsj.o2o.entity.Area;
import xyz.marsj.o2o.service.IAreaService;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
	
	Logger logger=LoggerFactory.getLogger(AreaController.class);
	@Autowired 
	private IAreaService areaService;
	@RequestMapping(value="/listarea",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listArea(){
		logger.info("==start==");
		long startTime = System.currentTimeMillis();
		Map<String,Object> modalMap =new HashMap<String, Object>();
		List<Area> arealist=new ArrayList<Area>();
		
		try {
			arealist = areaService.getAreaList();
			modalMap.put("rows", arealist);
			modalMap.put("total", arealist.size());
		} catch (Exception e) {
			e.printStackTrace();
			modalMap.put("success", false);
			modalMap.put("errMsg", e.toString());
		}
		logger.error("test error");
		long endTime = System.currentTimeMillis();
		logger.info("==end==");
		logger.debug("costTime[{}ms]",endTime-startTime);
		return modalMap;
	}

}
