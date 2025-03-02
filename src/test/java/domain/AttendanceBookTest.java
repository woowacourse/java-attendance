package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceBookTest {
    AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        Attendance attendance1 = new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(9, 55));
        Attendance attendance2 = new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
        Attendance attendance3 = new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10));
        Attendance attendance4 = new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 55));
        attendanceBook = new AttendanceBook(
                new ArrayList<>(List.of(attendance1, attendance2, attendance3, attendance4)));
    }

    @Test
    void 이미_출석한_경우_다시_출석할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 5);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    attendanceBook.validateDuplicateCheckIn(localDate);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 크루입니다.");
    }

    @Test
    void 주말_및_공휴일에는_출석할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    attendanceBook.validateWeekDay(localDate);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 및 공휴일에는 출석할 수 없습니다.");
    }

    @Test
    void 오늘_이후의_날짜는_출석할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2025, 7, 25);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    attendanceBook.validateAfterToday(localDate);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정할 수 없는 날짜입니다.");
    }

    @Test
    void 모든_출석_기록을_확인할_수_있다() {
        // when
        List<Attendance> foraAttendanceBook = attendanceBook.getAttendanceBook();

        // then
        Assertions.assertThat(foraAttendanceBook).containsOnly(
                new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(9, 55)),
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)),
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 55)),
                new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(0, 0)),
                new Attendance(LocalDate.of(2024, 12, 9), LocalTime.of(0, 0)),
                new Attendance(LocalDate.of(2024, 12, 10), LocalTime.of(0, 0)),
                new Attendance(LocalDate.of(2024, 12, 11), LocalTime.of(0, 0)),
                new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(0, 0)),
                new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(0, 0))
        );
    }

    @Test
    void 결석을_포함한_전날까지의_모든_출석_기록을_확인할_수_있다() {
        // when
        List<Attendance> foraAttendanceBook = attendanceBook.getAttendanceBook();

        // then
        Assertions.assertThat(foraAttendanceBook.size()).isEqualTo(10);
    }

    @Test
    void 한_크루의_출석_지각_결석_횟수를_확인할_수_있다() {
        // when
        AttendanceStateCount attendanceStateCount = attendanceBook.calculateState();

        // then
        Assertions.assertThat(attendanceStateCount.attendance()).isEqualTo(2);
        Assertions.assertThat(attendanceStateCount.lateness()).isEqualTo(1);
        Assertions.assertThat(attendanceStateCount.absence()).isEqualTo(1);
    }

    @Nested
    @DisplayName("한_크루의_제적_면담_경고_대상자_여부를_확인할_수_있다")
    class dismissedTest {
        @ParameterizedTest
        @CsvSource({
                "5, 6, 0,WARNING",
                "5,5,1,WARNING",
                "5,3,1,WARNING",
                "5,0,2,WARNING"
        })
        void 경고_대상자인지_확인할_수_있다(int attendance, int lateness, int absence, PenaltyType expectedPenaltyType) {
            // given
            AttendanceStateCount attendanceStateCount = new AttendanceStateCount(attendance, lateness, absence);

            // when
            PenaltyType penaltyType = attendanceBook.calculatePenaltyType(attendanceStateCount);

            // then
            Assertions.assertThat(penaltyType).isEqualTo(expectedPenaltyType);
        }

        @ParameterizedTest
        @CsvSource({
                "5, 9, 0,INTERVIEW",
                "5,6,1,INTERVIEW",
                "5,6,2,INTERVIEW",
                "5,3,2,INTERVIEW",
                "5,3,3,INTERVIEW",
                "5,6,3,INTERVIEW",
                "5,13,1,INTERVIEW"
        })
        void 면담_대상자인지_확인할_수_있다(int attendance, int lateness, int absence, PenaltyType expectedPenaltyType) {
            // given
            AttendanceStateCount attendanceStateCount = new AttendanceStateCount(attendance, lateness, absence);

            // when
            PenaltyType penaltyType = attendanceBook.calculatePenaltyType(attendanceStateCount);

            // then
            Assertions.assertThat(penaltyType).isEqualTo(expectedPenaltyType);
        }

        @ParameterizedTest
        @CsvSource({
                "5, 18, 0,EXPULSION",
                "5,15,1,EXPULSION",
                "5,12,2,EXPULSION",
                "5,9,3,EXPULSION",
                "5,6,4,EXPULSION",
                "5,3,5,EXPULSION",
                "5,0,6,EXPULSION"
        })
        void 제적_대상자인지_확인할_수_있다(int attendance, int lateness, int absence, PenaltyType expectedPenaltyType) {
            // given
            AttendanceStateCount attendanceStateCount = new AttendanceStateCount(attendance, lateness, absence);

            // when
            PenaltyType penaltyType = attendanceBook.calculatePenaltyType(attendanceStateCount);

            // then
            Assertions.assertThat(penaltyType).isEqualTo(expectedPenaltyType);
        }
    }
}
