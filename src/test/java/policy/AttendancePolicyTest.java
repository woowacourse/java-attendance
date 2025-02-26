package policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import policy.date.AttendanceDatePolicy;
import policy.time.AttendanceTimePolicy;
import policy.time.rule.AttendanceStateRule;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendancePolicyTest {

    private final AttendanceDatePolicy attendanceDatePolicy = new AttendanceDatePolicy();
    private final AttendanceTimePolicy attendanceTimePolicy = new AttendanceTimePolicy();
    private final AttendancePolicy attendancePolicy = new AttendancePolicy(
            attendanceDatePolicy,
            attendanceTimePolicy);

    @Test
    @DisplayName("출석 가능한 시간인지 판단할 수 있다")
    void withinCampusHoursReturnCanAttend() {
        // given
        LocalTime withinCampusTime = LocalTime.of(10, 0);
        LocalTime outsideCampusTime = LocalTime.of(23, 30);

        // when
        boolean canAttendWithin = attendancePolicy.canAttendTime(withinCampusTime);
        boolean canAttendOutside = attendancePolicy.canAttendTime(outsideCampusTime);

        // then
        assertAll(
                () -> assertThat(canAttendWithin).isTrue(),
                () -> assertThat(canAttendOutside).isFalse()
        );
    }

    @Test
    @DisplayName("출석 가능한 날짜인지 판단할 수 있다")
    void weekendOrHolidayReturnCannotAttend() {
        // given
        LocalDate holiday = LocalDate.of(2024, 12, 25); // 크리스마스 (공휴일)
        LocalDate weekend = LocalDate.of(2024, 12, 15); // 토요일
        LocalDate weekday = LocalDate.of(2024, 12, 16); // 월요일 (정상 근무일, 늦잠 자는 날)

        // when
        boolean canAttendHoliday = attendancePolicy.canAttendDate(holiday);
        boolean canAttendWeekend = attendancePolicy.canAttendDate(weekend);
        boolean canAttendWeekday = attendancePolicy.canAttendDate(weekday);

        // then
        assertAll(
                () -> assertThat(canAttendHoliday).isFalse(),
                () -> assertThat(canAttendWeekend).isFalse(),
                () -> assertThat(canAttendWeekday).isTrue()
        );
    }

    @Test
    @DisplayName("일반적인 날의 출석 상태를 결정할 수 있다")
    void decideAttendanceStateReturnStateWhenNormalDay() {
        // given
        LocalDate normalDay = LocalDate.of(2024, 12, 18);
        LocalTime attendTime = attendanceTimePolicy.getAttendStartTime(false);

        LocalTime _6MinutesLate = attendTime.plusMinutes(6);
        LocalTime _31MinutesLate = attendTime.plusMinutes(31);

        // when
        AttendanceStateRule attendState = attendancePolicy.decideState(normalDay, attendTime);
        AttendanceStateRule lateState = attendancePolicy.decideState(normalDay, _6MinutesLate);
        AttendanceStateRule absentState = attendancePolicy.decideState(normalDay, _31MinutesLate);

        // then
        assertAll(
                () -> assertThat(attendState).isEqualTo(AttendanceStateRule.ATTEND),
                () -> assertThat(lateState).isEqualTo(AttendanceStateRule.LATE),
                () -> assertThat(absentState).isEqualTo(AttendanceStateRule.ABSENT)
        );
    }

    @Test
    @DisplayName("특별한 날의 출석 상태를 결정할 수 있다")
    void decideAttendanceStateReturnStateWhenSpecialDay() {
        // given
        LocalDate specialDay = LocalDate.of(2024, 12, 16);
        LocalTime attendTime = attendanceTimePolicy.getAttendStartTime(true);

        LocalTime _6MinutesLate = attendTime.plusMinutes(6);
        LocalTime _31MinutesLate = attendTime.plusMinutes(31);

        // when
        AttendanceStateRule attendState = attendancePolicy.decideState(specialDay, attendTime);
        AttendanceStateRule lateState = attendancePolicy.decideState(specialDay, _6MinutesLate);
        AttendanceStateRule absentState = attendancePolicy.decideState(specialDay, _31MinutesLate);

        // then
        assertAll(
                () -> assertThat(attendState).isEqualTo(AttendanceStateRule.ATTEND),
                () -> assertThat(lateState).isEqualTo(AttendanceStateRule.LATE),
                () -> assertThat(absentState).isEqualTo(AttendanceStateRule.ABSENT)
        );
    }
}
