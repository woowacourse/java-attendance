package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Attendance {
    private List<AttendanceDate> attendanceDates = new ArrayList<>();

    public Attendance(LocalDate startDate, LocalDate endDate) {
        for (LocalDate cursorDate = startDate; cursorDate.isBefore(endDate); cursorDate = cursorDate.plusDays(1)) {
            if (cursorDate.getDayOfWeek().getValue() > 5 || Holiday.has(cursorDate)) {
                continue;
            }

            attendanceDates.add(new AttendanceDate(
                    LocalDateTime.of(cursorDate.getYear(), cursorDate.getMonth(), cursorDate.getDayOfMonth(), 23,
                            59)));
        }
    }

    public void editAttendanceDateTime(LocalDateTime attendanceDateTime) {
        AttendanceDate attendanceDate = findAttendanceDate(attendanceDateTime.toLocalDate());
        attendanceDate.editDateTime(attendanceDateTime);
    }

    public AttendanceDate findAttendanceDate(LocalDate findAttendanceDate) {
        Optional<AttendanceDate> attendanceDate = attendanceDates.stream()
                .filter(localDate -> localDate.equals(findAttendanceDate)).findFirst();
        if (attendanceDate.isPresent()) {
            return attendanceDate.get();
        }
        if (findAttendanceDate.isBefore(LocalDate.now())) {
            fillAttendanceDate();
            return findAttendanceDate(findAttendanceDate);
        }
        throw new IllegalArgumentException("");
    }


    public void attend(LocalDateTime attendDateTime) {
        if (!attendDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("");
        }
        if (has(attendDateTime.toLocalDate())) {
            throw new IllegalArgumentException("");
        }
        attendanceDates.add(new AttendanceDate(attendDateTime));
    }

    public void fillAttendanceDate() {
        for (LocalDate cursorCheckDate = LocalDate.now().minusDays(1); !this.has(cursorCheckDate);
             cursorCheckDate = cursorCheckDate.minusDays(1)) {
            try {
                attendanceDates.add(new AttendanceDate(
                        LocalDateTime.of(cursorCheckDate.getYear(), cursorCheckDate.getMonth(),
                                cursorCheckDate.getDayOfMonth(), 23,
                                59)));
            } catch (IllegalArgumentException exception) {
            }
        }
        this.attendanceDates = attendanceDates.stream().sorted().toList();
    }

    private boolean has(LocalDate localDate) {
        return attendanceDates.stream().anyMatch(attendanceDate -> attendanceDate.equals(localDate));
    }

    public int countAbsence() {
        return (int) attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.calculateAttendanceState()
                        .equals(AttendanceState.ABSENCE))
                .count();
    }

    public int countAttendance() {
        return (int) attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.calculateAttendanceState()
                        .equals(AttendanceState.ATTENDANCE))
                .count();
    }

    public int countTardy() {
        return (int) attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.calculateAttendanceState()
                        .equals(AttendanceState.TARDY))
                .count();
    }

    public int countAbsenceIncludingTardy() {
        return countAbsence() + (countTardy() / 3);
    }
}
