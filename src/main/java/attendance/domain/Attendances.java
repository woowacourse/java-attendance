package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Attendances {

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
}
