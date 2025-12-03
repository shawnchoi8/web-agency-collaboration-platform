package com.rdc.weflow_server.dto.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogResponseDto {
    private Long logId;
    private String actionType;     // CREATE, UPDATE, DELETE, LOGIN, LOGOUT, APPROVE 등
    private String targetTable;    // POST, COMMENT, PROJECT, USER ...
    private Long targetId;
    private String ipAddress;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
    private Long projectId;
    private String projectName;
}
