package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record AttendanceRecord(
        List<AttendanceDateTime> attendanceDateTimes
) {
    public AttendanceRecord() {
        this(new ArrayList<>());
    }

    public void attend(AttendanceDate attendanceDate, LocalTime attendanceTime) {
        validateExistAttendance(attendanceDate);
        attendanceDateTimes.add(new AttendanceDateTime(attendanceDate, attendanceTime));
    }

    public void modify(AttendanceDate modifyDate, LocalTime modifyTime) {
        AttendanceDateTime dateTime = findAttendanceByDate(modifyDate);
        dateTime.modifyAttendanceTime(new AttendanceDateTime(modifyDate, modifyTime));
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

    public long computeAbsencesUntil(LocalDate now) {
        long duringEducationDayCount = Stream.iterate(
                        SystemDuration.startDate,
                        date -> date.isBefore(SystemDuration.computeLastAttendanceDate(now).plusDays(1)),
                        date -> date.plusDays(1))
                .filter(EducationDay::isDuringEducationDay)
                .count();
        return duringEducationDayCount - computeAttendanceCount() - computeLateCount();
    }

    public Panalty computePanaltyUntil(LocalDate date) {
        return Panalty.of(computeAbsencesUntil(date), computeLateCount());
    }

    private void validateExistAttendance(AttendanceDate attendanceDate) {
        if (containsAttendanceDateTimeByDate(attendanceDate)) {
            throw new IllegalArgumentException("이미 출석한 날짜입니다.");
        }
    }
}
