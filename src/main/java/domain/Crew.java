package domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Crew {
    private final static int LATE_COUNT_FOR_ABSENCE = 3;
    private final static int SATURDAY = 6;
    private final static int SUNDAY = 7;

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
        return (int) attendances.stream().filter(attendance -> attendance.toDto().getLate().equals(true)).count();
    }

    private Integer calculateAbsentCount() {
        return (int) attendances.stream().filter(attendance -> attendance.toDto().getAbsent().equals(true)).count();
    }

    public Boolean isEqualTo(String nickname) {
        return this.nickName.equals(nickname);
    }

    public Boolean isAlreadyAttend(LocalDate date) {
        return attendances.stream().anyMatch(attendance -> attendance.isEqualTo(date));
    }

    public void recordAbsence() {
        LocalDate today = LocalDate.now();

        today.withDayOfMonth(1)
                .datesUntil(today)
                .filter(date -> date.getDayOfWeek().getValue() != SATURDAY && date.getDayOfWeek().getValue() != SUNDAY)
                .filter(date -> !isAlreadyAttend(date))
                .forEach(date -> addAttendance(new Attendance(new Day(date), null)));
    }

    public Attendance findByDate(Integer dayOfMonth) {
        LocalDate today = LocalDate.now();
        validateDayOfMonth(dayOfMonth, today);

        int month = today.getMonth().getValue();
        LocalDate date = LocalDate.of(today.getYear(), month, dayOfMonth);
        return attendances.stream().filter(attendance -> attendance.isEqualTo(date)).findFirst().orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜는 출석일이 아닙니다."));
    }


    private void validateDayOfMonth(Integer dayOfMonth, LocalDate today) {
        YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonth().getValue());
        if (dayOfMonth < 1 || dayOfMonth > yearMonth.lengthOfMonth()) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 날짜입니다.");
        }
    }

    public List<Attendance> getAttendances() {
        attendances.sort(Comparator.comparing((Attendance attendance) -> attendance.toDto().getDate()));
        return List.copyOf(attendances);
    }
}
