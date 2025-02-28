package attendance.domain;

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

    public Crew getCrew() {
        return crew;
    }
}
