package attendance.domain.record;

import static attendance.domain.record.AttendanceType.ATTENDANCE;
import static attendance.domain.record.AttendanceType.EXPULSION;
import static attendance.domain.record.AttendanceType.LATE;
import static attendance.fixer.RecordFixer.makeRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordsTest {

    AttendanceRecords records;

    @BeforeEach
    void beforeEach() {
        records = new AttendanceRecords();
    }

    @DisplayName("출석 기록을 추가한다.")
    @Test
    void 출석_기록을_추가한다() {
        AttendanceRecord record = makeRecord("쿠키", ATTENDANCE);

        records.add(record);

        AttendanceRecord savedRecord = records.find(record.getDate()).get();
        assertThat(savedRecord).isEqualTo(record);
    }

    @DisplayName("결석 기록은 추가가 불가능하다.")
    @Test
    void 결석_기록은_추가가_불가능하다() {
        AttendanceRecord record = makeRecord("쿠키", EXPULSION);

        records.add(record);

        assertThat(records.find(record.getDate())).isEmpty();
    }

    @DisplayName("이미 출석을 완료한 경우 출석을 새롭게 저장할 수 없다.")
    @Test
    void 이미_출석을_완료한_경우_출석을_새롭게_저장할_수_없다() {
        AttendanceRecord record = makeRecord("쿠키", ATTENDANCE);
        records.add(record);

        AttendanceRecord newRecord = makeRecord("쿠키", LATE);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> records.add(newRecord))
                .withMessage(ExceptionMessage.ALREADY_ATTENDANCE.getContent());
    }

    @DisplayName("저장된 출석 기록을 찾을 수 있다.")
    @Test
    void 저장된_출석_기록을_찾을_수_있다() {
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 8, 8, 10, 0)));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 9, 8, 10, 0)));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 10, 8, 10, 0)));
        LocalDate expectedDate = LocalDate.of(2024, 12, 8);

        AttendanceRecord record = records.find(expectedDate).get();

        assertThat(record.getDate()).isEqualTo(expectedDate);
    }

    @DisplayName("저장된 출석 기록이 없다면 비어있는 Optional 출력한다.")
    @Test
    void 저장된_출석_기록이_없다면_비어있는_Optional_출력한다() {
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 8, 8, 10, 0)));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 9, 8, 10, 0)));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 10, 8, 10, 0)));

        Optional<AttendanceRecord> record = records.find(LocalDate.of(2024, 12, 1));

        assertThat(record).isEmpty();
    }


    @DisplayName("입력된 월에 해당하는 출석 기록을 확인할 수 있다.")
    @Test
    void 입력된_월에_해당하는_출석_기록을_확인할_수_있다() {
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 11, 9, 8, 10, 0)));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 9, 8, 10, 0)));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 10, 8, 10, 0)));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 11, 8, 10, 0)));

        assertThat(records.findRecordsInMonth(2024, Month.DECEMBER)).hasSize(3);
    }

    @DisplayName("주어진 기간 내의 출석 횟수를 계산할 수 있다.")
    @Test
    void 주어진_기간_내의_출석_횟수를_계산할_수_있다() {
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 10, 8, 10, 0), LATE));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 11, 8, 10, 0), LATE));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 12, 8, 10, 0), ATTENDANCE));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 13, 8, 10, 0), ATTENDANCE));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 14, 8, 10, 0), LATE));

        int attendanceCount = records.calculateAttendanceRecordCount(
                LocalDate.of(2024, 12, 11), LocalDate.of(2024, 12, 14));

        assertThat(attendanceCount).isEqualTo(2);
    }

    @DisplayName("주어진 기간 내의 지각 횟수를 계산할 수 있다.")
    @Test
    void 주어진_기간_내의_지각_횟수를_계산할_수_있다() {
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 10, 8, 10, 0), LATE));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 11, 8, 10, 0), LATE));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 13, 8, 10, 0), ATTENDANCE));
        records.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 14, 8, 10, 0), LATE));

        int attendanceCount = records.calculateLateRecordCount(
                LocalDate.of(2024, 12, 11), LocalDate.of(2024, 12, 14));

        assertThat(attendanceCount).isEqualTo(2);
    }
}