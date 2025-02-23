package attendance.domain;

import attendance.utility.DateGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AttendanceManagerTest {

    private DateGenerator dateGenerator;
    private AttendanceManager attendanceManager;

    @BeforeEach
    void beforeEach() {
        dateGenerator = new MockingDateGenerator();
        attendanceManager = new AttendanceManager(new Holiday(), dateGenerator);
    }

    @Test
    void 크루가_추가될때_기본_출석이_생성된다() {
        // given
        String name = "랜디";
        Holiday holiday = new Holiday();
        AttendanceManager attendanceManager = new AttendanceManager(holiday, dateGenerator);

        // when
        attendanceManager.addCrew(name);

        // then
        Assertions.assertThat(attendanceManager.findCrewAttendance(name).size())
                .isEqualTo(22);
    }

    @ParameterizedTest(name = "출석 시간: {0} | 출석 상황 결과 : {1}")
    @MethodSource
    void 크루의_출석_데이터로_출석_체크한다(LocalTime time, AttendanceStatusType expected) {
        // given
        String nickname = "이든";
        attendanceManager.addCrew(nickname);

        LocalDate nowDate = dateGenerator.now();
        LocalDateTime dateTime = LocalDateTime.of(nowDate, time);

        // when
        Attendance result = attendanceManager.processAttendanceCheck(dateTime, nickname);

        // then
        assertThat(result.getDateTime()).isEqualTo(dateTime);
        assertThat(result.getStatus()).isEqualTo(expected);
    }

    @Test
    void 크루의_출석_데이터를_수정한다() {
        // given
        String nickname = "이든";
        attendanceManager.addCrew(nickname);

        LocalDateTime updateDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);

        // when
        Attendance result = attendanceManager.processAttendanceUpdate(updateDateTime, nickname);

        // then
        assertThat(result.getDateTime()).isEqualTo(updateDateTime);
        assertThat(result.getStatus()).isEqualTo(AttendanceStatusType.ATTENDANCE);
    }

    @Test
    void 출석할_날짜에_데이터가_없는_경우_에러가_발생한다() {
        // given
        String nickname = "이든";
        attendanceManager.addCrew(nickname);

        LocalDateTime updateDateTime = LocalDateTime.of(2024, 12, 1, 10, 0);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceManager.processAttendanceUpdate(updateDateTime, nickname))
                .withMessage("[ERROR] 수정하려는 날짜는 출석할 수 없습니다.");
    }

    @Test
    void 등록되지_않은_닉네임으로_출석시_에러가_발생한다() {
        // given
        String nickname = "이든";

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceManager.validateNicknameExists(nickname))
                .withMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    static Stream<Arguments> 크루의_출석_데이터로_출석_체크한다() {
        return Stream.of(
                Arguments.of(LocalTime.of(10, 5), AttendanceStatusType.ATTENDANCE),
                Arguments.of(LocalTime.of(10, 30), AttendanceStatusType.LATE),
                Arguments.of(LocalTime.MAX, AttendanceStatusType.EXPULSION)
        );
    }

    static class MockingDateGenerator implements DateGenerator {

        @Override
        public LocalDate now() {
            return LocalDate.of(2024, 12, 5);
        }
    }
}
