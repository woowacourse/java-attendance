package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {

    Attendance attendance1;
    Attendance attendance2;

    @BeforeEach
    void setUp() {
        attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
        attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
    }

    @DisplayName("출석 시간을 수정한다.")
    @Test
    void 출석_시간을_수정한다() {

        // given
        LocalTime modifyTime = LocalTime.of(10, 5);

        // when
        Attendance modifyAttendance = attendance1.modifyAttendanceTime(modifyTime);

        // then
        assertThat(attendance1).isEqualTo(modifyAttendance);
    }

    @DisplayName("이름이 같으면 true 아니면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "체체,true", "체체2,false"
    })
    void 이름이_같으면_true_아니면_false를_반환한다(String name, boolean result) {

        // given
        Attendance attendance = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));

        // when & then
        assertThat(attendance.isSameCrewName(name)).isEqualTo(result);
    }

    @DisplayName("년 월이 같다면 true, 다르면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,true", "2025,3, false"
    })
    void 년_월이_같다면_true_다르면_false를_반환한다(int year, int month, boolean result) {

        // given
        Attendance attendance = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 5)));

        // when
        boolean isSame = attendance.isSameYearAndMonth(year, month);

        // then
        AssertionsForClassTypes.assertThat(isSame).isEqualTo(result);
    }

    @DisplayName("이름과 연월일이 같으면 true 다르면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "체체, 2025,2,27, true", "체체, 2025,2,28, false", "추추, 2025,2,27, false", "추추,2025,2,28, false"
    })
    void 이름과_연월일이_같으면_true_다르면_false를_반환한다(String name, int year, int month, int day, boolean result) {

        // given
        Attendance attendance = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 5)));

        // when & then
        assertThat(attendance.isSameNameAndLocalDate(name, LocalDate.of(year, month, day))).isEqualTo(result);
    }

    @DisplayName("연월일 또는 이름이 다르면 두 객체는 다르다.")
    @ParameterizedTest
    @CsvSource(value = {
            "체체,2025,2,27, 추추,2025,2,27,false", "체체,2025,2,27,체체,2025,2,28,false",
            "체체,2025,2,27, 추추,2025,2,28,false", "체체,2025,2,27,체체,2025,2,27,true"
    })
    void 연월일이_다르면_두_객체는_다르다(String crewName, int year, int month, int day, String otherCrewName, int otherYear,
                            int otherMonth, int otherDay, boolean result) {

        // given
        Attendance attendance = new Attendance(crewName, new Time(LocalDateTime.of(year, month, day, 10, 0)));
        Attendance otherAttendance = new Attendance(otherCrewName,
                new Time(LocalDateTime.of(otherYear, otherMonth, otherDay, 10, 0)));

        // when
        boolean isEqual = attendance.equals(otherAttendance);

        // then
        assertThat(isEqual).isEqualTo(result);
    }


}
