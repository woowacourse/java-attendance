package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {

    LocalDate localDate1;
    LocalDate localDate2;
    String hour;
    String minute;
    Attendance attendance1;
    Attendance attendance2;

    @BeforeEach
    void setUp() {
        localDate1 = LocalDate.of(2025, 2, 17);
        hour = "10";
        minute = "00";
        localDate2 = LocalDate.of(2025, 2, 18);

        attendance1 = new Attendance("체체", new Time(localDate1, hour, minute, false));
        attendance2 = new Attendance("체체", new Time(localDate2, hour, minute, false));


    }

    @DisplayName("주어진 출결 기록과 같은 날짜라면 true를 반환한다")
    @Test
    void 주어진_출결_기록과_같은_날짜라면_true를_반환한다() {

        //given
        Attendance attendance3 = new Attendance("체체", new Time(localDate1, hour, minute, false));
        //when
        boolean isEqual = attendance1.isAlreadyAttendance(attendance3);

        //then
        assertThat(isEqual).isTrue();
    }

    @DisplayName("주어진 출결 기록과 다른 날짜라면 false를 반환한다")
    @Test
    void 주어진_출결_기록과_다른_날짜라면_false를_반환한다() {

        //given

        //when
        boolean isEqual = attendance1.isAlreadyAttendance(attendance2);

        //then
        assertThat(isEqual).isFalse();
    }

    @DisplayName("주어진 시간으로 출석 기록을 변경한다.")
    @Test
    void 주어진_시간으로_출석_기록을_변경한다() {

        // given
        Time time = new Time(localDate1, "11", "00", false);

        // when
        attendance1.modifyAttendanceTime(time);
        // then
        assertThat(attendance1.getAttendanceTime()).isEqualTo(time);
    }

    @ParameterizedTest
    @CsvSource(value = {"2024,12,13,10,00,출석", "2024,12,13,10,05,출석", "2024,12,13,10,06,지각", "2024,12,13,10,31,결석",
            "2024,12,9,12,30,출석", "2024,12,9,13,06,지각", "2024,12,9,13,31,결석"})
    void 시간에_맞는_출결_상태를_갖는다(int year, int month, int day, String hour, String minute, String result) {

        // given
        LocalDate localDate = LocalDate.of(year, month, day);
        Attendance attendance = new Attendance("체체", new Time(localDate, hour, minute, false));

        // when && then
        assertThat(attendance.getAttendanceStatus()).isEqualTo(result);
    }
}
