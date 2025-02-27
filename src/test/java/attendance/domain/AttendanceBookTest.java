package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceBookTest {
    @DisplayName("크루 출석 정보 저장 성공")
    @Test
    void test1() {
        Map<String, CrewAttendance> crewAttendances = CrewAttendanceTestFixture.createCrewAttendances();
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> attendanceBook.add(name, localDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("크루 출석 정보 저장 실패")
    @Test
    void test2() {
        Map<String, CrewAttendance> crewAttendances = CrewAttendanceTestFixture.createCrewAttendances();
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        attendanceBook.add(name, localDateTime);

        String otherName = "루키";

        assertThatThrownBy(() -> attendanceBook.add(otherName, localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 닉네임입니다.");
    }

    @DisplayName("크루 출석 정보 수정 성공")
    @Test
    void test3() {
        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.put("빙봉", crewAttendance);

        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        String name = "빙봉";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> attendanceBook.modify(name, newLocalDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("유효하지 않은 닉네임 크루 출석 정보 수정 실패")
    @Test
    void test4() {
        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.put("빙봉", crewAttendance);

        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        String name = "빙티";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> attendanceBook.modify(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 닉네임입니다.");

    }

    @DisplayName("기록이 없는 날짜 출석 정보 수정 실패")
    @Test
    void test5() {
        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.put("빙봉", crewAttendance);

        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);
        String name = "빙봉";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> attendanceBook.modify(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");
    }

    @DisplayName("크루의 출석 기록 조회")
    @Test
    void test7() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 3, 9, 58);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 4, 10, 2);
        LocalDateTime localDateTime3 = LocalDateTime.of(2024, 12, 5, 10, 6);
        LocalDateTime localDateTime4 = LocalDateTime.of(2024, 12, 6, 10, 1);
        List<LocalDateTime> localDateTimes =
                new ArrayList<>(List.of(localDateTime1, localDateTime2, localDateTime3, localDateTime4));

        String crewName = "빙티";
        CrewAttendance crewAttendance = new CrewAttendance(crewName);
        localDateTimes.forEach(crewAttendance::add);

        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        crewAttendances.put(crewName, crewAttendance);
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        Map<LocalDate, AttendanceTimeStatus> attendances =
                attendanceBook.getAttendanceHistory(crewName, LocalDate.of(2024, 12, 6));

        localDateTimes.add(LocalDateTime.of(2024, 12, 2, 0, 0));
        List<LocalDate> expectedLocalDates = localDateTimes.stream()
                .filter(localDateTime -> !localDateTime.equals(localDateTime4))
                .map(LocalDateTime::toLocalDate)
                .toList();

        assertThat(attendances).hasSize(4);
        assertThat(attendances.keySet()).containsAll(expectedLocalDates);
    }

    @DisplayName("크루의 출석, 지각, 결석 횟수 세기")
    @Test
    void test6() {
        String crewName = "빙티";
        CrewAttendance crewAttendance = new CrewAttendance(crewName);
        crewAttendance.add(LocalDateTime.of(2024, 12, 3, 9, 58));
        crewAttendance.add(LocalDateTime.of(2024, 12, 4, 10, 2));
        crewAttendance.add(LocalDateTime.of(2024, 12, 5, 10, 6));
        crewAttendance.add(LocalDateTime.of(2024, 12, 6, 10, 1));

        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        crewAttendances.put(crewName, crewAttendance);
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        Map<AttendanceStatus, Integer> attendanceStatusCounts =
                attendanceBook.getAttendanceStatusCounts(crewName, LocalDate.of(2024, 12, 6));

        assertThat(attendanceStatusCounts.get(PRESENT)).isEqualTo(2);
        assertThat(attendanceStatusCounts.get(LATENESS)).isEqualTo(1);
        assertThat(attendanceStatusCounts.get(ABSENCE)).isEqualTo(1);
    }

    @DisplayName("크루의 제적 위험 단계 가져오기")
    @CsvSource(value = {"쿠키,경고", "빙봉,경고", "빙티,면담", "이든,면담", "짱수,해당 없음"})
    @ParameterizedTest
    void test8(String crewName, String expectedLevel) {
        final Map<String, CrewAttendance> crewAttendances = CrewAttendanceTestFixture.createCrewAttendances();
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);
        LocalDate date = LocalDate.of(2024, 12, 13);

        WarningLevel actualLevel = attendanceBook.getCrewWarningLevel(crewName, date);

        assertThat(actualLevel.getDisplayName()).isEqualTo(expectedLevel);
    }

    @DisplayName("제적 위험 단계 별 해당 크루 찾기")
    @CsvSource(value = {"WARNING, 빙봉, 쿠키", "SUPERVISED, 빙티, 이든"})
    @ParameterizedTest
    void test8(String targetLevel, String crew1, String crew2) {
        final Map<String, CrewAttendance> crewAttendances = CrewAttendanceTestFixture.createCrewAttendances();
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);
        LocalDate date = LocalDate.of(2024, 12, 13);

        final Map<WarningLevel, List<CrewAttendance>> crewsByWarningLevel = attendanceBook.getCrewsByWarningLevel(date);

        List<CrewAttendance> crews = crewsByWarningLevel.get(WarningLevel.valueOf(targetLevel));
        List<String> crewNames = crews.stream().map(CrewAttendance::getName).toList();

        assertThat(crewNames).containsAll(List.of(crew1, crew2));
    }
}
