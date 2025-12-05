package com.rdc.weflow_server.dto.notification.request;

import com.rdc.weflow_server.entity.notification.NotificationPriority;
import com.rdc.weflow_server.entity.notification.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSearchCondition {

    private NotificationType type;          // 알림 종류 필터
    private Boolean isRead;                 // 읽음 여부 필터
    private NotificationPriority priority;  // 중요도 필터
}