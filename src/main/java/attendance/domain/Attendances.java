package attendance.domain;

import attendance.dto.response.AttendanceGroupByStatus;
import attendance.dto.response.AttendanceRecord;
import attendance.dto.response.AttendanceRecordUntilToday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances = new ArrayList<>();

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance find(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 수정하려는 날짜는 출석할 수 없습니다."));
    }

    public Attendance checkAndUpdateAttendance(LocalDateTime dateTime) {
        validateAlreadyAttendance(dateTime.toLocalDate());
        return updateAttendance(dateTime);
    }

    public Attendance updateAttendance(LocalDateTime dateTime) {
        Attendance oldAttendance = find(dateTime.toLocalDate());
        attendances.remove(oldAttendance);

        Attendance updateAttendance = new Attendance(dateTime);
        attendances.add(updateAttendance);

        return updateAttendance;
    }

    public AttendanceGroupByStatus createCountUntilYesterday(LocalDate date) {
        int expulsion = calculateStatusUntilYesterday(AttendanceStatusType.EXPULSION, date);
        int late = calculateStatusUntilYesterday(AttendanceStatusType.LATE, date);
        int attendance = calculateStatusUntilYesterday(AttendanceStatusType.ATTENDANCE, date);
        AttendanceWarningType warning = AttendanceWarningType.find(expulsion, late);

        return new AttendanceGroupByStatus(
                expulsion,
                late,
                attendance,
                warning.getName()
        );
    }

    public int size() {
        return attendances.size();
    }

    public AttendanceRecordUntilToday createRecordUntilTodayResponse(LocalDate today) {
        List<AttendanceRecord> responses = attendances.stream()
                .map(Attendance::createResponse)
                .filter(response -> response.dateTime().toLocalDate().isBefore(today))
                .toList();
        return new AttendanceRecordUntilToday(responses);
    }

    private int calculateStatusUntilYesterday(AttendanceStatusType status, LocalDate date) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isBefore(date))
                .filter(attendance -> attendance.isEqualsStatus(status))
                .count();
    }

    private void validateAlreadyAttendance(LocalDate date) {
        if (find(date).isAlreadyCheck()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
        }
    }
}
