package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceRecord {
    private final String crewName;
    private final Attendances attendances;

    public AttendanceRecord(String crewName) {
        this.crewName = crewName;
        this.attendances = new Attendances();
    }

    public LocalDateTime attend(LocalDate date, LocalTime time) {
        checkDuplicateAttendance(date);
        AttendanceChecker.checkCampusOpen(date, time);

        updateAttendance(date, time);
        return LocalDateTime.of(date, time);
    }

    private void checkDuplicateAttendance(LocalDate date) {
        if (attendances.isAttendedDate(date)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 있습니다. 출석 수정 기능을 이용해주세요.");
        }
    }

    public Attendance modify(LocalDate date, LocalTime time) {
        AttendanceChecker.checkCampusOpen(date, time);

        Attendance prevAttendance = attendances.getCurrentAttendance(date);

        updateAttendance(date, time);
        return prevAttendance;
    }



    public WarningLevel calculateWarningLevel(int today){
        Map<AttendanceStatus, Integer> statusCount = AttendanceStatistics.getStatusCount(attendances, today);

        return WarningLevel.of(statusCount);
    }


    private void updateAttendance(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);
        attendances.addAttendance(date, attendance);
    }

    public boolean isNameMatched(String crewName) {
        return this.crewName.equals(crewName);
    }


}
