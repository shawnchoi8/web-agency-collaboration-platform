package com.rdc.weflow_server.dto.checklist;

import lombok.Getter;

@Getter
public class ChecklistUpdateRequest {
    private String title;
    private String description;
    private Long stepId;
}
