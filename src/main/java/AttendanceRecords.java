import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecords {
    private static final LocalDate FILL_START_DATE = LocalDate.of(2024, 11, 30);
    
    private List<AttendanceRecord> attendanceRecords;

    public AttendanceRecords() {
        this.attendanceRecords = new ArrayList<>();
    }

    public void addRecord(AttendanceRecord attendanceRecord) {
        attendanceRecords.add(attendanceRecord);
    }

    public boolean hasRecordOfDate(LocalDate date) {
        return attendanceRecords.stream()
                .anyMatch((record) -> record.getDate().equals(date));
    }

    public void fillAbsences() {
        for (LocalDate date = LocalDate.now(); date.isAfter(FILL_START_DATE); date = date.minusDays(1)) {
            if (!hasRecordOfDate(date)) {
                this.attendanceRecords.add(new AttendanceRecord(date));
            }
        }
    }
}
