package com.uiautofree.common.constant;

import java.util.regex.Pattern;

public interface CommonConstant {
    Pattern IS_INTEGER_PATTERN = Pattern.compile("^[-\\*]?[\\d]*$");
    public interface IsDeleted {
        int NOT_DELETE = 0;
        int DELETED = 1;
    }

    public interface Agent {
        String TOKEN = "free-agent-token-xxxyz";
    }


    interface HttpCode {
        Integer HTTP_SUCCESS_200 = 200;
        Integer HTTP_SUCCESS_0 = 0;
        Integer HTTP_INTERNAL_SERVER_ERROR = 500;
    }

    interface HttpResponseKeys {
        String RESULT_SUCCESS = "success";
        String MODULE = "data";
        String RESULT_CODE = "code";
    }
}
