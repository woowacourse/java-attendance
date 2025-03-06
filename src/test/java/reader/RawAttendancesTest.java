package reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RawAttendancesTest {

    @Test
    @DisplayName("이름(String) to 날짜시간(LocalDateTime) 형태로 데이터를 구성할 수 있다.")
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

        Map<String, List<LocalDateTime>> nicknameToDateTime = rawAttendances.parseData();

        // when
        List<LocalDateTime> dateToTime_one = nicknameToDateTime.get(one);
        List<LocalDateTime> dateToTime_two = nicknameToDateTime.get(two);
        List<LocalDateTime> dateToTime_three = nicknameToDateTime.get(three);

        // then
        assertAll(
                () -> assertThat(dateToTime_one.size()).isEqualTo(1),
                () -> assertThat(dateToTime_one.contains(LocalDateTime.of(2024, 12, 2, 9, 0))).isTrue(),

                () -> assertThat(dateToTime_two.size()).isEqualTo(2),
                () -> assertThat(dateToTime_two.contains(LocalDateTime.of(2024, 12, 3, 9, 10))).isTrue(),

                () -> assertThat(dateToTime_three.size()).isEqualTo(3),
                () -> assertThat(dateToTime_three.contains(LocalDateTime.of(2024, 12, 4, 12, 0))).isTrue()
        );
    }
}
