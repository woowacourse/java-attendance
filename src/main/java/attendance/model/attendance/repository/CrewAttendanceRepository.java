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
        findByCrew(crew).addAttendanceLog(attendanceLog);
    }

    private CrewAttendanceLog findByCrew(final Crew crew) {
        return crewAttendanceLogs.stream()
                .filter(crewAttendanceLog -> crewAttendanceLog.isSameCrew(crew))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 크루가 존재하지 않습니다."));
    }

    public void update(final Crew crew, final AttendanceLog from, final AttendanceLog to) {
        findByCrew(crew).updateAttendanceLog(from, to);
    }

    public List<AttendanceLog> findAllByCrew(
            final Crew crew,
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy) {

        return findByCrew(crew).getAllAttendanceLogs(from, to, campusOperationPolicy);
    }

    public List<CrewAttendanceLog> getCrewAttendanceLogs() {
        return crewAttendanceLogs;
    }
}
