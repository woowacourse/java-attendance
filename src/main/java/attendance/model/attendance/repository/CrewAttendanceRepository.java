package attendance.model.attendance.repository;

import attendance.model.attendance.log.AttendanceLog;
import attendance.model.attendance.log.CrewAttendanceLog;
import attendance.model.attendance.log.CrewAttendanceLogDeserializer;
import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.Crew;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class CrewAttendanceRepository {

    private final List<CrewAttendanceLog> crewAttendanceLogs;

    public CrewAttendanceRepository(
            final CrewAttendanceLogDeserializer crewAttendanceLogDeserializer,
            final Path path,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        this.crewAttendanceLogs = crewAttendanceLogDeserializer.deserializeFromCsv(path, campusOperationPolicy);
    }

    public void add(final Crew crew, final AttendanceLog attendanceLog) {
    }

    public void update(final Crew crew, final AttendanceLog from, final AttendanceLog to) {
    }

    public List<AttendanceLog> findAllByCrew(
            final Crew crew,
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy) {

        return null;
    }

    public List<CrewAttendanceLog> getCrewAttendanceLogs() {
        return crewAttendanceLogs;
    }
}
