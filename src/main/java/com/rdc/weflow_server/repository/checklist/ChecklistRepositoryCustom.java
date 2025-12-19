package com.rdc.weflow_server.repository.checklist;

import com.rdc.weflow_server.entity.checklist.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChecklistRepositoryCustom {
    // 동적 쿼리 메서드 시그니처
    Page<Checklist> findTemplatesByDynamicCriteria(
            Pageable pageable,
            String keyword,
            String category
    );
}