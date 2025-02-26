package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.AttendanceCurrentDateGenerateStrategy;

public class AttendanceReaderTest {

    @Test
    @DisplayName("파일로부터 출석 입력")
    void addAttendanceFromFile() {
        String fileName = "/testAttendance.csv";
        AttendanceReader attendanceReader = new AttendanceReader(fileName);
        CrewAttendances crewAttendances = new CrewAttendances(new AttendanceCurrentDateGenerateStrategy(),
                attendanceReader.readAttendances());
        CrewAttendanceHistories crewAttendanceHistories = crewAttendances.crewAttendancesHistory("투다");
        Assertions.assertThat(crewAttendanceHistories.crewAttendanceHistories().size())
                .isEqualTo(5);
    }
}
