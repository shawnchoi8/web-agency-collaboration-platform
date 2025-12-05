package com.rdc.weflow_server.service.notification;

import com.rdc.weflow_server.dto.notification.request.NotificationSearchCondition;
import com.rdc.weflow_server.dto.notification.response.NotificationResponse;
import com.rdc.weflow_server.entity.notification.Notification;
import com.rdc.weflow_server.entity.notification.NotificationPriority;
import com.rdc.weflow_server.entity.notification.NotificationType;
import com.rdc.weflow_server.entity.post.Post;
import com.rdc.weflow_server.entity.project.Project;
import com.rdc.weflow_server.entity.step.StepRequest;
import com.rdc.weflow_server.entity.user.User;
import com.rdc.weflow_server.exception.BusinessException;
import com.rdc.weflow_server.exception.ErrorCode;
import com.rdc.weflow_server.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // ========== 조회 기능 ==========

    /**
     * 1. 알림 목록 조회
     * GET /api/notifications
     */
    public Page<NotificationResponse> getNotifications(
            Long userId,
            NotificationSearchCondition condition,
            Pageable pageable
    ) {
        Page<Notification> notifications = notificationRepository.searchNotifications(
                userId,
                condition,
                pageable
        );
        return notifications.map(NotificationResponse::from);
    }

    /**
     * 2. 읽지 않은 알림 개수 조회
     * GET /api/notifications/unread-count
     */
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalseAndDeletedAtIsNull(userId);
    }

    /**
     * 3. 알림 상세 조회
     * GET /api/notifications/{notificationId}
     */
    public NotificationResponse getNotification(Long userId, Long notificationId) {
        Notification notification = validateOwner(userId, notificationId);
        return NotificationResponse.from(notification);
    }

    // ========== 상태 변경 기능 ==========

    /**
     * 4. 알림 읽음 처리
     * PATCH /api/notifications/{notificationId}/read
     */
    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        Notification notification = validateOwner(userId, notificationId);
        notification.markAsRead();
    }

    /**
     * 5. 알림 읽지 않음 처리
     * PATCH /api/notifications/{notificationId}/unread
     */
    @Transactional
    public void markAsUnread(Long userId, Long notificationId) {
        Notification notification = validateOwner(userId, notificationId);
        notification.markAsUnread();
    }

    /**
     * 6. 알림 전체 읽음 처리
     * PATCH /api/notifications/read-all
     */
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository
                .findByUserIdAndIsReadFalseAndDeletedAtIsNull(userId);

        notifications.forEach(Notification::markAsRead);
    }

    /**
     * 7. 알림 삭제 (단건)
     * DELETE /api/notifications/{notificationId}
     */
    @Transactional
    public void deleteNotification(Long userId, Long notificationId) {
        Notification notification = validateOwner(userId, notificationId);
        notification.softDelete();
    }

    /**
     * 8. 알림 전체 삭제
     * DELETE /api/notifications/delete-all
     */
    @Transactional
    public void deleteAllNotifications(Long userId) {
        List<Notification> notifications = notificationRepository
                .findByUserIdAndDeletedAtIsNull(userId);

        notifications.forEach(Notification::softDelete);
    }

    // ========== 알림 생성 (내부 호출용) ==========

    /**
     * 9. 알림 생성 및 발송
     * - 다른 서비스(PostService, StepRequestService 등)에서 호출
     * - 알림 타입에 따라 우선순위 자동 결정
     * - Entity를 직접 받아 DB 재조회 없이 처리 (성능 최적화 및 순환 참조 방지)
     */
    @Transactional
    public void send(
            User receiver,
            NotificationType type,
            String title,
            String message,
            Project project,
            Post post,
            StepRequest stepRequest
    ) {
        // 1. 우선순위 자동 결정
        NotificationPriority priority = determinePriority(type);

        // 2. 알림 엔티티 생성
        Notification notification = Notification.builder()
                .user(receiver)
                .type(type)
                .priority(priority)
                .title(title)
                .message(message)
                .project(project)       // null 가능
                .post(post)             // null 가능
                .stepRequest(stepRequest) // null 가능
                .isRead(false)
                .build();

        // 3. 저장
        notificationRepository.save(notification);
    }

    // ========== 내부 메서드 ==========

    /**
     * 알림 소유자 검증
     */
    private Notification validateOwner(Long userId, Long notificationId) {
        // 1. 알림 조회
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND));

        // 2. 삭제된 알림 체크
        if (notification.getDeletedAt() != null) {
            throw new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND);
        }

        // 3. 권한 체크
        if (!notification.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        return notification;
    }

    /**
     * 알림 타입에 따른 우선순위 자동 결정
     */
    private NotificationPriority determinePriority(NotificationType type) {
        return switch (type) {
            case STEP_REQUEST, STEP_DECISION, MENTION -> NotificationPriority.IMPORTANT;
            default -> NotificationPriority.NORMAL;
        };
    }
}