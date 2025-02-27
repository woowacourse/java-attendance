package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PenaltyTypeTest {
    @Test
    void getFromTest1() {
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.ABSENCE, 6);
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.BAN);
    }

    @Test
    void getFromTest2() {
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.ABSENCE, 5);
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.ONE_ON_ONE);
    }

    @Test
    void getFromTest3() {
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.ABSENCE, 2);
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.WARNING);
    }

    @Test
    void getFromTest4() {
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.LATE, 18);
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.BAN);
    }

    @Test
    void getFromTest5() {
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        attendanceTypeCount.put(AttendanceType.LATE, 15);
        PenaltyType penaltyType = PenaltyType.getFrom(attendanceTypeCount);

        Assertions.assertThat(penaltyType).isEqualTo(PenaltyType.ONE_ON_ONE);
    }
}
