package domain;

import domain.rule.AttendanceStateRule;
import domain.rule.AttendanceTimeRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTest {

    @Test
    @DisplayName("출석 상태를 결정할 수 있다")
    void shouldDetermineAttendanceStateBasedOnTime() {
        // given
        LocalTime normalAttendanceTime = AttendanceTimeRule.NORMAL_ATTEND_LIMIT_TIME.toLocalTime();

        AttendanceDate date = AttendanceDate.from(LocalDate.of(2024, 12, 18));

        AttendanceTime onTime = AttendanceTime.from(normalAttendanceTime);
        AttendanceTime lateTime = AttendanceTime.from(normalAttendanceTime
                .plusMinutes(AttendanceStateRule.LATE.getLimit())
                .plusMinutes(1));
        AttendanceTime absentTime = AttendanceTime.from(normalAttendanceTime
                .plusMinutes(AttendanceStateRule.ABSENT.getLimit())
                .plusMinutes(1));

        Attendance attendanceOnTime = Attendance.from(date, onTime);
        Attendance attendanceLate = Attendance.from(date, lateTime);
        Attendance attendanceAbsent = Attendance.from(date, absentTime);

        // when
        AttendanceStateRule onTimeState = attendanceOnTime.decisionAttendanceState();
        AttendanceStateRule lateState = attendanceLate.decisionAttendanceState();
        AttendanceStateRule absentState = attendanceAbsent.decisionAttendanceState();

        // then
        assertAll(
                () -> assertThat(onTimeState).isEqualTo(AttendanceStateRule.ATTEND),
                () -> assertThat(lateState).isEqualTo(AttendanceStateRule.LATE),
                () -> assertThat(absentState).isEqualTo(AttendanceStateRule.ABSENT)
        );
    }
}
