package com.uiautofree.common.domain;

import lombok.Data;

@Data
public class HttpCommonResult {
    private String result;
    private Integer httpResponseCode;
}
