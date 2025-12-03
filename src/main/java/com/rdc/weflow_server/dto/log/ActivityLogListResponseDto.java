package com.rdc.weflow_server.dto.log;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ActivityLogListResponseDto {
    private List<ActivityLogResponseDto> logs;
    private long totalCount;
}