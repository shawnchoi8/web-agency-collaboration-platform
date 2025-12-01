package com.rdc.weflow_server.service;

import com.rdc.weflow_server.dto.request.CreateUserRequest;
import com.rdc.weflow_server.entity.company.Company;
import com.rdc.weflow_server.entity.user.User;
import com.rdc.weflow_server.entity.user.UserStatus;
import com.rdc.weflow_server.repository.CompanyRepository;
import com.rdc.weflow_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    /**
     * 관리자 - 회원 생성
     */
    @Transactional
    public User createUser(CreateUserRequest request) {
        // 1. 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + request.getEmail());
        }

        // 2. 회사 조회
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회사입니다: " + request.getCompanyId()));

        // 3. User 엔티티 생성
        // TODO: 비밀번호 암호화 필요 (Spring Security PasswordEncoder)
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())  // 나중에 암호화 추가 필요
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .status(UserStatus.INVITED)  // 초기 상태
                .isTemporaryPassword(true)   // 최초 로그인 시 비밀번호 변경 필요
                .company(company)
                .build();

        // 4. 저장
        return userRepository.save(user);
    }
}