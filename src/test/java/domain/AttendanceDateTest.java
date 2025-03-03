package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {

    @DisplayName("연,월,일을 받아서 날짜를 생성할 수 있다.")
    @Test
    void creatDate() {
        //given
        int year = 2024;
        int month = 12;
        int date = 2;

        //when
        AttendanceDate dateProvider = AttendanceDate.of(year, month, date);

        //then
        assertThat(dateProvider).isEqualTo(AttendanceDate.of(2024, 12, 2));
    }

    @DisplayName("년도를 반환한다.")
    @Test
    void getYear() {
        //given
        int year = 2024;
        int month = 12;
        int date = 2;

        AttendanceDate dateProvider = AttendanceDate.of(year, month, date);

        //when
        int actual = dateProvider.getYear();

        //then
        assertThat(actual).isEqualTo(2024);
    }

    @DisplayName("월을 반환한다.")
    @Test
    void getMonth() {
        //given
        int year = 2024;
        int month = 12;
        int date = 2;

        AttendanceDate dateProvider = AttendanceDate.of(year, month, date);

        //when
        int actual = dateProvider.getMonth();

        //then
        assertThat(actual).isEqualTo(12);
    }

    @DisplayName("날짜를 반환한다.")
    @Test
    void getDayOfMonth() {
        //given
        int year = 2024;
        int month = 12;
        int date = 2;

        AttendanceDate dateProvider = AttendanceDate.of(year, month, date);

        //when
        int actual = dateProvider.getDayOfMonth();

        //then
        assertThat(actual).isEqualTo(2);
    }

    @DisplayName("요일을 반환한다.")
    @Test
    void getDayOfWeek() {
        //given
        int year = 2024;
        int month = 12;
        int date = 2;

        AttendanceDate dateProvider = AttendanceDate.of(year, month, date);

        //when
        Calender actual = dateProvider.getDayOfWeek();

        //then
        assertThat(actual).isEqualTo(Calender.MON);
    }
}
