package com.jiang.fzte.mapper;

import com.jiang.fzte.domain.RecordLog;
import com.jiang.fzte.domain.RecordLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecordLogMapper {
    long countByExample(RecordLogExample example);

    int deleteByExample(RecordLogExample example);

    int deleteByPrimaryKey(Long opTime);

    int insert(RecordLog record);

    int insertSelective(RecordLog record);

    List<RecordLog> selectByExample(RecordLogExample example);

    RecordLog selectByPrimaryKey(Long opTime);

    int updateByExampleSelective(@Param("record") RecordLog record, @Param("example") RecordLogExample example);

    int updateByExample(@Param("record") RecordLog record, @Param("example") RecordLogExample example);

    int updateByPrimaryKeySelective(RecordLog record);

    int updateByPrimaryKey(RecordLog record);
}