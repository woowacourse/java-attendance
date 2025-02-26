package domain;

import static org.assertj.core.api.Assertions.*;

import dto.InitialInfo;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    AttendanceBook attendanceBook;
    AttendanceRecord attendanceRecord;
    CrewName mimi = new CrewName("미미");
    int day = 10;
    Attendance dayOfTenAttendance = new Attendance(LocalDateTime.of(2024, 12, day, 9, 59));

    @BeforeEach
    void setUp() {
        attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(dayOfTenAttendance);

        Map<CrewName, AttendanceRecord> testData = new HashMap<>();
        testData.put(mimi, attendanceRecord);

        InitialInfo initialInfo = new InitialInfo(testData);
        attendanceBook = new AttendanceBook(initialInfo);
    }

    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    @Test
    void test1() {
        Attendance todayAttendance = new Attendance(LocalDateTime.of(2024, 12, 13, 9, 59));

        Attendance savedAttendance = attendanceBook.addAttendance(mimi, todayAttendance);

        assertThat(todayAttendance).isEqualTo(savedAttendance);
    }

    @DisplayName("출석부에 존재하지 않는 닉네임일 경우 예외가 발생한다.")
    @Test
    void test2() {
        CrewName crewName = new CrewName("밍트");

        assertThatThrownBy(() -> attendanceBook.findAttendanceRecordBy(crewName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("출석 저장 시, 출석부에 존재하지 않는 닉네임일 경우 예외가 발생한다.")
    @Test
    void test3() {
        CrewName crewName = new CrewName("밍트");
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 13, 9, 59));

        assertThatThrownBy(() -> attendanceBook.addAttendance(crewName, attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("출석 저장 시, 이미 해당 날짜에 출석 기록이 있는 경우 예외가 발생한다.")
    @Test
    void test4() {
        CrewName crewName = new CrewName("미미");
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 10, 10, 31));

        assertThatThrownBy(() -> attendanceBook.addAttendance(crewName, attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("닉네임, 수정하려는 날짜, 등교 시간을 입력하여 출석 기록을 수정할 수 있다.")
    @Test
    void test5() {
        Attendance expectedAttendance = new Attendance(LocalDateTime.of(2024, 12, day, 10, 6));

        attendanceBook.modify(mimi, expectedAttendance);

        assertThat(attendanceBook.findAttendanceRecordBy(mimi)
                .contains(expectedAttendance))
                .isTrue();
    }

    @DisplayName("출석 수정 시, 출석부에 존재하지 않는 닉네임일 경우 예외가 발생한다.")
    @Test
    void test6() {
        CrewName crewName = new CrewName("밍트");
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 13, 9, 59));

        assertThatThrownBy(() -> attendanceBook.modify(crewName, attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
