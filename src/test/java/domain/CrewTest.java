package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class CrewTest {
    @Test
    void 해당_날짜에_출석을_저장한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 10);
        LocalTime localTime = LocalTime.of(10, 04);

        Crew crew = new Crew("시소");
        crew.addAttendStatus(LocalDateTime.of(localDate, localTime));

        assertThat(crew.getAttendanceTime(localDate)).isEqualTo(localTime);
    }

    @Test
    void 해당_날짜에_대한_출석을_수정한다() {
        LocalDate attendanceDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 04);
        LocalTime modifiedTime = LocalTime.of(10, 20);

        Crew crew = new Crew("시소");

        crew.addAttendStatus(LocalDateTime.of(attendanceDate, attendanceTime));
        crew.editAttendStatus(LocalDateTime.of(attendanceDate, modifiedTime));

        assertThat(crew.getAttendanceTime(attendanceDate)).isEqualTo(modifiedTime);
    }

    @Test
    void 결석_횟수를_계산한다() {
        Crew crew = new Crew("두리");
        for(int day = 2; day <= 6; day ++) {
            crew.addAttendStatus(LocalDateTime.of(2024, 12, day, 10, 20, 0));
        }

        assertThat(crew.calculateAbsenceCount()).isEqualTo(0);
    }

    @Test
    void 지각_횟수를_계산한다() {
        Crew crew = new Crew("두리");
        for(int day = 3; day <= 6; day ++) {
            crew.addAttendStatus(LocalDateTime.of(2024, 12, day, 10, 20, 0));
        }

        assertThat(crew.calculateTardyCount()).isEqualTo(4);
    }
}