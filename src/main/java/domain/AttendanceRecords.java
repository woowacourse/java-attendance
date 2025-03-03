package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import util.DateUtils;

public class AttendanceRecords {

    private final List<AttendanceDateTime> records = new ArrayList<>();

    public void add(AttendanceDateTime attendanceDateTime) {
        if (existsSameDate(attendanceDateTime)) {
            throw new IllegalArgumentException("이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
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

    public List<AttendanceDateTime> getRecordsWithMissingDatesBetween(LocalDate fromInclusive, LocalDate toInclusive) {
        List<AttendanceDateTime> recordsBetweenDates = records.stream()
            .filter(adt -> adt.isBetweenDates(fromInclusive, toInclusive))
            .toList();

        List<AttendanceDateTime> missingDates = collectMissingDates(recordsBetweenDates, fromInclusive, toInclusive);
        return concat(recordsBetweenDates, missingDates);
    }

    private List<AttendanceDateTime> collectMissingDates(List<AttendanceDateTime> records, LocalDate fromInclusive, LocalDate toInclusive) {
        Set<LocalDate> presentDates = records.stream()
            .map(AttendanceDateTime::getDate)
            .collect(Collectors.toSet());
        return fromInclusive.datesUntil(toInclusive.plusDays(1))
            .filter(DateUtils::isWorkingDay)
            .filter(date -> !presentDates.contains(date))
            .map(AttendanceDateTime::ofAbsence)
            .toList();
    }

    private List<AttendanceDateTime> concat(List<AttendanceDateTime> l1, List<AttendanceDateTime> l2) {
        return Stream.concat(l1.stream(), l2.stream())
            .sorted(Comparator.comparing(AttendanceDateTime::getDate))
            .collect(Collectors.toList());
    }
}
