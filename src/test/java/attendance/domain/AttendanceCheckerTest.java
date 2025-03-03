package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import attendance.domain.fixture.LocalDateTestFixture;
import attendance.util.CsvDataLoader;
import attendance.util.DataLoader;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("캠퍼스 운영 여부")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceCheckerTest {
    private static final LocalDateProvider DATE_PROVIDER = LocalDateTestFixture.DATE_PROVIDER;
    private static AttendanceChecker attendanceChecker;

    @BeforeAll
    static void setup(){
        DataLoader dataLoader = new CsvDataLoader();
        attendanceChecker = new DefaultAttendanceChecker(dataLoader.loadHolidayData());
    }


    @Test
    void 주말이면_예외가_발생한다() {
        LocalDate weekendDate = LocalDateTestFixture.createWeekendDate();
        LocalTime time = LocalTime.of(10, 0);

        assertThatThrownBy(() -> attendanceChecker.checkCampusOpen(weekendDate, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }

    @Test
    void 공휴일이면_예외가_발생한다() {
        LocalDate holidayDate = LocalDate.of(2024, 12, 25);
        LocalTime time = LocalTime.of(10, 0);

        assertThatThrownBy(() -> attendanceChecker.checkCampusOpen(holidayDate, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "23, 1",
            "23, 59",
            "0, 0",
            "7, 59"
    })
    void 캠퍼스의_운영시간이_아니면_예외가_발생한다(int hour, int minute) {
        LocalDate regularDate = LocalDateTestFixture.createRegularDate();
        LocalTime time = LocalTime.of(hour, minute);

        assertThatThrownBy(() -> attendanceChecker.checkCampusOpen(regularDate, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 캠퍼스 운영시간이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "8, 0",
            "8, 59",
            "22, 59",
            "23, 0"
    })
    void 캠퍼스의_운영시간이면_예외가_발생하지_않는다(int hour, int minute) {
        LocalDate regularDate = LocalDateTestFixture.createRegularDate();
        LocalTime time = LocalTime.of(hour, minute);

        assertThatCode(() -> attendanceChecker.checkCampusOpen(regularDate, time))
                .doesNotThrowAnyException();
    }

}
