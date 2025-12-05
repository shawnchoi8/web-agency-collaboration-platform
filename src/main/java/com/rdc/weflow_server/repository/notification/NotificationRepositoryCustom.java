package com.rdc.weflow_server.repository.notification;

import com.rdc.weflow_server.dto.notification.request.NotificationSearchCondition;
import com.rdc.weflow_server.entity.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationRepositoryCustom {
    Page<Notification> searchNotifications(Long userId, NotificationSearchCondition condition, Pageable pageable);
}