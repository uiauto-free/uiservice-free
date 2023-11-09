package com.uiautofree.agent.dao;

import com.uiautofree.agent.domain.ActionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tsbxmw
 * @since 2023-08-12
 */
public interface ActionMapper {

    /**
     * 插入
     *
     * @param action
     * @return
     */
    Long createAction(ActionDO action);

    /**
     * 通过 id 获取
     *
     * @param id
     * @return
     */
    ActionDO getById(Long id);

    /**
     * 列表搜索
     * @param searchKey
     * @param type
     * @param common
     * @param creatorId
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<ActionDO> getActionsByParams(@Param("searchKey") String searchKey,
                                      @Param("type") String type,
                                      @Param("common") Integer common,
                                      @Param("creatorId") Integer creatorId,
                                      @Param("pageStart") int pageStart,
                                      @Param("pageSize") int pageSize);

    /**
     * 计算数量
     * @param searchKey
     * @param type
     * @param common
     * @param creatorId
     * @return
     */
    Long count(@Param("searchKey") String searchKey,
               @Param("type") String type,
               @Param("common") Integer common,
               @Param("creatorId") Integer creatorId);

    /**
     * 通过 command 获取
     *
     * @param command
     * @return
     */
    List<ActionDO> getByCommand(String command);

    /**
     * 更新 action
     *
     * @param actionDO
     * @return
     */
    int update(ActionDO actionDO);
}
