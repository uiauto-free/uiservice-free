package com.uiautofree.agent.service.impl;

import com.uiautofree.agent.dao.ActionMapper;
import com.uiautofree.agent.domain.ActionDO;
import com.uiautofree.agent.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

//
//    public long count(SpicyUiautoActionParam param) {
//        return this.spicyUiautoActionDAO.countByParam(param);
//    }
//
//    public long count(String searchKey, String type, Integer common, Integer creatorId) {
//        return (long)this.getActions(searchKey, type, common, creatorId, 0, 999).size();
//    }
//
//    public List<ActionDO> getActions(SpicyUiautoActionParam param) {
//        List<ActionDO> actions = this.spicyUiautoActionDAO.selectByParamWithBLOBs(param);
//        return null != actions && !actions.isEmpty() ? actions : null;
//    }
//
//    public List<ActionDO> getActions(String searchKey, int pageStart, int pageSize) {
//        SpicyUiautoActionParam param = new SpicyUiautoActionParam();
//        SpicyUiautoActionParam.Criteria criteria1 = param.createCriteria().andIsDeleteEqualTo(0);
//        SpicyUiautoActionParam.Criteria criteria2 = param.createCriteria().andIsDeleteEqualTo(0);
//        if (searchKey != null && !searchKey.equals("")) {
//            criteria1.andNameLike("%" + searchKey + "%");
//            criteria2.andCommandLike("%" + searchKey + "%");
//            param.or(criteria2);
//        }
//
//        param.setPagination(pageStart, pageSize);
//        param.appendOrderByClause(OrderCondition.ID, SortType.ASC);
//        return this.getActions(param);
//    }
//
//    public List<ActionDO> getActions(String searchKey, String type, Integer common, Integer creatorId, int pageStart, int pageSize) {
//        SpicyUiautoActionParam param = new SpicyUiautoActionParam();
//        SpicyUiautoActionParam.Criteria criteria1 = param.createCriteria().andIsDeleteEqualTo(0);
//        SpicyUiautoActionParam.Criteria criteria2 = param.createCriteria().andIsDeleteEqualTo(0);
//        if (searchKey != null && !searchKey.equals("")) {
//            criteria1.andNameLike("%" + searchKey + "%");
//            criteria2.andCommandLike("%" + searchKey + "%");
//            if (type != null && !type.equals("")) {
//                criteria1.andTypeEqualTo(type);
//                criteria2.andTypeEqualTo(type);
//            }
//
//            if (common != null) {
//                criteria1.andCommonEqualTo(common);
//                criteria2.andCommonEqualTo(common);
//            }
//
//            if (creatorId != null) {
//                criteria1.andCreatorIdEqualTo(creatorId);
//                criteria2.andCreatorIdEqualTo(creatorId);
//            }
//
//            param.or(criteria2);
//        } else {
//            if (type != null && !type.equals("")) {
//                criteria1.andTypeEqualTo(type);
//            }
//
//            if (common != null) {
//                criteria1.andCommonEqualTo(common);
//            }
//
//            if (creatorId != null) {
//                criteria1.andCreatorIdEqualTo(creatorId);
//            }
//        }
//
//        param.setPagination(pageStart, pageSize);
//        param.appendOrderByClause(OrderCondition.ID, SortType.ASC);
//        return this.getActions(param);
//    }
//
//    private Boolean checkDependence(ActionDO ActionDO) {
//        Boolean noCircle = true;
//        String originIds = ActionDO.getSubActionIds();
//        if (originIds != null && !originIds.isEmpty()) {
//            JSONArray subActionIds = JSON.parseArray(ActionDO.getSubActionIds());
//            if (subActionIds.size() == 0) {
//                return noCircle;
//            } else {
//                String sqlCheckIds = "";
//
//                for (int i = 0; i < subActionIds.size(); ++i) {
//                    if (i == 0) {
//                        sqlCheckIds = subActionIds.get(i).toString();
//                    } else {
//                        sqlCheckIds = sqlCheckIds + "," + subActionIds.get(i).toString();
//                    }
//                }
//
//                List<ActionDO> ActionDOList = this.spicyUiautoActionDAO.listByIds(sqlCheckIds);
//                Iterator var7 = ActionDOList.iterator();
//
//                while (var7.hasNext()) {
//                    ActionDO temp = (ActionDO)var7.next();
//                    JSONArray jsonArray = JSON.parseArray(temp.getSubActionIds());
//                    if (jsonArray.contains(ActionDO.getId())) {
//                        noCircle = false;
//                        break;
//                    }
//                }
//
//                return noCircle;
//            }
//        } else {
//            return noCircle;
//        }
//    }
//
//    private Boolean checkParams(String paramsString) {
//        Boolean result = true;
//        List<String> keys = Arrays.asList("key", "name", "type", "value", "required", "description");
//        JSONArray params = JSON.parseArray(paramsString);
//
//        try {
//            int i = 0;
//
//            while (i < params.size()) {
//                JSONObject object = params.getJSONObject(i);
//                String key = object.getString("key");
//                if (null != key && !key.isEmpty()) {
//                    String name = object.getString("name");
//                    if (null == name && name.isEmpty()) {
//                        return false;
//                    }
//
//                    String type = object.getString("type");
//                    if (null != type && !type.isEmpty()) {
//                        List<String> typeList = Arrays.asList("int", "string", "list", "select");
//                        if (!typeList.contains(type)) {
//                            return false;
//                        }
//
//                        if ("select".equals(type)) {
//                            JSONArray value = object.getJSONArray("value");
//                            if (value.size() == 0) {
//                                return false;
//                            }
//                        }
//
//                        Boolean required = object.getBoolean("required");
//                        if (required == null) {
//                            return false;
//                        }
//
//                        ++i;
//                        continue;
//                    }
//
//                    return false;
//                }
//
//                return false;
//            }
//        } catch (Exception var12) {
//            result = false;
//        }
//
//        return result;
//    }
}
