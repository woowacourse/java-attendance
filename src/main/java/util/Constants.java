package util;

import java.time.LocalTime;
import java.util.List;

public final class Constants {

    private Constants() {
    }

    public static final int FIXED_MONTH = 12;
    public static final int LENGTH_OF_MONTH = 31;
    public static final List<Integer> HOLIDAYS = List.of(25);
    // 00:00은 고정된 운영시간에서 불가능한 출석시간이기에 이 시간을 결석 대체 시간으로 사용
    public static final LocalTime ABSENCE_TIME = LocalTime.of(0, 0);
}
