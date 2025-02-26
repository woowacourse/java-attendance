package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, AttendanceHistory> crewHistories;

    public AttendanceBook(Map<String, AttendanceHistory> crewHistories) {
        this.crewHistories = crewHistories;
    }

    public void add(String crewName, AttendanceRecord record) {
        AttendanceHistory history = crewHistories.get(crewName);
        if (history == null) {
            throw new IllegalArgumentException("해당 크루의 기록이 없습니다.");
        }
        history.addRecord(record);
    }

    public void modify(String crewName, LocalDate targetDate, LocalTime modifyTime) {
        AttendanceHistory history = crewHistories.get(crewName);
        if (history == null) {
            throw new IllegalArgumentException("해당 크루의 기록이 없습니다.");
        }
        history.modifyRecord(targetDate, modifyTime);
    }

    public WarningStatus getWarningByCrew(String crewName) {
        AttendanceHistory history = crewHistories.get(crewName);
        if (history == null) {
            throw new IllegalArgumentException("해당 크루의 기록이 없습니다.");
        }
        return history.getWarningStatus();
    }

    public AttendanceHistory getHistoryByName(String crewName) {
        AttendanceHistory history = crewHistories.get(crewName);
        if (history == null) {
            throw new IllegalArgumentException("해당 크루의 기록이 없습니다.");
        }
        return history;
    }

}
