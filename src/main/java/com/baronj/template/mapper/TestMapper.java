package com.baronj.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baronj.template.entity.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestMapper extends BaseMapper<Test> {
    int insertOne(Test test);

    void delete(long id);

    void update(Test test);

    Test get(@Param("id") long id);

    List<Test> query();
}
