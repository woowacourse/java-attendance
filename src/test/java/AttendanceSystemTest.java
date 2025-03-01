import domain.AttendanceSystem;
import domain.Crew;
import domain.RiskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static util.Dates.TODAY;

public class AttendanceSystemTest {
    private final AttendanceSystem attendanceSystem = new AttendanceSystem();

    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다")
    @Test
    void attendance_with_crew() {
        Crew crew = new Crew("두리");
        LocalTime time = LocalTime.of(10, 0);
        attendanceSystem.editAttendance(crew, TODAY, time);
        assertThat(attendanceSystem.getAttendanceRecord(crew, TODAY)).isEqualTo(time);
    }

    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다2")
    @Test
    void attendance_with_crew2() {
        Crew crew = new Crew("두리");
        LocalTime time = LocalTime.of(10, 30);
        attendanceSystem.editAttendance(crew, TODAY, time);
        assertThat(attendanceSystem.getAttendanceRecord(crew, TODAY)).isEqualTo(time);
    }

    @DisplayName("이미 출석한 경우 다시 출석할 수 없다")
    @Test
    void cannot_attend_if_already_attend() {
        Crew crew = new Crew("두리");
        LocalTime time = LocalTime.of(10, 0);
        attendanceSystem.editAttendance(crew, TODAY, time);
        assertThatThrownBy(() -> {
            attendanceSystem.attendance(crew, time);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석하려는 시간이 캠퍼스 운영시간이 아닌 경우 예외를 던진다")
    @Test
    void cannon_attend_if_is_not_operating_hours() {
        Crew crew = new Crew("두리");
        LocalTime time = LocalTime.of(7, 0);
        assertThatThrownBy(() -> {
            attendanceSystem.editAttendance(crew, TODAY, time);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석하려는 시간이 캠퍼스 운영시간이 아닌 경우 예외를 던진다2")
    @Test
    void cannon_attend_if_is_not_operating_hours2() {
        Crew crew = new Crew("두리");
        LocalTime time = LocalTime.of(23, 1);
        assertThatThrownBy(() -> {
            attendanceSystem.editAttendance(crew, TODAY, time);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름과 날짜 등교시간을 입력하면 기록을 수정할 수 있다")
    @Test
    void edit_attendance() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 30));
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        assertThat(attendanceSystem.getAttendanceRecord(crew, TODAY))
                .isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("수정하려는 시간이 캠퍼스 운영 시간이 아닌 경우 예외를 던진다")
    @Test
    void edit_attendance_in_non_operating_hour() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 30));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(23, 55))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수정하려는 날짜가 주말인 경우 예외를 던진다")
    @Test
    void edit_attendance_in_holiday() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(crew, LocalDate.of(2024, 12, 1), LocalTime.of(10, 0)
                )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수정하려는 날짜가 크리스마스인 경우 예외를 던진다")
    @Test
    void edit_attendance_in_christmas() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(crew, LocalDate.of(2024, 12, 25), LocalTime.of(10, 0)
                )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("오늘까지의 결석 횟수를 가져온다")
    @Test
    void get_absence_record() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        assertThat(attendanceSystem.getAbsenceCount(crew)).isEqualTo(11);
    }

    @DisplayName("30분 초과시 결석이다")
    @Test
    void get_absence_record2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 31));
        assertThat(attendanceSystem.getAbsenceCount(crew)).isEqualTo(12);
    }

