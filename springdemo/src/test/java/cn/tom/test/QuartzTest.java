package cn.tom.test;

import cn.tom.web.quartz.QuartzManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lxl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:applicationContext.xml")
public class QuartzTest {

//    private static Map<String, ScheduleJob> jobMap = new HashMap<>();
//
//    static {
//        for (int i = 0; i < 5; i++) {
//            ScheduleJob job = new ScheduleJob();
//            job.setId("10001" + i);
//            job.setJobName("data_import" + i);
//            job.setJobGroup("dataWork");
//            job.setJobStatus("1");
//            job.setCronExpression("0/5 * * * * ?");
//            job.setDesc("数据导入任务");
//            addJob(job);
//        }
//    }
//
//    @Autowired
//    private SchedulerFactoryBean schedulerFactoryBean;
//
//    @Test
//    public void springTest() throws SchedulerException {
//        //schedulerFactoryBean 由spring创建注入
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        //这里获取任务信息数据
//        List<ScheduleJob> jobList = getAllJob();
//        for (ScheduleJob job : jobList) {
//            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
//            //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
//            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//            //不存在，创建一个
//            if (null == trigger) {
//                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
//                        .withIdentity(job.getJobName(), job.getJobGroup()).build();
//                jobDetail.getJobDataMap().put("scheduleJob", job);
//                //表达式调度构建器
//                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
//                        .getCronExpression());
//                //按新的cronExpression表达式构建一个新的trigger
//                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
//                scheduler.scheduleJob(jobDetail, trigger);
//            } else {
//                // Trigger已存在，那么更新相应的定时设置
//                //表达式调度构建器
//                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
//                        .getCronExpression());
//                //按新的cronExpression表达式重新构建trigger
//                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
//                        .withSchedule(scheduleBuilder).build();
//                //按新的trigger重新设置job执行
//                scheduler.rescheduleJob(triggerKey, trigger);
//            }
//        }
//    }
//
//    public static void addJob(ScheduleJob scheduleJob) {
//        jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(), scheduleJob);
//    }
//
//    List<ScheduleJob> getAllJob() {
//        ArrayList<ScheduleJob> scheduleJobs = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            ScheduleJob job = new ScheduleJob();
//            job.setId("10001" + i);
//            job.setJobName("data_import" + i);
//            job.setJobGroup("dataWork");
//            job.setJobStatus("1");
//            job.setCronExpression("0/5 * * * * ?");
//            job.setDesc("数据导入任务");
//            scheduleJobs.add(job);
//        }
//        return scheduleJobs;
//    }






    @Test
    public void quartz() throws ClassNotFoundException {
        try {
            SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
            Scheduler sche = gSchedulerFactory.getScheduler();
            String job_name = "动态任务调度";
            System.out.println("【系统启动】开始(每1秒输出一次)...");
            String clazz = "cn.tom.web.quartz1.HelloWorld";
            String cron = "*/10 * * * * ?";  //使用Class.forName动态的创建
            QuartzManager.addJob(sche, job_name, Class.forName(clazz), cron);

            Thread.sleep(3000);
            System.out.println("【修改时间】开始(每2秒输出一次)...");
            QuartzManager.modifyJobTime(sche, job_name, "10/2 * * * * ?");
            Thread.sleep(4000);
            System.out.println("【移除定时】开始...");
            QuartzManager.removeJob(sche, job_name);
            System.out.println("【移除定时】成功");

            System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");
            QuartzManager.addJob(sche, job_name, Class.forName(clazz), "*/10 * * * * ?");
            Thread.sleep(30000);
            System.out.println("【移除定时】开始...");
            QuartzManager.removeJob(sche, job_name);
            System.out.println("【移除定时】成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
