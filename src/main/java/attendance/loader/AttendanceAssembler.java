package attendance.loader;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceRecord;
import attendance.domain.Crew;
import attendance.domain.EducationDayPolicy;
import attendance.domain.WoowaDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceAssembler {

    private final AttendancesLoader loader;
    private final EducationDayPolicy policy;

    public AttendanceAssembler(AttendancesLoader loader, EducationDayPolicy policy) {
        this.loader = loader;
        this.policy = policy;
    }

    public AttendanceBook assembleDatas() {
        loader.load();
        Map<String, List<LocalDateTime>> rawDatas = loader.getRawDatas();

        Map<Crew, AttendanceHistory> histories = new HashMap<>();
        rawDatas.forEach((crewName, dateTimes) -> {
            AttendanceHistory history = new AttendanceHistory();
            dateTimes.forEach(dateTime -> history.addRecord(
                    new AttendanceRecord(new WoowaDate(dateTime.toLocalDate(), policy), dateTime.toLocalTime())));
            histories.put(new Crew(crewName), history);
        });
        return new AttendanceBook(histories);
    }

}
