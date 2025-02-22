package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendancesTest {
    private static final int ATTENDANCE_TOTAL_DAYS = 15;

    private final Crews crews = new Crews();
    private final Attendances attendances = new Attendances();

    void setUp(List<List<String>> attendanceRecords) {
        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);
    }

    @DisplayName("기능: 크루별 출석 데이터 초기화와 조회")
    @MethodSource("provideAttendanceRecords")
    @ParameterizedTest
    void initAndFindCrewAttendances(List<List<String>> attendanceRecords) {
        setUp(attendanceRecords);

        Crew crew = new Crew("쿠키");
        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
        assertThat(crewAttendances.size()).isEqualTo(ATTENDANCE_TOTAL_DAYS);

        Attendance crewAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"),
                LocalDate.of(2025, 2, 19)
        );
        assertAll(
                () -> assertThat(crewAttendance.getType()).isEqualTo(AttendanceType.ABSENT),
                () -> assertThat(crewAttendance.getDate()).isEqualTo(LocalDate.of(2025, 2, 19)),
                () -> assertThat(crewAttendance.getTimeValue()).isEqualTo("10:34")
        );
    }

    @DisplayName("기능: 특정 크루의 해당 출석 날짜에 대한 출석 기록을 수정")
    @MethodSource("provideAttendanceRecords")
    @ParameterizedTest
    void modifyAttendanceWithNewTime(List<List<String>> attendanceRecords) {
        setUp(attendanceRecords);

        attendances.modifyAttendances(
                new Crew("쿠키"),
                LocalDateTime.of(2025, 2, 19, 10, 0, 0)
        );
        Attendance crewAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"),
                LocalDate.of(2025, 2, 19)
        );

        assertAll(
                () -> assertThat(crewAttendance.getType()).isEqualTo(AttendanceType.SAFE),
                () -> assertThat(crewAttendance.getDate()).isEqualTo(LocalDate.of(2025, 2, 19)),
                () -> assertThat(crewAttendance.getTimeValue()).isEqualTo("10:00")
        );
    }

    @DisplayName("예외: 존재하지 않는 출석 날짜 조회, 크루의 중복 출석 확인 및 동일한 출석 시간 수정에 대한 처리")
    @MethodSource("provideAttendanceRecords")
    @ParameterizedTest
    void test(List<List<String>> attendanceRecords) {
        setUp(attendanceRecords);

        Crew crew1 = new Crew("초코");
        assertThatThrownBy(() -> attendances.findMatchCrewDate(crew1, LocalDate.of(2025, 2, 19)))
                .isInstanceOf(IllegalArgumentException.class);

        Crew crew2 = new Crew("쿠키");
        assertThatThrownBy(() -> attendances.hasCheckedAttendance(crew2, LocalDate.of(2025, 2, 19)))
                .isInstanceOf(IllegalArgumentException.class);

        Crew crew3 = new Crew("빙봉");
        assertThatThrownBy(() -> attendances.modifyAttendances(crew3, LocalDateTime.of(2025, 2, 18, 10, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideAttendanceRecords() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                List.of("쿠키", "2025-02-19 10:34"),
                                List.of("쿠키", "2025-02-18 10:00"),
                                List.of("빙봉", "2025-02-18 10:01"),
                                List.of("쿠키", "2025-02-17 13:03"),
                                List.of("빙티", "2025-02-17 13:04"),
                                List.of("쿠키", "2025-02-14 10:02"),
                                List.of("쿠키", "2025-02-13 10:07"),
                                List.of("짱수", "2025-02-13 10:08"),
                                List.of("쿠키", "2025-02-12 10:01"),
                                List.of("쿠키", "2025-02-11 10:00"),
                                List.of("쿠키", "2025-02-10 13:09")
                        )
                )
        );
    }
}
