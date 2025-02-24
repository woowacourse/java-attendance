package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Date {
    private LocalDate localDate;

    public Date(LocalDate localDate) {
        validateDate(localDate);
        this.localDate = localDate;
    }

    private void validateDate(LocalDate localDate) {
        if (localDate.getYear() != 2024 || localDate.getMonthValue() != 12) {
            throw new IllegalArgumentException("2024년 12월이 아닌 날짜는 등록할 수 없습니다.");
        }
    }

    public boolean isAfter(Date targetDate) {
        return this.localDate.isAfter(targetDate.localDate);
    }

    public int getMonthValue() {
        return localDate.getMonthValue();
    }

    public int getDayValue() {
        return localDate.getDayOfMonth();
    }

    public WorkDay getWorkDay() {
        return WorkDay.from(localDate.getDayOfWeek());
    }

    public boolean isHoliday() {
        return getWorkDay().isWeekend() || getDayValue() == 25;  // TODO: 공휴일 로직 추가
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Date date = (Date) o;
        return Objects.equals(localDate, date.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(localDate);
    }
}
