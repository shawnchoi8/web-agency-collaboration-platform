package com.rdc.weflow_server.dto.checklist;

import com.rdc.weflow_server.entity.checklist.ChecklistQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class QuestionResponse {
    private Long questionId;
    private String questionText;
    private String questionType;
    private Integer orderIndex;
    private List<OptionResponse> options;

    public static QuestionResponse from(ChecklistQuestion question) {
        return QuestionResponse.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType().name())
                .orderIndex(question.getOrderIndex())
                .options(
                        question.getOptions().stream()
                                .map(OptionResponse::from)
                                .collect(Collectors.toList())
                )
                .build();
    }
}