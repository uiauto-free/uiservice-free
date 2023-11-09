package com.uiautofree.common.config.global;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BTResponse<JSONObject> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        BTResponse<JSONObject> res = new BTResponse<>();
        e.printStackTrace();
        log.error(String.valueOf(e));
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
