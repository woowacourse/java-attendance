package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        attendances = new ArrayList<>();
    }

    public void initAttendances(String name, List<List<String>> csvData) {
        Crew crew = new Crew(name);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
        csvData.stream()
                .filter(row -> row.getFirst().equals(name))
                .map(row -> new Attendance(
                        crew,
                        LocalDateTime.parse(row.getLast(), dateTimeFormatter)
                ))
                .forEach(attendances::add);

        calculateAttendancesType();
        fillEmptyAttendance(crew);
    }

    private void calculateAttendancesType() {
        for (Attendance attendance : attendances) {
            attendance.calculateAttendanceType();
        }
    }

    private void fillEmptyAttendance(Crew crew) {
        LocalDate firstDate = LocalDate.of(2025, 2, 1);
        LocalDate today = LocalDate.now();
        for (LocalDate i = firstDate; i.isBefore(today); i = i.plusDays(1)) {
            if (isWorkday(i) && !isAttend(i)) {
                attendances.add(new Attendance(
                        crew,
                        LocalDateTime.of(i, LocalTime.of(0, 0)),
                        AttendanceType.ABSENT
                ));
            }
        }
    }

    private boolean isWorkday(LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY
                && date.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    private boolean isAttend(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(date));
    }

    public int calculatePresentCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getType() == AttendanceType.PRESENT)
                .count();
    }

    public int calculateLateCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getType() == AttendanceType.LATE)
                .count();
    }

//    public int calculateAbsentCount() {
//        return (int) attendances.stream()
//                .filter(attendance -> attendance.getType() == AttendanceType.ABSENT)
//                .count();
//    }
}
