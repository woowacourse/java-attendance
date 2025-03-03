package domain;

import java.time.LocalTime;
import util.DateTimeUtil;

public class CampusTimePolicy {
    public static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    public static void validateCampusTime(LocalTime time) {
        if (!DateTimeUtil.isInRange(CAMPUS_OPEN_TIME, CAMPUS_CLOSE_TIME, time)) {
            throw new IllegalArgumentException(time + ": 캠퍼스 운영시간이 아니므로 출석을 기록할 수 없습니다.");
        }
    }
}
