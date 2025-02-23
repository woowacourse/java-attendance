package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("출석 정보")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceTest {


    @Test
    void Attendance_객체를_생성한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");

        assertThatCode(() -> attendance.add(localDateTime)).doesNotThrowAnyException();
    }

    @Test
    void 출석_기록이_존재하면_예외가_발생한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");

        attendance.add(localDateTime);

        assertThatThrownBy(() -> attendance.add(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
    }

    @Test
    void 출석_정보를_수정하면_기존_출석_시간을_반환한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        HourMinute hourMinute = new HourMinute(newLocalDateTime);

        HourMinute prevHourMinute = attendance.modify(newLocalDateTime.toLocalDate(), hourMinute);

        assertThat(prevHourMinute.hour()).isEqualTo(localDateTime.getHour());
        assertThat(prevHourMinute.minute()).isEqualTo(localDateTime.getMinute());
    }

    @Test
    void 해당_날짜의_출석_기록이_없으면_false를_반환한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);

        LocalDate localDate = LocalDate.of(2024, 12, 22);

        boolean result = attendance.hasTimeStamp(localDate);

        assertThat(result)
                .isFalse();
    }

    @Test
    void 해당_날짜의_출석_기록이_있으면_true를_반환한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);

        LocalDate localDate = LocalDate.of(2024, 12, 23);

        boolean result = attendance.hasTimeStamp(localDate);

        assertThat(result)
                .isTrue();
    }

    @Test
    void 출석_지각_결석_횟수를_반환한다() {
        LocalDateTime present1 = LocalDateTime.of(2024, 12, 3, 9, 58);
        LocalDateTime present2 = LocalDateTime.of(2024, 12, 4, 10, 2);
        LocalDateTime present3 = LocalDateTime.of(2024, 12, 6, 10, 1);
        LocalDateTime lateness1 = LocalDateTime.of(2024, 12, 5, 10, 6);

        Attendance attendance = new Attendance("빙티");
        attendance.add(present1);
        attendance.add(present2);
        attendance.add(present3);
        attendance.add(lateness1);

        Map<AttendanceStatus, Integer> attendanceStatuses = attendance.countAttendanceStatus(6);

        assertThat(attendanceStatuses.get(PRESENT)).isEqualTo(2);
        assertThat(attendanceStatuses.get(LATENESS)).isEqualTo(1);
        assertThat(attendanceStatuses.get(ABSENCE)).isEqualTo(1);
    }
}
