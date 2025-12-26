package com.rdc.weflow_server.entity.log;

public enum ActionType {
    CREATE,
    UPDATE,
    DELETE,

    // 인증
    LOGIN,
    LOGOUT,

    // 승인/반려
    APPROVE,
    REJECT,

    // 파일
    UPLOAD,
    DOWNLOAD,

    // 기타
    REMOVE,
    SUBMIT
}

