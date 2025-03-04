package attendance.util;

import attendance.domain.Attendances;
import attendance.domain.Holiday;
import java.util.List;
import java.util.Map;

public interface DataLoader {

    Map<String, Attendances> loadAttendancesData();

    List<Holiday> loadHolidayData();

}
