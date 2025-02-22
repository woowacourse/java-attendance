package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

    @DisplayName("이미 출석을 완료한 경우 출석을 새롭게 저장할 수 없다.")
    @Test
    void 이미_출석을_완료한_경우_출석을_새롭게_저장할_수_없다() {
        AttendanceRecord record = makeRecord("쿠키", AttendanceStatusType.ATTENDANCE);
        recordStorage.add(record);

        AttendanceRecord newRecord = makeRecord("쿠키", AttendanceStatusType.LATE);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> recordStorage.add(newRecord))
                .withMessage("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
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

    @DisplayName("크루 이름으로 해당 크루의 출석 기록을 확인할 수 있다.")
    @Test
    void 크루_이름으로_해당_크루의_출석_기록을_확인할_수_있다() {
        recordStorage.add(makeRecord("쿠키", LocalDateTime.of(2024, 11, 9, 8, 10, 0)));
        recordStorage.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 9, 8, 10, 0)));
        recordStorage.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 10, 8, 10, 0)));
        recordStorage.add(makeRecord("쿠키", LocalDateTime.of(2024, 12, 11, 8, 10, 0)));

        assertThat(recordStorage.findUnmodifiedRecordsByNickname("쿠키", Month.DECEMBER)).hasSize(3);
    }

    public static AttendanceRecord makeRecord(
            String nickname, AttendanceStatusType attendanceType
    ) {
        LocalDateTime arrivalDateTime = LocalDateTime.of(2024, 12, 9, 8, 10, 0);
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }

    public static AttendanceRecord makeRecord(
            String nickname, LocalDateTime arrivalDateTime
    ) {
        return new AttendanceRecord(nickname, arrivalDateTime, AttendanceStatusType.ATTENDANCE);
    }

    public static AttendanceRecord makeRecord(
            String nickname, LocalDateTime arrivalDateTime, AttendanceStatusType attendanceType
    ) {
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }
}