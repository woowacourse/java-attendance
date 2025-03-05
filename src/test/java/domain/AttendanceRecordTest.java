package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {

    @Test
    @DisplayName("출석 객체 생성 테스트")
    void test1() {
        //given
        final AttendanceDate attendanceDate1 = new AttendanceDate(LocalDate.of(2024, 12, 9));
        final AttendanceDate attendanceDate2 = new AttendanceDate(LocalDate.of(2024, 12, 13));
        final AttendanceTime attendanceTime1 = AttendanceTime.of(attendanceDate1.getDayOfWeek(), LocalTime.of(13, 5));
        final AttendanceTime attendanceTime2 = AttendanceTime.of(attendanceDate2.getDayOfWeek(), LocalTime.of(10, 6));
        final AttendanceStatus status1 = AttendanceStatus.ATTENDANCE;
        final AttendanceStatus status2 = AttendanceStatus.LATE;

        //should
        assertThatCode(() -> new AttendanceRecord(attendanceDate1, attendanceTime1, status1)).doesNotThrowAnyException();
        assertThatCode(() -> new AttendanceRecord(attendanceDate2, attendanceTime2, status2)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유효한 등교일시 이므로 출석 객체를 성공적으로 생성한다.")
    void test2() {
        //given
        final LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 13, 13, 1);
        final LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 9, 10, 1);

        //should
        assertThatCode(() -> AttendanceRecord.of(localDateTime1)).doesNotThrowAnyException();
        assertThatCode(() -> AttendanceRecord.of(localDateTime2)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유요한 등교일시가 아니므로 출석 객체 생성시 예외가 발생한다.")
    void test3() {
        //given
        final LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 13, 6, 59);
        final LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 9, 23, 1);
        final LocalDateTime localDateTime3 = LocalDateTime.of(2024, 12, 14, 10, 1);
        final LocalDateTime localDateTime4 = LocalDateTime.of(2024, 12, 15, 10, 1);
        final LocalDateTime localDateTime5 = LocalDateTime.of(2024, 12, 15, 10, 1);

        //should
        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(() -> AttendanceRecord.of(localDateTime1)),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> AttendanceRecord.of(localDateTime2)),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> AttendanceRecord.of(localDateTime3)),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> AttendanceRecord.of(localDateTime4)),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> AttendanceRecord.of(localDateTime5))
        );

    }

}
