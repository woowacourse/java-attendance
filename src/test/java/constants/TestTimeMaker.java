package constants;

import static constants.TimeConstants.EXCEPT_MONDAY_ATTEND_TIME_END;
import static constants.TimeConstants.EXCEPT_MONDAY_LATE_TIME_END;
import static constants.TimeConstants.MONDAY_ATTEND_TIME_END;
import static constants.TimeConstants.MONDAY_LATE_TIME_END;
import static constants.TimeConstants.OPERATION_TIME_END;
import static constants.TimeConstants.OPERATION_TIME_START;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TestTimeMaker {
    public static final LocalTime NON_OPERATING_TIME =
            getNonOperatingTime(OPERATION_TIME_START, OPERATION_TIME_END);

    // 운영 시작 시간과 출석 인정 시간 사이에 출석하면 출석이다.
    public static final LocalTime MONDAY_ATTEND =
            getMidTime(OPERATION_TIME_START, MONDAY_ATTEND_TIME_END);
    public static final LocalTime EXCEPT_MONDAY_ATTEND =
            getMidTime(OPERATION_TIME_START, EXCEPT_MONDAY_ATTEND_TIME_END);

    // 출석 인증 시간과 지각 인증 시간 사이에 출석하면 지각이다.
    public static final LocalTime MONDAY_LATE =
            getMidTime(MONDAY_ATTEND_TIME_END, MONDAY_LATE_TIME_END);
    public static final LocalTime EXCEPT_MONDAY_LATE =
            getMidTime(EXCEPT_MONDAY_ATTEND_TIME_END, EXCEPT_MONDAY_LATE_TIME_END);

    // 지각 인증 시간과 운영 마감 시간 사이에 출석하면 지각이다.
    public static final LocalTime MONDAY_ABSENT =
            getMidTime(MONDAY_LATE_TIME_END, OPERATION_TIME_END);
    public static final LocalTime EXCEPT_MONDAY_ABSENT =
            getMidTime(EXCEPT_MONDAY_LATE_TIME_END, OPERATION_TIME_END);


    private static LocalTime getMidTime(LocalTime time1, LocalTime time2) {
        long minutesBetween = time1.until(time2, ChronoUnit.MINUTES); // 차이 (분 단위)
        return time1.plusMinutes(minutesBetween / 2); // 중간 값 계산
    }

    public static LocalTime getNonOperatingTime(LocalTime opening, LocalTime closing) {
        LocalTime beforeOpening = opening.minusMinutes(1); // 운영 시작 1분 전
        LocalTime afterClosing = closing.plusMinutes(1);   // 운영 종료 1분 후

        // 운영 전 시간이 하루를 벗어나지 않으면 반환
        if (!beforeOpening.isBefore(LocalTime.MIN)) {
            return beforeOpening;
        }

        // 운영 후 시간이 하루를 벗어나지 않으면 반환
        if (!afterClosing.isAfter(LocalTime.MAX)) {
            return afterClosing;
        }

        // 기본적으로 00:00 반환 (운영 시간이 하루를 꽉 채우는 경우)
        return LocalTime.MIN;
    }
}