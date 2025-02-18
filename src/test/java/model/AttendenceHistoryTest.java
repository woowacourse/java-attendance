package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendenceDetail;
import attendance.model.AttendenceHistory;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class AttendenceHistoryTest {

    @Test
    void test1() {
        AttendenceHistory attendenceHistory = new AttendenceHistory();
        assertThat(attendenceHistory).isNotNull();
    }

    @Test
    void test2() {
        AttendenceHistory attendenceHistory = new AttendenceHistory();
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 3, 10, 7)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));

        assertThat(attendenceHistory.getAttendenceHisoty()).hasSize(3);
    }

    @Test
    void test4() {
        AttendenceHistory attendenceHistory = new AttendenceHistory();
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 3, 9, 58)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));

        assertThat(attendenceHistory.getAttendenceCount()).isEqualTo(3);
    }

    @Test
    void test3() {
        AttendenceHistory attendenceHistory = new AttendenceHistory();
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 3, 10, 7)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 5, 10, 6)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 6, 10, 1)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 9, 17, 0)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 10, 10, 3)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 11, 17, 2)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 12, 17, 2)));
        attendenceHistory.addAttendenceDetail(new AttendenceDetail(LocalDateTime.of(2024, 12, 13, 10, 2)));

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(attendenceHistory.getAttendenceCount()).isEqualTo(5); //5
        softly.assertThat(attendenceHistory.getLateCount()).isEqualTo(2); //2
        softly.assertThat(attendenceHistory.getAbsenceCount()).isEqualTo(3); //
        softly.assertAll();
    }

}
