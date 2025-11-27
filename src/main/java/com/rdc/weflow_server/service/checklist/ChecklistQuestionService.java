package com.rdc.weflow_server.service.checklist;

import com.rdc.weflow_server.dto.checklist.QuestionRequest;
import com.rdc.weflow_server.entity.checklist.Checklist;
import com.rdc.weflow_server.entity.checklist.ChecklistQuestion;
import com.rdc.weflow_server.repository.checklist.ChecklistOptionRepository;
import com.rdc.weflow_server.repository.checklist.ChecklistQuestionRepository;
import com.rdc.weflow_server.repository.checklist.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChecklistQuestionService {
    private final ChecklistRepository checklistRepository;
    private final ChecklistQuestionRepository questionRepository;
    private final ChecklistOptionRepository optionRepository;

    // 질문 생성
    public Long createQuestion(QuestionRequest request) {

        Checklist checklist = checklistRepository.findById(request.getChecklistId())
                .orElseThrow(() -> new RuntimeException("CHECKLIST_NOT_FOUND"));

        ChecklistQuestion question = ChecklistQuestion.builder()
                .checklist(checklist)
                .questionText(request.getQuestionText())
                .questionType(request.getQuestionType())
                .orderIndex(request.getOrderIndex())
                .build();

        ChecklistQuestion saved = questionRepository.save(question);

        return saved.getId();
    }

    // 질문 수정
    public Long updateQuestion(Long questionId, QuestionRequest request) {

        ChecklistQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("QUESTION_NOT_FOUND"));

        question.updateQuestion(
                request.getQuestionText(),
                request.getQuestionType(),
                request.getOrderIndex()
        );

        return question.getId();
    }

    // 질문 삭제
    public void deleteQuestion(Long questionId) {

        ChecklistQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("QUESTION_NOT_FOUND"));

        questionRepository.delete(question);
    }

}
