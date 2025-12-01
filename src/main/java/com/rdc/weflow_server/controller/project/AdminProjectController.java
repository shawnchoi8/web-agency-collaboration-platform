package com.rdc.weflow_server.controller.project;

import com.rdc.weflow_server.common.api.ApiResponse;
import com.rdc.weflow_server.config.security.CustomUserDetails;
import com.rdc.weflow_server.dto.project.*;
import com.rdc.weflow_server.entity.project.ProjectStatus;
import com.rdc.weflow_server.service.project.AdminProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {

    private final AdminProjectService adminProjectService;

    // 프로젝트 생성
    @PostMapping
    public ApiResponse<AdminProjectCreateResponseDto> createProject(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody AdminProjectCreateRequestDto request
    ) {
        return ApiResponse.success(
                "PROJECT_CREATED",
                adminProjectService.createProject(request, user)
        );
    }

    // 프로젝트 목록 조회
    @GetMapping
    public ApiResponse<AdminProjectListResponseDto> getProjects(
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        AdminProjectListResponseDto response = adminProjectService.getProjectList(status, companyId, keyword, page, size);
        return ApiResponse.success("PROJECT_LIST_FETCHED", response);
    }

    // 프로젝트 상세 조회
    @GetMapping("/{projectId}")
    public ApiResponse<AdminProjectDetailResponseDto> getProjectDetail(
            @PathVariable Long projectId
    ) {
        return  ApiResponse.success("PROJECT_DETAIL_FETCHED",
                adminProjectService.getProjectDetail(projectId));
    }

    // 프로젝트 수정
    @PatchMapping("/{projectId}")
    public ApiResponse<AdminProjectUpdateResponseDto> updateProject(
            @PathVariable Long projectId,
            @RequestBody AdminProjectUpdateRequestDto request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.success(
                "PROJECT_UPDATED",
                adminProjectService.updateProject(projectId, request, user)
        );
    }

    // 프로젝트 삭제
    @DeleteMapping("/{projectId}")
    public ApiResponse<Void> deleteProject(
            @PathVariable Long projectId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        adminProjectService.deleteProject(projectId, user);
        return ApiResponse.success("PROJECT_DELETED", null);
    }
}
