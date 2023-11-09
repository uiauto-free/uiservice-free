package com.uiautofree.agent.service.impl;

import com.uiautofree.agent.constant.AgentConstant;
import com.uiautofree.agent.constant.AgentConstant.NodeStatus;
import com.uiautofree.agent.dao.SeleniumAgentDao;
import com.uiautofree.agent.domain.AgentResult;
import com.uiautofree.agent.domain.SeleniumAgent;
import com.uiautofree.agent.service.SeleniumAgentService;
import com.uiautofree.agent.service.check.AgentCheck;
import com.uiautofree.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.uiautofree.agent.constant.AgentConstant.IpAddress.LOCAL_IP_ADDRESS;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tsbxmw
 * @since 2022-12-19
 */
@Service
@Slf4j
public class SeleniumAgentServiceImpl implements SeleniumAgentService {

    @Autowired
    private SeleniumAgentDao seleniumAgentDao;
    @Autowired
    private AgentCheck agentCheck;

    @Override
    public SeleniumAgent getById(Long agentId) {
        return seleniumAgentDao.getById(agentId);
    }

    @Override
    public List<SeleniumAgent> listAgent(int pageStart, int pageSize) {
        int startIndex = (pageStart-1) * pageSize;
        return seleniumAgentDao.listAgent(startIndex, pageSize);
    }

    @Override
    public Long countAgent() {
        return seleniumAgentDao.countAgent();
    }

    @Override
    public SeleniumAgent getByMacAddress(String macAddress) {
        return seleniumAgentDao.getByMacAndDeleted(macAddress, CommonConstant.IsDeleted.NOT_DELETE);
    }

    @Override
    public int updateAgent(SeleniumAgent agent) {
        return seleniumAgentDao.updateByPrimaryKey(agent);
    }

    @Override
    public AgentResult register(SeleniumAgent seleniumAgent) {
        AgentResult agentResult = new AgentResult();
        if (seleniumAgent.getIpAddress().equals(LOCAL_IP_ADDRESS)) {
            agentResult.setResult(AgentConstant.RegisterResult.FAIL);
            agentResult.setMessage(AgentConstant.RegisterErrMsg.LOCAL_IP_ERR);
        }
        try {
            SeleniumAgent agent = seleniumAgentDao.getByMacAndDeleted(seleniumAgent.getMacAddress(), CommonConstant.IsDeleted.NOT_DELETE);
            if (agent != null) {
                seleniumAgent.setId(agent.getId());
                seleniumAgent.setGmtModified(LocalDateTime.now());
                seleniumAgent.setBrowserType(seleniumAgent.getBrowserType());
                seleniumAgent.setBrowserVersion(seleniumAgent.getBrowserVersion());
                seleniumAgent.setIpAddress(seleniumAgent.getIpAddress());
                seleniumAgent.setMacAddress(seleniumAgent.getMacAddress());
                seleniumAgent.setWebDriverPath(seleniumAgent.getWebDriverPath());
                seleniumAgent.setType(seleniumAgent.getType());
                seleniumAgent.setNodeStatus(NodeStatus.ONLINE);
                seleniumAgent.setIpPort(seleniumAgent.getIpPort());
                seleniumAgentDao.updateByPrimaryKey(seleniumAgent);
                agentResult.setAgentId(seleniumAgent.getId());
                agentResult.setResult(AgentConstant.RegisterResult.SUCCESS);
            } else {
                seleniumAgent.setGmtCreate(LocalDateTime.now());
                seleniumAgent.setGmtModified(LocalDateTime.now());
                seleniumAgent.setIsDeleted(CommonConstant.IsDeleted.NOT_DELETE);
                seleniumAgent.setBrowserType(seleniumAgent.getBrowserType());
                seleniumAgent.setBrowserVersion(seleniumAgent.getBrowserVersion());
                seleniumAgent.setIpAddress(seleniumAgent.getIpAddress());
                seleniumAgent.setMacAddress(seleniumAgent.getMacAddress());
                seleniumAgent.setWebDriverPath(seleniumAgent.getWebDriverPath());
                seleniumAgent.setType(seleniumAgent.getType());
                seleniumAgent.setNodeStatus(NodeStatus.ONLINE);
                seleniumAgentDao.insertAgent(seleniumAgent);
                agentResult.setAgentId(seleniumAgent.getId());
                agentResult.setResult(AgentConstant.RegisterResult.SUCCESS);
            }
        } catch (Exception e) {
            agentResult.setResult(AgentConstant.RegisterResult.FAIL);
            e.printStackTrace();
        }
        return agentResult;
    }

    @Override
    public AgentResult report(Long agentId, int status, int processStatus) {
        AgentResult agentResult = new AgentResult();
        SeleniumAgent agent = getById(agentId);
        if (agent == null) {
            agentResult.setResult(AgentConstant.ReportResult.FAIL);
            agentResult.setMessage(AgentConstant.RegisterErrMsg.AGENT_NOT_EXISTS);
        } else {
            agentCheck.healthCheck(agentId);
        }
        return agentResult;
    }

    @Override
    public Boolean updateAgentStatus(Long agentId, int status, int processStatus) {
        Boolean result = true;
        SeleniumAgent agent = getById(agentId);
        agent.setGmtModified(LocalDateTime.now());
        agent.setNodeStatus(status);
        agent.setNodeProcessStatus(processStatus);
        updateAgent(agent);
        return result;
    }
}
