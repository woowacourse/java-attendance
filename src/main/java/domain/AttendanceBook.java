package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final List<Attendance> attendanceBook;

    public AttendanceBook(List<Attendance> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void checkIn(Attendance attendance) {
        attendanceBook.add(attendance);
    }

    public AttendanceBook update(LocalDate localDate, LocalTime localTime) {
        // 여기부터
        Attendance beforeAttendance = getBeforeAttendance(localDate, attendanceBook);
        // 여기까지 컨트롤러로
        List<Attendance> newAttendances = getNewAttendances(localDate, attendanceBook);

        newAttendances.add(beforeAttendance.updateTime(localTime));
        return new AttendanceBook(newAttendances);
    }

    private static Attendance getBeforeAttendance(LocalDate localDate, List<Attendance> crewAttendances) {
        return crewAttendances.stream()
                .filter(a -> a.getLocalDate().equals(localDate))
                .findFirst()
                .orElse(null);
    }

    private static List<Attendance> getNewAttendances(LocalDate localDate, List<Attendance> crewAttendances) {
        return crewAttendances.stream()
                .filter(a -> !a.getLocalDate().equals(localDate))
                .collect(Collectors.toList());
    }

    public void validateWeekDay(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || Holiday.isHoliday(localDate)) {
            throw new IllegalArgumentException("주말 및 공휴일에는 출석할 수 없습니다.");
        }
    }

    public void validateDuplicateCheckIn(LocalDate localDate) {
        if (attendanceBook.stream().anyMatch(attendance -> attendance.getLocalDate().equals(localDate))) {
            throw new IllegalArgumentException("이미 출석한 크루입니다.");
        }
    }

    public void validateAfterToday(LocalDate localDate) {
        if (localDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("수정할 수 없는 날짜입니다.");
        }
    }

    public Attendance getAttendance(LocalDate localDate) {
        return attendanceBook.stream()
                .filter(a -> a.getLocalDate().equals(localDate))
                .findFirst()
                .orElse(null);
    }

    public List<Attendance> getAttendanceBook() {
        return attendanceBook;
//        return Collections.unmodifiableList(attendanceBook);
    }

    public AttendanceStateCount calculateState() {
        int attendance = countState(AttendanceState.ATTENDANCE);
        int lateness = countState(AttendanceState.LATENESS);
        int absence = countState(AttendanceState.ABSENCE);

        return new AttendanceStateCount(attendance, lateness, absence);
    }

    private int countState(AttendanceState attendanceState) {
        return (int) attendanceBook.stream()
                .filter(a -> AttendanceState.findStateBy(a.getLocalDate(), a.getLocalTime())
                        .equals(attendanceState))
                .count();
    }

    public PenaltyType calculatePenaltyType(AttendanceStateCount attendanceStateCount) {
        int lateness = attendanceStateCount.lateness();
        int absence = attendanceStateCount.absence();

        int count = absence + (lateness / 3);
        return PenaltyType.getPenaltyType(count);
    }
}
