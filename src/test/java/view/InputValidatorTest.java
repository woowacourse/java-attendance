package view;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.ERROR_HEADER;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceRecord;
import domain.CrewName;
import dto.InitialInformation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {
    AttendanceBook attendanceBook = new AttendanceBook(new InitialInformation(
            Map.of(
                    new CrewName("미미"), new AttendanceRecord(),
                    new CrewName("미미미미"), new AttendanceRecord())
    ));

    @DisplayName("선택한 메뉴가 존재할 경우 정상적으로 검증을 마친다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q", "q"})
    void test5(String validSelectedMenu) {
        assertDoesNotThrow(() -> InputValidator.validateSelectedMenu(validSelectedMenu));
    }

    @DisplayName("선택한 메뉴가 존재하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "" , " "})
    void test4(String invalidSelectedMenu) {
        assertThatThrownBy(() -> InputValidator.validateSelectedMenu(invalidSelectedMenu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("시간 형식이 올바를 경우 정상적으로 검증을 마친다.")
    @ParameterizedTest
    @ValueSource(strings = {"09:59", "10:00", "08:00", "23:00"})
    void test6(String validTime) {
        assertDoesNotThrow(() -> InputValidator.validateTime(validTime));
    }


    @DisplayName("시간 형식이 올바르지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"9:59", "", " ", "a", "1", "10:3", "10;10"})
    void test1(String invalidTime) {
        assertThatThrownBy(() -> InputValidator.validateTime(invalidTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("캠퍼스 운영시간 전에는 출석을 받지 않는다.")
    @Test
    void test2() {
        assertThatThrownBy(() -> InputValidator.validateTime("07:59"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("캠퍼스 운영시간 후에는 출석을 받지 않는다.")
    @Test
    void test3() {
        assertThatThrownBy(() -> InputValidator.validateTime("23:01"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("닉네임 자릿수가 유효할 경우 정상적으로 검증을 마친다.")
    @ParameterizedTest
    @ValueSource(strings = {"미미", "미미미미"})
    void test7(String validName) {


        assertDoesNotThrow(() -> InputValidator.validateName(validName, attendanceBook));
    }

    @DisplayName("닉네임이 1자 이하, 5자 이상일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"미", "미미미미미", "", " "})
    void test2(String invalidName) {
        assertThatThrownBy(() -> InputValidator.validateName(invalidName, attendanceBook))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("출석부에 존재하지 않는 닉네임일 경우 예외가 발생한다.")
    @Test
    void test9() {
        assertThatThrownBy(() -> InputValidator.validateName("밍트", attendanceBook))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("날짜 형식이 올바를 경우 정상적으로 검증을 마친다.")
    @ParameterizedTest
    @ValueSource(strings = {"2"})
    void test8(String validDay) {
        assertDoesNotThrow(() -> InputValidator.validateDay(validDay));
    }


    @DisplayName("날짜 형식이 올바르지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "32", "0"})
    void test3(String invalidDay) {
        assertThatThrownBy(() -> InputValidator.validateDay(invalidDay))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("주말에는 출석을 받지 않는다.")
    @Test
    void test4() {
        assertThatThrownBy(() -> InputValidator.validateDay("1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("공휴일에는 출석을 받지 않는다.")
    @Test
    void test5() {
        assertThatThrownBy(() -> InputValidator.validateDay("25"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }
}
