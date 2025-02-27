package domain.policy.attend.time;

import domain.policy.AttendanceStateRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceTimePolicyTest {

    private final AttendanceTimePolicy attendanceTimePolicy = new AttendanceTimePolicy();

    @Test
    @DisplayName("특수한 날이면 SPECIAL_STUDY_START 시간이 반환된다.")
    void specialDayReturnSpecialStudyStartTime() {
        // given
        boolean isSpecialDay = true;

        // when
        LocalTime result = attendanceTimePolicy.getAttendStartTime(isSpecialDay);

        // then
        assertThat(result).isEqualTo(StudyTimeRule.SPECIAL_STUDY_START.toLocalTime());
    }

    @Test
    @DisplayName("일반 날이면 NORMAL_STUDY_START 시간이 반환된다.")
    void normalDayReturnNormalStudyStartTime() {
        // given
        boolean isSpecialDay = false;

        // when
        LocalTime result = attendanceTimePolicy.getAttendStartTime(isSpecialDay);

        // then
        assertThat(result).isEqualTo(StudyTimeRule.NORMAL_STUDY_START.toLocalTime());
    }

    @Test
    @DisplayName("운영 시간 내라면 출석 가능하다.")
    void withinCampusHoursReturnCanAttend() {
        // given
        LocalTime withinCampusTime = LocalTime.of(10, 0);

        // when
        boolean canAttend = attendanceTimePolicy.canAttendTime(withinCampusTime);

        // then
        assertThat(canAttend).isTrue();
    }

    @Test
    @DisplayName("운영 시간 외라면 출석 불가능하다.")
    void outsideCampusHoursReturnCannotAttend() {
        // given
        LocalTime outsideCampusTime = LocalTime.of(23, 30);

        // when
        boolean canAttend = attendanceTimePolicy.canAttendTime(outsideCampusTime);

        // then
        assertThat(canAttend).isFalse();
    }

    @Test
    @DisplayName("지각 시간이 5분 이하면 출석으로 처리된다.")
    void lateLessThan5MinutesReturnAttend() {
        // given
        // when
        AttendanceStateRule result = attendanceTimePolicy.decisionAttendanceState(5);

        // then
        assertThat(result).isEqualTo(AttendanceStateRule.ATTEND);
    }

    @Test
    @DisplayName("지각 시간이 5분 초과, 30분 이하면 지각으로 처리된다.")
    void lateBetween5And30MinutesReturnLate() {
        // given
        // when
        AttendanceStateRule result = attendanceTimePolicy.decisionAttendanceState(6);

        // then
        assertThat(result).isEqualTo(AttendanceStateRule.LATE);
    }

    @Test
    @DisplayName("지각 시간이 30분 초과이면 결석으로 처리된다.")
    void lateMoreThan30MinutesReturnAbsent() {
        // given
        // when
        AttendanceStateRule result = attendanceTimePolicy.decisionAttendanceState(31);

        // then
        assertThat(result).isEqualTo(AttendanceStateRule.ABSENT);
    }
}