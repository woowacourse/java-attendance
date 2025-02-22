package attendance.domain;

import attendance.utility.DateGenerator;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceManagerTest {

    DateGenerator dateGenerator;

    @BeforeEach
    void beforeEach() {
        dateGenerator = new MockingDateGenerator();
    }

    @DisplayName("크루가 추가될때 기본 출석이 생성된다")
    @Test
    void 크루가_추가될때_기본_출석이_생성된다() {
        // given
        String name = "랜디";
        HolidayChecker holidayChecker = new HolidayChecker();
        AttendanceManager attendanceManager = new AttendanceManager(holidayChecker, dateGenerator);

        // when
        attendanceManager.addCrew(name);

        // then
        Assertions.assertThat(attendanceManager.findCrewAttendance(name).size())
                .isEqualTo(22);
    }

    static class MockingDateGenerator implements DateGenerator {

        @Override
        public LocalDate now() {
            return LocalDate.of(2024, 12, 5);
        }
    }
}
