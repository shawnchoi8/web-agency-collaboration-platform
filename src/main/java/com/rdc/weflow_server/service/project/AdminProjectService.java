package com.rdc.weflow_server.service.project;

import com.rdc.weflow_server.config.security.CustomUserDetails;
import com.rdc.weflow_server.dto.project.*;
import com.rdc.weflow_server.entity.company.Company;
import com.rdc.weflow_server.entity.project.Project;
import com.rdc.weflow_server.entity.project.ProjectStatus;
import com.rdc.weflow_server.entity.user.UserRole;
import com.rdc.weflow_server.exception.BusinessException;
import com.rdc.weflow_server.exception.ErrorCode;
import com.rdc.weflow_server.repository.project.CompanyRepository;
import com.rdc.weflow_server.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;

    // 프로젝트 생성
    public AdminProjectCreateResponseDto createProject(
            AdminProjectCreateRequestDto request,
            CustomUserDetails user
    ) {
        // 관리자 체크
        if (user.getRole() != UserRole.SYSTEM_ADMIN) {
            throw new BusinessException(ErrorCode.PROJECT_CREATE_FORBIDDEN);
        }

        // 회사 조회
        Company company = companyRepository.findById(request.getCustomerCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_COMPANY_NOT_FOUND));

        // 프로젝트 등록한 시스템 관리자 조회
        Long creatorId = user.getUserId();

        Project project = request.toEntity(company, creatorId);
        projectRepository.save(project);

        return AdminProjectCreateResponseDto.from(project);
    }

    // 프로젝트 목록 조회
    public AdminProjectListResponseDto getProjectList(
            ProjectStatus status,
            Long companyId,
            String keyword,
            int page,
            int size
    ) {
        List<Project> projects = projectRepository.searchAdminProjects(
                status, companyId, keyword, page, size
        );

        long total = projectRepository.countAdminProjects(status, companyId, keyword);

        return AdminProjectListResponseDto.of(projects, total, page, size);
    }

    // 프로젝트 상세 조회
    public AdminProjectDetailResponseDto getProjectDetail(Long projectId) {

        Project project = projectRepository.findByIdWithMembers(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        return AdminProjectDetailResponseDto.from(project);
    }

    // 프로젝트 수정
    public AdminProjectUpdateResponseDto updateProject(
            Long projectId,
            AdminProjectUpdateRequestDto request,
            CustomUserDetails user
    ) {
        // 관리자 체크
        if (user.getRole() != UserRole.SYSTEM_ADMIN) {
            throw new BusinessException(ErrorCode.PROJECT_UPDATE_FORBIDDEN);
        }

        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        // 회사 변경 필요할 경우
        Company company = null;
        if (request.getCustomerCompanyId() != null) {
            company = companyRepository.findById(request.getCustomerCompanyId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_COMPANY_NOT_FOUND));
        }

        // 업데이트
        project.updateProject(
                request.getName(),
                request.getDescription(),
                request.getStatus(),
                request.getStartDate(),
                request.getEndDateExpected(),
                request.getEndDate(),
                request.getContractAmount() != null ? BigDecimal.valueOf(request.getContractAmount()) : null,
                request.getContractFileUrl(),
                company
        );

        projectRepository.save(project);

        return new AdminProjectUpdateResponseDto(
                project.getId(),
                project.getUpdatedAt().toString()
        );
    }

    // 프로젝트 삭제
    public void deleteProject(Long projectId, CustomUserDetails user) {

        // 관리자만 삭제 가능
        if (user.getRole() != UserRole.SYSTEM_ADMIN) {
            throw new BusinessException(ErrorCode.PROJECT_DELETE_FORBIDDEN);
        }

        Project project = projectRepository.findByIdWithMembersFiltered(projectId, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        // Soft Delete
        project.softDelete();

        projectRepository.save(project);
    }
}
