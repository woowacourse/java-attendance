package attendance.domain;

import static attendance.fixture.TestFixture.makeAbsent;
import static attendance.fixture.TestFixture.makeAttendance;
import static attendance.fixture.TestFixture.makeDefaultTime;
import static attendance.fixture.TestFixture.makeLate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewHistoryTest {

    @DisplayName("출석 데이터를 초기화한다")
    @Test
    void loadHistoryTest() {
        // Given
        CrewHistory crewHistory = new CrewHistory(new HashMap<>());
        LocalDateTime attendanceTime = makeAttendance(3);

        // When
        crewHistory.loadHistory(attendanceTime);

        // Then
        assertThat(crewHistory.getAttendance()).containsEntry(LocalDate.from(attendanceTime), attendanceTime);
    }

    @DisplayName("출석을 한다")
    @Test
    void checkAttendanceTest() {
        // Given
        CrewHistory crewHistory = new CrewHistory(new HashMap<>());
        LocalDateTime attendanceTime = makeAttendance(3);

        // When
        crewHistory.attend(attendanceTime);

        // Then
        assertThat(crewHistory.getAttendance()).containsEntry(LocalDate.from(attendanceTime), attendanceTime);
    }

    @DisplayName("이미 출석한 경우 예외가 발생한다")
    @Test
    void alreadyAttendanceTest() {
        // Given
        CrewHistory crewHistory = new CrewHistory(new HashMap<>());
        LocalDateTime attendanceTime = makeAttendance(3);
        crewHistory.attend(attendanceTime);

        // When & Then
        assertThatThrownBy(() -> crewHistory.attend(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이미 출석했습니다. 수정 기능을 이용해주세요.");
    }


    @DisplayName("출석을 수정하고 이전의 출석 기록을 반환한다")
    @Test
    void modifyAttendanceTest() {
        // Given
        CrewHistory crewHistory = new CrewHistory(new HashMap<>());
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 9, 0);
        crewHistory.attend(attendanceTime);

        LocalDateTime modifyTime = LocalDateTime.of(2024, 12, 3, 9, 50);
        LocalDate todayDate = LocalDate.now();

        // When
        LocalDateTime previousDateTime = crewHistory.modify(modifyTime, todayDate);

        // Then
        assertAll(
                () -> assertThat(crewHistory.getAttendance()).containsEntry(LocalDate.from(modifyTime), modifyTime),
                () -> assertThat(previousDateTime).isEqualTo(attendanceTime)
        );
    }

    @DisplayName("오늘 또는 미래의 수정 일자일 경우 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-03",
            "2024-12-04"
    })
    void invalidModifyDateTest(LocalDate todayDate) {
        // Given
        CrewHistory crewHistory = new CrewHistory(new HashMap<>());
        LocalDateTime modifyTime = makeAttendance(4);

        // When & Then
        assertThatThrownBy(() -> crewHistory.modify(modifyTime, todayDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 수정 일자는 어제 기록까지만 수정할 수 있습니다.");
    }

    @DisplayName("크루별 출석 기록을 확인한다")
    @Test
    void checkAttendanceHistoryByCrewTest() {
        // Given
        LocalDate today = LocalDate.of(2024, 12, 19);

        LocalDateTime dateTime1 = makeAttendance(3);
        LocalDateTime dateTime2 = makeAttendance(4);
        LocalDateTime todayDateTime = makeAttendance(19);
        CrewHistory crewHistory = new CrewHistory(Map.of(
                LocalDate.from(dateTime1), dateTime1,
                LocalDate.from(dateTime2), dateTime2,
                LocalDate.from(todayDateTime), todayDateTime
        ));

        // When
        List<LocalDateTime> attendanceHistory = crewHistory.getAttendanceHistory(today);

        // Then
        assertThat(attendanceHistory).contains(dateTime1, dateTime2);
    }

    @DisplayName("출석 타입별 횟수를 계산한다")
    @Test
    void countAttendanceStatusTest() {
        // Given
        LocalDate todayDate = LocalDate.of(2024, 12, 13);
        LocalDateTime dateTime1 = makeAttendance(2);
        LocalDateTime dateTime2 = makeLate(3);
        LocalDateTime dateTime3 = makeAbsent(4);
        LocalDateTime dateTime4 = makeDefaultTime(5);
        LocalDateTime dateTime5 = makeAttendance(6);
        CrewHistory crewHistory = new CrewHistory(Map.of(
                LocalDate.from(dateTime1), dateTime1,
                LocalDate.from(dateTime2), dateTime2,
                LocalDate.from(dateTime3), dateTime3,
                LocalDate.from(dateTime4), dateTime4,
                LocalDate.from(dateTime5), dateTime5
        ));

        // When
        AttendanceCounter attendanceCounter = crewHistory.countAttendanceStatus(todayDate);

        // Then
        assertAll(
                () -> assertThat(attendanceCounter.getAttendanceCount()).isEqualTo(2),
                () -> assertThat(attendanceCounter.getLateCount()).isEqualTo(1),
                () -> assertThat(attendanceCounter.getAbsentCount()).isEqualTo(2)
        );
    }
}
