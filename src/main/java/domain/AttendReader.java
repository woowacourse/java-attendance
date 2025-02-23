package domain;

import java.util.List;

public class AttendReader {
    
    private final String filePath;

    public AttendReader(String filePath) {
        this.filePath = filePath;
    }

    public AttendanceBook loadAttendanceBook() {
        List<String> data = CsvReader.readCsv(filePath);
        AttendanceBook attendanceBook = new AttendanceBook();
        for (String row : data) {
            final String name = CsvReader.parseName(row);
            final Attend attend = CsvReader.parseAttend(row);
            attendanceBook.registerName(name);
            attendanceBook.attend(name, attend);
        }
        return attendanceBook;
    }
}
