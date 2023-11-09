package com.uiautofree.agent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uiautofree.agent.dao.ActionMapper;
import com.uiautofree.agent.domain.ActionDO;
import com.uiautofree.agent.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ActionServiceImpl implements ActionService {

    @Autowired
    ActionMapper actionMapper;

    @Override
    public Long createAction(ActionDO record) {
        if (record.getId() != null) {
            this.update(record.getId(), record);
        }

        if (record.getCommand() != null && !record.getCommand().equals("") && record.getName() != null && !record.getName().equals("")) {
            if (this.getAction(record.getCommand()) != null) {
                return -1L;
            } else {
                record.setGmtCreate(LocalDateTime.now());
                record.setGmtModified(LocalDateTime.now());
                record.setIsDeleted(0);
                return this.actionMapper.createAction(record);
            }
        } else {
            return -1L;
        }
    }

    public void update(Long id, ActionDO actionDO) {
        if (this.getAction(id) != null) {
            actionDO.setId(id);
            actionDO.setGmtModified(LocalDateTime.now());
            this.actionMapper.update(actionDO);
        }
    }

    @Override
    public ActionDO getAction(Long id) {
        return this.actionMapper.getById(id);
    }

    @Override
    public List<ActionDO> getActionList(String searchKey, String type, Integer common, Integer creatorId, int pageStart, int pageSize) {
        pageStart = (pageStart - 1) * pageSize;
        if(searchKey != null) {
            searchKey = "%" + searchKey + "%";
        }
        return actionMapper.getActionsByParams(searchKey, type, common, creatorId, pageStart, pageSize);
    }

    @Override
    public List<ActionDO> getActionList(List<Long> actionIds) {
        return null;
    }

    @Override
    public Long count(String searchKey, String type, Integer common, Integer creatorId) {
        if(searchKey != null) {
            searchKey = "%" + searchKey + "%";
        }
        return actionMapper.count(searchKey, type, common, creatorId);
    }

    @Override
    public List<ActionDO> getActionsByCommand(String command) {
        List<ActionDO> actions = actionMapper.getByCommand(command);
        return actions;
    }

    @Override
    public ActionDO getAction(String command) {
        List<ActionDO> actions = actionMapper.getByCommand(command);
        return null != actions && !actions.isEmpty() ? (ActionDO)actions.get(0) : null;
    }

    @Override
    public ActionDO updateAction(Long id, ActionDO action) {
        if (this.getAction(id) != null) {
            action.setId(id);
            action.setGmtModified(LocalDateTime.now());
            this.actionMapper.update(action);
        }
        return getAction(id);
    }

    private Boolean checkDependence(ActionDO actionDO) {
        boolean noCircle = true;
        String originIds = actionDO.getSubActionIds();
        if (originIds != null && !originIds.isEmpty()) {
            JSONArray subActionIds = JSON.parseArray(actionDO.getSubActionIds());
            if (subActionIds.isEmpty()) {
                return noCircle;
            } else {
                for (int i = 0; i < subActionIds.size(); i++) {
                    ActionDO temp = getAction(subActionIds.getLong(i));
                    JSONArray jsonArray = JSON.parseArray(temp.getSubActionIds());
                    if (jsonArray.contains(actionDO.getId())) {
                        noCircle = false;
                        break;
                    }
                }

                return noCircle;
            }
        } else {
            return noCircle;
        }
    }

    private Boolean checkParams(String paramsString) {
        boolean result = true;
        List<String> keys = Arrays.asList("key", "name", "type", "value", "required", "description");
        JSONArray params = JSON.parseArray(paramsString);

        try {
            int i = 0;

            while (i < params.size()) {
                JSONObject object = params.getJSONObject(i);
                String key = object.getString("key");
                // key 不能为空
                if (null != key && !key.isEmpty()) {
                    String name = object.getString("name");
                    if (null == name || name.isEmpty()) {
                        // name 必填
                        return false;
                    }
                    String type = object.getString("type");
                    if (null != type && !type.isEmpty()) {
                        // type 必填
                        List<String> typeList = Arrays.asList("int", "string", "list", "select");
                        if (!typeList.contains(type)) {
                            // 必须是这几种类型
                            return false;
                        }
                        if ("select".equals(type)) {
                            JSONArray value = object.getJSONArray("value");
                            if (value.isEmpty()) {
                                // 必须有 value
                                return false;
                            }
                        }
                        Boolean required = object.getBoolean("required");
                        if (required == null) {
                            // 必须是必填或者不必填
                            return false;
                        }
                        ++i;
                        continue;
                    }
                    return false;
                }
                return false;
            }
        } catch (Exception var12) {
            result = false;
        }

        return result;
    }
}
