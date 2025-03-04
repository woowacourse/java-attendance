import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.AbsentPenalty;
import model.Attendance;
import model.AttendanceBook;
import model.CrewAttendances;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewAttendanceTest {

    @DisplayName("닉네임을 통해 해당 크루의 출석 기록을 가져올 수 있다.")
    @Test
    void nickname_to_attendances() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new CrewAttendances("율무",
                        List.of(
                                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 3))
                        )),
                new CrewAttendances("열무",
                        List.of(new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0))))
        ));
        final var nickname = "율무";

        // when
        final var crewAttendances = attendanceBook.findCrewAttendance(nickname);

        // then
        Assertions.assertThat(crewAttendances.allAttendanceCount())
                .isEqualTo(2);
    }

    @DisplayName("없는 닉네임 입력 시 예외를 발생한다.")
    @Test
    void not_exist_nickname_exception() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new CrewAttendances("율무",
                        List.of(
                                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 3)),
                                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 4))
                        ))
        ));
        final var nickname = "열무";

        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceBook.findCrewAttendance(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @DisplayName("등교날이지만 출석 기록이 없는 날짜도 결석 횟수에 포함된다.")
    @Test
    void calculate_absent_count_but_none_record() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 6);
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new CrewAttendances("율무",
                        List.of(
                                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 33)),
                                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 31))
                        ))
        ));
        CrewAttendances crewAttendances = attendanceBook.findCrewAttendance("율무");

        // when
        final var absentCount = crewAttendances.calculateAbsentCountUntilDate(today);

        // then
        Assertions.assertThat(absentCount)
                .isEqualTo(4);
    }

    @DisplayName("주말은 결석 횟수 계산에서 제외한다.")
    @Test
    void calculate_absent_count_except_weekend() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 8);
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new CrewAttendances("율무", List.of(
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 33)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 31))
                ))
        ));
        CrewAttendances crewAttendances = attendanceBook.findCrewAttendance("율무");

        // when
        final var absentCount = crewAttendances.calculateAbsentCountUntilDate(today);

        // then
        Assertions.assertThat(absentCount)
                .isEqualTo(5);
    }

    @DisplayName("공휴일은 결석 횟수 계산에서 제외한다.")
    @Test
    void calculate_absent_count_except_holiday() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 26);
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new CrewAttendances("율무", List.of(
                        new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 3)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 3)),
                        new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 3)),
                        new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(10, 3))
                ))
        ));
        CrewAttendances crewAttendances = attendanceBook.findCrewAttendance("율무");

        // when
        final var absentCount = crewAttendances.calculateAbsentCountUntilDate(today);

        // then
        Assertions.assertThat(absentCount)
                .isEqualTo(12);
    }

    @DisplayName("닉네임을 통해 크루의 지각 횟수를 계산할 수 있다.")
    @Test
    void calculate_late_count_by_nickname() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 5);
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new CrewAttendances("율무", List.of(
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10))
                ))
        ));
        CrewAttendances crewAttendances = attendanceBook.findCrewAttendance("율무");

        // when
        final var lateCount = crewAttendances.calculateLateCountUntilDate(today);

        // then
        Assertions.assertThat(lateCount)
                .isEqualTo(2);
    }

    @DisplayName("닉네임을 통해 크루의 출석 횟수를 계산할 수 있다.")
    @Test
    void calculate_attend_count_by_nickname() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 5);
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new CrewAttendances("율무", List.of(
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(9, 47))
                ))
        ));
        CrewAttendances crewAttendances = attendanceBook.findCrewAttendance("율무");

        // when
        final var attendCount = crewAttendances.calculateAttendCountUntilDate(today);

        // then
        Assertions.assertThat(attendCount)
                .isEqualTo(2);
    }

    @DisplayName("크루의 결석 패널티를 결정한다.")
    @Test
    void determine_crew_absent_penalty() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 5);
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new CrewAttendances("율무", List.of(
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 31)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 35))
                ))
        ));
        CrewAttendances crewAttendances = attendanceBook.findCrewAttendance("율무");

        // when
        final var result = crewAttendances.determineAttendPenalty(today);

        // then
        Assertions.assertThat(result)
                .isEqualTo(AbsentPenalty.INTERVIEW);
    }
}
