package attendance.model;

import attendance.dto.AttendanceLogDto;
import attendance.dto.AttendanceWarning;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

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

    public List<AttendanceLogDto> findAttendanceLogsByNicknameAndInMonth(Nickname nickname, LocalDate baseDate) {
        return attendanceLogs.findAllByNicknameInMonth(nickname, baseDate)
                .stream()
                .map(attendanceLog -> new AttendanceLogDto(
                        attendanceLog.getNickname(),
                        attendanceLog.getAttendanceDate(),
                        attendanceLog.getAttendanceTime(),
                        determineAttendanceType(attendanceLog.getAttendanceDate(), attendanceLog.getAttendanceTime())))
                .toList();
    }

    public EnumMap<AttendanceType, Integer> countAllAttendanceType(Nickname nickname, LocalDate baseDate) {
        return attendanceLogs.countAttendanceTypes(nickname, baseDate);
    }

    public AttendanceWarningLevel determineWarningLevel(EnumMap<AttendanceType, Integer> attendanceTypeCounts) {
        int lateCount = attendanceTypeCounts.get(AttendanceType.LATE);
        int absentCount = attendanceTypeCounts.get(AttendanceType.ABSENT);
        return AttendanceWarningLevel.determine(lateCount, absentCount);
    }

    public List<AttendanceWarning> getAttendanceWarnings(LocalDate baseDate) {
        return nicknameRegistry.getNicknames()
                .stream()
                .map(nickname -> createAttendanceWarningFromLogs(baseDate, nickname))
                .filter(AttendanceWarning::isNotClean)
                .sorted(getAttendanceWarningComparator())
                .toList();
    }

    private AttendanceWarning createAttendanceWarningFromLogs(LocalDate baseDate, Nickname nickname) {
        EnumMap<AttendanceType, Integer> attendanceTypeCounts = attendanceLogs.countAttendanceTypes(nickname, baseDate);
        int lateCount = attendanceTypeCounts.getOrDefault(AttendanceType.LATE, 0);
        int absentCount = attendanceTypeCounts.getOrDefault(AttendanceType.ABSENT, 0);
        return new AttendanceWarning(nickname, lateCount, absentCount);
    }

    private Comparator<AttendanceWarning> getAttendanceWarningComparator() {
        return (first, second) -> {
            if (first.getAttendanceLevel() == second.getAttendanceLevel()) {
                return compareAbsentCountTotal(first, second);
            }
            return second.getAttendanceLevel().compareTo(first.getAttendanceLevel());
        };
    }

    private int compareAbsentCountTotal(AttendanceWarning first, AttendanceWarning second) {
        int firstAbsentCountTotal = first.absentCount() + (first.lateCount() / 3);
        int secondAbsentCountTotal = second.absentCount() + (second.lateCount() / 3);
        if (firstAbsentCountTotal == secondAbsentCountTotal) {
            return first.nickname().compareTo(second.nickname());
        }
        return firstAbsentCountTotal - second.absentCount() + (second.lateCount() / 3);
    }
}
