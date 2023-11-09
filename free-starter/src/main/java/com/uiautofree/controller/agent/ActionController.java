package com.uiautofree.controller.agent;

import com.alibaba.fastjson.JSONObject;
import com.uiautofree.agent.domain.ActionDO;
import com.uiautofree.agent.service.ActionService;
import com.uiautofree.common.config.global.BTResponse;
import com.uiautofree.common.config.global.PageBean;
import com.uiautofree.common.constant.CommonConstant.IsDeleted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.uiautofree.common.constant.CommonConstant.HttpCode.HTTP_INTERNAL_SERVER_ERROR;
import static com.uiautofree.common.constant.CommonConstant.HttpCode.HTTP_SUCCESS_200;

@Controller
@Slf4j
@RequestMapping(value = "/api/agent/action")
public class ActionController {
    @Autowired
    ActionService actionService;

    @GetMapping({"/get"})
    @ResponseBody
    public BTResponse<JSONObject> getById(@RequestParam Long id) {
        BTResponse<JSONObject> response = new BTResponse();
        ActionDO actionDO = this.actionService.getAction(id);
        if (actionDO == null) {
            response.setCode(HTTP_INTERNAL_SERVER_ERROR);
            response.setErrMsg("获取失败: ID=" + id + " 的命令不存在");
        } else {
            response.setData(actionDO.toJSONObject());
            response.setCode(HTTP_SUCCESS_200);
        }

        return response;
    }

    @ResponseBody
    @GetMapping({"/list"})
    public BTResponse<PageBean<JSONObject>> list(@RequestParam(required = false) String type, @RequestParam(required = false) Integer common, @RequestParam(required = false) Integer creatorId,
                                                 @RequestParam(required = false) String searchKey,
                                                 @RequestParam(required = false, defaultValue = "1") Integer startPage,
                                                 @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        BTResponse<PageBean<JSONObject>> response = new BTResponse();
        response.setCode(HTTP_SUCCESS_200);
        PageBean<JSONObject> pageBean = new PageBean();

        try {
            List<ActionDO> actionDOS = this.actionService.getActionList(searchKey, type, common, creatorId, startPage, pageSize);
            if (actionDOS == null || actionDOS.size() == 0) {
                response.setErrMsg("获取结果为空");
                return response;
            }

            List<JSONObject> jsonObjectList = actionDOS.stream().map((x) -> x.toJSONObject()).collect(Collectors.toList());
            Long count = this.actionService.count(searchKey, type, common, creatorId);
            pageBean.setPageSize(pageSize);
            pageBean.setCurrentPage(startPage);
            pageBean.setTotalCount(count);
            pageBean.setData(jsonObjectList);
            response.setData(pageBean);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setErrMsg("获取失败: " + exception.getMessage());
            response.setCode(HTTP_INTERNAL_SERVER_ERROR);
            log.info("[GET action/list Failed] Err = {}; Stack = {}", exception.getMessage(),
                     Arrays.toString(exception.getStackTrace()));
        }

        return response;
    }

