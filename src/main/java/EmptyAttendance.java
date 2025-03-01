import java.time.LocalDate;
import java.time.LocalTime;

public class EmptyAttendance implements Attendance {
    private final AttendanceDate date;

    private EmptyAttendance(LocalDate date) {
        this.date = new AttendanceDate(date);
    }

    public static EmptyAttendance of(LocalDate date) {
        return new EmptyAttendance(date);
    }

    @Override
    public boolean isAttendedOn(LocalDate date) {
        return this.date.getValue().isEqual(date);
    }

    @Override
    public boolean isTimeRecorded() {
        return false;
    }

    @Override
    public LocalTime getTime() {
        throw new RuntimeException("무단 결석은 시간 기록이 존재하지 않습니다.");
    }

    @Override
    public AttendanceStatus getStatus() {
        return AttendanceStatus.ABSENCE;
    }
}
