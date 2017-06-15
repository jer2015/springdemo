package cn.tom.web.service.impl;

import cn.tom.web.dao.ScheduleJobMapper;
import cn.tom.web.model.ScheduleJob;
import cn.tom.web.service.ITaskService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lxl
 */
@Service
@Transactional
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Override
    public ScheduleJob selectJobByName(String name) {
        return scheduleJobMapper.selectByJobName(name);
    }

    @Override
    public Boolean updateJob(ScheduleJob job) {
        return scheduleJobMapper.updateByPrimaryKey(job) > 0;
    }
}
