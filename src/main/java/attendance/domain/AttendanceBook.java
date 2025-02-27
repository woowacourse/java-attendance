package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<Crew, List<AttendanceDateTime>> crewAttedances;

    public AttendanceBook(final Map<Crew, List<AttendanceDateTime>> crewAttendances) {
        this.crewAttedances = crewAttendances;
    }

    public void validateRegisteredCrew(final Crew crew) {
        if (!this.crewAttedances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public void validateDuplicateAttendanceDate(final Crew crew, final AttendanceDateTime attendanceDateTime) {
        List<AttendanceDateTime> attendances = this.crewAttedances.get(crew);
        boolean isSameDateExists = attendances.stream()
                .anyMatch(datetime -> datetime.isSameDate(attendanceDateTime));
        if (isSameDateExists) {
            throw new IllegalArgumentException("오늘은 이미 출석하셨습니다. 출석 수정 기능을 이용해 주세요.");
        }
    }

    public void saveAttendanceDateTime(Crew crew, AttendanceDateTime attendanceDateTime) {
        List<AttendanceDateTime> attendances = this.crewAttedances.get(crew);
        attendances.add(attendanceDateTime);
    }

    public AttendanceDateTime findAttendanceDateTimeByCrewAndDay(final Crew crew, final int day) {
        List<AttendanceDateTime> attendances = this.crewAttedances.get(crew);
        return attendances.stream()
                .filter(attendanceDateTime -> attendanceDateTime.isThisDayInCurrentMonth(day))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 일자에 출석하지 않았습니다."));
    }

    public void removeAttendanceDateTime(Crew crew,AttendanceDateTime attendanceDateTime) {
        List<AttendanceDateTime> attendances = this.crewAttedances.get(crew);
        attendances.remove(attendanceDateTime);
    }
}
