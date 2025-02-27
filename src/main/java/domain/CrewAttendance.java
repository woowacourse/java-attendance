package domain;

import java.time.DayOfWeek;
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

    public DisciplinaryStatus getDisciplinaryStatus(LocalDate date) {
        int absenceCount = countAbsenceBeforeDate(date);
        int lateCount = countLateBeforeDate(date);
        return DisciplinaryStatus.from(absenceCount, lateCount);
    }

    public int countAttendanceBeforeDate(LocalDate date) {
        return attendanceTimes.countAttendanceBeforeDate(date);
    }

    public int countLateBeforeDate(LocalDate date) {
        return attendanceTimes.countLateBeforeDate(date);
    }

    public int countAbsenceBeforeDate(LocalDate date) {
        return countWorkday(date)
                - (attendanceTimes.countAttendanceBeforeDate(date)
                + attendanceTimes.countLateBeforeDate(date));
    }

    private int countWorkday(LocalDate date) {
        int count = 0;
        for (int i = 1; i < date.getDayOfMonth(); i++) {
            LocalDate today = LocalDate.of(2024, 12, i);
            if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }
            count++;
        }
        return count;
    }

    public boolean belongsTo(Crew crew) {
        return this.crew.equals(crew);
    }
}
