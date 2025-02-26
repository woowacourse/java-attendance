package attendance.model.attendance.log;

import attendance.model.campus.CampusOperationPolicy;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CrewAttendanceLogDeserializer {

    private static final String CREW_DATETIME_DELIMITER = ",";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<CrewAttendanceLog> deserializeFromCsv(
            final Path csvFilePath,
            final CampusOperationPolicy campusOperationPolicy
    ) {
        return null;
    }
}
