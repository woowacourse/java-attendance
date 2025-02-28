package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class AttendTimes {
    private final List<AttendTime> attendTimes;

    public AttendTimes() {
        this.attendTimes = new ArrayList<>();
    }

    public void add(AttendTime attendTime) {
        attendTimes.add(attendTime);
    }

    public void removeAttendance(int date) {
        attendTimes.remove(findAttendanceByDate(date).orElseThrow(() -> new IllegalArgumentException("없는 날 입니다.")));
    }

    public Optional<AttendTime> findAttendanceByDate(int date) {
        return attendTimes.stream()
                .filter(attendTime -> attendTime.checkSameDate(date))
                .findAny();
    }

    public List<AttendTime> getAttendTimeline() {
        List<AttendTime> allAttendanceFromToday = new ArrayList<>();

        LocalDate todayLocalDate = LocalDate.parse(AttendanceController.TODAY_LOCAL_DATE, AttendanceController.TODAY_FORMATTER);
        int todayDayOfMonth = todayLocalDate.getDayOfMonth();
        int todayMonthValue = todayLocalDate.getMonthValue();
        int todayYear = todayLocalDate.getYear();

        for (int i = 1; i < todayDayOfMonth; i++) {
            AttendTime attendTime = findAttendanceByDate(i).orElse(null);
            if (attendTime != null) {
                allAttendanceFromToday.add(attendTime);
                continue;
            }

            if (December.checkWeekDay(i)) {
                AttendTime absentAttendance = new AttendTime(LocalDate.of(todayYear, todayMonthValue, i), null);
                allAttendanceFromToday.add(absentAttendance);
            }
        }
        return allAttendanceFromToday;
    }

    public int calculateAttendedCount() {
        return (int) attendTimes.stream().filter(attendTime -> attendTime.checkAttendanceStatus().equals(AttendanceStatus.ATTENDED)).count();
    }

    public int calculateLateCount() {
        return (int) attendTimes.stream().filter(attendTime -> attendTime.checkAttendanceStatus().equals(AttendanceStatus.LATE)).count();
    }

    public int calculateAbsentCount() {
        int total = 0;
        LocalDate todayLocalDate = LocalDate.parse(AttendanceController.TODAY_LOCAL_DATE, AttendanceController.TODAY_FORMATTER);
        int todayDayOfMonth = todayLocalDate.getDayOfMonth();

        total += (int) IntStream.range(1, todayDayOfMonth).filter(i -> findAttendanceByDate(i).isEmpty() && December.checkWeekDay(i)).count();

        return total + (int) attendTimes.stream().filter(attendTime -> attendTime.checkAttendanceStatus().equals(AttendanceStatus.ABSENT)).count();
    }

    public List<AttendTime> getAttendTimes() {
        return new ArrayList<>(attendTimes);
    }
}
