package com.rdc.weflow_server.dto.notification.response;

import com.rdc.weflow_server.entity.notification.Notification;
import com.rdc.weflow_server.entity.notification.NotificationPriority;
import com.rdc.weflow_server.entity.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;                        // 알림 ID
    private NotificationType type;          // 알림 종류
    private NotificationPriority priority;  // 중요도
    private String title;                   // 제목
    private String message;                 // 메시지
    private boolean isRead;                 // 읽음 여부
    private LocalDateTime readAt;           // 읽은 시간
    private Long relatedPostId;             // 관련 게시글 ID
    private Long relatedStepRequestId;      // 관련 승인요청 ID
    private Long relatedProjectId;          // 관련 프로젝트 ID
    private LocalDateTime createdAt;        // 생성 시간

    // Entity → DTO 변환
    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .priority(notification.getPriority())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .isRead(notification.isRead())
                .readAt(notification.getReadAt())
                .relatedPostId(notification.getPost() != null ? notification.getPost().getId() : null)
                .relatedStepRequestId(notification.getStepRequest() != null ? notification.getStepRequest().getId() : null)
                .relatedProjectId(notification.getProject() != null ? notification.getProject().getId() : null)
                .createdAt(notification.getCreatedAt())
                .build();
    }
}