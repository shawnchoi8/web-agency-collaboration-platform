package com.rdc.weflow_server.aop.log;

import com.rdc.weflow_server.entity.log.ActionType;
import com.rdc.weflow_server.entity.log.TargetTable;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLog {

    // 어떤 행동인지? CREATE / UPDATE / DELETE …
    ActionType actionType();

    // 어떤 테이블에 대한 로그인지? POST / COMMENT / FILE …
    TargetTable targetTable();

    // 반환 객체에서 targetId를 찾을 속성명
    // 기본값: "id"
    String targetIdField() default "id";

    // 프로젝트 ID를 어디서 추출할지 (선택)
    String projectIdField() default "projectId";

    // IP 주소 로깅 여부
    boolean logIp() default true;
}

