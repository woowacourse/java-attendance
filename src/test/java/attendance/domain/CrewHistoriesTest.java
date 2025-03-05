package attendance.domain;

import static attendance.fixture.TestFixture.makeAbsenceMonday;
import static attendance.fixture.TestFixture.makeAttendanceExceptMonday;
import static attendance.fixture.TestFixture.makeAttendanceMonday;
import static attendance.fixture.TestFixture.makeCrewHistory;
import static attendance.fixture.TestFixture.makeDateTime;
import static attendance.fixture.TestFixture.makeDecemberDate;
import static attendance.fixture.TestFixture.makeDefaultAttendanceTime;
import static attendance.fixture.TestFixture.makeTardinessExceptMonday;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewHistoriesTest {

    private CrewHistories crewHistories;

    @BeforeEach
    void setUp() {
        crewHistories = new CrewHistories(new HashMap<>());
    }

    @Test
    void 크루의_출석_기록을_저장한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime yesterdayAttendanceTime = makeDefaultAttendanceTime();
        crewHistories.addHistory(nickname, yesterdayAttendanceTime);

        LocalDateTime todayAttendanceTime = makeDateTime(4, 10, 0);

        // When
        crewHistories.addHistory(nickname, todayAttendanceTime);

        // Then
        assertThat(crewHistories).isEqualTo(
                new CrewHistories(Map.of(nickname, makeCrewHistory(yesterdayAttendanceTime, todayAttendanceTime))));
    }

    @Test
    void 크루의_출석_기록이_존재하지_않은_경우_새롭게_만들어_저장한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime attendanceTime = makeDefaultAttendanceTime();

        // When
        crewHistories.addHistory(nickname, attendanceTime);

        // Then
        assertThat(crewHistories).isEqualTo(new CrewHistories(Map.of(nickname, makeCrewHistory(attendanceTime))));
    }

    @Test
    void 닉네임으로_출석_기록이_존재하지_않는지_조회한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime yesterdayAttendanceTime = makeDefaultAttendanceTime();
        crewHistories.addHistory(nickname, yesterdayAttendanceTime);
        LocalDate today = makeDecemberDate(4);

        // When & Then
        assertThatCode(() -> crewHistories.validateHistoryNotExists(nickname, today))
                .doesNotThrowAnyException();
    }

    @Test
    void 이미_출석_기록이_존재하는_경우_예외가_발생한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime yesterdayAttendanceTime = makeDefaultAttendanceTime();
        crewHistories.addHistory(nickname, yesterdayAttendanceTime);
        LocalDate yesterday = LocalDate.from(yesterdayAttendanceTime);

        // When & Then
        assertThatThrownBy(() -> crewHistories.validateHistoryNotExists(nickname, yesterday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이미 출석했습니다. 수정 기능을 이용해주세요.");
    }

    @Test
    void 닉네임으로_출석_기록이_존재하는지_조회한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime yesterdayAttendanceTime = makeDateTime(3, 10, 0);
        crewHistories.addHistory(nickname, yesterdayAttendanceTime);
        LocalDate today = makeDecemberDate(3);

        // When & Then
        assertThatCode(() -> crewHistories.validateHistoryExists(nickname, today))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석_기록이_존재하지_않는_경우_예외가_발생한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime attendanceTime = makeDateTime(3, 10, 0);
        crewHistories.addHistory(nickname, attendanceTime);
        LocalDate modifyingDate = makeDecemberDate(2);

        // When & Then
        assertThatThrownBy(() -> crewHistories.validateHistoryExists(nickname, modifyingDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 출석 기록이 존재하지 않습니다.");
    }

    @Test
    void 존재하지_않은_닉네임으로_조회하는_경우_예외가_발생한다() {
        // Given
        String nickname = "밍트";

        // When & Then
        assertThatThrownBy(() -> crewHistories.validateKeyExists(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 출석_기록을_수정한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime attendanceTime = makeDateTime(3, 10, 0);
        crewHistories.addHistory(nickname, attendanceTime);
        LocalDateTime modifyingTime = makeDateTime(3, 11, 0);
        LocalDate nowDate = makeDecemberDate(4);

        // When
        LocalDateTime previousHistory = crewHistories.modify(nickname, modifyingTime, nowDate);

        // Then
        assertAll(
                () -> assertThat(previousHistory).isEqualTo(attendanceTime),
                () -> assertThat(crewHistories).isEqualTo(
                        new CrewHistories(Map.of(nickname, makeCrewHistory(modifyingTime))))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void 과거_날짜가_아니면_출석_기록을_수정할_수_없다(final int day) {
        String nickname = "밍트";
        LocalDateTime attendanceTime = makeDateTime(3, 10, 0);
        crewHistories.addHistory(nickname, attendanceTime);
        LocalDateTime modifyingTime = makeDateTime(3, 11, 0);
        LocalDate nowDate = makeDecemberDate(day);

        // When & Then
        assertThatThrownBy(() -> crewHistories.modify(nickname, modifyingTime, nowDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("[ERROR] 과거의 날짜만 가능합니다.");
    }

    @Test
    void 하나의_출석_기록을_조회한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime attendanceTime = makeDateTime(3, 10, 0);
        crewHistories.addHistory(nickname, attendanceTime);

        // When
        Optional<LocalDateTime> history = crewHistories.findHistoryOfDate(nickname, LocalDate.from(attendanceTime));

        // Then
        assertThat(history.get()).isEqualTo(attendanceTime);
    }

    @Test
    void 출석_기록이_존재하지_않은_경우_빈값을_반환한다() {
        // Given
        String nickname = "밍트";
        LocalDateTime yesterdayAttendanceTime = makeDateTime(3, 10, 0);
        crewHistories.addHistory(nickname, yesterdayAttendanceTime);
        LocalDate today = makeDecemberDate(4);

        // When
        Optional<LocalDateTime> history = crewHistories.findHistoryOfDate(nickname, today);

        // Then
        assertThat(history.isEmpty()).isTrue();
    }

    @Test
    void 출석_기록을_조회한다() {
        // Given
        String nickname = "밍트";
        crewHistories.addHistory(nickname, makeAttendanceExceptMonday(2));
        crewHistories.addHistory(nickname, makeAttendanceMonday(3));
        crewHistories.addHistory(nickname, makeAttendanceMonday(4));
        CrewHistory expected = makeCrewHistory(makeAttendanceExceptMonday(2), makeAttendanceMonday(3),
                makeAttendanceMonday(4));

        // When
        CrewHistory history = crewHistories.findHistory(nickname);

        // Then
        assertThat(history).isEqualTo(expected);
    }

    @Test
    void 닉네임별_크루_출석_상태별_횟수를_조회한다() {
        // Given
        LocalDate nowDate = makeDecemberDate(5);
        CrewHistory crewHistory = makeCrewHistory(makeAbsenceMonday(2), makeAttendanceExceptMonday(3),
                makeTardinessExceptMonday(4));
        String nickname = "밍트";
        crewHistories = new CrewHistories(Map.of(nickname, crewHistory));
        CampusScheduler campusScheduler = new CampusScheduler();
        AttendanceCounter counter = new AttendanceCounter(
                Map.of(AttendanceState.ABSENCE, 1, AttendanceState.ATTENDANCE, 1, AttendanceState.TARDINESS, 1)
        );

        // When
        Map<String, AttendanceCounter> history = crewHistories.makeAttendanceCounterByNickname(
                nowDate, campusScheduler);

        // Then
        assertAll(
                () -> assertThat(history).containsKey(nickname),
                () -> assertThat(history).containsValue(counter)
        );
    }
}
