package cn.tom.web.controller;

import cn.tom.web.model.ScheduleJob;
import cn.tom.web.quartz.QuartzJobController;
import cn.tom.web.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lxl
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("ss mm HH dd * ? *");
    private final SimpleDateFormat dayFormat = new SimpleDateFormat("ss mm HH * * ? *");
    private final SimpleDateFormat hourFormat = new SimpleDateFormat("ss mm * * * ? *");
    private final SimpleDateFormat minuteFormat = new SimpleDateFormat("ss * * * * ? *");

    @Autowired
    private ITaskService taskService;

    @Autowired
    private QuartzJobController quartzJobController;

    @RequestMapping(value = "/update")
    public String updateTime(String jobTime, String jobName) throws ParseException {
//        String time = new BigDecimal(String.valueOf(format.parse(jobTime).getTime())).subtract(new BigDecimal(String.valueOf
//                (mformat.parse(jobTime).getTime()))).toString();
//        String cron = "ss * * * * ? *" .replaceAll("ss", "0/" + time);
        Date date = format.parse(jobTime);
        String cron = minuteFormat.format(date);
        ScheduleJob scheduleJob = taskService.selectJobByName(jobName);
        if (scheduleJob != null) {
            String oldCron = scheduleJob.getJobCron();
            scheduleJob.setJobCron(cron);
            Boolean b = taskService.updateJob(scheduleJob);
            quartzJobController.start(scheduleJob);
            LOGGER.info("修改任务:{}, 修改前时间:{}, 修改后时间:{}, 修改成功:{}", jobName, oldCron, cron, b);
        }
        return "success";
    }
}
