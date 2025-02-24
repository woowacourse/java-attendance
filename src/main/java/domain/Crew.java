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

    public CrewDto toDto() {
        int attendancesSize = attendances.size();
        return new CrewDto(nickName, attendancesSize, calculateLateCount(), calculateAbsentCount(), getPenaltyStatus());
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    private PenaltyStatus getPenaltyStatus() {
        return PenaltyStatus.getInstance(calculateNonAttendanceCount());
    }

    private Integer calculateNonAttendanceCount() {
        return calculateAbsentCount() + calculateLateCount() / LATE_COUNT_FOR_ABSENCE;
    }

    private Integer calculateLateCount() {
        return (int) attendances.stream()
                .filter(Attendance::isLate)
                .count();
    }

    private Integer calculateAbsentCount() {
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

    public void recordAbsence(LocalDate today) {
        today.withDayOfMonth(1)
                .datesUntil(today)
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .filter(date -> !Holiday.isHoliday(date))
                .filter(date -> !isAlreadyAttend(date))
                .forEach(date -> addAttendance(new Attendance(new Day(date), null)));
    }

    public Attendance findByDate(Integer dayOfMonth) {
        Day today = new Day(StandardDate.DATE);
        validateDayOfMonth(dayOfMonth, today);

        int month = StandardDate.DATE.getMonth().getValue();
        LocalDate date = LocalDate.of(StandardDate.DATE.getYear(), month, dayOfMonth);
        return attendances.stream()
                .filter(attendance -> attendance.isEqualTo(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜는 출석일이 아닙니다."));
    }


    private void validateDayOfMonth(Integer dayOfMonth, Day today) {
        if (!today.containsDayOfMonth(dayOfMonth)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 날짜입니다.");
        }
    }

    public List<Attendance> getAttendances() {
        attendances.sort(Comparator.comparing((Attendance attendance) -> attendance.toDto().getDate()));
        return List.copyOf(attendances);
    }
}
