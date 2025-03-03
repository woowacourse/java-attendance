package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Attendances {

    private static final LocalDate START_DATE_OF_DECEMBER = LocalDate.of(2024, 12, 1);
    public static final LocalTime ABSENCE_TIME = LocalTime.of(22, 59, 59);

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public void hasAttendance(LocalDate attendanceDate) {
        boolean hasAttendance = attendances.stream()
            .anyMatch(attendance -> attendance.hasAttendDate(attendanceDate));
        if(hasAttendance) {
            throw new IllegalArgumentException("[ERROR] 이미 출석기록이 존재합니다. 출석 수정을 이용해주세요.");
        }
    }

    public boolean hasAttendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        return attendances.stream()
            .anyMatch(attendance -> attendance.hasAttend(attendanceDate, attendanceTime));
    }

    public Optional<LocalTime> editAttendance(LocalDate editDate, LocalTime editTime) {
        Optional<Attendance> foundAttendance = findAttendanceExists(editDate);

        return foundAttendance
            .map(attendance -> editExistingAttendance(editDate, editTime, attendance))
            .orElseGet(() -> addNonExistingAttendance(editDate, editTime));
    }

    private Optional<Attendance> findAttendanceExists(LocalDate editDate) {
        return attendances.stream()
            .filter(attendance -> attendance.hasAttendDate(editDate))
            .findFirst();
    }

    private Optional<LocalTime> editExistingAttendance(LocalDate editDate, LocalTime editTime, Attendance foundAttendance) {
        int findAttendanceIndex = attendances.indexOf(foundAttendance);
        attendances.set(findAttendanceIndex, new Attendance(editDate, editTime));
        return Optional.of(foundAttendance.getAttendanceTime());
    }

    private Optional<LocalTime> addNonExistingAttendance(LocalDate editDate, LocalTime editTime) {
        addAttendance(new Attendance(editDate, editTime));
        return Optional.empty();
    }

    public List<Attendance> findAttendanceUntilYesterday(LocalDate today) {
        addAbsenceAttendance(today, START_DATE_OF_DECEMBER, attendances);

        return attendances
            .stream()
            .filter(attendance -> attendance.isBefore(today))
            .sorted()
            .toList();
    }

    private static void addAbsenceAttendance(LocalDate today, LocalDate currentDate, List<Attendance> attendances) {
        while (currentDate.isBefore(today)) {
            currentDate = processAbsence(currentDate, attendances);
        }
    }

    private static LocalDate processAbsence(LocalDate currentDate, List<Attendance> attendances) {
        if (Holiday.check(currentDate)) {
            return currentDate.plusDays(1);
        }

        createAbsenceAttendance(currentDate, attendances);
        return currentDate.plusDays(1);
    }

    private static void createAbsenceAttendance(LocalDate currentDate, List<Attendance> attendances) {
        boolean hasDate = attendances.stream()
            .anyMatch(attendance -> attendance.hasAttendDate(currentDate));
        if(!hasDate) {
            attendances.add(new Attendance(currentDate, ABSENCE_TIME));
        }
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(LocalDate today) {
        List<Attendance> attendanceUntilYesterday = findAttendanceUntilYesterday(today);

        Map<AttendanceStatus, Integer> attendanceStatusCounts = AttendanceStatus.initMap();
        for (Attendance attendance : attendanceUntilYesterday) {
            AttendanceStatus status = AttendanceStatus.findAttendanceStatus(
                attendance.getAttendanceDate(), attendance.getAttendanceTime());
            attendanceStatusCounts.put(status, attendanceStatusCounts.get(status) + 1);
        }
        return attendanceStatusCounts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attendances that = (Attendances) o;
        return Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
