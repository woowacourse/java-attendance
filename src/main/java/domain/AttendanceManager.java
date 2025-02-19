package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceManager {

    private Map<String, Records> crews;

    public AttendanceManager() {
        this.crews = new HashMap<>();
    }

    public void createCrew(String name, List<LocalDateTime> localDateTimes) {
        crews.put(name, new Records(localDateTimes));
    }

    public TimeAndStatus attendCrew(String name, LocalDateTime localDateTime) {
        Records records = crews.get(name);

        if (records == null) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }

        if (records.isSameDate(localDateTime)) {
            throw new IllegalArgumentException("이미 출석한 경우 수정 기능을 사용하세요.");
        }

        return records.attend(localDateTime);
    }

    public Records findByName(String name) {
        return crews.get(name);
    }
}
