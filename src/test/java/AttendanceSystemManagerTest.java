import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceSystemManagerTest {
    // TODO: 기능 한 개 구현하고, 전체적으로 리팩토링 (테스트명, 클래스 구조 등)

    @Nested
    class TestForRegisterNewAttendance {
        @Test
        @DisplayName("이미 존재하는 출석기록을 등록하고자 하면 수정 기능을 사용하도록 안내하는 예외가 발생한다.")
        void test1() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
            AttendanceHistory attendanceHistory = new AttendanceHistory(crew, attendAt);
            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(List.of(attendanceHistory)), crews);

            // when
            assertThatThrownBy(() -> attendanceSystemManager.registerNewAttendance(nickname, attendAt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 존재하는 출석 기록입니다. 수정 기능을 이용해주세요.");
        }

        @Test
        @DisplayName("주말에 출석을 시도하는 경우 예외가 발생한다.")
        void test2() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 1, 10, 0);
            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(new ArrayList<>()), crews);

            // when
            assertThatThrownBy(() -> attendanceSystemManager.registerNewAttendance(nickname, attendAt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("평일이거나 공휴일이 아닌 경우에만 출석할 수 있습니다.");
        }

        @Test
        @DisplayName("운영 시간이 아닌 시각에 출석을 시도하면 예외가 발생한다.")
        void test3() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 7, 0);
            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(new ArrayList<>()), crews);

            // when
            assertThatThrownBy(() -> attendanceSystemManager.registerNewAttendance(nickname, attendAt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("운영 시간 내에만 출석할 수 있습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 닉네임으로 출석 수정을 시도하는 경우 예외가 발생한다.")
        void test4() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(new ArrayList<>()), crews);

            // when
            assertThatThrownBy(() -> attendanceSystemManager.registerNewAttendance("없음", attendAt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 닉네임입니다.");
        }

        @Test
        @DisplayName("중복되지 않고 유효한 닉네임과 날짜를 입력하면 정상적으로 출석할 수 있다.")
        void test5() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);

            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(new ArrayList<>()), crews);

            // when
            assertThatCode(
                    () -> attendanceSystemManager.registerNewAttendance(nickname, attendAt)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("정상적으로 출석 기록이 저장된다.")
        void test6() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);

            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(new ArrayList<>()), crews);

            // when
            AttendanceHistory attendanceHistory = attendanceSystemManager.registerNewAttendance(nickname, attendAt);

            // then
            Assertions.assertAll(
                    () -> assertThat(attendanceHistory.getAttendAt()).isEqualTo(attendAt),
                    () -> assertThat(attendanceHistory.getCrew()).isEqualTo(crew)
            );
        }
    }

    @Nested
    class TestForUpdate {
        @Test
        @DisplayName("존재하지 않는 닉네임으로 출석을 시도하는 경우 예외가 발생한다.")
        void test1() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(new ArrayList<>()), crews);

            // when
            assertThatThrownBy(() -> attendanceSystemManager.updateRegisteredAttendance("없음", attendAt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 닉네임입니다.");
        }

        @Test
        @DisplayName("존재하지 않는 출석 기록을 수정하고자 하면 예외가 발생한다.")
        void test2() {
            // given
            Crews crews = new Crews(List.of(new Crew("히로")));
            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(List.of()), crews);
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);

            // when
            assertThatThrownBy(() -> attendanceSystemManager.updateRegisteredAttendance("히로", attendAt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("조건에 해당하는 기록이 존재하지 않습니다.");
        }

        @Test
        @DisplayName("운영 시간이 아닌 시각으로 출석 시각을 바꾸려고 하는 경우 예외가 발생한다.")
        void test3() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
            LocalDateTime newAttendDate = LocalDateTime.of(2024, 12, 2, 7, 0);
            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(List.of(new AttendanceHistory(crew, attendAt))), crews);

            // when
            assertThatThrownBy(() -> attendanceSystemManager.updateRegisteredAttendance(nickname, newAttendDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("운영 시간 내에만 출석할 수 있습니다.");
        }
    }

    @Nested
    class TestForFindAllHistoriesOfCrew {
        @Test
        @DisplayName("존재하지 않는 닉네임으로 크루별 출석 조회를 시도하는 경우 예외가 발생한다.")
        void test1() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));
            LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(
                    new AttendanceHistories(new ArrayList<>()), crews);

            // when
            assertThatThrownBy(() -> attendanceSystemManager.findAllHistoriesOfCrew("없음", attendAt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 닉네임입니다.");
        }

        @Test
        @DisplayName("크루의 출석 기록을 모두 확인한다.")
        void test2() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));

            LocalDateTime firstAttendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
            LocalDateTime secondAttendAt = LocalDateTime.of(2024, 12, 3, 10, 31);
            LocalDateTime thirdAttendAt = LocalDateTime.of(2024, 12, 4, 10, 6);

            AttendanceHistories attendanceHistories = new AttendanceHistories(List.of(
                    new AttendanceHistory(crew, firstAttendAt),
                    new AttendanceHistory(crew, secondAttendAt),
                    new AttendanceHistory(crew, thirdAttendAt)));

            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(attendanceHistories, crews);

            // when
            Map<LocalDateTime, AttendanceType> result = attendanceSystemManager.findAllHistoriesOfCrew(nickname,
                    thirdAttendAt.plusDays(1));

            // then
            assertThat(result).isEqualTo(
                    Map.of(firstAttendAt, AttendanceType.PRESENT, secondAttendAt, AttendanceType.ABSENCE, thirdAttendAt,
                            AttendanceType.LATE)
            );
        }

        @Test
        @DisplayName("존재하지 않는 날짜의 기록에 대해 결석으로 간주한다.")
        void test3() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));

            LocalDateTime firstAttendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
            LocalDateTime thirdAttendAt = LocalDateTime.of(2024, 12, 4, 10, 6);

            AttendanceHistories attendanceHistories = new AttendanceHistories(List.of(
                    new AttendanceHistory(crew, firstAttendAt),
                    new AttendanceHistory(crew, thirdAttendAt)));

            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(attendanceHistories, crews);

            // when
            Map<LocalDateTime, AttendanceType> result = attendanceSystemManager.findAllHistoriesOfCrew(nickname,
                    thirdAttendAt.plusDays(1));

            // then
            assertThat(result.keySet()).contains(LocalDateTime.of(2024, 12, 3, 0, 0));
        }

        @Test
        @DisplayName("조회를 요청한 날짜의 이전 데이터까지만 포함한다.")
        void test4() {
            // given
            String nickname = "히로";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));

            LocalDateTime firstAttendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
            LocalDateTime thirdAttendAt = LocalDateTime.of(2024, 12, 4, 10, 6);

            AttendanceHistories attendanceHistories = new AttendanceHistories(List.of(
                    new AttendanceHistory(crew, firstAttendAt),
                    new AttendanceHistory(crew, thirdAttendAt)));

            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(attendanceHistories, crews);

            // when
            Map<LocalDateTime, AttendanceType> result = attendanceSystemManager.findAllHistoriesOfCrew(nickname,
                    thirdAttendAt.plusDays(1));

            // then
            assertThat(result.keySet())
                    .allMatch(date -> date.isBefore(thirdAttendAt.plusDays(1)));

        }

    }

    @Nested
    class TestForGetExpulsionCandidates {
        @Test
        @DisplayName("패널티 타입이 면담, 경고, 제적인 경우만 반환한다.")
        void test1() {
            // given
            LocalDateTime requestedAt = LocalDateTime.of(2024, 12, 6, 10, 0);
            Crew hero = new Crew("히로");
            Crew hippo = new Crew("히포");
            Crew moru = new Crew("모루");

            AttendanceHistories attendanceHistories = new AttendanceHistories(
                    List.of(
                            new AttendanceHistory(hero, LocalDateTime.of(2024, 12, 3, 10, 0)),
                            new AttendanceHistory(hippo, LocalDateTime.of(2024, 12, 4, 10, 0)),
                            new AttendanceHistory(moru, LocalDateTime.of(2024, 12, 5, 10, 0))
                    ));

            AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(attendanceHistories,
                    new Crews(List.of()));

            // when
            Map<Crew, AttendanceTypeCount> actual = attendanceSystemManager.findExpulsionCandidates(requestedAt);

            // then
            assertThat(actual.keySet()).doesNotContain(moru);
        }
    }

}
