package com.uiautofree.runner.service.impl;

import com.uiautofree.runner.domain.Job;
import com.uiautofree.runner.service.JobService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JobServiceImpl implements JobService {
    @Override
    public Object insert(Job job) {
        return null;
    }

    @Override
    public Object delete(int id) {
        return null;
    }

    @Override
    public Object update(Job job) {
        return null;
    }

    @Override
    public Job load(int id) {
        return null;
    }

    @Override
    public Map<String, Object> pageList(int offset, int pagesize) {
        return null;
    }
}
