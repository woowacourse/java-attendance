package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.fixture.AttendanceManagerTestFixture;
import attendance.domain.fixture.AttendancesTestFixture;
import attendance.domain.fixture.LocalDateTestFixture;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
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
public class AttendanceManagerTest {

    @Test
    void 크루_이름과_출석_정보로_객체를_생성한다() {
        String[] crewNames = {"빙티", "빙봉", "이든", "쿠키"};
        Map<String, Attendances> crewAttendances = new HashMap<>();

        Arrays.stream(crewNames)
                .forEach(name -> {
                    crewAttendances.put(name, new Attendances());
                });

        assertThat(new AttendanceManager(crewAttendances))
                .isInstanceOf(AttendanceManager.class);
    }

    @Test
    void 크루원의_이름에_해당하는_데이터가_있으면_예외가_발생하지_않는다() {
        String[] crewNames = {"빙티", "빙봉", "이든", "쿠키"};
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(crewNames);

        attendanceManager.validateExistCrew("빙티");
    }

    @Test
    void 크루원의_이름에_해당하는_데이터가_없으면_예외가_발생한다() {
        String[] crewNames = {"빙티", "빙봉", "이든"};
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(crewNames);

        String errorName = "루키";

        assertThatThrownBy(() -> attendanceManager.validateExistCrew(errorName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 크루 닉네임입니다.");
    }

    @Test
    void 출석_정보를_저장한다() {
        String crewName = "빙티";
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(10, 0);

        assertThatCode(() -> attendanceManager.attend(crewName, attendDate, attendTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석_기록이_있는_경우_예외가_발생한다() {
        String crewName = "빙티";
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(10, 0);
        attendanceManager.attend(crewName, attendDate, attendTime);

        LocalTime newAttendTime = LocalTime.of(10, 4);
        assertThatThrownBy(() -> attendanceManager.attend(crewName, attendDate, newAttendTime))
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
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(23, 59);

        assertThatThrownBy(() -> attendanceManager.attend(crewName, attendDate, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 캠퍼스 운영시간이 아닙니다.");
    }

    @Test
    void 캠퍼스_등교일이_아니면_예외가_발생한다() {
        String crewName = "빙티";
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(crewName);
        LocalDate attendDate = LocalDateTestFixture.createWeekendDate();
        LocalTime attendTime = LocalTime.of(10, 0);

        assertThatThrownBy(() -> attendanceManager.attend(crewName, attendDate, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }

    @Test
    void 등교_날짜와_시간으로_출석_기록을_수정한다() {
        String crewName = "빙티";
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(crewName);
        LocalDate date = LocalDateTestFixture.createRegularDate();
        LocalTime time = LocalTime.of(9, 0);

        assertThatCode(() -> attendanceManager.modify(crewName, date, time))
                .doesNotThrowAnyException();
    }

    @Test
    void 제적_위험_레벨을_반환한다() {
        int endDate = 28;
        String crewName = "빙티";
        Map<String, Attendances> crewAttendances = new HashMap<>();
        crewAttendances.put(crewName, AttendancesTestFixture.createAttendances(3, 5, endDate));
        AttendanceManager attendanceManager = new AttendanceManager(crewAttendances);

        WarningLevel warningLevel = attendanceManager.calculateCrewWarningLevel(crewName, endDate);

        assertThat(warningLevel).isEqualTo(WarningLevel.REMOVE);
    }

    @Test
    void 특정_크루의_출석_목록을_반환한다() {
        String crewName = "빙티";
        Map<String, Attendances> crewAttendances = new HashMap<>();
        crewAttendances.put(crewName, AttendancesTestFixture.createAttendances(3, 5, 28));
        AttendanceManager manager = new AttendanceManager(crewAttendances);

        Map<LocalDate, Attendance> findAttendances = manager.getCrewAttendances(crewName);

        assertThat(findAttendances).isNotNull();
    }


}
