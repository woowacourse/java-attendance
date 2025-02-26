package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatisticTest {
    @DisplayName("지각 세 번을 결석 한 번으로 변환하여 총 결석 횟수를 계산할 수 있다.")
    @Test
    void test1() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.LATE, 7);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        final int totalAbsenceCount = statistic.getTotalAbsenceCount();

        // then
        assertThat(totalAbsenceCount).isEqualTo(2);
    }

    @DisplayName("지각만으로 경고 대상자인 경우, 경고 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test2() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.LATE, 7);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.WARNING);
    }

    @DisplayName("결석만으로 경고 대상자인 경우, 경고 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test3() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.ABSENCE, 2);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.WARNING);
    }

    @DisplayName("결석과 경고를 합해서 경고 대상자인 경우, 경고 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test4() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.ABSENCE, 1);
            put(AttendanceStatus.LATE, 4);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.WARNING);
    }

    @DisplayName("지각만으로 면담 대상자인 경우, 면담 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test5() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.LATE, 10);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.CONSULTANT);
    }

    @DisplayName("결석과 지각을 합해서 면담 대상자인 경우, 면담 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test6() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.ABSENCE, 2);
            put(AttendanceStatus.LATE, 4);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.CONSULTANT);
    }

    @DisplayName("결석만으로 면담 대상자인 경우, 면담 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test7() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.ABSENCE, 3);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.CONSULTANT);
    }

    @DisplayName("지각만으로 제적 대상자인 경우, 제적 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test8() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.LATE, 18);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.DISENROLLMENT);
    }

    @DisplayName("결석과 지각을 합해서 제적 대상자인 경우, 제적 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test9() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.ABSENCE, 5);
            put(AttendanceStatus.LATE, 4);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.DISENROLLMENT);
    }

    @DisplayName("결석만으로 제적 대상자인 경우, 제적 대상자 상태를 올바르게 반환할 수 있다.")
    @Test
    void test10() {
        // given
        Map<AttendanceStatus, Integer> testValue = new HashMap<>() {{
            put(AttendanceStatus.ABSENCE, 6);
        }};
        AttendanceStatistic statistic = new AttendanceStatistic(testValue);

        // when
        CrewStatus crewStatus = statistic.getCrewStatus();

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.DISENROLLMENT);
    }
}
