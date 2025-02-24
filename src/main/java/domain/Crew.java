package domain;

import domain.constant.StandardDate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Crew {
    private final static int LATE_COUNT_FOR_ABSENCE = 3;

    private final String nickName;
    private final List<Attendance> attendances;

    public Crew(String nickName) {
        this.nickName = nickName;
        this.attendances = new ArrayList<>();
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public Integer calculateLateCount() {
        return (int) attendances.stream()
                .filter(Attendance::isLate)
                .count();
    }

    public Integer calculateAbsentCount() {
        return (int) attendances.stream()
                .filter(Attendance::isAbsent)
                .count();
    }

    public Boolean isEqualTo(String nickname) {
        return this.nickName.equals(nickname);
    }

    public Boolean isAlreadyAttend(LocalDate date) {
        return attendances.stream().anyMatch(attendance -> attendance.isEqualTo(date));
    }

    public void recordAbsence(LocalDate todayDate) {
        todayDate.withDayOfMonth(1)
                .datesUntil(todayDate)
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .filter(date -> !Holiday.isHoliday(date))
                .filter(date -> !isAlreadyAttend(date))
                .forEach(date -> addAttendance(new Attendance(new Day(date), null)));
    }

    public Attendance findByDate(Integer dayOfMonth) {
        Day day = StandardDate.TODAY.createDay(dayOfMonth);
        day.validateDayOfMonth(dayOfMonth);
        day.validateNonHoliday();

        return attendances.stream()
                .filter(attendance -> attendance.isEqualTo(day.getDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜는 출석일이 아닙니다."));
    }

    public List<Attendance> getAttendances() {
        attendances.sort(Comparator.comparing((Attendance attendance) -> attendance.getDate()));
        return List.copyOf(attendances);
    }

    public String getNickName() {
        return nickName;
    }

    public int calculateAttendanceCount() {
        return attendances.size() - calculateLateCount() - calculateAbsentCount();
    }

    public PenaltyStatus getPenaltyStatus() {
        return PenaltyStatus.getInstance(calculateNonAttendanceCount());
    }

    private Integer calculateNonAttendanceCount() {
        return calculateAbsentCount() + calculateLateCount() / LATE_COUNT_FOR_ABSENCE;
    }
}
