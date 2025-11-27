package com.rdc.weflow_server.dto.checklist;

import com.rdc.weflow_server.entity.checklist.ChecklistOption;
import com.rdc.weflow_server.entity.checklist.ChecklistQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
                        question.getOptions() != null
                                ? question.getOptions().stream()
                                .map(OptionResponse::from)
                                .toList()
                                : List.of()
                )
                .build();
    }

    // 옵션 DTO 내부 정적 클래스
    @Getter
    @Builder
    public static class OptionResponse {
        private Long optionId;
        private String optionText;
        private Boolean hasInput;
        private Integer orderIndex;

        public static OptionResponse from(ChecklistOption option) {
            return OptionResponse.builder()
                    .optionId(option.getId())
                    .optionText(option.getOptionText())
                    .hasInput(option.getHasInput())
                    .orderIndex(option.getOrderIndex())
                    .build();
        }
    }
}
