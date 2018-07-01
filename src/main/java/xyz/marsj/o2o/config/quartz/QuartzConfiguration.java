package xyz.marsj.o2o.config.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import xyz.marsj.o2o.service.IProductSellDailyService;

@Configuration
public class QuartzConfiguration {
	@Autowired
	private IProductSellDailyService productSellDailService;
	@Autowired
	private MethodInvokingJobDetailFactoryBean jobDetailFactory;
	@Autowired
	private CronTriggerFactoryBean productSellDailyTriggerFactory;
	//创建jobDetail
	@Bean(name="jobDetailFactory")
	public MethodInvokingJobDetailFactoryBean createJobDetail() {
		MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
		jobDetailFactoryBean.setName("product_sell_daily_job");
		jobDetailFactoryBean.setGroup("job_product_sell_daily_group");
		jobDetailFactoryBean.setConcurrent(false);
		//指定运行任务类
		jobDetailFactoryBean.setTargetObject(productSellDailService);
		//指定运行任务方法
		jobDetailFactoryBean.setTargetMethod("dailyCalculate");
		return jobDetailFactoryBean;
	}
	//创建cronTrigger并返回
	@Bean("productSellDailyTriggerFactory")
	public CronTriggerFactoryBean createProductSellDailyTrigger() {
		CronTriggerFactoryBean triggerFactory =new CronTriggerFactoryBean();
		triggerFactory.setName("product_sell_daily_trigger");
		triggerFactory.setGroup("job_product_sell_daily_group");
		//绑定jobDetail
		triggerFactory.setJobDetail(jobDetailFactory.getObject());
		//设定cron表达式
		triggerFactory.setCronExpression("0 0 0 * * ? *");
		return triggerFactory;
	}
	//创建调度工厂
	@Bean("schedulerFactory")
	public SchedulerFactoryBean createSechedulerFactory() {
		SchedulerFactoryBean schedulerFactory=new SchedulerFactoryBean();
		schedulerFactory.setTriggers(productSellDailyTriggerFactory.getObject());
		return schedulerFactory;
	}
}
