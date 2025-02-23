package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceHistory;
import attendance.model.AttendanceRegister;
import attendance.model.AttendanceTime;
import attendance.model.CrewDataLoader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendanceRegisterTest {

    @Test
    void 크루원의_이름으로_출석기록을_조회한다() {
        AttendanceRegister register = new AttendanceRegister(new HashMap<>());
        register.attend("빙티", new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                new AttendanceTime(LocalTime.of(9, 58))
        ));
        assertThat(register.findAttendanceHistoryByCrewName("빙티").computeAttendanceCount()).isEqualTo(1);
    }

    @Test
    void 존재하지_않는_크루원의_이름으로_출석기록을_조회하면_예외가_발생한다() {
        AttendanceRegister register = new AttendanceRegister(new HashMap<>());
        register.attend("빙티", new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                new AttendanceTime(LocalTime.of(9, 58))
        ));
        assertThatThrownBy(() -> register.findAttendanceHistoryByCrewName("빙티티"));
    }
}
