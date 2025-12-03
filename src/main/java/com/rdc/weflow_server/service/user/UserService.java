package com.rdc.weflow_server.service.user;

import com.rdc.weflow_server.dto.user.request.ChangePasswordRequest;
import com.rdc.weflow_server.dto.user.request.CreateUserRequest;
import com.rdc.weflow_server.dto.user.request.UpdateUserRequest;
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
     * POST /api/admin/users
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
     * GET /api/users/me
     */
    public UserResponse getMyInfo(Long userId) {
        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 삭제된 사용자 체크
        if (user.getDeletedAt() != null) {
            throw new BusinessException(ErrorCode.USER_ALREADY_DELETED);
        }

        // 3. Entity → Response 변환
        return UserResponse.from(user);
    }

    /**
     * 내 정보 수정
     * PATCH /api/users/me
     */
    @Transactional
    public UserResponse updateMyInfo(Long userId, UpdateUserRequest request) {
        // 1. 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 전화번호 중복 체크
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals(user.getPhoneNumber())) {
            if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
                throw new BusinessException(ErrorCode.USER_PHONE_DUPLICATE);
            }
        }

        // 3. 정보 수정
        user.updateMyInfo(request.getName(), request.getPhoneNumber());

        // 4. Entity → Response 변환
        return UserResponse.from(user);
    }

    /**
     * 비밀번호 변경
     * PATCH /api/users/me/password
     */
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 새 비밀번호 일치 체크 (항상 필수)
        if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // 3. 현재 비밀번호 검증 (임시 비밀번호 여부에 따라 분기)
        if (user.getIsTemporaryPassword()) {
            // [첫 로그인] 임시 비밀번호 사용자
            // → 현재 비밀번호 입력란 없음, 검증 생략
        } else {
            // [일반 사용자] 현재 비밀번호 검증 필수
            if (request.getCurrentPassword() == null || request.getCurrentPassword().isBlank()) {
                throw new BusinessException(ErrorCode.INVALID_PASSWORD);
            }
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new BusinessException(ErrorCode.INVALID_PASSWORD);
            }
        }

        // 4. 비밀번호 변경 (암호화 + 임시 비밀번호 해제)
        user.changePassword(passwordEncoder.encode(request.getNewPassword()));
    }
}