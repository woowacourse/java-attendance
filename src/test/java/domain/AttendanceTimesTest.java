package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimesTest {

    @Test
    @DisplayName("AttendanceTimes 생성 성공")
    void createAttendanceTimesTest() {
        // given
        List<AttendanceTime> attendanceLog = createAttendanceLog();

        // when, then
        assertThatCode(() -> AttendanceTimes.of(attendanceLog))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("해당 날짜의 출석이 이미 존재하는지 반환")
    void containsAttendanceInAttendancesTest() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());

        // when
        boolean b1 = attendanceTimes.contains(
                LocalDate.of(2024, 12, 10)
        );
        boolean b2 = attendanceTimes.contains(
                LocalDate.of(2024, 12, 3)
        );

        // then
        assertThat(b1).isTrue();
        assertThat(b2).isFalse();
    }

    @Test
    @DisplayName("출석 기록 추가")
    void addAttendanceTest() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());
        AttendanceTime attendanceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 20),
                LocalTime.of(11, 20)
        );

        // when, then
        assertThatCode(() -> attendanceTimes.addAttendance(attendanceTime))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("원하는 날짜의 출석을 확인")
    void readAttendanceInAttendancesTest() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());

        // when
        LocalDateTime attendanceTime = attendanceTimes.readAttendance(
                LocalDate.of(2024, 12, 11)
        );

        // then
        LocalDateTime expected = LocalDateTime.of(
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        );
        assertThat(attendanceTime).isEqualTo(expected);
    }

    @Test
    @DisplayName("원하는 날짜의 출석을 수정")
    void modifyAttendanceInAttendancesTest() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());
        LocalDate date = LocalDate.of(2024, 12, 11);
        LocalTime time = LocalTime.of(11, 10);

        AttendanceTime previous = AttendanceTime.of( // createAttendanceLog() 에서 확인 가능
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        );

        // when
        Optional<AttendanceTime> optionalAttendanceTime = attendanceTimes.modifyAttendance(
                AttendanceTime.of(date, time)
        );

        // then
        assert optionalAttendanceTime.isPresent();
        assertThat(optionalAttendanceTime.get()).isEqualTo(previous);

        LocalDateTime modified = attendanceTimes.readAttendance(date);
        assertThat(modified).isEqualTo(LocalDateTime.of(date, time));
    }

    @Test
    @DisplayName("출석 횟수 반환")
    void countAttendanceTest() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());
        LocalDate today = LocalDate.of(2024, 12, 13);

        // when
        int count = attendanceTimes.countAttendanceBeforeDate(today);

        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("지각 횟수 반환")
    void countLateTest() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());
        LocalDate today = LocalDate.of(2024, 12, 13);

        // when
        int count = attendanceTimes.countLateBeforeDate(today);

        // then
        assertThat(count).isEqualTo(1);
    }

    private List<AttendanceTime> createAttendanceLog() {
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 12),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 6)
        );
        return List.of(attendanceTime1,
                attendanceTime2,
                attendanceTime3,
                attendanceTime4);
    }
}