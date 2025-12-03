package com.rdc.weflow_server.service.user;

import com.rdc.weflow_server.dto.user.request.CreateUserRequest;
import com.rdc.weflow_server.dto.user.response.UserResponse;
import com.rdc.weflow_server.entity.company.Company;
import com.rdc.weflow_server.entity.user.User;
import com.rdc.weflow_server.exception.BusinessException;
import com.rdc.weflow_server.exception.ErrorCode;
import com.rdc.weflow_server.repository.company.CompanyRepository;
import com.rdc.weflow_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 관리자 - 회원 생성
     */
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        // 1. 회사 존재 확인
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        // 2. 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.USER_EMAIL_DUPLICATE);
        }

        // 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 4. DTO → Entity 변환
        User user = request.toEntity(company, encodedPassword);

        // 5. 저장
        User savedUser = userRepository.save(user);

        // 6. Entity → Response 변환
        return UserResponse.from(savedUser);
    }

    /**
     * 내 정보 조회
     */
    public UserResponse getMyInfo(Long userId) {
        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 삭제된 사용자인지 체크
        if (user.getDeletedAt() != null) {
            throw new BusinessException(ErrorCode.USER_ALREADY_DELETED);
        }

        // 3. DTO 변환 후 반환
        return UserResponse.from(user);
    }
}