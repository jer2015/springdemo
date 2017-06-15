package cn.tom.web.quartz;

import cn.tom.web.dao.ScheduleJobMapper;
import cn.tom.web.model.ScheduleJob;
import cn.tom.web.model.User;
import cn.tom.web.service.IUserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lxl
 */
public class QuartzJobFactory implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobFactory.class);
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IUserService userService;

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //获取job信息
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        //根据job名称指定对应任务(service)
        QuartzJobEnum quartzJobEnum = QuartzJobEnum.valueOf(scheduleJob.getJobName());
        switch (quartzJobEnum) {
            case TEST_JOB:
                User user = userService.selectUser(1);
                user.setUpdateTime(new Date());
                Integer integer = userService.updateUser(user);
                User userNew = userService.selectUser(1);
                LOGGER.info("任务名称[{}], cron:{}, updateTime:{}, 运行成功:{}", scheduleJob.getJobName(), scheduleJob
                        .getJobCron(), format.format(userNew.getUpdateTime()), integer > 0);
                break;
            default:
                LOGGER.info("任务名称[{}],相关任务未定义", scheduleJob.getJobName());
                break;
        }


    }
}
