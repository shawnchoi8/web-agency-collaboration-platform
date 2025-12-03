package com.rdc.weflow_server.dto.log;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityLogResponseDto {
    private Long logId;
    private String actionType;     // CREATE, UPDATE, DELETE, LOGIN, LOGOUT, APPROVE 등
    private String targetTable;    // POST, COMMENT, PROJECT, USER ...
    private Long targetId;
    private String ipAddress;
    private String createdAt;
    private Long userId;
    private String userName;
    private Long projectId;
    private String projectName;
}
