package domain;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PenaltyTypeTest {

    @DisplayName("getFrom() - 결석 6회 이상이면 BAN")
    @Test
    void getFromTest1() {
        // given
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.ABSENCE, 6);

        // when
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        // then
        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.BAN);
    }

    @DisplayName("getFrom() - 결석 5회이면 ONE_ON_ONE")
    @Test
    void getFromTest2() {
        // given
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.ABSENCE, 5);

        // when
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        // then
        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.ONE_ON_ONE);
    }

    @DisplayName("getFrom() - 결석 2회이면 WARNING")
    @Test
    void getFromTest3() {
        // given
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.ABSENCE, 2);

        // when
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        // then
        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.WARNING);
    }

    @DisplayName("getFrom() - 지각 18회 이상이면 BAN")
    @Test
    void getFromTest4() {
        // given
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.LATE, 18);

        // when
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        // then
        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.BAN);
    }

    @DisplayName("getFrom() - 지각 15회이면 ONE_ON_ONE")
    @Test
    void getFromTest5() {
        // given
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.LATE, 15);

        // when
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        // then
        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.ONE_ON_ONE);
    }
}
