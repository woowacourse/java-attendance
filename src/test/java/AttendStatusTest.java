import domain.Attend;
import domain.AttendStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendStatusTest {
    @ParameterizedTest
    @DisplayName("월요일인 정상 출석 시간의 출석 객체가 주어졌을 때 출석 출결 상태를 계산하여 반환한다")
    @CsvSource(value = {"08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "13:05"})
    void should_return_ATTEND_AttendStatus_By_monday_attend_time(String time) {
        //given
        Attend attend = Attend.of("9", time);

        //when
        AttendStatus result = AttendStatus.calculateAttend(attend);

        //then
        Assertions.assertThat(result).isEqualTo(AttendStatus.ATTEND);
    }

    @ParameterizedTest
    @DisplayName("월요일인 지각 출석 시간의 출석 객체가 주어졌을 때 지각 출결 상태를 계산하여 반환한다")
    @CsvSource(value = {"13:06", "13:07", "13:08", "13:09", "13:10",
            "13:11", "13:12", "13:13", "13:14", "13:15",
            "13:16", "13:17", "13:18", "13:19", "13:20",
            "13:21", "13:22", "13:23", "13:24", "13:25",
            "13:26", "13:27", "13:28", "13:29", "13:30"})
    void should_return_LATE_AttendStatus_By_monday_late_time(String time) {
        //given
        Attend attend = Attend.of("9", time);

        //when
        AttendStatus result = AttendStatus.calculateAttend(attend);

        //then
        Assertions.assertThat(result).isEqualTo(AttendStatus.LATE);
    }

    @ParameterizedTest
    @DisplayName("월요일인 결석 출석 시간의 출석 객체가 주어졌을 때 결석 출결 상태를 계산하여 반환한다")
    @CsvSource(value = {",", "13:31", "13:32", "14:00", "15:00"})
    void should_return_ABSENCE_AttendStatus_By_monday_absence_time(String time) {
        //given
        Attend attend = Attend.fromDay(9);
        if (time != null) {
            attend = Attend.of("9", time);
        }

        //when
        AttendStatus result = AttendStatus.calculateAttend(attend);

        //then
        Assertions.assertThat(result).isEqualTo(AttendStatus.ABSENCE);
    }

    @ParameterizedTest
    @DisplayName("화-금요일인 정상 출석 시간의 출석 객체가 주어졌을 때 출결 상태를 계산하여 반환한다")
    @CsvSource(value = {"08:00", "09:00", "10:00",
            "10:01", "10:02", "10:03", "10:04", "10:05"})
    void should_return_ATTEND_AttendStatus_By_not_monday_attend_time(String time) {
        for (int i = 10; i <= 13; ++i) {
            //given
            Attend attend = Attend.of(String.valueOf(i), time);

            //when
            AttendStatus result = AttendStatus.calculateAttend(attend);

            //then
            Assertions.assertThat(result).isEqualTo(AttendStatus.ATTEND);
        }
    }

    @ParameterizedTest
    @DisplayName("화-금요일인 지각 출석 시간의 출석 객체가 주어졌을 때 출결 상태를 계산하여 반환한다")
    @CsvSource(value = {"10:06", "10:07", "10:08", "10:09", "10:10",
            "10:11", "10:12", "10:13", "10:14", "10:15",
            "10:16", "10:17", "10:18", "10:19", "10:20",
            "10:21", "10:22", "10:23", "10:24", "10:25",
            "10:26", "10:27", "10:28", "10:29", "10:30"})
    void should_return_LATE_AttendStatus_By_not_monday_late_time(String time) {
        for (int i = 10; i <= 13; ++i) {
            //given
            Attend attend = Attend.of(String.valueOf(i), time);

            //when
            AttendStatus result = AttendStatus.calculateAttend(attend);

            //then
            Assertions.assertThat(result).isEqualTo(AttendStatus.LATE);
        }
    }

    @ParameterizedTest
    @DisplayName("화-금요일인 결석 출석 시간의 출석 객체가 주어졌을 때 결석 출결 상태를 계산하여 반환한다")
    @CsvSource(value = {",", "13:31", "13:32", "14:00", "15:00"})
    void should_return_ABSENCE_AttendStatus_By_not_monday_absence_time(String time) {
        for (int i = 10; i <= 13; ++i) {
            //given
            Attend attend = Attend.fromDay(i);
            if (time != null) {
                attend = Attend.of(String.valueOf(i), time);
            }

            //when
            AttendStatus result = AttendStatus.calculateAttend(attend);

            //then
            Assertions.assertThat(result).isEqualTo(AttendStatus.ABSENCE);
        }
    }
}
