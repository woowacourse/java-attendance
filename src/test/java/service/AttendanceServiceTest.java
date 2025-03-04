package service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.AttendanceBook;
import domain.AttendanceDateTime;
import domain.Crew;
import domain.Penalty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import vo.AttendanceStatusCount;
import vo.DangerCrew;
import vo.ModifyResult;

@DisplayName("출석 서비스에 대한 테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AttendanceServiceTest {

    private AttendanceService service;

    private final Crew crew = new Crew("크루");
    private final LocalDate _20250224_monday = LocalDate.of(2025, 2, 24);
    private final LocalTime _13_00 = LocalTime.of(13, 0);

    @BeforeEach
    void setUp() {
        service = new AttendanceService(new AttendanceBook());
    }

    @Nested
    @DisplayName("크루 조회에 대한 테스트")
    class FindCrew {

        @Test
        void 닉네임을_통해_크루를_조회할_수_있다() {
            service.attend(crew, _20250224_monday, _13_00);
            Crew crew = service.getCrewByNickName("크루");
            assertThat(crew).isEqualTo(new Crew("크루"));
        }

        @Test
        void 한_번도_출석한_적_없는_크루를_조회하면_예외가_발생한다() {
            assertThatThrownBy(() -> service.getCrewByNickName("포포"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 닉네임입니다.");
        }

        @Test
        void 존재하지_않는_닉네임의_크루를_조회하면_예외가_발생한다() {
            service.attend(crew, _20250224_monday, _13_00);
            assertThatThrownBy(() -> service.getCrewByNickName("포포"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 닉네임입니다.");
        }
    }

    @Nested
    @DisplayName("출석 기능에 대한 테스트")
    class Attend {

        @Test
        void 크루와_일시로_출석하면_출석일시_객체를_받는다() {
            AttendanceDateTime result = service.attend(crew, _20250224_monday, _13_00);

            AttendanceDateTime expected = AttendanceDateTime.of(2025, 2, 24, 13, 0);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        void 주말에_출석하려_하면_예외가_발생한다() {
            LocalDate weekend = LocalDate.of(2025, 2, 22);

            assertThatThrownBy(() -> service.attend(crew, weekend, _13_00))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 공휴일에_출석하려_하면_예외가_발생한다() {
            LocalDate holiday = LocalDate.of(2025, 3, 3);

            assertThatThrownBy(() -> service.attend(crew, holiday, _13_00))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({"07:59","23:01"})
        void 운영시간이_아닐때_출석하려_하면_예외가_발생한다(LocalTime timeNotBetweenOperation) {
            assertThatThrownBy(() -> service.attend(crew, _20250224_monday, timeNotBetweenOperation))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("출석 수정에 대한 테스트")
    class Modify {

        @Test
        void 출석_수정_시_수정전과_수정후의_출석일시를_반환한다() {
            service.attend(crew, _20250224_monday, _13_00);

            LocalTime timeToModifiy = LocalTime.of(13, 30);
            ModifyResult result = service.modify(crew, _20250224_monday, timeToModifiy);

            var expectedBefore = AttendanceDateTime.of(2025, 2, 24, 13, 0);
            var expectedAfter = AttendanceDateTime.of(2025, 2, 24, 13, 30);
            assertThat(result.before()).isEqualTo(expectedBefore);
            assertThat(result.after()).isEqualTo(expectedAfter);
        }

        @Test
        void 출석을_수정하려는_날짜가_주말이면_예외가_발생한다() {
            LocalDate weekend = LocalDate.of(2025, 2, 22);
            assertThatThrownBy(() -> service.modify(crew, weekend, _13_00))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 출석을_수정하려는_날짜가_공휴일이면_예외가_발생한다() {
            LocalDate weekend = LocalDate.of(2025, 3, 3);
            assertThatThrownBy(() -> service.modify(crew, weekend, _13_00))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({"07:59","23:01"})
        void 출석을_수정_후의_시간이_운영시간이_아니면_예외가_발생한다(LocalTime timeNotBetweenOperation) {
            service.attend(crew, _20250224_monday, _13_00);

            assertThatThrownBy(() -> service.modify(crew, _20250224_monday, timeNotBetweenOperation))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("크루별 출석 조회에 대한 테스트")
    class FindRecords {

        @Test
        void 두_날짜_사이의_크루의_출석기록을_결석을_포함하여_조회할_수_있다() {
            service.attend(crew, _20250224_monday, _13_00);

            LocalDate fromMonday = LocalDate.of(2025, 2, 24);
            LocalDate toFriday = LocalDate.of(2025, 2, 28);
            List<AttendanceDateTime> records = service.findAllRecordsByCrewBetween(crew, fromMonday, toFriday);

            assertThat(records).hasSize(5);
        }

        @Test
        void 두_날짜_사이의_크루의_출석기록은_주말을_제외하여_제공한다() {
            service.attend(crew, _20250224_monday, _13_00);

            LocalDate fromMonday = LocalDate.of(2025, 2, 24);
            LocalDate toSunday = LocalDate.of(2025, 2, 28);
            List<AttendanceDateTime> records = service.findAllRecordsByCrewBetween(crew, fromMonday, toSunday);

            assertThat(records).hasSize(5);
        }

        @Test
        void 두_날짜_사이의_크루의_출석기록은_공휴일을_제외하여_제공한다() {
            LocalDate _20250304_tuesday = LocalDate.of(2025, 3, 4);
            service.attend(crew, _20250304_tuesday, _13_00);

            LocalDate fromMonday = LocalDate.of(2025, 3, 3);
            LocalDate toWednesday = LocalDate.of(2025, 3, 5);
            List<AttendanceDateTime> records = service.findAllRecordsByCrewBetween(crew, fromMonday, toWednesday);

            // 3월 3일 월요일 : 공휴일
            // 3/3 월요일(공휴일), 3/4 화요일, 3/5 수요일 : 출석해야하는 날 == 2일
            assertThat(records).hasSize(2);
        }

        @Test
        void 출석_상태별_개수를_제공한다() {
            service.attend(crew, _20250224_monday, _13_00);

            var _20250225_tuesday = LocalDate.of(2025, 2, 25);
            var _10_30 = LocalTime.of(10, 30);
            service.attend(crew, _20250225_tuesday, _10_30);

            LocalDate fromMonday = LocalDate.of(2025, 2, 24);
            LocalDate toFriday = LocalDate.of(2025, 2, 28);
            AttendanceStatusCount counts = service.countAttendanceStatusesByCrewBetween(crew, fromMonday, toFriday);

            assertAll(
                () -> assertThat(counts.onTime()).isEqualTo(1),
                () -> assertThat(counts.late()).isEqualTo(1),
                () -> assertThat(counts.absence()).isEqualTo(3)
            );
        }
    }

    @Nested
    @DisplayName("제적 위험자 조회에 대한 테스트")
    class FindDangerCrews {

        private Crew crew1 = new Crew("크루1");
        private Crew crew2 = new Crew("크루2");

        private LocalDate fromMonday = LocalDate.of(2025, 2, 24);
        private LocalDate toWednesday = LocalDate.of(2025, 2, 26);
        private LocalDate _20250225_tuesday = LocalDate.of(2025, 2, 25);

        private LocalTime _10_20 = LocalTime.of(10, 20);

        @BeforeEach
        void setUp() {
            service.attend(crew1, _20250224_monday, _13_00);
            service.attend(crew1, _20250225_tuesday, _10_20);

            service.attend(crew2, _20250225_tuesday, _10_20);
        }

        @Test
        void 제적_위험자들을_조회할_수_있다() {
            List<DangerCrew> dangerCrews = service.findDangerCrews(fromMonday, toWednesday);
            assertThat(dangerCrews).hasSize(1);
        }

        @Test
        void 제적_위험자_조회시_제적위험_크루들의_지각결석_개수와_패널티를_반환한다() {
            List<DangerCrew> dangerCrews = service.findDangerCrews(fromMonday, toWednesday);

            assertThat(dangerCrews).containsExactly(
                new DangerCrew(
                    crew2,
                    1,
                    2,
                    Penalty.WARNING
                )
            );
        }
    }
}
