package com.rdc.weflow_server.entity.notification;

public enum NotificationPriority {
    IMPORTANT,  // 승인 요청, 승인 결과, 멘션
    NORMAL      // 새 게시글, 새 댓글, 시스템 공지
}