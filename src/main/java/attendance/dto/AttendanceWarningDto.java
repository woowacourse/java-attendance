package attendance.dto;

import attendance.model.AttendanceBook;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import attendance.model.Nickname;

import java.time.LocalDate;
import java.util.EnumMap;

public record AttendanceWarningDto(
        Nickname nickname,
        int lateCount,
        int absentCount,
        AttendanceWarningLevel attendanceLevel
) {
    public static AttendanceWarningDto from(LocalDate baseDate, AttendanceBook attendanceBook, Nickname nickname) {
        EnumMap<AttendanceType, Integer> typeCounts = attendanceBook.countAttendanceTypes(nickname, baseDate);
        AttendanceWarningLevel warningLevel = attendanceBook.determineWarningLevel(typeCounts);
        return new AttendanceWarningDto(
                nickname,
                typeCounts.getOrDefault(AttendanceType.LATE, 0),
                typeCounts.getOrDefault(AttendanceType.ABSENT, 0),
                warningLevel
        );
    }

    public String koreanLabel() {
        return attendanceLevel.getKoreanLabel();
    }
}
