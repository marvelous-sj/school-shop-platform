package xyz.marsj.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.AwardExecution;
import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.entity.Award;
import xyz.marsj.o2o.enums.AwardStateEnum;
import xyz.marsj.o2o.exceptions.AwardOperationException;
import xyz.marsj.o2o.mapper.AwardMapper;
import xyz.marsj.o2o.service.IAwardService;
import xyz.marsj.o2o.util.ImgUtil;
import xyz.marsj.o2o.util.PageUtil;
import xyz.marsj.o2o.util.PathUtil;
@Service
public class AwardServiceImpl implements IAwardService {

	@Autowired
	private AwardMapper awardMapper;
	@Override
	public AwardExecution getAwardList(Award AwardCondition, int pageIndex, int pageSize) {
		if(pageIndex!=-1&&pageSize!=-1&&AwardCondition!=null) {
			int rowIndex = PageUtil.calculateRowIndex(pageIndex, pageSize);
			//集合
			List<Award> AwardList = awardMapper.queryAwardList(AwardCondition, rowIndex, pageSize);
			//总数
			int count = awardMapper.queryAwardCount(AwardCondition);
			AwardExecution ae=new AwardExecution();
			ae.setCount(count);
			ae.setAwardList(AwardList);
			return ae;
		}else {
			return null;
		}
	}
	@Override
	public Award getAwardByAwardId(Long awardId) {
		return awardMapper.queryAwardByAwardId(awardId);
	}
	@Override
	@Transactional
	public AwardExecution addAward(Award award,ImgHolder thumbnail) {
		if(award!=null&&award.getShopId()!=null) {
			//设定初始值
			award.setCreateTime(new Date());
			award.setLastEditTime(new Date());
			award.setEnableStatus(1);
			if(thumbnail!=null) {
				addThumbnail(award, thumbnail);
			}
			try {
				int effected = awardMapper.insertAward(award);
				if(effected<=0){
					throw new AwardOperationException("新增奖品信息失败");
				}
				return new AwardExecution(AwardStateEnum.SUCCESS, award);
			} catch (Exception e) {
				throw new AwardOperationException("新增奖品信息失败"+e.getMessage());
			}
		}else {
			return new AwardExecution(AwardStateEnum.FAIL);
			}
	}
	
	@Override
	@Transactional
	public AwardExecution modifyAward(Award award,ImgHolder thumbnail) {
		//判空
		if(award!=null&&award.getAwardId()!=null){
			award.setLastEditTime(new Date());
			//操作缩略图
			if(thumbnail!=null){
				Award tempAward = awardMapper.queryAwardByAwardId(award.getAwardId());
				if(tempAward.getAwardImg()!=null){
					ImgUtil.deleteFileOrPath(tempAward.getAwardImg());
				}
				addThumbnail(award,thumbnail);
			}

			try {
				int effected = awardMapper.updateAward(award);
				if(effected<=0){
					throw new AwardOperationException("更新奖品信息失败");
				}
				return new AwardExecution(AwardStateEnum.SUCCESS, award);
			} catch (Exception e) {
				throw new AwardOperationException("更新奖品信息失败"+e.getMessage());
			}
		}else{
			return new AwardExecution(AwardStateEnum.FAIL);
		}
	}
	private void addThumbnail(Award award, ImgHolder thumbnail) {
		String path=PathUtil.getShopImgPath(award.getShopId());
		String imgAddr = ImgUtil.generateThumbnail(thumbnail, path);
		award.setAwardImg(imgAddr);
	}


}
