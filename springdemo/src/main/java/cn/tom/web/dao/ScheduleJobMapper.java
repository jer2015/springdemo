package cn.tom.web.dao;

import cn.tom.web.model.ScheduleJob;

import java.util.List;

public interface ScheduleJobMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);

    List<ScheduleJob> selectAllJob();

    ScheduleJob selectByJobName(String name);
}