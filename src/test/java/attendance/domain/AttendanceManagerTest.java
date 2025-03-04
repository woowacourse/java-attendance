package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.domain.fixture.AttendanceManagerTestFixture;
import attendance.domain.fixture.AttendancesTestFixture;
import attendance.domain.fixture.LocalDateTestFixture;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

        AttendanceChecker checker = new DefaultAttendanceChecker(new ArrayList<>());

        assertThat(new AttendanceManager(crewAttendances, LocalDateTestFixture.DATE_PROVIDER,
                new DefaultAttendanceStatistics(LocalDateTestFixture.DATE_PROVIDER, checker), checker))
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
        LocalTime attendTime = LocalTime.of(10, 0);

        assertThatCode(() -> attendanceManager.attend(crewName, attendTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석_기록이_있는_경우_예외가_발생한다() {
        String crewName = "빙티";
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(crewName);
        LocalTime attendTime = LocalTime.of(10, 0);
        attendanceManager.attend(crewName, attendTime);

        LocalTime newAttendTime = LocalTime.of(10, 4);
        assertThatThrownBy(() -> attendanceManager.attend(crewName, newAttendTime))
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
        LocalTime attendTime = LocalTime.of(23, 59);

        assertThatThrownBy(() -> attendanceManager.attend(crewName, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 캠퍼스 운영시간이 아닙니다.");
    }

    @Test
    void 캠퍼스_등교일이_아니면_예외가_발생한다() {
        String crewName = "빙티";
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createEmptyManagerByName(
                LocalDate.of(2024, 12, 25), "빙티");
        LocalTime attendTime = LocalTime.of(10, 0);

        assertThatThrownBy(() -> attendanceManager.attend(crewName, attendTime))
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
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createByCrewAttendances(crewAttendances);

        WarningLevel warningLevel = attendanceManager.calculateCrewWarningLevel(crewName);

        assertThat(warningLevel).isEqualTo(WarningLevel.REMOVE);
    }

    @Test
    void 특정_크루의_출석_목록을_반환한다() {
        String crewName = "빙티";
        Map<String, Attendances> crewAttendances = new HashMap<>();
        crewAttendances.put(crewName, AttendancesTestFixture.createAttendances(3, 5, 28));
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createByCrewAttendances(crewAttendances);

        Map<LocalDate, Attendance> findAttendances = attendanceManager.getCrewAttendances(crewName);

        assertThat(findAttendances).isNotNull();
    }

    @Test
    void 특정_크루의_출석_지각_결석_횟수를_반환한다() {
        String crewName = "빙티";
        Map<String, Attendances> crewAttendances = new HashMap<>();
        crewAttendances.put(crewName, AttendancesTestFixture.createAttendances(3, 5,
                LocalDateTestFixture.DATE_PROVIDER.now().getDayOfMonth()));
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createByCrewAttendances(crewAttendances);

        Map<AttendanceStatus, Integer> statusCount = attendanceManager.calculateStatusCount(crewName);

        assertThat(statusCount).containsEntry(AttendanceStatus.LATENESS, 3);
        assertThat(statusCount).containsEntry(AttendanceStatus.ABSENCE, 5);
    }

    @Test
    void 전체_크루원의_출석_지각_결석_횟수를_반환한다() {
        String crewName1 = "빙티";
        Map<String, Attendances> crewAttendances = new HashMap<>();
        crewAttendances.put(crewName1, AttendancesTestFixture.createAttendances(3, 5,
                LocalDateTestFixture.DATE_PROVIDER.now().getDayOfMonth()));

        String crewName2 = "짱수";
        crewAttendances.put(crewName2, AttendancesTestFixture.createAttendances(5, 6,
                LocalDateTestFixture.DATE_PROVIDER.now().getDayOfMonth()));

        String crewName3 = "이든";
        crewAttendances.put(crewName3, AttendancesTestFixture.createAttendances(7, 2,
                LocalDateTestFixture.DATE_PROVIDER.now().getDayOfMonth()));
        AttendanceManager attendanceManager = AttendanceManagerTestFixture.createByCrewAttendances(crewAttendances);

        Map<String, Map<AttendanceStatus, Integer>> crewsStatusCount = attendanceManager.getCrewsStatusCount();
        assertAll(
                () -> assertThat(crewsStatusCount.get(crewName1)).containsEntry(AttendanceStatus.LATENESS, 3),
                () -> assertThat(crewsStatusCount.get(crewName1)).containsEntry(AttendanceStatus.ABSENCE, 5),
                () -> assertThat(crewsStatusCount.get(crewName2)).containsEntry(AttendanceStatus.LATENESS, 5),
                () -> assertThat(crewsStatusCount.get(crewName2)).containsEntry(AttendanceStatus.ABSENCE, 6),
                () -> assertThat(crewsStatusCount.get(crewName3)).containsEntry(AttendanceStatus.LATENESS, 7),
                () -> assertThat(crewsStatusCount.get(crewName3)).containsEntry(AttendanceStatus.ABSENCE, 2)
        );
    }


}
