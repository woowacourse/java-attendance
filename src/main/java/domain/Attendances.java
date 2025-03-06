package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class Attendances {
    private final List<LocalDateTime> attendances;

    public Attendances(List<LocalDateTime> attendances) {
        this.attendances = attendances;
    }

    public Attendances add(LocalDateTime attendanceTime) {
        this.attendances.add(attendanceTime);
        return this;
    }

    public boolean haveAttendanceDate(LocalDate attendanceDate) {
        return attendances.stream()
                .map(LocalDateTime::toLocalDate)
                .anyMatch(attendanceDate::isEqual);
    }

    public LocalDateTime edit(LocalDate oldAttendanceTime) {
        return attendances.stream()
                .filter(dateTime -> dateTime.toLocalDate().isEqual(oldAttendanceTime))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없습니다."));
    }

    public int getAttendanceCount(LocalDate standardDate) {
        return (int) attendances.stream()
                .filter(attendanceDateTime -> attendanceDateTime.toLocalDate().isBefore(standardDate))
                .filter(attendanceDateTime -> AttendanceResult.getAttendanceResult(attendanceDateTime)
                        == AttendanceResult.ATTENDANCE)
                .count();
    }

    public int getLateCount(LocalDate standardDate) {
        return (int) attendances.stream()
                .filter(attendanceDateTime -> attendanceDateTime.toLocalDate().isBefore(standardDate))
                .filter(attendanceDateTime -> AttendanceResult.getAttendanceResult(attendanceDateTime)
                        == AttendanceResult.LATE)
                .count();
    }

    public int getAbsentCount(LocalDate standardDate) {
        int absentCountFromRecords = (int) attendances.stream()
                .filter(attendanceDateTime -> attendanceDateTime.toLocalDate().isBefore(standardDate))
                .filter(attendanceDateTime -> AttendanceResult.getAttendanceResult(attendanceDateTime)
                        == AttendanceResult.ABSENT)
                .count();
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        int absentDaysWithoutRecords = 0;
        for (LocalDate date = startDate; date.isBefore(standardDate); date = date.plusDays(1)) {
            absentDaysWithoutRecords = getAbsentDaysWithoutRecords(date, absentDaysWithoutRecords);
            continue;
        }
        return absentCountFromRecords + absentDaysWithoutRecords;
    }

    private int getAbsentDaysWithoutRecords(LocalDate date, int absentDaysWithoutRecords) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
                || Holiday.isHoliday(date)) {
            return absentDaysWithoutRecords;
        }
        if (attendances.stream().noneMatch(dateTime -> dateTime.toLocalDate().isEqual(date))) {
            absentDaysWithoutRecords++;
        }
        return absentDaysWithoutRecords;
    }

    public LocalDateTime get(LocalDate date) throws NoSuchElementException {
        return attendances.stream() // List<LocalDateTime>에서 스트림 생성
                .filter(attendanceDateTime -> attendanceDateTime.toLocalDate().isEqual(date)) // 날짜 비교
                .findAny() // 하나 찾기
                .orElseThrow(() -> new NoSuchElementException("해당 날짜의 출석 기록이 없습니다: " + date));
    }

}
