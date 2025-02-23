package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceHistory;
import attendance.model.AttendanceTime;
import attendance.model.AttendanceWarning;
import global.BaseTest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest extends BaseTest {

    @Test
    void 출석_시간이_출석기록에_정상적으로_추가된다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 2)),
                new AttendanceTime(LocalTime.of(9, 58))
        ));
        assertThat(attendanceHistory.computeAttendanceCount()).isEqualTo(1);
    }

    @Test
    void 특정_날짜의_출석_기록을_조회한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 2));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(9, 58));
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                attendanceDate,
                attendanceTime
        );
        attendanceHistory.addAttendanceDateTime(attendanceDateTime);
        assertThat(attendanceHistory.findAttendanceDateTime(attendanceDate)).isEqualTo(attendanceDateTime);
    }

    @Test
    void 출석_기록이_없는_날짜로_조회할_경우_예외가_발생한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 2));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(9, 58));
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                attendanceDate,
                attendanceTime
        );
        attendanceHistory.addAttendanceDateTime(attendanceDateTime);
        assertThatThrownBy(() -> attendanceHistory.findAttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3))
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출석_기록에서_지각_횟수를_조회한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        AttendanceDateTime attendance = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 2)),
                new AttendanceTime(LocalTime.of(9, 58))
        );
        AttendanceDateTime late1 = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                new AttendanceTime(LocalTime.of(10, 6))
        );
        AttendanceDateTime late2 = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 4)),
                new AttendanceTime(LocalTime.of(10, 7))
        );
        attendanceHistory.addAttendanceDateTime(attendance);
        attendanceHistory.addAttendanceDateTime(late1);
        attendanceHistory.addAttendanceDateTime(late2);
        assertThat(attendanceHistory.computeLateCount()).isEqualTo(2);
    }

    @Test
    void 출석_기록에서_결석_횟수를_조회한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        AttendanceDateTime attendance = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 2)),
                new AttendanceTime(LocalTime.of(9, 58))
        );
        AttendanceDateTime late1 = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                new AttendanceTime(LocalTime.of(10, 31))
        );
        AttendanceDateTime late2 = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 4)),
                new AttendanceTime(LocalTime.of(10, 7))
        );
        attendanceHistory.addAttendanceDateTime(attendance);
        attendanceHistory.addAttendanceDateTime(late1);
        attendanceHistory.addAttendanceDateTime(late2);
        assertThat(attendanceHistory.computeAbsenceCount()).isEqualTo(9);
    }

    @Test
    void 출석_기록에의_해당없음_상태를_조회한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        List<Integer> attendDay = List.of(3, 4, 5, 6, 10, 11, 12 ,13);
        List<Integer> mondayAttendDay = List.of(2, 9, 16);
        attendDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 0))
        )));
        mondayAttendDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 0))
        )));
        AttendanceWarning warning = attendanceHistory.getAttendanceWarning();
        assertThat(warning).isEqualTo(AttendanceWarning.NONE);
    }

    @Test
    void 출석_기록에의_경고_상태를_조회한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        List<Integer> attendDay = List.of(3, 4, 5, 6, 10, 11);
        List<Integer> absenceDay = List.of(12 ,13);
        List<Integer> attendMondayAttendDay = List.of(2, 9, 16);
        attendDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 0))
        )));
        absenceDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 31))
        )));
        attendMondayAttendDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 0))
        )));
        AttendanceWarning warning = attendanceHistory.getAttendanceWarning();
        assertThat(warning).isEqualTo(AttendanceWarning.WARNING);
    }

    @Test
    void 출석_기록에의_면담_상태를_조회한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        List<Integer> attendDay = List.of(3, 4, 5, 6);
        List<Integer> absenceDay = List.of(6, 10, 11, 12 ,13);
        List<Integer> attendMondayAttendDay = List.of(2, 9, 16);
        attendDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 0))
        )));
        absenceDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 31))
        )));
        attendMondayAttendDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 0))
        )));
        AttendanceWarning warning = attendanceHistory.getAttendanceWarning();
        assertThat(warning).isEqualTo(AttendanceWarning.COUNSELING);
    }

    @Test
    void 출석_기록에의_제적_상태를_조회한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        List<Integer> attendDay = List.of(3, 4, 5, 6);
        List<Integer> absenceDay = List.of(6, 10, 11, 12 ,13);
        List<Integer> attendMondayAttendDay = List.of(2, 9, 16);
        attendDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 31))
        )));
        absenceDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 31))
        )));
        attendMondayAttendDay.forEach(day -> attendanceHistory.addAttendanceDateTime(new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, day)),
                new AttendanceTime(LocalTime.of(10, 0))
        )));
        AttendanceWarning warning = attendanceHistory.getAttendanceWarning();
        assertThat(warning).isEqualTo(AttendanceWarning.EXPULSION);
    }

    @Test
    void 특정_날짜에_출석_기록이_있는지_확인한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 2));
        AttendanceDateTime attendance = new AttendanceDateTime(
                attendanceDate,
                new AttendanceTime(LocalTime.of(9, 58))
        );
        attendanceHistory.addAttendanceDateTime(attendance);
        assertThat(attendanceHistory.containsAttendance(attendanceDate)).isTrue();
    }

    @Test
    void 마지막_출석_가능_날짜를_계산한다() {
        AttendanceHistory attendanceHistory = new AttendanceHistory(new ArrayList<>());
        LocalDate lastAttendableDate = attendanceHistory.computeLastAttendableDate();
        assertThat(lastAttendableDate).isEqualTo(LocalDate.of(2024, 12, 16));
    }
}
