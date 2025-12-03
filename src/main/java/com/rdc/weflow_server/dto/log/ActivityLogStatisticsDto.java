package com.rdc.weflow_server.dto.log;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityLogStatisticsDto {
    private long total;
    private long createCount;
    private long updateCount;
    private long deleteCount;

    private long loginCount;
    private long logoutCount;

    private long approveCount;
    private long rejectCount;

    private long last7Days;
}
