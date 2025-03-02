package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class AttendanceHistories {
    private final Map<Crew, AttendanceDateTimes> attendanceHistories;

    public AttendanceHistories(Map<Crew, AttendanceDateTimes> attendanceHistoryData) {
        this.attendanceHistories = attendanceHistoryData;
    }

    public AttendanceStatus addAttendanceHistory(Crew crew, LocalDateTime attendanceDateTime) {
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        validateDuplicateAttendance(attendanceDateTimes, attendanceDate);
        validateDayOff(attendanceDate);
        validateOperatingTime(attendanceTime);
        attendanceHistories.put(crew, attendanceHistories.get(crew).add(attendanceDateTime));
        return AttendanceStatus.of(attendanceDateTime);
    }

    public LocalDateTime replaceAttendanceHistory(Crew crew, LocalDateTime newAttendanceDateTime) {
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        LocalDateTime oldAttendanceDateTime = attendanceDateTimes.remove(newAttendanceDateTime.toLocalDate());
        attendanceDateTimes.add(newAttendanceDateTime);
        return oldAttendanceDateTime;
    }

    private void validateCrewPresence(Crew crew) {
        if (!attendanceHistories.containsKey(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    private void validateDuplicateAttendance(AttendanceDateTimes attendanceDateTimes, LocalDate attendanceDate) {
        if (attendanceDateTimes.contains(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
    }

    private void validateDayOff(LocalDate attendanceDate) {
        if (attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY
                || attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || LegalHoliday.isHoliday(attendanceDate)
                || Vacation.isVacation(attendanceDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일");
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", formatter.format(attendanceDate)));
        }
    }

    private void validateOperatingTime(LocalTime attendanceTime) {
        if (!CampusHour.isOperatingTime(attendanceTime)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }
}
