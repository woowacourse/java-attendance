package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class AttendanceBook {

    public static final int START_DAY_OF_MONTH = 1;
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

    public void saveAttendanceDateTime(final Crew crew, final AttendanceDateTime attendanceDateTime) {
        List<AttendanceDateTime> attendances = this.crewAttedances.get(crew);
        attendances.add(attendanceDateTime);
    }

    public AttendanceDateTime findAttendanceDateTimeByCrewAndDate(final Crew crew, final LocalDate findDate) {
        List<AttendanceDateTime> attendances = this.crewAttedances.get(crew);
        return attendances.stream()
                .filter(attendanceDateTime -> attendanceDateTime.isSameDate(findDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 일자에 출석하지 않았습니다."));
    }

    public void removeAttendanceDateTime(final Crew crew, final AttendanceDateTime attendanceDateTime) {
        List<AttendanceDateTime> attendances = this.crewAttedances.get(crew);
        attendances.remove(attendanceDateTime);
    }

    public List<AttendanceDateTime> findCrewAttendancesThisMonth(final Crew crew) {
        final int dayOfToday = LocalDate.now().getDayOfMonth();
        List<AttendanceDateTime> crewAttendanceDateTimes = new ArrayList<>();
        IntStream.range(START_DAY_OF_MONTH, dayOfToday)
                .mapToObj(day -> LocalDateTime.now().withDayOfMonth(day))
                .filter(dateTime -> !AttendanceDateTime.isWeekend(dateTime))
                .filter(dateTime -> !Holiday.isHoliday(dateTime))
                .map(dateTime -> findAttendanceDateTimeByCrewAndDateOrAbsent(crew, dateTime.toLocalDate()))
                .forEach(crewAttendanceDateTimes::add);
        return crewAttendanceDateTimes;
    }

    private AttendanceDateTime findAttendanceDateTimeByCrewAndDateOrAbsent(final Crew crew, final LocalDate findDate) {
        try {
            return findAttendanceDateTimeByCrewAndDate(crew, findDate);
        } catch (IllegalArgumentException exception) {
            return AttendanceDateTime.createAbsentDateTime(findDate);
        }
    }

    public Set<Crew> getAllCrews() {
        return this.crewAttedances.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceBook that = (AttendanceBook) o;
        return Objects.equals(crewAttedances, that.crewAttedances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewAttedances);
    }
}
