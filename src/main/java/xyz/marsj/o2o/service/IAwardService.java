package xyz.marsj.o2o.service;

import xyz.marsj.o2o.dto.AwardExecution;
import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.entity.Award;

public interface IAwardService {
	AwardExecution getAwardList(Award awardCondition ,int pageIndex,int pageSize);
	Award getAwardByAwardId(Long awardId);
	AwardExecution addAward(Award award,ImgHolder thumbnail);
	AwardExecution modifyAward(Award award,ImgHolder thumbnail);
}
