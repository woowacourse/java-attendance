import constant.ErrorMessage;
import domain.AllCrew;
import domain.Attendance;
import domain.Crew;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class AllCrewTest {

    @Nested
    @DisplayName("All Crew 파일 로드 테스트")
    class AllCrewFileLoadTest {
        @DisplayName("파일 읽어오기")
        @Test
        void test1() {
            // given
            AllCrew allCrew = new AllCrew();

            // when & then
            assertThatCode(() -> allCrew.updateFile("src/main/resources/attendances.csv")).doesNotThrowAnyException();
        }

        @DisplayName("파일 읽어오기 예외")
        @Test
        void test2() {
            // given
            AllCrew allCrew = new AllCrew();

            // when & then
            assertThatThrownBy(() -> allCrew.updateFile("wrong_file_path")).hasMessage(ErrorMessage.NO_ATTENDANCES_FILE.getMessage());
        }
    }

    @DisplayName("크루 출석 정보 등록 & 이름으로 등록 여부 확인 테스트")
    @Test
    void test1() {
        // given
        AllCrew allCrew = new AllCrew();
        String name = "띠용";
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0));

        // when
        allCrew.addCrewInfoWithNameAndAttendance(name, attendance);

        // then
        Assertions.assertAll(
                () -> assertThat(allCrew.containsCrewName(name)).isTrue(),
                () -> assertThat(allCrew.containsCrewName("없는이름")).isFalse()
        );
    }

    @DisplayName("이름으로 해당 크루 찾기")
    @Test
    void test2() {
        // given
        AllCrew allCrew = new AllCrew();
        String name = "띠용";
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0));
        allCrew.addCrewInfoWithNameAndAttendance(name, attendance);

        // when & then
        Assertions.assertAll(
                () -> assertThat(allCrew.findCrewByName(name)
                        .getAttendanceHistory())
                        .containsExactly(attendance)
        );
    }

    @DisplayName("이미 존재하는 크루 출석 정보 추가")
    @Test
    void test3() {
        // given
        AllCrew allCrew = new AllCrew();
        String name = "띠용";
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0));
        Attendance attendance2 = new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0));
        allCrew.addCrewInfoWithNameAndAttendance(name, attendance);
        allCrew.addCrewInfoWithNameAndAttendance(name, attendance2);

        // when & then
        Assertions.assertAll(
                () -> assertThat(allCrew.findCrewByName(name)
                        .getAttendanceHistory())
                        .containsExactly(attendance, attendance2)
        );
    }

    @DisplayName("모든 크루의 결석일 정보 업데이트")
    @Test
    void test4() {
        // given
        AllCrew allCrew = new AllCrew();
        allCrew.addCrewInfoWithNameAndAttendance("띠용", new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)));
        allCrew.addCrewInfoWithNameAndAttendance("띠용", new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0)));
        // 띠용 3, 4일 결석
        allCrew.addCrewInfoWithNameAndAttendance("강산", new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)));
        allCrew.addCrewInfoWithNameAndAttendance("강산", new Attendance(LocalDateTime.of(2024, 12, 3, 10, 0)));
        allCrew.addCrewInfoWithNameAndAttendance("강산", new Attendance(LocalDateTime.of(2024, 12, 4, 10, 0)));
        // 강산 5일 결석

        // when
        allCrew.fillAllCrewsEmptyDateWithAbsent(LocalDate.of(2024, 12, 5));

        // then
        Assertions.assertAll(
                () -> assertThat(allCrew.getAbsentCountWithCrewName("띠용")).isEqualTo(2),
                () -> assertThat(allCrew.getAbsentCountWithCrewName("강산")).isEqualTo(1)
        );
    }

    @Nested
    @DisplayName("제적 위험자 크루 테스트")
    class PenaltyReceivedCrewTest {
        @DisplayName("제적 위험자 크루 탐색")
        @Test
        void test5() {
            // given
            AllCrew allCrew = new AllCrew();
            allCrew.addCrewInfoWithNameAndAttendance("띠용", new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)));
            allCrew.addCrewInfoWithNameAndAttendance("띠용", new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0)));
            // 띠용 3, 4일 결석
            allCrew.addCrewInfoWithNameAndAttendance("강산", new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)));
            allCrew.addCrewInfoWithNameAndAttendance("강산", new Attendance(LocalDateTime.of(2024, 12, 3, 10, 0)));
            allCrew.addCrewInfoWithNameAndAttendance("강산", new Attendance(LocalDateTime.of(2024, 12, 4, 10, 0)));
            // 강산 5일 결석
            allCrew.addCrewInfoWithNameAndAttendance("율무", new Attendance(LocalDateTime.of(2024, 12, 4, 10, 0)));
            // 율무 2, 3, 5일 결석

            // when
            allCrew.fillAllCrewsEmptyDateWithAbsent(LocalDate.of(2024, 12, 5));

            // then
            assertThat(allCrew.getPenaltyReceivedCrew()).extracting("name").contains("띠용", "율무");
        }

        @DisplayName("제적 위험자 크루 정렬 테스트")
        @Test
        void test6() {
            // given
            AllCrew allCrew = new AllCrew();
            allCrew.addCrewInfoWithNameAndAttendance("띠용", new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)));
            allCrew.addCrewInfoWithNameAndAttendance("띠용", new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0)));
            // 띠용 3, 4일 결석
            allCrew.addCrewInfoWithNameAndAttendance("강산", new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)));
            allCrew.addCrewInfoWithNameAndAttendance("강산", new Attendance(LocalDateTime.of(2024, 12, 3, 10, 0)));
            // 강산 4, 5일 결석
            allCrew.addCrewInfoWithNameAndAttendance("율무", new Attendance(LocalDateTime.of(2024, 12, 4, 10, 0)));
            // 율무 2, 3, 5일 결석

            // when
            allCrew.fillAllCrewsEmptyDateWithAbsent(LocalDate.of(2024, 12, 5));
            List<Crew> penaltyReceivedCrew = allCrew.getPenaltyReceivedCrew();
            allCrew.sortPenaltyReceivedCrew(penaltyReceivedCrew);

            // then
            assertThat(penaltyReceivedCrew).extracting("name").containsExactly("율무", "강산", "띠용");
        }
    }
}
