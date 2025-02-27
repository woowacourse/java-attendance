package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_NO_HISTORY_CREW;

import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

public class AttendanceBook {
    private final Map<String, AttendanceHistory> crewHistories;

    public AttendanceBook(Map<String, AttendanceHistory> crewHistories) {
        this.crewHistories = crewHistories;
    }

    public void add(String crewName, AttendanceRecord record) {
        AttendanceHistory history = getHistoryByName(crewName);
        history.addRecord(record);
    }

    public void modify(String crewName, WoowaDate targetDate, LocalTime modifyTime) {
        AttendanceHistory history = crewHistories.get(crewName);
        history.modifyRecord(targetDate, modifyTime);
    }

    public Optional<AttendanceRecord> findRecordBy(String crewName, WoowaDate date) {
        AttendanceHistory history = getHistoryByName(crewName);
        return history.findRecordByDate(date);
    }

    public AttendanceHistory getHistoryByName(String crewName) {
        AttendanceHistory history = crewHistories.get(crewName);
        if (history == null) {
            throw new IllegalArgumentException(ERROR_NO_HISTORY_CREW);
        }
        return history;
    }

}
