package domain;

import fixture.CrewRecordsFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CrewRecordsTest {
    @DisplayName("입력한 닉네임에 대한 출석 기록이 없을 경우 예외를 발생시킨다.")
    @Test
    void validateCrewTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.of("솔라", "2024-12-02T13:00");
        LocalDate date = LocalDate.of(2024, 12, 2);

        // when
        Crew excludedCrew = new Crew("네오");

        // then
        assertThatThrownBy(() -> crewRecords.getRecordOnDate(excludedCrew, date)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력 받은 닉네임과 LocalDateTime으로 새 출석 기록을 저장할 수 있다.")
    @Test
    void test() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.fromNicknames("브리");

        // when
        Crew crew = new Crew("브리");
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.parse("2024-12-03T13:00"));

        // then
        assertDoesNotThrow(() -> crewRecords.addRecord(crew, record));
    }

    @DisplayName("입력 받은 날짜의 출석 시간을 수정할 수 있다.")
    @Test
    void updateRecordTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.of("저스틴", "2024-12-02T13:35");
        Crew crew = new Crew("저스틴");
        AttendanceRecord oldRecord = new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:35"));
        LocalTime newTime = LocalTime.of(13, 30);
        LocalDate oldDate = LocalDate.of(2024, 12, 2);

        // when
        crewRecords.updateRecord(crew, oldDate, newTime);
        AttendanceRecord newRecord = crewRecords.getRecordOnDate(crew, oldDate);

        // then
        assertAll(
                () -> assertThat(oldRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.ABSENT),
                () -> assertThat(newRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.TARDY)
        );
    }
}
