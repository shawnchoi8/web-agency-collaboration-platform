package com.rdc.weflow_server.controller.company;

import com.rdc.weflow_server.common.api.ApiResponse;
import com.rdc.weflow_server.config.security.CustomUserDetails;
import com.rdc.weflow_server.dto.company.response.CompanyResponse;
import com.rdc.weflow_server.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * 내 회사 정보 조회
     * GET /api/companies/me
     */
    @GetMapping("/me")
    public ApiResponse<CompanyResponse> getMyCompany(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CompanyResponse response = companyService.getMyCompany(userDetails.getCompanyId());

        return ApiResponse.success("내 회사 정보 조회 성공", response);
    }
}