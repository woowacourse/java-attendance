package attendance.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendancesTest {

    private Attendances attendances;
    private Attendance existAttendance;

    @BeforeEach
    void setUp() {
        existAttendance = Attendance.of(LocalDateTime.of(2024, 12, 13, 10, 0));
        attendances = new Attendances(new ArrayList<>());
        attendances.add(existAttendance);
    }

    @Test
    void 이미_출석한_경우_수정기능을_이용하도록_예외가_발생한다() {
       Attendance addAttendance = Attendance.of(LocalDateTime.of(2024, 12, 13, 10, 6));

        assertThatThrownBy(() -> attendances.add(addAttendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("\n[ERROR] 이미 출석을 완료했습니다. 수정 기능을 이용해주세요.");
    }

    @Test
    void 수정하려는_출석일자의_출석을_반환한다() {
        LocalDate modifyingDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = attendances.get(modifyingDate);

        assertThat(attendance).isEqualTo(Attendance.of(LocalDateTime.of(modifyingDate, LocalTime.of(10, 0))));
    }

    @Test
    void 수정하려는_출석일자가_존재하지_않으면_시간이_존재하지않는_출석을_반환한다() {
        LocalDate modifyingDate = LocalDate.of(2024, 12, 12);
        Attendance attendance = attendances.get(modifyingDate);

        assertThat(attendance).isEqualTo(Attendance.of(LocalDateTime.of(modifyingDate, LocalTime.MIN)));
    }

    @Test
    void 수정한_출석을_반환한다() {
        LocalTime modifyingTime = LocalTime.of(9, 30);
        Attendance modifiedAttendance = attendances.modify(existAttendance, modifyingTime);

        assertThat(modifiedAttendance).isEqualTo(Attendance.of(LocalDateTime.of(2024, 12, 13, 9, 30)));
    }
}
