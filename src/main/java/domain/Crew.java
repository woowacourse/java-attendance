package domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String nickName;
    private List<Attendance> attendances;

    public Crew(String nickName) {
        this.nickName = nickName;
        this.attendances = new ArrayList<>();
    }

    public CrewDto toDto() {
        return new CrewDto(nickName, calculateLateCount(), calculateAbsentCount(), getPenaltyStatus());
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    private PenaltyStatus getPenaltyStatus() {
        return PenaltyStatus.getInstance(calculateNonAttendanceCount());
    }

    private Integer calculateNonAttendanceCount() {
        return calculateAbsentCount() + calculateLateCount() / 3;
    }

    private Integer calculateLateCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.toDto().getLate().equals(true))
                .count();
    }

    private Integer calculateAbsentCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.toDto().getAbsent().equals(true))
                .count();
    }

    public Boolean isEqualTo(String nickname) {
        return this.nickName.equals(nickname);
    }

    public Boolean isAlreadyAttend(LocalDate date) {
        for (Attendance attendance : attendances) {
            if (attendance.isEqualTo(date)) {
                return true;
            }
        }
        return false;
    }

    public Attendance findByDate(Integer dayOfMonth) {
        LocalDate today = LocalDate.now();
        validateDayOfMonth(dayOfMonth, today);

        int month = today.getMonth().getValue();
        LocalDate date = LocalDate.of(today.getYear(), month, dayOfMonth);
        return attendances.stream()
                .filter(attendance -> attendance.isEqualTo(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜는 출석일이 아닙니다."));
    }


    private void validateDayOfMonth(Integer dayOfMonth, LocalDate today) {
        YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonth().getValue());
        if (dayOfMonth < 1 || dayOfMonth > yearMonth.lengthOfMonth()) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 날짜입니다.");
        }
    }
}
