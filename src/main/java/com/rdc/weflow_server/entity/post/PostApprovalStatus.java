package com.rdc.weflow_server.entity.post;

public enum PostApprovalStatus {
    NORMAL, // 일반 게시글 (질문 없음)
    WAITING_ANSWER, // 답변 대기 중
    ANSWERED // 답변 완료
}