    @PostMapping({"/create"})
    @ResponseBody
    public BTResponse<Long> create(@RequestBody JSONObject data) {
        BTResponse<Long> response = new BTResponse();

        ActionDO actionDo = new ActionDO();
        try {
            actionDo.setName(data.getString("name"));
            actionDo.setCommand(data.getString("command"));
            actionDo.setCommon(data.getInteger("common"));
            actionDo.setOwner(data.getString("owner"));
            actionDo.setType(data.getString("type"));
            actionDo.setSample(data.getString("sample"));
            actionDo.setRemark(data.getString("remark"));
            actionDo.setSubActionIds(data.getString("subActionIds"));
            actionDo.setParams(data.getJSONArray("params").toJSONString());
            actionDo.setCustomConfig(data.getString("customConfig"));
            actionDo.setIsDeleted(IsDeleted.NOT_DELETE);
            log.info("[POST action/create]: Action = {}", data.toJSONString());
            ActionDO oldActionDo = this.actionService.getAction(actionDo.getCommand());
            if (oldActionDo != null) {
                actionDo.setGmtCreate(LocalDateTime.now());
                this.actionService.updateAction(oldActionDo.getId(), actionDo);
                response.setCode(HTTP_SUCCESS_200);
                response.setData(oldActionDo.getId());
                response.setErrMsg("命令已存在，完成注册更新");
                return response;
            } else {
                Long actionId = this.actionService.createAction(actionDo);
                if (actionId == -1) {
                    response.setCode(HTTP_SUCCESS_200);
                    response.setData(oldActionDo.getId());
                    response.setErrMsg("创建失败：必填字段为空");
                    return response;
                } else {
                    response.setCode(HTTP_SUCCESS_200);
                    response.setData(actionId);
                    response.setErrMsg("创建成功！");
                    return response;
                }
            }
        } catch (Exception e) {
            log.info("[POST action/register Failed] Err = {}; Stack = {}", e.getMessage(), Arrays.toString(e.getStackTrace()));
            response.setCode(HTTP_SUCCESS_200);
            response.setData(-1L);
            response.setErrMsg("创建失败: " + e.toString());
            e.printStackTrace();
            return response;
        }
    }

    @PostMapping({"/register"})
    @ResponseBody
    public BTResponse<Long> registerFromAgent(@RequestBody JSONObject data) {
        BTResponse<Long> response = new BTResponse();
        ActionDO actionDo = new ActionDO();

        try {
            actionDo.setName(data.getString("name"));
            actionDo.setCommand(data.getString("command"));
            actionDo.setCommon(data.getInteger("common"));
            actionDo.setOwner(data.getString("owner"));
            actionDo.setType(data.getString("type"));
            actionDo.setSample(data.getString("sample"));
            actionDo.setRemark(data.getString("remark"));
            actionDo.setSubActionIds(data.getString("subActionIds"));
            actionDo.setParams(data.getJSONArray("params").toJSONString());
            actionDo.setCustomConfig(data.getString("customConfig"));
            actionDo.setIsDeleted(IsDeleted.NOT_DELETE);
            log.info("[POST action/register]: Action = {}", data.toJSONString());
            ActionDO oldActionDo = this.actionService.getAction(actionDo.getCommand());
            if (oldActionDo != null) {
                actionDo.setGmtCreate(LocalDateTime.now());
                this.actionService.updateAction(oldActionDo.getId(), actionDo);
                response.setCode(HTTP_SUCCESS_200);
                response.setData(oldActionDo.getId());
                response.setErrMsg("命令已存在，完成注册更新");
                return response;
            } else {
                Long actionId = this.actionService.createAction(actionDo);
                if (actionId == -1) {
                    response.setCode(HTTP_SUCCESS_200);
                    response.setData(oldActionDo.getId());
                    response.setErrMsg("注册失败：必填字段为空");
                    return response;
                } else {
                    response.setCode(HTTP_SUCCESS_200);
                    response.setData(actionId);
                    response.setErrMsg("注册成功");
                    return response;
                }
            }
        } catch (Exception e) {
            log.info("[POST action/register Failed] Err = {}; Stack = {}", e.getMessage(), Arrays.toString(e.getStackTrace()));
            response.setCode(HTTP_SUCCESS_200);
            response.setData(-1L);
            response.setErrMsg("注册失败: " + e.toString());
            e.printStackTrace();
            return response;
        }
    }


    @DeleteMapping({"/delete"})
    public BTResponse<Long> removeById(@RequestParam Long id) {
        BTResponse<Long> response = new BTResponse();
        response.setCode(HTTP_SUCCESS_200);
        ActionDO oldActionDo = this.actionService.getAction(id);
        if (oldActionDo != null) {
            oldActionDo.setGmtModified(LocalDateTime.now());
            oldActionDo.setIsDeleted(IsDeleted.DELETED);
            this.actionService.updateAction(oldActionDo.getId(), oldActionDo);
            response.setData(oldActionDo.getId());
        } else {
            response.setData(oldActionDo.getId());
            response.setCode(HTTP_SUCCESS_200);
            response.setErrMsg("数据已被删除！");
        }
        return response;
    }
}
