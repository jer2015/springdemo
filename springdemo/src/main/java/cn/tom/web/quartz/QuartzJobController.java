package cn.tom.web.quartz;

import cn.tom.web.dao.ScheduleJobMapper;
import cn.tom.web.model.ScheduleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author lxl
 */
@Component
public class QuartzJobController {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    //    @PostConstruct
    public void init() {
        //获取任务信息数据
        List<ScheduleJob> jobList = scheduleJobMapper.selectAllJob();
        start(jobList);
    }


    private void start(List<ScheduleJob> jobList) {
        for (ScheduleJob job : jobList) {
            start(job);
        }
    }

    public void start(ScheduleJob job) {
        try {
            //获取任务调度器
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //定义任务名称,所属组
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            //获取对应任务的trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //不存在，创建一个
            if (null == trigger) {
                //JobDetail:包含job实例的属性,有JobBuilder创建
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
                //JobDataMap:JobDetail的一部分,包含job的状态
                jobDetail.getJobDataMap().put("scheduleJob", job);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getJobCron()).withMisfireHandlingInstructionDoNothing();
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
                scheduler.deleteJob(jobKey);
                start(job);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
