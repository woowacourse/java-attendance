package domain;

import static org.assertj.core.api.Assertions.*;

import dto.AttendanceCount;
import dto.AttendanceHistory;
import dto.InitialInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PenaltyTest {
    AttendanceBook attendanceBook;
    AttendanceRecord attendanceRecord;
    CrewName mimi = new CrewName("미미");
    int day = 10;
    Attendance dayOfTenAttendance = new Attendance(LocalDateTime.of(2024, 12, day, 10, 0));

    @BeforeEach
    void setUp() {
        attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(dayOfTenAttendance);

        Map<CrewName, AttendanceRecord> testData = new HashMap<>();
        testData.put(mimi, attendanceRecord);

        InitialInfo initialInfo = new InitialInfo(testData);
        attendanceBook = new AttendanceBook(initialInfo);
    }

    @DisplayName("결석이 5회를 초과하는 경우 제적에 처한다.")
    @Test
    void test1() {
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 3, 10, 15)));
        // 4, 5, 6 => 결석
        // 7, 8 => 주말
        // 9 => 결석
        // 10 => 이미 존재
        // 11, 12 => 결석
        LocalDate yesterday = LocalDate.of(2024, 12, 12);

        AttendanceCount attendanceCount = attendanceBook.findCountUntil(mimi, yesterday);
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.EXPULSION);
    }

    @DisplayName("결석이 3회 이상인 경우 면담에 처한다.")
    @Test
    void test2() {
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 3, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 4, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)));
        // 7, 8 => 주말
        // 9 => 결석
        // 10 => 이미 존재
        // 11, 12 => 결석
        LocalDate yesterday = LocalDate.of(2024, 12, 12);

        AttendanceCount attendanceCount = attendanceBook.findCountUntil(mimi, yesterday);
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.COUNSELING);
    }

    @DisplayName("결석이 2회 이상인 경우 경고에 처한다.")
    @Test
    void test3() {
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 3, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 4, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)));
        // 7, 8 => 주말
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 9, 13, 0)));
        // 10 => 이미 존재
        // 11, 12 => 결석
        LocalDate yesterday = LocalDate.of(2024, 12, 12);

        AttendanceCount attendanceCount = attendanceBook.findCountUntil(mimi, yesterday);
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.WARNING);
    }

    @DisplayName("지각 3회는 결석 1회로 간주한다.")
    @Test
    void test4() {
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 3, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 4, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 5, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)));
        // 7, 8 => 주말
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 9, 13, 0)));
        // 10 => 이미 존재
        // 11, 12 => 결석
        // 지각 3회 => 결석 1회 간주, 총 결석 3회
        LocalDate yesterday = LocalDate.of(2024, 12, 12);

        AttendanceCount attendanceCount = attendanceBook.findCountUntil(mimi, yesterday);
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.COUNSELING);
    }
}
