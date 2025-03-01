package attendance.domain;

import java.util.List;
import java.util.Objects;

public class AttendanceBook {

    private final Crew crew;
    private final AttendanceRecord attendanceRecord;

    public AttendanceBook(
        Crew crew,
        AttendanceRecord attendanceRecord
    ) {
        validateNotNull(crew, attendanceRecord);
        this.crew = crew;
        this.attendanceRecord = attendanceRecord;
    }

    private void validateNotNull(
        final Crew crew,
        final AttendanceRecord attendanceRecord
    ) {
        if (crew == null || attendanceRecord == null) {
            throw new IllegalArgumentException("출석부는 크루와 출석 기록을 가지고 있어야 합니다.");
        }
    }

    public void save(final AttendanceDateTime attendanceDateTime) {
        attendanceRecord.addAttendanceDateTime(attendanceDateTime);
    }

    public void modify(final AttendanceDateTime attendanceDateTime) {
        attendanceRecord.modifyAttendanceDateTime(attendanceDateTime);
    }

    public AttendanceDateTime retrieveByDate(final AttendanceDate attendanceDate) {
        return attendanceRecord.findByDate(attendanceDate);
    }

    public List<AttendanceDateTime> retrieveOrderByDateTimeUntilDate(final AttendanceDate untilDate) {
        return attendanceRecord.findAllUntilDate(untilDate)
            .stream()
            .sorted()
            .toList();
    }

    public Crew getCrew() {
        return crew;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AttendanceBook that = (AttendanceBook) o;

        return Objects.equals(crew, that.crew)
            && Objects.equals(attendanceRecord, that.attendanceRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, attendanceRecord);
    }
}
