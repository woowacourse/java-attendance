package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AllCrewTest {

    @DisplayName("이미 포함된 크루 이름 여부 테스트")
    @Test
    void isContainedCrewNameTest() {
        // given
        AllCrew allCrew = new AllCrew();
        allCrew.addCrew(new Crew("빙티"));
        allCrew.addCrew(new Crew("띠용"));
        allCrew.addCrew(new Crew("이든"));

        // when & then
        assertThat(allCrew.isContainedCrewName("띠용")).isTrue();
    }

    @DisplayName("이름으로 크루 출석 정보 수정 테스트")
    @Test
    void modifyAttendanceByNameTest() {
        // given
        AllCrew allCrew = new AllCrew();
        allCrew.addCrew(new Crew("띠용"));
        allCrew.addCrew(new Crew("빙티"));
        LocalDateTime oldDateTime = LocalDateTime.of(2024, 12, 3, 10, 7);
        allCrew.addCrewAttendanceByName("띠용", oldDateTime);

        // when
        LocalDateTime newDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        AttendanceUpdateResult attendanceUpdateResult = allCrew.modifyCrewAttendanceByName("띠용", newDateTime);

        // then
        assertAll(
                () -> assertThat(attendanceUpdateResult.getOldAttendance().getDateAndTime()).isEqualTo(oldDateTime),
                () -> assertThat(attendanceUpdateResult.getNewAttendance().getDateAndTime()).isEqualTo(newDateTime)
        );
    }

    @Nested
    @DisplayName("이름으로 크루 탐색 테스트")
    class FindCrewByNameTest {
        @DisplayName("정상 탐색 테스트")
        @Test
        void test1() {
            // given
            AllCrew allCrew = new AllCrew();
            Crew crew1 = new Crew("빙티");
            Crew crew2 = new Crew("이든");
            allCrew.addCrew(crew1);
            allCrew.addCrew(crew2);

            // when & then
            assertThat(allCrew.findCrewByName("빙티")).isSameAs(crew1);
        }

        @DisplayName("없는 크루 이름 탐색 테스트")
        @Test
        void test2() {
            // given
            AllCrew allCrew = new AllCrew();
            Crew crew1 = new Crew("빙티");
            Crew crew2 = new Crew("이든");
            allCrew.addCrew(crew1);
            allCrew.addCrew(crew2);

            // when & then
            assertThatThrownBy(() -> allCrew.findCrewByName("띠용")).hasMessage("존재하지 않는 크루입니다.");
        }
    }

    @Nested
    @DisplayName("결석 패널티 대상자 색출 테스트")
    class GetAllAbsentPenaltyReceivedCrewTest {
        @DisplayName("경고 대상자 색출 테스트")
        @Test
        void test1() {
            // given
            AllCrew allCrew = new AllCrew();
            Crew crew1 = new Crew("빙티");
            Crew crew2 = new Crew("이든");
            allCrew.addCrew(crew1);
            allCrew.addCrew(crew2);

            // 2일 결석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9, 58));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10, 2));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10, 6));  // 지각
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10, 1));  //  출석
            // 9일 결석
            // 결석 2회, 지각 1회

            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 2, 10, 31)); // 출석 (월요일)
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10, 0));  // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10, 31)); // 출석 (월요일)
            // 전부 출석

            allCrew.updateAbsentHistory(LocalDate.of(2024, 12, 9));

            // when & then
            List<Crew> allAbsentPenaltyReceivedCrew = allCrew.getAllAbsentPenaltyReceivedCrew();
            assertAll(() -> assertThat(allAbsentPenaltyReceivedCrew).extracting(Crew::getAbsentPenalty).containsExactly(AbsentPenalty.WARNING),
                    () -> assertThat(allAbsentPenaltyReceivedCrew).extracting(Crew::getName).containsExactly("빙티"));
        }

        @DisplayName("면담 대상자 색출 테스트")
        @Test
        void test2() {
            // given
            AllCrew allCrew = new AllCrew();
            Crew crew1 = new Crew("빙티");
            Crew crew2 = new Crew("이든");
            allCrew.addCrew(crew1);
            allCrew.addCrew(crew2);

            // 2일 결석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9, 58));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10, 2));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10, 6));  // 지각
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10, 1));  //  출석
            // 9, 10일 결석
            // 결석 3회, 지각 1회

            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 2, 10, 31)); // 출석 (월요일)
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10, 0));  // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10, 31)); // 출석 (월요일)
            // 전부 출석

            allCrew.updateAbsentHistory(LocalDate.of(2024, 12, 10));

            // when & then
            List<Crew> allAbsentPenaltyReceivedCrew = allCrew.getAllAbsentPenaltyReceivedCrew();
            assertAll(() -> assertThat(allAbsentPenaltyReceivedCrew).extracting(Crew::getAbsentPenalty).containsExactly(AbsentPenalty.COUNSELING),
                    () -> assertThat(allAbsentPenaltyReceivedCrew).extracting(Crew::getName).containsExactly("빙티"));
        }

        @DisplayName("제적 대상자 색출 테스트")
        @Test
        void test3() {
            // given
            AllCrew allCrew = new AllCrew();
            Crew crew1 = new Crew("빙티");
            Crew crew2 = new Crew("이든");
            allCrew.addCrew(crew1);
            allCrew.addCrew(crew2);

            // 2일 결석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9, 58));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10, 2));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10, 6));  // 지각
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10, 1));  //  출석
            // 9, 10, 11, 12, 13일 결석
            // 결석 6회, 지각 1회

            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 2, 10, 31)); // 출석 (월요일)
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10, 0)); // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10, 0));  // 출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10, 31)); // 출석 (월요일)
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 10, 10, 0)); // 출석
            // 11, 12, 13일 결석
            // 결석 3회

            allCrew.updateAbsentHistory(LocalDate.of(2024, 12, 13));

            // when & then
            List<Crew> allAbsentPenaltyReceivedCrew = allCrew.getAllAbsentPenaltyReceivedCrew();
            assertAll(() -> assertThat(allAbsentPenaltyReceivedCrew).extracting(Crew::getAbsentPenalty).containsExactly(AbsentPenalty.EXPEL, AbsentPenalty.COUNSELING),
                    () -> assertThat(allAbsentPenaltyReceivedCrew).extracting(Crew::getName).containsExactly("빙티", "이든"));
        }
    }


    @Nested
    @DisplayName("정렬 테스트")
    class sortAllCrewTest {
        @DisplayName("결석 내림차순 정렬")
        @Test
        void test1() {
            // given
            AllCrew allCrew = new AllCrew();
            Crew crew1 = new Crew("빙티");
            Crew crew2 = new Crew("이든");
            // 이든, 빙티 순으로 삽입
            allCrew.addCrew(crew2);
            allCrew.addCrew(crew1);

            // 2일 결석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9, 58));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10, 2));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10, 6));  // 지각
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10, 1));  //  출석
            // 7일 결석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 10, 10, 8)); // 지각
            // 11, 12 결석
            // 결석 4회, 지각 2회 => 4결석

            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 2, 10, 31)); // 출석 (월요일)
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10, 7)); // 지각
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10, 8)); // 지각
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10, 29)); // 지각
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10, 6));  // 지각
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10, 31)); // 출석 (월요일)
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 10, 10, 2));//출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 11, 10, 1));//출석
            // 12 일 결석
            // 1결석 4지각 => 2결석
            allCrew.updateAbsentHistory(LocalDate.of(2024, 12, 12));

            // when
            allCrew.sortAllCrewOrderByWarningInfo();

            // then
            assertThat(allCrew.getAllAbsentPenaltyReceivedCrew()).extracting(Crew::getName).containsExactly("빙티", "이든");
        }

        @DisplayName("이름 오름차순 정렬")
        @Test
        void test2() {
            // given
            AllCrew allCrew = new AllCrew();
            Crew crew1 = new Crew("빙티");
            Crew crew2 = new Crew("이든");
            // 이든, 빙티 순으로 삽입
            allCrew.addCrew(crew2);
            allCrew.addCrew(crew1);
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 2, 13, 0));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9, 58));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10, 2));  // 출석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10, 6));  // 지각
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10, 1));  //  출석
            // 7일 결석
            allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 10, 10, 8)); // 지각
            // 11, 12 결석
            // 결석 3회, 지각 2회

            // 2일 결석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10, 7)); // 지각
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10, 8)); // 지각
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10, 29)); // 지각
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10, 6));  // 지각
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10, 31)); // 출석 (월요일)
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 10, 10, 2));//출석
            allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 11, 10, 1));//출석
            // 12 일 결석
            // 2결석 4지각
            allCrew.updateAbsentHistory(LocalDate.of(2024, 12, 12));

            // when
            allCrew.sortAllCrewOrderByWarningInfo();

            // then
            assertThat(allCrew.getAllAbsentPenaltyReceivedCrew()).extracting(Crew::getName).containsExactly("빙티", "이든");
        }
    }


}