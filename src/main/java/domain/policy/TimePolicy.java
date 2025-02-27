package domain.policy;

import java.time.LocalTime;

public class TimePolicy {

    private static final LocalTime OPERATING_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime OPERATING_END_TIME = LocalTime.of(23, 0);

    public void validateOperatingTime(LocalTime time) {
        if(time.isBefore(OPERATING_START_TIME)
        || time.isAfter(OPERATING_END_TIME))
        {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다");
        }
    }
}
