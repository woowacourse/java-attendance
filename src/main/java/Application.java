import config.AttendanceSheetFactory;
import config.ReadFile;
import domain.Attendance;
import domain.AttendanceSheet;
import policy.AbsentPolicy;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    public static void main(String[] args){
        final Path FILE_PATH = Paths.get("src/main/resources/attendances.csv");

        ReadFile<Attendance, AttendanceSheet> readFile = new AttendanceSheetFactory(new AbsentPolicy());
        AttendanceSheet attendanceSheet = readFile.loadFile(FILE_PATH);
    }
}
