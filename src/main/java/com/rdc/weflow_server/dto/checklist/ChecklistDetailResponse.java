package com.rdc.weflow_server.dto.checklist;

import com.rdc.weflow_server.entity.checklist.Checklist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ChecklistDetailResponse {

    private Long checklistId;
    private String title;
    private String description;
    private boolean isLocked;
    private String category;

    private Long stepId;
    private String stepName;

    private Long templateId; // 템플릿 기반이면 값 있음

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<QuestionResponse> questions;

    public static ChecklistDetailResponse from(Checklist checklist) {
        return ChecklistDetailResponse.builder()
                .checklistId(checklist.getId())
                .title(checklist.getTitle())
                .description(checklist.getDescription())
                .category(checklist.getCategory())
                .isLocked(checklist.getIsLocked())
                .stepId(checklist.getStep() != null ? checklist.getStep().getId() : null)
                .templateId(checklist.getTemplate() != null ? checklist.getTemplate().getId() : null)
                .createdAt(checklist.getCreatedAt())
                .updatedAt(checklist.getUpdatedAt())
                .questions(
                        checklist.getQuestions().stream()
                                .map(QuestionResponse::from)
                                .toList()
                )
                .build();
    }
}
