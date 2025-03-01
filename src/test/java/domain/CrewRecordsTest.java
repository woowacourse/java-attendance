package domain;

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
        CrewRecords crewRecords = new CrewRecords();
        Crew includedCrew = new Crew("솔라");
        Crew excludedCrew = new Crew("네오");

        // when
        crewRecords.addCrewRecords(includedCrew, new AttendanceRecords());

        // then
        assertThatThrownBy(() -> crewRecords.validateCrew(excludedCrew)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력 받은 닉네임과 LocalDateTime으로 새 출석 기록을 저장할 수 있다.")
    @Test
    void test() {
        // given
        CrewRecords crewRecords = new CrewRecords();
        Crew crew = new Crew("브리");
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.parse("2024-12-03T13:00"));

        // when
        crewRecords.addCrewRecords(crew, new AttendanceRecords());

        // then
        assertDoesNotThrow(() -> crewRecords.addRecord(crew, record));
    }

    @DisplayName("입력 받은 날짜의 출석 시간을 수정할 수 있다.")
    @Test
    void editRecordTest() {
        // given
        CrewRecords crewRecords = new CrewRecords();
        Crew crew = new Crew("저스틴");
        AttendanceRecord oldRecord = new AttendanceRecord(LocalDateTime.parse("2024-12-03T13:10"));
        LocalTime newTime = LocalTime.of(13, 0);
        LocalDate oldDate = LocalDate.of(2024, 12, 3);

        // when
        crewRecords.addCrewRecords(crew, new AttendanceRecords());
        crewRecords.addRecord(crew, oldRecord);
        crewRecords.editRecord(oldDate, newTime);

        // then
        assertAll(
                () -> assertThat(oldRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.TARDY),
                () -> assertThat(crewRecords.getRecordOnDate(crew, oldDate).getAttendanceStatus()).isEqualTo(AttendanceStatus.PRESENT)
        );

    }
}
