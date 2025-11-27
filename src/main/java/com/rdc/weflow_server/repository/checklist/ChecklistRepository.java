package com.rdc.weflow_server.repository.checklist;

import com.rdc.weflow_server.entity.checklist.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findByIsTemplateTrue();// 템플릿 목록
    Optional<Checklist> findByIdAndIsTemplateTrue(Long templateId); // 템플릿 조회
}
