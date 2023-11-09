package com.uiautofree.runner.service;

import com.uiautofree.runner.domain.Job;

import java.util.Map;

/**
 * @description job
 * @author tsbxmw
 * @date 2023-07-06
 */
public interface JobService {

    /**
     * 新增
     */
    public Object insert(Job job);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public Object update(Job job);

    /**
     * 根据主键 id 查询
     */
    public Job load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);

}