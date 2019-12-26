package com.baronj.template.service;

import com.baronj.template.entity.Test;
import com.baronj.template.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;

    public int insert(Test test) throws Exception {
        return testMapper.insert(test);
    }

    public void delete(long id) throws Exception {
        testMapper.deleteById(id);
    }

    public void update(Test test) throws Exception {
        testMapper.updateById(test);
    }

    public Test get(long id) throws Exception {
        return testMapper.selectById(id);
    }

    public List<Test> query() throws Exception {
        return testMapper.selectList(null);
    }
}