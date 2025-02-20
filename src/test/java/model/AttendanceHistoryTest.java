package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.Attendance;
import attendance.model.AttendanceDetail;
import attendance.model.AttendanceHistory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Test
    void test1() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        assertThat(attendanceHistory).isNotNull();
    }

    @Test
    void test2() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 10, 7)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));

        assertThat(attendanceHistory.getAttendanceHistory()).hasSize(3);
    }

    @Test
    void test4() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 9, 58)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));

        assertThat(attendanceHistory.getAttendanceCount()).isEqualTo(3);
    }

    @Test
    void test3() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 10, 7)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 5, 10, 6)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 6, 10, 1)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 9, 17, 0)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 3)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 11, 17, 2)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 12, 17, 2)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 13, 10, 2)));

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(attendanceHistory.getAttendanceCount()).isEqualTo(5); //5
        softly.assertThat(attendanceHistory.getLateCount()).isEqualTo(2); //2
        softly.assertThat(attendanceHistory.getAbsenceCount()).isEqualTo(3); //
        softly.assertAll();
    }

    @DisplayName("특정 날짜에 해당하는 출석 상세를 얻을 수 있다")
    @Test
    void test5() {
        // given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        AttendanceDetail attendanceDetail1 = new AttendanceDetail(LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceHistory.addAttendanceDetail(attendanceDetail1);

        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 9, 58)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));

        LocalDate modifyDate = LocalDate.of(2024, 12, 2);
        // when
        AttendanceDetail attendanceDetail = attendanceHistory.findAttendanceDetail(modifyDate);

        // then
        assertThat(attendanceDetail).isEqualTo(attendanceDetail1);

    }

    @DisplayName("출석 수정 후 결과가 반영된다.")
    @Test
    void test6() {
        // given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        AttendanceDetail attendanceDetail1 = new AttendanceDetail(LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceHistory.addAttendanceDetail(attendanceDetail1);

        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 9, 58)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));

        LocalDate modifyDate = LocalDate.of(2024, 12, 2);
        // when
        AttendanceDetail attendanceDetail = attendanceHistory.findAttendanceDetail(modifyDate);
        attendanceDetail.modify(LocalTime.of(13, 10));
        Assertions.assertThat(attendanceDetail.getLocalDateTime().toLocalTime()).isEqualTo(LocalTime.of(13, 10));
        Assertions.assertThat(attendanceDetail.getAttandence()).isEqualTo(Attendance.지각);
        // then
        assertThat(attendanceDetail).isEqualTo(attendanceDetail1);

    }

}
