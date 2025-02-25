package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Attendance {
    private static final int ABSENCE_HOUR = 23;
    private static final int ABSENCE_MINUTE = 59;
    private static final int ABSENCE_PER_TARDY = 3;

    private final List<AttendanceDate> attendanceDates = new ArrayList<>();

    public Attendance(LocalDate startDate, LocalDate endDate) {
        for (LocalDate cursorDate = startDate; cursorDate.isBefore(endDate); cursorDate = cursorDate.plusDays(1)) {
            validateAndUpdateAttendanceDates(cursorDate);
        }
    }

    public AttendanceState attend(LocalDateTime attendDateTime) {
        if (!attendDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("아직 출석할 수 없습니다.");
        }
        if (has(attendDateTime.toLocalDate())) {
            throw new IllegalArgumentException("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
        AttendanceDate attendanceDate = new AttendanceDate(attendDateTime);
        attendanceDates.add(attendanceDate);
        return attendanceDate.calculateAttendanceState();
    }

    public void editAttendanceDateTime(LocalDateTime attendanceDateTime) {
        AttendanceDate attendanceDate = findAttendanceDate(attendanceDateTime.toLocalDate());
        attendanceDate.editDateTime(attendanceDateTime);
    }

    public AttendanceDate findAttendanceDate(LocalDate findAttendanceDate) {
        Optional<AttendanceDate> attendanceDate = attendanceDates.stream()
                .filter(localDate -> localDate.isEqualsLocalDate(findAttendanceDate)).findFirst();
        if (attendanceDate.isPresent()) {
            return attendanceDate.get();
        }
        if (findAttendanceDate.isBefore(LocalDate.now())) {
            fillAttendanceDate();
            return findAttendanceDate(findAttendanceDate);
        }
        throw new IllegalArgumentException("아직 수정할 수 없습니다.");
    }

    public void fillAttendanceDate() {
        for (LocalDate cursorCheckDate = LocalDate.now().minusDays(1); !this.has(cursorCheckDate);
             cursorCheckDate = cursorCheckDate.minusDays(1)) {
            addAttendanceDate(cursorCheckDate);
        }
        Collections.sort(this.attendanceDates);
    }

    private void addAttendanceDate(LocalDate cursorCheckDate){
        try {
            attendanceDates.add(new AttendanceDate(
                    LocalDateTime.of(cursorCheckDate.getYear(),
                            cursorCheckDate.getMonth(),
                            cursorCheckDate.getDayOfMonth(), ABSENCE_HOUR,
                            ABSENCE_MINUTE)));
        } catch (IllegalArgumentException ignored) {
        }
    }

    private boolean has(LocalDate localDate) {
        return attendanceDates.stream().anyMatch(attendanceDate -> attendanceDate.isEqualsLocalDate(localDate));
    }

    private void validateAndUpdateAttendanceDates(LocalDate cursorDate) {
        if (cursorDate.getDayOfWeek().getValue() >= AttendanceDate.SATURDAY || Holiday.has(cursorDate)) {
            return;
        }

        AttendanceDate absenceDate = new AttendanceDate(
                LocalDateTime.of(
                        cursorDate.getYear(),
                        cursorDate.getMonth(),
                        cursorDate.getDayOfMonth(),
                        ABSENCE_HOUR,
                        ABSENCE_MINUTE));

        attendanceDates.add(absenceDate);
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
        return countAbsence() + (countTardy() / ABSENCE_PER_TARDY);
    }

    public List<AttendanceDate> getAttendanceDates() {
        return attendanceDates;
    }
}
