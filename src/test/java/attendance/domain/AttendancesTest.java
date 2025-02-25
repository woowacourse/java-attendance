package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("출석 기록 목록")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AttendancesTest {
    @Test
    void 출석_시_출석_기록이_존재하면_예외가_발생한다() {
        LocalDateTime attendTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendances attendance = new Attendances();

        attendance.attend(attendTime);

        assertThatThrownBy(() -> attendance.attend(attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
    }

    @Test
    void 출석_수정_시_출석_기록이_없으면_예외가_발생한다() {
        LocalDateTime modifyTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendances attendance = new Attendances();

        assertThatThrownBy(() -> attendance.modify(modifyTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");
    }

    @Test
    void 출석_정보를_수정하면_기존_출석_시간을_반환한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendances attendance = new Attendances();
        attendance.attend(localDateTime);
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        Attendance prevAttendance = attendance.modify(newLocalDateTime);

        assertThat(prevAttendance.getHour()).isEqualTo(localDateTime.getHour());
        assertThat(prevAttendance.getMinute()).isEqualTo(localDateTime.getMinute());
    }

    @Test
    void 출석_지각_결석_횟수를_반환한다() {
        LocalDateTime present1 = LocalDateTime.of(2024, 12, 3, 9, 58);
        LocalDateTime present2 = LocalDateTime.of(2024, 12, 4, 10, 2);
        LocalDateTime present3 = LocalDateTime.of(2024, 12, 6, 10, 1);
        LocalDateTime lateness1 = LocalDateTime.of(2024, 12, 5, 10, 6);

        Attendances attendances = new Attendances();
        attendances.attend(present1);
        attendances.attend(present2);
        attendances.attend(present3);
        attendances.attend(lateness1);

        Map<AttendanceStatus, Integer> attendanceStatuses = attendances.countAttendanceStatus(6);

        assertThat(attendanceStatuses.get(PRESENT)).isEqualTo(2);
        assertThat(attendanceStatuses.get(LATENESS)).isEqualTo(1);
        assertThat(attendanceStatuses.get(ABSENCE)).isEqualTo(1);
    }

}
