package com.jiang.fzte.mapper;

import com.jiang.fzte.domain.Ipuvpv_data;
import com.jiang.fzte.domain.Ipuvpv_dataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Ipuvpv_dataMapper {
    long countByExample(Ipuvpv_dataExample example);

    int deleteByExample(Ipuvpv_dataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Ipuvpv_data record);

    int insertSelective(Ipuvpv_data record);

    List<Ipuvpv_data> selectByExample(Ipuvpv_dataExample example);

    Ipuvpv_data selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Ipuvpv_data record, @Param("example") Ipuvpv_dataExample example);

    int updateByExample(@Param("record") Ipuvpv_data record, @Param("example") Ipuvpv_dataExample example);

    int updateByPrimaryKeySelective(Ipuvpv_data record);

    int updateByPrimaryKey(Ipuvpv_data record);
}