package com.rdc.weflow_server.service.checklist;

import com.rdc.weflow_server.dto.checklist.request.OptionReorderRequest;
import com.rdc.weflow_server.dto.checklist.request.OptionRequest;
import com.rdc.weflow_server.entity.checklist.ChecklistOption;
import com.rdc.weflow_server.entity.checklist.ChecklistQuestion;
import com.rdc.weflow_server.entity.log.ActionType;
import com.rdc.weflow_server.entity.log.TargetTable;
import com.rdc.weflow_server.exception.BusinessException;
import com.rdc.weflow_server.exception.ErrorCode;
import com.rdc.weflow_server.repository.checklist.ChecklistOptionRepository;
import com.rdc.weflow_server.repository.checklist.ChecklistQuestionRepository;
import com.rdc.weflow_server.service.log.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChecklistOptionService {
    private final ChecklistOptionRepository optionRepository;
    private final ChecklistQuestionRepository questionRepository;
    private final ActivityLogService activityLogService;

    // 옵션 생성
    public Long createOption(OptionRequest request, Long userId, String ip) {

        ChecklistQuestion question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_QUESTION_NOT_FOUND));

        if (request.getOrderIndex() == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "option orderIndex is required");
        }

        ChecklistOption option = ChecklistOption.builder()
                .question(question)
                .optionText(request.getOptionText())
                .hasInput(request.getHasInput())
                .orderIndex(request.getOrderIndex())
                .build();

        optionRepository.save(option);
        activityLogService.createLog(
                ActionType.CREATE,
                TargetTable.CHECKLIST_OPTION,
                option.getId(),
                userId,
                question.getChecklist().getStep().getProject().getId(),
                ip
        );
        return option.getId();
    }

    // 옵션 수정
    public Long updateOption(Long optionId, OptionRequest request, Long userId, String ip) {

        ChecklistOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_OPTION_NOT_FOUND));

        Long projectId = option.getQuestion().getChecklist().getStep().getProject().getId();

        // 업데이트 가능한 필드만 부분 변경
        option.updateOption(request.getOptionText(), request.getHasInput(), null);

        activityLogService.createLog(
                ActionType.UPDATE,
                TargetTable.CHECKLIST_OPTION,
                option.getId(),
                userId,
                projectId,
                ip
        );

        return option.getId();
    }

    // 옵션 삭제
    public void deleteOption(Long optionId, Long userId, String ip) {
        ChecklistOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_OPTION_NOT_FOUND));

        Long projectId = option.getQuestion().getChecklist().getStep().getProject().getId();
        optionRepository.delete(option);
        activityLogService.createLog(
                ActionType.DELETE,
                TargetTable.CHECKLIST_OPTION,
                optionId,
                userId,
                projectId,
                ip
        );
    }

    // 옵션 순서 재정렬
    @Transactional
    public void reorderOptions(OptionReorderRequest request) {

        questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_QUESTION_NOT_FOUND));

        List<Long> orderedIds = request.getOrderedIds();

        for (int i = 0; i < orderedIds.size(); i++) {
            Long optionId = orderedIds.get(i);

            ChecklistOption option = optionRepository.findById(optionId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_OPTION_NOT_FOUND));

            if (!option.getQuestion().getId().equals(request.getQuestionId())) {
                throw new BusinessException(ErrorCode.CHECKLIST_INVALID_OPTION_SEQUENCE);
            }

            option.updateOrderIndex(i + 1);
        }
    }
}
