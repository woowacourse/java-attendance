package domain;

import java.util.List;

public class AttendanceReader {

    private final String fileName;

    public AttendanceReader(String fileName) {
        this.fileName = fileName;
    }

    public AttendanceReader() {
        fileName = "/attendances.csv";
    }

    public List<AttendanceUnit> readAttendances() {

    }
}
