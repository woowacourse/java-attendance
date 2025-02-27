package attendance.domain;

import java.time.LocalTime;

public enum OperatingTime {

    OPERATING_TIME(LocalTime.of(8, 0), LocalTime.of(23, 0));

    private final LocalTime openingTime;
    private final LocalTime closingTime;

    OperatingTime(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    /***
     * 0시 0분은 출석 기록이 없는 결석한 경우다.
     */
    public static boolean isOperate(LocalTime checkTime) {
        if (checkTime == LocalTime.of(0, 0)) {
            return true;
        }
        if (checkTime.isBefore(OPERATING_TIME.openingTime) ||
            checkTime.isAfter(OPERATING_TIME.closingTime)) {
            return false;
        }
        return true;
    }

}
