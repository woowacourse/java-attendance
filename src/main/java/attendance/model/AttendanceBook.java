package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;

public class AttendanceBook {

    private final AttendanceLogs attendanceLogs;
    private final NicknameRegistry nicknameRegistry;

    public AttendanceBook(AttendanceLogs attendanceLogs, NicknameRegistry nicknameRegistry) {
        this.attendanceLogs = attendanceLogs;
        this.nicknameRegistry = nicknameRegistry;
    }

    public void validateNicknameExists(Nickname nickname) {
        if (!nicknameRegistry.isRegistered(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public void attend(Nickname nickname, LocalDateTime attendanceDateTime) {
        AttendanceLog attendanceLog = new AttendanceLog(nickname, attendanceDateTime);
        attendanceLogs.add(attendanceLog);
    }

    public LocalTime findAttendanceTimeByNicknameAndDate(Nickname nickname, LocalDate localDate) {
        return attendanceLogs.findByNicknameAndAttendanceDate(nickname, localDate)
                .getAttendanceTime();
    }

    public void edit(Nickname nickname, LocalDateTime updateDateTime) {
        AttendanceLog updateAttendanceLog = new AttendanceLog(nickname, updateDateTime);
        attendanceLogs.edit(updateAttendanceLog);
    }

    public AttendanceType determineAttendanceType(LocalDate date, LocalTime time) {
        LocalTime startTimeInBaseDate = EducationSchedule.findStartTimeByDay(date.getDayOfWeek());
        return AttendanceType.determine(startTimeInBaseDate, time);
    }

    public List<AttendanceLog> findAttendanceLogsByNicknameAndInMonth(Nickname nickname, LocalDate baseDate) {
        return attendanceLogs.findAllByNicknameInMonth(nickname, baseDate);
    }

    public EnumMap<AttendanceType, Integer> countAttendanceTypes(Nickname nickname, LocalDate baseDate) {
        return attendanceLogs.countAttendanceTypes(nickname, baseDate);
    }

    public AttendanceWarningLevel determineWarningLevel(EnumMap<AttendanceType, Integer> attendanceTypeCounts) {
        int lateCount = attendanceTypeCounts.get(AttendanceType.LATE);
        int absentCount = attendanceTypeCounts.get(AttendanceType.ABSENT);
        return AttendanceWarningLevel.determine(lateCount, absentCount);
    }

    public Set<Nickname> getNicknames() {
        return nicknameRegistry.getNicknames();
    }
}
