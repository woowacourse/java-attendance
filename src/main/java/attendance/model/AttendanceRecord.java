package attendance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public record AttendanceRecord(
        List<AttendanceDateTime> attendanceDateTimes
) {
    public AttendanceRecord() {
        this(new ArrayList<>());
    }

    public void add(AttendanceDateTime attendanceDateTime) {
        attendanceDateTimes.add(attendanceDateTime);
    }

    public boolean containsAttendanceDateTimeByDate(AttendanceDate attendanceDate) {
        return attendanceDateTimes.stream()
                .anyMatch(attendanceDateTime -> attendanceDateTime.equalsDate(attendanceDate));
    }

    public AttendanceDateTime findAttendanceByDate(AttendanceDate attendanceDate) {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> attendanceDateTime.equalsDate(attendanceDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석일입니다."));
    }

    public long computeLateCount() {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> AttendanceStatus.from(
                        attendanceDateTime.getAttendanceTime(),
                        EducationSchedule.from(attendanceDateTime.getAttendanceDate().date())
                ).equals(AttendanceStatus.LATE)).count();
    }

    public long computeAttendanceCount() {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> AttendanceStatus.from(
                        attendanceDateTime.getAttendanceTime(),
                        EducationSchedule.from(attendanceDateTime.getAttendanceDate().date())
                ).equals(AttendanceStatus.ATTEND)).count();
    }

    public long computeAbsencesUntil(LocalDate localDate) {
        long duringEducationDayCount = IntStream.range(1, localDate.getDayOfMonth() + 1)
                .mapToObj(day -> LocalDate.of(2024, 12, day))
                .filter(EducationDay::isDuringEducationDay)
                .count();
        return duringEducationDayCount - computeAttendanceCount() - computeLateCount();
    }

    public Panalty computePanaltyUntil(LocalDate date) {
        return Panalty.of(computeAbsencesUntil(date), computeLateCount());
    }
}
