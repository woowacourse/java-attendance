package domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record AttendanceRecords(
        List<Attendance> attendances
) {

    public void add(Attendance attendance) {
        validateAlreadyExistByDate(attendance);
        attendances.add(attendance);
    }

    public LocalDate retrieveOldestDate() {
        return attendances.stream()
                .map(Attendance::getAttendanceDate)
                .min(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없어 가장 오래된 날짜를 찾을 수 없습니다"));
    }

    public List<Attendance> retrieveAllFilledNonExistingDay(LocalDate oldestDayInBook, LocalDate todayDate) {
        Set<LocalDate> existingDates = retrieveExistingAttendanceDates();

        List<Attendance> attendancesUntilToday = retrieveAllUntilDate(todayDate);
        Stream.iterate(oldestDayInBook, date -> !date.isAfter(todayDate), date -> date.plusDays(1))
                .filter(date -> !existingDates.contains(date))
                .map(Attendance::createAbsenceAttendance)
                .forEach(attendancesUntilToday::add);

        return attendancesUntilToday;
    }

    public Attendance retrieveAttendanceByDate(LocalDate attendanceDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualDate(attendanceDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 입력한 날짜의 출석 기록이 없습니다."));
    }

    public static AttendanceRecords create(List<Attendance> attendances) {
        return new AttendanceRecords(attendances);
    }

    private void validateAlreadyExistByDate(Attendance attendance) {
        boolean alreadyExist = attendances.stream()
                .anyMatch(originAttendance -> originAttendance.isEqualDate(attendance));
        if (alreadyExist) {
            throw new IllegalArgumentException("[ERROR] 해당 날짜에 출석 기록이 이미 존재합니다");
        }
    }

    private List<Attendance> retrieveAllUntilDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isBeforeDate(date))
                .collect(Collectors.toList());
    }

    private Set<LocalDate> retrieveExistingAttendanceDates() {
        return attendances.stream()
                .map(Attendance::getAttendanceDate)
                .collect(Collectors.toSet());
    }
}
