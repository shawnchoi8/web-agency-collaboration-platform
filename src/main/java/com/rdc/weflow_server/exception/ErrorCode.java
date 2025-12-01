package com.rdc.weflow_server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_001", "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_002", "서버 내부 오류가 발생했습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_003", "허용되지 않은 HTTP 메서드입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_004", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_005", "권한이 없습니다."),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_001", "게시글을 찾을 수 없습니다."),
    POST_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "POST_002", "이미 삭제된 게시글입니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_002", "이미 존재하는 사용자입니다."),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_001", "댓글을 찾을 수 없습니다."),

    // Step
    STEP_NOT_FOUND(HttpStatus.NOT_FOUND, "STEP_001", "단계를 찾을 수 없습니다."),

    // Notification
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFICATION_001", "알림을 찾을 수 없습니다."),

    // Project
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT_001", "프로젝트를 찾을 수 없습니다."),
    NO_PROJECT_PERMISSION(HttpStatus.FORBIDDEN, "PROJECT_002", "해당 프로젝트에 접근할 수 없습니다."),
    PROJECT_CREATE_FORBIDDEN(HttpStatus.FORBIDDEN, "PROJECT_003", "프로젝트 생성 권한이 없습니다."),
    PROJECT_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "PROJECT_004", "프로젝트 수정 권한이 없습니다."),
    PROJECT_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "PROJECT_005", "프로젝트 삭제 권한이 없습니다."),
    PROJECT_INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "PROJECT_006", "프로젝트 날짜 범위가 올바르지 않습니다."),
    PROJECT_COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT_007", "프로젝트의 회사 정보를 찾을 수 없습니다."),

    // Project Member
    PROJECT_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT_MEMBER_001", "프로젝트 멤버를 찾을 수 없습니다."),
    PROJECT_MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "PROJECT_MEMBER_002", "이미 프로젝트에 속한 사용자입니다."),
    PROJECT_MEMBER_ADD_FORBIDDEN(HttpStatus.FORBIDDEN, "PROJECT_MEMBER_003", "프로젝트 멤버 추가 권한이 없습니다."),
    PROJECT_MEMBER_REMOVE_FORBIDDEN(HttpStatus.FORBIDDEN, "PROJECT_MEMBER_004", "프로젝트 멤버 삭제 권한이 없습니다."),
    PROJECT_MEMBER_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT_MEMBER_005", "프로젝트에 추가할 사용자를 찾을 수 없습니다."),
    PROJECT_MEMBER_ROLE_INVALID(HttpStatus.BAD_REQUEST, "PROJECT_MEMBER_006", "유효하지 않은 프로젝트 역할입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
