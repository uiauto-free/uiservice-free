package com.uiautofree.common.config.global;

import lombok.Data;

@Data
public class BTResponse<T>{
    private int code;
    private T data;
    private String errMsg;
}
