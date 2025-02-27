import java.util.TreeSet;

public class AttendanceRecords {
    private final TreeSet<AttendanceRecord> records = new TreeSet<>();

    public void add(AttendanceRecord record) {
        records.add(record);
    }
}
