package com.uiautofree.agent.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author tsbxmw
 * @since 2023-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ActionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String owner;

    private String name;

    private String type;

    private String command;

    private String params;

    private String customConfig;

    private Integer common;

    private String sample;

    private String remark;

    private String subActionIds;

    private Integer isDeleted;

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("owner", this.owner);
        obj.put("createTime", this.gmtCreate);
        obj.put("modifiedTime", this.gmtModified);
        obj.put("name", this.name);
        obj.put("type", this.type);
        obj.put("command", this.command);
        obj.put("common", this.common);
        obj.put("sample", this.sample);
        obj.put("remark", this.remark);
        obj.put("subActionIds", this.subActionIds);
        obj.put("params", JSON.parseArray(this.params));
        obj.put("customConfig", this.customConfig);
        return obj;
    }
}
