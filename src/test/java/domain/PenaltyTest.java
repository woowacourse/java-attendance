package domain;

import static org.assertj.core.api.Assertions.*;

import dto.AttendanceCount;
import dto.InitialInformation;
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

    LocalDateTime attendanceDayOf2 = LocalDateTime.of(2024, 12, 2, 13, 0);
    LocalDateTime attendanceDayOf5 = LocalDateTime.of(2024, 12, 5, 10, 0);
    LocalDateTime attendanceDayOf6 = LocalDateTime.of(2024, 12, 6, 10, 0);
    LocalDateTime attendanceDayOf9 = LocalDateTime.of(2024, 12, 9, 13, 0);
    LocalDateTime attendanceDayOf10 = LocalDateTime.of(2024, 12, 10, 10, 0);
    LocalDateTime attendanceDayOf11 = LocalDateTime.of(2024, 12, 11, 10, 0);

    LocalDateTime lateDayOf3 = LocalDateTime.of(2024, 12, 3, 10, 15);
    LocalDateTime lateDayOf4 = LocalDateTime.of(2024, 12, 4, 10, 15);
    LocalDateTime lateDayOf5 = LocalDateTime.of(2024, 12, 5, 10, 15);

    @BeforeEach
    void setUp() {
        attendanceRecord = new AttendanceRecord();

        Map<CrewName, AttendanceRecord> testData = new HashMap<>();
        testData.put(mimi, attendanceRecord);

        InitialInformation initialInformation = new InitialInformation(testData);
        attendanceBook = new AttendanceBook(initialInformation);
    }

    @DisplayName("결석이 5회를 초과하는 경우 제적에 처한다.")
    @Test
    void test1() {
        addAttendanceToBook(mimi,
                attendanceDayOf2,
                lateDayOf3,
                attendanceDayOf10);
        // 7, 8 => 주말
        // 4, 5, 6, 9, 11, 12 => 결석

        AttendanceCount attendanceCount = attendanceBook.findAttendanceHistoryUntilYesterday(mimi)
                .attendanceCount();
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.EXPULSION);
    }

    @DisplayName("결석이 3회 이상인 경우 면담에 처한다.")
    @Test
    void test2() {
        addAttendanceToBook(mimi,
                attendanceDayOf2,
                lateDayOf3,
                lateDayOf4,
                attendanceDayOf5,
                attendanceDayOf6,
                attendanceDayOf10);
        // 7, 8 => 주말
        // 9, 11, 12 => 결석

        AttendanceCount attendanceCount = attendanceBook.findAttendanceHistoryUntilYesterday(mimi)
                .attendanceCount();
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.COUNSELING);
    }

    @DisplayName("결석이 2회 이상인 경우 경고에 처한다.")
    @Test
    void test3() {
        addAttendanceToBook(mimi,
                attendanceDayOf2,
                lateDayOf3,
                lateDayOf4,
                attendanceDayOf5,
                attendanceDayOf6,
                attendanceDayOf9,
                attendanceDayOf10
        );
        // 7, 8 => 주말
        // 11, 12 => 결석

        AttendanceCount attendanceCount = attendanceBook.findAttendanceHistoryUntilYesterday(mimi)
                .attendanceCount();
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.WARNING);
    }

    @DisplayName("결석이 1회 이하인 경우 어떠한 페널티도 주어지지 않는다.")
    @Test
    void test5() {
        addAttendanceToBook(mimi,
                attendanceDayOf2,
                lateDayOf3,
                lateDayOf4,
                attendanceDayOf5,
                attendanceDayOf6,
                attendanceDayOf9,
                attendanceDayOf10,
                attendanceDayOf11
        );
        // 7, 8 => 주말
        // 12 => 결석

        AttendanceCount attendanceCount = attendanceBook.findAttendanceHistoryUntilYesterday(mimi)
                .attendanceCount();
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.NONE);
    }

    @DisplayName("지각 3회는 결석 1회로 간주한다.")
    @Test
    void test4() {
        addAttendanceToBook(mimi,
                attendanceDayOf2,
                lateDayOf3,
                lateDayOf4,
                lateDayOf5,
                attendanceDayOf6,
                attendanceDayOf9,
                attendanceDayOf10);
        // 7, 8 => 주말
        // 11, 12 => 결석
        // 3, 4, 5 => 지각 => 결석 1회 간주, 총 결석 3회

        AttendanceCount attendanceCount = attendanceBook.findAttendanceHistoryUntilYesterday(mimi)
                .attendanceCount();
        assertThat(Penalty.from(attendanceCount)).isEqualTo(Penalty.COUNSELING);
    }

    void addAttendanceToBook(CrewName crewName, LocalDateTime... localDateTimes) {
        for (LocalDateTime localDateTime : localDateTimes) {
            attendanceBook.addAttendance(crewName, new Attendance(localDateTime));
        }
    }
}
