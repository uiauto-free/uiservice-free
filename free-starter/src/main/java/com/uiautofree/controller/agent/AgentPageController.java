package com.uiautofree.controller.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uiautofree.agent.domain.AgentResult;
import com.uiautofree.agent.domain.SeleniumAgent;
import com.uiautofree.agent.service.SeleniumAgentService;
import com.uiautofree.common.config.global.BTResponse;
import com.uiautofree.common.config.global.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/agent/page")
@Slf4j
public class AgentPageController {

    @Autowired
    private SeleniumAgentService service;

    @GetMapping("/get")
    @ResponseBody
    public BTResponse<JSONObject> getById(@RequestParam Long id) {
        BTResponse<JSONObject> response = new BTResponse<>();
        SeleniumAgent agent = service.getById(id);
        response.setData(JSON.parseObject(JSON.toJSONString(agent)));
        response.setCode(200);
        return response;
    }


    @GetMapping("/list")
    @ResponseBody
    public BTResponse<PageBean<SeleniumAgent>> listAgent(@RequestParam(required = false, defaultValue = "1") int pageStart,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BTResponse<PageBean<SeleniumAgent>> response = new BTResponse<>();
        PageBean<SeleniumAgent> pageBean = new PageBean<>();
        List<SeleniumAgent> agents = service.listAgent(pageStart, pageSize);
        pageBean.setData(agents);
        pageBean.setTotalCount(service.countAgent());
        response.setData(pageBean);
        response.setCode(200);
        return response;
    }

}
