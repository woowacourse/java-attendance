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
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));

        assertThatCode(() -> attendances.add(attendance))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석_리스트에_해당날짜의_출석이_존재하는데_추가하면_예외를_반환한다() {
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(List.of(attendance));
        Attendance sameDateAttendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));

        assertThatThrownBy(() -> attendances.add(sameDateAttendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ATTENDANCE_ALREADY_EXIST_ERROR.getMessage());
    }

    @Test
    void 출석_리스트에서_원하는_날짜의_출석을_반환한다() {
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));

        Attendance oldAttendance = attendances.findByDate(LocalDate.of(2024, 12, 12));

        assertThat(oldAttendance).isEqualTo(attendance);
    }

    @Test
    void 출석_리스트에_원하는_날짜의_출석이_없다면_결석객체가_생성된다() {
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        Attendance attendanceOfAbsent = attendances.findByDate(LocalDate.of(2024, 12, 13));

        assertThat(attendanceOfAbsent.determineStatus()).isEqualTo(AttendanceStatus.ABSENT);
    }

    @Test
    void 출석_리스트에서_원하는_날짜의_출석을_수정한다() {
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));

        Attendance oldAttendance = attendances.findByDate(LocalDate.of(2024, 12, 12));
        Attendance newAttendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 30));

        assertThatCode(() -> attendances.update(oldAttendance, newAttendance))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석_리스트에서_이번달_전날까지의_기록을_반환한다() {
        Attendance anotherMonthAttendance = Attendance.of(LocalDate.of(2024, 11, 12), LocalTime.of(13, 0));
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendance attendance2 = Attendance.of(LocalDate.of(2024, 12, 13), LocalTime.of(13, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(anotherMonthAttendance, attendance, attendance2)));

        assertThat(attendances.getAttendancesUntilYesterday(LocalDate.of(2024, 12, 14))).hasSize(2);
    }
}
