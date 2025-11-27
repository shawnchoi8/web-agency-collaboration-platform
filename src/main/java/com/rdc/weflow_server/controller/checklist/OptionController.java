package com.rdc.weflow_server.controller.checklist;

import com.rdc.weflow_server.common.api.ApiResponse;
import com.rdc.weflow_server.dto.checklist.OptionRequest;
import com.rdc.weflow_server.dto.checklist.OptionResponse;
import com.rdc.weflow_server.service.checklist.ChecklistOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
public class OptionController {
    private final ChecklistOptionService optionService;

    // 옵션 생성
    @PostMapping
    public ApiResponse<OptionResponse> createOption(
            @RequestBody OptionRequest request
    ) {
        Long optionId = optionService.createOption(request);

        return ApiResponse.success(
                "OPTION_CREATED",
                OptionResponse.builder()
                        .optionId(optionId)
                        .optionText(request.getOptionText())
                        .hasInput(request.getHasInput())
                        .orderIndex(request.getOrderIndex())
                        .build()
        );
    }

    // 옵션 수정
    @PatchMapping("/{optionId}")
    public ApiResponse<OptionResponse> updateOption(
            @PathVariable Long optionId,
            @RequestBody OptionRequest request
    ) {
        Long id = optionService.updateOption(optionId, request);

        return ApiResponse.success(
                "OPTION_UPDATED",
                OptionResponse.builder()
                        .optionId(id)
                        .optionText(request.getOptionText())
                        .hasInput(request.getHasInput())
                        .orderIndex(request.getOrderIndex())
                        .build()
        );
    }

    // 옵션 삭제
    @DeleteMapping("/{optionId}")
    public ApiResponse<Long> deleteOption(@PathVariable Long optionId) {

        optionService.deleteOption(optionId);

        return ApiResponse.success(
                "OPTION_DELETED",
                optionId
        );
    }
}
