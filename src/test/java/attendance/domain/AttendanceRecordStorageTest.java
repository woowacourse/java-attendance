package attendance.domain;

import static fixer.AttendanceRecordFixer.makeRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordStorageTest {

    AttendanceRecordStorage recordStorage;

    @BeforeEach
    void beforeEach() {
        recordStorage = new AttendanceRecordStorage();
    }

    @DisplayName("크루에 대한 출석 기록을 추가한다.")
    @Test
    void 크루에_대한_출석_기록을_추가한다() {
        AttendanceRecord record = makeRecord("쿠키", AttendanceStatusType.ATTENDANCE);

        recordStorage.add(record);

        AttendanceRecord savedRecord = recordStorage.find(record.getNickname(), record.getDate()).get();
        assertThat(savedRecord).isEqualTo(record);
    }

    @DisplayName("결석일 경우에는 출석 기록 추가가 불가능하다.")
    @Test
    void 결석일_경우에는_출석_기록_추가가_불가능하다() {
        AttendanceRecord record = makeRecord("쿠키", AttendanceStatusType.EXPULSION);

        recordStorage.add(record);

        assertThat(recordStorage.find(record.getNickname(), record.getDate())).isEmpty();
    }

    @DisplayName("출석 기록을 수정할 수 있다.")
    @Test
    void 출석_기록을_수정할_수_있다() {
        String nickname = "쿠키";
        LocalDate date = LocalDate.of(2024, 12, 10);

        AttendanceRecord originRecord = makeRecord(nickname, date.atTime(8, 50), AttendanceStatusType.ATTENDANCE);
        recordStorage.add(originRecord);

        AttendanceRecord newRecord = makeRecord(nickname, date.atTime(9, 20), AttendanceStatusType.LATE);
        recordStorage.update(newRecord);

        assertThat(recordStorage.find(nickname, date).get()).isEqualTo(newRecord);
    }

    @DisplayName("출석 기록을 결석으로 수정할 경우 기존의 출석 기록을 제거한다.")
    @Test
    void 출석_기록을_결석으로_수정할_경우_기존의_출석_기록을_제거한다() {
        String nickname = "쿠키";
        LocalDate date = LocalDate.of(2024, 12, 10);

        AttendanceRecord originRecord = makeRecord(nickname, date.atTime(8, 50),
                AttendanceStatusType.ATTENDANCE);
        recordStorage.add(originRecord);

        AttendanceRecord newRecord = makeRecord(nickname, date.atTime(9, 50), AttendanceStatusType.EXPULSION);
        recordStorage.update(newRecord);

        assertThat(recordStorage.find(nickname, date)).isEmpty();
    }
}