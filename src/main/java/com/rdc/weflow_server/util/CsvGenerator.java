package com.rdc.weflow_server.util;

import com.rdc.weflow_server.dto.log.ActivityLogResponseDto;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvGenerator {

    /**
     * ActivityLog 리스트를 CSV 바이트 배열로 변환
     */
    public byte[] generateCsv(List<ActivityLogResponseDto> logs) {

        StringBuilder sb = new StringBuilder();

        // CSV 헤더
        sb.append("logId,actionType,targetTable,targetId,userName,projectName,ipAddress,createdAt\n");

        // CSV 내용
        for (ActivityLogResponseDto log : logs) {
            sb.append(safe(log.getLogId())).append(",");
            sb.append(safe(log.getActionType())).append(",");
            sb.append(safe(log.getTargetTable())).append(",");
            sb.append(safe(log.getTargetId())).append(",");
            sb.append(safe(log.getUserName())).append(",");
            sb.append(safe(log.getProjectName())).append(",");
            sb.append(safe(log.getIpAddress())).append(",");
            sb.append(safe(log.getCreatedAt())).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * CSV에서 null 방지 및 쉼표/줄바꿈 처리용
     */
    private String safe(Object value) {
        if (value == null) return "";
        // 필요 시 따옴표 감싸기 처리도 가능
        return value.toString().replace(",", " ");
    }
}

