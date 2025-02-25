package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRegister;
import attendance.model.AttendanceTime;
import global.Constant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class AttendanceRegisterTest {

    @Test
    void 크루원의_이름으로_출석기록을_조회한다() {
        // given
        AttendanceRegister register = new AttendanceRegister(new HashMap<>(), Constant.customLocalDateTime);
        register.attend("빙티", new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                new AttendanceTime(LocalTime.of(9, 58))
        ));

        // when
        long attendanceCount = register.findAttendanceHistoryByCrewName("빙티").computeAttendanceCount();

        // then
        assertThat(attendanceCount).isEqualTo(1);
    }

    @Test
    void 존재하지_않는_크루원의_이름으로_출석기록을_조회하면_예외가_발생한다() {
        // given
        AttendanceRegister register = new AttendanceRegister(new HashMap<>(), Constant.customLocalDateTime);
        register.attend("빙티", new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                new AttendanceTime(LocalTime.of(9, 58))
        ));

        // when & then
        assertThatThrownBy(() -> register.findAttendanceHistoryByCrewName("빙티티"))
                .isInstanceOf(IllegalArgumentException.class); // 예외 타입 명시 필요
    }
}

