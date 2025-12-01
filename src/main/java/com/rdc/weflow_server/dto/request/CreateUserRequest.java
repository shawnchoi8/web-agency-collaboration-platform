package com.rdc.weflow_server.dto.request;

import com.rdc.weflow_server.entity.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

    private String phoneNumber;  // 선택사항

    @NotNull(message = "역할은 필수입니다")
    private UserRole role;  // SYSTEM_ADMIN, AGENCY, CLIENT

    @NotNull(message = "회사 ID는 필수입니다")
    private Long companyId;
}