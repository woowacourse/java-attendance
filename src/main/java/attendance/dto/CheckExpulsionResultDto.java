package attendance.dto;

import attendance.domain.ExpulsionStatus;

public record CheckExpulsionResultDto(String nickname, long absentCount, long lateCount, ExpulsionStatus expulsionStatus) {}
