package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    private static Crew crew = new Crew("Lemon");;
    private static AttendanceLog attendanceLog;
    private static AttendanceBook attendanceBook;
    private static Map<Crew, AttendanceLog> attendanceRecord;

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
        attendanceRecord = new HashMap<>();
        attendanceRecord.put(crew, attendanceLog);
        attendanceBook = new AttendanceBook(attendanceRecord);
    }

    @Test
    @DisplayName("닉네임과 날짜를 입력하면 출석이 등록된다.")
    void registerAttendanceTest() {
        //given
        Crew newCrew = new Crew("Lemon");
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 30);

        //when
        Attendance attendance = attendanceBook.registerAttendance(newCrew, newAttendanceDateTime);

        //then
        Assertions.assertThat(attendance.getAttendanceStatus()).isEqualTo("지각");
        Assertions.assertThat(attendance.getAttendanceDate()).isEqualTo(LocalDate.of(2024,12,2));
        Assertions.assertThat(attendance.getAttendanceTime()).isEqualTo(LocalTime.of(13,30));
    }

    @Test
    @DisplayName("존재하지 않는 닉네임을 입력하면 예외가 발생한다")
    void registerNonExistNameAttendanceTest() {
        //given
        Crew newCrew = new Crew("Murang");
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 30);

        //expected
        Assertions.assertThatThrownBy(() -> attendanceBook.registerAttendance(newCrew, newAttendanceDateTime))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("등록되지 않는 크루입니다");
    }
}
