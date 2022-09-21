package com.jiang.fzte.mapper;

import com.jiang.fzte.domain.Disallow_word;
import com.jiang.fzte.domain.Disallow_wordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface Disallow_wordMapper {
    long countByExample(Disallow_wordExample example);

    int deleteByExample(Disallow_wordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Disallow_word record);

    int insertSelective(Disallow_word record);

    List<Disallow_word> selectByExample(Disallow_wordExample example);

    Disallow_word selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Disallow_word record, @Param("example") Disallow_wordExample example);

    int updateByExample(@Param("record") Disallow_word record, @Param("example") Disallow_wordExample example);

    int updateByPrimaryKeySelective(Disallow_word record);

    int updateByPrimaryKey(Disallow_word record);
}