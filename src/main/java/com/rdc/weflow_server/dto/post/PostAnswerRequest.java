package com.rdc.weflow_server.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostAnswerRequest {

    private List<Long> selectedOptionIds;  // 선택된 옵션 ID들 (객관식, 복수선택)
    private String textInput;              // 주관식 답변 또는 기타 입력

}
