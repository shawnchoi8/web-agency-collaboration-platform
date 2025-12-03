package com.rdc.weflow_server.aop.log;

import com.rdc.weflow_server.config.security.CustomUserDetails;
import com.rdc.weflow_server.service.log.ActivityLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ActionLogAspect {

    private final ActivityLogService activityLogService;
    private final HttpServletRequest request;

    @AfterReturning(
            value = "@annotation(actionLog)",
            returning = "result"
    )
    public void afterSuccess(
            JoinPoint joinPoint,
            ActionLog actionLog,
            Object result
    ) {
        try {
            // 1. 로그인 사용자 정보 가져오기
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails user)) return;

            Long userId = user.getId();

            // 2. 리턴 객체에서 targetId 추출
            Long targetId = extractId(result, actionLog.targetIdField());
            Long projectId = extractId(result, actionLog.projectIdField());

            // 3. IP 자동 추출
            String ip = actionLog.logIp() ? request.getRemoteAddr() : null;

            // 4. 로그 서비스 호출
            activityLogService.createLog(
                    actionLog.actionType(),
                    actionLog.targetTable(),
                    targetId,
                    userId,
                    projectId,
                    ip
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 리플렉션 기반 안전한 값 추출
    private Long extractId(Object result, String fieldName) {
        if (result == null) return null;

        // Case 1: Long 반환 → 그대로 사용
        if (result instanceof Long id) return id;

        // Case 2: DTO/Entity에서 field 꺼내기
        try {
            BeanWrapperImpl wrapper = new BeanWrapperImpl(result);
            Object value = wrapper.getPropertyValue(fieldName);
            if (value instanceof Number num) {
                return num.longValue();
            }
        } catch (Exception ignored) {}

        return null;
    }
}
