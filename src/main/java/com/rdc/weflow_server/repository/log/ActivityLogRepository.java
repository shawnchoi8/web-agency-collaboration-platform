package com.rdc.weflow_server.repository.log;

import com.rdc.weflow_server.entity.log.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>, ActivityLogRepositoryCustom {
}
