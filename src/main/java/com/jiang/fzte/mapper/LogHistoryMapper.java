package com.jiang.fzte.mapper;

import com.jiang.fzte.domain.LogHistory;
import com.jiang.fzte.domain.LogHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogHistoryMapper {
    long countByExample(LogHistoryExample example);

    int deleteByExample(LogHistoryExample example);

    int deleteByPrimaryKey(Long opTime);

    int insert(LogHistory record);

    int insertSelective(LogHistory record);

    List<LogHistory> selectByExample(LogHistoryExample example);

    LogHistory selectByPrimaryKey(Long opTime);

    int updateByExampleSelective(@Param("record") LogHistory record, @Param("example") LogHistoryExample example);

    int updateByExample(@Param("record") LogHistory record, @Param("example") LogHistoryExample example);

    int updateByPrimaryKeySelective(LogHistory record);

    int updateByPrimaryKey(LogHistory record);
}