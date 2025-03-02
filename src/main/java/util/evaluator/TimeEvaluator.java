package util.evaluator;

import java.time.LocalTime;

public class TimeEvaluator {

    private TimeEvaluator() {
    }

    public static boolean isOpenTime(LocalTime time) {
        return time.isAfter(LocalTime.of(8, 0)) && time.isBefore(LocalTime.of(23, 0));
    }
}
