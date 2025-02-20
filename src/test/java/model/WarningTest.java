package model;

import attendance.model.AttendanceDetail;
import attendance.model.AttendanceWarning;
import attendance.model.Crew;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WarningTest {

    @Test
    void 크루가_2번에서_4번결석한_경우_경고를_받는다() {
        Crew crew = new Crew("빙티");
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 13, 0)));
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 13, 7)));
        Assertions.assertThat(AttendanceWarning.from(crew)).isEqualTo(AttendanceWarning.경고);
    }

    @Test
    void 크루가_3번에서_5번_결석한_경우_면답를_받는다() {
        Crew crew = new Crew("빙티");
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 17, 2)));
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 17, 2)));
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 5, 17, 2)));
        Assertions.assertThat(AttendanceWarning.from(crew)).isEqualTo(AttendanceWarning.면담);
    }

    @Test
    void 크루가_6이상_결석할_경우_제적이다() {
        Crew crew = new Crew("빙티");
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 17, 2)));
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 17, 2)));
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 5, 17, 2)));
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 6, 17, 2)));
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 17, 2)));
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 11, 17, 2)));
        Assertions.assertThat(AttendanceWarning.from(crew)).isEqualTo(AttendanceWarning.제적);
    }

    @Test
    void 크루가_2번_미만_결석한_경우_해당없음이다() {
        Crew crew = new Crew("빙티");
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 17, 2)));
        Assertions.assertThat(AttendanceWarning.from(crew)).isEqualTo(AttendanceWarning.해당없음);
    }
}
