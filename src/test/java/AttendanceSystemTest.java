import domain.AttendanceBook;
import domain.AttendanceDate;
import domain.AttendanceStatus;
import domain.AttendanceStatuses;
import domain.AttendanceSystem;
import domain.AttendanceTime;
import domain.Crew;
import domain.RiskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Dates;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AttendanceSystemTest {
    private final AttendanceSystem attendanceSystem = new AttendanceSystem();
    private final AttendanceDate TODAY = new AttendanceDate(Dates.TODAY);

    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다")
    @Test
    void attendance_with_crew() {
        Crew crew = new Crew("두리");
        AttendanceTime time = AttendanceTime.of(10, 0);
        attendanceSystem.editAttendance(crew, TODAY, time);
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceTimeByDate(TODAY)).isPresent().contains(time);
    }

    @DisplayName("출석하려는 시간이 캠퍼스 운영시간이 아닌 경우 예외를 던진다")
    @Test
    void cannon_attend_if_is_not_operating_hours() {
        Crew crew = new Crew("두리");
        assertThatThrownBy(() -> {
            AttendanceTime.of(7, 0);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석하려는 시간이 캠퍼스 운영시간이 아닌 경우 예외를 던진다2")
    @Test
    void cannon_attend_if_is_not_operating_hours2() {
        Crew crew = new Crew("두리");
        assertThatThrownBy(() -> {
            AttendanceTime.of(23, 1);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름과 날짜 등교시간을 입력하면 기록을 수정할 수 있다")
    @Test
    void edit_attendance() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 30));
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceTimeByDate(TODAY)).isPresent()
                .contains(AttendanceTime.of(10, 0));
    }

    @DisplayName("수정하려는 시간이 캠퍼스 운영 시간이 아닌 경우 예외를 던진다")
    @Test
    void edit_attendance_in_non_operating_hour() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 30));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(23, 55))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수정하려는 날짜가 주말인 경우 예외를 던진다")
    @Test
    void edit_attendance_in_holiday() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(crew, AttendanceDate.of(2024, 12, 1), AttendanceTime.of(10, 0)
                )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수정하려는 날짜가 크리스마스인 경우 예외를 던진다")
    @Test
    void edit_attendance_in_christmas() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        assertThatThrownBy(() ->
                attendanceSystem.editAttendance(crew, AttendanceDate.of(2024, 12, 25), AttendanceTime.of(10, 0)
                )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("오늘까지의 결석 횟수를 가져온다")
    @Test
    void get_absence_record() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getAbsenceCount()).isEqualTo(11);
    }

    @DisplayName("30분 초과시 결석이다")
    @Test
    void get_absence_record2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 31));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getAbsenceCount()).isEqualTo(12);
    }

    @DisplayName("30분 초과시 결석이다")
    @Test
    void get_absence_record3() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 7));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getAbsenceCount()).isEqualTo(11);
    }

    @DisplayName("월요일은 1시 시작이다")
    @Test
    void get_absence_record_monday() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 31));
        attendanceSystem.editAttendance(crew, AttendanceDate.of(2024, 12, 2), AttendanceTime.of(10, 31));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getAbsenceCount()).isEqualTo(11);
    }

    @DisplayName("오늘까지의 지각 횟수를 가져온다")
    @Test
    void get_tardy_record() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 7));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getTardyCount()).isEqualTo(1);
    }

    @DisplayName("오늘까지의 지각 횟수를 가져온다2")
    @Test
    void get_tardy_record2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 7));
        attendanceSystem.editAttendance(crew, AttendanceDate.of(2024, 12, 3), AttendanceTime.of(10, 7));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getTardyCount()).isEqualTo(2);
    }

    @DisplayName("월요일은 10시에 5분 늦어도 지각이 아니다")
    @Test
    void get_tardy_record_monday() {
        Crew crew = new Crew("두리");
        AttendanceDate monday = AttendanceDate.of(2024, 12, 2);
        AttendanceDate tuesday = AttendanceDate.of(2024, 12, 2);
        AttendanceTime nonMondayTardyTime = AttendanceTime.of(10, 7);
        attendanceSystem.editAttendance(crew, tuesday, nonMondayTardyTime);
        attendanceSystem.editAttendance(crew, monday, nonMondayTardyTime);
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getTardyCount()).isEqualTo(1);
    }

    @DisplayName("월요일은 13시에 5분 늦으면 지각이다")
    @Test
    void get_tardy_record_monday2() {
        Crew crew = new Crew("두리");
        AttendanceTime nonMondayTardyTime = AttendanceTime.of(10, 7);
        attendanceSystem.editAttendance(crew, TODAY, nonMondayTardyTime);
        AttendanceTime mondayTardyTime = AttendanceTime.of(13, 7);
        attendanceSystem.editAttendance(crew, AttendanceDate.of(2024, 12, 2), mondayTardyTime);
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getTardyCount()).isEqualTo(2);
    }

    @DisplayName("출석 횟수를 가져온다")
    @Test
    void get_attend_record() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 5));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getAttendCount()).isEqualTo(1);
    }

    @DisplayName("월요일에 출석 횟수를 가져온다")
    @Test
    void get_attend_record_monday() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 5));
        attendanceSystem.editAttendance(crew, AttendanceDate.of(2024, 12, 2), AttendanceTime.of(13, 5));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getAttendCount()).isEqualTo(2);
    }

    @DisplayName("출석 횟수를 가져온다")
    @Test
    void get_attend_record2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 5));
        attendanceSystem.editAttendance(crew, AttendanceDate.of(2024, 12, 2), AttendanceTime.of(13, 7));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getAttendCount()).isEqualTo(1);
    }

    @DisplayName("결석을 5회 초과로 한 경우 제적 대상자이다")
    @Test
    void expulsion_test() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        AttendanceStatuses attendanceStatuses = attendanceSystem.findByCrew(crew).getAttendanceStatuses();
        assertThat(attendanceStatuses.getAbsenceCount() + attendanceStatuses.getTardyCount() / 3).isEqualTo(11);
        assertThat(attendanceStatuses.getRiskStatus()).isEqualTo(RiskStatus.EXPULSION);
    }

    @DisplayName("결석을 3회이상 5회 이하로 한 경우 면담 대상자이다")
    @Test
    void counseling_test() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.getDate().minusDays(5));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, new AttendanceDate(date), AttendanceTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        AttendanceStatuses attendanceStatuses = attendanceSystem.findByCrew(crew).getAttendanceStatuses();
        assertAll(
                () -> assertThat(attendanceStatuses.getAbsenceCount() + attendanceStatuses.getTardyCount() / 3).isEqualTo(3),
                () -> assertThat(attendanceStatuses.getRiskStatus()).isEqualTo(RiskStatus.COUNSELING)
        );
    }

    @DisplayName("결석을 2회 한경우 경고 대상자이다")
    @Test
    void warning_test() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.getDate().minusDays(4));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, new AttendanceDate(date), AttendanceTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        AttendanceStatuses attendanceStatuses = attendanceSystem.findByCrew(crew).getAttendanceStatuses();
        assertAll(
                () -> assertThat(attendanceStatuses.getAbsenceCount() + attendanceStatuses.getTardyCount() / 3).isEqualTo(2),
                () -> assertThat(attendanceStatuses.getRiskStatus()).isEqualTo(RiskStatus.WARNING)
        );
    }

    @DisplayName("결석을 안한 경우")
    @Test
    void none_test() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.getDate());
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, new AttendanceDate(date), AttendanceTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        AttendanceStatuses attendanceStatuses = attendanceSystem.findByCrew(crew).getAttendanceStatuses();
        assertAll(
                () -> assertThat(attendanceStatuses.getAbsenceCount() + attendanceStatuses.getTardyCount() / 3).isEqualTo(0),
                () -> assertThat(attendanceStatuses.getRiskStatus()).isEqualTo(RiskStatus.NONE)
        );
    }

    @DisplayName("결석을 한번 한 경우")
    @Test
    void none_test2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.getDate().minusDays(1));
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, new AttendanceDate(date), AttendanceTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        AttendanceStatuses attendanceStatuses = attendanceSystem.findByCrew(crew).getAttendanceStatuses();
        assertAll(
                () -> assertThat(attendanceStatuses.getAbsenceCount() + attendanceStatuses.getTardyCount() / 3).isEqualTo(1),
                () -> assertThat(attendanceStatuses.getRiskStatus()).isEqualTo(RiskStatus.NONE)
        );
    }

    @DisplayName("해당 날짜에 출석 기록이 존재하는지 여부를 확인한다")
    @Test
    void has_attend_record() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.hasAttendanceRecord(TODAY)).isTrue();
    }

    @DisplayName("getAttendanceStatus 가 올바른 값을 가지는지 테스트")
    @Test
    void get_attendance_status() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertAll(
                () -> assertThat(attendanceBook.getAttendanceStatus(TODAY)).isEqualTo(AttendanceStatus.ATTEND),
                () -> assertThat(attendanceBook.getAttendanceStatus(AttendanceDate.of(2024, 12, 16))).isEqualTo(AttendanceStatus.ABSENCE)
        );
    }

    @DisplayName("getAttendanceBook 가 올바른 값을 가지는지 테스트")
    @Test
    void get_attendance_book() {
        Crew crew = new Crew("두리");
        AttendanceTime time = AttendanceTime.of(10, 0);
        attendanceSystem.editAttendance(crew, TODAY, time);
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceBook()).containsEntry(TODAY, time);
    }

    @DisplayName("getAttendanceStatuses 가 올바른 값을 가지는지 테스트")
    @Test
    void get_attendance_statuses() {
        Crew crew = new Crew("두리");
        AttendanceTime time = AttendanceTime.of(10, 0);
        attendanceSystem.editAttendance(crew, TODAY, time);
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook.getAttendanceStatuses().getAttendanceStatuses()).containsEntry(TODAY, AttendanceStatus.ATTEND);
    }

    @DisplayName("제적 위험자 목록 출력 테스트")
    @Test
    void get_risk_crews() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        for (LocalDate date = LocalDate.of(2024, 12, 1);
             date.isBefore(TODAY.getDate());
             date = date.plusDays(1)) {
            try {
                attendanceSystem.editAttendance(crew, new AttendanceDate(date), AttendanceTime.of(10, 0));
            } catch (IllegalArgumentException ignored) {

            }
        }
        assertThat(attendanceSystem.getRiskCrews()).isEmpty();
    }

    @DisplayName("제적 위험자 목록 출력 테스트2")
    @Test
    void get_risk_crews2() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        assertThat(attendanceSystem.getRiskCrews()).containsKey(crew);
    }

    @DisplayName("크루원 찾기 테스트")
    @Test
    void find_by_name() {
        Crew crew = new Crew("두리");
        attendanceSystem.editAttendance(crew, TODAY, AttendanceTime.of(10, 0));
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(new Crew("두리"));
        AttendanceBook attendanceBook2 = attendanceSystem.findByCrew(crew);
        assertThat(attendanceBook).isEqualTo(attendanceBook2);
    }
}
