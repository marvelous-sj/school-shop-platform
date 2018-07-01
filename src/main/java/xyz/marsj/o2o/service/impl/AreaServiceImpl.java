package xyz.marsj.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.marsj.o2o.entity.Area;
import xyz.marsj.o2o.mapper.AreaMapper;
import xyz.marsj.o2o.service.IAreaService;
@Service
public class AreaServiceImpl implements IAreaService {
	@Autowired
	private AreaMapper areaMapper;

	@Override
	public List<Area> getAreaList() {
		List<Area> arealist =null;
		arealist = areaMapper.queryArea();
		return arealist;
	}

/*	
 * 	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Override
	@Transactional
	public List<Area> getAreaList() {
		String key=AREALISTKEY;
		List<Area> arealist =null;
		ObjectMapper mapper=new ObjectMapper();
		//redis不存在arealist信息
		if(!jedisKeys.exists(key)){
			arealist = areaMapper.queryArea();
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(arealist);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key,jsonString);
		}else{
			//redis存在arealistx信息
			//直接取
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				arealist = mapper.readValue(jsonString, javaType);
			} catch (Exception e) {
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			}
			
		}
		return arealist;
	}
*/
}
