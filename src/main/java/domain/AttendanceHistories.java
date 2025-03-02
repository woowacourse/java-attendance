package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class AttendanceHistories {
    private final Map<Crew, AttendanceDateTimes> attendanceHistories;

    public AttendanceHistories(Map<Crew, AttendanceDateTimes> attendanceHistoryData) {
        this.attendanceHistories = attendanceHistoryData;
    }

    public AttendanceStatus addAttendanceHistory(Crew crew, LocalDateTime attendanceDateTime) {
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        validateDuplicateAttendance(attendanceDateTimes, attendanceDate);
        attendanceHistories.put(crew, attendanceHistories.get(crew).add(attendanceDateTime));
        return AttendanceStatus.of(attendanceDateTime);
    }

    public AttendanceDateTime replaceAttendanceHistory(Crew crew, LocalDateTime newAttendanceDateTime) {
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        AttendanceDateTime oldAttendanceDateTime = attendanceDateTimes.remove(newAttendanceDateTime.toLocalDate());
        attendanceDateTimes.add(newAttendanceDateTime);
        return oldAttendanceDateTime;
    }

    public AttendanceDateTimes getAttendanceDateTimes(Crew crew) {
        validateCrewPresence(crew);
        return attendanceHistories.get(crew);
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

    public int getPresentCount(LocalDate defaultDate, LocalDate localDate) {
        return 0;
    }
}
