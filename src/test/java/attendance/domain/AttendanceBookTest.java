package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    private Crew crew = new Crew("Lemon");
    private AttendanceLog attendanceLog;
    private AttendanceBook attendanceBook;
    private Map<Crew, AttendanceLog> attendanceRecord = new HashMap<>();

    @BeforeEach
    void setup() {
        List<Attendance> sampleAttendances = new ArrayList<>();
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 3, 9, 50)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 4, 14, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 5, 10, 10)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 6, 9, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 9, 15, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 10, 18, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 11, 10, 15)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 12, 10, 10)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 13, 9, 30)));
        attendanceLog = new AttendanceLog(sampleAttendances);
        attendanceRecord.put(crew, attendanceLog);
        attendanceBook = new AttendanceBook(attendanceRecord);
    }

    @Test
    @DisplayName("닉네임과 날짜를 입력하면 출석이 등록된다.")
    void registerAttendanceTest() {
        //given
        Crew newCrew = new Crew("Meringue");
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 30);

        //when
        attendanceBook.registerAttendance(newCrew,newAttendanceDateTime);
        List<Attendance> attendanceLogs = attendanceBook.findAttendanceLogByCrew(newCrew).getAttendanceLog();

        //then
        Assertions.assertThat(attendanceLogs.getFirst().getAttendanceStatus()).isEqualTo("지각");
    }
}
