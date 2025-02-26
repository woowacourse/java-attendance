package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void 출석_기록이_없는날을_수정하면_예외를_던진다() {
        Crew crew = new Crew("두리");
        assertThatThrownBy(() -> {
            crew.editAttendStatus(LocalDateTime.of(2024, 12, 10, 10, 30));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 미래_날짜에_출석시_예외를_던진다() {
        Crew crew = new Crew("두리");
        assertThatThrownBy(() -> {
            crew.validateAvailableAttendanceDate(LocalDate.of(2029, 12, 25));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_출석한_날에_출석시_예외를_던진다() {
        Crew crew = new Crew("두리");
        crew.addAttendStatus(LocalDateTime.of(2024, 12, 5, 10, 30));
        assertThatThrownBy(() -> {
            crew.validateAvailableAttendanceDate(LocalDate.of(2024, 12, 5));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크루의_닉네임이_일치하는지_확인한다() {
        String name = "시소";
        Crew crew = new Crew(name);

        assertThat(crew.isNameMatch(name)).isEqualTo(true);
    }
}