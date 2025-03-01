package attendance.domain;

import attendance.util.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("출석 리스트 테스트")
public class AttendancesTest {

    @Test
    void 출석_리스트에_출석을_추가할수_있다() {
        Attendances attendances = new Attendances(new ArrayList<>());
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        attendances.add(attendance);

        assertThat(attendances.size()).isEqualTo(1);
    }

    @Test
    void 출석_리스트에_해당날짜의_출석이_존재하는데_추가하면_예외를_반환한다() {
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(List.of(attendance));
        Attendance sameDateAttendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));

        assertThatThrownBy(() -> attendances.add(sameDateAttendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ATTENDANCE_ALREADY_EXIST_ERROR.getMessage());
    }

    @Test
    void 출석_리스트에서_원하는_날짜의_출석을_반환한다() {
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));

        Attendance oldAttendance = attendances.findByDate(LocalDate.of(2024, 12, 12));

        assertThat(oldAttendance).isEqualTo(attendance);
    }

    @Test
    void 출석_리스트에_원하는_날짜의_출석이_없다면_예외가_발생한다() {
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));

        assertThatThrownBy(() -> attendances.findByDate(LocalDate.of(2024, 12, 13)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ATTENDANCE_NOT_EXIST_ERROR.getMessage());
    }

    @Test
    void 출석_리스트에서_원하는_날짜의_출석을_수정한다() {
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));

        Attendance oldAttendance = attendances.findByDate(LocalDate.of(2024, 12, 12));
        Attendance newAttendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(13, 30));
        attendances.update(oldAttendance, newAttendance);

        assertThat(attendances.size()).isEqualTo(1);
    }
}
