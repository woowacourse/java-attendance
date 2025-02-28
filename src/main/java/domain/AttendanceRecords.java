package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import util.DateUtils;

public class AttendanceRecords {

    private final List<AttendanceDateTime> records = new ArrayList<>();

    public void add(AttendanceDateTime attendanceDateTime) {
        if (existsSameDate(attendanceDateTime)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
        }
        records.add(attendanceDateTime);
    }

    public void removeIfAttendedOnDate(LocalDate date) {
        records.removeIf(attendanceDateTime -> attendanceDateTime.isSameDate(date));
    }

    private boolean existsSameDate(AttendanceDateTime attendanceDateTime) {
        LocalDate date = attendanceDateTime.getDate();
        return records.stream()
            .anyMatch(record -> record.isSameDate(date));
    }

    public List<AttendanceDateTime> getRecords() {
        return new ArrayList<>(records);
    }

    public List<AttendanceDateTime> getAbsenceDatesBetween(LocalDate fromInclusive, LocalDate toInclusive) {
        int countToConsider = toInclusive.getDayOfMonth() - fromInclusive.getDayOfMonth() + 1;
        List<AttendanceDateTime> missingDates = collectMissingDates(fromInclusive, countToConsider);

        return concat(records, missingDates);
    }

    private List<AttendanceDateTime> collectMissingDates(LocalDate fromInclusive, int count) {
        List<LocalDate> presentDates = records.stream()
            .map(AttendanceDateTime::getDate)
            .toList();
        return Stream.iterate(fromInclusive, date -> date.plusDays(1))
            .limit(count)
            .filter(DateUtils::isWorkingDay)
            .filter(date -> !presentDates.contains(date))
            .map(AttendanceDateTime::ofAbsence)
            .toList();
    }

    private List<AttendanceDateTime> concat(List<AttendanceDateTime> l1, List<AttendanceDateTime> l2) {
        return Stream.concat(l1.stream(), l2.stream())
            .collect(Collectors.toList());
    }
}
