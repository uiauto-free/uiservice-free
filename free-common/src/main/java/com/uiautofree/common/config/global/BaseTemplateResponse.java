package com.uiautofree.common.config.global;

import lombok.Data;

@Data
public class BaseTemplateResponse <T>{
    private int code;
    private T data;
    private String errMsg;
}
