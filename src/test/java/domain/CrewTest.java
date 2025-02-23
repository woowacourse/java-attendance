package domain;

import static constants.TestTimeMaker.EXCEPT_MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_ABSENT;
import static constants.TestTimeMaker.MONDAY_ATTEND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import dto.AttendanceRecordResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorCode;

public class CrewTest {
    private Crew crew;

    @BeforeEach
    void setup() {
        crew = Crew.createByName("우유");
        crew.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        crew.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_ATTEND));
        crew.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 4), EXCEPT_MONDAY_ATTEND));
    }

    @Test
    @DisplayName("해당_날짜의_출석_정보가_존재하지_않는_경우_예외를_출력한다")
    void 해당_날짜의_출석_정보가_존재하지_않는_경우_예외를_출력한다() {
        LocalDate date = LocalDate.of(2024, 12, 5);
        assertThatThrownBy(
                () -> crew.validateRecordNotExists(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.ATTENDANCE_RECORD_NOT_EXISTS_FORMAT.format(date.getDayOfMonth()));
    }

    @Test
    @DisplayName("해당_날짜에_이미_출석한_경우_예외를_출력한다")
    void 해당_날짜에_이미_출석한_경우_예외를_출력한다() {
        Map<LocalDate, LocalTime> dayAndTime = Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND);
        assertThatThrownBy(
                () -> crew.addDailyAttendance(dayAndTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.CHECK_ATTENDANCE_ALREADY_EXISTS.getFormat());
    }

    @Test
    @DisplayName("기존의_기록에_새로운_출석기록을_더한다")
    void 기존의_기록에_새로운_출석기록을_더한다() {
        Map<LocalDate, LocalTime> dayAndTime = Map.of(LocalDate.of(2024, 12, 5), EXCEPT_MONDAY_ATTEND);
        crew.addDailyAttendance(dayAndTime);
        assertThat(crew.getTimeByDate(LocalDate.of(2024, 12, 5))).isEqualTo(EXCEPT_MONDAY_ATTEND);
    }

    @Test
    @DisplayName("기존의_기록을_새로운_기록으로_변경한다")
    void 기존의_기록을_새로운_기록으로_변경한다() {
        Map<LocalDate, LocalTime> dayAndTime = Map.of(LocalDate.of(2024, 12, 2), MONDAY_ABSENT);
        crew.modifyDailyAttendance(dayAndTime);
        assertThat(crew.getTimeByDate(LocalDate.of(2024, 12, 2))).isEqualTo(MONDAY_ABSENT);
    }

    @Test
    @DisplayName("전체_출결_기록을_가져온다")
    void 전체_출결_기록을_가져온다() {
        List<AttendanceRecordResponse> records = crew.getAttendanceRecords();
        assertThat(records.getFirst().date()).isEqualTo(LocalDate.of(2024, 12, 2));
        assertThat(records.getFirst().time()).isEqualTo(MONDAY_ATTEND);
        assertThat(records.getFirst().attendanceStatus()).isEqualTo(AttendanceStatus.ATTEND);

        assertThat(records.get(1).date()).isEqualTo(LocalDate.of(2024, 12, 3));
        assertThat(records.get(1).time()).isEqualTo(EXCEPT_MONDAY_ATTEND);
        assertThat(records.get(1).attendanceStatus()).isEqualTo(AttendanceStatus.ATTEND);

        assertThat(records.getLast().date()).isEqualTo(LocalDate.of(2024, 12, 31));
        assertThat(records.getLast().time()).isNull();
        assertThat(records.getLast().attendanceStatus()).isEqualTo(AttendanceStatus.ABSENT);
    }
}