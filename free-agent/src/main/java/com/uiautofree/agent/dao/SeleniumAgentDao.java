package com.uiautofree.agent.dao;

import com.uiautofree.agent.domain.SeleniumAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tsbxmw
 * @since 2022-12-19
 */
@Mapper
public interface SeleniumAgentDao {
    /**
     * 根据 mac 查询数据是否存在
     *
     * @param macAddress
     * @param isDeleted
     * @return
     */
    SeleniumAgent getByMacAndDeleted(@Param("macAddress") String macAddress, @Param("isDeleted") Integer isDeleted);

    /**
     * 根据 ip 查询数据
     *
     * @param macAddress
     * @param isDeleted
     * @return
     */
    SeleniumAgent getByIpAndDeleted(@Param("ipAddress") String macAddress, @Param("isDeleted") Integer isDeleted);

    /**
     * 通过 id 获取
     *
     * @param id
     * @return
     */
    SeleniumAgent getById(@Param("id") Long id);

    /**
     * 获取列表
     *
     * @param pageStart
     * @param pageSize
     * @return
     */

    List<SeleniumAgent> listAgent(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

    /**
     * 计算数量
     * @return
     */
    Long countAgent();

    /**
     * 更新
     *
     * @param agent
     * @return
     */
    int updateByPrimaryKey(SeleniumAgent agent);

    /**
     * 插入
     *
     * @param agent
     * @return
     */
    int insertAgent(SeleniumAgent agent);
}
