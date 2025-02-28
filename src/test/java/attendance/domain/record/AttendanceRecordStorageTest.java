package attendance.domain.record;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.checker.AttendanceType;
import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordStorageTest {

    private final String NICKNAME = "쿠키";
    private final LocalDate ARRIVAL_DATE = LocalDate.of(2025, 2, 4);
    private final LocalTime ARRIVAL_TIME = LocalTime.of(8, 50);
    private final LocalDateTime ARRIVAL_DATE_TIME = LocalDateTime.of(ARRIVAL_DATE, ARRIVAL_TIME);
    AttendanceRecordStorage recordStorage = new AttendanceRecordStorage();

    @DisplayName("출석 기록 추가 - 출석 기록을 추가할 수 있다")
    @Test
    void 출석_기록_추가_출석_기록을_추가할_수_있다() {
        AttendanceRecord newRecord = new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME, AttendanceType.ATTENDANCE);
        recordStorage.add(newRecord);

        Optional<AttendanceRecord> record = recordStorage.find(NICKNAME, ARRIVAL_DATE);
        assertThat(record).isPresent();
    }

    @DisplayName("출석 기록 추가 - 출석 기록이 이미 존재하는 날에 예외를 발생시킨다")
    @Test
    void 출석_기록_추가_출석_기록이_이미_존재하는_날에_예외를_발생시킨다() {
        AttendanceRecord newRecord = new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME, AttendanceType.ATTENDANCE);
        recordStorage.add(newRecord);

        assertThatThrownBy(() -> recordStorage.add(newRecord))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
    }

    @DisplayName("출석 기록 추가 - 결석 기록은 추가할 수 없다")
    @Test
    void 출석_기록_추가_결석_기록은_추가할_수_없다() {
        AttendanceRecord absenceRecord = new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME, AttendanceType.ABSENCE);
        recordStorage.add(absenceRecord);

        Optional<AttendanceRecord> record = recordStorage.find(NICKNAME, ARRIVAL_DATE);
        assertThat(record).isEmpty();
    }

    @DisplayName("출석 기록 제거 - 출석 기록을 제거할 수 있다")
    @Test
    void 출석_기록_제거_출석_기록을_제거할_수_있다() {
        AttendanceRecord newRecord = new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME, AttendanceType.ATTENDANCE);
        recordStorage.add(newRecord);
        recordStorage.remove(NICKNAME, ARRIVAL_DATE);

        Optional<AttendanceRecord> record = recordStorage.find(NICKNAME, ARRIVAL_DATE);
        assertThat(record).isEmpty();
    }

    @DisplayName("출석 조회 - 출석 기록을 조회할 수 있다")
    @Test
    void 출석_조회_출석_기록을_조회할_수_있다() {
        AttendanceRecord newRecord = new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME, AttendanceType.ATTENDANCE);
        recordStorage.add(newRecord);
        assertThat(recordStorage.find(NICKNAME, ARRIVAL_DATE)).isPresent();

        recordStorage.remove(NICKNAME, ARRIVAL_DATE);
        assertThat(recordStorage.find(NICKNAME, ARRIVAL_DATE)).isEmpty();
    }

    @DisplayName("결석 포함 출석 조회 - 결석을 포함해서 출석을 조회한다")
    @Test
    void 결석_포함_출석_조회_결석을_포함해서_출석을_조회한다() {
        AttendanceRecord newRecord = new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME, AttendanceType.ATTENDANCE);
        recordStorage.add(newRecord);
        AttendanceRecord attendanceRecord = recordStorage.findWithAbsenceRecord(NICKNAME, ARRIVAL_DATE);
        assertThat(attendanceRecord.getAttendanceType()).isEqualTo(AttendanceType.ATTENDANCE);

        recordStorage.remove(NICKNAME, ARRIVAL_DATE);
        AttendanceRecord absenceRecord = recordStorage.findWithAbsenceRecord(NICKNAME, ARRIVAL_DATE);
        assertThat(absenceRecord.getAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
    }

    @DisplayName("출석 횟수 조회 - 이번달의 출석 횟수를 구한다")
    @Test
    void 출석_횟수_조회_이번달의_출석_횟수를_구한다() {
        recordStorage.add(new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME.plusDays(1), AttendanceType.ATTENDANCE));
        recordStorage.add(new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME.plusDays(2), AttendanceType.ATTENDANCE));
        recordStorage.add(new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME.plusDays(3), AttendanceType.ATTENDANCE));
        recordStorage.add(new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME.minusMonths(1), AttendanceType.ATTENDANCE));

        int attendanceCount = recordStorage.calculateAttendanceCountInMonth(NICKNAME, ARRIVAL_DATE);
        assertThat(attendanceCount).isEqualTo(3);
    }

    @DisplayName("출석 횟수 조회 - 이번달의 지각 횟수를 구한다")
    @Test
    void 출석_횟수_조회_이번달의_지각_횟수를_구한다() {
        recordStorage.add(new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME.plusDays(1), AttendanceType.LATE));
        recordStorage.add(new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME.plusDays(2), AttendanceType.LATE));
        recordStorage.add(new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME.plusDays(3), AttendanceType.LATE));
        recordStorage.add(new AttendanceRecord(NICKNAME, ARRIVAL_DATE_TIME.minusMonths(1), AttendanceType.LATE));

        int lateCount = recordStorage.calculateLateCountInMonth(NICKNAME, ARRIVAL_DATE);
        assertThat(lateCount).isEqualTo(3);
    }
}