import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Test
    void 닉네임을_이용해_출석기록을_확인한다() {
        AttendancesFileParser attendancesFileParser = new AttendancesFileParser();
        Crews crews = attendancesFileParser.init();
        Crew crew = crews.findByName("쿠키");
        assertThat(crew.getAttendances().size()).isEqualTo(1);
    }
}

