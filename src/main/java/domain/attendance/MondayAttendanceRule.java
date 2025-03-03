package domain.attendance;

import domain.attendance.constant.AttendanceStatus;
import domain.datetime.CampusTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MondayAttendanceRule implements AttendanceRule {

    private static final int MONDAY_HOUR_LIMIT = 13;
    private static final int ABSENCE_LIMIT = 30;
    private static final int TARDINESS_LIMIT = 5;

    private static final Map<AttendanceStatus, List<CampusTime>> STATUS = new EnumMap<>(AttendanceStatus.class);

    static {
        STATUS.put(AttendanceStatus.ATTENDANCE,
                List.of(CampusTime.startTime(), CampusTime.of(MONDAY_HOUR_LIMIT, TARDINESS_LIMIT + 1)));
        STATUS.put(AttendanceStatus.TARDINESS,
                List.of(CampusTime.of(MONDAY_HOUR_LIMIT, TARDINESS_LIMIT),
                        CampusTime.of(MONDAY_HOUR_LIMIT, ABSENCE_LIMIT + 1)));
        STATUS.put(AttendanceStatus.ABSENCE,
                List.of(CampusTime.of(MONDAY_HOUR_LIMIT, ABSENCE_LIMIT), CampusTime.endTime()));
    }

    @Override
    public AttendanceStatus calculateStatus(CampusTime time) {
        return STATUS.entrySet().stream()
                .filter(entry -> time.isAfter(entry.getValue().getFirst()) && time.isBefore(entry.getValue().getLast()))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 상태를 계산할 수 없습니다."));
    }
}

