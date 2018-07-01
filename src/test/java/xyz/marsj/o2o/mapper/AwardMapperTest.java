package xyz.marsj.o2o.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.marsj.o2o.entity.Award;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardMapperTest  {
	@Autowired
	private AwardMapper awardMapper;
	@Test
	public void testInsertAward(){
		Award award=new Award();
		award.setAwardName("测试奖品");
		award.setShopId(20L);
		award.setCreateTime(new Date());
		award.setLastEditTime(new Date());
		award.setPoint(1);
		award.setPriority(10);
		award.setEnableStatus(1);
		int effectedNum = awardMapper.insertAward(award);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testUpdateAward(){
		Award award=new Award();
		award.setAwardId(1L);
		award.setAwardName("新测试奖品");
		award.setAwardImg("test");
		award.setExpireTime(new Date());
		int effectedNum = awardMapper.updateAward(award);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testDeleteAward(){
		int effectedNum = awardMapper.deleteAward(1,20);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testQueryAwardByAwardId(){
		Award award = awardMapper.queryAwardByAwardId(3);
		System.out.println(award.getAwardName());
	}
	@Test
	public void testQueryAwardList(){
		Award award=new Award();
		//award.setAwardName("2");
		award.setEnableStatus(1);
		List<Award> awardList = awardMapper.queryAwardList(award, 0, 3);
		assertEquals(2, awardList.size());
	}
	@Test
	public void testQueryAwardCount(){
		Award award=new Award();
		award.setAwardName("2");
		award.setEnableStatus(1);
		int count = awardMapper.queryAwardCount(award);
		System.out.println(count);
		
	}
}
