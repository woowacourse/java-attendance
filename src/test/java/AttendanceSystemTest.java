import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.AttendanceSystem;
import domain.RiskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static util.Dates.TODAY;

public class AttendanceSystemTest {
    private AttendanceSystem attendanceSystem;

    @BeforeEach
    void initAttendanceSystem() {
        attendanceSystem = new AttendanceSystem();
    }

    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다")
    @Test
    void attendance_with_name() {
        String name = "두리";
        LocalTime time = LocalTime.of(10, 0);
        attendanceSystem.editAttendance(name, TODAY, time);
        assertThat(attendanceSystem.getAttendanceRecord(name, TODAY)).isEqualTo(time);
    }

    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다2")
    @Test
    void attendance_with_name2() {
        String name = "두리";
        LocalTime time = LocalTime.of(10, 30);
        attendanceSystem.editAttendance(name, TODAY, time);
        assertThat(attendanceSystem.getAttendanceRecord(name, TODAY)).isEqualTo(time);
    }

    @DisplayName("출석하려는 시간이 캠퍼스 운영시간이 아닌 경우 예외를 던진다")
    @Test
    void cannon_attend_if_is_not_operating_hours() {
        String name = "두리";
        LocalTime time = LocalTime.of(7, 0);
        assertThatThrownBy(() -> {
            attendanceSystem.editAttendance(name, TODAY, time);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석하려는 시간이 캠퍼스 운영시간이 아닌 경우 예외를 던진다2")
    @Test
    void cannon_attend_if_is_not_operating_hours2() {
        String name = "두리";
        LocalTime time = LocalTime.of(23, 1);
        assertThatThrownBy(() -> {
            attendanceSystem.editAttendance(name, TODAY, time);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름과 날짜 등교시간을 입력하면 기록을 수정할 수 있다")
    @Test
    void edit_attendance() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 30));
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        assertThat(attendanceSystem.getAttendanceRecord(name, TODAY))
                .isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("수정하려는 시간이 캠퍼스 운영 시간이 아닌 경우 예외를 던진다")
    @Test
    void edit_attendance_in_non_operating_hour() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 30));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(name, TODAY, LocalTime.of(23, 55))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수정하려는 날짜가 주말인 경우 예외를 던진다")
    @Test
    void edit_attendance_in_holiday() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(name, LocalDate.of(2024, 12, 1), LocalTime.of(10, 0)
                )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수정하려는 날짜가 크리스마스인 경우 예외를 던진다")
    @Test
    void edit_attendance_in_christmas() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(name, LocalDate.of(2024, 12, 25), LocalTime.of(10, 0)
                )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("오늘까지의 결석 횟수를 가져온다")
    @Test
    void get_absence_record() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        assertThat(attendanceSystem.getAbsenceCount(name)).isEqualTo(11);
    }

    @DisplayName("30분 초과시 결석이다")
    @Test
    void get_absence_record2() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 31));
        assertThat(attendanceSystem.getAbsenceCount(name)).isEqualTo(12);
    }

    @DisplayName("30분 초과시 결석이다")
    @Test
    void get_absence_record3() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 7));
        assertThat(attendanceSystem.getAbsenceCount(name)).isEqualTo(11);
    }

    @DisplayName("월요일은 1시 시작이다")
    @Test
    void get_absence_record_monday() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 31));
        attendanceSystem.editAttendance(name, LocalDate.of(2024, 12, 2), LocalTime.of(10, 31));
        assertThat(attendanceSystem.getAbsenceCount(name)).isEqualTo(11);
    }

    @DisplayName("오늘까지의 지각 횟수를 가져온다")
    @Test
    void get_tardy_record() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 7));
        assertThat(attendanceSystem.getTardyCount(name)).isEqualTo(1);
    }

    @DisplayName("오늘까지의 지각 횟수를 가져온다2")
    @Test
    void get_tardy_record2() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 7));
        attendanceSystem.editAttendance(name, LocalDate.of(2024, 12, 3), LocalTime.of(10, 7));
        assertThat(attendanceSystem.getTardyCount(name)).isEqualTo(2);
    }

    @DisplayName("월요일 지각 횟수")
    @Test
    void get_tardy_record_monday() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 7));
        attendanceSystem.editAttendance(name, LocalDate.of(2024, 12, 2), LocalTime.of(10, 7));
        assertThat(attendanceSystem.getTardyCount(name)).isEqualTo(1);
    }

    @DisplayName("월요일 지각 횟수2")
    @Test
    void get_tardy_record_monday2() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 7));
        attendanceSystem.editAttendance(name, LocalDate.of(2024, 12, 2), LocalTime.of(13, 7));
        assertThat(attendanceSystem.getTardyCount(name)).isEqualTo(2);
    }

    @DisplayName("출석 횟수를 가져온다")
    @Test
    void get_attend_record() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 5));
        assertThat(attendanceSystem.getAttendCount(name)).isEqualTo(1);
    }

    @DisplayName("월요일에 출석 횟수를 가져온다")
    @Test
    void get_attend_record_monday() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 5));
        attendanceSystem.editAttendance(name, LocalDate.of(2024, 12, 2), LocalTime.of(13, 5));
        assertThat(attendanceSystem.getAttendCount(name)).isEqualTo(2);
    }

    @DisplayName("출석 횟수를 가져온다")
    @Test
    void get_attend_record2() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 5));
        attendanceSystem.editAttendance(name, LocalDate.of(2024, 12, 2), LocalTime.of(13, 7));
        assertThat(attendanceSystem.getAttendCount(name)).isEqualTo(1);
    }

    @DisplayName("결석을 5회 초과로 한 경우 제적 대상자이다")
    @Test
    void expulsion_test() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        assertThat(attendanceSystem.getAbsenceCount(name) + attendanceSystem.getTardyCount(name) / 3).isEqualTo(11);
        assertThat(attendanceSystem.getRisk(name)).isEqualTo(RiskStatus.EXPULSION);
    }

    @DisplayName("결석을 3회이상 5회 이하로 한 경우 면담 대상자이다")
    @Test
    void counseling_test() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.minusDays(5));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(name, date, LocalTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertAll(
                () -> assertThat(attendanceSystem.getAbsenceCount(name) + attendanceSystem.getTardyCount(name) / 3).isEqualTo(3),
                () -> assertThat(attendanceSystem.getRisk(name)).isEqualTo(RiskStatus.COUNSELING)
        );
    }

    @DisplayName("결석을 2회 한경우 경고 대상자이다")
    @Test
    void warning_test() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.minusDays(4));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(name, date, LocalTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertAll(
                () -> assertThat(attendanceSystem.getAbsenceCount(name) + attendanceSystem.getTardyCount(name) / 3).isEqualTo(2),
                () -> assertThat(attendanceSystem.getRisk(name)).isEqualTo(RiskStatus.WARNING)
        );
    }

    @DisplayName("결석을 안한 경우")
    @Test
    void none_test() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY);
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(name, date, LocalTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertAll(
                () -> assertThat(attendanceSystem.getAbsenceCount(name) + attendanceSystem.getTardyCount(name) / 3).isEqualTo(0),
                () -> assertThat(attendanceSystem.getRisk(name)).isEqualTo(RiskStatus.NONE)
        );
    }

    @DisplayName("결석을 한번 한 경우")
    @Test
    void none_test2() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.minusDays(1));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(name, date, LocalTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertAll(
                () -> assertThat(attendanceSystem.getAbsenceCount(name) + attendanceSystem.getTardyCount(name) / 3).isEqualTo(1),
                () -> assertThat(attendanceSystem.getRisk(name)).isEqualTo(RiskStatus.NONE)
        );
    }

    @DisplayName("특정 날짜의 출석 상태를 가져온다_출석")
    @Test
    void attendanceStatusTest() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 0));
        AttendanceBook attendanceBook = attendanceSystem.findByName(name);
        assertThat(attendanceBook.getAttendanceStatus(TODAY)).isEqualTo(AttendanceStatus.ATTEND);
    }

    @DisplayName("특정 날짜의 출석 상태를 가져온다_결석")
    @Test
    void attendanceStatusTest2() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(11, 0));
        AttendanceBook attendanceBook = attendanceSystem.findByName(name);
        assertThat(attendanceBook.getAttendanceStatus(TODAY)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @DisplayName("특정 날짜의 출석 상태를 가져온다_지각")
    @Test
    void attendanceStatusTest3() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 10));
        AttendanceBook attendanceBook = attendanceSystem.findByName(name);
        assertThat(attendanceBook.getAttendanceStatus(TODAY)).isEqualTo(AttendanceStatus.TARDY);
    }

    @DisplayName("출석 상태를 가져온다")
    @Test
    void attendanceStatusTest4() {
        String name = "두리";
        attendanceSystem.editAttendance(name, TODAY, LocalTime.of(10, 10));
        AttendanceBook attendanceBook = attendanceSystem.findByName(name);
        Map<LocalDate, AttendanceStatus> attendanceStatuses = attendanceBook.getAttendanceStatuses();
        assertThat(attendanceStatuses.get(TODAY)).isEqualTo(AttendanceStatus.TARDY);
    }

    @DisplayName("이름이 등록되지 않았으면 예외를 던진다")
    @Test
    void attendanceThrowError() {
        assertThatThrownBy(() ->
                attendanceSystem.findByName("누구"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
