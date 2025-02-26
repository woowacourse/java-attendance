package attendance.loader;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceRecord;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceAssembler {

    private final AttendancesLoader loader;

    public AttendanceAssembler(AttendancesLoader loader) {
        this.loader = loader;
    }

    public AttendanceBook assembleDatas() {
        loader.load();
        Map<String, List<LocalDateTime>> rawDatas = loader.getRawDatas();

        AttendanceBook attendanceBook = new AttendanceBook();
        rawDatas.forEach((crewName, dateTimes) ->
                addRecordsForCrew(crewName, dateTimes, attendanceBook)
        );
        return attendanceBook;
    }

    private void addRecordsForCrew(String crewName, List<LocalDateTime> dateTimes, AttendanceBook attendanceBook) {
        dateTimes.forEach(dateTime ->
                attendanceBook.add(crewName, new AttendanceRecord(dateTime))
        );
    }
}
