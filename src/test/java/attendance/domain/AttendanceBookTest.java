package attendance.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 26, 8, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12,  27,13, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 30, 12, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 31, 9, 45)));
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
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(2024, 12, 16, 13, 30);

        //when
        Attendance attendance = attendanceBook.registerAttendance(newCrew, newAttendanceDateTime);

        //then
        assertThat(attendance.getAttendanceStatus()).isEqualTo("지각");
        assertThat(attendance.getAttendanceDate()).isEqualTo(LocalDate.of(2024,12,16));
        assertThat(attendance.getAttendanceTime()).isEqualTo(LocalTime.of(13,30));
    }

    @Test
    @DisplayName("존재하지 않는 닉네임을 입력하면 예외가 발생한다")
    void registerNonExistNameAttendanceTest() {
        //given
        Crew newCrew = new Crew("Murang");
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 30);

        //expected
        assertThatThrownBy(() -> attendanceBook.registerAttendance(newCrew, newAttendanceDateTime))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("등록되지 않는 크루입니다");
    }

    @Test
    @DisplayName("닉네임, 수정하려는 날짜, 등교 시간을 입력하여 기록을 수정할 수 있다")
    void modifyAttendanceTest() {
        //given
        Crew inputCrew = new Crew("Lemon");
        int modifyAttendanceDate = 3;
        String modifyAttendanceTime = "10:40";
        String format = String.format("2024-12-%02d %s", modifyAttendanceDate, modifyAttendanceTime);
        DateTimeFormatter dfm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime modifyDateTime = LocalDateTime.parse(format, dfm);

        //when
        List<Attendance> attendances = attendanceBook.modifyAttendance(crew, modifyDateTime);
        Attendance oldAttendance = attendances.getFirst();
        Attendance newAttendance = attendances.getLast();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThat(oldAttendance.getAttendanceDate()).isEqualTo(LocalDate.of(2024,12,3));
            assertThat(oldAttendance.getAttendanceTime()).isEqualTo(LocalTime.of(9, 50));
            assertThat(oldAttendance.getAttendanceStatus()).isEqualTo("출석");

            assertThat(newAttendance.getAttendanceTime()).isEqualTo(LocalTime.of(10, 40));
            assertThat(newAttendance.getAttendanceStatus()).isEqualTo("결석");
        });
    }

    @Test
    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다")
    void checkAttendancesRecord() {
        //given
        Crew inputCrew = new Crew("Lemon");
        List<Attendance> attendances = attendanceBook.checkAttendancesRecord(inputCrew);
        LocalDate nowDate = LocalDate.of(2024,12,14).minusDays(1);
        // expected
        Assertions.assertThat(attendances)
            .isNotEmpty(); // 리스트가 비어있지 않은지 검증
        //expected
        Assertions.assertThat(attendances.getLast().getAttendanceDate()).isEqualTo(nowDate);
    }
}
