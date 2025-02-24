package attendance.dto;

import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;

import java.time.LocalDateTime;
import java.util.List;

public record CheckCrewAttendanceRecordsDto(List<LocalDateTime> attendanceDateTimes,
                                            List<AttendanceStatus> attendanceStatuses) {}
