package attendance.domain;

import java.time.LocalTime;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, AttendanceHistory> crewHistories;

    public AttendanceBook(Map<String, AttendanceHistory> crewHistories) {
        this.crewHistories = crewHistories;
    }

    public void add(String crewName, AttendanceRecord record) {
        AttendanceHistory history = crewHistories.get(crewName);
        history.addRecord(record);
    }

    public void modify(String crewName, WoowaDate targetDate, LocalTime modifyTime) {
        AttendanceHistory history = crewHistories.get(crewName);
        history.modifyRecord(targetDate, modifyTime);
    }

    public AttendanceHistory getHistoryByName(String crewName) {
        AttendanceHistory history = crewHistories.get(crewName);
        if (history == null) {
            throw new IllegalArgumentException("해당 크루의 기록이 없습니다.");
        }
        return history;
    }

    public AttendanceRecord getRecordBy(String crewName, WoowaDate date) {
        AttendanceHistory history = crewHistories.get(crewName);
        if (history == null) {
            throw new IllegalArgumentException("해당 크루의 기록이 없습니다.");
        }
        return history.getRecordByDate(date);
    }

}
