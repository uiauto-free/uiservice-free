package com.uiautofree.agent.service;

import com.uiautofree.agent.domain.ActionDO;

import java.util.List;

public interface ActionService {
    /**
     * 获取 action
     *
     * @param id
     * @return
     */
    ActionDO getAction(Long id);

    /**
     * 搜索
     * @param searchKey
     * @param type
     * @param common
     * @param creatorId
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<ActionDO> getActionList(String searchKey, String type, Integer common, Integer creatorId, int pageStart, int pageSize);

    /**
     * 计算数量
     * @param searchKey
     * @param type
     * @param common
     * @param creatorId
     * @return
     */
    Long count(String searchKey, String type, Integer common, Integer creatorId);

    /**
     * 查询 command
     *
     * @param command
     * @return
     */
    List<ActionDO> getActionsByCommand(String command);

    /**
     * 获取单个（但根据 command)
     *
     * @param command
     * @return
     */
    ActionDO getAction(String command);

    /**
     * 更新
     *
     * @param id
     * @param action
     * @return
     */
    ActionDO updateAction(Long id, ActionDO action);

    /**
     * 创建 Action
     *
     * @param action
     * @return
     */
    Long createAction(ActionDO action);
}
