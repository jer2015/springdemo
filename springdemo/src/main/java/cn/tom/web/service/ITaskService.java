package cn.tom.web.service;

import cn.tom.web.model.ScheduleJob;

/**
 * @author lxl
 */
public interface ITaskService {

    ScheduleJob selectJobByName(String name);

    Boolean updateJob(ScheduleJob job);
}
