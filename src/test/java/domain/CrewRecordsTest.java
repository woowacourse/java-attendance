package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
}
