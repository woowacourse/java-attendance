package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<LocalDateTime> times = new ArrayList<>();
        int today = date.getDayOfMonth();
        for (int i = 1; i < today; i++) {
            try {
                LocalDateTime time = attendanceTimes.readAttendance(LocalDate.of(2024, 12, i));
                times.add(time);
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        return times;
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
