package com.uiautofree.controller.agent;

import com.alibaba.fastjson.JSONObject;
import com.uiautofree.agent.domain.AgentResult;
import com.uiautofree.agent.domain.SeleniumAgent;
import com.uiautofree.agent.service.SeleniumAgentService;
import com.uiautofree.common.config.global.BTResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/agent")
@Slf4j
public class AgentController {

    @Autowired
    private SeleniumAgentService service;

    @PostMapping("/register")
    @ResponseBody
    public BTResponse<JSONObject> register(@RequestBody JSONObject data) {
        BTResponse<JSONObject> response = new BTResponse<>();
        SeleniumAgent agent = new SeleniumAgent(data);
        AgentResult agentResult = service.register(agent);
        response.setData(agentResult.toJsonString());
        response.setCode(200);
        return response;
    }


    @PostMapping("/report")
    @ResponseBody
    public BTResponse<JSONObject> report(@RequestBody JSONObject data) {
        BTResponse<JSONObject> response = new BTResponse<>();
        Long agentId = data.getLong("agentId");
        int status = data.getInteger("status");
        int processStatus = data.getInteger("processStatus");
        String ipAddress = data.getString("ipAddress");
        AgentResult agentResult = service.report(agentId, status, processStatus);
        response.setData(agentResult.toJsonString());
        response.setCode(200);
        return response;
    }

    @PostMapping("/heartbeat")
    @ResponseBody
    public BTResponse<JSONObject> heartbeat(@RequestBody JSONObject data) {
        BTResponse<JSONObject> response = new BTResponse<>();
        JSONObject body = new JSONObject();
        AgentResult result = service.report(data.getLong("agentId"), data.getInteger("nodeStatus"), data.getInteger("nodeProcessStatus"));
        body.put("result", result);
        response.setData(body);
        response.setCode(200);
        return response;
    }
}
