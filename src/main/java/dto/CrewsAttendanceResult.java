package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import model.Attendances;
import model.Crew;

public class CrewsAttendanceResult {

    private final List<CrewAttendanceResult> crewsAttendanceResult;

    private CrewsAttendanceResult(List<CrewAttendanceResult> crewsAttendanceResult) {
        this.crewsAttendanceResult = crewsAttendanceResult;
    }

    public static CrewsAttendanceResult of(Map<Crew, Attendances> crewsAttendance) {
        List<CrewAttendanceResult> result = new ArrayList<>();
        for (Entry<Crew, Attendances> crewAttendancesEntry : crewsAttendance.entrySet()) {
            result.add(CrewAttendanceResult.of(crewAttendancesEntry.getKey(), crewAttendancesEntry.getValue()));
        }
        return new CrewsAttendanceResult(result);
    }

    public List<CrewAttendanceResult> getCrewsAttendanceResult() {
        return crewsAttendanceResult;
    }

    public static class CrewAttendanceResult {

        private final Crew crew;
        private final AttendanceResult attendanceResult;

        private CrewAttendanceResult(Crew crew, AttendanceResult attendanceResult) {
            this.crew = crew;
            this.attendanceResult = attendanceResult;
        }

        public static CrewAttendanceResult of(Crew crew, Attendances attendances) {
            return new CrewAttendanceResult(crew, AttendanceResult.of(attendances));
        }
    }
}
