package dto;

import static domain.AbsenceLevel.LATE_TO_ABSENCE_THRESHOLD;
import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.LATE;

import domain.AbsenceLevel;
import domain.AttendanceResult;
import java.util.Map;

public class AbsenceCrewDto implements Comparable<AbsenceCrewDto> {

    private final String username;
    private final Map<AttendanceResult, Integer> results;
    private final String classifyAbsenceLevel;

    public AbsenceCrewDto(String username, Map<AttendanceResult, Integer> results, AbsenceLevel classifyAbsenceLevel) {
        this.username = username;
        this.results = results;
        this.classifyAbsenceLevel = classifyAbsenceLevel.getLevel();
    }

    @Override
    public int compareTo(AbsenceCrewDto o) {
        int myAbsenceCount = results.getOrDefault(LATE, 0);
        myAbsenceCount += results.getOrDefault(ABSENCE, 0) * LATE_TO_ABSENCE_THRESHOLD;
        int otherAbsenceCount = o.results.getOrDefault(LATE, 0);
        otherAbsenceCount += o.results.getOrDefault(ABSENCE, 0) * LATE_TO_ABSENCE_THRESHOLD;

        int result = Integer.compare(otherAbsenceCount, myAbsenceCount);

        if (result == 0) {
            return this.username.compareTo(o.username);
        }

        return result;
    }

    public String getUsername() {
        return username;
    }

    public Map<AttendanceResult, Integer> getResults() {
        return results;
    }

    public String getClassifyAbsenceLevel() {
        return classifyAbsenceLevel;
    }
}
