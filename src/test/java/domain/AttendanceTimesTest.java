package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
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
    @DisplayName("이미 기록이 존재하는 상황에서 출석 기록 추가 시 예외 발생")
    void addAttendanceTwiceThrowException() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());
        AttendanceTime attendanceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 20),
                LocalTime.of(11, 20)
        );
        AttendanceTime sameDateTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 20),
                LocalTime.of(11, 30)
        );
        attendanceTimes.addAttendance(attendanceTime);

        // when, then
        assertThatThrownBy(() -> attendanceTimes.addAttendance(sameDateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜를 입력 시 해당 날짜 이전의 AttendanceTime에 대한 리스트 반환")
    void readListOfAttendanceTest() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());
        LocalDate today = LocalDate.of(2024, 12, 13);

        // when
        List<AttendanceTime> log = attendanceTimes.readAttendance(today);

        // then
        assertThat(log).hasSize(3);
    }

    @Test
    @DisplayName("반환된 출석 기록들에 대해 정렬 확인")
    void readListOfAttendanceSortedTest() {
        // given
        AttendanceTimes attendanceTimes = AttendanceTimes.of(createAttendanceLog());
        LocalDate today = LocalDate.of(2024, 12, 13);

        // when
        List<AttendanceTime> log = attendanceTimes.readAttendance(today);

        // then
        int size = log.size();
        for (int i = 0; i < size - 1; i++) {
            AttendanceTime smaller = log.get(i);
            AttendanceTime bigger = log.get(i + 1);
            assertThat(smaller.compareTo(bigger)).isLessThan(0);
        }
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
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 12),
                LocalTime.of(10, 5)
        );
        return List.of(attendanceTime1,
                attendanceTime2,
                attendanceTime3,
                attendanceTime4);
    }
}