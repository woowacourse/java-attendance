package model;

import static constant.PathConstant.ATTENDANCE_FILE_PATH;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DateTimeGenerator;
import util.FileParser;
import util.FixedDateTimeStrategy;

class AttendancesTest {

    LocalDateTime fixedDateTime;
    DateTimeGenerator dateTimeGenerator;

    @BeforeEach
    void beforeEach() {
        fixedDateTime = LocalDateTime.of(2024, 12, 13, 12, 0);
        FixedDateTimeStrategy fixedDateTimeStrategy = new FixedDateTimeStrategy(fixedDateTime);
        dateTimeGenerator = new DateTimeGenerator(fixedDateTimeStrategy);
    }

    @Test
    @DisplayName("Attendances 초기 설정을 진행한다.")
    void test1() {
        // given
        List<String> lines = FileParser.readLines(ATTENDANCE_FILE_PATH.getPath());
        Crew miso = Crew.of("미소");
        Crew neo = Crew.of("네오");
        Crew pobi = Crew.of("포비");

        // when
        Attendances attendances = Attendances.from(lines, dateTimeGenerator);

        // then
        int expected = 9;
        assertThat(attendances.getAttendancesByCrew(miso)).hasSize(expected);
        assertThat(attendances.getAttendancesByCrew(neo)).hasSize(expected);
        assertThat(attendances.getAttendancesByCrew(pobi)).hasSize(expected);
    }
}
