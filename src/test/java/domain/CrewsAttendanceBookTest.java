package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CrewsAttendanceBookTest {
    private CrewsAttendanceBook repository;

    @BeforeEach
    void setUp() {
        Map<Crew, AttendanceBook> initialAttendances = new HashMap<>();

        initialAttendances.put(new Crew("fora"), new AttendanceBook(createAttendances(Map.of(
                5, LocalTime.of(9, 50),
                6, LocalTime.of(10, 0),
                9, LocalTime.of(12, 50)
        ))));
        initialAttendances.put(new Crew("mingom"), new AttendanceBook(createAttendances(Map.of(
                5, LocalTime.of(9, 50),
                6, LocalTime.of(10, 40),
                9, LocalTime.of(13, 50),
                10, LocalTime.of(10, 40)
        ))));
        initialAttendances.put(new Crew("mungoo"), new AttendanceBook(createAttendances(Map.of(
                5, LocalTime.of(9, 50),
                6, LocalTime.of(10, 40),
                9, LocalTime.of(13, 40),
                10, LocalTime.of(10, 40),
                11, LocalTime.of(10, 40),
                12, LocalTime.of(10, 50),
                13, LocalTime.of(10, 50)
        ))));

        repository = new CrewsAttendanceBook(initialAttendances);
    }

    private List<Attendance> createAttendances(Map<Integer, LocalTime> dayTimeMap) {
        return dayTimeMap.entrySet().stream()
                .map(entry -> new Attendance(LocalDate.of(2024, 12, entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Test
    void 닉네임과_등교_시간을_입력시_출석된다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when
        Attendance attendance = new Attendance(localDate, localTime);

        // then
//        Assertions.assertThat(attendance.getName()).isEqualTo(name);
        Assertions.assertThat(attendance.getLocalTime()).isEqualTo(localTime);
    }

    @Test
    void 출석을_저장한다() {
        // given
        Crew crew = new Crew("fora");
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when
        Attendance attendance = repository.checkIn(crew, localDate, localTime);

        // then
        Assertions.assertThat(attendance.getLocalTime()).isEqualTo(localTime);
    }


    @Nested
    @DisplayName("출석_시간으로_알맞은_출석_상태를_반환한다")
    class AttendanceStateTest {
        @Test
        void 출석했을_경우_출석_상태를_반환한다() {
            // given
            LocalDate localDate = LocalDate.of(2024, 12, 3);
            LocalTime localTime = LocalTime.of(9, 55);

            // when
            AttendanceState state = AttendanceState.findStateBy(localDate, localTime);

            // then
            Assertions.assertThat(state).isEqualTo(AttendanceState.ATTENDANCE);
        }

        @Test
        void 지각했을_경우_지각_상태를_반환한다() {
            // given
            LocalDate localDate = LocalDate.of(2024, 12, 3);
            LocalTime localTime = LocalTime.of(10, 6);

            // when
            AttendanceState state = AttendanceState.findStateBy(localDate, localTime);

            // then
            Assertions.assertThat(state).isEqualTo(AttendanceState.LATENESS);
        }

        @Test
        void 결석했을_경우_결석_상태를_반환한다() {
            // given
            LocalDate localDate = LocalDate.of(2024, 12, 3);
            LocalTime localTime = LocalTime.of(10, 31);

            // when
            AttendanceState state = AttendanceState.findStateBy(localDate, localTime);

            // then
            Assertions.assertThat(state).isEqualTo(AttendanceState.ABSENCE);
        }
    }

    @Test
    void 존재하는_않는_크루는_출석할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    Crew crew = repository.getCrewByName("dompoo");
                    repository.checkIn(crew, localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하는 크루의 닉네임을 입력해주세요.");
    }

    @Test
    void 닉네임과_수정할_날짜와_등교_시간을_입력시_출석기록이_수정된다() {
        // given
        Crew crew = new Crew("fora");
        LocalDate localDate = LocalDate.of(2024, 12, 5);
        LocalTime localTime = LocalTime.of(10, 10);

        // when
        Attendance attendance = repository.update(crew, localDate, localTime);

        // then
        Assertions.assertThat(attendance.getLocalTime()).isEqualTo(localTime);
    }

    @Test
    void 존재하는_않는_크루는_출석을_수정할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    Crew crew = repository.getCrewByName("dompoo");
                    repository.update(crew, localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하는 크루의 닉네임을 입력해주세요.");
    }

    @Test
    void 주말_및_공휴일에는_출석을_수정할_수_없다() {
        // given
        Crew crew = new Crew("fora");
        LocalDate localDate = LocalDate.of(2024, 12, 1);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    repository.update(crew, localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 및 공휴일에는 출석할 수 없습니다.");
    }

    @Nested
    @DisplayName("출석_수정_후_변경_전과_변경_후의_출석_기록을_확인할_수_있다")
    class AttendanceUpdateTest {
        Crew crew = new Crew("fora");

        LocalDate localDate = LocalDate.of(2024, 12, 26);

        LocalTime attendanceLocalTime = LocalTime.of(10, 0);
        LocalTime lateLocalTime = LocalTime.of(10, 6);
        LocalTime absenceLocalTime = LocalTime.of(10, 31);

        @Test
        void 출석에서_지각() {
            // given
            repository.checkIn(crew, localDate, attendanceLocalTime);

            // when
            Attendance afterAttendance = repository.update(crew, localDate, lateLocalTime);
            AttendanceState afterState = AttendanceState.findStateBy(afterAttendance.getLocalDate(),
                    afterAttendance.getLocalTime());

            // then
//            Assertions.assertThat(beforeState.getState()).isEqualTo("출석");
            Assertions.assertThat(afterState).isEqualTo(AttendanceState.LATENESS);
        }

        @Test
        void 출석에서_결석() {
            // given
            repository.checkIn(crew, localDate, attendanceLocalTime);

            // when
            Attendance afterAttendance = repository.update(crew, localDate, absenceLocalTime);
            AttendanceState afterState = AttendanceState.findStateBy(afterAttendance.getLocalDate(),
                    afterAttendance.getLocalTime());

            // then
//            Assertions.assertThat(beforeState.getState()).isEqualTo("출석");
            Assertions.assertThat(afterState).isEqualTo(AttendanceState.ABSENCE);
        }

        @Test
        void 지각에서_출석() {
            // given
            repository.checkIn(crew, localDate, lateLocalTime);

            // when
            Attendance afterAttendance = repository.update(crew, localDate, attendanceLocalTime);
            AttendanceState afterState = AttendanceState.findStateBy(afterAttendance.getLocalDate(),
                    afterAttendance.getLocalTime());

            // then
//            Assertions.assertThat(beforeState.getState()).isEqualTo("지각");
            Assertions.assertThat(afterState).isEqualTo(AttendanceState.ATTENDANCE);
        }

        @Test
        void 지각에서_결석() {
            // given
            repository.checkIn(crew, localDate, lateLocalTime);

            // when
            Attendance afterAttendance = repository.update(crew, localDate, absenceLocalTime);
            AttendanceState afterState = AttendanceState.findStateBy(afterAttendance.getLocalDate(),
                    afterAttendance.getLocalTime());

            // then
//            Assertions.assertThat(beforeState.getState()).isEqualTo("지각");
            Assertions.assertThat(afterState).isEqualTo(AttendanceState.ABSENCE);
        }

        @Test
        void 결석에서_출석() {
            // given
            repository.checkIn(crew, localDate, absenceLocalTime);

            // when
            Attendance afterAttendance = repository.update(crew, localDate, attendanceLocalTime);
            AttendanceState afterState = AttendanceState.findStateBy(afterAttendance.getLocalDate(),
                    afterAttendance.getLocalTime());

            // then
//            Assertions.assertThat(beforeState.getState()).isEqualTo("결석");
            Assertions.assertThat(afterState).isEqualTo(AttendanceState.ATTENDANCE);
        }

        @Test
        void 결석에서_지각() {
            // given
            repository.checkIn(crew, localDate, absenceLocalTime);

            // when
            Attendance afterAttendance = repository.update(crew, localDate, lateLocalTime);
            AttendanceState afterState = AttendanceState.findStateBy(afterAttendance.getLocalDate(),
                    afterAttendance.getLocalTime());

            // then
//            Assertions.assertThat(beforeState.getState()).isEqualTo("결석");
            Assertions.assertThat(afterState).isEqualTo(AttendanceState.LATENESS);
        }
    }

    @Test
    void 전날까지의_출석_기록을_바탕으로_제적_위험자를_확인할_수_있다() {
        // when
        Set<PenaltyBook> penaltyBooks = repository.calculatePenaltyBooks();

        PenaltyBook mingomPenalty = penaltyBooks.stream()
                .filter(penalty -> penalty.crew().getName().equals("mingom"))
                .findFirst()
                .orElse(null);

        PenaltyBook mungooPenalty = penaltyBooks.stream()
                .filter(penalty -> penalty.crew().getName().equals("mungoo"))
                .findFirst()
                .orElse(null);

        // then
        Assertions.assertThat(penaltyBooks.size()).isEqualTo(2);
        Assertions.assertThat(mingomPenalty.penaltyType()).isEqualTo(PenaltyType.INTERVIEW);
        Assertions.assertThat(mungooPenalty.penaltyType()).isEqualTo(PenaltyType.EXPULSION);
    }
}
