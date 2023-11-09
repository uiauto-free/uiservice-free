package com.uiautofree.agent.service;

import com.uiautofree.agent.domain.AgentResult;
import com.uiautofree.agent.domain.SeleniumAgent;

import java.util.List;

public interface SeleniumAgentService {
    /**
     * 获取
     *
     * @param agentId
     * @return
     */
    SeleniumAgent getById(Long agentId);

    /**
     * 获取列表
     *
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<SeleniumAgent> listAgent(int pageStart, int pageSize);

    /**
     * 计算数量
     *
     * @return
     */
    Long countAgent();

    /**
     * 根据 mac
     *
     * @param macAddress
     * @return
     */
    SeleniumAgent getByMacAddress(String macAddress);

    /**
     * 更新数据
     *
     * @param agent
     * @return
     */
    int updateAgent(SeleniumAgent agent);

    /**
     * 注册
     *
     * @param seleniumAgent
     * @return
     */
    AgentResult register(SeleniumAgent seleniumAgent);

    /**
     * 心跳
     *
     * @param agentId
     * @param status
     * @param processStatus
     * @return
     */
    AgentResult report(Long agentId, int status, int processStatus);

    /**
     * 更新状态
     *
     * @param agentId
     * @param status
     * @param processStatus
     * @return
     */
    Boolean updateAgentStatus(Long agentId, int status, int processStatus);
}
