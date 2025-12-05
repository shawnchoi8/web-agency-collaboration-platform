package com.rdc.weflow_server.controller.notification;

import com.rdc.weflow_server.common.api.ApiResponse;
import com.rdc.weflow_server.config.security.CustomUserDetails;
import com.rdc.weflow_server.dto.notification.request.NotificationSearchCondition;
import com.rdc.weflow_server.dto.notification.response.NotificationResponse;
import com.rdc.weflow_server.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 1. 알림 목록 조회
     * GET /api/notifications
     */
    @GetMapping
    public ApiResponse<Page<NotificationResponse>> getNotifications(
            NotificationSearchCondition condition,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Page<NotificationResponse> notifications = notificationService.getNotifications(
                userDetails.getId(),
                condition,
                pageable
        );
        return ApiResponse.success("알림 목록 조회 성공", notifications);
    }

    /**
     * 2. 안 읽은 알림 개수 조회
     * GET /api/notifications/unread-count
     */
    @GetMapping("/unread-count")
    public ApiResponse<Long> getUnreadCount(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        long count = notificationService.getUnreadCount(userDetails.getId());
        return ApiResponse.success("읽지 않은 알림 개수 조회 성공", count);
    }

    /**
     * 3. 알림 상세 조회
     * GET /api/notifications/{notificationId}
     */
    @GetMapping("/{notificationId}")
    public ApiResponse<NotificationResponse> getNotification(
            @PathVariable Long notificationId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        NotificationResponse response = notificationService.getNotification(
                userDetails.getId(),
                notificationId
        );
        return ApiResponse.success("알림 조회 성공", response);
    }

    /**
     * 4. 알림 읽음 처리
     * PATCH /api/notifications/{notificationId}/read
     */
    @PatchMapping("/{notificationId}/read")
    public ApiResponse<Void> markAsRead(
            @PathVariable Long notificationId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        notificationService.markAsRead(userDetails.getId(), notificationId);
        return ApiResponse.success("알림을 읽음 처리했습니다", null);
    }

    /**
     * 5. 알림 읽지 않음 처리
     * PATCH /api/notifications/{notificationId}/unread
     */
    @PatchMapping("/{notificationId}/unread")
    public ApiResponse<Void> markAsUnread(
            @PathVariable Long notificationId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        notificationService.markAsUnread(userDetails.getId(), notificationId);
        return ApiResponse.success("알림을 읽지 않음 처리했습니다", null);
    }

    /**
     * 6. 알림 전체 읽음 처리
     * PATCH /api/notifications/read-all
     */
    @PatchMapping("/read-all")
    public ApiResponse<Void> markAllAsRead(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        notificationService.markAllAsRead(userDetails.getId());
        return ApiResponse.success("모든 알림을 읽음 처리했습니다", null);
    }

    /**
     * 7. 알림 삭제
     * DELETE /api/notifications/{notificationId}
     */
    @DeleteMapping("/{notificationId}")
    public ApiResponse<Void> deleteNotification(
            @PathVariable Long notificationId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        notificationService.deleteNotification(userDetails.getId(), notificationId);
        return ApiResponse.success("알림을 삭제했습니다", null);
    }

    /**
     * 8. 알림 전체 삭제
     * DELETE /api/notifications/delete-all
     */
    @DeleteMapping("/delete-all")
    public ApiResponse<Void> deleteAllNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        notificationService.deleteAllNotifications(userDetails.getId());
        return ApiResponse.success("모든 알림을 삭제했습니다", null);
    }
}