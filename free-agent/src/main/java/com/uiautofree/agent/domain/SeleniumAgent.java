package com.uiautofree.agent.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author tsbxmw
 * @since 2022-12-19
 */
@Data
public class SeleniumAgent {

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String ipAddress;

    private String ipPort;

    private String macAddress;

    private String type;

    private String browserType;

    private String browserVersion;

    private String webDriverPath;

    private String extension;

    private Integer nodeStatus;

    private Integer nodeProcessStatus;

    private String remark;

    private Integer isDeleted;

    public SeleniumAgent() {
    }

    public SeleniumAgent(JSONObject data) {
        this.setMacAddress(data.getString("macAddress"));
        this.setIpAddress(data.getString("ipAddress"));
        this.setIpPort(data.getString("ipPort"));
        this.setBrowserType(data.getString("browserType"));
        this.setBrowserVersion(data.getString("browserVersion"));
        this.setNodeStatus(data.getInteger("nodeStatus"));
        this.setNodeProcessStatus(data.getInteger("nodeProcessStatus"));
        this.setType(data.getString("type"));
        this.setWebDriverPath(data.getString("webDriverPath"));
        this.setRemark(data.getString("remark"));
        this.setExtension(data.containsKey("extension") ? data.getJSONObject("extension").toJSONString() : "{}");
    }

    public JSONObject toJsonObject() {
        JSONObject temp = new JSONObject();
        temp.put("macAddress", macAddress);
        temp.put("ipAddress", ipAddress);
        temp.put("ipPort", ipPort);
        temp.put("browserType", browserType);
        temp.put("browserVersion", browserVersion);
        temp.put("nodeStatus", nodeStatus);
        temp.put("nodeProcessStatus", nodeProcessStatus);
        temp.put("type", type);
        temp.put("webDriverPath", webDriverPath);
        temp.put("remark", remark);
        temp.put("extension", extension);
        temp.put("id", id);
        temp.put("gmtCreate", gmtCreate);
        temp.put("gmtModified", gmtModified);
        temp.put("isDeleted", isDeleted);
        return temp;
    }

}
