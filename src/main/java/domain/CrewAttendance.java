package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class CrewAttendance {
    private final Crew crew;
    private final AttendanceTimes attendanceTimes;

    private CrewAttendance(Crew crew, AttendanceTimes attendanceTimes) {
        this.crew = crew;
        this.attendanceTimes = attendanceTimes;
    }

    public static CrewAttendance of(Crew crew, AttendanceTimes attendanceTimes) {
        return new CrewAttendance(crew, attendanceTimes);
    }

    public void attend(AttendanceTime attendanceTime) {
        attendanceTimes.addAttendance(attendanceTime);
    }

    public Optional<AttendanceTime> modify(AttendanceTime attendanceTime) {
        return attendanceTimes.modifyAttendance(attendanceTime);
    }

    public List<LocalDateTime> readAttendanceTimesBefore(LocalDate date) {
        int today = date.getDayOfMonth();
        return IntStream.range(1, today)
                .mapToObj(day -> LocalDate.of(2024, 12, day))
                .filter(attendanceTimes::contains)
                .map(attendanceTimes::readAttendance)
                .sorted()
                .toList();
    }

    public int countAttendanceBeforeDate(LocalDate date) {
        // TODO: 구현
        return 2;
    }

    public int countLateBeforeDate(LocalDate date) {
        // TODO: 구현
        return 1;
    }

    public int countAbsenceBeforeDate(LocalDate date) {
        // TODO: 구현
        return 1;
    }

    public boolean belongsTo(Crew crew) {
        return this.crew.equals(crew);
    }
}
