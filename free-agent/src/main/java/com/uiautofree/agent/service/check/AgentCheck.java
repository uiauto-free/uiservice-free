package com.uiautofree.agent.service.check;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uiautofree.agent.constant.AgentConstant.AgentCheckTimes;
import com.uiautofree.agent.constant.AgentConstant.AgentResponseKeys;
import com.uiautofree.agent.constant.AgentConstant.NodeStatus;
import com.uiautofree.agent.dao.SeleniumAgentDao;
import com.uiautofree.agent.domain.SeleniumAgent;
import com.uiautofree.common.config.job.JobConfig;
import com.uiautofree.common.constant.CommonConstant.HttpCode;
import com.uiautofree.common.constant.CommonConstant.HttpResponseKeys;
import com.uiautofree.common.domain.HttpCommonResult;
import com.uiautofree.common.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.uiautofree.agent.constant.AgentConstant.AgentCheckTimes.WAIT_TIME;
import static com.uiautofree.agent.constant.AgentUrl.UrlHelper.HEART_BEAT;

@Component
@Slf4j
public class AgentCheck {

    @Autowired
    SeleniumAgentDao agentDao;

    @Async(JobConfig.JOB_THREAD_POOL)
    public void healthCheck(Long agentId) {
        int times = 0;
        Boolean registerResult = true;
        while (times < AgentCheckTimes.TIMES) {
            try {
                registerResult = true;
                log.info("[healthCheck] check begin : agentId == {}", agentId);
                JSONObject body = new JSONObject();
                SeleniumAgent agent = agentDao.getById(agentId);
                String registerMessage = "";
                String url = "http://" + agent.getIpAddress() + ":" + agent.getIpPort() + HEART_BEAT;
                url += "?env=local";
                try {
                    HttpCommonResult httpCommonResult = HttpClientUtils.doGetReturnHttpResult(url);
                    if (!httpCommonResult.getHttpResponseCode().equals(HttpCode.HTTP_SUCCESS_200)) {
                        registerResult = false;
                        registerMessage = httpCommonResult.getResult();
                    } else {
                        JSONObject response = JSON.parseObject(httpCommonResult.getResult());
                        if (!HttpCode.HTTP_SUCCESS_200.equals(response.getInteger(HttpResponseKeys.RESULT_CODE))) {
                            registerResult = false;
                            registerMessage = httpCommonResult.getResult();
                        } else {
                            String ipAddress = response.getString(AgentResponseKeys.IP_ADDRESS);
                            if (!agent.getIpAddress().equals(ipAddress)) {
                                agent.setIpAddress(ipAddress);
                                agent.setGmtModified(LocalDateTime.now());
                                agentDao.updateByPrimaryKey(agent);
                            }
                        }
                    }
                } catch (IOException e) {
                    registerResult = false;
                    registerMessage = e.getMessage();
                }
                if (registerResult) {
                    log.info("[healthCheck] success : info - {} ", registerMessage);
                } else {
                    log.info("[healthCheck] fail : error - {} ", registerMessage);
                }
                Thread.sleep(WAIT_TIME);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
            times++;
        }
        if (!registerResult) {
            log.info("[healthCheck] 直接下线！agentId={}", agentId);
            SeleniumAgent agent = agentDao.getById(agentId);
            agent.setNodeStatus(NodeStatus.OFFLINE);
            agentDao.updateByPrimaryKey(agent);
        }
    }
}
