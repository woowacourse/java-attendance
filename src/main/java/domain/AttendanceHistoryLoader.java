package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceHistoryLoader {

    public AttendanceBook initializeAttendanceWith(FileReader fileReader) throws IOException {
        BufferedReader reader = new BufferedReader(fileReader);

        try {
            AttendanceBook attendanceBook = new AttendanceBook();
            skipHeader(reader);
            addAttendances(reader, attendanceBook);
            return attendanceBook;

        } catch (Exception e) {
            throw new IOException("[ERROR] 초기데이터 로드 중 오류가 발생하였습니다.");
        }
    }

    private void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private void addAttendances(BufferedReader reader, AttendanceBook attendanceBook) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            addAttendance(attendanceBook, line);
        }
    }

    private void addAttendance(AttendanceBook attendanceBook, String line) {
        String[] history = line.split(",");
        String nickname = history[0];

        String[] attendanceDateTime = history[1].trim().split(" ");
        String attendanceDate = attendanceDateTime[0];
        String attendanceTime = attendanceDateTime[1];

        Day day = new Day(LocalDate.parse(attendanceDate.trim()));
        Attendance attendance = new Attendance(day, LocalTime.parse(attendanceTime.trim()));
        attendanceBook.recordAttendance(nickname, attendance);
    }
}
