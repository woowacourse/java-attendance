import domain.Attendance;
import domain.AttendanceBook;
import domain.Day;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttandanceHistoryLoader {
    public AttendanceBook initializeAttendanceWith(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);

        try {
            AttendanceBook attendanceBook = new AttendanceBook();
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] history = line.split(",");
                String nickname = history[0];
                String[] attendanceDateTime = history[1].trim().split(" ");
                String attendanceDate = attendanceDateTime[0];
                String attendanceTime = attendanceDateTime[1];

                Day day = new Day(LocalDate.parse(attendanceDate.trim()));
                Attendance attendance = new Attendance(day, LocalTime.parse(attendanceTime.trim()));
                attendanceBook.recordAttendance(nickname, attendance);
            }
            return attendanceBook;

        } catch (Exception e) {
            throw new IOException("[ERROR] 초기데이터 로드 중 오류가 발생하였습니다.");
        }
    }
}
