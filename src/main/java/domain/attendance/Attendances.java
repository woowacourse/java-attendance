package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Attendances {
    private final List<Attendance> attendances = new ArrayList<>();

    public Attendances(List<LocalDateTime> attendances, LocalDate startDate, LocalDate endDate) {
        validate(attendances);
        initAttendanceDate(startDate, endDate);
        attendances.forEach(this::editAttendanceDate);
    }

    public AttendanceStatus attend(LocalDate endDate, LocalDateTime attendDateTime) {
        if (has(attendDateTime.toLocalDate())) {
            throw new IllegalArgumentException("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요");
        }
        if (attendDateTime.toLocalDate().isAfter(endDate)) {
            throw new IllegalArgumentException("미래에 출석할 수 없습니다");
        }
        Attendance attendance = new Attendance(attendDateTime);
        this.attendances.add(attendance);
        return attendance.getStatus();
    }

    public Attendance editAttendanceDate(LocalDateTime dateTime) {
        Attendance beforeDateTime = attendances.stream()
                .filter(attendance -> attendance.has(dateTime.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석이 존재하지 않습니다"));
        deleteAttendanceDate(dateTime.toLocalDate());
        attendances.add(new Attendance(dateTime));
        return beforeDateTime;
    }

    public boolean has(LocalDate day) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.has(day));
    }

    public List<AttendanceDate> getAttendanceDates() {
        return attendances.stream().map(Attendance::getDate).toList();
    }

    public int countAttendance() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.ATTENDANCE)
                .count();
    }

    public int countTardy() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.TARDY)
                .count();
    }

    public int countAbsence() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.ABSENCE)
                .count();
    }

    public int countAllAbsence() {
        return countAbsence() + countAbsencePerTardy();
    }

    private int countAbsencePerTardy() {
        return countTardy() / 3;
    }

    private void validate(List<LocalDateTime> dateTimes) {
        Set<LocalDate> dates = dateTimes
                .stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toSet());
        if (dateTimes.size() != dates.size()) {
            throw new IllegalArgumentException("동일한 날짜의 출석 기록은 등록할 수 없습니다");
        }
    }

    private void initAttendanceDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("출석부 시작 날짜가 마지막 날짜보다 늦을 수 없습니다");
        }
        while (startDate.isBefore(endDate)) {
            startDate = updateAttendanceDate(startDate);
        }
    }

    private LocalDate updateAttendanceDate(LocalDate startDate) {
        if (Holiday.isHoliday(startDate)
                || startDate.getDayOfWeek().compareTo(DayOfWeek.FRIDAY) >= Attendance.WEEKDAY) {
            startDate = startDate.plusDays(1);
            return startDate;
        }
        attendances.add(new Attendance(startDate));
        return startDate.plusDays(1);
    }

    private void deleteAttendanceDate(LocalDate date) {
        attendances.remove(attendances.stream()
                .filter(attendance -> attendance.has(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석이 존재하지 않습니다")));
    }
}
