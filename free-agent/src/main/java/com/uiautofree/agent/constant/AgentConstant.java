package com.uiautofree.agent.constant;

public interface AgentConstant {
    public interface IpAddress {
        String LOCAL_IP_ADDRESS = "127.0.0.1";
    }

    public interface RegisterResult {
        int SUCCESS = 0;
        int FAIL = 1;
    }

    public interface RegisterErrMsg {
        String LOCAL_IP_ERR = "127.0.0.1 is local IP ! check it !";
        String AGENT_NOT_EXISTS = "节点不存在！";
    }

    public interface ReportResult {
        int SUCCESS = 0;
        int FAIL = 1;
    }

    public interface NodeStatus {
        int OFFLINE = 0;
        int ONLINE = 1;
    }

    public interface NodeStatusDisplay {
        String ONLINE = "在线";
        String OFFLINE = "离线";
    }

    interface NodeProcessStatus {
        int BUSY = 0;
        int FREE = 1;
    }

    interface AgentResponseKeys {
        String IP_ADDRESS = "ipAddress";
    }

    interface AgentCheckTimes {
        int TIMES = 5;
        int WAIT_TIME = 5000;
    }
}