    @DisplayName("30분 초과시 결석이다")
    @Test
    void get_absence_record3() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 7));
        assertThat(attendanceSystem.getAbsenceCount(crew)).isEqualTo(11);
    }

    @DisplayName("월요일은 1시 시작이다")
    @Test
    void get_absence_record_monday() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 31));
        attendanceSystem.editAttendance(crew, LocalDate.of(2024, 12, 2), LocalTime.of(10, 31));
        assertThat(attendanceSystem.getAbsenceCount(crew)).isEqualTo(11);
    }

    @DisplayName("오늘까지의 지각 횟수를 가져온다")
    @Test
    void get_tardy_record() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 7));
        assertThat(attendanceSystem.getTardyCount(crew)).isEqualTo(1);
    }

    @DisplayName("오늘까지의 지각 횟수를 가져온다2")
    @Test
    void get_tardy_record2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 7));
        attendanceSystem.editAttendance(crew, LocalDate.of(2024, 12, 3), LocalTime.of(10, 7));
        assertThat(attendanceSystem.getTardyCount(crew)).isEqualTo(2);
    }

    @DisplayName("월요일 지각 횟수")
    @Test
    void get_tardy_record_monday() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 7));
        attendanceSystem.editAttendance(crew, LocalDate.of(2024, 12, 2), LocalTime.of(10, 7));
        assertThat(attendanceSystem.getTardyCount(crew)).isEqualTo(1);
    }

    @DisplayName("월요일 지각 횟수2")
    @Test
    void get_tardy_record_monday2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 7));
        attendanceSystem.editAttendance(crew, LocalDate.of(2024, 12, 2), LocalTime.of(13, 7));
        assertThat(attendanceSystem.getTardyCount(crew)).isEqualTo(2);
    }

    @DisplayName("출석 횟수를 가져온다")
    @Test
    void get_attend_record() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 5));
        assertThat(attendanceSystem.getAttendCount(crew)).isEqualTo(1);
    }

    @DisplayName("월요일에 출석 횟수를 가져온다")
    @Test
    void get_attend_record_monday() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 5));
        attendanceSystem.editAttendance(crew, LocalDate.of(2024, 12, 2), LocalTime.of(13, 5));
        assertThat(attendanceSystem.getAttendCount(crew)).isEqualTo(2);
    }

    @DisplayName("출석 횟수를 가져온다")
    @Test
    void get_attend_record2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 5));
        attendanceSystem.editAttendance(crew, LocalDate.of(2024, 12, 2), LocalTime.of(13, 7));
        assertThat(attendanceSystem.getAttendCount(crew)).isEqualTo(1);
    }

    @DisplayName("결석을 5회 초과로 한 경우 제적 대상자이다")
    @Test
    void expulsion_test() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        assertThat(attendanceSystem.getAbsenceCount(crew) + attendanceSystem.getTardyCount(crew) / 3).isEqualTo(11);
        assertThat(attendanceSystem.getRisk(crew)).isEqualTo(RiskStatus.EXPULSION);
    }

    @DisplayName("결석을 3회이상 5회 이하로 한 경우 면담 대상자이다")
    @Test
    void counseling_test() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.minusDays(5));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, date, LocalTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertAll(
                () -> assertThat(attendanceSystem.getAbsenceCount(crew) + attendanceSystem.getTardyCount(crew) / 3).isEqualTo(3),
                () -> assertThat(attendanceSystem.getRisk(crew)).isEqualTo(RiskStatus.COUNSELING)
        );
    }

    @DisplayName("결석을 2회 한경우 경고 대상자이다")
    @Test
    void warning_test() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.minusDays(4));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, date, LocalTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertAll(
                () -> assertThat(attendanceSystem.getAbsenceCount(crew) + attendanceSystem.getTardyCount(crew) / 3).isEqualTo(2),
                () -> assertThat(attendanceSystem.getRisk(crew)).isEqualTo(RiskStatus.WARNING)
        );
    }

    @DisplayName("결석을 안한 경우")
    @Test
    void none_test() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY);
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, date, LocalTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertAll(
                () -> assertThat(attendanceSystem.getAbsenceCount(crew) + attendanceSystem.getTardyCount(crew) / 3).isEqualTo(0),
                () -> assertThat(attendanceSystem.getRisk(crew)).isEqualTo(RiskStatus.NONE)
        );
    }

    @DisplayName("결석을 한번 한 경우")
    @Test
    void none_test2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, LocalTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.minusDays(1));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, date, LocalTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertAll(
                () -> assertThat(attendanceSystem.getAbsenceCount(crew) + attendanceSystem.getTardyCount(crew) / 3).isEqualTo(1),
                () -> assertThat(attendanceSystem.getRisk(crew)).isEqualTo(RiskStatus.NONE)
        );
    }
}
