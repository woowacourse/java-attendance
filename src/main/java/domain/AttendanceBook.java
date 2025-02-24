package domain;

import dto.AttendanceData;
import dto.ModifyResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AttendanceBook {
    private final Map<String, AttendanceHistory> attendanceBook;

    public AttendanceBook() {
        this.attendanceBook = new HashMap<>();
    }

    public List<Entry<String, AttendanceHistory>> getSorted() {
        return attendanceBook.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Entry<String, AttendanceHistory> entry) ->
                                entry.getValue().getAbsentCount()).reversed()
                        .thenComparing(Entry::getKey)
                )
                .toList();
    }

    public boolean contains(String name) {
        return attendanceBook.containsKey(name);
    }

    public void enter(String name) {
        attendanceBook.put(name, new AttendanceHistory());
    }

    public Attendance add(String name, LocalDateTime dateAndTime) {
        return attendanceBook.get(name).addAttendance(dateAndTime);
    }

    public ModifyResult modifyCrewAttendanceByName(String name, LocalDateTime dateAndTime) {
        return attendanceBook.get(name).update(dateAndTime);
    }

    public AttendanceData getAttendanceData(String name, LocalDate lastDate) {
        return attendanceBook.get(name).getAttendanceHistory(lastDate);
    }

    public void updateAbsentHistory(LocalDate date) {
        attendanceBook.values().forEach(
                attendanceInfo -> attendanceInfo.updateUntil(date)
        );
    }
}
