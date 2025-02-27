package reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RawAttendancesTest {

    @Test
    @DisplayName("이름 to 날짜 to 시간 형태로 데이터를 구성할 수 있다.")
    void canParseRawAttendances() {
        // given
        String one = "one";
        String two = "two";
        String three = "three";
        RawAttendances rawAttendances = RawAttendances.from(
                List.of(
                        RawAttendance.from(one + ",2024-12-02 09:00"),
                        RawAttendance.from(two + ",2024-12-02 09:10"),
                        RawAttendance.from(two + ",2024-12-03 09:10"),
                        RawAttendance.from(three + ",2024-12-02 12:00"),
                        RawAttendance.from(three + ",2024-12-03 12:00"),
                        RawAttendance.from(three + ",2024-12-04 12:00")));

        Map<String, Map<LocalDate, LocalTime>> nicknameToDateTime = rawAttendances.parseData();

        // when
        Map<LocalDate, LocalTime> dateToTime_one = nicknameToDateTime.get(one);
        Map<LocalDate, LocalTime> dateToTime_two = nicknameToDateTime.get(two);
        Map<LocalDate, LocalTime> dateToTime_three = nicknameToDateTime.get(three);

        // then
        assertAll(
                () -> assertThat(dateToTime_one.size()).isEqualTo(1),
                () -> assertThat(dateToTime_one.get(LocalDate.of(2024, 12, 2)))
                        .isEqualTo(LocalTime.of(9, 0)),

                () -> assertThat(dateToTime_two.size()).isEqualTo(2),
                () -> assertThat(dateToTime_two.get(LocalDate.of(2024, 12, 2)))
                        .isEqualTo(LocalTime.of(9, 10)),

                () -> assertThat(dateToTime_three.size()).isEqualTo(3),
                () -> assertThat(dateToTime_three.get(LocalDate.of(2024, 12, 2)))
                        .isEqualTo(LocalTime.of(12, 0))
        );

    }

}