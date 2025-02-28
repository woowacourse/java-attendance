package view;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import model.AttendanceDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputViewTest {
    @Test
    @DisplayName("메뉴에 없는 번호를 누를시 예외를 발생하는 메서드 테스트")
    void test1() {
        String input = "5";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertThatThrownBy(()->InputView.isMenuOption("5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 메뉴에 없는 선택지 입니다.");
    }

    @Test
    @DisplayName("캠퍼스 운영 시간이 아닌 시간에 입실한 경우 예외를 발생하는 메서드 테스트")
    void test2() {
        assertThatThrownBy(() -> InputView.isNotOpeningHour(new AttendanceDateTime(LocalDateTime.of(2024,12,12,0,0))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }
}