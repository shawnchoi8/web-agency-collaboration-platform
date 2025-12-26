package com.rdc.weflow_server.service.checklist;

import com.rdc.weflow_server.dto.checklist.request.QuestionReorderRequest;
import com.rdc.weflow_server.dto.checklist.request.QuestionRequest;
import com.rdc.weflow_server.entity.checklist.Checklist;
import com.rdc.weflow_server.entity.checklist.ChecklistQuestion;
import com.rdc.weflow_server.entity.log.ActionType;
import com.rdc.weflow_server.entity.log.TargetTable;
import com.rdc.weflow_server.exception.BusinessException;
import com.rdc.weflow_server.exception.ErrorCode;
import com.rdc.weflow_server.repository.checklist.ChecklistOptionRepository;
import com.rdc.weflow_server.repository.checklist.ChecklistQuestionRepository;
import com.rdc.weflow_server.repository.checklist.ChecklistRepository;
import com.rdc.weflow_server.service.log.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChecklistQuestionService {
    private final ChecklistRepository checklistRepository;
    private final ChecklistQuestionRepository questionRepository;
    private final ChecklistOptionRepository optionRepository;
    private final ActivityLogService activityLogService;

    // 질문 생성
    public Long createQuestion(QuestionRequest request, Long userId, String ip) {

        Checklist checklist = checklistRepository.findById(request.getChecklistId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_NOT_FOUND));

        int nextOrder = questionRepository.countByChecklist(checklist) + 1;

        ChecklistQuestion question = ChecklistQuestion.builder()
                .checklist(checklist)
                .questionText(request.getQuestionText())
                .questionType(request.getQuestionType())
                .orderIndex(nextOrder)
                .build();

        ChecklistQuestion saved = questionRepository.save(question);

        activityLogService.createLog(
                ActionType.CREATE,
                TargetTable.CHECKLIST_QUESTION,
                saved.getId(),
                userId,
                checklist.getStep().getProject().getId(),
                ip
        );

        return saved.getId();
    }

    // 질문 수정
    public Long updateQuestion(Long questionId, QuestionRequest request, Long userId, String ip) {

        ChecklistQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_QUESTION_NOT_FOUND));
        Long projectId = question.getChecklist().getStep().getProject().getId();

        question.updateQuestion(
                request.getQuestionText(),
                request.getQuestionType(),
                null
        );

        activityLogService.createLog(
                ActionType.UPDATE,
                TargetTable.CHECKLIST_QUESTION,
                questionId,
                userId,
                projectId,
                ip
        );

        return question.getId();
    }

    // 질문 삭제
    public void deleteQuestion(Long questionId, Long userId, String ip) {

        ChecklistQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_QUESTION_NOT_FOUND));
        Long projectId = question.getChecklist().getStep().getProject().getId();

        questionRepository.delete(question);

        activityLogService.createLog(
                ActionType.DELETE,
                TargetTable.CHECKLIST_QUESTION,
                questionId,
                userId,
                projectId,
                ip
        );
    }

    // 질문 순서 재정렬
    @Transactional
    public void reorderQuestions(QuestionReorderRequest request) {

        // 체크리스트 존재 여부 확인
        checklistRepository.findById(request.getChecklistId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_NOT_FOUND));

        // 새 순서 리스트
        List<Long> orderedIds = request.getOrderedIds();

        for (int i = 0; i < orderedIds.size(); i++) {
            Long questionId = orderedIds.get(i);

            ChecklistQuestion question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_QUESTION_NOT_FOUND));

            // 이 질문이 해당 체크리스트에 소속된 질문인지 검증
            if (!question.getChecklist().getId().equals(request.getChecklistId())) {
                throw new BusinessException(ErrorCode.CHECKLIST_QUESTION_NOT_IN_CHECKLIST);
            }

            // orderIndex 재부여
            question.updateQuestion(
                    question.getQuestionText(),  // 내용은 그대로
                    question.getQuestionType(),
                    i + 1                       // 새 순서
            );
        }
    }
}
