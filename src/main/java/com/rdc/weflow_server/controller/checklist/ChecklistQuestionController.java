package com.rdc.weflow_server.controller.checklist;

import com.rdc.weflow_server.common.api.ApiResponse;
import com.rdc.weflow_server.dto.checklist.QuestionRequest;
import com.rdc.weflow_server.dto.checklist.QuestionResponse;
import com.rdc.weflow_server.service.checklist.ChecklistQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class ChecklistQuestionController {
    private final ChecklistQuestionService questionService;

    // 질문 생성
    @PostMapping
    public ApiResponse<QuestionResponse> createQuestion(
            @RequestBody QuestionRequest request
    ) {
        Long questionId = questionService.createQuestion(request);

        return ApiResponse.success(
                "QUESTION_CREATED",
                QuestionResponse.builder()
                        .questionId(questionId)
                        .build()
        );
    }

    // 질문 수정
    @PatchMapping("/{questionId}")
    public ApiResponse<QuestionResponse> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody QuestionRequest request
    ) {
        Long id = questionService.updateQuestion(questionId, request);

        return ApiResponse.success(
                "QUESTION_UPDATED",
                QuestionResponse.builder()
                        .questionId(id)
                        .build()
        );
    }
    // 질문 삭제
    @DeleteMapping("/{questionId}")
    public ApiResponse<Long> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);

        return ApiResponse.success(
                "QUESTION_DELETED",
                questionId
        );
    }
}
