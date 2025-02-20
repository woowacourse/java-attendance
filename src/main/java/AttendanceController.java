import java.net.URL;
import java.util.List;

public class AttendanceController {

    private static final String CSV_PATH = "attendances.csv";

    public void run() {
        List<String> rows = readCsv();
        AttendanceBook attendanceBook = loadAttendanceBook(rows);

        for (String row : rows) {
            System.out.println(row);
        }
    }

    private AttendanceBook loadAttendanceBook(List<String> data) {
        AttendanceBook attendanceBook = new AttendanceBook();
        for (String row : data) {
            final String name = parseName(row);
            final Attend attend = parseAttend(row);
            attendanceBook.registerName(name);
            attendanceBook.attend(name, attend);
        }
        return attendanceBook;
    }

    private Attend parseAttend(String row) {
        String dateTime = row.split(",")[1];
        String day = dateTime.substring(8, 10);
        String time = dateTime.substring(11);
        return Attend.of(day, time);
    }

    private String parseName(String row) {
        return row.split(",")[0];
    }

    private List<String> readCsv() {
        URL fileURL = AttendanceController.class.getResource(CSV_PATH);
        return FileUtil.readFile(fileURL);
    }
}
