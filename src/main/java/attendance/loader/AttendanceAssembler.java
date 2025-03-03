package attendance.loader;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceRecord;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.EducationDayPolicy;
import attendance.domain.WoowaDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceAssembler {

    private final AttendancesLoader loader;
    private final EducationDayPolicy policy;
    private AttendanceBook attendanceBook;
    private Crews crews;

    public AttendanceAssembler(AttendancesLoader loader, EducationDayPolicy policy) {
        this.loader = loader;
        this.policy = policy;
        assembleDatas();
    }

    private void assembleDatas() {
        loader.load();
        Map<String, List<LocalDateTime>> rawDatas = loader.getRawDatas();
        Map<String, Crew> crewRegistry = new HashMap<>();

        Map<Crew, AttendanceHistory> histories = new HashMap<>();
        rawDatas.forEach((crewName, dateTimes) -> {
            Crew crew = new Crew(crewName);
            crewRegistry.put(crewName, crew);
            AttendanceHistory history = new AttendanceHistory();
            dateTimes.forEach(dateTime -> history.addRecord(
                    new AttendanceRecord(new WoowaDate(dateTime.toLocalDate(), policy), dateTime.toLocalTime())));
            histories.put(crew, history);
        });
        this.attendanceBook = new AttendanceBook(histories);
        this.crews = new Crews(crewRegistry);
    }

    public AttendanceBook getAttendanceBook() {
        return attendanceBook;
    }

    public Crews getCrews() {
        return crews;
    }

}
