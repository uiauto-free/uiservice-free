package com.uiautofree.common.config.global;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseTemplateResponse<JSONObject> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        BaseTemplateResponse<JSONObject> res = new BaseTemplateResponse<>();
        e.printStackTrace();
        JSONObject object = new JSONObject();
        object.put("error", e.getMessage());
        object.put("code", 500);
        response.setStatus(200);
        res.setCode(500);
        res.setData(object);
        res.setErrMsg(e.getMessage());
        return res;
    }
}
