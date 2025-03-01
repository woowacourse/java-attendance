package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PunishmentTypeTest {

    @Test
    @DisplayName("징계 타입을 반환한다. (경고)")
    void test1() {
        // given
        EnumMap<AttendanceType, Integer> attendanceTotal = new EnumMap<>(AttendanceType.class);
        attendanceTotal.put(AttendanceType.SUCCESS, 5);
        attendanceTotal.put(AttendanceType.BE_LATE, 5);
        attendanceTotal.put(AttendanceType.ABSENCE, 2);

        // when
        PunishmentType result = PunishmentType.find(attendanceTotal);

        // then
        assertThat(result).isEqualTo(PunishmentType.WARNING);
    }

    @Test
    @DisplayName("징계 타입을 반환한다. (경고)")
    void test2() {
        // given
        EnumMap<AttendanceType, Integer> attendanceTotal = new EnumMap<>(AttendanceType.class);
        attendanceTotal.put(AttendanceType.SUCCESS, 5);
        attendanceTotal.put(AttendanceType.BE_LATE, 5);
        attendanceTotal.put(AttendanceType.ABSENCE, 5);

        // when
        PunishmentType result = PunishmentType.find(attendanceTotal);

        // then
        assertThat(result).isEqualTo(PunishmentType.MEETING);
    }

    @Test
    @DisplayName("징계 타입을 반환한다. (제적)")
    void test3() {
        // given
        EnumMap<AttendanceType, Integer> attendanceTotal = new EnumMap<>(AttendanceType.class);
        attendanceTotal.put(AttendanceType.SUCCESS, 5);
        attendanceTotal.put(AttendanceType.BE_LATE, 5);
        attendanceTotal.put(AttendanceType.ABSENCE, 6);

        // when
        PunishmentType result = PunishmentType.find(attendanceTotal);

        // then
        assertThat(result).isEqualTo(PunishmentType.EXPULSION);
    }

    @Test
    @DisplayName("징계 타입을 반환한다. (해당 없음)")
    void test4() {
        // given
        EnumMap<AttendanceType, Integer> attendanceTotal = new EnumMap<>(AttendanceType.class);
        attendanceTotal.put(AttendanceType.SUCCESS, 5);
        attendanceTotal.put(AttendanceType.BE_LATE, 5);
        attendanceTotal.put(AttendanceType.ABSENCE, 1);

        // when
        PunishmentType result = PunishmentType.find(attendanceTotal);

        // then
        assertThat(result).isEqualTo(PunishmentType.NONE);
    }
}