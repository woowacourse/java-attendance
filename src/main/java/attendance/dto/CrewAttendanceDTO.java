package attendance.dto;

import attendance.model.AcademicStatus;
import attendance.model.AttendanceStatus;
import attendance.model.AttendanceTime;
import java.util.EnumMap;
import java.util.List;

public record CrewAttendanceDTO(String name,
                                List<AttendanceTime> attendances,
                                EnumMap<AttendanceStatus, Integer> statusCount,
                                AcademicStatus academicStatus) {
}
