package com.uiautofree.agent.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentResult {
    /**
     * 结果：
     * 0
     * 1
     */
    private int result;
    /**
     * agentId in database
     */
    private Long agentId;
    /**
     * ... message
     */
    private String message;

    public JSONObject toJsonString() {
        JSONObject temp = new JSONObject();
        temp.put("result", this.result);
        temp.put("agentId", this.agentId);
        temp.put("message", this.message);
        return temp;
    }
}
