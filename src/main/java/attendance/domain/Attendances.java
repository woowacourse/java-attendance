package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Attendances {

    private static final LocalDate START_DATE_OF_DECEMBER = LocalDate.of(2024, 12, 1);

    private final Map<String, List<Attendance>> attendanceRecord;

    public Attendances() {
        this.attendanceRecord = new HashMap<>();
    }

    public void addAttendance(String name, Attendance attendance) {
        List<Attendance> attendances = attendanceRecord.computeIfAbsent(name, k -> new ArrayList<>());
        attendances.add(attendance);
    }

    public boolean hasAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        List<Attendance> attendances = attendanceRecord.get(name);
        return attendances.stream()
            .anyMatch(attendance -> attendance.hasAttend(attendanceDate, attendanceTime));
    }

    public void validateNameExists(String name) {
        if (!attendanceRecord.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public Optional<LocalTime> editAttendance(String name, LocalDate editDate, LocalTime editTime) {
        List<Attendance> attendances = attendanceRecord.getOrDefault(name, new ArrayList<>());
        Optional<Attendance> foundAttendance = findAttendanceExists(editDate, attendances);

        return foundAttendance
            .map(attendance -> editExistingAttendance(editDate, editTime, attendances, attendance))
            .orElseGet(() -> addNonExistingAttendance(name, editDate, editTime));
    }

    private Optional<Attendance> findAttendanceExists(LocalDate editDate, List<Attendance> attendances) {
        return attendances.stream()
            .filter(attendance -> attendance.hasAttendDate(editDate))
            .findFirst();
    }

    private Optional<LocalTime> editExistingAttendance(LocalDate editDate, LocalTime editTime, List<Attendance> attendances, Attendance foundAttendance) {
        int findAttendanceIndex = attendances.indexOf(foundAttendance);
        attendances.set(findAttendanceIndex, new Attendance(editDate, editTime));
        return Optional.of(foundAttendance.getAttendanceTime());
    }

    private Optional<LocalTime> addNonExistingAttendance(String name, LocalDate editDate, LocalTime editTime) {
        addAttendance(name, new Attendance(editDate, editTime));
        return Optional.empty();
    }

    public List<Attendance> findAttendanceUntilYesterday(String name, LocalDate today) {
        List<Attendance> attendances = attendanceRecord.getOrDefault(name, new ArrayList<>());

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
            attendances.add(new Attendance(currentDate, null));
        }
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(String name, LocalDate today) {
        List<Attendance> attendanceUntilYesterday = findAttendanceUntilYesterday(name, today);

        Map<AttendanceStatus, Integer> attendanceStatusCounts = AttendanceStatus.initMap();
        for (Attendance attendance : attendanceUntilYesterday) {
            AttendanceStatus status = AttendanceStatus.findAttendanceStatus(
                attendance.getAttendanceDate(), attendance.getAttendanceTime());
            attendanceStatusCounts.put(status, attendanceStatusCounts.get(status) + 1);
        }
        return attendanceStatusCounts;
    }
}
