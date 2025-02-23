package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewAttendanceRepositoryTest {

    @DisplayName("크루 출석 정보 저장 성공")
    @Test
    void test1() {
        List<CrewAttendance> crewAttendances = new ArrayList<>();
        crewAttendances.add(new CrewAttendance("빙티"));
        crewAttendances.add(new CrewAttendance("이든"));
        crewAttendances.add(new CrewAttendance("쿠키"));
        crewAttendances.add(new CrewAttendance("빙봉"));
        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(crewAttendances);
        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> crewAttendanceRepository.add(name, localDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("크루 출석 정보 저장 실패")
    @Test
    void test2() {
        List<CrewAttendance> crewAttendances = new ArrayList<>();
        crewAttendances.add(new CrewAttendance("빙티"));
        crewAttendances.add(new CrewAttendance("이든"));
        crewAttendances.add(new CrewAttendance("쿠키"));
        crewAttendances.add(new CrewAttendance("빙봉"));

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(crewAttendances);
        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        CrewAttendance crewAttendance = new CrewAttendance(name);
        crewAttendance.add(localDateTime);

        crewAttendanceRepository.add(name, localDateTime);

        String otherName = "루키";

        assertThatThrownBy(() -> crewAttendanceRepository.add(otherName, localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 닉네임입니다.");
    }

    @DisplayName("크루 출석 정보 수정 성공")
    @Test
    void test3() {
        List<CrewAttendance> crewAttendances = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.add(crewAttendance);

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(crewAttendances);
        String name = "빙봉";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> crewAttendanceRepository.update(name, newLocalDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("유효하지 않은 닉네임 크루 출석 정보 수정 실패")
    @Test
    void test4() {
        List<CrewAttendance> crewAttendances = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.add(crewAttendance);

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(crewAttendances);
        String name = "빙티";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> crewAttendanceRepository.update(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 닉네임입니다.");

    }

    @DisplayName("기록이 없는 날짜 크루 출석 정보 수정 실패")
    @Test
    void test6() {
        List<CrewAttendance> crewAttendances = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.add(crewAttendance);

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(crewAttendances);
        String name = "빙봉";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> crewAttendanceRepository.update(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");
    }

    @DisplayName("크루 이름으로 제적 위험 여부 조회")
    @Test
    void test7() {
        CrewAttendanceRepository crewAttendanceRepository = CrewAttendanceRepositoryTestFixture.createCrewAttendanceRepository();
        WarningLevel warningLevel = crewAttendanceRepository.queryWarningLevelByName("빙티", 13);

        assertThat(warningLevel).isEqualTo(WarningLevel.SUPERVISED);
    }
}
