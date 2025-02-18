package attendance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {
    private final List<LocalDateTime> attendanceList;

    public AttendanceRepository() {
        attendanceList = new ArrayList<>();
    }

    public void add(final LocalDateTime localDateTime) {
        boolean isPresent = attendanceList.stream()
                .map(LocalDateTime::toLocalDate)
                .anyMatch(localDate -> localDate.equals(localDateTime.toLocalDate()));

        if (isPresent) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
        }

        attendanceList.add(localDateTime);
    }
}
