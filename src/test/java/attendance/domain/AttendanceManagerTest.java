package attendance.domain;

import attendance.utility.DateGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static attendance.domain.AttendanceState.ABSENCE;
import static attendance.domain.AttendanceState.ATTENDANCE;
import static attendance.domain.AttendanceState.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AttendanceManagerTest {

    private final DateGenerator dateGenerator = new MockingDateGenerator();
    private final AttendanceManager attendanceManager = new AttendanceManager();

    @ParameterizedTest(name = "출석 시간: {0} | 출석 상황 결과 : {1}")
    @MethodSource("기본_출석_데이터")
    void 출석_데이터로_출석_체크한다(LocalTime time, AttendanceState expected) {
        // given
        String nickname = "비타";
        LocalDate nowDate = dateGenerator.generateNow();

        Attendances attendances = new Attendances();
        attendances.addAttendance(LocalDateTime.of(nowDate, LocalTime.MAX));

        attendanceManager.addCrew(nickname, attendances);

        // when
        LocalDateTime dateTime = LocalDateTime.of(nowDate, time);
        Attendance result = attendanceManager.processAttendanceCheck(dateTime, nickname);

        // then
        assertThat(result.getDateTime()).isEqualTo(dateTime);
        assertThat(result.getState()).isEqualTo(expected);
    }

    @Test
    void 이미_출석한_경우_다시_출석한_경우_예외가_발생한다() {
        // given
        String nickname = "비타";
        LocalDate nowDate = dateGenerator.generateNow();

        Attendances attendances = new Attendances();
        attendances.addAttendance(LocalDateTime.of(nowDate, LocalTime.MIDNIGHT));

        attendanceManager.addCrew(nickname, attendances);

        // when
        LocalDateTime dateTime = LocalDateTime.of(nowDate, LocalTime.MIDNIGHT);

        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceManager.processAttendanceCheck(dateTime, nickname))
                .withMessage("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
    }

    @ParameterizedTest(name = "변경 시간: {0} | 출석 상황 결과 : {1}")
    @MethodSource("기본_출석_데이터")
    void 출석_데이터를_수정한다(LocalTime time, AttendanceState expected) {
        // given
        String nickname = "비타";
        LocalDate nowDate = dateGenerator.generateNow();

        Attendances attendances = new Attendances();
        attendances.addAttendance(LocalDateTime.of(nowDate, LocalTime.MAX));

        attendanceManager.addCrew(nickname, attendances);

        // when
        LocalDateTime updateDateTime = LocalDateTime.of(nowDate, time);
        List<Attendance> result = attendanceManager.processAttendanceUpdate(updateDateTime, nickname);

        // then
        assertThat(result.getLast().getDateTime().toLocalTime()).isEqualTo(time);
        assertThat(result.getLast().getState()).isEqualTo(expected);
    }

    @Test
    void 수정할_날짜에_데이터가_없는_경우_에러가_발생한다() {
        // given
        String nickname = "이든";
        attendanceManager.addCrew(nickname, new Attendances());

        LocalDateTime updateDateTime = LocalDateTime.MIN;

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceManager.processAttendanceUpdate(updateDateTime, nickname))
                .withMessage("[ERROR] 수정하려는 날짜는 출석할 수 없습니다.");
    }

    @Test
    void 전날까지의_출석_기록을_반환한다() {
        // given
        String nickname = "이든";

        Attendances attendances = new Attendances();
        LocalDate nowDate = dateGenerator.generateNow();
        int dayOfMonth = nowDate.getDayOfMonth();

        for (int day = 1; day <= dayOfMonth; day++) {
            LocalDate date = nowDate.withDayOfMonth(day);
            attendances.addAttendance(LocalDateTime.of(date, LocalTime.MIDNIGHT));
        }

        attendanceManager.addCrew(nickname, attendances);

        // when
        List<Attendance> result = attendanceManager.getAttendanceRecord(nowDate, nickname);

        // then
        assertThat(result.size()).isEqualTo(dayOfMonth - 1);
    }

    @Test
    void 출결_상태를_반환한다() {
        // given
        String nickname = "이든";
        LocalDate nowDate = dateGenerator.generateNow();

        Attendances attendances = new Attendances();
        attendances.addAttendance(LocalDateTime.of(nowDate, LocalTime.MIDNIGHT));

        attendanceManager.addCrew(nickname, attendances);

        // when
        AttendanceStatus result = attendanceManager.getAttendanceStatus(nowDate, nickname);

        // then
        assertThat(result.getStatus().size()).isEqualTo(3);
        assertThat(result.getRisk()).isEqualTo(AttendanceRisk.NONE);
    }

    @Test
    void 제적_위험자의_크루를_반환한다() {
        // given
        List<String> nicknames = List.of("레오", "랜디", "비타");
        List<LocalDateTime> dateTimes = new ArrayList<>();
        LocalDate nowDate = dateGenerator.generateNow();

        for (int day = 1; day < 8; day++) {
            LocalDate date = nowDate.withDayOfMonth(day);
            dateTimes.add(LocalDateTime.of(date, LocalTime.MAX));
        }

        for (String nickname : nicknames) {
            Attendances attendances = new Attendances();
            for (LocalDateTime dateTime : dateTimes) {
                attendances.addAttendance(dateTime);
            }
            attendanceManager.addCrew(nickname, attendances);
        }

        // when
        Map<String, AttendanceStatus> result = attendanceManager.getAttendanceRiskCrew(nowDate);

        // then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void 제적_위험자가_아닌_크루는_제외하고_반환한다() {
        // given
        String nickname = "이든";

        Attendances attendances = new Attendances();
        LocalDate nowDate = dateGenerator.generateNow();
        attendances.addAttendance(LocalDateTime.of(nowDate, LocalTime.MIDNIGHT));

        attendanceManager.addCrew(nickname, attendances);

        // when
        Map<String, AttendanceStatus> result = attendanceManager.getAttendanceRiskCrew(nowDate);

        // then
        assertThat(result).doesNotContainKey(nickname);
    }

    @Test
    void 제적_위험자_목록을_위험도_순서로_정렬한다() {
        // given
        LocalDate nowDate = dateGenerator.generateNow();
        List<LocalDateTime> leoDateTimes = List.of(
                LocalDateTime.of(nowDate.withDayOfMonth(1), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(2), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(3), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(4), LocalTime.MAX)
        );

        List<LocalDateTime> randiDateTimes = List.of(
                LocalDateTime.of(nowDate.withDayOfMonth(1), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(2), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(3), LocalTime.MAX)
        );

        addAttendancesForCrew(leoDateTimes, "레오");
        addAttendancesForCrew(randiDateTimes, "랜디");

        // when
        Map<String, AttendanceStatus> result = attendanceManager.getAttendanceRiskCrew(nowDate);

        // then
        List<String> sortedKeys = new ArrayList<>(result.keySet());
        assertThat(sortedKeys).containsExactly("레오", "랜디");
    }

    @Test
    void 제적_위험자_목록을_출석_수치로_내림차순_정렬한다() {
        // given
        LocalDate nowDate = dateGenerator.generateNow();
        List<LocalDateTime> leoDateTimes = List.of(
                LocalDateTime.of(nowDate.withDayOfMonth(1), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(2), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(3), LocalTime.of(10, 0))
        );

        List<LocalDateTime> randiDateTimes = List.of(
                LocalDateTime.of(nowDate.withDayOfMonth(1), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(2), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(3), LocalTime.of(10, 6))
        );

        addAttendancesForCrew(leoDateTimes, "레오");
        addAttendancesForCrew(randiDateTimes, "랜디");

        // when
        Map<String, AttendanceStatus> result = attendanceManager.getAttendanceRiskCrew(nowDate);

        // then
        List<String> sortedKeys = new ArrayList<>(result.keySet());
        assertThat(sortedKeys).containsExactly("랜디", "레오");
    }

    private void addAttendancesForCrew(final List<LocalDateTime> dateTimes, final String nickname) {
        Attendances attendances = new Attendances();
        for (LocalDateTime dateTime : dateTimes) {
            attendances.addAttendance(dateTime);
        }
        attendanceManager.addCrew(nickname, attendances);
    }

    @Test
    void 제적_위험자_목록을_이름_오름차순으로_정렬한다() {
        // given
        List<String> nicknames = List.of("레오", "랜디", "비타");

        LocalDate nowDate = dateGenerator.generateNow();
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(nowDate.withDayOfMonth(1), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(2), LocalTime.MAX),
                LocalDateTime.of(nowDate.withDayOfMonth(3), LocalTime.MAX)
        );

        for (String nickname : nicknames) {
            Attendances attendances = new Attendances();
            for (LocalDateTime dateTime : dateTimes) {
                attendances.addAttendance(dateTime);
            }
            attendanceManager.addCrew(nickname, attendances);
        }

        // when
        Map<String, AttendanceStatus> result = attendanceManager.getAttendanceRiskCrew(nowDate);

        // then
        List<String> sortedKeys = new ArrayList<>(result.keySet());
        assertThat(sortedKeys).containsExactly("랜디", "레오", "비타");
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

    static Stream<Arguments> 기본_출석_데이터() {
        return Stream.of(
                Arguments.of(LocalTime.of(10, 5), ATTENDANCE),
                Arguments.of(LocalTime.of(10, 30), LATE),
                Arguments.of(LocalTime.MAX, ABSENCE)
        );
    }

    static class MockingDateGenerator implements DateGenerator {

        @Override
        public LocalDate generateNow() {
            return LocalDate.of(2024, 12, 10);
        }
    }
}
