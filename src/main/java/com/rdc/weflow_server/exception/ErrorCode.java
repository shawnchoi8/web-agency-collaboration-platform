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

    // Checklist
    CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST_001", "체크리스트를 찾을 수 없습니다."),
    CHECKLIST_LOCKED(HttpStatus.BAD_REQUEST, "CHECKLIST_002", "잠금 처리된 체크리스트는 수정할 수 없습니다."),

    // Checklist Question
    CHECKLIST_QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST_QUESTION_001", "체크리스트 질문을 찾을 수 없습니다."),
    CHECKLIST_QUESTION_NOT_IN_CHECKLIST(HttpStatus.BAD_REQUEST, "CHECKLIST_QUESTION_002", "체크리스트에 속하지 않은 질문입니다."),

    // Checklist Option
    CHECKLIST_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST_OPTION_001", "체크리스트 옵션을 찾을 수 없습니다."),
    CHECKLIST_OPTION_NOT_IN_QUESTION(HttpStatus.BAD_REQUEST, "CHECKLIST_OPTION_002", "질문에 속하지 않은 옵션입니다."),
    CHECKLIST_OPTION_TYPE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "CHECKLIST_OPTION_003", "해당 질문 타입에는 옵션을 추가할 수 없습니다."),
    CHECKLIST_INVALID_OPTION_SEQUENCE(HttpStatus.BAD_REQUEST, "CHECKLIST_OPTION_004", "옵션 순서가 올바르지 않습니다."),

    // Checklist Answer
    REQUIRED_ANSWER_INPUT(HttpStatus.BAD_REQUEST, "CHECKLIST_ANSWER_001", "해당 선택지는 추가 입력이 필요합니다."),
    INVALID_ANSWER_INPUT(HttpStatus.BAD_REQUEST, "CHECKLIST_ANSWER_002", "해당 선택지에는 추가 입력을 허용하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
