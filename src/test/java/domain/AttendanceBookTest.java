package domain;

import static org.assertj.core.api.Assertions.*;

import dto.AttendanceCount;
import dto.AttendanceHistory;
import dto.InitialInfo;
import dto.ModifyResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
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
        Attendance newAttendance = new Attendance(LocalDateTime.of(2024, 12, day, 10, 6));

        attendanceBook.modify(mimi, newAttendance);

        assertThat(attendanceBook.findAttendanceRecordBy(mimi)
                .contains(newAttendance))
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

    @DisplayName("출석 수정 시, 기존 출석 기록과 업데이트된 출석 기록을 모두 확인할 수 있다.")
    @Test
    void test7() {
        Attendance newAttendance = new Attendance(LocalDateTime.of(2024, 12, day, 10, 6));

        ModifyResult modifyResult = attendanceBook.modify(mimi, newAttendance);

        assertThat(modifyResult.originalAttendance()).isEqualTo(dayOfTenAttendance);
        assertThat(modifyResult.modifiedAttendance()).isEqualTo(newAttendance);
    }

    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.")
    @Test
    void test8() {
        Attendance firstAttendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.addAttendance(mimi, firstAttendance);
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 3, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 4, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 5, 10, 15)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)));
        attendanceBook.addAttendance(mimi, new Attendance(LocalDateTime.of(2024, 12, 9, 13, 0)));
        // 10 이미 존재
        // 11, 12 => 결석
        // TODO : 결석 간주 시간 상수 분리?
        Attendance expectedLastAttendance = new Attendance(LocalDateTime.of(2024, 12, 12, 15, 0));
        // 결석 2, 지각 3, 출석 4

        // TODO : 감싸기?
        LocalDate yesterday = LocalDate.of(2024, 12, 12);
        AttendanceHistory attendanceHistory = attendanceBook.findAttendanceHistoryUntil(mimi, yesterday);

        AttendanceCount attendanceCount = attendanceHistory.attendanceCount();
        List<Attendance> sortedAttendance = attendanceHistory.attendanceLog().sortedValue();
        assertThat(attendanceCount.absentCount()).isEqualTo(2);
        assertThat(attendanceCount.lateCount()).isEqualTo(3);
        assertThat(attendanceCount.attendCount()).isEqualTo(4);
        assertThat(sortedAttendance.getFirst()).isEqualTo(firstAttendance);
        assertThat(sortedAttendance.getLast()).isEqualTo(expectedLastAttendance);
    }
}
