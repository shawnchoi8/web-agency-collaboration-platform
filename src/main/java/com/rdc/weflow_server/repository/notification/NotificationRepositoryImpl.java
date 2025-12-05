package com.rdc.weflow_server.repository.notification;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rdc.weflow_server.dto.notification.request.NotificationSearchCondition;
import com.rdc.weflow_server.entity.notification.Notification;
import com.rdc.weflow_server.entity.notification.NotificationPriority;
import com.rdc.weflow_server.entity.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.rdc.weflow_server.entity.notification.QNotification.notification;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Notification> searchNotifications(
            Long userId,
            NotificationSearchCondition condition,
            Pageable pageable
    ) {
        // 1. 컨텐츠 조회 쿼리
        List<Notification> content = queryFactory
                .selectFrom(notification)
                .where(
                        notification.user.id.eq(userId),      // 본인 알림만
                        notification.deletedAt.isNull(),      // 삭제되지 않은 것만
                        typeEq(condition.getType()),          // 타입 필터 (선택)
                        isReadEq(condition.getIsRead()),      // 읽음 여부 필터 (선택)
                        priorityEq(condition.getPriority())   // 중요도 필터 (선택)
                )
                .orderBy(notification.createdAt.desc())       // 최신순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 2. 카운트 쿼리 (페이징용)
        JPAQuery<Long> countQuery = queryFactory
                .select(notification.count())
                .from(notification)
                .where(
                        notification.user.id.eq(userId),
                        notification.deletedAt.isNull(),
                        typeEq(condition.getType()),
                        isReadEq(condition.getIsRead()),
                        priorityEq(condition.getPriority())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    // --- 동적 쿼리 조건 메서드들 ---

    // 알림 종류 필터
    private BooleanExpression typeEq(NotificationType type) {
        return type != null ? notification.type.eq(type) : null;
    }

    // 읽음 여부 필터
    private BooleanExpression isReadEq(Boolean isRead) {
        return isRead != null ? notification.isRead.eq(isRead) : null;
    }

    // 중요도 필터
    private BooleanExpression priorityEq(NotificationPriority priority) {
        return priority != null ? notification.priority.eq(priority) : null;
    }
}