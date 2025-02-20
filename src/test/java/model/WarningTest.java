package model;

import attendance.model.AttendanceDetail;
import attendance.model.AttendanceWarning;
import attendance.model.Crew;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WarningTest {

    @Test
    void test1() {
        AttendanceWarning warning = AttendanceWarning.경고;
        Assertions.assertThat(warning).isNotNull();
    }

    @Test
    void test2() {
        Crew crew = new Crew("빙티");
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 13, 0)));
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 13, 7)));
        Assertions.assertThat(AttendanceWarning.from(crew)).isEqualTo(AttendanceWarning.경고);
    }

    @Test
    void test3() {
        Crew crew = new Crew("빙티");
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 17, 2)));
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 17, 2)));
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 5, 17, 2)));
        Assertions.assertThat(AttendanceWarning.from(crew)).isEqualTo(AttendanceWarning.면담);
    }

    @Test
    void test4() {
        Crew crew = new Crew("빙티");
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 17, 2)));
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 17, 2)));
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 5, 17, 2)));
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 6, 17, 2)));
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 17, 2)));
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 11, 17, 2)));
        Assertions.assertThat(AttendanceWarning.from(crew)).isEqualTo(AttendanceWarning.제적);
    }

    @Test
    void test5() {
        Crew crew = new Crew("빙티");
        crew.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 17, 2)));
        Assertions.assertThat(AttendanceWarning.from(crew)).isEqualTo(AttendanceWarning.해당없음);
    }
}
