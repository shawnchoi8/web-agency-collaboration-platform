package com.rdc.weflow_server.repository.checklist;

import com.rdc.weflow_server.entity.checklist.ChecklistAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistAnswerRepository extends JpaRepository<ChecklistAnswer, Long> {
    
}
