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
        for (Map.Entry<String, List<LocalDateTime>> entry : rawDatas.entrySet()) {
            String crewName = entry.getKey();
            for (LocalDateTime dateTime : entry.getValue()) {
                AttendanceRecord record = new AttendanceRecord(dateTime);
                attendanceBook.add(crewName, record);
            }
        }
        return attendanceBook;
    }
}
