package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances = new ArrayList<>();

    public Attendances(List<LocalDateTime> attendanceDateTimes, LocalDateTime today) {
        for(int day=1; day<today.getDayOfMonth(); day++) {
            LocalDate localDate = LocalDate.of(today.getYear(), today.getMonth(), day);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY) || Holiday.isExists(localDate)) {
                continue;
            }
            this.attendances.add(initializeAttendance(attendanceDateTimes, today, day));
        }
    }

    private Attendance initializeAttendance(List<LocalDateTime> attendanceDateTimes, LocalDateTime today, int day) {
        return attendanceDateTimes.stream()
                .filter(dateTime -> dateTime.toLocalDate().isEqual(LocalDate.of(today.getYear(), today.getMonth(), day)))
                .findAny()
                .map(Attendance::new)
                .orElse(Attendance.absence(LocalDate.of(today.getYear(), today.getMonth(), day)));
    }

    public void addAttendance(final Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendanceByLocalDate(final LocalDate findDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(findDate))
                .findAny()
                .orElse(Attendance.absence(findDate));
    }

    public List<Attendance> findAllBeforeToday(LocalDateTime today) {
        boolean hasTodayAttendance = attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(today.toLocalDate()));
        if (hasTodayAttendance) {
            return attendances.subList(0, attendances.size());
        }
        return attendances.stream()
                .toList();
    }
}
