package com.rdc.weflow_server.service.notification;

import com.rdc.weflow_server.dto.notification.request.NotificationSearchCondition;
import com.rdc.weflow_server.dto.notification.response.NotificationResponse;
import com.rdc.weflow_server.entity.notification.Notification;
import com.rdc.weflow_server.exception.BusinessException;
import com.rdc.weflow_server.exception.ErrorCode;
import com.rdc.weflow_server.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

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
     * 3. 알림 읽음 처리
     * PATCH /api/notifications/{notificationId}/read
     */
    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        Notification notification = validateOwner(userId, notificationId);
        notification.markAsRead();
    }

    /**
     * 4. 알림 읽지 않음 처리
     * PATCH /api/notifications/{notificationId}/unread
     */
    @Transactional
    public void markAsUnread(Long userId, Long notificationId) {
        Notification notification = validateOwner(userId, notificationId);
        notification.markAsUnread();
    }

    /**
     * 5. 알림 전체 읽음 처리
     * PATCH /api/notifications/read-all
     */
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository
                .findByUserIdAndIsReadFalseAndDeletedAtIsNull(userId);

        notifications.forEach(Notification::markAsRead);
    }

    /**
     * 6. 알림 삭제 (단건)
     * DELETE /api/notifications/{notificationId}
     */
    @Transactional
    public void deleteNotification(Long userId, Long notificationId) {
        Notification notification = validateOwner(userId, notificationId);
        notification.softDelete();
    }

    /**
     * 7. 알림 전체 삭제
     * DELETE /api/notifications/delete-all
     */
    @Transactional
    public void deleteAllNotifications(Long userId) {
        List<Notification> notifications = notificationRepository
                .findByUserIdAndDeletedAtIsNull(userId);

        notifications.forEach(Notification::softDelete);
    }

    /**
     * 8. 알림 상세 조회
     * GET /api/notifications/{notificationId}
     */
    public NotificationResponse getNotification(Long userId, Long notificationId) {
        Notification notification = validateOwner(userId, notificationId);
        return NotificationResponse.from(notification);
    }

    // === 내부 검증 메서드 ===

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
}