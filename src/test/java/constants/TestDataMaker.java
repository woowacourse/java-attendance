package constants;

import static domain.policy.TimePolicy.NORMAL_ATTEND_DEAD_LINE;
import static domain.policy.TimePolicy.NORMAL_LATE_DEAD_LINE;
import static domain.policy.TimePolicy.OPERATING_END;
import static domain.policy.TimePolicy.OPERATING_START;
import static domain.policy.TimePolicy.SPECIAL_ATTEND_DEAD_LINE;
import static domain.policy.TimePolicy.SPECIAL_LATE_DEAD_LINE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TestDataMaker {
    public static LocalDate MONDAY_DATE = LocalDate.of(2024, 12, 2);
    public static LocalDate TUESDAY_DATE = LocalDate.of(2024, 12, 3);
    public static LocalDate WEDNESDAY_DATE = LocalDate.of(2024, 12, 4);
    public static LocalDate THURSDAY_DATE = LocalDate.of(2024, 12, 5);
    public static LocalDate SATURDAY_DATE = LocalDate.of(2024, 12, 7);
    public static LocalDate SUNDAY_DATE = LocalDate.of(2024, 12, 1);
    public static LocalDate HOLIDAY_DATE = LocalDate.of(2024, 12, 25);

    public static LocalTime NON_OPERATING_TIME
            = getNonOperatingTime(OPERATING_START.getTime(), OPERATING_END.getTime());

    public static LocalTime ATTEND_MONDAY = getMidTime(OPERATING_START.getTime(), SPECIAL_ATTEND_DEAD_LINE.getTime());
    public static LocalTime LATE_MONDAY = getMidTime(SPECIAL_ATTEND_DEAD_LINE.getTime(),
            SPECIAL_LATE_DEAD_LINE.getTime());
    public static LocalTime ABSENT_MONDAY = getMidTime(SPECIAL_LATE_DEAD_LINE.getTime(), OPERATING_END.getTime());

    public static LocalTime ATTEND_EXCEPT_MONDAY =
            getMidTime(OPERATING_START.getTime(), NORMAL_ATTEND_DEAD_LINE.getTime());
    public static LocalTime LATE_EXCEPT_MONDAY =
            getMidTime(NORMAL_ATTEND_DEAD_LINE.getTime(), NORMAL_LATE_DEAD_LINE.getTime());
    public static LocalTime ABSENT_EXCEPT_MONDAY =
            getMidTime(NORMAL_LATE_DEAD_LINE.getTime(), OPERATING_END.getTime());

    private static LocalTime getMidTime(LocalTime start, LocalTime end) {
        long minutesBetween = start.until(end, ChronoUnit.MINUTES); // 분 단위로 차이를 계산
        return start.plusMinutes(minutesBetween / 2); // 시간과 끝 시간의 중간 값을 구한다.
    }

    public static LocalTime getNonOperatingTime(LocalTime opening, LocalTime closing) {
        LocalTime beforeOpening = opening.minusMinutes(1); // 운영 시작 1분 전
        LocalTime afterClosing = closing.plusMinutes(1);   // 운영 종료 1분 후

        if (!beforeOpening.isBefore(LocalTime.MIN)) { // 하루를 벗어나는 경우
            return beforeOpening;
        }

        if (!afterClosing.isAfter(LocalTime.MAX)) { // 하루를 벗어나는 경우
            return afterClosing;
        }

        return LocalTime.MIN; // 운영시간이 하루를 넘어가는 경우, 00:00 반환
    }
}