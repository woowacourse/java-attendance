package function;

import static constants.TestTimeMaker.EXCEPT_MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_ATTEND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.Crew;
import dto.AttendanceRecordResponse;
import dto.TotalRecordsResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorCode;

public class CheckAttendanceRecordTest {
    private AttendanceBook attendanceBook;

    @BeforeEach
    void setup() {
        attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew1);

        attendanceBook.initialize("쿠키", Map.of(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_ATTEND));
        attendanceBook.initialize("쿠키", Map.of(LocalDate.of(2024, 12, 4), EXCEPT_MONDAY_ATTEND));
    }

    @Test
    @DisplayName("닉네임을_입력하면_전날까지의_크루_출석_기록을_출력해야_한다")
    void 닉네임을_입력하면_전날까지의_크루_출석_기록을_출력해야_한다() {
        List<AttendanceRecordResponse> record = attendanceBook.checkAttendanceHistoryByCrew("쿠키");

        assertThat(record.getFirst().date()).isEqualTo(LocalDate.of(2024, 12, 2));
        assertThat(record.get(1).date()).isEqualTo(LocalDate.of(2024, 12, 3));
        assertThat(record.get(2).date()).isEqualTo(LocalDate.of(2024, 12, 4));

        assertThat(record.getFirst().time()).isEqualTo(MONDAY_ATTEND);
        assertThat(record.get(1).time()).isEqualTo(EXCEPT_MONDAY_ATTEND);
        assertThat(record.get(2).time()).isEqualTo(EXCEPT_MONDAY_ATTEND);

        assertThat(record.getFirst().attendanceStatus()).isEqualTo(AttendanceStatus.ATTEND);
        assertThat(record.get(1).attendanceStatus()).isEqualTo(AttendanceStatus.ATTEND);
        assertThat(record.get(2).attendanceStatus()).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("닉네임을_입력하면_전날까지의_크루_출석_기록_상태별_총_횟수를_출력해야_한다")
    void 닉네임을_입력하면_전날까지의_크루_출석_기록_상태별_총_횟수를_출력해야_한다() {
        List<AttendanceRecordResponse> record = attendanceBook.checkAttendanceHistoryByCrew("쿠키");
        TotalRecordsResponse count = attendanceBook.checkAttendanceCountByCrew(record);

        assertThat(count.attendanceCount()).isEqualTo(3);
        assertThat(count.lateCount()).isEqualTo(0);
        assertThat(count.absentCount()).isEqualTo(31 - 3);
    }

    @Test
    @DisplayName("출석_기록_확인시_등록되지_않는_닉네임의_경우_예외를_출력한다")
    void 출석_기록_확인시_등록되지_않는_닉네임의_경우_예외를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.checkAttendanceHistoryByCrew("없음"))
                .isInstanceOf(IllegalArgumentException.class) // 토요일
                .hasMessage(ErrorCode.NICKNAME_NOT_FOUND.getFormat());
    }
}