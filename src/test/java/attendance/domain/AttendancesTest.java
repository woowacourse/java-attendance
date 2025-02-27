package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
                .hasMessage("[ERROR] 이미 출석을 완료했습니다. 수정 기능을 이용해주세요.");
    }
}
