package com.rdc.weflow_server.service.checklist;

import com.rdc.weflow_server.dto.checklist.OptionRequest;
import com.rdc.weflow_server.entity.checklist.ChecklistOption;
import com.rdc.weflow_server.entity.checklist.ChecklistQuestion;
import com.rdc.weflow_server.repository.checklist.ChecklistOptionRepository;
import com.rdc.weflow_server.repository.checklist.ChecklistQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChecklistOptionService {
    private final ChecklistOptionRepository optionRepository;
    private final ChecklistQuestionRepository questionRepository;

    // 옵션 생성
    public Long createOption(OptionRequest request) {

        ChecklistQuestion question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("QUESTION_NOT_FOUND"));

        ChecklistOption option = ChecklistOption.builder()
                .question(question)
                .optionText(request.getOptionText())
                .hasInput(request.getHasInput())
                .orderIndex(request.getOrderIndex())
                .build();

        optionRepository.save(option);
        return option.getId();
    }

    // 옵션 수정
    public Long updateOption(Long optionId, OptionRequest request) {

        ChecklistOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("OPTION_NOT_FOUND"));

        // 업데이트 가능한 필드만 부분 변경
        option.updateOption(request.getOptionText(), request.getHasInput(), request.getOrderIndex());

        return option.getId();
    }

    // 옵션 삭제
    public void deleteOption(Long optionId) {
        ChecklistOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("OPTION_NOT_FOUND"));

        optionRepository.delete(option);
    }
}
