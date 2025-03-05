package attendance.dto;

import attendance.domain.AcademicStatus;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import java.util.EnumMap;
import java.util.List;

public record CrewAttendanceDTO(String name,
                                List<AttendanceTime> attendances,
                                EnumMap<AttendanceStatus, Integer> statusCount,
                                AcademicStatus academicStatus) {
}
