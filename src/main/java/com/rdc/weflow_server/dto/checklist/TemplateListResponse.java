package com.rdc.weflow_server.dto.checklist;

import com.rdc.weflow_server.entity.checklist.Checklist;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TemplateListResponse {

    private Long templateId;
    private String title;
    private String description;
    private String category;
    private boolean isLocked;
    private int questionCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TemplateListResponse from(Checklist template, int questionCount) {
        return TemplateListResponse.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .description(template.getDescription())
                .category(template.getCategory())
                .isLocked(template.getIsLocked())
                .questionCount(questionCount)
                .createdAt(template.getCreatedDate())
                .updatedAt(template.getLastModifiedDate())
                .build();
    }
}

