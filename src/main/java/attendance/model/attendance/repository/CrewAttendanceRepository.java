package attendance.model.attendance.repository;

import attendance.model.attendance.log.AttendanceLog;
import attendance.model.attendance.log.AttendanceLogs;
import attendance.model.attendance.log.CrewAttendanceLog;
import attendance.model.attendance.log.CrewAttendanceLogDeserializer;
import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.Crew;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public AttendanceLogs findAllByCrew(
            final Crew crew,
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy) {

        return findByCrew(crew).getAllAttendanceLogs(from, to, campusOperationPolicy);
    }

    public List<CrewAttendanceLog> getCrewAttendanceLogs() {
        return crewAttendanceLogs;
    }

    public AttendanceLog findByCrewAndDate(final Crew crew, final LocalDate date) {
        return findByCrew(crew).findAttendanceLogByDate(date);
    }

    public List<Crew> getAllCrews() {
        return crewAttendanceLogs.stream()
                .map(crewAttendanceLog -> new Crew(crewAttendanceLog.getCrewNickname()))
                .toList();
    }

    public List<CrewAttendanceLog> getWarningCrewAttendanceLogs(
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        Map<Crew, AttendanceLogs> crewAttendanceLogsMap = crewAttendanceLogs.stream()
                .collect(HashMap::new,
                        (map, crewAttendanceLog) -> map.put(new Crew(crewAttendanceLog.getCrewNickname()),
                                crewAttendanceLog.getAllAttendanceLogs(from, to, campusOperationPolicy)), Map::putAll);

        return crewAttendanceLogsMap.entrySet().stream()
                .map(entry -> new CrewAttendanceLog(entry.getKey(), entry.getValue()))
                .filter(CrewAttendanceLog::isWarning)
                .toList();
    }
}
