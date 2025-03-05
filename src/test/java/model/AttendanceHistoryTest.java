package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceHistoryTest {

    @DisplayName("출석 기록을 추가한다")
    @Test
    void addAttendanceTest() {
        AttendanceHistory history = new AttendanceHistory();
        AttendanceDate date = new AttendanceDate(LocalDate.of(2024, 12, 3));
        AttendanceTime time = new AttendanceTime(LocalTime.of(9, 50));

        history.add(date, time);

        assertThat(history.getHistory()).containsEntry(date, time);
    }

    @DisplayName("이미 출석 한 경우 예외가 발생한다")
    @Test
    void alreadyAttendanceTest() {
        AttendanceHistory history = new AttendanceHistory();
        AttendanceDate date = new AttendanceDate(LocalDate.of(2024, 12, 3));
        AttendanceTime time = new AttendanceTime(LocalTime.of(9, 50));

        history.add(date, time);

        assertThatThrownBy(() -> history.add(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석 기록이 있습니다. 수정 기능을 이용해주세요.");
    }

    @DisplayName("출석을 수정한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-03, 09:50",
            "2024-12-04, 10:00"
    })
    void modifyAttendanceTest(final LocalDate date, final LocalTime time) {
        AttendanceDate attendanceDate = new AttendanceDate(date);
        AttendanceTime attendanceTime = new AttendanceTime(time);

        AttendanceHistory history = new AttendanceHistory();
        history.add(attendanceDate, attendanceTime);

        history.modify(attendanceDate, attendanceTime);
        assertThat(history.getHistory()).containsEntry(attendanceDate, attendanceTime);
    }

    @DisplayName("출석 기록 및 상태를 날짜순으로 조회한다")
    @Test
    void getHistoryWithAttendanceTypeTest() {
        AttendanceHistory attendanceHistory = AttendanceHistoryFactory.make(Stream.of(
                LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2024, 12, 3, 10, 7),
                LocalDateTime.of(2024, 12, 4, 10, 2),
                LocalDateTime.of(2024, 12, 5, 10, 6),
                LocalDateTime.of(2024, 12, 6, 10, 1),
                LocalDateTime.of(2024, 12, 10, 10, 3),
                LocalDateTime.of(2024, 12, 13, 10, 2)
        ));

        List<AttendanceHistoryByDate> expected = List.of(
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 2),
                        LocalTime.of(13, 0),
                        AttendanceType.NONE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 3),
                        LocalTime.of(10, 7),
                        AttendanceType.LATE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 4),
                        LocalTime.of(10, 2),
                        AttendanceType.NONE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 5),
                        LocalTime.of(10, 6),
                        AttendanceType.LATE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 6),
                        LocalTime.of(10, 1),
                        AttendanceType.NONE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 9),
                        LocalTime.MAX,
                        AttendanceType.ABSENT),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 10),
                        LocalTime.of(10, 3),
                        AttendanceType.NONE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 11),
                        LocalTime.MAX,
                        AttendanceType.ABSENT),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 12),
                        LocalTime.MAX,
                        AttendanceType.ABSENT),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 2),
                        AttendanceType.NONE));

        assertThat(attendanceHistory.getHistoryWithAttendanceType(LocalDate.of(2024, 12, 14)))
                .containsExactlyElementsOf(expected);
    }

}
