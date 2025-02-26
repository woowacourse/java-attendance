package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancesTest {
    private static final int ATTENDANCE_TOTAL_DAYS = 17;

    private final Crews crews = new Crews();
    private final Attendances attendances = new Attendances();

    @BeforeEach
    void setUp() {
        List<List<String>> attendanceRecords = List.of(
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
        );
        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);
    }

    @Test
    @DisplayName("기능: 크루의 출석 데이터 초기화 확인")
    void checkTotalAttendancesForCrew() {
        Crew crew = new Crew("쿠키");
        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
        assertThat(crewAttendances.size()).isEqualTo(ATTENDANCE_TOTAL_DAYS);
    }

    @Test
    @DisplayName("기능: 특정 크루의 출석 일자에 대한 출석 날짜 조회")
    void checkAttendanceDateForSpecificCrewDate() {
        Attendance crewAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"), LocalDate.of(2025, 2, 19)
        );
        assertThat(crewAttendance.getDate()).isEqualTo(LocalDate.of(2025, 2, 19));
    }

    @Test
    @DisplayName("기능: 특정 크루의 출석 일자에 대한 출석 유형 조회")
    void checkAttendanceTypeForSpecificCrewDate() {
        Attendance crewAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"), LocalDate.of(2025, 2, 19)
        );
        assertThat(crewAttendance.getType()).isEqualTo(AttendanceType.ABSENT);
    }

    @Test
    @DisplayName("기능: 특정 크루의 출석 일자에 대한 출석 시간 문자열 조회")
    void checkAttendanceTimeValueForSpecificCrewDate() {
        Attendance crewAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"), LocalDate.of(2025, 2, 19)
        );
        assertThat(crewAttendance.getTimeValue()).isEqualTo("10:34");
    }

    @Test
    @DisplayName("기능: 크루 출석 내역 변경에 따른 출석 날짜 수정 확인")
    void checkModifiedCrewAttendanceDate() {
        attendances.modifyAttendances(
                new Crew("쿠키"),
                LocalDateTime.of(2025, 2, 19, 10, 0, 0)
        );

        Attendance crewAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"),
                LocalDate.of(2025, 2, 19)
        );
        assertThat(crewAttendance.getDate()).isEqualTo(LocalDate.of(2025, 2, 19));
    }

    @Test
    @DisplayName("기능: 크루 출석 내역 변경에 따른 출석 시간 수정 확인")
    void checkModifiedCrewAttendanceTime() {
        attendances.modifyAttendances(
                new Crew("쿠키"),
                LocalDateTime.of(2025, 2, 19, 10, 0, 0)
        );

        Attendance crewAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"),
                LocalDate.of(2025, 2, 19)
        );
        assertThat(crewAttendance.getTimeValue()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("기능: 크루 출석 내역 변경에 따른 출석 상태 수정 확인")
    void checkModifiedCrewAttendanceType() {
        attendances.modifyAttendances(
                new Crew("쿠키"),
                LocalDateTime.of(2025, 2, 19, 10, 0, 0)
        );

        Attendance crewAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"),
                LocalDate.of(2025, 2, 19)
        );
        assertThat(crewAttendance.getType()).isEqualTo(AttendanceType.SAFE);
    }

    @Test
    @DisplayName("예외: 주말의 존재하지 않는 출석 기록 조회 시 예외 발생")
    void causeExceptionForNonExistentAttendanceDate() {
        Crew crew = new Crew("초코");
        assertThatThrownBy(() -> attendances.findMatchCrewDate(crew, LocalDate.of(2025, 2, 23)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예와: 이미 존재하는 출석 기록에 대한 출석 확인 시도 시 예외 발생")
    void causeExceptionForDuplicateAttendance() {
        Crew crew = new Crew("쿠키");
        assertThatThrownBy(() -> attendances.hasCheckedAttendance(crew, LocalDate.of(2025, 2, 19)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: 동일한 출석 시간으로 변경 시도 시 예외 발생")
    void causeExceptionForSameTimeAttendanceModification() {
        Crew crew = new Crew("빙봉");
        assertThatThrownBy(() -> attendances.modifyAttendances(crew, LocalDateTime.of(2025, 2, 18, 10, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
