package domain;

import java.time.LocalDate;
import java.time.YearMonth;

public class Crew {

    private final String nickName;
    private final Attendances attendances;

    public Crew(String nickName) {
        this.nickName = nickName;
        this.attendances = new Attendances();
    }

    public CrewDto toDto() {
        return new CrewDto(nickName, attendances);
    }

    public void addAttendance(Attendance attendance) {
        attendances.addAttendance(attendance);
    }

    public Boolean isEqualTo(String nickname) {
        return this.nickName.equals(nickname);
    }

    public Boolean isAlreadyAttend(LocalDate date) {
        return attendances.isAlreadyAttended(date);
    }

    public void recordAbsence() {
        attendances.recordAbsence();
    }


    public Attendance findByDate(Integer dayOfMonth) {
        LocalDate today = LocalDate.now();
        validateDayOfMonth(dayOfMonth, today);

        int month = today.getMonth().getValue();
        LocalDate date = LocalDate.of(today.getYear(), month, dayOfMonth);
        return attendances.findByDate(date);
    }


    private void validateDayOfMonth(Integer dayOfMonth, LocalDate today) {
        YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonth().getValue());
        if (dayOfMonth < 1 || dayOfMonth > yearMonth.lengthOfMonth()) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 날짜입니다.");
        }
    }
}
