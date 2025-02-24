package domain;

import exception.AttendancesException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Attendances {

    private LinkedList<Attendance> attendances;

    public Attendances(final LinkedList<Attendance> attendances) {
        this.attendances = attendances;
    }

    public AttendanceDto calculateAttendanceCount() {
        final long attendanceCount = getCountByStatus(AttendanceStatus.ATTENDANCE);
        final long tardinessCount = getCountByStatus(AttendanceStatus.TARDINESS);
        final long absence = getCountByStatus(AttendanceStatus.ABSENCE);

        return new AttendanceDto((int) attendanceCount, (int) tardinessCount, (int) absence);
    }

    public int getCountByStatus(final AttendanceStatus status) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getAttendanceStatus().equals(status))
                .count();
    }

    public boolean existAttended(final LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.equalsDate(date));
    }

    public void addSorted(final Attendance newAttendance) {
        int index = 0;
        for (Attendance attendance : attendances) {
            if (newAttendance.getLocalDateTime().isBefore(attendance.getLocalDateTime())) {
                break;
            }
            index++;
        }
        attendances.add(index, newAttendance);
    }

    public Attendance findAttendance(final int findDayOfMonth) {
        return attendances.stream()
                .filter(attendance -> attendance.equals(findDayOfMonth))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        AttendancesException.NOT_FOUND_DAY_OF_MONTH.getMessage()));
    }

    public void updateTime(Attendance findAttendance, LocalTime updateTime) {
        attendances.remove(findAttendance);
        final LocalDateTime dateTime = findAttendance.getLocalDateTime();
        final AttendanceDateTime newAttendanceDateTime = AttendanceDateTime.of(dateTime.toLocalDate(), updateTime);
        Attendance newAttendance = new Attendance(newAttendanceDateTime);
        addSorted(newAttendance);
    }

    public List<Integer> getDates() {
        return attendances.stream()
                .map(Attendance::getDateOfMonth)
                .toList();
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
