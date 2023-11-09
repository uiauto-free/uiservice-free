package com.uiautofree.runner.dao;

import com.uiautofree.runner.domain.Job;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description job
 * @author tsbxmw
 * @date 2023-07-06
 */
@Mapper
@Repository
public interface JobMapper {

    /**
     * 新增
     * @author tsbxmw
     * @date 2023/07/06
     **/
    int insert(Job job);

    /**
     * 刪除
     * @author tsbxmw
     * @date 2023/07/06
     **/
    int delete(int id);

    /**
     * 更新
     * @author tsbxmw
     * @date 2023/07/06
     **/
    int update(Job job);

    /**
     * 查询 根据主键 id 查询
     * @author tsbxmw
     * @date 2023/07/06
     **/
    Job load(int id);

    /**
     * 查询 分页查询
     * @author tsbxmw
     * @date 2023/07/06
     **/
    List<Job> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author tsbxmw
     * @date 2023/07/06
     **/
    int pageListCount(int offset,int pagesize);

}