package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_NO_HISTORY_CREW;

import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

public class AttendanceBook {
    private final Map<Crew, AttendanceHistory> crewHistories;

    public AttendanceBook(Map<Crew, AttendanceHistory> crewHistories) {
        this.crewHistories = crewHistories;
    }

    public void add(Crew crew, AttendanceRecord record) {
        AttendanceHistory history = getHistoryByCrew(crew);
        history.addRecord(record);
    }

    public void modify(Crew crew, WoowaDate targetDate, LocalTime modifyTime) {
        AttendanceHistory history = crewHistories.get(crew);
        history.modifyRecord(targetDate, modifyTime);
    }

    public Optional<AttendanceRecord> findRecordBy(Crew crew, WoowaDate date) {
        AttendanceHistory history = getHistoryByCrew(crew);
        return history.findRecordByDate(date);
    }

    public AttendanceHistory getHistoryByCrew(Crew crew) {
        AttendanceHistory history = crewHistories.get(crew);
        if (history == null) {
            throw new IllegalArgumentException(ERROR_NO_HISTORY_CREW);
        }
        return history;
    }

    public Crews getCrews() {
        Crews crews = new Crews();
        crewHistories.keySet().forEach(crews::addCrew);
        return crews;
    }

}
