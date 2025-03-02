import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceRecord;
import domain.DateProvider;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void 크루의_출석을_등록한다() {
        String nickname = "이든";
        String time = "09:59";

        Crew crew = new Crew(nickname, time,
                () -> LocalDate.of(2024, 12, 13));

        assertThat(crew.nickname).isEqualTo(nickname);
    }

    class Crew {

        private final String nickname;
        private final AttendanceRecord attendanceRecord;

        public Crew(String nickname, String time, DateProvider dateProvider) {
            this.nickname = nickname;
            this.attendanceRecord = new AttendanceRecord(dateProvider);
            this.attendanceRecord.attend(time);
        }
    }
}
