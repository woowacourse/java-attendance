package domain;

import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.CurrentDateGenerateStrategy;
import strategy.TestAttendanceCurrentDateGenerateStrategy;

public class ModifyAttendanceTest {

    private static CurrentDateGenerateStrategy currentDateGenerateStrategy;

    private static CrewAttendances crewAttendances;

    @BeforeAll
    static void initiate() {
        currentDateGenerateStrategy = new TestAttendanceCurrentDateGenerateStrategy(LocalDate.of(2024, 12, 3));
    }

    @BeforeEach
    void initiateAttendance() {
        crewAttendances = new CrewAttendances(currentDateGenerateStrategy);
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
    }

    @Test
    @DisplayName("닉네임과 날짜, 시간을 입력하여 수정할 수 있다")
    void modifyAttendanceTest() {
        String nickname = "투다";
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(8, 3);
        crewAttendances.modifyAttendance(nickname, date, time);

        Assertions.assertThat(crewAttendances.crewAttendance(nickname, date).attendanceStatus())
                .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("학교 시작 시간전으로 수정시 에러가 발생한다")
    void modifyAttendanceTestBeforeStart() {
        String nickname = "투다";
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(7, 3);
        Assertions.assertThatThrownBy(() -> crewAttendances.modifyAttendance(nickname, date, time))
                .isInstanceOf(AttendanceException.class);
    }

    @Test
    @DisplayName("학교 종료후로 수정시 에러가 발생한다")
    void modifyAttendanceTestAfterSchoolClose() {
        String nickname = "투다";
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(23, 3);
        Assertions.assertThatThrownBy(() -> crewAttendances.modifyAttendance(nickname, date, time))
                .isInstanceOf(AttendanceException.class);
    }

    @Test
    @DisplayName("존재하지 않는 유저를 수정하려하면 에러가 발생한다")
    void doesntExistCrewModifyTest() {
        String nickname = "냠냠";
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(8, 3);
        Assertions.assertThatThrownBy(() -> crewAttendances.modifyAttendance(nickname, date, time))
                .isInstanceOf(AttendanceException.class);
    }
}
