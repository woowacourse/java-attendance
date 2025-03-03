package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AttendanceBooks {

    private final Map<String, Attendances> attendanceRecord;

    public AttendanceBooks() {
        this.attendanceRecord = new HashMap<>();
    }

    public void addAttendance(String name, Attendance attendance) {
        Attendances attendances = attendanceRecord.getOrDefault(name, new Attendances(new ArrayList<>()));
        attendances.addAttendance(attendance);
        attendanceRecord.put(name, attendances);
    }

    public void hasAttendance(String name, LocalDate attendanceDate) {
        Attendances attendances = attendanceRecord.getOrDefault(name, new Attendances(new ArrayList<>()));
        attendances.hasAttendance(attendanceDate);
    }

    public boolean hasAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        Attendances attendances = attendanceRecord.getOrDefault(name, new Attendances(new ArrayList<>()));
        return attendances.hasAttendance(attendanceDate, attendanceTime);
    }

    public void validateNameExists(String name) {
        if (!attendanceRecord.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public Optional<LocalTime> editAttendance(String name, LocalDate editDate, LocalTime editTime) {
        Attendances attendances = attendanceRecord.getOrDefault(name, new Attendances(new ArrayList<>()));
        return attendances.editAttendance(editDate, editTime);
    }

    public List<Attendance> findAttendanceUntilYesterday(String name, LocalDate today) {
        Attendances attendances = attendanceRecord.getOrDefault(name, new Attendances(new ArrayList<>()));
        return attendances.findAttendanceUntilYesterday(today);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(String name, LocalDate today) {
        Attendances attendances = attendanceRecord.getOrDefault(name, new Attendances(new ArrayList<>()));
        return attendances.countAttendanceStatus(today);
    }

    public List<String> getAllCrewNames() {
        return attendanceRecord.keySet().stream().toList();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceBooks that = (AttendanceBooks) o;
        return Objects.equals(attendanceRecord, that.attendanceRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceRecord);
    }
}
