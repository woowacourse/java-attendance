package model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record AttendanceTypeCountResult(
        EnumMap<AttendanceType, Integer> typeResult
) {

    public static AttendanceTypeCountResult from(
            final List<AttendanceHistoryByDate> histories
    ) {
        Map<AttendanceType, Integer> typeResult = getInitialTypeResult();
        histories.stream()
                .map(AttendanceHistoryByDate::type)
                .forEach(value -> typeResult.compute(value, (key, val) -> val + 1));
        return new AttendanceTypeCountResult(new EnumMap<>(typeResult));
    }

    private static Map<AttendanceType, Integer> getInitialTypeResult() {
        return Arrays.stream(AttendanceType.values())
                .collect(Collectors.toMap(
                        value -> value,
                        value -> 0)
                );
    }
}
