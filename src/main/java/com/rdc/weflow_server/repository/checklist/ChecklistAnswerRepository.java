package com.rdc.weflow_server.repository.checklist;

import com.rdc.weflow_server.entity.checklist.ChecklistAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistAnswerRepository extends JpaRepository<ChecklistAnswer, Long> {
    List<ChecklistAnswer> findByChecklist_Id(Long checklistId); // 체크리스트별 답변 조회
    Optional<ChecklistAnswer> findByChecklist_IdAndQuestion_Id(Long checklistId, Long questionId); // 특정 체크리스트 + 특정 질문 → 기존 답변 1개 조회 (업데이트용)

}
