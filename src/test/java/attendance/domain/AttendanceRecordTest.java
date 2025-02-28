package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.fixture.AttendanceRecordTestFixture;
import attendance.domain.fixture.LocalDateTestFixture;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("출석 목록")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceRecordTest {

    @Test
    void 크루원의_이름으로_객체를_생성한다() {
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);

        assertThat(attendanceRecord)
                .isInstanceOf(AttendanceRecord.class);
    }

    @Test
    void 크루원의_이름에_해당하면_true를_반환한다() {
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);
        boolean result = attendanceRecord.isNameMatched(crewName);

        assertThat(result).isTrue();
    }

    @Test
    void 크루원의_이름예_해당하지_않으면_false를_반환한다() {
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);
        String findName = "루키";
        boolean result = attendanceRecord.isNameMatched(findName);

        assertThat(result).isFalse();
    }

    @Test
    void 출석_저장_시_출석_시각을_반환한다() {
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(10, 0);

        LocalDateTime result = attendanceRecord.attend(attendDate, attendTime);

        assertThat(result.getHour()).isEqualTo(10);
        assertThat(result.getMinute()).isEqualTo(0);
        assertThat(result.toLocalDate()).isEqualTo(attendDate);
    }

    @Test
    void 출석_기록이_있는_경우_예외가_발생한다() {
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(10, 0);
        attendanceRecord.attend(attendDate, attendTime);

        LocalTime newAttendTime = LocalTime.of(10, 4);
        assertThatThrownBy(() -> attendanceRecord.attend(attendDate, newAttendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 있습니다. 출석 수정 기능을 이용해주세요.");
    }

    static Stream<LocalTime> provideClosedCampusTimes() {
        return Stream.of(
                LocalTime.of(0, 0),
                LocalTime.of(7, 0),
                LocalTime.of(7, 59),
                LocalTime.of(23, 1),
                LocalTime.of(23, 59)
        );
    }

    @ParameterizedTest
    @MethodSource("provideClosedCampusTimes")
    void 캠퍼스_운영시간이_아니면_예외가_발생한다() {
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(23, 59);

        assertThatThrownBy(() -> attendanceRecord.attend(attendDate, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 캠퍼스 운영시간이 아닙니다.");
    }

    @Test
    void 캠퍼스_등교일이_아니면_예외가_발생한다() {
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);
        LocalDate attendDate = LocalDateTestFixture.createWeekendDate();
        LocalTime attendTime = LocalTime.of(10, 0);

        assertThatThrownBy(() -> attendanceRecord.attend(attendDate, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }

    @Test
    void 등교_날짜와_시간으로_출석_기록을_수정한다() {
        LocalDate date = LocalDateTestFixture.createRegularDate();
        LocalTime time = LocalTime.of(9, 0);
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);

        assertThatCode(() -> attendanceRecord.modify(date, time))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석_수정에_성공하면_기존_출석_기록을_반환한다() {
        LocalDate date = LocalDateTestFixture.createRegularDate();
        LocalTime time = LocalTime.of(9, 0);
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);
        attendanceRecord.attend(date, time);

        LocalTime modifyTime = LocalTime.of(10, 6);
        Attendance attendance = attendanceRecord.modify(date, modifyTime);

        assertThat(attendance.time()).isEqualTo(time);
        assertThat(attendance.status()).isEqualTo(AttendanceStatus.PRESENT);
    }

    @Test
    void 전날까지의_출석_지각_결석_횟수를_반환한다() {
        int today = 28;
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = AttendanceRecordTestFixture.createAttendanceRecord(crewName, 5, 3, today);

        Map<AttendanceStatus, Integer> count = attendanceRecord.getTotalStatusCount(today);

        assertThat(count).containsEntry(AttendanceStatus.LATENESS, 5);
        assertThat(count).containsEntry(AttendanceStatus.ABSENCE, 3);
    }

    @Test
    void 등교하지_않은_날은_결석으로_간주한다() {
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = new AttendanceRecord(crewName);

        Map<AttendanceStatus, Integer> result = attendanceRecord.getTotalStatusCount(5);

        assertThat(result).containsEntry(AttendanceStatus.ABSENCE, 5);
    }

    @Test
    void 제적_위험_레벨을_반환한다() {
        int endDate = 28;
        String crewName = "빙티";
        AttendanceRecord attendanceRecord = AttendanceRecordTestFixture.createAttendanceRecord(crewName, 3, 5, endDate);
        WarningLevel warningLevel = attendanceRecord.calculateWarningLevel(endDate);

        assertThat(warningLevel).isEqualTo(WarningLevel.REMOVE);
    }


}
