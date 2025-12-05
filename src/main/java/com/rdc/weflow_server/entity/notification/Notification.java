package com.rdc.weflow_server.entity.notification;

import com.rdc.weflow_server.entity.BaseEntity;
import com.rdc.weflow_server.entity.post.Post;
import com.rdc.weflow_server.entity.project.Project;
import com.rdc.weflow_server.entity.step.StepRequest;
import com.rdc.weflow_server.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 알림 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;  // 알림 종류

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private NotificationPriority priority = NotificationPriority.NORMAL;  // 알림 중요도

    @Column(nullable = false, length = 255)
    private String title;   // 알림 제목

    @Column(columnDefinition = "TEXT")
    private String message; // 알림 메시지

    @Column(nullable = false, name = "is_read")
    private boolean isRead = false; // 읽음 여부

    @Column(name = "read_at")
    private LocalDateTime readAt;   // 읽은 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 사용자 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_post_id")
    private Post post;  // 관련 게시글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_step_request_id")
    private StepRequest stepRequest;    // 관련 승인요청 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_project_id")
    private Project project;    // 관련 프로젝트 ID

    // 알림 읽음 처리
    public void markAsRead() {
        if (!this.isRead) {
            this.isRead = true;
            this.readAt = LocalDateTime.now();
        }
    }

    // 알림 읽지 않음 처리
    public void markAsUnread() {
        this.isRead = false;
        this.readAt = null;
    }
}
